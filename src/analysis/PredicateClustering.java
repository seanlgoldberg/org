package analysis;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class PredicateClustering {

	List<Predicate> allPredicates;
	Set<Predicate> allLinkingPreds;
	Map<String, Set<String>> range_clusters;
	Map<String, Set<String>> domain_clusters;
	Map<String, Set<Predicate>> linkingPreds;
	String filename;
	static final int CLUSTER_SIZE = 5;
	static final int RELATIONS_PER_PREDICATE = 1;
	
	public PredicateClustering(List<Predicate> p, String f) {
		allPredicates = p;
		allLinkingPreds = new TreeSet<Predicate>();
		filename = f;
		partition();
		findLinkers();
		printClusters();
		printLinking();
		printFunctionality();
	}
	
	public void partition() {
		range_clusters = new HashMap<String, Set<String>>();
		domain_clusters = new HashMap<String, Set<String>>();
		Set<String> objects;
		for (Predicate pred : allPredicates) {
			//String a = pred.arg1 + " : " + pred.relation;
			//String b = pred.relation + " : " + pred.arg1;
			String a = pred.relation;
			String b = pred.relation;
			if (range_clusters.get(a) != null) {
				objects = range_clusters.get(a);
				objects.add(pred.arg2);
				range_clusters.put(a, objects);
			} else {
				objects = new HashSet<String>();
				objects.add(pred.arg2);
				range_clusters.put(a, objects);
			}
			if (domain_clusters.get(b) != null) {
				objects = domain_clusters.get(b);
				objects.add(pred.arg1);
				domain_clusters.put(b,objects);
			} else {
				objects = new HashSet<String>();
				objects.add(pred.arg1);
				domain_clusters.put(b,objects);
			}
		}
	}
	
	public void findLinkers() {
		linkingPreds = new HashMap<String, Set<Predicate>>();
		for (String k : range_clusters.keySet()) {
			Set<String> cluster = range_clusters.get(k);
			for (String s1 : cluster) {
				for (String s2: cluster) {
					//s1 and s2 are the objects of some subject-verb pair in the same cluster
					//search through all preds to find relation containing s1 and s2
					for (Predicate pred : allPredicates) {
						if ((pred.arg1.equals(s1) && pred.arg2.equals(s2)) ||
								(pred.arg2.equals(s1) && pred.arg1.equals(s2))) {
							String a = s1 + " : " + s2;
							if (linkingPreds.get(a) != null) {
								Set<Predicate> value = linkingPreds.get(a);
								value.add(pred);
								linkingPreds.put(a, value);
							} else {
								Set<Predicate> value = new HashSet<Predicate>();
								value.add(pred);
								linkingPreds.put(a, value);
							}
						}
					}
				}
			}
		}
	}
	
	public void printFunctionality() {
		System.out.println("!!!!");
		try {
			FileWriter f = new FileWriter("results/predicate_functionality.csv", false);
			for (String k : range_clusters.keySet()) {
				Set<String> range_cluster = range_clusters.get(k);
				Set<String> domain_cluster = domain_clusters.get(k);
				f.write(k.replace(",","") + ", " + domain_cluster.size() + ", " + range_cluster.size() 
						+ ", " + (range_cluster.size()+domain_cluster.size()) + "\n");
			}
			f.close();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void printClusters() {
		try {
			FileWriter f = new FileWriter("results/predicate_clusters.txt",false);
			for (String k : range_clusters.keySet()) {
				Set<String> cluster = range_clusters.get(k);
				if (cluster.size() > CLUSTER_SIZE) {	
					f.write(k + "\n");
					
					
					for (String s : cluster) {
						f.write(s + "\n");
					}
					
					for (String s1 : cluster) {
						for (String s2 : cluster) {
							String a = s1 + " : " + s2;
							if (linkingPreds.get(a) != null) {
								Set<Predicate> preds = linkingPreds.get(a);
								if (preds.size() > RELATIONS_PER_PREDICATE) {
									for (Predicate p : preds) {
										f.write(p + "\n");
										allLinkingPreds.add(p);
									}
								}
							}
						}
					}
					
					f.write("\n");
				}
			}
			f.close();
		} catch (IOException ex) {
			
		}
	}
	
	public void printLinking() {
		try {
			FileWriter f = new FileWriter("results/" + filename);
			for (Predicate p : allLinkingPreds) {
				f.write(p + "\n");
			}
			f.close();
		} catch(IOException ex) {
			
		}
	}
}

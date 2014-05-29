package analysis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RulePrinter {
	
	// Used to initialize a file that will be part of an appending process
	public static void initializeFile(String filename) {
		try {
			FileWriter f = new FileWriter("results/" + filename, false);
			f.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void printRules(String filename, List<Rule> rules, boolean append) {
		try {
			FileWriter f = new FileWriter("results/" + filename, append);
			System.out.println("Printing " + rules.size() + " rules to " + filename);
			for (Rule r : rules) {
				f.write(r.toString() + "\n");
			}
			f.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void printRules(String filename, Set<Rule> rules, boolean append) {
		try {
			FileWriter f = new FileWriter("results/" + filename, append);
			for (Rule r : rules) {
				f.write(r.toString() + "\n");
			}
			f.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void printRules(String filename, TreeMap<String,Double> map) {
		Set<String> keys = map.keySet();
		try {
			FileWriter f = new FileWriter("results/" + filename);
			for (String k : keys) {
				double count = map.get(k);
				f.write(k + "\t" + count + "\n");
			}
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void printPredicates(String filename, List<Predicate> preds, boolean append) {
		try {
			FileWriter f = new FileWriter("results/" + filename, append);
			System.out.println("Printing " + preds.size() + " predicates to " + filename);
			for (Predicate pred : preds) {
				f.write(pred.toString() + "\n");
			}
			f.close();
		} catch(IOException ex) {
			
		}
	}
	
	public static void printPredicates(String filename, Set<Predicate> preds, boolean append) {
		try {
			FileWriter f = new FileWriter("results/" + filename, append);
			System.out.println("Printing " + preds.size() + " predicates to " + filename);
			for (Predicate pred : preds) {
				f.write(pred.toString() + "\n");
			}
			f.close();
		} catch(IOException ex) {
			
		}
	}
	
}

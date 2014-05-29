package analysis;

import java.io.*;
import java.util.*;

public class Evaluator {
/*
	public static void evaluate(ArrayList<Rule> candidate, ArrayList<Rule> truth, boolean multFiles) {
		HashSet<Rule> matching = new HashSet<Rule>();
		HashSet<Rule> nonMatching = new HashSet<Rule>();
		int flag = 0;
		for (Rule i : truth) {
			for (Rule j : candidate) {
				if (i.equals(j)) {
					flag = 1;
					matching.add(i);
				}
			}
			
			if (flag == 0) {
				
				nonMatching.add(i);
			}
			flag = 0;
			
		}
	
		if (multFiles) {
			printRules(phrase[i] + "_matchingRules.csv", matching, false);
			printRules(phrase[i] + "_nonMatchingRules.csv", nonMatching, false);
		} else {
			printRules("matchingRules.csv", matching, true);
			printRules("nonMatchingRules.csv", nonMatching, true);
		}
	}
	
*/
	
	public static void evaluate(String file1, String file2) {
		Set<String> predictions = new HashSet<String>();
		Set<String> gold_truth = new HashSet<String>();
		try {
			BufferedReader br1 = new BufferedReader(new FileReader("results/" + file1));
			BufferedReader br2 = new BufferedReader(new FileReader("results/" + file2));
			while(true) {
				String line = br1.readLine();
				if (line == null) break;
				predictions.add(line);
			}
			while(true) {
				String line = br2.readLine();
				if (line == null) break;
				gold_truth.add(line);
			}
			//create a copy of predictions
			Set<String> tmp = new HashSet<String>();
			for (String s : predictions) {
				tmp.add(s);
			}
			tmp.retainAll(gold_truth);
			
			double precision = ((double) tmp.size())/predictions.size();
			double recall = ((double) tmp.size())/gold_truth.size();
			System.out.println("Predictions Size: " + predictions.size());
			System.out.println("Gold Truth Size: " + gold_truth.size());
			System.out.println("Intersection Size: " + tmp.size());
			System.out.println("Precision: " + precision);
			System.out.println("Recall: " + recall);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static void evalTest(ArrayList<Rule> candidate, ArrayList<Rule> truth) {
		try {
			FileWriter f = new FileWriter("evalTest.txt");
			for (Rule i : candidate) {
				for (Rule j : truth) {
					if (i.base.equals(j.base) || i.base.equals(j.linker)) {
						f.write(i.toString() + "\n");
						f.write(j.toString() + "\n");
						f.write(i.equals(j) + "\n");
						f.write("\n");
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Set<Rule> sample(Set<Rule> rules, int sampleSize) {
		//TODO: Keep sampling rules until set size equals sampleSize
		List<Rule> tmpRules = new ArrayList<Rule>(rules);
		Set<Rule> sampledRules = new HashSet<Rule>();
		for (int i = sampleSize; sampleSize > 0; sampleSize--) {
			int sampleIdx = (int)(Math.random()*tmpRules.size());
			sampledRules.add(tmpRules.get(sampleIdx));
		}
		return sampledRules;
	}
}

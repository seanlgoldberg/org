package analysis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

public class RulePrinter {

	public static void printRules(String filename, ArrayList<Rule> rules, boolean append) {
		try {
			FileWriter f = new FileWriter(filename, append);
			System.out.println("Printing " + rules.size() + " rules to " + filename);
			for (Rule r : rules) {
				f.write(r.toString() + "\n");
			}
			f.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void printRules(String filename, HashSet<Rule> rules, boolean append) {
		try {
			FileWriter f = new FileWriter(filename, append);
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
			FileWriter f = new FileWriter(filename);
			for (String k : keys) {
				double count = map.get(k);
				f.write(k + "\t" + count + "\n");
			}
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

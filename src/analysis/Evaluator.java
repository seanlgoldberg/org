package analysis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Evaluator {

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
}

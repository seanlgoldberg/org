package analysis;

import java.util.*;

public class Operators {
	
	//used to create a copy for set minus operation
	public static Set<Rule> setMinus(Set<Rule> rules1, Set<Rule> rules2) {
		Set<Rule> minusRules = new HashSet<Rule>();
		Iterator<Rule> it = rules1.iterator();
		while (it.hasNext()) {
			minusRules.add(it.next());
		}
		minusRules.removeAll(rules2);
		return minusRules;
	}
	
	//used to create a copy for set intersection operation
	public static Set<Rule> setIntersection(Set<Rule> rules1, Set<Rule> rules2) {
		Set<Rule> intersectRules = new HashSet<Rule>();
		Iterator<Rule> it = rules1.iterator();
		while (it.hasNext()) {
			intersectRules.add(it.next());
		}
		intersectRules.retainAll(rules2);
		return intersectRules;
	}

}

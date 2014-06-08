package analysis;

import java.util.*;

public class Test {
	public static void main(String args[]) {
		Predicate p1 = new Predicate("s1","v1","o1");
		Predicate p2 = new Predicate("s","v","o");
		Predicate p3 = new Predicate("s","v","o");
		Predicate p4 = new Predicate("s1","v1","o1");
		Predicate p5 = new Predicate("s","v","o");
		Predicate p6 = new Predicate("s","v","o");
		
		Rule r1 = new Rule(p1,p2,p3);
		Rule r2 = new Rule(p4,p5,p6);
		
		System.out.println(p1.equals(p4));
		System.out.println(r1.equals(r2));
		System.out.println(r1.hashCode());
		System.out.println(r2.hashCode());
		
		Set<Rule> s1 = new HashSet<Rule>();
		s1.add(r1);
		Set<Rule> s2 = new HashSet<Rule>();
		s2.add(r2);
		
		System.out.println(s1);
		System.out.println(s2);
		Iterator<Rule> it1 = s1.iterator();
		Iterator<Rule> it2 = s2.iterator();
		System.out.println(it1.next().equals(it2.next()));
		System.out.println(s1.contains(r1));
		System.out.println(s1.contains(r2));
	}

}

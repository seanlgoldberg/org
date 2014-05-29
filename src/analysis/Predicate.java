package analysis;

import java.util.Comparator;

public class Predicate implements Comparable<Predicate>,Comparator<Predicate>{

	String arg1;
	String arg2;
	String relation;
	
	public Predicate(String r, String a1, String a2) {
		arg1 = a1;
		arg2 = a2;
		relation = r;
	}
	
	public String toString() {
		return (relation + "(" + arg1 + ", " + arg2 + ")");
	}
	
	@Override
	public boolean equals(Object obj) {
		Predicate p = (Predicate) obj;
		if (this.relation.equals(p.relation) &&
				this.arg1.equals(p.arg1) &&
				this.arg2.equals(p.arg2)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return relation.hashCode() + arg1.hashCode() + arg2.hashCode();
	}
	
	public int compareTo(Predicate p) {
		if (this.relation.equals(p.relation)) {
			if (this.arg1.equals(p.arg1)) {
				return this.arg2.compareTo(p.arg2);
			}
			else {
				return this.arg1.compareTo(p.arg1);
			}
		}
		else {
			return this.relation.compareTo(p.relation);
		}
	}
	
	public int compare(Predicate p1, Predicate p2) {
		return p1.compareTo(p2);
	}
}

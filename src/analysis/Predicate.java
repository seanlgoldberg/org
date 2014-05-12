package analysis;

public class Predicate {

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
	
	public boolean equals(Predicate p) {
		if (this.relation.equals(p.relation) &&
				this.arg1.equals(p.arg1) &&
				this.arg2.equals(p.arg2)) {
			return true;
		}
		return false;
	}
}

package analysis;

public class Rule {

	Predicate base;
	Predicate linker;
	Predicate consequent;
	
	public Rule(Predicate p1, Predicate p2, Predicate p3) {
		base = p1;
		linker = p2;
		consequent = p3;
	}
	
	public String toString() {
		return base.toString() + " ^ " + linker.toString() + " -> " + consequent.toString();
	}
	
	public boolean equals(Rule r) {
		/*
		if (this.base.equals(r.base) &&
				this.linker.equals(r.linker) &&
				this.consequent.equals(r.consequent)) {
			return true;
		}
		else if (this.base.equals(r.linker) &&
				this.linker.equals(r.base) &&
				this.consequent.equals(r.consequent)) {
			return true;
		}
		*/
		if (this.base.equals(r.base) && this.linker.equals(r.linker)) return true;
		if (this.base.equals(r.linker) && this.linker.equals(r.base)) return true;
		return false;
	}
}

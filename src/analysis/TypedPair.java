package analysis;

public class TypedPair implements Comparable<TypedPair> {
	String arg1;
	String arg2;
	public TypedPair(String arg1, String arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	public int compareTo(TypedPair pair) {
		return 1;
	}
}

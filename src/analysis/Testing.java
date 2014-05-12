package analysis;
import java.util.*;

public class Testing {
	public static void main(String[] args) {
		ArrayList<Predicate> testList = new ArrayList<Predicate>();
		Predicate p = new Predicate("use", "technology", "product");
		Predicate p2 = new Predicate("use", "technology", "product");
		testList.add(p);
		for (int i = 0; i < testList.size(); i++) {
			Predicate pTest = testList.get(i);
			System.out.println(pTest.equals(p2));
		}
		System.out.println(testList.contains(p2));
	}
}

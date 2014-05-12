package analysis;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class pipeline {
	
	private static Connection connection = null;
	/*
	private static String[] phrase = {"bear", "arrive", "cause", "return", "play", "win", "buy", "develop", "support",
										"make", "base", "come", "acquire", "use"};
	*/
	private static int i;
	
	/*
	 * Retrieves all rules from Sherlock database of length 2
	 */
	private ResultSet getLength2rules() {
		ResultSet length2rules = null;
		try {
			Statement stmt = connection.createStatement();
			//Select rules that don't contain a two LHS predicates, ie. only 1 LHS predicate
			String query = "SELECT * FROM RULES " + 
						"WHERE RULE NOT LIKE '%),%'";
			length2rules = stmt.executeQuery(query);
			return length2rules;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return length2rules;
	}
	
	/*
	 * Takes in a ResultSet and does something to it
	 */
	public void processRules(ResultSet rset) {
		ArrayList<String> rules = new ArrayList<String>();
		try {
			while (rset.next()) {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Produces rules with form R1(X,Y) ^ R2(Y,Z) -> R1(X,Z)
	 */
	public static ArrayList<Rule> type1join() {
			
			ArrayList<Rule> rules = new ArrayList<Rule>();
			
			String query = "SELECT s1.relation AS relation1, s1.argClass1 AS argClass1_1, s1.argClass2 AS argClass2_1, " +
					"s2.relation AS relation3, s2.argClass1 AS argClass1_3, s2.argClass2 AS argClass2_3, " +
					"t1.relation AS relation2, t1.argClass1 AS argClass1_2, t1.argClass2 AS argClass2_2 " +
					"FROM sherlocktypedrelations s1, " +
					"sherlocktypedrelations s2, linkers t1 " +
					"WHERE s1.argClass1=s2.argClass1 AND s1.argClass2=t1.argClass1 " +
					"AND s2.argClass2=t1.argClass2 AND s1.relation=s2.relation"; //AND s1.relation LIKE '%" + phrase[i] + "%'";
			
			/*
			String query = "SELECT s1.relation AS relation1, s1.argClass1 AS argClass1_1, s1.argClass2 AS argClass2_1, " +
							"s2.relation AS relation3, s2.argClass1 AS argClass1_3, s2.argClass2 AS argClass2_3, " +
							"t1.relation AS relation2, t1.argClass1 AS argClass1_2, t1.argClass2 AS argClass2_2 " +
							"FROM sherlocktypedrelations s1, " +
							"sherlocktypedrelations s2, sherlocklinkingtypedrelations t1 " +
							"WHERE s1.argClass1=s2.argClass1 AND (s1.argClass2=t1.argClass1 OR s1.argClass1=t1.argClass1) " +
							"AND s2.argClass2=t1.argClass2 AND s1.relation=s2.relation AND s1.relation LIKE '%" + phrase[i] + "%'";
			*/
			/*
			String query = "SELECT s1.relation AS relation1, s1.argClass1 AS argClass1_1, s1.argClass2 AS argClass2_1, " +
					"s2.relation AS relation3, s2.argClass1 AS argClass1_3, s2.argClass2 AS argClass2_3, " +
					"t1.relation AS relation2, t1.argClass1 AS argClass1_2, t1.argClass2 AS argClass2_2 " +
					"FROM sherlocktypedrelations s1, " +
					"sherlocktypedrelations s2, sherlocklinkingtypedrelations t1 " +
					"WHERE s1.argClass1=s2.argClass1 AND (s1.argClass2=t1.argClass1 OR s1.argClass1=t1.argClass2) " +
					"AND s2.argClass2=t1.argClass2 AND s1.relation=s2.relation";
			*/
			try {
				Statement stmt = connection.createStatement();
				System.out.println("Trying Query...");
				ResultSet rset = stmt.executeQuery(query);
				System.out.println("Successful!");
				
				while(rset.next()) {
					
					Predicate p1 = new Predicate(rset.getString("relation1").replace(", ", ""), 
							rset.getString("argClass1_1"), rset.getString("argClass2_1"));
					Predicate p2 = new Predicate(rset.getString("relation2").replace(", ", ""), 
							rset.getString("argClass1_2"), rset.getString("argClass2_2"));
					Predicate p3 = new Predicate(rset.getString("relation3").replace(", ", ""), 
							rset.getString("argClass1_3"), rset.getString("argClass2_3"));
					
					Rule newRule = new Rule(p1, p2, p3);
					rules.add(newRule);
					
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rules;
		
	}
	/*
	public static ArrayList<Rule> getSherlockRules() {
		ArrayList<Rule> rules = new ArrayList<Rule>();
		
		String query = "SELECT rule FROM rules WHERE rule LIKE '%" + phrase[i] + "%'";
		//String query = "SELECT rule FROM rules";
		try {
			Statement stmt = connection.createStatement();
			System.out.println("Trying Query...");
			ResultSet rset = stmt.executeQuery(query);
			System.out.println("Successful!");
			while(rset.next()) {
				
				String r = rset.getString("rule");
				// parse r into a Rule
				String delims = "[(,):-]+";
				String[] terms = r.split(delims);
				if (terms.length > 6) {
					
					Predicate p3 = new Predicate(terms[0].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[1].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[2].replace("_A","").replace("_B","").replace("_C","").replace("_"," ")); 
					Predicate p1 = new Predicate(terms[3].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[4].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[5].replace("_A","").replace("_B","").replace("_C","").replace("_"," ")); 
					Predicate p2 = new Predicate(terms[6].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[7].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[8].replace("_A","").replace("_B","").replace("_C","").replace("_"," "));

					if (p2.relation.equals(p3.relation) || p1.relation.equals(p3.relation))
						if ((p1.arg2.equals(p2.arg1))
								&& (p1.relation.contains(phrase[i]) || p2.relation.contains(phrase[i]))) {
									rules.add(new Rule(p1, p2, p3));
						}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rules;
	}
	*/
	/*
	public static ArrayList<Predicate> addLinkersByEntailment() {
		//Query all length 2 where antecedent is a linker
		ArrayList<Predicate> linkingPreds = new ArrayList<Predicate>();
		ArrayList<Predicate> newLinkingPreds = new ArrayList<Predicate>();
		String query = "SELECT * FROM rules2";
		String getLinkersQuery = "SELECT * FROM sherlocklinkingtypedrelations";
		try {
			Statement stmt = connection.createStatement();
			Statement stmt2 = connection.createStatement();
			System.out.println("Trying Query...");
			ResultSet rset = stmt2.executeQuery(query);
			ResultSet rsetLinker = stmt.executeQuery(getLinkersQuery);
			System.out.println("Successful!");
			
			while (rsetLinker.next()) {
				Predicate p = new Predicate(rsetLinker.getString("relation").replace(", ", ""), 
						rsetLinker.getString("argClass1"), rsetLinker.getString("argClass2"));
				linkingPreds.add(p);
			}
			
			while(rset.next()) {
				
				String r = rset.getString("rule");
				// parse r into a Rule
				String delims = "[(,):-]+";
				String[] terms = r.split(delims);
				if (terms.length == 6) {
					Predicate p1 = new Predicate(terms[0].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[1].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[2].replace("_A","").replace("_B","").replace("_C","").replace("_"," ")); 
					Predicate p2 = new Predicate(terms[3].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[4].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[5].replace("_A","").replace("_B","").replace("_C","").replace("_"," "));
					for (int i = 0; i < linkingPreds.size(); i++) {
						Predicate pTest = linkingPreds.get(i);
						if (pTest.equals(p1) && !newLinkingPreds.contains(p2)) {
							System.out.println("Adding " + p2);
							newLinkingPreds.add(p2);
						}
						if (pTest.equals(p2) && !newLinkingPreds.contains(p1)) {
							System.out.println("Adding " + p1);
							newLinkingPreds.add(p1);
						}
					}
				}
			}
			
			//add a second inference 
			
			System.out.println("Size:" + newLinkingPreds.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("");
		
		
		try {
			Statement stmt = connection.createStatement();
			Statement stmt2 = connection.createStatement();
			System.out.println("Trying Query...");
			ResultSet rset = stmt2.executeQuery(query);
			ResultSet rsetLinker = stmt.executeQuery(getLinkersQuery);
			System.out.println("Successful!");
			
			while (rsetLinker.next()) {
				Predicate p = new Predicate(rsetLinker.getString("relation").replace(", ", ""), 
						rsetLinker.getString("argClass1"), rsetLinker.getString("argClass2"));
				linkingPreds.add(p);
			}
			
			while(rset.next()) {
				
				String r = rset.getString("rule");
				// parse r into a Rule
				String delims = "[(,):-]+";
				String[] terms = r.split(delims);
				if (terms.length == 6) {
					Predicate p1 = new Predicate(terms[0].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[1].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[2].replace("_A","").replace("_B","").replace("_C","").replace("_"," ")); 
					Predicate p2 = new Predicate(terms[3].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[4].replace("_A","").replace("_B","").replace("_C","").replace("_"," "), 
							terms[5].replace("_A","").replace("_B","").replace("_C","").replace("_"," "));
					for (int i = 0; i < newLinkingPreds.size(); i++) {
						Predicate pTest = newLinkingPreds.get(i);
						if (pTest.equals(p1) && !newLinkingPreds.contains(p2)) {
							System.out.println("Adding " + p2);
							newLinkingPreds.add(p2);
						}
						if (pTest.equals(p2) && !newLinkingPreds.contains(p1)) {
							System.out.println("Adding " + p1);
							newLinkingPreds.add(p1);
						}
					}
				}
			}
			
			System.out.println("Size:" + newLinkingPreds.size());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Create new table with original linkers and new entailed linkers
		
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("Select * from linkers");
		
			String createLinkerTable = "CREATE TABLE linkers(relation VARCHAR2(500)," +
						   		 "argClass1 VARCHAR2(500)," +
						   		 "argClass2 VARCHAR2(500))";
		
				stmt = connection.createStatement();
				stmt.execute("DROP TABLE linkers");
				stmt.execute(createLinkerTable);
				for (int i=0; i < newLinkingPreds.size(); i++) {
					stmt.execute("INSERT INTO linkers VALUES ('" + newLinkingPreds.get(i).relation + "', '"
									+ newLinkingPreds.get(i).arg1 + "', '" + newLinkingPreds.get(i).arg2 + "')");
				}
				for (int i=0; i < linkingPreds.size(); i++) {
					stmt.execute("INSERT INTO linkers VALUES ('" + linkingPreds.get(i).relation + "', '"
									+ linkingPreds.get(i).arg1 + "', '" + linkingPreds.get(i).arg2 + "')");
				}
			} catch (SQLException f) {
				f.printStackTrace();
			}
		
		
		
		//Modify type1join to pull linkers from new table
		
		return newLinkingPreds;
	}
	*/
	
	
	public static TreeMap<String,Double> getLinkingPredsAuto() {
		TreeMap<String, Double> ubiquity = new TreeMap<String,Double>();
		TreeMap<TypedPair, ArrayList<String>> relationList = new TreeMap<TypedPair, ArrayList<String>>();
		//produce list of relations
		String query = "SELECT * FROM sherlocktypedrelations";
		try {
			Statement stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			
			rset.next();
			ArrayList<String> relations = new ArrayList<String>();
			String relation = rset.getString("relation");
			String arg1 = rset.getString("argClass1");
			String arg2 = rset.getString("argClass2");
			TypedPair pair = new TypedPair(arg1, arg2);
			relations.add(relation);
			relationList.put(pair, relations);
			while (rset.next()) {
				relations = new ArrayList<String>();
				relation = rset.getString("relation");
				arg1 = rset.getString("argClass1");
				arg2 = rset.getString("argClass2");
				pair = new TypedPair(arg1, arg2);
				if (relationList.containsKey(pair)) {
					relations = relationList.get(pair);
					relations.add(relation);
					relationList.put(pair, relations);
				} else {
					relations.add(relation);
					relationList.put(pair, relations);
				}
			}
			
			Collection<ArrayList<String>> values = relationList.values();
			Iterator<ArrayList<String>> it = values.iterator();
			double count = -1;
			
			while (it.hasNext()) {
				ArrayList<String> it2 = (ArrayList<String>)it.next();
				Iterator<String> it3 = it2.iterator();
				while (it3.hasNext()) {
					String newRelation = it3.next();
					if (ubiquity.containsKey(newRelation)) {
						count = ubiquity.get(newRelation);
						count++;
						ubiquity.put(newRelation,count);
					} else {
						count = 1;
						ubiquity.put(newRelation,count);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//produce list of typed pairs
		//produce Map<TypedPair, ArrayList<String>> mapping
		//   typed pairs to list of relations
		//produce final TreeMap by crawling through Map and
		//  incrementing relation count for each typed pair
		//  instance found
		return ubiquity;
	}
	
	public static void main(String[] args) throws SQLException {
        //for (i = 0; i < phrase.length; i++) {
        	connection = Connector.setConnection();
	        DataSet dataInfo = Loader.getData();
	        //Loader.loadData(dataInfo, connection);
        
	        //ArrayList<Predicate> extraPreds = addLinkersByEntailment();
	        
	        ArrayList<Rule> rules = type1join();
	        //ArrayList<Rule> sherlockRules = getSherlockRules();
	        
	        //TreeMap<String,Double> ubiquity = getLinkingPredsAuto();
	        //printRules("ubiquity.csv", ubiquity);
	        RulePrinter.printRules("rulesTotal.csv", rules, true);
	        //printRules("sherlockRules.csv", sherlockRules);
	        
	        //Evaluator.evaluate(rules, sherlockRules, true);
	        //evalTest(rules, sherlockRules);
	        //build list of linking predicates
	        //extract all predicates sharing one linking argument and build up rules
	        
	        /*processRules(getLength2rules());*/
        //}
        
	}

}

class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}

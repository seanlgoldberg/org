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
		Set<String> rules = new HashSet<String>();
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
	public static Set<Rule> getORGrules(Set<Predicate> linkingPreds) {
			
			Set<Rule> rules = new HashSet<Rule>();
			
			String query = "SELECT s1.relation AS relation1, s1.argClass1 AS subject1, s1.argClass2 AS object1, " +
						    "s2.relation AS relation2, s2.argClass1 AS subject2, s2.argClass2 AS object2 " +
							"FROM sherlocktypedrelations s1, sherlocktypedrelations s2 " +
							"WHERE s1.relation=s2.relation AND s1.argClass1=s2.argClass1";
			/*
			String query = "SELECT s1.relation AS relation1, s1.argClass1 AS argClass1_1, s1.argClass2 AS argClass2_1, " +
					"s2.relation AS relation3, s2.argClass1 AS argClass1_3, s2.argClass2 AS argClass2_3, " +
					"t1.relation AS relation2, t1.argClass1 AS argClass1_2, t1.argClass2 AS argClass2_2 " +
					"FROM sherlocktypedrelations s1, " +
					"sherlocktypedrelations s2, linkers t1 " +
					"WHERE s1.argClass1=s2.argClass1 AND s1.argClass2=t1.argClass1 " +
					"AND s2.argClass2=t1.argClass2 AND s1.relation=s2.relation"; //AND s1.relation LIKE '%" + phrase[i] + "%'";
			*/
			
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
				
				RulePrinter.initializeFile("ORG_rules.txt");
				
				while(rset.next()) {
					for (Predicate p2 : linkingPreds) {
						//rules.clear();
						Predicate p1 = new Predicate(rset.getString("relation1").replace(", ", ""), 
								rset.getString("subject1"), rset.getString("object1"));
						//Predicate p2 = new Predicate(rset.getString("relation2").replace(", ", ""), 
							//	rset.getString("argClass1_2"), rset.getString("argClass2_2"));
						Predicate p3 = new Predicate(rset.getString("relation2").replace(", ", ""), 
								rset.getString("subject2"), rset.getString("object2"));
						
						if ((p1.arg2.equals(p2.arg1)) && (p2.arg2.equals(p3.arg2))) {
							Rule newRule = new Rule(p1, p2, p3);
							rules.add(newRule);
							//RulePrinter.printRules("ORG_rules.txt", rules, true);
						}
					}
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rules;
		
	}
	
	public static Set<Predicate> getLinkingPreds() {
		Set<Predicate> linkingPreds = new TreeSet<Predicate>();
		
		String query = "SELECT * from sherlocklinkingtypedrelations";
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				String relation = rset.getString("relation");
				String arg1 = rset.getString("argclass1");
				String arg2 = rset.getString("argclass2");
				Predicate pred = new Predicate(relation,arg1,arg2);
				linkingPreds.add(pred);
			}
		} catch(SQLException ex) {
			
		}
		return linkingPreds;
	}
	
	public static Set<Predicate> getSherlockPreds() {
		Set<Predicate> sherlockPreds = new HashSet<Predicate>();
		
		String query = "SELECT relation, argclass1, argclass2 from sherlocktypedrelations";
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			while (rset.next()) {
				String relation = rset.getString("relation");
				String arg1 = rset.getString("argclass1");
				String arg2 = rset.getString("argclass2");
				if (!relation.equals("tag with") && !relation.equals("join") && !relation.equals("to see")) {
					sherlockPreds.add(new Predicate(relation,arg1,arg2));
				}
			}
		} catch(SQLException ex) {
			
		}
		return sherlockPreds;
	}
	
	public static Set<Rule> getSherlockRules() {
		Set<Rule> rules = new HashSet<Rule>();
		
		String query = "SELECT rule FROM rules WHERE rule LIKE '%),%'";
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

					//Make sure rules have same base relation in antecedent and consequent
					//  and share arguments
					if (p1.relation.equals(p3.relation)) {
						if (p1.arg2.equals(p2.arg1)) {
							rules.add(new Rule(p1, p2, p3));
						}
					}
					else if (p2.relation.equals(p3.relation)) {
						if (p2.arg2.equals(p1.arg1)) {
							rules.add(new Rule(p2, p1, p3));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rules;
	}
	
	
	public static void partitionRules(ArrayList<Predicate> linkingPreds, ArrayList<Rule> rules) {
		ArrayList<Rule> linkingRules = new ArrayList<Rule>();
		ArrayList<Rule> nonLinkingRules = new ArrayList<Rule>();
		boolean added = false;
		
		for (Rule rule : rules) {
			for (Predicate pred : linkingPreds) {	
				if (pred.equals(rule.linker)) {
					linkingRules.add(rule);
					added = true;
					break;
				}
			}
			if (!added) {
				nonLinkingRules.add(rule);
			}
			added = false;
		}
		
		RulePrinter.printRules("linking_sherlock_rules.txt", linkingRules, false);
		RulePrinter.printRules("non_linking_sherlock_rules.txt", nonLinkingRules, false);
	}
	
	public static void main(String[] args) throws SQLException {
        
    	connection = Connector.setConnection();
        DataSet dataInfo = Loader.getData();
        //Loader.loadData(dataInfo, connection);
    
        //add a flag so this doesn't need to be commented
        //ArrayList<Predicate> extraPreds = addLinkersByEntailment();
        
        
        //get manual list of linking predicates
        Set<Predicate> linkingPreds = getLinkingPreds();
        
        //retrieve and print all ORG rules
        Set<Rule> ORGrules = getORGrules(linkingPreds);
 
        //retrieve all Sherlock Rules
        Set<Rule> sherlockRules = getSherlockRules();
        
        
        Set<Rule> ORGrulesMinus = Operators.setMinus(ORGrules, sherlockRules);
        Set<Rule> sherlockRulesMinus = Operators.setMinus(sherlockRules, ORGrules);
        Set<Rule> ORGsherlockIntersect = Operators.setIntersection(ORGrules, sherlockRules);
        
        //sample rules
        Set<Rule> ORGsamples = Evaluator.sample(ORGrulesMinus,100);
        Set<Rule> sherlockSamples = Evaluator.sample(sherlockRulesMinus, 100);
        
        /*
        ArrayList<Predicate> sherlockPreds = getSherlockPreds();
        new PredicateClustering(sherlockPreds, "auto_linking.txt");
        //partitionRules(linkingPreds, sherlockRules);
        */

        //printing predicates and rules
        RulePrinter.printPredicates("sherlock_linking_predicates_manual.txt", linkingPreds, false);
        //RulePrinter.printPredicates("sherlock_predicates.txt", sherlockPreds, false);
        
        RulePrinter.printRules("ORG_rules.txt", ORGrules, false);
        RulePrinter.printRules("sherlock_rules.txt", sherlockRules, false);
        RulePrinter.printRules("ORG_rules_minus.txt", ORGrulesMinus, false);
        RulePrinter.printRules("sherlock_rules_minus.txt", sherlockRulesMinus, false);
        RulePrinter.printRules("ORG_sherlock_intersection.txt", ORGsherlockIntersect, false);
        RulePrinter.printRules("ORG_samples.txt", ORGsamples, false);
        RulePrinter.printRules("sherlock_samples.txt", sherlockSamples, false);
        
        //evaluate rules
        Evaluator.evaluate("auto_linking.txt", "sherlock_linking_predicates_manual.txt");
        //Evaluator.evaluate(rules, sherlockRules, true);
       
        
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

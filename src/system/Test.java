package system;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.query.*;

public class Test {
	public static void main(String[] args) {
		
		String db = "http://dbpedia.org/resource";
		//String dataLoc = "data/mappingbased_properties_cleaned_en.ttl";
		String dataLoc = "/data/d01/freebase2/data/SPO.dat";
		
		//Generate new model based on DBpedia properties
		Model model = ModelFactory.createDefaultModel();
		model.read(dataLoc);
		
		String queryString = "SELECT ?p " + 
							"WHERE { <" + db + "/Autism> ?p ?o }";
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.create(query,model);
		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				RDFNode p = soln.get("p");
				System.out.println(p);
			}
		} finally {
			qexec.close();
		}
	}
}

package system;

import java.io.*;
import java.util.*;
import com.hp.hpl.jena.rdf.model.*;

/*
 * Sherlock allExtractions has 10 columns
 * ID, subj, pred, obj, norm subj, norm pred, norm obj, freq, score, wiki page
 */

public class Sherlock2RDFConverter {
	
	private static Model model;
	
	public static void main(String[] args) {
		model = ModelFactory.createDefaultModel();
		readData();
	}
	
	// make sure all data fits in memory
	public static void readData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("data/allExtractions"));
			while(true) {
				String line = br.readLine();
				if (line == null) break;
				String[] lines = line.trim().split("\t");
				Resource r1 = model.createResource(lines[4]);
				Property p = model.createProperty(lines[5]);
				Resource r2 = model.createResource(lines[6]);
				r1.addProperty(p, r2);
			}
			br.close();
		} catch(IOException ex) {
			
		}
	}
}

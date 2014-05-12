/*
 * Abstract RuleSet class creates a template for multiple data sources
 */

package analysis;

public class DataSet {

	String filename;
	String description;
	String sqlLoaderFile;
	String tableName;
	String createStmt;
	
	public DataSet(String fn, String desc, String loaderFile, String tn, String create) {
		filename = fn;
		description = desc;
		sqlLoaderFile = loaderFile;
		tableName = tn;
		createStmt = create;
		
	}
	
	
}


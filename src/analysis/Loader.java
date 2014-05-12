package analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class Loader {

	private static void loadData(DataSet dInfo, Connection connection) {
		try {
			Statement stmt = connection.createStatement();
			try {
				System.out.println("Trying.");
				stmt.execute(dInfo.createStmt);
				System.out.println("Successful!");
			} catch (SQLException e) {
				stmt.execute("DROP TABLE " + dInfo.tableName);
				stmt.execute(dInfo.createStmt);
			}
			String proc = "sqlldr sean/goldberg1126@localhost control=" + dInfo.sqlLoaderFile;
			try {
				String line;
				Process p = Runtime.getRuntime().exec(proc);
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getErrorStream()));
				  while ((line = input.readLine()) != null) {
				    System.out.println(line);
				  }
				  input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DataSet getData() {
		return new DataSet("linkingTypedRelations.txt", 
				   "Sherlock Linking Typed Relations",
				   "linkingTypedRelationsLoader.ctl",
				   "SherlockLinkingTypedRelations",
				   "CREATE TABLE SherlockLinkingTypedRelations(relation VARCHAR2(500)," +
						   		 "argClass1 VARCHAR2(500)," +
						   		 "argClass2 VARCHAR2(500)," + 
						   		 "weightedCount FLOAT," +
						   		 "PMIscore FLOAT)");
		
		/*
		return new DataSet("allTypedRelations.txt", 
				   "Sherlock Typed Relations",
				   "allTypedRelationsLoader.ctl",
				   "SherlockTypedRelations",
				   "CREATE TABLE SherlockTypedRelations(relation VARCHAR2(500)," +
						   		 "argClass1 VARCHAR2(500)," +
						   		 "argClass2 VARCHAR2(500)," + 
						   		 "weightedCount FLOAT," +
						   		 "PMIscore FLOAT)");
		*/
		/*
		stmt.execute("drop table evidence;");
		stmt.execute("create table evidence (id INT," +
											"arg1 VARCHAR2(500)," +
											"relation VARCHAR2(500)," +
											"arg2 VARCHAR2(500)," +
											"normarg1 VARCHAR2(500)," +
											"normrelation VARCHAR2(500)," +
											"normarg2 VARCHAR2(500)," +
											"junk VARCHAR2(500)," +
											"conf NUMBER," +
											"source CLOB)");
		String proc = "sqlldr sean/goldberg1126@localhost control=" + controlFile;
		*/
	}
}

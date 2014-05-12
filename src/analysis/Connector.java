package analysis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	public static Connection setConnection() throws SQLException{
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    
        System.out.println("Oracle JDBC Successfully Driver Registered!");
        //Connection connection = null;   
        String portNumber = "1521";
        /*
        String serverName = "oracle.cise.ufl.edu";
        String sid = "orcl";
        String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
        */
        String serverName = "localhost";
        String sid = "XE";
        String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + "/" + sid;
        String username = "sean";
        String password = "goldberg1126";
        
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);  
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console.");
            e.printStackTrace();
            //return connection;
        }
        return connection;
	}
	
}

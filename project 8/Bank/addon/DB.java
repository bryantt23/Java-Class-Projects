/**
 *
 * @author kenneth Odoh
 */

/**
	This is covered under GPL licence version 2 or higher
*/

import java.sql.Connection; //for connection object
import java.sql.DriverManager; //for driver connection
import java.sql.Statement;  //for Statements

public class DB {
	private static Statement stmt;
	private static Connection conn;
	
	public static void connect() {
        // Settings
		final String DRIVER = "com.mysql.jdbc.Driver";
		final String DATABASE_URL = "jdbc:mysql://localhost/BankDB";
		final String USERNAME = "jhtp7";
		final String PASSWORD = "jhtp7";

        
        try {
	        Class.forName(DRIVER);
	        conn = DriverManager.getConnection(DATABASE_URL,USERNAME,PASSWORD);
	        setStmt(conn.createStatement());
        } catch (Exception ex) {
        	// Error
        	System.out.println("Connection error: " + ex);
        }
	}
	
	// Close connection
	public static void disconnect(){
    	try {
    		getStmt().close();
        	conn.close();
		} catch (Exception e) {
		} 
    }

	public static void setStmt(Statement stmt) {
		DB.stmt = stmt;
	}

	public static Statement getStmt() {
		return stmt;
	}
}

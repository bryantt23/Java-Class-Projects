/**
 *
 * @author kenneth Odoh
 */

/**
	This is covered under GPL licence version 2 or higher
*/

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Query {
	// Retrieve multiple lines.
	public static String[][] query(String query) {
		String[][] result = null;
		
		try {
			DB.connect();
			
	        ResultSet rs = DB.getStmt().executeQuery(query);
	        ResultSetMetaData rsMetaData = rs.getMetaData();
        	rs.last();
        	int columnCount = rsMetaData.getColumnCount();
        	result = new String[rs.getRow()][columnCount];
        	rs.beforeFirst();
	        while (rs.next()) {
	        	for (int i = 0; i < columnCount; i++) {
	        		result[rs.getRow()-1][i] = rs.getString(i+1);
				}
            }
	        rs.close();
	        DB.disconnect();
		} catch(Exception e) {
			System.out.println("Query error: " + e);
		}
		return result;
	}
	
	// Insert stuff to db.
	public static void Insert(String query) throws MyException {
		try {
			DB.connect();
			
	        DB.getStmt().executeUpdate(query);
	        
	        DB.disconnect();
		} catch(Exception e) {
			System.out.println("SQL Insert error: " + e);
                        throw new MyException("SQL Insert error: " + e);
		}
	}

	// update stuff to db.
	public static void UpDate(String query) throws MyException {
		try {
			DB.connect();
			
	        DB.getStmt().executeUpdate(query);
	        
	        DB.disconnect();
		} catch(Exception e) {
			System.out.println("SQL Update error: " + e);
                        throw new MyException("SQL Update error: " + e);
		}
	}
	


	public static String [][] getSavingAccBalance(String userID)
	{
		int realuserID = 0;
		String queryStr = null;
		try
		{
			if(!Validator.canHappenSQLInject(userID))
			{
				realuserID = Integer.parseInt(userID);
			}
		}catch(NumberFormatException v)
		{}
		catch(NullPointerException w)
		{}
		if(realuserID > 0)
			queryStr="SELECT SUM(amount) FROM SAVINGACCOUNT WHERE custID='"+realuserID+"'";
		String [][] result = Query.query(queryStr);

		return result;
	}

	public static String [][] getCurrentAccBalance(String userID)
	{
		int realuserID = 0;
		String queryStr = null;
		try
		{
			if(!Validator.canHappenSQLInject(userID))
			{
				realuserID = Integer.parseInt(userID);
			}
		}catch(NumberFormatException v)
		{}
		catch(NullPointerException w)
		{}
		if(realuserID > 0)
			queryStr="SELECT SUM(amount) FROM CURRENTACCOUNT  WHERE custID='"+realuserID+"'";
		String [][] result =  Query.query(queryStr);
		return result;
	}
	//it can be used to create account as well as make deposit to account
	public static void makeDepositSavingsAcc(String custID, double amount)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		String accNo = custID + "SAV";
		String [][] result = null;
		try
		{
			if(realuserID > 0)
			{
				Query.Insert("INSERT INTO SAVINGACCOUNT (accNo,custID,amount) VALUES ('"+accNo+"','"+realuserID+"','"+amount+"')");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}

	}

	public static void makeDepositCurrentAcc(String custID, double amount)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		String accNo = custID + "CUR";
		try
		{
			if(realuserID > 0)
			{
				Query.Insert("INSERT INTO CURRENTACCOUNT (accNo,custID,amount) VALUES ('"+accNo+"','"+realuserID+"','"+amount+"')");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}

	//make overdraft loan from current account
	public static void makeOverDraftLoan(String custID, double amount)
	{
		String accNo = custID + "CUR";
		try
		{
			Query.Insert("INSERT INTO OVERDRAFTLOAN (curAccNo ,amount) VALUES('"+accNo+"','"+amount+"')");
		}catch(MyException e)
		{}
	} 

	//how to get overdraft balance
	public static String[][] getOverDraftBalance(String userID)
	{
		String accNo = userID + "CUR";
		String queryStr="SELECT SUM(amount) FROM OVERDRAFTLOAN where curAccNo='"+accNo +"'";
		String [][] result = Query.query(queryStr);
		return result;
	}


	//make a new customer
	public static void makeNewCustomer(String SSN ,String firstname,String lastname,String address,String phone,String email,String username,String userpassword,String	status, String type)
	{
		try
		{
			Query.Insert("INSERT INTO CUSTOMER (SSN ,firstname,lastname,address,phone,email,username,userpassword,status,create_at ,type) VALUES " +"('"+Query.md5(SSN)+"','"+firstname+"','"+lastname+"','"+address+"','"+phone+"','"+email+"','"+username+"','"+Query.md5(userpassword)+"','"+status+"','"+Query.GetTimeStamp()+"','"+type+"')");
		}catch(MyException e)
		{}

	}

	//update User information
	public static void upDateUserFirstName(String custID, String firstname)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set firstname = '"+firstname+"' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}

	public static void upDateUserLastName(String custID, String lastname)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set lastname = '"+lastname+"' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}

	public static void upDateUseraddress(String custID, String address)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set address = '"+address+"' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}

	public static void upDateUserPhone(String custID, String phone)
	{
		int realuserID = 0 ;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set phone = '"+phone+"' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
		
	}

	public static void upDateUserEmail(String custID, String email)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set email = '"+email+"' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}

	public static void upDateUsername(String custID, String username)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set username = '"+username+"' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}

	}

	public static void upDateUserPassword(String custID, String password)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set userpassword = '"+Query.md5(password)+"' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}

	public static void upDateUserStatus(String custID, String status)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set status = '"+status+"' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}

	public static void upDateUserSSN(String custID, String ssn)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set SSN = '"+ssn+"' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}
	//How to delete a customer
	public static void DeleteCustomer(String custID)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("DELETE FROM CUSTOMER where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}

	//How to disable a customer
	public static void DisableCustomer(String custID)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set status = '1' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}

	//How to enable a customer
	public static void EnableCustomer(String custID)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set status = '0' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}

	//How to upgrade from a normal user to administrator
	public static void upGradeCustomer(String custID)
	{
		int realuserID = 0;
		try
		{
			if(!Validator.canHappenSQLInject(custID))
			{
				realuserID = Integer.parseInt(custID);
			}
		}catch(NumberFormatException v)
		{}
		try
		{
			if(realuserID > 0)
			{
				Query.UpDate("UPDATE CUSTOMER set type = '1' where ID = '"+realuserID+"'");
			}
		}catch(MyException e)
		{}
		catch(NullPointerException w)
		{}
	}



	//get user full name
	public static String [][]getCustomerFulName(String userID)
	{
		String result[][] = null;
		try
		{
			int realuserID = Integer.parseInt(userID);
			result = Query.query("SELECT firstname, lastname FROM CUSTOMER WHERE ID ='"+realuserID+"'");
		}catch(NumberFormatException v)
		{}
		return result;
	}

	//get user address
	public static String getCustomerAddress(String userID)
	{
		String result[][] = null;
		try
		{
			int realuserID = Integer.parseInt(userID);
			result = Query.query("SELECT address FROM CUSTOMER WHERE ID ='"+realuserID+"'");
		}catch(NumberFormatException v)
		{}
		return result[0][0];
	}

	//get user phone
	public static String getCustomerPhone(String userID)
	{
		String result[][] = null;
		try
		{
			int realuserID = Integer.parseInt(userID);
			result = Query.query("SELECT phone FROM CUSTOMER WHERE ID ='"+realuserID+"'");
		}catch(NumberFormatException v)
		{}
		return result[0][0];
	}


	//get user email
	public static String getCustomerEmail(String userID)
	{
		String result[][] = null;
		try
		{
			int realuserID = Integer.parseInt(userID);
			result = Query.query("SELECT email FROM CUSTOMER WHERE ID ='"+realuserID+"'");
		}catch(NumberFormatException v)
		{}
		return result[0][0];
	}

	//get user username
	public static String getCustomerUserName(String userID)
	{
		String result[][] = null;
		try
		{
			int realuserID = Integer.parseInt(userID);
			result = Query.query("SELECT username FROM CUSTOMER WHERE ID ='"+realuserID+"'");
		}catch(NumberFormatException v)
		{}
		return result[0][0];
	}

	//get user id from ssn
	public static String getCustomerIDFromSSN(String ssn)
	{
		String result[][] = null;
		try {
			if(!Validator.canHappenSQLInject(ssn))
			{
				result = Query.query("SELECT ID FROM CUSTOMER WHERE SSN ='"+Query.md5(ssn)+"'");
			}
		}catch(Exception e) {
			System.out.println("User verification error: " + e);
		}
		return result[0][0];
	}
	
	// Verify username and password validity
	public static String[][] getUserLoginDetails(String username, String password) 
	{
		String result[][] = null;

		try {
			if((!Validator.canHappenSQLInject(username)) || (!Validator.canHappenSQLInject(password)))
			{
				result = Query.query("SELECT ID,status,type FROM CUSTOMER WHERE username='"+username+"' " +
						"AND userpassword='"+Query.md5(password)+"'");
			}
		} catch (Exception e) 
		{
			System.out.println("User verification error: " + e);
		}
		return result;
	}

	public static String getTotalCustomerCurAccBalance()
	{
		String result[][] = Query.query("SELECT SUM(amount) FROM CURRENTACCOUNT");
		return result[0][0];
	}

	public static String getTotalCustomerSavAccBalance()
	{
		String result[][] = Query.query("SELECT SUM(amount) FROM SAVINGACCOUNT");
		return result[0][0];
	}

	public static String getTotalCustomerOverDraftBalance()
	{
		String result[][] = Query.query("SELECT SUM(amount) FROM OVERDRAFTLOAN");
		return result[0][0];
	}
	 	
	
	public static String md5(String s){
		MessageDigest m = null;
		try 
		{
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
		}catch(Exception e)
		{}
		return new BigInteger(1,m.digest()).toString(16);
	}

	
	public static String GetTimeStamp() {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
	}
}

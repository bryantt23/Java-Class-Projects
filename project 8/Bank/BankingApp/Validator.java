/**
 *
 * @author kenneth Odoh
 */

/**
	This is covered under GPL licence version 2 or higher
*/

import javax.swing.JOptionPane;
import java.util.regex.*;

/**
Referenced source:
http://www.zparacha.com/validate-email-ssn-phone-number-using-java-regular-expression/
*/ 



public class Validator
{
   /** isEmailValid: Validate email address using Java reg ex. 
    * This method checks if the input string is a valid email address. 
    * @param email String. Email address to validate 
    * @return boolean: true if email address is valid, false otherwise. 
    */  


	public static boolean isEmailValid(String email)
	{  
		boolean isValid = false;  
		try
		{
			  
			/* 
			Email format: A valid email address will have following format: 
				    [\\w\\.-]+: Begins with word characters, (may include periods and hypens). 
				@: It must have a '@' symbol after initial characters. 
				([\\w\\-]+\\.)+: '@' must follow by more alphanumeric characters (may include hypens.). 
			This part must also have a "." to separate domain and subdomain names. 
				[A-Z]{2,4}$ : Must end with two to four alaphabets. 
			(This will allow domain names with 2, 3 and 4 characters e.g pa, com, net, wxyz) 
			 
			Examples: Following email addresses will pass validation 
			abc@xyz.net; ab.c@tx.gov 
			*/  
			  
			//Initialize reg ex for email.  
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";  
			CharSequence inputStr = email;  
			//Make the comparison case-insensitive.  
			Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);  
			Matcher matcher = pattern.matcher(inputStr);  
			if(matcher.matches())
			{  
				isValid = true;  
			}  
			if(!isValid)
			{
				JOptionPane.showMessageDialog(null, "Email must be in form of abc@xyz.net; ab.c@tx.gov ");
			}
		}catch(NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "Empty email field");
		}

		return isValid;  
	}  


		/** isSSNValid: Validate Social Security number (SSN) using Java reg ex. 
		* This method checks if the input string is a valid SSN. 
		* @param email String. Social Security number to validate 
		* @return boolean: true if social security number is valid, false otherwise. 
		*/  

	public static boolean isSSNValid(String ssn)
	{  
		boolean isValid = false;  
		 /*SSN format xxx-xx-xxxx, xxxxxxxxx, xxx-xxxxxx; xxxxx-xxxx: 
		         ^\\d{3}: Starts with three numeric digits. 
		    [- ]?: Followed by an optional "-" 
		    \\d{2}: Two numeric digits after the optional "-" 
		    [- ]?: May contain an optional second "-" character. 
		    \\d{4}: ends with four numeric digits. 
		 
		        Examples: 879-89-8989; 869878789 etc. 
		*/  
		  
		//Initialize reg ex for SSN. 
		try
		{  
			String expression = "^\\d{6}[- ]\\d{4}$";  
			CharSequence inputStr = ssn;  
			Pattern pattern = Pattern.compile(expression);  
			Matcher matcher = pattern.matcher(inputStr);  
			if(matcher.matches())
			{  
				isValid = true;  
			} 

			if(!isValid)
			{
				JOptionPane.showMessageDialog(null, "SSN must be in form of 231223-1456 "); 
			}
		}catch(NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "Empty ssn field");
		}
		return isValid;  

	 }


		/** isNumeric: Validate a number using Java regex. 
		* This method checks if the input string contains all numeric characters. 
		* @param email String. Number to validate 
		* @return boolean: true if the input is all numeric, false otherwise. 
		*/  
		  
	public static boolean isPhone(String number)
	{  
		boolean isValid = false;  
		  
		/*Number: A numeric value will have following format: 
		         ^[-+]?: Starts with an optional "+" or "-" sign. 
		     [0-9]*: May have one or more digits. 
		    \\.? : May contain an optional "." (decimal point) character. 
		    [0-9]+$ : ends with numeric digit. 
		*/  
		  
		//Initialize reg ex for numeric data. 
		try
		{  
			String expression = "^[-+]?[0-9]*\\.?[0-9]+$";  
			CharSequence inputStr = number;  
			Pattern pattern = Pattern.compile(expression);  
			Matcher matcher = pattern.matcher(inputStr);  

			if(matcher.matches())
			{  
				isValid = true;  
			}  
			if(!isValid)
			{
				JOptionPane.showMessageDialog(null, "Enter phone number instead of alphabets");
			}

		}catch(NullPointerException e)
		{
			JOptionPane.showMessageDialog(null, "Empty phone field");
		} 
		return isValid;  
	} 

	/*protection from SQL injection. This will prevent any search that has an SQL keywords. These are potential sources of SQL injection. This program will refuse any query that has any of the specified keywords.*/

	public static boolean canHappenSQLInject(String input)
	{
		boolean status = false;
		try
		{
			String inputStr = input.toUpperCase();
			String[] KEYWORDS={ "SELECT", "INSERT", "WHERE", "DISTINCT", "UPDATE", "DELETE", "TRUNCATE", "TABLE", "ORDER", "GROUP" };

			int LIMIT = KEYWORDS.length;
			String firStr = "(?i).*";
			String lastStr = ".*";
			String [] matchStr  = new String [LIMIT];
			String matchString = null;

			for(int count = 0; count < KEYWORDS.length; count++)
			{
				matchString = firStr.concat(KEYWORDS[count]);
				matchString = matchString.concat(lastStr);
				status = inputStr.matches(matchString);
				if(status)
				{
					JOptionPane.showMessageDialog(null, "You have to paraphrase your search string to avoid the possibility of SQLinjection");
					break;
				}
			}
		}catch(NullPointerException v)
		{
			JOptionPane.showMessageDialog(null, "You have not entered a value to validate ");
		}

		return status;	
	}  
} 


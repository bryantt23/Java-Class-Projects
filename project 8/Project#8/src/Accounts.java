//Folder/Project Name: Project#8
//Programmer Name: Bryant Tunbutr

//Date: June 3, 2013
//Class Name: BankApplication
/*Project Description: This bank project uses a GUI to 
 * input the customer name, pin number and transaction amount,
 * along with buttons and radio buttons
 * for processing and display.
 * This is upgraded from Project#7 because there is an Accounts Superclass
 * and the Savings and Checking Classes inherit from Accounts
 * There are now fees for high amount withdrawals
 * 
 * There is a GUI class, an Accounts(calculation) class, Savings, Checking class
 * 
 * This is the Accounts Superclass
*/

import java.awt.*;
import java.awt.List;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;

public class Accounts
{
	//Declare instance variables
	private static int customerArrayIndexInteger;
	private static int indexArrayInteger;
	
	private static double customerBalanceDouble;	
	private static double amountDouble;	

	private static String displayAccountsString = null;

	private static boolean isThereADuplicatePIN;
	private static boolean doesPinExist;		
	private static int lastAcctInteger, lastAcctPlusOneInteger;
	
	// private can only be seen in this class
	// private variables
	private int countInteger, pinNumberInteger;
	private int pinNumberForTransactionsInteger;
	private int countPlusOneInteger;
	
	private String firstNameString, lastNameString;		
	private String withdrawalString;
	  
	// protected variables
	// protected can be seen by subclasses, Savings and Checking Classes

	//arrays
	protected static int[] pinArrayInteger = new int [10];
	protected static String[] firstNameArrayString = new String [10];
	protected static String[] lastNameArrayString = new String [10];
	protected static String[] accountTypeArrayString = new String [10];
	protected static double [] customerBalancesArray = new double [10];  

	protected static int customerPinInteger;
	
	protected static String customerFirstNameString;
	protected static String customerLastNameString;
	protected static String pinNumberString;
	protected static String customerBalanceString;

	protected static double initialDepositDouble;
	protected static double feeDepositDouble;
	
	//currency format
	static DecimalFormat currency = new DecimalFormat("$#,###.00");	
	
	//Constructors--same name as class, no return type
	//to set and store account information
	public Accounts()
	{}	

	// set information based on lastAcctInteger
	public Accounts(int lastAcctInteger, int pinNumberInteger, String firstNameString, String lastNameString)
	{ 
		// individual set methods
		
		setNewAccountPin(lastAcctInteger, pinNumberInteger);		
		setNewAccountFirstName(lastAcctInteger, firstNameString);		
		setNewAccountLastName(lastAcctInteger, lastNameString);			
		setNewAccountBalance(lastAcctInteger);			
		setNewAccountType(lastAcctInteger);			
	}	
	
	//to check for duplicate pin entry
	public Accounts(int countPlusOneInteger, int pinNumberInteger)
	{
		setIsThereADuplicatePinInfo(countPlusOneInteger, pinNumberInteger);		
	}

	//to display accounts already created
	public Accounts(int countInteger)
	{
		setAccountsDisplay(countInteger);	
	}

	//to process withdrawals
	public void setWithdrawal(double amountDouble, int indexArrayInteger)
	{	
		// left blank so appropriate subclass will process
	}
	
	//to process deposits
	public static void setDeposit(double amountDouble, int indexArrayInteger)
	{	
		//done here because it is same for checking and savings
		//add to current balance in that index array
		customerBalancesArray[indexArrayInteger]+=amountDouble;		
	}
		
	//to check for duplicate pin entry
	public void setIsThereADuplicatePinInfo(int countPlusOneInteger, int pinNumberInteger)
	{
		//use loop to see if entered PIN matches any of the existing PIN numbers in the PIN array
		for(int i = 0; i <countPlusOneInteger; i++){
			if (  	pinNumberInteger==pinArrayInteger[i] || pinNumberInteger==pinArrayInteger[i+1] || 
					pinNumberInteger==pinArrayInteger[i+2] || pinNumberInteger==pinArrayInteger[i+3] || 
					pinNumberInteger==pinArrayInteger[i+4] || pinNumberInteger==pinArrayInteger[i+5] || 
					pinNumberInteger==pinArrayInteger[i+6] || pinNumberInteger==pinArrayInteger[i+7] || 
					pinNumberInteger==pinArrayInteger[i+8] || pinNumberInteger==pinArrayInteger[i+9] 
					){
				//return boolean of true if there is a match
				isThereADuplicatePIN = true;
			}
				else{
					//return boolean of false if there is no match
					isThereADuplicatePIN = false;		
					}
		}
	}

	//to display accounts already created
	public void setAccountsDisplay(int countInteger)
	{
		//header String
		displayAccountsString = ("Name" + '\t' + '\t' + "PIN"+ '\t' +"Balance"+ '\n'+ '\n');

		String tempString =	null;

		//loop through and create a String of all of the account information
		//this loop stops at the countInteger, meaning it only displays existing accounts
		for(int j = 0; j < countInteger; j++){
			tempString = String.format(
					firstNameArrayString[j] + " "+ lastNameArrayString[j] + '\t' + '\t' + pinArrayInteger[j]+ '\t' + currency.format(customerBalancesArray[j])+ '\n');			
				displayAccountsString +=(tempString);
				}						
	}	

	//to set and store account pin 
	public void setNewAccountPin(int lastAcctInteger, int pinNumberInteger)
	{
		//add to the PIN array and set for the get method
		pinArrayInteger[lastAcctInteger] = pinNumberInteger;
		customerPinInteger = pinNumberInteger;		
	}

	//to set and store account first name 
	public void setNewAccountFirstName(int lastAcctInteger, String firstNameString)
	{
		//add to the first name array and set for the get method
		firstNameArrayString[lastAcctInteger] = firstNameString;		
		customerFirstNameString = firstNameString;		
	}
	
	//to set and store account last name 
	public void setNewAccountLastName(int lastAcctInteger, String lastNameString)
	{
		//add to the last name array and set for the get method
		lastNameArrayString[lastAcctInteger] = lastNameString;
		customerLastNameString = lastNameString;		
	}

	//to set and store account balance based on initial deposit, which is based
	//on whether it is checking or savings acct
	public void setNewAccountBalance(int lastAcctInteger)
	{
		//add to the balances array and set for the get method
		customerBalancesArray[lastAcctInteger] = initialDepositDouble;
		customerBalanceDouble = initialDepositDouble;			
	}
	
	//to set new account type
	public void setNewAccountType(int lastAcctInteger)
	{
		//add to the balances array and set for the get method
		accountTypeArrayString[lastAcctInteger] = "";
	}
		
	//return doesPinExist boolean
	public static boolean getDoesPinExist()
	{
		return doesPinExist;
	}

	//return isThereADuplicatePIN boolean
	public static boolean getIsThereADuplicatePIN()
	{
		return isThereADuplicatePIN;
	}

	//convert then return pinNumberString 
	public static String getPinNumberString()
	{
		pinNumberString = Integer.toString(customerPinInteger);
		return pinNumberString;
	}

	//return customer First Name  
	public static String getFirstName()
	{		
		return customerFirstNameString;
	}	
	
	//return customer Last Name  
	public static String getLastName()
	{				
		return customerLastNameString;
	}	
	
	//convert balance then return as String 
	public static String getBalanceString()
	{			
		customerBalanceString = currency.format(customerBalanceDouble);
		return customerBalanceString;
	}	

	//return account type as a String
	public static String[] getAccountTypeArrayString()
	{			
		return accountTypeArrayString;
	}	
	
	//return all accounts as a String for display
	public static String getDisplayAccountsString()
	{			
		return displayAccountsString;
	}	
	
	//return firstNameArrayString as a String
	public static String[] getFirstNameArrayString()
	{			
		return firstNameArrayString;
	}	
	
	//return lastNameArrayString as a String
	public static String[] getLastNameArrayString()
	{			
		return lastNameArrayString;
	}	
	
	//return customerBalancesArray as a double
	public static double[] getBalancesArray()
	{			
		return customerBalancesArray;
	}	

	//return fee for withdrawal as a double
	public static double getFeeDepositDouble()
	{			
		return feeDepositDouble;
	}	
	
	//return pinArrayInteger as an integer
	public static int[] getPinNumbersArray()
	{			
		return pinArrayInteger;
	}	
	
	//return customerArrayIndexInteger as an integer
	public static int getArrayIndexInteger()
	{			
		return customerArrayIndexInteger;
	}	
	
}	
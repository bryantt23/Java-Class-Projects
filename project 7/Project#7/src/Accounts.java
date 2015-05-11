//Folder/Project Name: Project#7
//Programmer Name: Bryant Tunbutr


//Date: May 24, 2013
//Class Name: BankApplication
/*Project Description: This car bank project uses a GUI to 
 * input the customer name, pin number and transaction amount,
 * along with buttons and radio buttons
 * for processing and display.
 * There is a GUI class, an accounts(calculation) class,
 * 
 * This is the calculations class
*/

import java.awt.*;
import java.awt.List;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;

public class Accounts
{
	//Declare instance variables
	private int countInteger, pinNumberInteger;
	private int pinNumberForTransactionsInteger;
	private int countPlusOneInteger = countInteger+1;
	
	private String firstNameString, lastNameString;		
	private String withdrawalString;

	private static int customerArrayIndexInteger;
	
	private static int customerPinInteger;
	private static String customerFirstNameString, customerLastNameString;	
	private static double customerBalanceDouble;	

	private static double amountDouble;	
	private static int indexArrayInteger;
	
	private static String pinNumberString, customerBalanceString;

	private static String displayAccountsString = null;

	private static boolean isThereADuplicatePIN;
	private static boolean doesPinExist;

	private static int[] pinArrayInteger = new int [10];
	private static String[] firstNameArrayString = new String [10];
	private static String[] lastNameArrayString = new String [10];
	private static double [] customerBalancesArray = new double [10];    
	
	//declare constant
	private final double INITIAL_DEPOSIT_DOUBLE = 100;

	//currency format
	static DecimalFormat currency = new DecimalFormat("$#,###.00");	
	
	//Constructors--same name as class, no return type
	//to set and store account information
	public Accounts(int countInteger, int pinNumberInteger, String firstNameString, String lastNameString)
	{
		setNewAccount(countInteger, pinNumberInteger, firstNameString, lastNameString);		
	}	
	
	//to process withdrawals
	public Accounts(String withdrawalString, double amountDouble, int indexArrayInteger)
	{
		withdrawal(withdrawalString, amountDouble, indexArrayInteger);	
	}

	//to process deposits
	public Accounts(double amountDouble, int indexArrayInteger)
	{
		deposit(amountDouble, indexArrayInteger);		
	}

	//to check for duplicate pin entry
	public Accounts(int countPlusOneInteger, int pinNumberInteger)
	{
		isThereADuplicatePIN(countPlusOneInteger, pinNumberInteger);		
	}

	//to display accounts already created
	public Accounts(int countInteger)
	{
		displayAccounts(countInteger);	
	}

	//to process withdrawals
	public void withdrawal(String withdrawalString, double amountDouble, int indexArrayInteger)
	{	
		//subtract from current balance in that index array
		customerBalancesArray[indexArrayInteger]-=amountDouble;
	}

	//to process deposits
	public void deposit(double amountDouble, int indexArrayInteger)
	{	
		//add to current balance in that index array
		customerBalancesArray[indexArrayInteger]+=amountDouble;		
	}

	//to check for duplicate pin entry
	public void isThereADuplicatePIN(int countPlusOneInteger, int pinNumberInteger)
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
	public void displayAccounts(int countInteger)
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
	
	//to set and store account information
	public void setNewAccount(int countInteger, int pinNumberInteger, String firstNameString, String lastNameString)
	{

		//add to the PIN array and set for the get method
		pinArrayInteger[countInteger] = pinNumberInteger;
		customerPinInteger = pinNumberInteger;		

		//add to the first name array and set for the get method
		firstNameArrayString[countInteger] = firstNameString;		
		customerFirstNameString = firstNameString;		

		//add to the last name array and set for the get method
		lastNameArrayString[countInteger] = lastNameString;
		customerLastNameString = lastNameString;		

		//add to the balances array and set for the get method
		customerBalancesArray[countInteger] = INITIAL_DEPOSIT_DOUBLE;
		customerBalanceDouble = INITIAL_DEPOSIT_DOUBLE;	
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

	//return customerFirstNameString 
	public static String getFirstName()
	{		
		return customerFirstNameString;
	}	
	
	//return customerLastNameString 
	public static String getLastName()
	{				
		return customerLastNameString;
	}	
	
	//convert then return customerLastNameString 
	public static String getBalanceString()
	{			
		customerBalanceString = currency.format(customerBalanceDouble);
		return customerBalanceString;
	}	
	
	//return all accounts as a String
	public static String getDisplayAccountsString()
	{			
		return displayAccountsString;
	}	
	
	//return firstNameArrayString as a String
	public static String[] getFirstNameArrayString()
	{			
		return firstNameArrayString;
	}	
	
	//return all lastNameArrayString as a String
	public static String[] getLastNameArrayString()
	{			
		return lastNameArrayString;
	}	
	
	//return customerBalancesArray as a double
	public static double[] getBalancesArray()
	{			
		return customerBalancesArray;
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
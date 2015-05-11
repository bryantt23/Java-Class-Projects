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
 * This is the Checking subclass
*/

import javax.swing.JOptionPane;

// this inherits from Accounts
public class Checking extends Accounts
{
	// instance variables
	static double customerBalanceDouble;	

	private final double INITIAL_CHECKING_DEPOSIT_DOUBLE = 100; //used to send to Superclass for initial deposit

	//constants
	static final double CHECKING_WITHDRAWAL_FEE_DOUBLE = 2.0;
	static final double CHECKING_WITHDRAWAL_LIMIT_DOUBLE = 750;

	//Constructors--same name as class, no return type
	//to set and store account information
	
	// set information based on lastAcctInteger
	public Checking(int lastAcctInteger, int pinNumberInteger, String firstNameString, String lastNameString)
	{
		// individual set methods
		
		setNewAccountPin(lastAcctInteger, pinNumberInteger);		
		setNewAccountFirstName(lastAcctInteger, firstNameString);		
		setNewAccountLastName(lastAcctInteger, lastNameString);			
		setNewAccountBalance(lastAcctInteger);			
		setNewAccountType(lastAcctInteger);			
	}	

	//to process withdrawals
	public void setWithdrawal(double amountDouble, int indexArrayInteger)
	{	
		//check to see if eligible for fee
		if (amountDouble > CHECKING_WITHDRAWAL_LIMIT_DOUBLE)
		{
			//Check to see if sufficient balance to do a withdrawal
			if((amountDouble + CHECKING_WITHDRAWAL_FEE_DOUBLE) <= (Accounts.getBalancesArray()[indexArrayInteger] ))
			{
				// calculate withdrawal amount including fee								
				amountDouble = amountDouble + CHECKING_WITHDRAWAL_FEE_DOUBLE;				

		        int responseInt;
		        // gives user choice to say yes, no, or cancel before doing withdrawal
		        responseInt = JOptionPane.showConfirmDialog(null, "There will be a $2.00 fee. Proceed?");
		        	if (responseInt==JOptionPane.YES_OPTION) //if yes
		        	{
		        		//subtract from current balance in that index array
		        		customerBalancesArray[indexArrayInteger]-=(amountDouble);	
		        		
		        		feeDepositDouble = 2.00; // lets the superclass know about fee for display purposes
		        	}		        	
		        	else //if no or cancel
		        	{
		        		//tells user nothing has been changed
		        		JOptionPane.showMessageDialog(null, "Withdrawal has not been processed.");
		        	}		        	
			}			
			else{ // if amount including fee would overdraw account
				
				//display message to user to select a lower amount
				JOptionPane.showMessageDialog(null, "This would overdraw your account! \n " +
						"Please select a lower amount");			
			}
		}
		else{ // if amount would not cause fee and not overdraw
			
    		customerBalancesArray[indexArrayInteger]-=(amountDouble);					
		}
	}
	
	//to set and store account Balance
	public void setNewAccountBalance(int lastAcctInteger)
	{
		initialDepositDouble = INITIAL_CHECKING_DEPOSIT_DOUBLE;
		//add to the balances array and set for the get method
		customerBalancesArray[lastAcctInteger] = initialDepositDouble;
		customerBalanceDouble = initialDepositDouble;			
	}

	//to set and store account Pin
	public void setNewAccountPin(int lastAcctInteger, int pinNumberInteger)
	{
		//add to the PIN array and set for the get method
		pinArrayInteger[lastAcctInteger] = pinNumberInteger;
		customerPinInteger = pinNumberInteger;		
	}

	//to set and store account First Name
	public void setNewAccountFirstName(int lastAcctInteger, String firstNameString)
	{
		//add to the first name array and set for the get method
		firstNameArrayString[lastAcctInteger] = firstNameString;		
		customerFirstNameString = firstNameString;		
	}
	
	//to set and store account Last Name
	public void setNewAccountLastName(int lastAcctInteger, String lastNameString)
	{
		//add to the last name array and set for the get method
		lastNameArrayString[lastAcctInteger] = lastNameString;
		customerLastNameString = lastNameString;		
	}
	
	//to set new account type for display purposes
	public void setNewAccountType(int lastAcctInteger)
	{
		//add to the array and set for the get method
		accountTypeArrayString[lastAcctInteger] = "Checking";
	}		

	//convert then return pinNumber as a String 
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
	
	//convert then return Balance as a String 
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

}

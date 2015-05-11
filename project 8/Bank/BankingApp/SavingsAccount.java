/**
 *
 * @author kenneth Odoh
 */

/**
	This is covered under GPL licence version 2 or higher
*/

import javax.swing.JOptionPane;
public class SavingsAccount 
{
    private String customerid;

    public SavingsAccount() 
	{
	}

    public SavingsAccount(String custID) 
	{
		customerid = custID;
    }

	/**
		withdraw money from the account.Withdrawal is same as negative deposit
	*/
    public void withdraw(double amount) throws MyException
    {
		try
		{
		    if (getBalance() >= amount) 
		    {   
				Query.makeDepositSavingsAcc(getCustomerId(), (-1.00 * amount));               
		    }
		    else
		    {
		        throw new MyException("Overdraft is not available for saving account.You need a current account to get the feature of overdraft");
		    }
		}catch(NumberFormatException H)
		{
			JOptionPane.showMessageDialog(null, "Incompatible data type.!!!!!");
		}
    }

	/**
		get the balance
	*/

    public double getBalance() 
    {
		double tmp = 0.00;
		try
		{
			String balance[][] = Query.getSavingAccBalance(getCustomerId() );

			if(balance[0][0].length() == 0 )
			{
				tmp = 0.00;
			}else
			{
				tmp = Double.parseDouble(balance[0][0]);
			}
		}catch(NullPointerException v)
		{}
		return tmp;
    }

	/**
		make the deposit
	*/
    public void deposit(double amount) 
    {
		try
		{
        	Query.makeDepositSavingsAcc(getCustomerId(), amount);
		}catch(NumberFormatException H)
		{
			JOptionPane.showMessageDialog(null, "Incompatible data type.!!!!!");
		}
    }
	/**
		get the customer id
	*/
    public String getCustomerId() 
    {
        return customerid;
    }
}

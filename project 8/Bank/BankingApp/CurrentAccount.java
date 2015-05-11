/**
 *
 * @author kenneth Odoh
 */

/**
	This is covered under GPL licence version 2 or higher
*/
import java.math.*;
import javax.swing.JOptionPane;
public class CurrentAccount 
{

    private final double OVERDRAFTLIMIT = 2000.00;
    private final float OVERDRAFT_RATE = -0.050f; // over draft rate
    private String customerid; 


    public CurrentAccount(String custID) 
    {
		customerid = custID;
    }

	/**
		calcualte overdraft sum account
	*/
	private double overdraftSum()
	{
		double tmp = 0.00;
		try
		{
			String overdraftsum[][] = Query.getOverDraftBalance(getCustomerId() );
			if (overdraftsum[0][0].length() == 0)
			{
				tmp = 0.00;
			}
			else	
			{
					tmp = Double.parseDouble(overdraftsum[0][0]);
			}
		}catch(NullPointerException v)
		{}
		return tmp;
	}
			
	/**
		round the amount
	*/
	private double roundDecimal(int number_of_places, double value)
	{
		BigDecimal bd = new BigDecimal(value).setScale(number_of_places,RoundingMode.HALF_EVEN);
		value = bd.doubleValue();
		return value;
	}
	
	/**
		withdraw from the account
	*/
    public void withdraw(double amount)throws MyException
    {
		try
		{
				double overdsum = overdraftSum();  //overdraft sum
				if(getBalance() >= amount)
				{
					Query.makeDepositCurrentAcc(getCustomerId(), (-1.00 * amount));
				}
				else
				{
					if(roundDecimal(2,(overdsum + amount)) <= roundDecimal(2,(OVERDRAFTLIMIT + getBalance())))
					{
						double loanAmt = Math.abs(getBalance() - amount);
						Query.makeOverDraftLoan(getCustomerId(),loanAmt );
						//deduct overdraft charges from the balance
						Query.makeDepositCurrentAcc(getCustomerId(), OVERDRAFT_RATE * loanAmt);			
					}
					else
					{
						throw new MyException("You have exceeded your borrowing limit");
					}
				}

		}catch(NumberFormatException H)
		{
			JOptionPane.showMessageDialog(null, "Incompatible data type.Check both text fields!!!!!");
		}

    }

	/**
		get the balance from the account
	*/
    public double getBalance() 
    {
		double tmp = 0.00;
		try
		{
			String balance[][] = Query.getCurrentAccBalance(getCustomerId() );
			if(balance[0][0].length() == 0)
			{
				tmp = 0.00;
			}
			else
			{
				tmp = Double.parseDouble(balance[0][0]);
			}
		}catch(NullPointerException v)
		{}
		return tmp;
    }

	/**
		get the customer id from the account
	*/
    public String getCustomerId() 
    {
        return customerid;
    }


	/**
		deposit into the account
	*/
    public void deposit(double amount) 
    {
		try
		{
			Query.makeDepositCurrentAcc(getCustomerId() ,amount);
		}catch(NumberFormatException H)
		{
			JOptionPane.showMessageDialog(null, "Incompatible data type.!!!!!");
		}
    }
}

/**
 *
 * @author kenneth Odoh
 */

/**
	This is covered under GPL licence version 2 or higher
*/

/**
This is used to organize the code and make the customer and administrator have different view.The views does not overlap.
*/
public class BankApplication
{  
	private AdministratorViewFirstPage adminView;
	private CustomerViewFirstPage custpageView;
	private String custID; //customer id
	private String status; // user status
	private String userType; //user type
    public BankApplication(String ID,String stat,String Type)
    {
		custID = ID;
		status = stat;
		userType = Type;
		
		/**
			While the user is still the logged in the application continues running.
		*/
		if(Login.isLoggedIn())
		{
			if (status.equals("0"))
			{
				if (userType.equals("1"))
				{
					//administrator view
					adminView = new AdministratorViewFirstPage(getCustID()) ;
				}
				else if (userType.equals("0"))
				{
					//customer view
					custpageView = new CustomerViewFirstPage(getCustID());
				}			
			}
		}
	}

	/**
		return the customer ID
	*/
	public String getCustID()
	{
		return custID;
	}

	/**
		return the status
	*/
	public String getStatus()
	{
		return status;
	}

	/**
		return the user type
	*/
	public String getUserType()
	{
		return userType;
	}

}

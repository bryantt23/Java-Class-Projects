/**
 *
 * @author kenneth Odoh
 */

/**
	This is covered under GPL licence version 2 or higher
*/

import java.text.*;
import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Customer
{
    private String firstName;
   	private String SSN;
	private String firstname;	
	private String lastname;
	private String address;
	private String phone;	
	private String email;	
	private String username;
	private String userpassword;	
	private String status;
	private String type ;
	private String userID;

    //used when logging in by the user using username and password
    //There cannot be any annonymous user.Everybody must be identified.


    //when writing to the database but the ID will be generated by the database
	public Customer(String SSN ,String firstname,String lastname,String address,String phone,String email,String userpassword,String	status, String type)
	{
		if((Validator.isEmailValid(email)) && (Validator.isSSNValid(SSN)) && (Validator.isPhone(phone)))
		{
			setStatus(status);
			setType(type);
			createUserName(firstname,lastname);
			Query.makeNewCustomer(SSN ,firstname,lastname, address, phone, email, username, userpassword, getStatus(), getType());
			userID = Query.getCustomerIDFromSSN(SSN);
		}

	}

	public Customer(String id)
	{
		userID = id;
	}

	public Customer()
	{}

	/**
		get the customer id from customer
	*/
	public String getCustomerId()
	{
		return userID;
	}

	/**
		update the customer's first name
	*/
	public void upDateCustomerFirstName(String firstname)
	{
		Query.upDateUserFirstName(getCustomerId(), firstname);
	}


	/**
		update the customer's last name
	*/

	public void upDateCustomerSSN(String ssn)
	{
		if(Validator.isSSNValid(ssn))
			Query.upDateUserSSN(getCustomerId(), ssn);
	}
	/**
		update the customer's last name
	*/
	public void upDateCustomerLastName(String lastname)
	{
		Query.upDateUserLastName(getCustomerId(), lastname);
	}

	/**
		update the customer's address 
	*/
	public void upDateCustomerAddress(String address)
	{
		Query.upDateUseraddress(getCustomerId(), address);
	}

	/**
		update the customer's phone
	*/
	public void upDateCustomerPhone(String phone)
	{
		if(Validator.isPhone(phone))
			Query.upDateUserPhone(getCustomerId(), phone);
	}

	/**
		update the customer's email
	*/

	public void upDateCustomerEmail(String email)
	{
		if(Validator.isEmailValid(email))
			Query.upDateUserEmail(getCustomerId(), email);
	}

	/**
		update the customer's username
	*/
	public void upDateCustomerUserName(String username)
	{
		Query.upDateUsername(getCustomerId(), username);
	}

	/**
		update the customer's password
	*/
	public void upDateCustomerPassword(String password)
	{
		Query.upDateUserPassword(getCustomerId(), password);
	}

	/**
		update the customer's status
	*/
	public void upDateCustomerStatus(String password)
	{
		Query.upDateUserStatus(getCustomerId(), password);
	}


    //when reading from the database but the ID will be auto generated by the database


    //use the initial letter of the first name with the last and some randomly generated number to form 

    private void setStatus(String input)
    {
		try
		{
		    if (input.equalsIgnoreCase("0"))
			{
		        status = "0"; //user enabled
			}
		    else if (input.equalsIgnoreCase("1"))
			{
		        status = "1";//user disabled
			}
		}catch(NullPointerException h)
		{}
    }

	/**
		set the customer's status
	*/
    private String getStatus()
    {
        return status;
    }

	/**
		set the customer's type
	*/
    private void setType(String input)
    {
		try
		{
		    if (input.equalsIgnoreCase("0"))
			{
		        type = "0"; //for customer
			}
		    else if (input.equalsIgnoreCase("1"))
			{
		        type = "1";//for administrator
			}
		}catch(NullPointerException h)
		{}
    }

	/**
		get the customer's type
	*/
    private String getType()
    {
        return type;
    }

	/**
		create the customer's username
	*/
    private void createUserName(String first,String last)
    {
        char[] firstInitial = first.toCharArray(); 
        String val = Character.toString(firstInitial[0]);//get the first string literal
        Random rand = new Random(); 
        NumberFormat numberFormat = new DecimalFormat("00000");
        int suffix = rand.nextInt(100000);
        username = val + last + numberFormat.format(suffix);
        
    }

	/**
		get the customer's username
	*/

    public String getUserName()
    {
		try
		{
		username = Query.getCustomerUserName(getCustomerId());
		}catch(NullPointerException w)
		{}
        return username;
    }

	/**
		get the customer's full name
	*/
    public String getfullName()
    {
		String fullname[][] = Query.getCustomerFulName(getCustomerId());
        return (fullname[0][0] +"  "+ fullname[0][1]);
    }

	/**
		get the customer's first name
	*/
    public String getfirstName()
    {
		String fullname[][] = Query.getCustomerFulName(getCustomerId());
        return fullname[0][0];
    }

	/**
		get the customer's last name
	*/
    public String getLastName()
    {
		String fullname[][] = Query.getCustomerFulName(getCustomerId());
        return fullname[0][1];
    }

	/**
		get the customer's address
	*/
    public String getAddress()
    {
		address = Query.getCustomerAddress(getCustomerId());
        return address;
    }

	/**
		get the customer's phone
	*/
    public String getPhone()
    {
		phone = Query.getCustomerPhone(getCustomerId());
        return phone;
    }

	/**
		get the customer's email
	*/
    public String getEmail()
    {
		email = Query.getCustomerEmail(getCustomerId());
        return email;
    }

	/**
		get the customer's id from SSN
	*/
	public String getCustomerIDFromSSN(String ssn)
	{
		return Query.getCustomerIDFromSSN(ssn);
	}

}

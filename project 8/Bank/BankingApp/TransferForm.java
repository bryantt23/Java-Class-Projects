/**
 *
 * @author kenneth Odoh
 */

/**
	This is covered under GPL licence version 2 or higher
*/

import java.awt.*;     // Needed for BorderLayout class
import javax.swing.*;  // Needed for Swing classes
import java.awt.event.*; // Needed for event listener interface
public class TransferForm extends JFrame
{
	private JLabel bankNumLabel;
	private JLabel amountLabel;

	private JLabel dummyLabel;
	private JLabel balanceLabel;

	private JTextField bankNumfield;
	private JTextField amountfield;
	private JTextField balancefield;
	private JButton commitButton;

    private final int WINDOW_WIDTH = 650;   // Window width
    private final int WINDOW_HEIGHT = 300;  // Window height

	private int accType;

	private String custID;

	private SavingsAccount mySav;
	private CurrentAccount myCur;

	private ConfirmationForm myConfirm;

/*
				accType = 1   current account
				accType = 2  Saving account
*/


    public TransferForm (String ID, int TYPE)
    {


        // Set the title bar text.
        setTitle("Bank Transfer form ");
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(4,2));

		accType = TYPE;
		custID = ID;

        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(4,2));
	

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		bankNumLabel = new JLabel("Enter Recipient's bank account no.") ;
		amountLabel = new JLabel("Enter amount to transfer to Recipient") ;
		dummyLabel = new JLabel() ;
		dummyLabel.setVisible(false);
		balanceLabel = new JLabel("Your current balance is ") ;

		bankNumfield = new JTextField(20);
		amountfield = new JTextField(20);
		balancefield = new JTextField(20);

		balancefield.setEditable(false);

		commitButton = new JButton("Commit"); 


		commitButton.addActionListener(new ExecuteButtonListener());

        getContentPane().add(bankNumLabel);    
        getContentPane().add(bankNumfield);
        getContentPane().add(amountLabel);    
        getContentPane().add(amountfield);
        getContentPane().add(dummyLabel);    
        getContentPane().add(commitButton);
        getContentPane().add(balanceLabel);    
        getContentPane().add(balancefield);

		setVisible(true);
	}


	public int getAccType()
	{
		return accType;
	}

	public String getSenderID()
	{
		return custID;
	}

	public String [] getIDFromAccountNum(String givenAccNo)
	{
	/************************************************************************************

	 String 	substring(int beginIndex, int endIndex)
		      Returns a new string that is a substring of this string.


	 int 	indexOf(String str)
		      Returns the index within this string of the first occurrence of the specified substring.

	 int 	length()
		      Returns the length of this string.

	// Anywhere
	b = string.matches("(?i).*i am.*");

	How to get the customer id from the account number.
		get the account number. 
		from account number get user id.
		check if the account no contains the required substring

		get the length of the user input for customer id.
		get the index of the first occurence of the account keyword.
		get a substring from the index of first occurenc of account keyword to the length of the string.
		check the length of this new substring
		if it is 3.The account is validated.Otherwise.Invalidate account number is displayed to the user.

		if validated:
			get a substring from zero to the index obtained from above.
			This will give the customer id as the customer id preceeds the account keyword.
	************************************************************************************/
		String custno = "";


		int givenAccNoLength = givenAccNo.length();

		String accountSubString = givenAccNo.substring((givenAccNoLength -3), givenAccNoLength);

		int lastIndex=0; 	
		boolean status = false;
		String firStr = "(?i).*";
		String lastStr = ".*";
		String matchStr = firStr.concat(accountSubString);
		matchStr = matchStr.concat(lastStr);

		if ((accountSubString.equals("CUR")) || (accountSubString.equals("SAV")))
		{
			status = givenAccNo.matches(matchStr);
		}

		if(status)
		{
			if (accountSubString.equals("CUR"))
			{
				lastIndex =	givenAccNo.indexOf(accountSubString);
			}
			else if (accountSubString.equals("SAV"))
			{
				lastIndex =	givenAccNo.indexOf(accountSubString);
			}
			custno = givenAccNo.substring(0, lastIndex );
			if(custno.matches("^-?\\d+$"))
			{
				//hit database
			}
			else
			{
				custno ="";
				JOptionPane.showMessageDialog(null, "This is in a wrong format!!!!!");
			}


		}
		else
		{
			JOptionPane.showMessageDialog(null, "This is not a valid customer account number\nCheck the account number and try again!!!!!");
		}	
		String []resultArray = new String [2];
		resultArray[0] = custno;
		resultArray[1] = accountSubString;

		return resultArray;

	}

    private class ExecuteButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if ( e.getSource() == commitButton )
            {
/*
				accType = 1   current account
				accType = 2  Saving account
*/

				String accNo, amount;

				try
				{
					accNo = bankNumfield.getText();
					amount = amountfield.getText();

					if((accNo.length() == 0) || (amount.length() == 0))
					{
						JOptionPane.showMessageDialog(null, "Make sure the the recipient account number or amount is filled!!!!!");
					}
					else
					{
/*
				accType = 1   current account
				accType = 2  Saving account
*/
						//get the account number
						accNo = accNo.toUpperCase();
						String[] result = null;
						String userID,ACCTYPE;
						result = getIDFromAccountNum(accNo);//recipient account number
						userID = result[0];
						ACCTYPE = result[1];
						//ensure that the userid and acctype is updated frequently as the program flows
						double amt = Double.parseDouble(amount);
						myConfirm = new ConfirmationForm (userID);
						int aCCOUNTTYPE = getAccType();

						boolean status = Confirm.isConfirmed();
						if(status)
						{	
							myConfirm.dispose();				
							if(aCCOUNTTYPE == 2)
							{
								//saving account
								result = getIDFromAccountNum(accNo);//recipient account number

								userID = result[0];
								ACCTYPE = result[1];
								mySav = new SavingsAccount(getSenderID() );

								//withdraw the same amount from your account and transfer it to the recipient account
								aCCOUNTTYPE = getAccType();
								try
								{
									mySav.withdraw(amt);
									JOptionPane.showMessageDialog(null, "Your bank account transfer was successful!!!!!");
								}catch(MyException H)
								{
									JOptionPane.showMessageDialog(null, "Withdrawal was not successful!!!!!");
								}
								balancefield.setText(Double.toString(mySav.getBalance()));			
								aCCOUNTTYPE = getAccType();						
								if(ACCTYPE.equals("SAV"))
								{
									aCCOUNTTYPE = getAccType();
									result = getIDFromAccountNum(accNo);//recipient account number
									userID = result[0];
									ACCTYPE = result[1];
									mySav = new SavingsAccount(userID );
									//deposit the amount into the recipient account
									mySav.deposit(amt);
									aCCOUNTTYPE = getAccType();
								}
								else if(ACCTYPE.equals("CUR"))
								{
									aCCOUNTTYPE = getAccType();
									result = getIDFromAccountNum(accNo);//recipient account number
									userID = result[0];
									ACCTYPE = result[1];
									myCur = new CurrentAccount(userID );
									//deposit the amount into the recipient account
									myCur.deposit(amt);
									aCCOUNTTYPE = getAccType();
								}

							}
							else if(aCCOUNTTYPE == 1)
							{
								aCCOUNTTYPE = getAccType();
								result = getIDFromAccountNum(accNo);//recipient account number
								userID = result[0];
								ACCTYPE = result[1];
								myCur = new CurrentAccount(getSenderID() );
								//current account
								//withdraw the same amount from your account and transfer it to the recipient account
								try
								{
									myCur.withdraw(amt);
									JOptionPane.showMessageDialog(null, "Your bank account transfer was successful!!!!!");
								}catch(MyException H)
								{
									JOptionPane.showMessageDialog(null, "Withdrawal was not successful!!!!!");
								}
								balancefield.setText(Double.toString(myCur.getBalance()));
								aCCOUNTTYPE = getAccType();
								if(ACCTYPE.equals("SAV"))
								{
									aCCOUNTTYPE = getAccType();
									result = getIDFromAccountNum(accNo);//recipient account number
									userID = result[0];
									ACCTYPE = result[1];
									mySav = new SavingsAccount(userID );
									//deposit the amount into the recipient account
									mySav.deposit(amt);
									aCCOUNTTYPE = getAccType();
								}
								else if(ACCTYPE.equals("CUR"))
								{
									aCCOUNTTYPE = getAccType();
									result = getIDFromAccountNum(accNo);//recipient account number
									userID = result[0];
									ACCTYPE = result[1];
									myCur = new CurrentAccount(userID );
									//deposit the amount into the recipient account
									myCur.deposit(amt);
									aCCOUNTTYPE = getAccType();
								}
							}
						}
					}
				}catch(NumberFormatException H)
				{
					JOptionPane.showMessageDialog(null, "Incompatible data type.Check both text fields!!!!!");
				}
			}
		}
	}

/*

	public static void main(String [] args)
	{
		TransferForm myobj = new TransferForm ("2", 2);
	}
*/



}

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
public class WithdrawalViewForm extends JFrame
{
    private final String MSG = "Enter the amount to withdrawal ";
    private final String MSG_BAL = "Your current balance is ";
    private JLabel welcomelabel;
    private JLabel dummylabel;
    private JLabel balanceLabel;
    private JButton commitButton;
    private JTextField currentBalfield;
    private JTextField depositBalfield;

    private final int WINDOW_WIDTH = 700;   // Window width
    private final int WINDOW_HEIGHT = 200;  // Window height

	private int ACCTYPE;
	private SavingsAccount mySav; //savings account object
	private CurrentAccount myCur; //current account object


    private JLabel messageLabel;
   	private JLabel dummyLabel1;
   	private JLabel dummyLabel2;
   	private JLabel dummyLabel3;

    private JTextField fullNamefield;

	private String custID;

	private Customer myCust;
    public WithdrawalViewForm(String ID,int TYPE)
    {


        // Set the title bar text.
        setTitle("Making a Withdrawal");
        JFrame.setDefaultLookAndFeelDecorated(true);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		custID = ID;
		ACCTYPE = TYPE; 

		//create a customer object
		myCust = new Customer(ID) ;

    	messageLabel = new JLabel("Choose account to make withdrawal ");
		fullNamefield = new JTextField(20);
        fullNamefield.setEditable(false);
        fullNamefield.setText(myCust.getfullName());

		dummyLabel2 = new JLabel();
		dummyLabel3 = new JLabel();


        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(4,2));

        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        welcomelabel = new JLabel(MSG);
        currentBalfield = new JTextField(15);    
        dummylabel = new JLabel();
        dummylabel.setVisible(false);

        commitButton = new JButton("Commit");
        balanceLabel = new JLabel(MSG_BAL);

        depositBalfield = new JTextField(15);


        currentBalfield = new JTextField(15);
        currentBalfield.setEditable(false);

        getContentPane().add(messageLabel);
        getContentPane().add(fullNamefield);

        getContentPane().add(welcomelabel);
        getContentPane().add(depositBalfield);
        getContentPane().add(dummylabel);
        getContentPane().add(commitButton);
        getContentPane().add(balanceLabel);
        getContentPane().add(currentBalfield);

        commitButton.addActionListener(new ExecuteButtonListener());

        setVisible(true);
    }

	/**
		get the customer id
	*/
	public String getCustomerID()
	{
		return custID;
	}

	/**
		register event to buttons
	*/
    private class ExecuteButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
             // Get the action command.
             String actionCommand = e.getActionCommand();

             // Determine which button was clicked and display
             // a message.
             if (actionCommand.equals("Commit"))
             {
 /*
				accType = 1   current account
				accType = 2  Saving account
*/
				//do database query 
				String amountStr = depositBalfield.getText();
				double amount;
				if(amountStr.length() == 0)
				{
					amount = 0.00;
				}else
				{
					amount = Double.parseDouble(amountStr);
				}

				if(ACCTYPE == 2)
				{
					//savings account
					currentBalfield.setText("");
					mySav = new SavingsAccount(getCustomerID());
					try
					{
						//make withdrawal
						mySav.withdraw(amount);
					}catch(MyException h)
					{}
					currentBalfield.setText(Double.toString(mySav.getBalance()));
					JOptionPane.showMessageDialog(null, "You have made the withdrawal from your Savings Account successfully");
				}else if(ACCTYPE == 1)
				{
					//current account
					currentBalfield.setText("");
					myCur = new CurrentAccount(getCustomerID()) ;
					try
					{
						//make withdrawal
						myCur.withdraw(amount);
					}catch(MyException h)
					{}
					currentBalfield.setText(Double.toString(myCur.getBalance()) );
					JOptionPane.showMessageDialog(null, "You have made the withdrawal from your Current Account successfully");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "You have not chosen your account type.Choose the right button.");
				}

             }
        }
    }
}

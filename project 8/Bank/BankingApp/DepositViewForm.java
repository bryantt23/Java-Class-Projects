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
public class DepositViewForm extends JFrame
{
    private final String MSG = "Enter the amount to make a deposit ";
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
	private SavingsAccount mySav;
	private CurrentAccount myCur;

    private JRadioButton savAccountButton;
    private JRadioButton curAccountButton;
    private ButtonGroup actionButtonGroup;

    private JLabel messageLabel;
   	private JLabel dummyLabel1;
   	private JLabel dummyLabel2;
   	private JLabel dummyLabel3;

    private JTextField fullNamefield;

	private String custID;

	private Customer myCust;
    public DepositViewForm(String ID)
    {


        // Set the title bar text.
        setTitle("Making a Deposit");
        JFrame.setDefaultLookAndFeelDecorated(true);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		custID = ID;
		//create customer object
		myCust = new Customer(ID) ;

		//create all needed GUI object
    	messageLabel = new JLabel("Choose account to make deposit ");
		fullNamefield = new JTextField(20);
        fullNamefield.setEditable(false);
        fullNamefield.setText(myCust.getfullName());

		dummyLabel2 = new JLabel();
		dummyLabel3 = new JLabel();


        savAccountButton = new JRadioButton("Savings Account") ;
        curAccountButton = new JRadioButton("Current Account");

        actionButtonGroup = new ButtonGroup();
        actionButtonGroup.add(savAccountButton);
        actionButtonGroup.add(curAccountButton);

        RadioButtonHandler handler = new RadioButtonHandler();
        savAccountButton.addItemListener( handler );
        curAccountButton.addItemListener( handler );


        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(6,2));

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
		getContentPane().add(savAccountButton);
        getContentPane().add(dummyLabel2);
        getContentPane().add(curAccountButton);
        getContentPane().add(dummyLabel3);
        getContentPane().add(welcomelabel);
        getContentPane().add(depositBalfield);
        getContentPane().add(dummylabel);
        getContentPane().add(commitButton);
        getContentPane().add(balanceLabel);
        getContentPane().add(currentBalfield);
		
        commitButton.addActionListener(new ExecuteButtonListener());

        setVisible(true);
    }

	//get the customer id
	public String getCustomerID()
	{
		return custID;
	}

	/**
		register events to radio buttons
	*/
    private class RadioButtonHandler implements ItemListener 
    {
        
        
        public void itemStateChanged( ItemEvent e )
        {
            if ( e.getSource() == savAccountButton )
            {
                ACCTYPE = 1;
            }            
            else if ( e.getSource() == curAccountButton) 
            {
                ACCTYPE = 2;
            } 
        }
    }

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

				if(ACCTYPE == 1)
				{
					//savings account
					currentBalfield.setText("");
					/**
						create savings account
					*/
					mySav = new SavingsAccount(getCustomerID());
					mySav.deposit(amount);
					currentBalfield.setText(Double.toString(mySav.getBalance()));
					JOptionPane.showMessageDialog(null, "You have made the deposit to your Savings Account successfully");
				}else if(ACCTYPE == 2)
				{
					//current account
					currentBalfield.setText("");
					/**
						create current account
					*/
					myCur = new CurrentAccount(getCustomerID()) ;
					myCur.deposit(amount);
					currentBalfield.setText(Double.toString(myCur.getBalance()) );
					JOptionPane.showMessageDialog(null, "You have made the deposit to your Current Account successfully");
				}
				else
				{
					JOptionPane.showMessageDialog(null, "You have not chosen your account type.Choose the right button.");
				}

             }
        }
    }
}

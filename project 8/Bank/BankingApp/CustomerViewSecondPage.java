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
public class CustomerViewSecondPage extends JFrame
{
    private final String MSG = "You are current viewing your account with details";

    private JLabel firstmessage1;
    private JLabel firstmessage2;
    private JLabel firstmessage3;

    private JTextField customernamefield;
    private JTextField accountnumberfield;

    private JLabel message1;
    private JPanel firstPanel;

    private JPanel secondPanel;
    private JLabel secondmessage1;
    private JRadioButton currentBalButton;
    private JRadioButton withdrawalButton;
    private JRadioButton viewtransactionButton;
    private JRadioButton passChangeButton;
    private JRadioButton makTransferButton;
    private ButtonGroup actionButtonGroup;

    private JButton balanbutton;
    private JButton withbutton;
    private JButton viewtransbutton;
    private JButton passWordChange;
    private JButton makTransButton;



    private BalanceViewForm balanceForm;
    private WithdrawalViewForm withDrawForm;
	private DisplayGenericTable bankStat;
	private PasswordChangeForm myPassChange;
	private TransferForm myTransfer;

    private JLabel dummyLabel1;
	private JPanel upperPanel;
    private JLabel dummyLabel2;


    private final int WINDOW_WIDTH = 700;   // Window width
    private final int WINDOW_HEIGHT = 350;  // Window height

	private String custID;
	private int accType;
	private Customer myCust;
/*
				accType = 1   current account
				accType = 2  Saving account
*/

    public CustomerViewSecondPage(String ID, int Type)
    {


        // Set the title bar text.
        setTitle("Login Success second Page ");
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(2,1));

		custID = ID; 
		accType = Type;


        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		//create needed buttons
        balanbutton = new JButton();
        withbutton = new JButton();
        viewtransbutton = new JButton();
		passWordChange = new JButton();
		makTransButton = new JButton();

		//create customer object
		myCust = new Customer(ID);


		dummyLabel1 = new JLabel();
		dummyLabel1.setVisible(false);

		//create needed GUI objects
        firstPanel = new JPanel();
        firstPanel.setLayout(new GridLayout(3,2));
        firstmessage1 = new JLabel(MSG);
        firstmessage2 = new JLabel("Customer Name");
        customernamefield = new JTextField(40);
        customernamefield.setText(myCust.getfullName());
        customernamefield.setEditable(false);
        firstmessage3 = new JLabel("Account number");
        accountnumberfield = new JTextField(30);
        accountnumberfield.setText(getAccountNo());
        accountnumberfield.setEditable(false);
        firstPanel.add(firstmessage1);
        firstPanel.add(dummyLabel1);
        firstPanel.add(firstmessage2 );
        firstPanel.add(customernamefield);
        firstPanel.add(firstmessage3);
        firstPanel.add(accountnumberfield);


        secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(6,2));
		dummyLabel2 = new JLabel();
		dummyLabel2.setVisible(false);
        secondmessage1 = new JLabel("what do you want to do ");
        currentBalButton = new JRadioButton("View current balance ") ;
        withdrawalButton = new JRadioButton("make withdrawal");
        viewtransactionButton = new JRadioButton("view transaction");
		passChangeButton = new JRadioButton("make Password change");
		makTransferButton = new JRadioButton("make Transfer");

        actionButtonGroup = new ButtonGroup();
        actionButtonGroup.add(currentBalButton);
        actionButtonGroup.add(withdrawalButton);
        actionButtonGroup.add(viewtransactionButton);
        actionButtonGroup.add(passChangeButton);
        actionButtonGroup.add(makTransferButton);

        secondPanel.add(secondmessage1);
        secondPanel.add(dummyLabel2);
        secondPanel.add(currentBalButton);
        secondPanel.add(balanbutton);

        secondPanel.add(withdrawalButton);
        secondPanel.add(withbutton);
        secondPanel.add(viewtransactionButton);
        secondPanel.add(viewtransbutton);

        secondPanel.add(passChangeButton);
        secondPanel.add(passWordChange);

        secondPanel.add(makTransferButton);
        secondPanel.add(makTransButton);




         // register events

        RadioButtonHandler handler = new RadioButtonHandler();
        currentBalButton.addItemListener( handler );
        withdrawalButton.addItemListener( handler );
        viewtransactionButton.addItemListener( handler );
		passChangeButton.addItemListener( handler );
		makTransferButton.addItemListener( handler );

        balanbutton.addActionListener(new ExecuteButtonListener());
        withbutton.addActionListener(new ExecuteButtonListener());
        viewtransbutton.addActionListener(new ExecuteButtonListener());
		passWordChange.addActionListener(new ExecuteButtonListener());
		makTransButton.addActionListener(new ExecuteButtonListener());
		

		/**
		set the buttons to invisible as there is no need for the user to click the buttons as the buttons are already clicked
		*/
        balanbutton.setVisible(false);
        withbutton.setVisible(false);
        viewtransbutton.setVisible(false);
		passWordChange.setVisible(false);	
		makTransButton.setVisible(false);	

        getContentPane().add(firstPanel);    
        getContentPane().add(secondPanel);

        setVisible(true);
    }

	public String getCustomerID()
	{
		return custID;
	}

	public int getAccType()
	{
		return accType;
	}

	public String getAccountNo()
	{
	/*
					accType = 1   current account
					accType = 2  Saving account
	*/
		String accNo;
		if(getAccType() == 1)
		{
			accNo = getCustomerID() + "CUR";
		}
		else
		{
			accNo = getCustomerID() + "SAV";
		}
		return accNo;			
	}

	/**
		register events to radio buttons
	*/
    private class RadioButtonHandler implements ItemListener 
    {

        public void itemStateChanged( ItemEvent e )
        {
            if ( e.getSource() == currentBalButton )
            {
                balanbutton.doClick();
            }             
            else if ( e.getSource() == withdrawalButton )
            {
                withbutton.doClick();
            }            
            else if ( e.getSource() == viewtransactionButton) 
            {
                viewtransbutton.doClick();
            } 
			else if(e.getSource() == passChangeButton)
			{
				passWordChange.doClick();
			}
			else if(e.getSource() == makTransferButton)
			{
				makTransButton.doClick();
			}
        }
    }

	/**
		register events to button
	*/

    private class ExecuteButtonListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
             // Determine which button was clicked and display
             // a message.
             //Customer myCustomer = new Customer() ;
            if ( e.getSource() == balanbutton )
            {
/*
				accType = 1   current account
				accType = 2  Saving account
*/
				try
				{
					/**
					create balanceform object and make all other object invisible.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
				    balanceForm = new  BalanceViewForm(getCustomerID(),getAccType());
					withDrawForm.setVisible(false);	
					//withDrawForm.dispose();	

					bankStat.setVisible(false);	
					//bankStat.dispose();

					myPassChange.setVisible(false);	
					//myPassChange.dispose();

					myTransfer.setVisible(false);	
					//myTransfer.dispose();
				}catch (NullPointerException h)
				{}
		
            }            
            else if ( e.getSource() == withbutton )
            {
				try
				{
					/**
					create withdrawform object and make all other object invisible.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
					withDrawForm = new WithdrawalViewForm(getCustomerID(),getAccType());
					balanceForm.setVisible(false);
					//balanceForm.dispose();

					bankStat.setVisible(false);
					//bankStat.dispose();

					myTransfer.setVisible(false);
					//myTransfer.dispose();

					myPassChange.setVisible(false);
					//myPassChange.dispose();
				}catch (NullPointerException h)
				{}

            }            
            else if ( e.getSource() == viewtransbutton) 
            {
				String title,strQuery;
				int custID;
				try
				{
					if(getAccType() == 1)
					{
						/**
							create DisplayGenericTable object for displaying account balance
						*/
						title = "Display Current Account Bank Statement";
						custID = Integer.parseInt(getCustomerID());
						strQuery = "SELECT * FROM CURRENTACCOUNT WHERE custID='"+custID+"' ";				
						bankStat = new DisplayGenericTable(title, strQuery);
					}
					else if(getAccType() == 2)
					{
						/**
							create DisplayGenericTable object for displaying account balance
						*/
						title = "Display Savings Account Bank Statement";
						custID = Integer.parseInt(getCustomerID());
						strQuery = "SELECT * FROM SAVINGACCOUNT WHERE custID='"+custID+"' ";				
						bankStat = new DisplayGenericTable(title, strQuery);
					}

					withDrawForm.setVisible(false);
					//withDrawForm.dispose();

					balanceForm.setVisible(false);
					//balanceForm.dispose();

					myTransfer.setVisible(false);
					//myTransfer.dispose();

					myPassChange.setVisible(false);
					//myPassChange.dispose();

				}catch (NullPointerException h)
				{}
            } 
			else if( e.getSource() == passWordChange)
			{
				try
				{
					/**
					create PasswordChangeForm object and make all other object invisible.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
					myPassChange = new PasswordChangeForm(getCustomerID());
					withDrawForm.setVisible(false);
					//withDrawForm.dispose();

					balanceForm.setVisible(false);
					//balanceForm.dispose();

					bankStat.setVisible(false);
					//bankStat.dispose();

					myTransfer.setVisible(false);
					//myTransfer.dispose();
				}catch (NullPointerException h)
				{}
			}
			else if( e.getSource() == makTransButton)
			{
				try
				{
					myTransfer = new TransferForm(getCustomerID(),getAccType());
					withDrawForm.setVisible(false);
					//withDrawForm.dispose();

					balanceForm.setVisible(false);
					//balanceForm.dispose();

					bankStat.setVisible(false);
					//bankStat.dispose();

					myPassChange.setVisible(false);
					//myPassChange.dispose();
				}catch (NullPointerException h)
				{}
			}

        }
    }

}

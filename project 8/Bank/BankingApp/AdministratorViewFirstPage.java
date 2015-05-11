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

/**
This is the front page where the administrator can shows the action to make on the user
*/
public class AdministratorViewFirstPage extends JFrame
{
    private final String MSG = "You logged-in as administrator.Your details are: ";

    private JLabel firstmessage1;
    private JLabel firstmessage2;
    private JLabel firstmessage3;

    private JTextField employeenamefield;
    private JTextField employeenumberfield;


    private JButton logoutButton;
    private JLabel message1;
    private JPanel firstPanel;

    private JPanel secondPanel;
    private JLabel secondmessage1;
    private JRadioButton createCustomerBalButton;
    private JRadioButton deleteCustomerButton;
    private JRadioButton disableCustomerButton;
    private JRadioButton MakeDepositButton;
    private JRadioButton viewtransactionButton;
    private JRadioButton viewCustomerButton;
    private ButtonGroup actionButtonGroup;
	private DisplayGenericTable customerTabObj; //database tabular object

    private final int WINDOW_WIDTH = 750;   // Window width
    private final int WINDOW_HEIGHT = 300;  // Window height


	/**
		The composition of the classes are AdminCreateCustomerView, DeleteCustomerForm , DisableCustomerForm ,BankStatement,MakeDeposit and Customer.These are required to enable the administrator make action from one frame
	*/
    private AdminCreateCustomerView createCustomerView;
    private DeleteCustomerForm deleteCustomerView;
    private DisableCustomerForm  disableCustomerView;
	private BankStatement myBank;
	private MakeDeposit myform ;
	private Customer myCust;

    private JButton creatCustButton;
    private JButton delCustButton;
    private JButton disCustButton;
    private JButton viewCustButton;
    private JButton makDepButton;
    private JButton custVButton;

	private String custID;

    //TO DO 
    //view transaction to be implemented later
    public AdministratorViewFirstPage(String ID)
    {
        // Set the title bar text.
        setTitle("Adminstrator Login Success first Page ");
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(2,1));
		custID = ID;



        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        firstPanel = new JPanel();
        firstPanel.setLayout(new GridLayout(3,2));
        firstmessage1 = new JLabel(MSG);
        logoutButton = new JButton("log out");
        firstmessage2 = new JLabel("Employee Name");
		myCust = new Customer(getCustomerID());
        employeenamefield = new JTextField(40);
        employeenamefield.setText(myCust.getfullName());
        employeenamefield.setEditable(false);
        firstmessage3 = new JLabel("Employee number");

        employeenumberfield = new JTextField(30);
        employeenumberfield.setText(custID);
        employeenumberfield.setEditable(false);
        firstPanel.add(firstmessage1);
        firstPanel.add(logoutButton);
        firstPanel.add(firstmessage2 );
        firstPanel.add(employeenamefield);
        firstPanel.add(firstmessage3);
        firstPanel.add(employeenumberfield);

        secondPanel = new JPanel();
        secondPanel.setLayout(new GridLayout(7,2));
        secondmessage1 = new JLabel("what do you want to do ");

		/**
			Here is the radio button that the administrator.
		*/
        createCustomerBalButton = new JRadioButton("Create a new customer") ;
        deleteCustomerButton = new JRadioButton("Delete an existing customer");
        disableCustomerButton = new JRadioButton("Disable/enable an Customer");
        viewtransactionButton = new JRadioButton("view bank transaction");
		MakeDepositButton = new JRadioButton("Make a deposit to Customer's Account");
		viewCustomerButton  = new JRadioButton("View all customer");

        creatCustButton = new JButton();
        delCustButton = new JButton();
        disCustButton = new JButton();
        viewCustButton = new JButton();
		makDepButton = new JButton();
		custVButton = new JButton();
		/**
			The buttons are made invisible since it will be automatically clicked when the radio button is clicked so this is not needed by user.
		*/

        creatCustButton.setVisible(false);
        delCustButton.setVisible(false);
        disCustButton.setVisible(false);
        viewCustButton.setVisible(false);
		makDepButton.setVisible(false);
		custVButton.setVisible(false);


        actionButtonGroup = new ButtonGroup();
        actionButtonGroup.add(createCustomerBalButton);
        actionButtonGroup.add(deleteCustomerButton);
        actionButtonGroup.add(disableCustomerButton);
        actionButtonGroup.add(viewtransactionButton);
        actionButtonGroup.add(MakeDepositButton);
        actionButtonGroup.add(viewCustomerButton);

         // register events
        RadioButtonHandler handler = new RadioButtonHandler();
        createCustomerBalButton.addItemListener( handler );
        deleteCustomerButton.addItemListener( handler );
        disableCustomerButton.addItemListener( handler );
        viewtransactionButton.addItemListener( handler );
		MakeDepositButton.addItemListener( handler );
        viewCustomerButton.addItemListener( handler );

        secondPanel.add(secondmessage1);
        secondPanel.add(creatCustButton);

        secondPanel.add(createCustomerBalButton);
        secondPanel.add(delCustButton);

        secondPanel.add(deleteCustomerButton);
        secondPanel.add(disCustButton);

        secondPanel.add(disableCustomerButton);
        secondPanel.add(viewCustButton);

        secondPanel.add(viewtransactionButton);
        secondPanel.add(makDepButton);

        secondPanel.add(MakeDepositButton);

        secondPanel.add(custVButton);

        secondPanel.add(viewCustomerButton);


		/**
			add events to the button
		*/
        creatCustButton.addActionListener(new ExecuteButtonListener());
        delCustButton.addActionListener(new ExecuteButtonListener());
        disCustButton.addActionListener(new ExecuteButtonListener());
        viewCustButton.addActionListener(new ExecuteButtonListener());
		logoutButton.addActionListener(new ExecuteButtonListener());
		makDepButton.addActionListener(new ExecuteButtonListener());
		custVButton.addActionListener(new ExecuteButtonListener());


		/**
			add the component to the frame
		*/
        getContentPane().add(firstPanel);    
        getContentPane().add(secondPanel);


        setVisible(true);
    }

	public String getCustomerID()
	{
		return custID;
	}

	/**
		The radio buttons automatically click the Jbutton once the radio button is clicked.
	*/
    private class RadioButtonHandler implements ItemListener 
    {

        public void itemStateChanged( ItemEvent e )
        {
            if ( e.getSource() == createCustomerBalButton )
            {
                creatCustButton.doClick();
            }            
            else if ( e.getSource() == deleteCustomerButton) 
            {
                delCustButton.doClick();
            } 
            else if ( e.getSource() == disableCustomerButton )
            {
                disCustButton.doClick();
            }            
            else if ( e.getSource() == viewtransactionButton) 
            {
				viewCustButton.doClick();
            } 
            else if ( e.getSource() == MakeDepositButton) 
            {
                makDepButton.doClick();
            } 
            else if ( e.getSource() == viewCustomerButton) 
            {
                custVButton.doClick();
            }
        }
    }

	/**
		Create the event for the button. The appropriate object is created when the radio button is clicked.
		However,since the object created are frames we could make the appropriate object frame visible at a time.
	*/

    private class ExecuteButtonListener implements ActionListener
    {
        @SuppressWarnings("unchecked")
        public void actionPerformed(ActionEvent e)
        {
            if ( e.getSource() == creatCustButton)
            {
				try
				{
					/**
					create customer object and make all other object invisible.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
		            createCustomerView = new AdminCreateCustomerView();
					disableCustomerView.setVisible(false);
					//disableCustomerView.dispose();


					deleteCustomerView.setVisible(false);
					//deleteCustomerView.dispose();


					myBank.setVisible(false);
					//myBank.dispose();


					myform.setVisible(false);
					//myform.dispose();


					customerTabObj.setVisible(false);  
					//customerTabObj.dispose();
				}catch(NullPointerException b)
				{}
            }            
            else if ( e.getSource() == delCustButton) 
            {
				try
				{
					/**
					create delete customer object and make all other object invisible.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
		            deleteCustomerView = new DeleteCustomerForm();
		            createCustomerView.setVisible(false);
		            //createCustomerView.dispose();

					disableCustomerView.setVisible(false);
					//disableCustomerView.dispose();


					myBank.setVisible(false);
					//myBank.dispose();

					myform.setVisible(false);
					//myform.dispose();

					customerTabObj.setVisible(false);  
					//customerTabObj.dispose();
				}catch(NullPointerException b)
				{}
            } 
            else if ( e.getSource() == disCustButton )
            {
				try
				{
					/**
					disable customer object and make all other object invisible.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
		            disableCustomerView = new DisableCustomerForm();
		            createCustomerView.setVisible(false);
		            //createCustomerView.dispose();

					deleteCustomerView.setVisible(false);
					//deleteCustomerView.dispose();

					myBank.setVisible(false);
					//myBank.dispose();

					myform.setVisible(false);
					//myform.dispose();

					customerTabObj.setVisible(false);
					//customerTabObj.dispose(); 
				}catch(NullPointerException b)
				{}
            }            
            else if ( e.getSource() == viewCustButton ) 
            {
				try
				{
					/**
					make a bank overall summary object and make all other object invisible.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
					myBank = new BankStatement(); 
		            disableCustomerView.setVisible(false);
		            //disableCustomerView.dispose();

		            createCustomerView.setVisible(false);
		            //createCustomerView.dispose();

					deleteCustomerView.setVisible(false);
					//deleteCustomerView.dispose();

					myform.setVisible(false);
					//myform.dispose();

					customerTabObj.setVisible(false);
					//customerTabObj.dispose(); 
				}catch(NullPointerException b)
				{}          
            } 
            else if ( e.getSource() == makDepButton ) 
            {
				try
				{
					/**
					make a deposit object and make all other object invisible.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
					myform = new MakeDeposit();  
		            disableCustomerView.setVisible(false); 
		            //disableCustomerView.dispose();  

		            createCustomerView.setVisible(false); 
		            //createCustomerView.dispose();

					deleteCustomerView.setVisible(false); 
					//deleteCustomerView.dispose();

					myBank.setVisible(false); 
					//myBank.dispose();

					customerTabObj.setVisible(false); 
					//customerTabObj.dispose();
				}catch(NullPointerException b)
				{}             
            }
            else if ( e.getSource() == custVButton ) 
            {
				try
				{
					/**
						create a savings account bank statement.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
					String DEFAULT_QUERY="SELECT ID, firstname,lastname,address,phone,update_at,create_at,type  FROM CUSTOMER";
					String title = "Total Customer Details";
					customerTabObj = new DisplayGenericTable(title,DEFAULT_QUERY);
					myform.setVisible(false); 
					//myform.dispose(); 

		            disableCustomerView.setVisible(false); 
		            //disableCustomerView.dispose();
 
		            createCustomerView.setVisible(false); 
		            //createCustomerView.dispose();

					deleteCustomerView.setVisible(false); 
					//deleteCustomerView.dispose();

					myBank.setVisible(false); 
					//myBank.dispose();
				}catch(NullPointerException b)
				{}
			} 
            else if ( e.getSource() == logoutButton ) 
            {
				Login.login("out");
				JOptionPane.showMessageDialog(null, "You have successfully logged out of our bank");
			}
        }
    }

}

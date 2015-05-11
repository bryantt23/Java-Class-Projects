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
This class is used to create the customers needed for the bak
*/


public class AdminCreateCustomerView extends JFrame
{
	/**
		The fields contains customer object and GUI artifact.
		dummy labels are used to position the GUI on the application.
	*/
	private Customer myCustObj;
	private String custID;
    private JLabel firstnameLabel;
    private JLabel lastnameLabel;
    private JLabel passwordLabel;
    private JLabel addressLabel;
    private JLabel emailLabel;
    private JLabel phoneLabel;
    private JLabel ssnLabel;

    private JPanel leftPanel;

    private JTextField firstnameField;
    private JTextField lastnameField;
    private JTextField passwordField;
    private JTextArea addressField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField ssnField;

    private JLabel UserTypeLabel;
    private JLabel dummyLabel1;
    private JButton updateUserButton;
    private JButton newUserButton;


    private JLabel statusLabel;
    private JLabel dummyLabel3;

    private JRadioButton enableUserButton;
    private JRadioButton disableUserButton;

    private ButtonGroup accountGroup;

    private JRadioButton adminButton;
    private JRadioButton customerButton;

    private ButtonGroup staffGroup;

    private JPanel rightPanel;
    private final int WINDOW_WIDTH = 750;   // Window width
    private final int WINDOW_HEIGHT = 400;  // Window height

    //private CustomerAccount myAccount;
    private String USERTYPE ;
    private String STATUS ;
    private JFrame frame;

	/*******************************
		USERTYPE = 1 --> administrator
		USERTYPE = 0 --> customer
		STATUS = 0   --> enable
		STATUS = 1   --> disable  
	*******************************/

    public AdminCreateCustomerView()
    {

        // Set the title bar text.
        setTitle("Adminstrator creating a customer");
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(1,2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        firstnameLabel = new JLabel("First Name");
        lastnameLabel = new JLabel("Last Name");
        passwordLabel = new JLabel("Default Password");
        addressLabel = new JLabel("Address");
        emailLabel = new JLabel("Email");
        phoneLabel = new JLabel("Phone");
		ssnLabel = new JLabel("SSN e.g 247844-1635");

        firstnameField = new JTextField(15);
        lastnameField = new JTextField(15);
        passwordField = new JTextField(15);
		addressField= new JTextArea(6, 20);
        emailField = new JTextField(60);
        phoneField = new JTextField(25);
		ssnField = new JTextField(25);

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7,2));
      	leftPanel.setBorder(BorderFactory.createTitledBorder("User Info Page"));
        leftPanel.add(firstnameLabel);
        leftPanel.add(firstnameField);
        leftPanel.add(lastnameLabel);
        leftPanel.add(lastnameField);
        leftPanel.add(passwordLabel);
        leftPanel.add(passwordField);
        leftPanel.add(emailLabel);
        leftPanel.add(emailField);
        leftPanel.add(phoneLabel);
        leftPanel.add(phoneField);
    	leftPanel.add(ssnLabel);
    	leftPanel.add(ssnField);
        leftPanel.add(addressLabel);
        JScrollPane scrollingArea = new JScrollPane(addressField);
		addressField.setLineWrap(true);
		addressField.setWrapStyleWord(true);
        leftPanel.add(scrollingArea);


        UserTypeLabel = new JLabel("Type of Customer");
        dummyLabel1 = new JLabel();

        newUserButton = new JButton("New User");
		updateUserButton = new JButton("Update User");

        dummyLabel1.setVisible(false);

        adminButton = new JRadioButton("administrator/staff");
        customerButton = new JRadioButton("customer");
        staffGroup = new ButtonGroup();
        staffGroup.add(adminButton);
        staffGroup.add(customerButton);

        statusLabel = new JLabel("Enable / Disable Customer");
        dummyLabel3 = new JLabel();

        dummyLabel3.setVisible(false);

        enableUserButton = new JRadioButton("Enable");
        disableUserButton = new JRadioButton("Disable");

        accountGroup = new ButtonGroup();
        accountGroup.add(enableUserButton);
        accountGroup.add(disableUserButton);


        RadioButtonActionHandler handler1 = new RadioButtonActionHandler();
        enableUserButton.addItemListener( handler1 );
        disableUserButton.addItemListener( handler1 );


        newUserButton.addActionListener(new newUserButtonListener());
		updateUserButton.addActionListener(new newUserButtonListener());
         // register events
        RadioButtonHandler handler = new RadioButtonHandler();
        adminButton.addItemListener( handler );
        customerButton.addItemListener( handler );
         


        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5,2));
      	// Add a border around the panel.
      	rightPanel.setBorder(BorderFactory.createTitledBorder("User Info contd..."));
        rightPanel.add(UserTypeLabel);
        rightPanel.add(adminButton);
        rightPanel.add(dummyLabel1);
        rightPanel.add(customerButton);

        rightPanel.add(statusLabel);
        rightPanel.add(enableUserButton);

        rightPanel.add(dummyLabel3);
        rightPanel.add(disableUserButton);



        rightPanel.add(updateUserButton);
        rightPanel.add(newUserButton);


        getContentPane().add(leftPanel); 
        getContentPane().add(rightPanel); 


        setVisible(true);
        
    }

	//event for buttons
    private class newUserButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
			 String ussn = ssnField.getText() ;
			 String ufirst = firstnameField.getText();
			 String ulast = lastnameField.getText();
			 String uaddress = addressField.getText();
			 String uphone = phoneField.getText();
			 String uemail = emailField.getText();
			 String upassword = passwordField.getText();
			 String username;

             // Determine which button was clicked and display
             // a message.
			 if((STATUS == null) || (USERTYPE == null))
			 {
				//The STATUS and USERTYPE variable are checked for null to ensure that it can be clicked
				JOptionPane.showMessageDialog(null, "You have to click the 'Type of customer button' or 'Enable / Disable Customer button'");
			 }

			 /**
				This is a validation of the textfield to ensure that there no empty boxes
			 */
		     if((ussn.length() > 0 )&& ( ufirst.length() > 0) && (ulast.length() > 0) && (uaddress.length() > 0) && (uphone.length() > 0) && (uemail.length() > 0) && (upassword.length() > 0))
			 {
				     if (e.getSource() == newUserButton )
				     {
						if((Validator.isEmailValid(uemail)) || (Validator.isSSNValid(ussn)) || (Validator.isPhone(uphone)))
						{
							try
							{
								myCustObj = new Customer(ussn ,ufirst, ulast,uaddress, uphone, uemail, upassword, STATUS, USERTYPE);
								username = myCustObj.getUserName();
								if(username.length() == 0)
								{
									JOptionPane.showMessageDialog(null,"User was not created" );
								}
								else
								{
									JOptionPane.showMessageDialog(null,"A new user has been created with the following detail"+"\nUsername: "+myCustObj.getUserName() + "\nPassword: " + upassword );
								}
							}catch(NullPointerException vv)
							{}
							catch(ArrayIndexOutOfBoundsException bb)
							{}
						}


				     }
					 else if (e.getSource() == updateUserButton) 
					 {
						// update user information
						/**
							if the user clicks the new user button.A customer is created once.We cannot create the same user twice.We can update the user using the update user button.
						*/
						if((Validator.isEmailValid(uemail)) || (Validator.isSSNValid(ussn)) || (Validator.isPhone(uphone)))
						{
							try
							{
								myCustObj.upDateCustomerFirstName(ufirst);
								myCustObj.upDateCustomerLastName(ulast);
								myCustObj.upDateCustomerAddress(uaddress);
								myCustObj.upDateCustomerPhone(uphone);
								myCustObj.upDateCustomerEmail(uemail);
								myCustObj.upDateCustomerPassword(upassword);
								myCustObj.upDateCustomerStatus(STATUS);
								myCustObj.upDateCustomerSSN(ussn);
							
								username = myCustObj.getUserName();
								if(username.length() == 0)
								{
									JOptionPane.showMessageDialog(null,"User was not created" );
								}
								else
								{
									JOptionPane.showMessageDialog(null,"A new user has been updated with the following detail"+"\nUsername: "+myCustObj.getUserName() + "\nPassword: " + upassword );
								}
							}catch(NullPointerException vv)
							{}
							catch(ArrayIndexOutOfBoundsException bb)
							{
									JOptionPane.showMessageDialog(null,"Generated username is too long!!" );
							}
						}
					 }
			 }
			else
			{
				/**
					when there is an error in the textfield.The textfields will show missing.If the textfield is not filled
				*/
				JOptionPane.showMessageDialog(null, "You need to provide the following Data with missing tag"+ "\nSocial security number: "+((ussn.length() == 0 )? "Missing": "Available") + "\nFirst name: "+ ((ufirst.length() == 0)?"Missing": "Available") + "\nLast name: "+ ((ulast.length() == 0)?"Missing": "Available") + "\nAddress: "+((uaddress.length() == 0)?"Missing": "Available") + "\nPhone: "+((uphone.length() == 0)?"Missing": "Available") + "\nEmail: "+((uemail.length()== 0)?"Missing": "Available") +"\nPassword: " +((upassword.length() == 0)?"Missing": "Available") ); 
			}
        }


      }

	/**
		The radio button is used to initial the variables when it is needed
	*/

    private class RadioButtonHandler implements ItemListener 
    {
        public void itemStateChanged( ItemEvent e )
        {
			try
			{
		        if ( e.getSource() == adminButton )
				{
		           USERTYPE = "1"; 
				}               
		        else if ( e.getSource() == customerButton)
				{
		           USERTYPE = "0";  
				}
			}catch (NullPointerException b)
			{} 
        }
    }

	/**
		The radio button is used to initial the variables when it is needed
	*/

    private class RadioButtonActionHandler implements ItemListener 
    {
        public void itemStateChanged( ItemEvent e )
        {
			try
			{
				if ( e.getSource() == enableUserButton )
				{
					STATUS = "0";  
		        }             
				else if ( e.getSource() == disableUserButton)
				{
					STATUS = "1";  
				}
			}catch (NullPointerException b)
			{} 
        }
    }


	public static void main(String [] args)
	{
		new AdminCreateCustomerView();
	}
}


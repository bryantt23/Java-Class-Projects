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

public class CustomerViewFirstPage extends JFrame
{
    private final String MSG = "Welcome to Kenneth Bank!!!\nWhich of your account do you want to access...";
    private JButton sButton; //set this button invisible
    private JButton cButton; //set this button invisible
    private JRadioButton currentButton;
    private JRadioButton savingButton;
    private JLabel welcomeMessageLabel;
    private ButtonGroup accountButtonGroup;
    private final int WINDOW_WIDTH = 650;   // Window width
    private final int WINDOW_HEIGHT = 200;  // Window height


    private JButton logoutButton;
    private JLabel dummyLabel1;
    private JLabel dummyLabel2;
    private JLabel dummyLabel3;
    private JLabel dummyLabel4;
    private JLabel dummyLabel5;

	private String custID;
	private int accType;
	private CustomerViewSecondPage mypage;


/*
				accType = 1   current account
				accType = 2  Saving account
*/

    public CustomerViewFirstPage(String ID)
    {
        // Set the title bar text.
        setTitle("Login Success Page");
        JFrame.setDefaultLookAndFeelDecorated(true);
		custID = ID;

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		logoutButton = new JButton("Log out");
		dummyLabel1 = new JLabel();
		dummyLabel2 = new JLabel();
		dummyLabel3 = new JLabel();
		dummyLabel4 = new JLabel();
		dummyLabel5 = new JLabel();
		Box boxNorth =  Box.createHorizontalBox(); //create horizontal box
		boxNorth.add( logoutButton );
		boxNorth.add( dummyLabel1 );
		boxNorth.add( dummyLabel2 );
		boxNorth.add( dummyLabel3 );
		boxNorth.add( dummyLabel4 );
		boxNorth.add( dummyLabel5 );


        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(6,1));

        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        currentButton = new JRadioButton("Current Account");
        savingButton = new JRadioButton("Saving Account");
        sButton = new JButton(); //set this button invisible
        cButton= new JButton(); //set this button invisible

        accountButtonGroup = new ButtonGroup();
        accountButtonGroup.add(currentButton);
        accountButtonGroup.add(savingButton);

        welcomeMessageLabel = new JLabel(MSG);

        getContentPane().add(boxNorth);
        getContentPane().add(welcomeMessageLabel);    
        getContentPane().add(currentButton);
        getContentPane().add(savingButton);
        getContentPane().add(sButton);
        getContentPane().add(cButton);


        RadioButtonHandler handler = new RadioButtonHandler();
        currentButton.addItemListener( handler );
        savingButton.addItemListener( handler );

        sButton.addActionListener(new ExecuteButtonListener());
        cButton.addActionListener(new ExecuteButtonListener());
		logoutButton.addActionListener(new ExecuteButtonListener());



        sButton.setVisible(false); //set this button invisible
        cButton.setVisible(false); //set this button invisible

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

	/**
		register events to buttons
	*/
    private class ExecuteButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
             // Determine which button was clicked and display
             // a message.
             //Customer myCustomer = new Customer() ;
             if (e.getSource() == sButton)
             {
				mypage = new CustomerViewSecondPage(getCustomerID(),getAccType());
             }
             else if (e.getSource() == cButton)
             {
				mypage = new CustomerViewSecondPage(getCustomerID(),getAccType());
             } 
             else if ( e.getSource() == logoutButton) 
			 {
				Login.login("out");// log out
				JOptionPane.showMessageDialog(null, "You have successfully logged out of our bank");
			 }	
        }
    }

    private class RadioButtonHandler implements ItemListener 
    {
        public void itemStateChanged( ItemEvent e )
        {
            if ( e.getSource() == currentButton )
            {
				accType = 1;
                sButton.doClick();
            }            
            else if ( e.getSource() == savingButton) 
            {
				accType = 2;
                cButton.doClick();
            }  
			
        }
    }

}

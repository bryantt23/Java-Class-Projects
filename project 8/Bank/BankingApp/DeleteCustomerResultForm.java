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

public class DeleteCustomerResultForm extends JFrame
{
    private final String MSG ="The user has the following information";
    private final String MSG_INFO ="Do you want to delete the user with the following";
    private JRadioButton yesButton;
    private JRadioButton noButton;

    private ButtonGroup searchGroup;

    private JLabel welcomeLabel;
    private JLabel lowerwelcomeLabel;
    private JLabel fullNameLabel;

    private JLabel dummy;


    private JTextField fullnameField;

    private JPanel upperPanel;
    private JPanel lowerPanel;

    private JButton deleteButton;

    private final int WINDOW_WIDTH = 750;   // Window width
    private final int WINDOW_HEIGHT = 200;  // Window height

    private boolean DELETESTATUS;
	private String custID;

	private  Customer myCust;

    public DeleteCustomerResultForm(String ID)
    {
        // Set the title bar text.
        setTitle("Adminstrator | Delete the customer ");
        JFrame.setDefaultLookAndFeelDecorated(true);
		custID = ID;

        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(2,1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		//create all needed GUI artifacts
        welcomeLabel = new JLabel(MSG);


        dummy = new JLabel();
        dummy.setVisible(false);

		fullNameLabel = new JLabel("Full Name");

        fullnameField = new JTextField(50);
		fullnameField.setText(getCustomerName());
        fullnameField.setEditable(false);
        upperPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(3,2));
        upperPanel.add(welcomeLabel);
        upperPanel.add(dummy);
        upperPanel.add(fullNameLabel);
        upperPanel.add(fullnameField);


        lowerPanel = new JPanel();
        yesButton = new JRadioButton("Yes");
        noButton = new JRadioButton("No");

        RadioButtonHandler handler = new RadioButtonHandler();
        yesButton.addItemListener( handler );
        noButton.addItemListener( handler );


        deleteButton = new  JButton("Delete Customer");

        searchGroup = new ButtonGroup();
        searchGroup.add(yesButton);
        searchGroup.add(noButton);
        lowerwelcomeLabel = new JLabel(MSG_INFO);
        lowerPanel.setLayout(new GridLayout(4,1));
        lowerPanel.add(lowerwelcomeLabel);
        lowerPanel.add(yesButton);
        lowerPanel.add(noButton);
        lowerPanel.add(deleteButton);

        deleteButton.addActionListener(new ExecuteButtonListener());



        getContentPane().add(upperPanel); 
        getContentPane().add(lowerPanel); 

        setVisible(true);
        
    }
	public String getCustomerId()
	{
		return custID;
	}
	
	public String getCustomerName()
	{
		myCust = new Customer(getCustomerId() );
		return  myCust.getfullName();
	}
	/**
		register events to button
	*/
    private class ExecuteButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
             // Get the action command.
             String actionCommand = e.getActionCommand();

             // Determine which button was clicked and display
             // a message.
             if (actionCommand.equals("Delete Customer"))
             {
                if(DELETESTATUS)
                {
                    //do database query and delete customer
					Query.DeleteCustomer(custID);
					JOptionPane.showMessageDialog(null, "You have deleted customer successfully");
                }
             }
        }
    }

	/**
		register events to radio button
	*/
    private class RadioButtonHandler implements ItemListener 
    {

        public void itemStateChanged( ItemEvent e )
        {         
             // Determine which button was clicked and display
             // a message.
            if ( e.getSource() == yesButton)
            {
                DELETESTATUS = true;
            } 
            else if ( e.getSource() == noButton)
            {
                DELETESTATUS = false;
            }           
        }
    }
}


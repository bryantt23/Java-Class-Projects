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
public class PasswordChangeForm extends JFrame
{
	private JLabel oldPasswordLabel;
	private JLabel newPasswordLabel;
	private JLabel reTypePassordLabel;
	private JLabel dummyLabel;

	private JTextField passwordfield;
	private JTextField newPasswordfield;
	private JTextField reTypePassordfield;
	private JButton saveButton;

    private final int WINDOW_WIDTH = 650;   // Window width
    private final int WINDOW_HEIGHT = 300;  // Window height

	private String custID;
	private Customer myCust; //customer object

    public  PasswordChangeForm(String ID)
    {


        // Set the title bar text.
        setTitle("Password change Page ");
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(4,2));

		custID = ID; 

        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(4,2));

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		oldPasswordLabel = new JLabel("Old Password") ;
		newPasswordLabel = new JLabel("New Password");
		reTypePassordLabel = new JLabel("retype New Password");
		dummyLabel = new JLabel();

		dummyLabel.setVisible(false);

		passwordfield = new JTextField(15);
		newPasswordfield = new JTextField(15);
		reTypePassordfield = new JTextField(15);
		saveButton = new JButton("Save") ;

		saveButton.addActionListener(new ExecuteButtonListener());

        getContentPane().add(oldPasswordLabel);    
        getContentPane().add(passwordfield);
        getContentPane().add(newPasswordLabel);    
        getContentPane().add(newPasswordfield);
        getContentPane().add(reTypePassordLabel);    
        getContentPane().add(reTypePassordfield);
        getContentPane().add(dummyLabel);    
        getContentPane().add(saveButton);

		setVisible(true);
	}

	public String getCustomerID()
	{
		return custID;
	}

    private class ExecuteButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
			/**
				get the current,old and retype password from the textfield.
			*/
			String pass,newpass,retypepass;
			pass = passwordfield.getText();
			newpass = newPasswordfield.getText();
			retypepass = reTypePassordfield.getText();
            if ( e.getSource() == saveButton )
            {
				
				if((pass.length() == 0) || (newpass.length() == 0) || (retypepass.length() == 0))
				{
					//checks if any field is empty
					JOptionPane.showMessageDialog(null, "Some of the fields are empty. Please fill all fields.!!!");
				}
				else
				{
					if(newpass.equals(retypepass))
					{
						//compares the newpass and retype password
						myCust = new Customer(getCustomerID() );
						String username =  myCust.getUserName(); // get username of customer
						/**
							use the username and old password to get the user id and compare this with the current user id
						*/
						String[][] output = Query.getUserLoginDetails(username, pass);
						try
						{
							if(output[0][0].equalsIgnoreCase(getCustomerID()))
							{
								//compare user id with customer id
								myCust.upDateCustomerPassword(newpass);
								JOptionPane.showMessageDialog(null, "Your password has been successfully changed..!!!");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Your old password is not correct.!!!");
							}
						}catch(ArrayIndexOutOfBoundsException h)
						{
							JOptionPane.showMessageDialog(null, "No match was found in database..!!!");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The two repeated password does not match..!!!");
					}
				}
			}
		}
	}
}

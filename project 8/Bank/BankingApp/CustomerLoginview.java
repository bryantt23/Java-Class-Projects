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



public class CustomerLoginview extends JFrame
{
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton clearButton;
    private JTextField usernamefield;
    private JPasswordField passwordfield;
    //private Customer user;
    private final int WINDOW_WIDTH = 400;   // Window width
    private final int WINDOW_HEIGHT = 200;  // Window height

	private BankApplication myBank;

    public CustomerLoginview()
    {
        // Set the title bar text.
        setTitle("Login Page");
        // Set the size of the window.
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add a BorderLayout manager to the content pane.
        setLayout(new GridLayout(3,2));

        // Create 2 labels.
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");
          

        // Create 2 buttons.
        loginButton = new JButton("Login");
        clearButton = new JButton("Clear");

        loginButton.addActionListener(new LoginButtonListener());
        clearButton.addActionListener(new LoginButtonListener());

        usernamefield = new JTextField(20) ;
        passwordfield = new JPasswordField(20) ;

        getContentPane().add(usernameLabel);
        getContentPane().add(usernamefield);
        getContentPane().add(passwordLabel);
        getContentPane().add(passwordfield);
        getContentPane().add(loginButton);
        getContentPane().add(clearButton);         

        setVisible(true);
    }


    private class LoginButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
        	if ( e.getSource() == loginButton)
            {
				/**
					get the password and username
				*/
				String username = usernamefield.getText(); 
				String password = new String(passwordfield.getPassword()) ;

				//call database to log in user
				if((username.length()> 0 )&& (password.length()> 0 ))
				{
					try
					{
		 				String returnVal[][] = Query.getUserLoginDetails(username,  password);
			

						if ((returnVal[0][0].length()> 0 )&&(returnVal[0][1].length()> 0 )&&(returnVal[0][2].length()> 0 ))
						{
							if(returnVal[0][1].equals("0"))
							{
								String custID ,status ,userType;
								custID = returnVal[0][0];
								status = returnVal[0][1];
								userType = returnVal[0][2];
								Login.login("in");  //log in user
								myBank = new BankApplication(custID,status,userType);
								JOptionPane.showMessageDialog(null,"You have been successfully authenticated!!!");
							}
							else if(returnVal[0][1].equals("1"))
							{
								JOptionPane.showMessageDialog(null,"You have been disabled.See administrator!!!");
							}
							else
							{
								JOptionPane.showMessageDialog(null,"Your credentials were not found. Check username or password.Confirm if you have an account!!!");
							}
						}
					}catch(ArrayIndexOutOfBoundsException z)
					{}

				}
				else
				{
					if((username.length() == 0 )|| (password.length()== 0 ))
					{
						JOptionPane.showMessageDialog(null,"Missing Username or password!!!");
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Incorrect username or password!!!");
					}

				}
             }
             else if  ( e.getSource() == clearButton)
             {
                usernamefield.setText("");
                passwordfield.setText("");

             }

      	}
    }

	public static void main(String [] args)
	{
		new CustomerLoginview();
	}

}

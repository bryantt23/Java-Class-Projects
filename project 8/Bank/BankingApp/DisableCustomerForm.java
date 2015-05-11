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

public class DisableCustomerForm extends JFrame
{


    private JLabel welcomeLabel;
    private JLabel dummyLabel1;
    private JLabel dummyLabel2;
    private JLabel message;
    private JTextField inputField;

    private JButton searchButton;

    private final int WINDOW_WIDTH = 750;   // Window width
    private final int WINDOW_HEIGHT = 200;  // Window height

    private DisableCustomerResultForm disCustResForm;
	private  Customer myCust;

    public DisableCustomerForm()
    {
        // Set the title bar text.
        setTitle("Adminstrator | search for customer to disable ");
        JFrame.setDefaultLookAndFeelDecorated(true);


        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(3,2));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);



        welcomeLabel = new JLabel("Do you want to search for user?");
        dummyLabel1 = new JLabel();
        dummyLabel2 = new JLabel();



        dummyLabel1.setVisible(false);
        dummyLabel2.setVisible(false);

        message = new JLabel("Enter SSN e.g 231230-1874");
        inputField = new JTextField(30);
		searchButton = new JButton("Search");

		searchButton.addActionListener(new ExecuteButtonListener());
		//add the elements to the frame

        getContentPane().add(welcomeLabel);
        getContentPane().add(dummyLabel1);
        getContentPane().add(message);
        getContentPane().add(dummyLabel2); 
        getContentPane().add(inputField);
        getContentPane().add(searchButton); 


        setVisible(true);
        
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
             if (actionCommand.equals("Search"))
             {
				String searchWord = inputField.getText();
				//create customer object
				myCust = new Customer() ;
				try
				{
					
					if (searchWord.length() > 0)
					{
						/**
							create DisableCustomerResultForm object.We need to catch nullpointer exception .
						*/
						if(!Validator.canHappenSQLInject(searchWord))
						{
							if(Validator.isSSNValid(searchWord))
							{
								disCustResForm = new DisableCustomerResultForm(myCust.getCustomerIDFromSSN(searchWord)) ;
							}
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "You have not enter your SSN");
					}
				}catch(NullPointerException v)
				{
					//avoid empty box
					JOptionPane.showMessageDialog(null, "not existent in database");
				}catch(ArrayIndexOutOfBoundsException h)
				{
					//avoid out of bounds error when no result is found
					JOptionPane.showMessageDialog(null, "not existent in database");
				}


             }
        }
    }
}

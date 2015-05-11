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
import java.util.Calendar;// Needed for calendar
import java.text.SimpleDateFormat;// Needed for date formatting

/**
This BalanceViewForm is used to display the current account balance
*/
public class BalanceViewForm extends JFrame
{
    private final String MSG = "Your current balance at ";
    private JLabel welcomelabel;
    private JLabel balanceMesgrlabel;
    private JButton BalanceButton;
    private JTextField currentBalfield; //current balance field 
    private JTextField TimeStampfield;

    private final int WINDOW_WIDTH = 500;   // Window width
    private final int WINDOW_HEIGHT = 200;  // Window height

	private String custID;
	private int accType;

	private SavingsAccount mySav;
	private CurrentAccount myCur;

/*
				accType = 1   current account
				accType = 2  Saving account
*/

    public BalanceViewForm(String ID, int Type)
    {
        // Set the title bar text.
        setTitle("Account Balance");
        JFrame.setDefaultLookAndFeelDecorated(true);
		custID = ID; 
		accType = Type;


        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(3,2));

        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        welcomelabel = new JLabel(MSG);
        balanceMesgrlabel = new JLabel("Balance £");
        BalanceButton = new JButton("view Balance");


        currentBalfield = new JTextField(15);
		TimeStampfield = new JTextField(15);

        currentBalfield.setEditable(false);
		TimeStampfield.setEditable(false);
		//add event to button
		BalanceButton.addActionListener(new ExecuteButtonListener());
		//make the button automatically clicked
		BalanceButton.doClick();
		BalanceButton.setVisible(false);
        getContentPane().add(welcomelabel);    
        getContentPane().add(TimeStampfield);
        getContentPane().add(balanceMesgrlabel);
        getContentPane().add(currentBalfield);
        getContentPane().add(BalanceButton);

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

    private class ExecuteButtonListener implements ActionListener
    {
        @SuppressWarnings("unchecked")
        public void actionPerformed(ActionEvent e)
        {
/*
				accType = 1   current account
				accType = 2  Saving account
*/

            if ( e.getSource() == BalanceButton)
            {
				//call the database
				TimeStampfield.setText(GetTimeStamp() );
				if(getAccType() == 1 )
				{
					//create current account object
					myCur = new CurrentAccount(getCustomerID()) ;
					currentBalfield.setText(Double.toString(myCur.getBalance()) );
				}
				else if(getAccType() == 2 )
				{
					//create saving current object
					mySav = new SavingsAccount(getCustomerID());
					currentBalfield.setText(Double.toString(mySav.getBalance()) );
				}
			}
		}
	}

	//create a time stamp
	public String GetTimeStamp() {
        String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
	}
}

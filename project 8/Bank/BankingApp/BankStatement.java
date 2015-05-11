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
	This is used to display the bank statement of account in a tabular form.
*/

public class BankStatement extends JFrame
{
	private DisplayGenericTable bankStat; //database tabular object
    private JRadioButton savingAccButton; //saving account object
    private JRadioButton currentAccButton;//current account object

    private JButton savingButton;
    private JButton currentButton;

    private JTextField totalSavingfield;
    private JTextField totalCurrentfield;
    private JTextField totalOverdraftfield;

    private JLabel SavingLabel;
    private JLabel CurrentLabel;
    private JLabel OverdraftLabel;


    private JLabel firmessage;
    private JLabel secmessage;
    private JLabel thirmessage;

    private final int WINDOW_WIDTH = 750;   // Window width
    private final int WINDOW_HEIGHT = 200;  // Window height

    private ButtonGroup actionButtonGroup;
	public BankStatement()
	{
        // Set the title bar text.
        setTitle("Bank Statement Display ");
        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(6,2));

		totalSavingfield = new JTextField(15);
		totalCurrentfield = new JTextField(15);
		totalOverdraftfield = new JTextField(15);

		totalSavingfield.setText(Query.getTotalCustomerSavAccBalance());
		totalCurrentfield.setText(Query.getTotalCustomerCurAccBalance());
		totalOverdraftfield.setText(Query.getTotalCustomerOverDraftBalance());
		totalSavingfield.setEditable(false);
		totalCurrentfield.setEditable(false);
		totalOverdraftfield.setEditable(false);

		SavingLabel = new JLabel("Savings Account Total Bal: ");
		CurrentLabel = new JLabel("Current Account Total Bal: ");
		OverdraftLabel = new JLabel("Overdraft Sum: ");

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		/**
			create the radio buton to receive users action
		*/
		savingAccButton = new JRadioButton("Total Savings Account");
		currentAccButton = new JRadioButton("Total Current Account");

     	actionButtonGroup = new ButtonGroup();
        actionButtonGroup.add(savingAccButton);
        actionButtonGroup.add(currentAccButton);

         // register events
        RadioButtonHandler handler = new RadioButtonHandler();
        savingAccButton.addItemListener( handler );
        currentAccButton.addItemListener( handler );

		firmessage = new JLabel("You can see total transactions in bank") ;
		secmessage = new JLabel() ;
		thirmessage = new JLabel() ;

		secmessage.setVisible(false);		
		thirmessage.setVisible(false);

		savingButton = new JButton();
		currentButton = new JButton();

		/**
			The button is made invisible as it is not needed to be clicked by the user.
		*/
		savingButton.setVisible(false);		
		currentButton.setVisible(false);

		/**
			register event to button
		*/
        savingButton.addActionListener(new ExecuteButtonListener());
        currentButton.addActionListener(new ExecuteButtonListener());

        getContentPane().add(firmessage);    
        getContentPane().add(secmessage);
        getContentPane().add(savingAccButton);    
        getContentPane().add(savingButton);
        getContentPane().add(currentAccButton);    
        getContentPane().add(currentButton);
		getContentPane().add(SavingLabel);
		getContentPane().add(totalSavingfield);
		getContentPane().add(CurrentLabel);
		getContentPane().add(totalCurrentfield);
		getContentPane().add(OverdraftLabel);
		getContentPane().add(totalOverdraftfield);
        setVisible(true);


	}

	/**
		register radio button
	*/
    private class RadioButtonHandler implements ItemListener 
    {

        public void itemStateChanged( ItemEvent e )
        {
            if ( e.getSource() == savingAccButton)
            {
                savingButton.doClick();
            }            
            else if ( e.getSource() == currentAccButton) 
            {
                currentButton.doClick();
            } 

        }
    }

	/**
		create event for the buttons
	*/
    private class ExecuteButtonListener implements ActionListener
    {
        @SuppressWarnings("unchecked")
        public void actionPerformed(ActionEvent e)
        {
			String DEFAULT_QUERY , title;

            if ( e.getSource() == savingButton)
            {
				try
				{
					/**
						create a savings account bank statement.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
					DEFAULT_QUERY="SELECT * FROM SAVINGACCOUNT";
					title = "Total Savings Account Statement";
					bankStat = new DisplayGenericTable(title,DEFAULT_QUERY);
				}catch(NullPointerException b)
				{}
            }            
            else if ( e.getSource() == currentButton) 
            {
				try
				{
					/**
						create a savings account bank statement.We need to catch nullpointer exception as the object we are making invisible may not have been created at the time when the event is run.
					*/
					DEFAULT_QUERY="SELECT * FROM CURRENTACCOUNT";
					title = "Total Current Account Statement";
					bankStat = new DisplayGenericTable(title,DEFAULT_QUERY);
				}catch(NullPointerException b)
				{}
            } 
        }
    }
}

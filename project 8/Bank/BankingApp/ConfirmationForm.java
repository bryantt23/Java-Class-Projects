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
import java.util.Random;  //random number
public class ConfirmationForm extends JFrame
{
	private JLabel custNameLabel;
	private JLabel securityCodLabel;
	private JLabel confirmCodLabel;


	private JTextField custNamefield;
	private JTextField securityCodfield;
	private JTextField confirmCodfield;


	private JButton confirmButton;
	private JButton activateButton;

    private final int WINDOW_WIDTH = 650;   // Window width
    private final int WINDOW_HEIGHT = 300;  // Window height

	private Customer custObj;

	private String recipientID;

    public ConfirmationForm (String ID)
    {
        // Set the title bar text.
        setTitle("Confirm Transfer form ");
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Add a GridLayout manager to the content pane.
        setLayout(new GridLayout(4,2));


		recipientID = ID;
        // Specify an action for the close button.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(4,2));

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		custNameLabel = new JLabel("Customer name") ;
		securityCodLabel = new JLabel("Security Code") ;
		confirmCodLabel = new JLabel("Confirm Security Code") ;


		custNamefield = new JTextField(20); 
		custNamefield.setEditable(false);
		securityCodfield = new JTextField(8); 
		securityCodfield.setEditable(false);
		confirmCodfield = new JTextField(8); 


		confirmButton = new JButton("Confirm") ;
		activateButton = new JButton() ;
		activateButton.setVisible(false);




		confirmButton.addActionListener(new ExecuteButtonListener());
		activateButton.addActionListener(new RealButtonListener());
		activateButton.doClick();


        getContentPane().add(custNameLabel);    
        getContentPane().add(custNamefield);
        getContentPane().add(securityCodLabel);    
        getContentPane().add(securityCodfield);
        getContentPane().add(confirmCodLabel);    
        getContentPane().add(confirmCodfield);
        getContentPane().add(activateButton);    
        getContentPane().add(confirmButton);

		setVisible(true);
	}



	public int generateRandom()
	{
		Random randOj = new Random();
		return (randOj.nextInt(9999) + 1000);
	}

	public String recipientID()
	{
		return recipientID;
	}

    private class RealButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if ( e.getSource() == activateButton )
            {
				String code = Integer.toString( generateRandom() );
				securityCodfield.setText(code);
			
				custObj = new Customer(recipientID() );
				custNamefield.setText(custObj.getfullName());
			}
		}
	}

    private class ExecuteButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
			String securityCode, confirmCode;
            if ( e.getSource() == confirmButton )
            {		
				try
				{
					securityCode = securityCodfield.getText();
					confirmCode = confirmCodfield.getText();
					if(confirmCode.length() == 0)
					{
						JOptionPane.showMessageDialog(null, "Make sure the confirmation code is filled!!!!!");
					}
					else
					{
						int intSecurityCode = Integer.parseInt(securityCode);
						int intConfirmCode = Integer.parseInt(confirmCode);
						if (intSecurityCode == intConfirmCode )
						{
							Confirm.setStatus("confirmed");
							JOptionPane.showMessageDialog(null, "Click the comit button again!!!!!");
							dispose(); //make this window dissappear automatically
						}
					}
				}catch(NumberFormatException H)
				{
					JOptionPane.showMessageDialog(null, "Incompatible data type.Check security code text fields!!!!!");
				}

			}
		}
	}
/*
	public static void main(String [] args)
	{
		ConfirmationForm myobj = new ConfirmationForm ("2");
	}
*/

}

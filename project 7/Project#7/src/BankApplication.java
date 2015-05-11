//Folder/Project Name: Project#7
//Programmer Name: Bryant Tunbutr

//Date: May 24, 2013
//Class Name: BankApplication
/*Project Description: This car bank project uses a GUI to 
 * input the customer name, pin number and transaction amount,
 * along with buttons and radio buttons
 * for processing and display.
 * There is a GUI class, an accounts(calculation) class,
 * 
 * This is the GUI class
*/

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;		//used for the swing components
import java.awt.event.*;	//used for the ActionListener
import java.text.*;			//used for the DecimalFormat class
import java.util.*;

public class BankApplication<Account> extends JFrame 
implements ActionListener
{	
	//labels
	JLabel companyLabel = new JLabel("Friendly Neighborhood Bank");
	JLabel iconLabel;

	//panels
	JPanel enterPanel = new JPanel();	
	JPanel doTransactionsPanel = new JPanel();
	JPanel newAccountPanel = new JPanel();	
	
	//radio buttons on enter panel
	JRadioButton newAccountRadioButton = new JRadioButton("Create a new account");
	JRadioButton doTransactionsRadioButton = new JRadioButton("Deposit or withdrawal");
	JRadioButton displayAllAccountsRadioButton = new JRadioButton("Display all accounts");
	JRadioButton invisibleRadioButton = new JRadioButton();
	//radio buttons on transactions panel
	JRadioButton depositRadioButton = new JRadioButton("Deposit");
	JRadioButton withdrawalRadioButton = new JRadioButton("Withdrawal");

	//grouped radio buttons
	ButtonGroup transactionsButtonGroup = new ButtonGroup();
	ButtonGroup enterPanelButtonGroup = new ButtonGroup();
	
	//buttons
	JButton addNewAccountButton = new JButton("Add a new account");
	JButton backButton = new JButton("Back");
	JButton backAccountButton = new JButton("Back");		
	JButton processButton = new JButton("Process");
	
	//for add new accounts panel
	JLabel firstNameLabel = new JLabel("First Name: ");
	JLabel lastNameLabel = new JLabel("Last Name: ");		
	JLabel pinLabel = new JLabel("PIN Number: ");
	JTextField firstNameTextField = new JTextField(33);
	JTextField lastNameTextField = new JTextField(33);
	JTextField pinCreateTextField = new JTextField(33);
	
	//for transactions panel
	JTextField pinTransactionsTextField = new JTextField(5);
	JLabel amountLabel = new JLabel("Amount: ");

	// text areas
	JTextArea outputTextArea = new JTextArea(13, 33);
	JTextArea output2TextArea = new JTextArea(13, 33);
	JTextArea outputTransactionsTextArea = new JTextArea(13, 33);
	JTextField amountTextField = new JTextField(5);

	//to format currency strings
	DecimalFormat currency = new DecimalFormat("$,###.00");	
	
	//variables
	boolean validationBoolean; //placed here because used in calculation class 
	boolean transactionsValidationBoolean;  

	int countInteger;
	int countPlusOneInteger = countInteger+1; //used for looping
	int indexArrayInteger; 
    int pinCreatedInteger;
    int pinNumberForTransactionsInteger;

	double amountDouble;
	
	String withdrawalString = "friendly bank";
    String firstNameString, lastNameString, pinCreatedString, pinNumberForTransactionsString;
	
	//This method will create an object of ourselves and set the default close operation
	public static void main(String[] args)
	{
		BankApplication myProgram = new BankApplication();
		myProgram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	//This is the constructor and will add the components to the GUI, 
	//register the listeners, and set the properties of the JFrame
	public BankApplication()
	{			
		//call a method to set up the components
		super("Account");
		
		//constructor 
		//call the methods to add components to the other panels
		//to add the enter panel
		//to add the new accounts panel
		//to add the transactions panel
		//to the frame
		designEnterPanel();
		designNewAccountPanel();
		designDoTransactions();
		
		add(enterPanel);
		setSize(530, 500);
		setVisible(true);		   	

		//add the radio buttons to button group
		transactionsButtonGroup.add(depositRadioButton);
		transactionsButtonGroup.add(withdrawalRadioButton);	

		//add the radio buttons to button group
		enterPanelButtonGroup.add(newAccountRadioButton);
		enterPanelButtonGroup.add(displayAllAccountsRadioButton);		
		enterPanelButtonGroup.add(doTransactionsRadioButton);		
		
		//add the objects to the action listener		
		newAccountRadioButton.addActionListener(this);
		doTransactionsRadioButton.addActionListener(this);
		displayAllAccountsRadioButton.addActionListener(this);
		backButton.addActionListener(this);
		backAccountButton.addActionListener(this);
		addNewAccountButton.addActionListener(this);	
		depositRadioButton.addActionListener(this);		
		withdrawalRadioButton.addActionListener(this);		
		processButton.addActionListener(this);		
	}		

	//create and add components to the enterPanel		
	public void designEnterPanel()
	{		
		//create components to be added to panel
		iconLabel = new JLabel(new ImageIcon("piggy-bank-icon.png"));
    	JLabel programmerNameLabel = new JLabel("\n Maintained by Bryant Tunbutr");
    	Font bigFont = new Font("Times New Roman", Font.BOLD, 34);    					
		companyLabel.setFont(bigFont);		
		companyLabel.setForeground(Color.RED);		
		
		JScrollPane outputScrollPane = new JScrollPane(output2TextArea);  

		JFrame frame = new JFrame();
		frame.setSize(430, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//add components to the enterPanel		
		enterPanel.add(iconLabel);
		enterPanel.add(companyLabel);		
		enterPanel.add(newAccountRadioButton);
		enterPanel.add(doTransactionsRadioButton);
		enterPanel.add(displayAllAccountsRadioButton);
		enterPanel.add(outputScrollPane);
		enterPanel.add(output2TextArea);
    	enterPanel.add(programmerNameLabel);		
	}
	
	//This method will check for which component triggered this method.
	//If the transaction radio button is the trigger, call 
	//the transactions panel, if the back button is pressed, call the enter panel.
	//If the create account radio button is the trigger, call 
	//the create account panel.
	//If the process button is the trigger, call the deposit or withdrawal method
	//based on which radio button was selected.
	//If the display radio button is the trigger, call display method			
	public void actionPerformed(ActionEvent evt)
	{		
	    Object sourceObject = evt.getSource();

	    if(sourceObject == newAccountRadioButton)
	    {
			if(newAccountRadioButton.isSelected())
				{
					invisibleRadioButton.setSelected(true);
					remove(enterPanel);
					add(newAccountPanel);
					setSize(530, 530);
					setVisible(true);
				}
	    }	
	    else if(sourceObject == doTransactionsRadioButton)
	    {
			if(doTransactionsRadioButton.isSelected())
				{
					invisibleRadioButton.setSelected(true);
					remove(enterPanel);
					add(doTransactionsPanel);
					setSize(530, 430);
					setVisible(true);
				}
	    }		    
	    if(sourceObject == addNewAccountButton)
	    {
	    	if(validation())
				//calls to add item to array
	    		addNewAccount();
	    }	    
	    if(sourceObject == displayAllAccountsRadioButton)
	    {
	    	displayAllAccounts();
	    }	    
		if(sourceObject == backAccountButton)
		{
			//return to enterPanel from newAccountPanel
			remove(newAccountPanel);
			add(enterPanel);
			setSize(460, 620);
			setVisible(true);			
		}
		if(sourceObject == backButton)
		{
			//return to enterPanel from doTransactionsPanel
			remove(doTransactionsPanel);
			add(enterPanel);
			setSize(460, 620);
			setVisible(true);			
		}
		if(sourceObject == processButton)
	    {
	    	if(transactionsValidation())
	    		
	    	if(!depositRadioButton.isSelected()	&& !withdrawalRadioButton.isSelected()){
				JOptionPane.showMessageDialog(null, "Please select a transaction type");
				depositRadioButton.requestFocus();
	    	}
	    	else
	    	{	    		
				if(depositRadioButton.isSelected())
					{
						getArrayIndexInteger();
						deposit();			
					}
				if(withdrawalRadioButton.isSelected())
				{
					getArrayIndexInteger();
					withdrawal();			
				}
	    	}	    		
	    }	
	}

	//create and add components to the new account panel		
	public void designNewAccountPanel()
	{
		//create components to be added to account panel		
		//add labels & text areas
		JLabel companyLabel = new JLabel("Friendly Neighborhood Bank");
    	Font bigFont = new Font("Times New Roman", Font.BOLD, 34);    					
		companyLabel.setFont(bigFont);		
		companyLabel.setForeground(Color.RED);		
    	JLabel programmerNameLabel = new JLabel("\n Maintained by Bryant Tunbutr");
		iconLabel = new JLabel(new ImageIcon("piggy-bank-icon.png"));

		//add components to the account panel		
		newAccountPanel.add(iconLabel);
		newAccountPanel.add(companyLabel);
		newAccountPanel.add(firstNameLabel);
		newAccountPanel.add(firstNameTextField);
		newAccountPanel.add(lastNameLabel);
		newAccountPanel.add(lastNameTextField);
		newAccountPanel.add(pinLabel);
		newAccountPanel.add(pinCreateTextField);
		newAccountPanel.add(outputTextArea);
		newAccountPanel.add(addNewAccountButton);
		newAccountPanel.add(backAccountButton);
		newAccountPanel.add(programmerNameLabel);		
	}

	//create and add components to the do transactions			
	public void designDoTransactions()
	{
		//create components to be added to the do transactions
		//add labels & text areas
		JLabel companyLabel = new JLabel("Friendly Neighborhood Bank");
    	Font bigFont = new Font("Times New Roman", Font.BOLD, 34);    					
		companyLabel.setFont(bigFont);		
		companyLabel.setForeground(Color.RED);		
		JLabel pinLabel = new JLabel("PIN Number: ");
    	JLabel programmerNameLabel = new JLabel("\n Maintained by Bryant Tunbutr");
		iconLabel = new JLabel(new ImageIcon("piggy-bank-icon.png"));
		
		//add components to the do transactions		
		doTransactionsPanel.add(iconLabel);
		doTransactionsPanel.add(companyLabel);
		doTransactionsPanel.add(depositRadioButton);
		doTransactionsPanel.add(withdrawalRadioButton);
		doTransactionsPanel.add(pinLabel);
		doTransactionsPanel.add(pinTransactionsTextField);
		doTransactionsPanel.add(amountLabel);
		doTransactionsPanel.add(amountTextField);
		doTransactionsPanel.add(outputTransactionsTextArea);
		doTransactionsPanel.add(processButton);
		doTransactionsPanel.add(backButton);
		doTransactionsPanel.add(programmerNameLabel);		
	}
	
	//This method adds new accounts to the program
	public void addNewAccount()
	{
	//retrieve the input from the  user
    firstNameString = firstNameTextField.getText();
    lastNameString = lastNameTextField.getText();
    pinCreatedString = pinCreateTextField.getText();    
    
    try {
	    pinCreatedInteger = Integer.parseInt(pinCreatedString);

	    //runs method to check for duplicate pin numbers in Accounts Class
		Accounts isThereADuplicatePIN = new Accounts(countPlusOneInteger, pinCreatedInteger);
		
		//display instructions to user
		if (isThereADuplicatePIN.getIsThereADuplicatePIN() == true){
			JOptionPane.showMessageDialog(null, "This PIN number is not available. " +
					"Please enter a different PIN number");}
		
			else{
				//This method set a new account into the Accounts Class 
				Accounts joeAccount = new Accounts(countInteger, pinCreatedInteger, firstNameString, lastNameString);

				//This displays the current information using get methods from the Accounts Class
			    outputTextArea.setText("Name" + '\t' + '\t' + "PIN"+ '\t' +"Balance"+ '\n'+ '\n' +
			    	joeAccount.getFirstName() + " " + joeAccount.getLastName() + '\t' + '\t' + 
			    	joeAccount.getPinNumberString() + '\t' + joeAccount.getBalanceString() + '\n'+ '\n');
			    
			    //increment to keep track of how many accounts have already been created
			    countInteger++;	    			
		}	
    }
    catch(NumberFormatException err)//if non number entered
	{//send message to user to enter in specific text box & place focus in that text box
		JOptionPane.showMessageDialog(null, "Please enter a number for your PIN");
		pinCreateTextField.selectAll();
		pinCreateTextField.requestFocus();				
		}
}

	//This displays all of the current accounts in a text area
	public void displayAllAccounts()
	{		
		//This method sends the current number of accounts to the Accounts Class, then the Accounts Class
		//runs the displayAccounts method
		Accounts displayAccount = new Accounts(countInteger);

		//get the String from the Accounts Class then display in text area
		output2TextArea.append(displayAccount.getDisplayAccountsString());
		}						

	//Finds the array index of the PIN number the user entered. 
	//Either the matching PIN is found and the index is stored as an integer or
	//no matching PIN is found and -1 is stored as the index integer
	public int getArrayIndexInteger()
	 {
		//get PIN number from user
		pinNumberForTransactionsString = pinTransactionsTextField.getText();    
	    
		//convert to an integer
	    pinNumberForTransactionsInteger = Integer.parseInt(pinNumberForTransactionsString);
	    
	    //loop through all of the pin numbers in the pin number array stored in the Accounts Class
	    for (int i = 0; i < Accounts.getPinNumbersArray().length; i++)
	      {
	       if (Accounts.getPinNumbersArray()[i] == pinNumberForTransactionsInteger)
	        {
	    	 //if the user entered PIN matches a PIN stored in array, return the pin number array index integer
	         return(i); 
	        }
	      }
	  //if there is no match, return -1
	  return(-1); 
	 }

	//Processes deposits based on user entry
	public void deposit()
	{
		//get the index based on the entered PIN number
		int indexArrayInteger = getArrayIndexInteger();
		
		//if the pin number does not exist 
		if (indexArrayInteger==-1){
			JOptionPane.showMessageDialog(null, "This PIN number does exist!");				
			pinTransactionsTextField.requestFocus();	
		}
		//if the PIN does exist
		else 
		{
			try
			{
				double amountDouble = Double.parseDouble(amountTextField.getText());	
				
				//Uses Accounts Class to calculate new balance based on deposit amount and index integer from 
				//PIN number entered
				Accounts deposit = new Accounts(amountDouble, indexArrayInteger);

				//get the information from the Accounts Class then display in text area
				outputTransactionsTextArea.append("Name" + '\t' + '\t' + "PIN"+ '\t' +"Trans Type"+ '\t' +"Trans Amt"+ '\t'  +"Balance"+ '\n'+ '\n' +
						Accounts.getFirstNameArrayString()[indexArrayInteger] + " " + Accounts.getLastNameArrayString()[indexArrayInteger] + '\t' + '\t' + 
			    	Accounts.getPinNumbersArray()[indexArrayInteger] + '\t' + "Deposit" + '\t' + currency.format(amountDouble) + '\t' + currency.format(Accounts.getBalancesArray()[indexArrayInteger]) + '\n'+ '\n');		    
		}
		catch(NumberFormatException err)//if non number entered
			{//send message to user to enter in specific text box & place focus in that textbox
			JOptionPane.showMessageDialog(null, "Please enter a number for the amount");
			amountTextField.selectAll();
			amountTextField.requestFocus();				
			}
		}				
	}		

	//Processes withdrawals based on user entry
	public void withdrawal()
	{		
		//get the index based on the entered PIN number
		int indexArrayInteger = getArrayIndexInteger();		
		
		//if the pin number does not exist 
		if (indexArrayInteger==-1){
			JOptionPane.showMessageDialog(null, "This PIN number does exist!");				
			pinTransactionsTextField.requestFocus();
		}
		//if the PIN does exist
		else 
		{
			try
			{
				double amountDouble = Double.parseDouble(amountTextField.getText());	
				
				//Check to see if sufficient balance to do a withdrawal
				if(amountDouble<=Accounts.getBalancesArray()[indexArrayInteger]){
					
					//Uses Accounts Class to calculate new balance based on withdrawal amount and index integer from 				
					//PIN number entered								
					Accounts withdrawal = new Accounts(withdrawalString, amountDouble, indexArrayInteger);					

					//get the information from the Accounts Class then display in text area
					outputTransactionsTextArea.append("Name" + '\t' + '\t' + "PIN"+ '\t' +"Trans Type"+ '\t' +"Trans Amt"+ '\t'  +"Balance"+ '\n'+ '\n' +
							Accounts.getFirstNameArrayString()[indexArrayInteger] + " " + Accounts.getLastNameArrayString()[indexArrayInteger] + '\t' + '\t' + 
				    	Accounts.getPinNumbersArray()[indexArrayInteger] + '\t' + "Withdrawal" + '\t' + currency.format(amountDouble) + '\t' + currency.format(Accounts.getBalancesArray()[indexArrayInteger]) + '\n'+ '\n');	    
				}
				else{
					//display message to user to select a lower amount
					JOptionPane.showMessageDialog(null, "This would overdraw your account! \n " +
							"Please select a lower amount");						
					amountTextField.requestFocus();
			}						
		}
			catch(NumberFormatException err)//if non number entered
				{//send message to user to enter in specific text box & place focus in that text box
    			JOptionPane.showMessageDialog(null, "Please enter a number for the amount");
    			amountTextField.selectAll();
    			amountTextField.requestFocus();				
				}
			}				
	}						
	
	//Validate all text fields to ensure that some entry was made in each
	//Send an appropriate error message, if no entry was made and reset the insertion point
	public boolean transactionsValidation()
		{
		    if(!(pinTransactionsTextField.getText()).equals(""))//make sure text field is not empty
		    {
				if(!(amountTextField.getText()).equals(""))//make sure text field is not empty
				{
					transactionsValidationBoolean = true; 
				}
				else
				{//send message to tell user to enter days rented
					JOptionPane.showMessageDialog(null, "Please enter an amount");
					amountTextField.requestFocus();
					transactionsValidationBoolean = false;
				}
		    }
		    else
		    {//send message to tell user to enter name
				JOptionPane.showMessageDialog(null, "Please enter your PIN number");
				pinTransactionsTextField.requestFocus();
				transactionsValidationBoolean = false;
		    }
		    return transactionsValidationBoolean;
			}			
		
	//Validate all text fields to ensure that some entry was made in each
	//Send an appropriate error message, if no entry was made and reset the insertion point
	public boolean validation()
	{
	    if(!(firstNameTextField.getText()).equals(""))//make sure text field is not empty
	    {
			if(!(lastNameTextField.getText()).equals(""))//make sure text field is not empty
			{
				if(!(pinCreateTextField.getText()).equals(""))//make sure text field is not empty
				{
		    		validationBoolean = true; 
				}					
				else
					{//send message to tell user to enter miles driven
						JOptionPane.showMessageDialog(null, "Please enter your PIN number");
						pinCreateTextField.requestFocus();
						validationBoolean = false;
					}
			}
			else
			{//send message to tell user to enter days rented
				JOptionPane.showMessageDialog(null, "Please enter last name");
				lastNameTextField.requestFocus();
				validationBoolean = false;
			}
	    }
	    else
	    {//send message to tell user to enter name
			JOptionPane.showMessageDialog(null, "Please enter first name");
			firstNameTextField.requestFocus();
			validationBoolean = false;
	    }
	    return validationBoolean;
		}	
	}
	



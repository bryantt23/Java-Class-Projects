//Folder/Project Name: Project#8
//Programmer Name: Bryant Tunbutr

//Date: June 3, 2013
//Class Name: BankApplication
/*Project Description: This bank project uses a GUI to 
 * input the customer name, pin number and transaction amount,
 * along with buttons and radio buttons
 * for processing and display.
 * This is upgraded from Project#7 because there is an Accounts Superclass
 * and the Savings and Checking Classes inherit from Accounts
 * There are now fees for high amount withdrawals
 * 
 * There is a GUI class, an Accounts(calculation) class, Savings, Checking class
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
	
	//radio buttons on new account panel
	JRadioButton checkingRadioButton = new JRadioButton("Create checking account");
	JRadioButton savingsRadioButton = new JRadioButton("Create savings account");

	//grouped radio buttons
	ButtonGroup transactionsButtonGroup = new ButtonGroup();
	ButtonGroup enterPanelButtonGroup = new ButtonGroup();
	ButtonGroup newAccountTypeButtonGroup = new ButtonGroup();
	
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
	JTextArea newAccountOutputTextArea = new JTextArea(33, 53);
	JTextArea outputDisplayAccountsTextArea = new JTextArea(33, 53);
	JTextArea outputTransactionsTextArea = new JTextArea(33, 53);
	JTextField amountTextField = new JTextField(5);

	//to format currency strings
	DecimalFormat currency = new DecimalFormat("$,###.00");	
	
	//variables
	boolean validationBoolean; //placed here because used in calculation class 
	boolean transactionsValidationBoolean;  

	int countInteger; //keeps track of how many accounts created
	int countPlusOneInteger = countInteger+1; //used for looping
	int indexArrayInteger; 
    int pinCreatedInteger;
    int pinNumberForTransactionsInteger;

	double amountDouble;
	
    String firstNameString, lastNameString, pinCreatedString, pinNumberForTransactionsString;

    //array of accounts at instance level
    Accounts myAcct [ ] = new Accounts [10]; //  <--instance level
    int lastAcctInteger = -1; //  <--instance level
    
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
		super("Accounts");
		
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
		
		setSize(1130, 730);
		setVisible(true);		   	

		//add the radio buttons to button group
		transactionsButtonGroup.add(depositRadioButton);
		transactionsButtonGroup.add(withdrawalRadioButton);	

		//add the radio buttons to button group
		enterPanelButtonGroup.add(newAccountRadioButton);
		enterPanelButtonGroup.add(displayAllAccountsRadioButton);		
		enterPanelButtonGroup.add(doTransactionsRadioButton);		
		
		//add the radio buttons to button group
		newAccountTypeButtonGroup.add(checkingRadioButton);
		newAccountTypeButtonGroup.add(savingsRadioButton);
		
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
		savingsRadioButton.addActionListener(this);		
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
		
		JScrollPane outputScrollPane = new JScrollPane(outputDisplayAccountsTextArea);  

		JFrame frame = new JFrame();
		frame.setSize(1130, 730);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//add components to the enterPanel		
		enterPanel.add(iconLabel);
		enterPanel.add(companyLabel);		
		enterPanel.add(newAccountRadioButton);
		enterPanel.add(doTransactionsRadioButton);
		enterPanel.add(displayAllAccountsRadioButton);
		enterPanel.add(outputScrollPane);
		enterPanel.add(outputDisplayAccountsTextArea);
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

					setSize(1030, 730);
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
					setSize(1080, 730);
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
			setSize(1130, 730);
			setVisible(true);			
		}
		if(sourceObject == backButton)
		{
			//return to enterPanel from doTransactionsPanel
			remove(doTransactionsPanel);
			add(enterPanel);
			setSize(1030, 730);
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
		newAccountPanel.add(checkingRadioButton);
		newAccountPanel.add(savingsRadioButton);
		newAccountPanel.add(newAccountOutputTextArea);
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

		JScrollPane outputTransactionsScrollPane = new JScrollPane(outputTransactionsTextArea);
	
		//add components to the do transactions	panel
		doTransactionsPanel.add(iconLabel);
		doTransactionsPanel.add(companyLabel);
		doTransactionsPanel.add(depositRadioButton);
		doTransactionsPanel.add(withdrawalRadioButton);
		doTransactionsPanel.add(pinLabel);
		doTransactionsPanel.add(pinTransactionsTextField);
		doTransactionsPanel.add(amountLabel);
		doTransactionsPanel.add(amountTextField);
		doTransactionsPanel.add(outputTransactionsTextArea);
		doTransactionsPanel.add(outputTransactionsScrollPane);
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

	    //runs method in Accounts Class to check to see if user selected pin numbers has not been taken yet 
		Accounts isThereADuplicatePIN = new Accounts(countPlusOneInteger, pinCreatedInteger);
		
		// if PIN is already taken, tells user to pick a new one
		if (isThereADuplicatePIN.getIsThereADuplicatePIN() == true){
			JOptionPane.showMessageDialog(null, "This PIN number is not available. " +
					"Please enter a different PIN number");}
		
		else{
			//This method sets a new account into the Accounts Class 
			lastAcctInteger++;
			
			//creates a new savings account
			if (savingsRadioButton.isSelected()){
				
				// constructor based on myAcct array and number of accounts created 
			    myAcct[lastAcctInteger] = new Savings(lastAcctInteger, pinCreatedInteger, firstNameString, lastNameString);
			       			    
				//This displays the current information using get methods from the Accounts Class & Savings Class
			    newAccountOutputTextArea.setText(
						"Name" + '\t' + '\t' + "PIN"+ '\t' +"Acct Type"+ '\t' +
						"Trans Type"+ '\t' +"Trans Amt"+ '\t' + "Charge" + '\t'  +"Balance"+ '\n'+ '\n' +					
								
						Accounts.getFirstName() + " " + Accounts.getLastName() + '\t' + '\t' + 
						Accounts.getPinNumberString() + '\t' + "Savings" + '\t' +
						"New" + '\t' + "" + '\t' + "" + '\t' + 
						currency.format(Savings.initialDepositDouble) + '\n'+ '\n');				    
			    
			    //increment to keep track of how many accounts have already been created
			    countInteger++;	    				
			}
			
			// if a checking account is to be created 
			else if (checkingRadioButton.isSelected()){	

				// constructor based on myAcct array and number of accounts created and user information
				myAcct[lastAcctInteger] = new Checking(lastAcctInteger, pinCreatedInteger, firstNameString, lastNameString);
		       
				//This displays the current information using get methods from the Accounts Class and Checking Class
			    newAccountOutputTextArea.setText(
					"Name" + '\t' + '\t' + "PIN"+ '\t' +"Acct Type"+ '\t' +
					"Trans Type"+ '\t' +"Trans Amt"+ '\t' + "Charge" + '\t'  +"Balance"+ '\n'+ '\n' +					
							
					Accounts.getFirstName() + " " + Accounts.getLastName() + '\t' + '\t' + 
					Accounts.getPinNumberString() + '\t' + "Checking" + '\t' +
					"New" + '\t' + "" + '\t' + "" + '\t' + 
					currency.format(Checking.initialDepositDouble) + '\n'+ '\n');			
			    			    
			    //increment to keep track of how many accounts have already been created
			    countInteger++;	    	    		    
		}	
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
		outputDisplayAccountsTextArea.append(displayAccount.getDisplayAccountsString());
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
			//alert user
			JOptionPane.showMessageDialog(null, "This PIN number does exist!");				
			pinTransactionsTextField.requestFocus();	
		}
		//if the PIN does exist do deposit
		else 
		{
			try
			{
				//get info from user and parse
				double amountDouble = Double.parseDouble(amountTextField.getText());	

				// run method in Accounts Class to setDeposit using user info and the index of the PIN number based array
				myAcct[indexArrayInteger].setDeposit(amountDouble, indexArrayInteger);				
				
				//get the information from the Accounts Class then display in text area
				outputTransactionsTextArea.append(
						"Name" + '\t' + '\t' + "PIN"+ '\t' +"Acct Type"+ '\t' +
						"Trans Type"+ '\t' +"Trans Amt"+ '\t' + "Charge" + '\t'  +"Balance"+ '\n'+ '\n' +
						
						Accounts.getFirstNameArrayString()[indexArrayInteger] + " " + Accounts.getLastNameArrayString()[indexArrayInteger] + '\t' + '\t' + 
						Accounts.getPinNumbersArray()[indexArrayInteger] + '\t' + Accounts.getAccountTypeArrayString()[indexArrayInteger] + '\t' +
						"Deposit" + '\t' + currency.format(amountDouble) + '\t' + "" + '\t' + 
						currency.format(Accounts.getBalancesArray()[indexArrayInteger]) + '\n'+ '\n');	
		}
		catch(NumberFormatException err)//if non number entered
			{//send message to user to enter in specific text box & place focus in that text box
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
			//alert user
			JOptionPane.showMessageDialog(null, "This PIN number does exist!");				
			pinTransactionsTextField.requestFocus();
		}
		//if the PIN does exist
		else 
		{
			try
			{
				//get info from user and parse
				double amountDouble = Double.parseDouble(amountTextField.getText());	
				
				//Check to see if sufficient balance to do a withdrawal
				if(amountDouble<=Accounts.getBalancesArray()[indexArrayInteger])
				{
					// run method in Accounts Class to setDeposit using user info and the index of the PIN number based array
					myAcct[indexArrayInteger].setWithdrawal(amountDouble, indexArrayInteger);			

					//get the information from the Accounts Class then display in text area
					outputTransactionsTextArea.append(
							"Name" + '\t' + '\t' + "PIN"+ '\t' +"Acct Type"+ '\t' +
							"Trans Type"+ '\t' +"Trans Amt"+ '\t' + "Charge" + '\t'  +"Balance"+ '\n'+ '\n' +
					
							Accounts.getFirstNameArrayString()[indexArrayInteger] + " " + Accounts.getLastNameArrayString()[indexArrayInteger] + '\t' + '\t' + 
							Accounts.getPinNumbersArray()[indexArrayInteger] + '\t' + Accounts.getAccountTypeArrayString()[indexArrayInteger] + '\t' +
							"Withdrawal" + '\t' + currency.format(amountDouble) + '\t' + currency.format(Accounts.getFeeDepositDouble()) + '\t' + 
							currency.format(Accounts.getBalancesArray()[indexArrayInteger]) + '\n'+ '\n');	
				}
				else{//if withdrawal desired is above balance
					
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
		
	//Validate all text fields & radio buttons to ensure that some entry or selection was made 
	//Send an appropriate error message, if no entry was made and reset the insertion point
	public boolean transactionsValidation()
		{
		    if(!(pinTransactionsTextField.getText()).equals(""))//make sure text field is not empty
		    {
				if(!(amountTextField.getText()).equals(""))//make sure text field is not empty
				{
					if(depositRadioButton.isSelected() || withdrawalRadioButton.isSelected()) //make sure transaction selected
					{
						transactionsValidationBoolean = true; 						
					}
					else{// tell user to select transaction
						JOptionPane.showMessageDialog(null, "Please select a transaction type");
						depositRadioButton.requestFocus();						
					}					
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

	//Validate all text fields & radio buttons to ensure that some entry or selection was made 
	//Send an appropriate error message, if no entry was made and reset the insertion point
	public boolean validation()
	{
	    if(!(firstNameTextField.getText()).equals(""))//make sure text field is not empty
	    {
			if(!(lastNameTextField.getText()).equals(""))//make sure text field is not empty
			{
				if(!(pinCreateTextField.getText()).equals(""))//make sure text field is not empty
				{
					if(checkingRadioButton.isSelected() || savingsRadioButton.isSelected())//make sure account type chosen
					{
						validationBoolean = true; 						
					}
					else{//tell user to pick acct type
						JOptionPane.showMessageDialog(null, "Please select account type");
						checkingRadioButton.requestFocus();						
					}					
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
	



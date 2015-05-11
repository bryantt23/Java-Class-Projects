//Folder/Project Name: Project#4
//Programmer Name: Bryant Tunbutr
//Date: April 17, 2013
//Class Name: BryantsCarRentalApplication
/*Project Description: This car rental project uses a GUI to 
 * input the customer name, days rented and miles driven,
 * along with radio buttons for car model 
 * and a checkbox for navigation system
 * and then outputs the total number of cars rented
 * and rental costs using swing components
*/

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import javax.swing.*;		//used for the swing components
import java.awt.event.*;	//used for the ActionListener
import java.text.*;			//used for the DecimalFormat class


public class BryantsCarRentalApplication extends JFrame 
implements ActionListener
{
	//declare your instance objects here
  	// Objects to enter input and display
	
	//labels
	JLabel companyLabel = new JLabel("Bryant's Car Rental");
	JLabel iconLabel;
	JLabel nameLabel = new JLabel("Customer Name: ");
	//textfields with labels
	JTextField nameTextField = new JTextField(33);
	JLabel daysRentedLabel = new JLabel("Days Rented: ");
	JTextField daysRentedTextField = new JTextField(33);
	JLabel milesDrivenLabel = new JLabel("Miles Driven: ");
	JTextField milesDrivenTextField = new JTextField(33);
	//buttons
	JButton calculateButton = new JButton("Rental");
	JButton summaryButton = new JButton("Summary");
	JButton clearButton = new JButton("Clear");
	//text area with scroll bars
	JTextArea outputTextArea = new JTextArea(15, 46);
	JScrollPane outputScrollPane = new JScrollPane(outputTextArea);  
	//checkbox
	JCheckBox navigationCheckBox = new JCheckBox("Navigation System"); 
	//radio buttons
	JRadioButton economyRadioButton = new JRadioButton("Economy");
	JRadioButton midsizeRadioButton = new JRadioButton("Mid-Size");
	JRadioButton fullsizeRadioButton = new JRadioButton("Full-Size");
	JRadioButton luxuryRadioButton = new JRadioButton("Luxury");
	JRadioButton invisibleRadioButton = new JRadioButton("");
	//grouped radio buttons
	ButtonGroup modelButtonGroup = new ButtonGroup();

	JPanel panel = new JPanel();
	
	//variables
	int daysRentedInteger, milesDrivenInteger, daysRentedTotalInteger, milesDrivenTotalInteger,
		economyDaysRentedInteger, midsizeDaysRentedInteger, fullsizeDaysRentedInteger, luxuryDaysRentedInteger,
		economyCarsCountInteger, midsizeCarsCountInteger, fullsizeCarsCountInteger, luxuryCarsCountInteger, totalCarsCountInteger;
	String modelSelectString, navigationString = "No";
	float dailyRateFloat, daysRentedTimesDailyRateFloat, mileageRateFloat, dailyRatePlusMileageFloat, dailyRatePlusMileageTotalFloat,
		economyDailyRatePlusMileageFloat, midsizeDailyRatePlusMileageFloat, 
		fullsizeDailyRatePlusMileageFloat, luxuryDailyRatePlusMileageFloat;
	
	//constants
	//cost per day by model	
	final float ECONOMY_DAILY_RATE_FLOAT = 26;
	final float MIDSIZE_DAILY_RATE_FLOAT = 40;
	final float FULLSIZE_DAILY_RATE_FLOAT = 65;
	final float LUXURY_DAILY_RATE_FLOAT = 85;
	//cost per mile by model	
	final float ECONOMY_MILEAGE_RATE_FLOAT = 0.15F;
	final float MIDSIZE_MILEAGE_RATE_FLOAT = 0.18F;
	final float FULLSIZE_MILEAGE_RATE_FLOAT = 0.25F;
	final float LUXURY_MILEAGE_RATE_FLOAT = 0.40F;
	//cost per day for navigation system	
	final float NAVIGATION_RATE_FLOAT = 5;
	//constant for free miles
	final int FREE_MILES_INTEGER = 100;

	//This method will create an object of ourselves and set the default close operation
	public static void main(String[] args)
	{
		BryantsCarRentalApplication myProgram = new BryantsCarRentalApplication();
		myProgram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	//This is the constructor and will add the components to the GUI, 
	//register the listeners, and set the properties of the JFrame
	public BryantsCarRentalApplication()
	{			
		iconLabel = new JLabel(new ImageIcon("eco_green_car_icon.jpg"));
		
		JPanel optionsPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
    	JLabel programmerNameLabel = new JLabel("\n Maintained by Bryant Tunbutr");

    	Font bigFont = new Font("Times New Roman", Font.BOLD, 44);    	

		//add the radiobuttons to button group
		modelButtonGroup.add(economyRadioButton);
		modelButtonGroup.add(midsizeRadioButton);
		modelButtonGroup.add(fullsizeRadioButton);
		modelButtonGroup.add(luxuryRadioButton);
		modelButtonGroup.add(invisibleRadioButton);
		
		//disable the summary button
		summaryButton.setEnabled(false);		
		
		//deselect all radio buttons by selecting the invisible radio button
		invisibleRadioButton.setSelected(true);    		

		companyLabel.setFont(bigFont);		
		companyLabel.setForeground(Color.RED);		
								
		JFrame frame = new JFrame();
		frame.setTitle("Radio");
		frame.setSize(530, 630);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.add(iconLabel);
		panel.add(companyLabel);
		panel.add(nameLabel);
		panel.add(nameTextField);
		panel.add(daysRentedLabel);
		panel.add(daysRentedTextField);
		panel.add(milesDrivenLabel);
		panel.add(milesDrivenTextField);
										
		panel.add(economyRadioButton);
		panel.add(midsizeRadioButton);
		panel.add(fullsizeRadioButton);
		panel.add(luxuryRadioButton);		

		optionsPanel.add(navigationCheckBox);
		panel.add(optionsPanel);
		
		buttonsPanel.add(calculateButton);
		buttonsPanel.add(summaryButton);
		buttonsPanel.add(clearButton);

    	panel.add(buttonsPanel);    	
    	panel.add(outputScrollPane);
    	panel.add(programmerNameLabel);
		
		frame.getContentPane().add(panel);
		frame.setVisible(true);

		//add the objects to the actionlistener
		milesDrivenTextField.addActionListener(this);
		calculateButton.addActionListener(this);
		clearButton.addActionListener(this);		
		summaryButton.addActionListener(this);
	}


	//This method will check for which component triggered this method.
	//If the purchase button or the milesDrivenTextField is the trigger, call 
	//the validation method, and if OK, call the purchase method
	//If the summary button is the trigger, call the summary method
	public void actionPerformed(ActionEvent evt)
	{
	    Object sourceObject = evt.getSource();
	    
	    if(sourceObject == calculateButton || sourceObject == milesDrivenTextField)
	    {
	    	if(validation())
	    		purchase();
	    }
	    if(sourceObject == clearButton)
	    {
	    	clearTextFields();
	    }

	    if(sourceObject == summaryButton)
	    {
	    	summary();
	    }	
	}
	
	//actions to take based on radiobutton selection
	public boolean selectRadioButton()
	{
		boolean selectedBoolean;
		if(economyRadioButton.isSelected()||midsizeRadioButton.isSelected()
				||fullsizeRadioButton.isSelected()||luxuryRadioButton.isSelected())
		{
			selectedBoolean = true;
		}
		else
		{
			selectedBoolean = false;
		}
		return selectedBoolean;
	}
				
	//Validate all text fields to ensure that some entry was made in each
	//Send an appropriate error message, if no entry was made and reset the insertion point
	public boolean validation()
	{
	    boolean validationBoolean;
	    
	    if(!(nameTextField.getText()).equals(""))//make sure text field is not empty
	    {
			if(!(daysRentedTextField.getText()).equals(""))//make sure text field is not empty
			{
				if(!(milesDrivenTextField.getText()).equals(""))//make sure text field is not empty
				{
			    	if(selectRadioButton())//make sure model has been selected
			    	{
			    		validationBoolean = true; 
			    	}
			    	else
			    	{//send message to user to select model
			    		JOptionPane.showMessageDialog(null, "Please select a model");
			    		economyRadioButton.requestFocus();
		    			validationBoolean = false;
			    	}										
				}					
				else
					{//send message to tell user to enter miles driven
						JOptionPane.showMessageDialog(null, "Please enter miles driven");
						milesDrivenTextField.requestFocus();
						validationBoolean = false;
					}
			}
			else
			{//send message to tell user to enter days rented
				JOptionPane.showMessageDialog(null, "Please enter days rented");
				daysRentedTextField.requestFocus();
				validationBoolean = false;
			}
	    }
	    else
	    {//send message to tell user to enter name
			JOptionPane.showMessageDialog(null, "Please enter name");
			nameTextField.requestFocus();
			validationBoolean = false;
	    }
	    return validationBoolean;
		}
	
	//actions to take based on which car model is selected
	public void modelSelect()
	{
		//if economy model is selected... 
		if(economyRadioButton.isSelected())
		{
			//use this model string which gets used when the rental is displayed 
			modelSelectString = "Economy";
			//use the economy cost to calculate daily rental cost
			dailyRateFloat = ECONOMY_DAILY_RATE_FLOAT;
			//use economy mileage to calculate rental cost
			mileageRateFloat = ECONOMY_MILEAGE_RATE_FLOAT;
			//same steps occur for the other models...
		}
		else if(midsizeRadioButton.isSelected())
		{
			modelSelectString = "Mid-Size";
			dailyRateFloat = MIDSIZE_DAILY_RATE_FLOAT;
			mileageRateFloat = MIDSIZE_MILEAGE_RATE_FLOAT;
		}
		else if(fullsizeRadioButton.isSelected())
		{
			modelSelectString = "Full-Size";
			dailyRateFloat = FULLSIZE_DAILY_RATE_FLOAT;
			mileageRateFloat = FULLSIZE_MILEAGE_RATE_FLOAT;
		}
		else 
		{
			modelSelectString = "Luxury";
			dailyRateFloat = LUXURY_DAILY_RATE_FLOAT;
			mileageRateFloat = LUXURY_MILEAGE_RATE_FLOAT;
		}				
	}	
	
	//This method will retrieve and convert the data from the components,
	//calculate the total for this car, accumulate the totals for all cars, display the result, and 
	//reset the textfields for the next entry
	public void purchase()
	{
	    //local variables
	    String nameString, daysRentedString, milesDrivenString;
	 
		//retrieve the input from the  user
		nameString = nameTextField.getText();
		daysRentedString = daysRentedTextField.getText();
		milesDrivenString = milesDrivenTextField.getText();
		modelSelect();
		
		//navigation selection
		if (navigationCheckBox.isSelected()){
			dailyRateFloat = dailyRateFloat + NAVIGATION_RATE_FLOAT;//this adds cost to daily rate
			navigationString = "Yes";//this changes the Navigation from default of "No" to "Yes"
		}
		
		try
		{
			//change data types
			daysRentedInteger = Integer.parseInt(daysRentedString);
			try
			{
				milesDrivenInteger = Integer.parseInt(milesDrivenString);	
				
				//calculate cost due to days rented times daily rate
				daysRentedTimesDailyRateFloat = daysRentedInteger * dailyRateFloat;
				
				//calculate cost due to mileage
				if (milesDrivenInteger < 101){//cost if less than 101 miles driven
					mileageRateFloat = 0;
				}
				else
				{//cost if more than 100 miles driven
					mileageRateFloat = (milesDrivenInteger - FREE_MILES_INTEGER) * mileageRateFloat;
				}
				
				// add up cost due to mileage and daily rate
				dailyRatePlusMileageFloat = daysRentedTimesDailyRateFloat + mileageRateFloat;	
				//accumulate  the totals
				daysRentedTotalInteger += daysRentedInteger;
				milesDrivenTotalInteger += milesDrivenInteger;
				dailyRatePlusMileageTotalFloat += dailyRatePlusMileageFloat;
				
				//accumulate days & mileage by model 
				if(economyRadioButton.isSelected())
				{
					economyDaysRentedInteger += daysRentedInteger;
					economyDailyRatePlusMileageFloat += dailyRatePlusMileageFloat;
					economyCarsCountInteger++;
					totalCarsCountInteger++;
				}
				else if(midsizeRadioButton.isSelected())
				{
					midsizeDaysRentedInteger += daysRentedInteger;
					midsizeDailyRatePlusMileageFloat += dailyRatePlusMileageFloat;
					midsizeCarsCountInteger++;
					totalCarsCountInteger++;
				}
				else if(fullsizeRadioButton.isSelected())
				{
					fullsizeDaysRentedInteger += daysRentedInteger;
					fullsizeDailyRatePlusMileageFloat += dailyRatePlusMileageFloat;
					fullsizeCarsCountInteger++;
					totalCarsCountInteger++;
				}
				else 
				{
					luxuryDaysRentedInteger += daysRentedInteger;
					luxuryDailyRatePlusMileageFloat += dailyRatePlusMileageFloat;
					luxuryCarsCountInteger++;
					totalCarsCountInteger++;
				}
										
				//display
				displayRental();
				summaryButton.setEnabled(true);
						
				//clear textfields 
				clearTextFields();		
				
			}
			catch(NumberFormatException err)//if non number entered
				{//send message to user to enter in specific text box & place focus in that textbox
    			JOptionPane.showMessageDialog(null, "Please enter a number for miles driven");
    			milesDrivenTextField.selectAll();
    			milesDrivenTextField.requestFocus();				
				}
		}

			catch(NumberFormatException err)//if non number entered
				{//send message to user to enter in specific text box & place focus in that textbox
    			JOptionPane.showMessageDialog(null, "Please enter a number for days rented");
    			daysRentedTextField.selectAll();
    			daysRentedTextField.requestFocus();				
				}
	}
	
	//This method will format the result and display appropriate information in the text area
	public void displayRental()
	{
		//Format the values to currency format
		DecimalFormat valueDecimalFormat = new DecimalFormat("$#0.00");
		//display the total for the customer
		outputTextArea.setText(
				
				"Name" + '\t'+ "Car Type"+ '\t' + "Miles Driven" + '\t' + "Days Rented"+ '\t'+ "Navigation"+ '\t' + "Total"+'\n'+
				nameTextField.getText() + '\t'+ modelSelectString + '\t' + milesDrivenInteger + '\t' + daysRentedInteger+ '\t'+ navigationString+ '\t' + valueDecimalFormat.format(dailyRatePlusMileageFloat)+'\n'
				
				);
	}
		
	//This method will clear the input fields for the next entry
	public void clearTextFields()
	{
	    //clear the text fields
		nameTextField.setText("");
		daysRentedTextField.setText("");
		milesDrivenTextField.setText("");
	
		//place the cursor back in the name text field
		nameTextField.requestFocus();		
		
		//deselect all radio buttons by selecting the invisible radio button
		invisibleRadioButton.setSelected(true);
		
		//uncheck the navigation checkbox
		navigationCheckBox.setSelected(false);
	}

	//to display summary information
	public void summary()
	{		
		//Format the values to currency format
		DecimalFormat valueDecimalFormat = new DecimalFormat("$#0.00");
		//display the total for the customer

		//this is the formatted string of the summary 
		String summaryString = 
				//Type of car, tabs, Total Cars Rented, tabs, Total Cost and new line
				//This format is followed for each car model
				"Type of Car" + '\t'+ "Total Cars Rented"+ '\t'+ "Total Cost"+ '\n' +
				"Economy" + '\t'+ economyCarsCountInteger+ '\t' + '\t'+  valueDecimalFormat.format(economyDailyRatePlusMileageFloat) + '\n' +
				"Mid-size" + '\t'+ midsizeCarsCountInteger+ '\t' + '\t'+  valueDecimalFormat.format(midsizeDailyRatePlusMileageFloat) + '\n' +
				"Full-size" + '\t'+ fullsizeCarsCountInteger+ '\t' + '\t'+  valueDecimalFormat.format(fullsizeDailyRatePlusMileageFloat) + '\n' +
				"Luxury" + '\t'+ luxuryCarsCountInteger+ '\t' + '\t'+  valueDecimalFormat.format(luxuryDailyRatePlusMileageFloat) + '\n' +
				"Total" + '\t'+ totalCarsCountInteger+ '\t' + '\t'+  valueDecimalFormat.format(dailyRatePlusMileageTotalFloat) + '\n' ;
        System.out.print(summaryString);
        
        //This displays the formatted summaryString in a JOptionPane
        JOptionPane.showMessageDialog(null, new JTextArea(summaryString));                
	}
 		
}

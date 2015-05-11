//Folder/Project Name: Project#9
//Programmer Name: Bryant Tunbutr
//Date: June 5, 2013
//Class Name: BCarRentalApplication
/*Project Description: This car rental project uses a GUI to 
 * input the customer name, days rented and miles driven,
 * along with a combo box for car model 
 * and a check box for navigation system
 * and then outputs the total number of cars rented
 * and rental costs using swing components
 * This is an upgrade from project 4 because
 * there is a GUI class, a calculation class,
 * and there is a new day button and function
 * 
 * This is an upgrade from project 6 because a
 * GridLayout and BorderLayout Panel have been 
 * added to create a more professional appearance.
 * There are now three panels--mainPanel, borderLayoutPanel,
 * gridLayoutPanel
 * 
 * This is the GUI class
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.*;		//used for the swing components
import java.awt.event.*;	//used for the ActionListener
import java.text.*;			//used for the DecimalFormat class

public class BCarRentalApplication extends JFrame 
implements ActionListener
{
	//declare your instance objects here
  	// Objects to enter input and display	
	
	//labels
	JLabel companyLabel = new JLabel("Bryant's Car Rental");
	JLabel iconLabel;
	JLabel nameLabel = new JLabel("Customer Name: ");
	JLabel dailyRateInfoLabel = new JLabel(""); 
	
	//text fields with labels
	JTextField nameTextField = new JTextField(15);
	JLabel daysRentedLabel = new JLabel("Days Rented: ");
	JTextField daysRentedTextField = new JTextField(15);
	JLabel milesDrivenLabel = new JLabel("Miles Driven: ");
	JTextField milesDrivenTextField = new JTextField(15);
	
	//buttons
	JButton calculateButton = new JButton("Rental");
	JButton summaryButton = new JButton("Summary");
	JButton clearButton = new JButton("Clear");
	JButton newDayButton = new JButton("New Day");
	
	//text area with scroll bars
	JTextArea outputTextArea = new JTextArea(15, 46);
	JScrollPane outputScrollPane = new JScrollPane(outputTextArea);  

	//arrays and combo boxes
	String carModelsArrayString [] = {"Economy",  "Mid-Size", 
			"Full-Size", "Luxury", };
	JComboBox carModelsComboBox = new JComboBox(carModelsArrayString);
	
	//checkbox
	JCheckBox navigationCheckBox = new JCheckBox("Navigation System"); 
	
	//main panel used for icon, company name, programmer label,
	//and to add other panels 
	JPanel mainPanel = new JPanel();
	
	//panel using border layout for buttons & combo box
	JPanel borderLayoutPanel = new JPanel();
	
	//panel using border layout for labels & text boxes
	JPanel gridLayoutPanel = new JPanel();
	
	//variables
	int daysRentedInteger, milesDrivenInteger, daysRentedTotalInteger, milesDrivenTotalInteger,
		economyDaysRentedInteger, midsizeDaysRentedInteger, fullsizeDaysRentedInteger, luxuryDaysRentedInteger,
		economyCarsCountInteger, midsizeCarsCountInteger, fullsizeCarsCountInteger, luxuryCarsCountInteger, totalCarsCountInteger;
	int indexInt;  //placed here because used in 2 methods    
    String modelSelectString, navigationString = "No"; 
	float dailyRateFloat, daysRentedTimesDailyRateFloat, mileageRateFloat, dailyRatePlusMileageFloat, dailyRatePlusMileageTotalFloat,
		economyDailyRatePlusMileageFloat, midsizeDailyRatePlusMileageFloat, 
		fullsizeDailyRatePlusMileageFloat, luxuryDailyRatePlusMileageFloat;
	boolean validationBoolean; //placed here because used in calculation class 
	
	//This method will create an object of ourselves and set the default close operation
	public static void main(String[] args)
	{
		BCarRentalApplication myProgram = new BCarRentalApplication();
		myProgram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//This is the constructor and will add the components to the GUI, 
	//register the listeners, and set the properties of the JFrame
	public BCarRentalApplication()
	{			
		//call a method to set up the components
		super("Calculation");
		
		iconLabel = new JLabel(new ImageIcon("eco_green_car_icon.jpg"));
    	JLabel programmerNameLabel = new JLabel("\n Maintained by Bryant Tunbutr");
    	Font bigFont = new Font("Times New Roman", Font.BOLD, 44);    					
		companyLabel.setFont(bigFont);		
		companyLabel.setForeground(Color.RED);		
								
		JFrame frame = new JFrame();
		frame.setTitle("Radio");
		frame.setSize(670, 530);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add components to mainPanel
		mainPanel.add(iconLabel);
		mainPanel.add(companyLabel);
		
		//add gridLayoutPanel
        add(gridLayoutPanel);
        gridLayoutPanel.setLayout(new GridLayout(0,2,5,5));	//set design
        
        //add components to gridLayoutPanel
		gridLayoutPanel.add(nameLabel);
		gridLayoutPanel.add(nameTextField);
		gridLayoutPanel.add(daysRentedLabel);
		gridLayoutPanel.add(daysRentedTextField);
		gridLayoutPanel.add(milesDrivenLabel);
		gridLayoutPanel.add(milesDrivenTextField);											
		gridLayoutPanel.add(navigationCheckBox);				
		gridLayoutPanel.add(dailyRateInfoLabel);		
		
		
		// pixels between cols, pixels between rows
		//add gridLayoutPanel
		add(borderLayoutPanel);
		borderLayoutPanel.setLayout(new BorderLayout(5,15)); // arguments in BorderLayout
        
        //add components to BorderLayout
		borderLayoutPanel.add(carModelsComboBox, BorderLayout.NORTH);
		borderLayoutPanel.add(calculateButton, BorderLayout.SOUTH);
		borderLayoutPanel.add(summaryButton, BorderLayout.EAST);
		borderLayoutPanel.add(clearButton, BorderLayout.WEST);
		borderLayoutPanel.add(newDayButton, BorderLayout.CENTER);

		//add gridLayoutPanel & BorderLayout to mainPanel
		mainPanel.add(gridLayoutPanel);
		mainPanel.add(borderLayoutPanel);
		
    	mainPanel.add(outputScrollPane);
    	mainPanel.add(programmerNameLabel);		
    	
		frame.getContentPane().add(mainPanel);
		frame.setVisible(true);

		//add the objects to the action listener
		milesDrivenTextField.addActionListener(this);
		calculateButton.addActionListener(this);
		clearButton.addActionListener(this);		
		summaryButton.addActionListener(this);
		newDayButton.addActionListener(this);
		carModelsComboBox.addActionListener(this);
	}

	//This method will check for which component triggered this method.
	//If the purchase button or the milesDrivenTextField is the trigger, call 
	//the validation method, and if OK, call the purchase method
	//If the summary button is the trigger, call the summary method
	//has clear and new day methods and triggers too
	public void actionPerformed(ActionEvent evt)
	{
	    Object sourceObject = evt.getSource();
	    if(sourceObject == carModelsComboBox)
	    {
	    	navigation();
	    	displayDailyRate();
	    }	
	    if(sourceObject == navigationCheckBox)
	    {
	    	navigation();
	    }		    
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
	    if(sourceObject == newDayButton)
	    {
	    	newDay();
	    }	
	}
	
	//this resets all accumulated values to 0
	public void newDay(){		
        int responseInt;
        // gives user choice to say yes, no, or cancel before clearing out everything
        responseInt = JOptionPane.showConfirmDialog(null, "Start a new day and reset all totals?");
        	if (responseInt==JOptionPane.YES_OPTION) //if yes
        	{
        		Calculation joeCalculation = new Calculation();
        		joeCalculation.setNewDay();
    	    	clearTextFields();
    	    	//tells user it was successful
        		JOptionPane.showMessageDialog(null, "A new day has been started and all totals have been reset.");
        	}
        	else //if no or cancel
        	{
        		//tells user nothing has been changed
        		JOptionPane.showMessageDialog(null, "A new day has not been started and all totals are the same.");
        	}			
	}
	
	//this makes the jcombo box (index) to automatically display mileage and daily rate by car model
	public void displayDailyRate(){				
        indexInt = carModelsComboBox.getSelectedIndex();	
		Calculation joeCalculation = new Calculation(indexInt, navigationString);
		dailyRateInfoLabel.setText(joeCalculation.getDailyRate());
	}
	

	//This method will retrieve and convert the data from the components,
	//will test it and if valid will second it to second class for calculation and acculmulation
	//which will then be returned and displayed in this presentation class
	public void purchase()
	{
	    //local variables
	    String nameString, daysRentedString, milesDrivenString;
	 
		//retrieve the input from the  user
		nameString = nameTextField.getText();
		daysRentedString = daysRentedTextField.getText();
		milesDrivenString = milesDrivenTextField.getText();
		
		try
		{
			//change data types
			daysRentedInteger = Integer.parseInt(daysRentedString);
			try
			{
				milesDrivenInteger = Integer.parseInt(milesDrivenString);	
				indexInt = carModelsComboBox.getSelectedIndex();	

				//use constructor to input data & calculate 
				Calculation joeCalculation1 = new Calculation(indexInt, daysRentedInteger, milesDrivenInteger, navigationString, validationBoolean);
	
				//display data, retrieve using constructor & get text methods

				outputTextArea.setText("Name" + '\t'+ "Car Type"+ '\t' + "Miles Driven" + '\t' + "Days Rented"+ '\t'+ "Navigation"+ '\t' + "Total"+'\n'+
										nameTextField.getText() + '\t'+ joeCalculation1.getModelSelectString() + '\t' + milesDrivenInteger + '\t' + daysRentedInteger+ '\t'+ navigationString+ '\t' + joeCalculation1.getDailyRatePlusMileageString()+'\n'
						);
				
				
				/*
				outputTextArea.setText("Name: " + nameString);
				outputTextArea.append("\nDaily Rate: " + joeCalculation1.getRateString());
				outputTextArea.append("\nDaysRentedTimesDailyRate: " + joeCalculation1.getDaysRentedTimesDailyRateString());
				outputTextArea.append("\ngetDailyRatePlusMileageString: " + joeCalculation1.getDailyRatePlusMileageString());
			*/
			}
			catch(NumberFormatException err)//if non number entered
				{//send message to user to enter in specific text box & place focus in that text box
    			JOptionPane.showMessageDialog(null, "Please enter a number for miles driven");
    			milesDrivenTextField.selectAll();
    			milesDrivenTextField.requestFocus();				
				}
		}
			catch(NumberFormatException err)//if non number entered
				{//send message to user to enter in specific text box & place focus in that text box
    			JOptionPane.showMessageDialog(null, "Please enter a number for days rented");
    			daysRentedTextField.selectAll();
    			daysRentedTextField.requestFocus();				
				}		
	}
	
	//to display summary information
	public void summary()
	{		
		//this is the formatted string of the summary 
		String summaryString = 
				"Type of Car" + '\t'+ "Total Cars Rented"+ '\t'+ "Total Cost"+ '\n' +
				"Economy" + '\t'+ Calculation.getEconomyCarsCountString() + '\t' + '\t'+  Calculation.getEconomyDailyRatePlusMileageTotalString() + '\n' +
				"Mid-size" + '\t'+ Calculation.getMidsizeCarsCountString() + '\t' + '\t'+  Calculation.getMidsizeDailyRatePlusMileageTotalString() + '\n' +
				"Full-size" + '\t'+ Calculation.getFullsizeCarsCountString() + '\t' + '\t'+ Calculation.getFullsizeDailyRatePlusMileageTotalString()  + '\n' +
				"Luxury" + '\t'+ Calculation.getLuxuryCarsCountString() + '\t' + '\t'+ Calculation.getLuxuryDailyRatePlusMileageTotalString() + '\n' +
				"Total" + '\t'+ Calculation.getTotalCarsCountString()+ '\t' + '\t'+ Calculation.getDailyRatePlusMileageTotalString() + '\n' ;
					
        //This displays the formatted summaryString in a JOptionPane
        JOptionPane.showMessageDialog(null, new JTextArea(summaryString));         
        System.out.print(summaryString);		
	}
	
	public void navigation ()
	{
		//navigation selection
		if (navigationCheckBox.isSelected())
		{
			navigationString = "Yes";//this changes the Navigation from default of "No" to "Yes"
		}
		else
		{
			navigationString = "No";
		}
	}
		
	//Validate all text fields to ensure that some entry was made in each
	//Send an appropriate error message, if no entry was made and reset the insertion point
	public boolean validation()
	{
	    if(!(nameTextField.getText()).equals(""))//make sure text field is not empty
	    {
			if(!(daysRentedTextField.getText()).equals(""))//make sure text field is not empty
			{
				if(!(milesDrivenTextField.getText()).equals(""))//make sure text field is not empty
				{
		    		validationBoolean = true; 
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
	
	//This method will clear the input fields for the next entry
	public void clearTextFields()
	{
	    //clear the text fields
		nameTextField.setText("");
		daysRentedTextField.setText("");
		milesDrivenTextField.setText("");
	
		//place the cursor back in the name text field
		nameTextField.requestFocus();		
		
		//reset j combo box
		carModelsComboBox.setSelectedIndex(0);
		
		//uncheck the navigation check box
		navigationCheckBox.setSelected(false);		
	}
}

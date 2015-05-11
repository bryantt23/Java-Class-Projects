//Folder/Project Name: Project#5
//Programmer Name: Bryant Tunbutr
//Date: April 29, 2013
//Class Name: BagelStopApp
/*Project Description: This bagel shop project uses a GUI to 
 * input new flavors, drop down lists for flavors,
 * along with radio buttons for type of food 
 * and buttons to add and remove flavors and display information
 * and outputs the total number of bagels and cream cheese flavors
 * and uses buttons to add and remove flavors and display help
 * information using swing components
*/

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

public class BagelStopApp extends JFrame
	implements ActionListener
{
  	// Objects to enter input and display
	//Declare instance variables / objects
	JPanel mainPanel = new JPanel();
	String inputFlavorString;
	
	//labels
	JLabel companyLabel = new JLabel("Bagel Stop");
	JLabel programmerNameLabel = new JLabel("\n Maintained by Bryant Tunbutr");
	
	//arrays and combo boxes
	String bagelFlavorArrayString [] = {"Plain", "Egg", "Rye", "Salt", "Blueberry", "Garlic", "Onion", "Sesame", "Poppy Seed", "Works"};
	JComboBox bagelFlavorsComboBox = new JComboBox(bagelFlavorArrayString);
	String creamCheeseFlavorArrayString [] = {"Plain", "Herb", "Garlic"};
	JComboBox creamCheeseFlavorsComboBox = new JComboBox(creamCheeseFlavorArrayString);
	
	//text field
	JTextField newFlavorTextField = new JTextField(10);

	//buttons
	JButton addFlavorButton = new JButton("Add new flavor");
	JButton removeButton = new JButton("Remove flavor");
	JButton countButton = new JButton("Count");
	JButton helpButton = new JButton("Help");
	
	//radio buttons
	JRadioButton bagelRadioButton = new JRadioButton("Bagel");
	JRadioButton creamCheeseRadioButton = new JRadioButton("Cream cheese");
	JRadioButton invisibleRadioButton = new JRadioButton("");

	//group radio buttons
	ButtonGroup bagelOrCreamCheeseButtonGroup = new ButtonGroup();
	
	//create font
	Font bigFont = new Font("Times New Roman", Font.BOLD, 44);    	

	//This method will create an object of ourselves and set the default close operation
	public static void main(String[] args) 
	{
		BagelStopApp myApp = new BagelStopApp();
		myApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//This is the constructor and will add the components to the GUI, 
	//register the listeners, and set the properties of the JFrame
	public BagelStopApp()
	{
		//add the radio buttons to button group
		bagelOrCreamCheeseButtonGroup.add(bagelRadioButton);
		bagelOrCreamCheeseButtonGroup.add(creamCheeseRadioButton);
		bagelOrCreamCheeseButtonGroup.add(invisibleRadioButton);
		
		//Add components to JPanel
		mainPanel.add(companyLabel);
		mainPanel.add(bagelRadioButton);
		mainPanel.add(creamCheeseRadioButton);
		mainPanel.add(new JLabel("Bagel flavors"));
		mainPanel.add(bagelFlavorsComboBox);
		mainPanel.add(new JLabel("Cream cheese flavors"));
		mainPanel.add(creamCheeseFlavorsComboBox);
		mainPanel.add(new JLabel("Enter new flavor"));
		mainPanel.add(newFlavorTextField);
		mainPanel.add(addFlavorButton);
		mainPanel.add(removeButton);
		mainPanel.add(countButton);
		mainPanel.add(helpButton);		
		mainPanel.add(programmerNameLabel);		

    	//properties of company label 
		companyLabel.setFont(bigFont); //set font		
		companyLabel.setForeground(Color.RED); //set color	
		
		//register the listeners
		helpButton.addActionListener(this);
		addFlavorButton.addActionListener(this);
		removeButton.addActionListener(this);
		countButton.addActionListener(this);
		bagelFlavorsComboBox.addActionListener(this);
		creamCheeseFlavorsComboBox.addActionListener(this);
		
		//Set properties for the JFrame and add panel
		add(mainPanel);
		setSize(400, 250);
		setVisible(true);		
	}

	//This method will check for which component triggered this method.
	//If the helpButton is the trigger, call the help method.
	//If the addFlavorButton is the trigger, call the findAndAddFlavor method.
	//If the removeButton is the trigger, call the removeFlavor method.
	//If the countButton button is the trigger, call the count method
	public void actionPerformed(ActionEvent evt)
	{
	    if(evt.getSource() == helpButton)
	    {
	    	help();
	    }	    
	    if(evt.getSource() == addFlavorButton )
	    {
	    	findAndAddFlavor();
	    }	    
	    if(evt.getSource() == removeButton )
	    {
	    	removeFlavor();
	    }
	    if(evt.getSource() == countButton )
	    {
	    	count();
	    }
	}
	    
	//counts number cream cheese and bagel flavors
	public void count()
	{
		int countTotalBagelsInteger = bagelFlavorsComboBox.getItemCount(); //Gets number in combo box;
		int countTotalCreamCheeseInteger = creamCheeseFlavorsComboBox.getItemCount(); //Gets number in combo box
		
		//send message with instructions to user
		JOptionPane.showMessageDialog(null, "Total number of bagels " + countTotalBagelsInteger  + "\n"
				+ "Total number of cream cheeses " + countTotalCreamCheeseInteger + "\n" );					
	}
	
	//instructs user how to add and remove flavors
	public void help()
	{
		//send message with instructions to user
		JOptionPane.showMessageDialog(null, "To add a Bagel flavor or Cream cheese flavor \n\n" +
				"First click the radio button for Bagel flavor or Cream cheese \n" +
				"Then type the flavor to be added into the Enter new flavor textbox \n" +
				"Then click the Add flavor button \n" +
				"Finally confirm your selection \n\n" +			
				
				"To remove a Bagel flavor or Cream cheese flavor \n\n" +
				"First click the radio button for Bagel flavor or Cream cheese \n" +
				"Then select the flavor to be removed from the drop down list \n" +
				"Then click the Remove flavor button \n" +
				"Finally confirm your selection \n\n" 
				);					
	}
	
	//adds flavors of cream cheese and bagels
	public void findAndAddFlavor()
	{
    	selectRadioButton();
    	
    	inputFlavorString = newFlavorTextField.getText();				
		if(inputFlavorString.trim().length() > 0)//make sure text field is not empty
    	{
			if(bagelRadioButton.isSelected())
			{
				//Search the items for a match
				boolean foundFlavorBoolean = findDuplicateBagels(inputFlavorString);
				if(foundFlavorBoolean)
	    			JOptionPane.showMessageDialog(null, inputFlavorString + " bagel flavor is already in the list");
				else
				{
					bagelFlavorsComboBox.addItem(inputFlavorString);//only adds to combo box not array
	    			JOptionPane.showMessageDialog(null, inputFlavorString + " bagel flavor has been added to the list");
				}
			}
			if(creamCheeseRadioButton.isSelected())
			{
				//Search the items for a match
				boolean foundFlavorBoolean = findDuplicateCreamCheese(inputFlavorString);
				if(foundFlavorBoolean)
	    			JOptionPane.showMessageDialog(null, inputFlavorString + " cream cheese flavor is already in the list");
				else
				{
					creamCheeseFlavorsComboBox.addItem(inputFlavorString);//only adds to combo box not array
	    			JOptionPane.showMessageDialog(null, inputFlavorString + " cream cheese flavor has been added to the list");
				}
			}			
    	}	        	
		else
        	{//send message to user to select bagel or cream cheese
    			JOptionPane.showMessageDialog(null, "Please type in the flavor of Bagel or Cream cheese");
    			newFlavorTextField.requestFocus();		
    		}
		//reset info
		invisibleRadioButton.setSelected(true);
		newFlavorTextField.setText("");		
		creamCheeseFlavorsComboBox.setSelectedIndex(0);
		bagelFlavorsComboBox.setSelectedIndex(0);
		}    				

	//checks that bagel flavor is not already there
	public boolean findDuplicateBagels(String inputString)
	{
		boolean itemFoundBoolean = false;
		String listItemString = "";
		int indexInteger = 0;
		int countTotalBagelsInteger = bagelFlavorsComboBox.getItemCount(); //Gets number in combo box
		
		//loop runs until there is nothing left to check
		while (!itemFoundBoolean && indexInteger < countTotalBagelsInteger)
		{		
			listItemString = String.valueOf(bagelFlavorsComboBox.getItemAt(indexInteger));
			
			if(inputString.equalsIgnoreCase(listItemString)) //makes sure "plain" is same as "PLain"
			{
				itemFoundBoolean = true;
			}
			indexInteger++;
		}		
		return itemFoundBoolean;
	}

	//checks that cream cheese flavor is not already there
	public boolean findDuplicateCreamCheese(String inputString)
	{
		boolean itemFoundBoolean = false;
		String listItemString = "";
		int indexInteger = 0;
		int countTotalCreamCheeseInteger = creamCheeseFlavorsComboBox.getItemCount(); //Gets number in combo box
		
		while (!itemFoundBoolean && indexInteger < countTotalCreamCheeseInteger)
		{		
			listItemString = String.valueOf(creamCheeseFlavorsComboBox.getItemAt(indexInteger));
			
			if(inputString.equalsIgnoreCase(listItemString))
			{
				itemFoundBoolean = true;
			}
			indexInteger++;
		}		
		return itemFoundBoolean;
	}

	//actions to take based on radio button selection
	//ensures that Bagel or Cream cheese has been selected
	public boolean selectRadioButton()
	{
		boolean selectedBoolean;
		if(bagelRadioButton.isSelected()||creamCheeseRadioButton.isSelected())
		{
			selectedBoolean = true;
		}
		else
		{//send message to user to select bagel or cream cheese
			JOptionPane.showMessageDialog(null, "Please select Bagel or Cream cheese");
			bagelRadioButton.requestFocus();
			selectedBoolean = false;
		}
		return selectedBoolean;
	}
				
	//Validate all text fields to ensure that some entry was made in each
	//Send an appropriate error message, if no entry was made and reset the insertion point
	public boolean validation()
	{
	    boolean validationBoolean;
	    
	    if(selectRadioButton())//make sure food has been selected			    	
	    {	    		    	
			inputFlavorString = newFlavorTextField.getText();				
			if(inputFlavorString.trim().length() > 0)//make sure text field is not empty
	    	{
	    		validationBoolean = true; 	    		
	    	}	        	
			else
	        	{//send message to user to select bagel or cream cheese
	    			JOptionPane.showMessageDialog(null, "Please type in the flavor of Bagel or Cream cheese");
	    			newFlavorTextField.requestFocus();		
	    			validationBoolean = false;			    
	    		}
			}
		else		    	
		{//send message to user to select bagel or cream cheese
			JOptionPane.showMessageDialog(null, "Please select Bagel or Cream cheese");
			bagelRadioButton.requestFocus();
			validationBoolean = false;			    	
		}										
	    return validationBoolean;
		}
	
	//removes the existing flavor
	public void removeFlavor()
	{
    	selectRadioButton(); //first make sure a Bagel or Cream cheese radio button has been selected
    	
		if (bagelRadioButton.isSelected()) //removes bagel flavor
		{ 
            String itemString = bagelFlavorsComboBox.getSelectedItem().toString();
            int responseInt;
            // gives user choice to say yes, no, or cancel
            responseInt = JOptionPane.showConfirmDialog(null, "Remove the " +  itemString + " bagel flavor?");
            	if (responseInt==JOptionPane.YES_OPTION) //if yes
            	{
            		bagelFlavorsComboBox.removeItem(itemString);
            		JOptionPane.showMessageDialog(null, "The " + itemString + " bagel flavor has been removed.");
            	}
            	else //if no or cancel
            	{
            		JOptionPane.showMessageDialog(null, "The " + itemString + " bagel flavor has not been removed.");
            		invisibleRadioButton.setSelected(true);
            	}			
		}
		if (creamCheeseRadioButton.isSelected())
		{
            String item = creamCheeseFlavorsComboBox.getSelectedItem().toString();
            int response;
            response = JOptionPane.showConfirmDialog(null, "Remove the " +  item + " cream cheese flavor?");
            	if (response==JOptionPane.YES_OPTION)
            	{
            		creamCheeseFlavorsComboBox.removeItem(item);
            		JOptionPane.showMessageDialog(null, "The " + item + " cream cheese flavor has been removed.");
            	}
            	else
            	{
            		JOptionPane.showMessageDialog(null, "The " + item + " cream cheese flavor has not been removed.");
            		invisibleRadioButton.setSelected(true);
            	}			
		}
		invisibleRadioButton.setSelected(true);
		newFlavorTextField.setText("");
		creamCheeseFlavorsComboBox.setSelectedIndex(0);
		bagelFlavorsComboBox.setSelectedIndex(0);
		//reset info
	}
					
}//end of class

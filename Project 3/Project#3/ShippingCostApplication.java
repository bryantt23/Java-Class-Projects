//Folder/Project Name: Project#3
//Programmer Name: Bryant Tunbutr
//Date: April 1, 2013
//Class Name: ShippingCostApplication
/*Project Description: This shipping cost project uses a GUI to 
 * input the shipping code, weight in pounds and ounces,
 * and then outputs the total number of packages
 * and shipping costs using swing components
*/

import javax.swing.*;   //for swing components
import java.awt.*;      // for Font
import java.awt.event.*; //for ActionListener
import java.text.*;  //for DecimalFormat class

public class ShippingCostApplication extends JFrame
	implements ActionListener
{
	//declare your instance objects here
	JPanel mainPanel = new JPanel();
	JLabel iconLabel;
	JLabel companyLabel = new JLabel("Bryant's Shipping");
	JLabel idCodeLabel = new JLabel("6-digit Identification Code: ");
	JLabel poundsLabel = new JLabel ("Weight in pounds: ");
	JLabel ouncesLabel = new JLabel("Weight in ounces: ");
	JTextField idCodeTextField = new JTextField(20);
	JTextField poundsTextField = new JTextField(20);
	JTextField ouncesTextField = new JTextField(20);
	JButton calculateButton = new JButton("Calculate Shipping Charges");
	JTextArea outputTextArea = new JTextArea(5, 20);
	JScrollPane outputScrollPane = new JScrollPane(outputTextArea);  //For scroll bars around text area
	JLabel countLabel = new JLabel();
	JLabel totalPackagesLabel = new JLabel();
	JLabel programmerNameLabel = new JLabel("Maintained by Bryant Tunbutr");
	
	Font bigFont = new Font("Times New Roman", Font.BOLD, 28);
	
	//declare instance variables
	int totalPackagesCountInteger;
	double totalShippingCostDouble;
	
	//Declare constants needed
	final double SHIPPING_RATE_DOUBLE = .12D;
	final double POUNDS_TO_OUNCES_DOUBLE = 16D;

	// This is the first method called in a application
	//We will create an object of ourselves to call the
	//constructor and then set the default close operation for the frame
	public static void main(String[] args)
	{		
		ShippingCostApplication basicGUI = new ShippingCostApplication();
		basicGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//end of main

	//This is the constructor for this class. It will be called from main
	//It will set up our GUI panel with needed components
	//and set up appropriate event listening
	public ShippingCostApplication()
	{
		//call the superclass' constructor and send a title
		super("Shipping Cost Application");
		
		//add GUI components to the appropriate container
		mainPanel.add(companyLabel);
		companyLabel.setFont(bigFont);
		companyLabel.setForeground(Color.RED);
		iconLabel = new JLabel(new ImageIcon("shipping.jpg"));
		mainPanel.add(iconLabel);
		mainPanel.add(idCodeLabel);
		mainPanel.add(idCodeTextField);
		mainPanel.add(poundsLabel);
		mainPanel.add(poundsTextField);
		mainPanel.add(ouncesLabel);
		mainPanel.add(ouncesTextField);
		mainPanel.add(calculateButton);
		mainPanel.add(outputScrollPane);
		mainPanel.add(countLabel);
		mainPanel.add(totalPackagesLabel);
		mainPanel.add(programmerNameLabel);

		//add the JPanel to the JFrame
		add(mainPanel);
		
		//call the addListeners method for registering components
		addListeners();		

		//set the properties of the JFrame for display
		this.setSize(300, 550);
		this.setVisible(true);
	}//end of constructor
	
	//This method will register components with the ActionListener
	public void addListeners()
	{
		calculateButton.addActionListener(this);
		ouncesTextField.addActionListener(this);
	}//end of addListeners method
	
	//This method will collect user input, calculate the shipping cost
	//and display the results back to the user
	public void actionPerformed(ActionEvent event)
	{
		//declare variables and objects
		int poundsInteger = 0;
		int ouncesInteger = 0;
		double shippingCostDouble = 0;
		String tempString = "";
		
		DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");
		// NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
		
		//retrieve and convert our user input
		tempString = poundsTextField.getText();
		poundsInteger = Integer.parseInt(tempString);
		tempString = ouncesTextField.getText();
		ouncesInteger = Integer.parseInt(tempString);
		
		//calculate the gross pay and accumulate
		shippingCostDouble = (poundsInteger* POUNDS_TO_OUNCES_DOUBLE + ouncesInteger) * SHIPPING_RATE_DOUBLE;
		totalShippingCostDouble += shippingCostDouble;
		totalPackagesCountInteger++;
	
		//display the results back to the user
		outputTextArea.setText("ID Code: \t"+ idCodeTextField.getText());
		outputTextArea.append("\n" + "Weight: \t" + poundsInteger + " lb. ");
		outputTextArea.append(ouncesInteger + " oz.");
		tempString = currencyFormat.format(shippingCostDouble);
		outputTextArea.append("\nShipping Cost: \t" + tempString);
		
		countLabel.setText("Total packages: "+ totalPackagesCountInteger);
		tempString = currencyFormat.format(totalShippingCostDouble);
		totalPackagesLabel.setText("Total shipping cost: "+ tempString);				
	}

}//end of class

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
 * This is the calculation class
*/

import java.text.DecimalFormat;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class Calculation
{
	//Declare instance variables
	private int indexInt;
	private String navigationString;	
	private int milesDrivenInteger, milesDrivenTotalInteger, daysRentedInteger, daysRentedTotalInteger;

	private double mileageRateDouble;
	private int dailyRateInteger;
	private float daysRentedTimesDailyRateFloat;
	private double dailyRatePlusMileageFloat;
	
	//initialize values to 0 so that summary does not call null values, then convert to string below 
	private static int economyCarsCountInteger = 0;
	private static int  midsizeCarsCountInteger = 0;
	private static int fullsizeCarsCountInteger = 0;
	private static int luxuryCarsCountInteger = 0;
	private static int totalCarsCountInteger = 0;

	private static float economyDailyRatePlusMileageTotalFloat = 0;
	private static float midsizeDailyRatePlusMileageTotalFloat = 0;
	private static float fullsizeDailyRatePlusMileageTotalFloat = 0;
	private static float luxuryDailyRatePlusMileageTotalFloat = 0;
	private static float dailyRatePlusMileageTotalFloat = 0;
	
	//Format the values to currency format
	static DecimalFormat valueDecimalFormat = new DecimalFormat("$#0.00");

	//constants
	//cost per day by model	for calculations
	private static final String CAR_MODELS_PRICES_NO_NAVIGATION_VALUES_ARRAY_STRING [] = {"26", "40", "65", "85"};
	private static final String CAR_MODELS_PRICES_WITH_NAVIGATION_VALUES_ARRAY_STRING [] = {"31", "45", "70", "90"};
	private static final double CAR_MODELS_MILEAGE_VALUES_ARRAY_DOUBLE [] = {.15, .18, .25, .40};
	
	//cost per day by model	for display
	private static final String CAR_MODELS_PRICES_NO_NAVIGATION_ARRAY_STRING [] = {"$26 per day - $.15 per mile", 
		"$40 per day - $.18 per mile", 
		"$65 per day - $.25 per mile",
		"$85 per day - $.40 per mile" };
	private static final String CAR_MODELS_PRICES_WITH_NAVIGATION_ARRAY_STRING [] = {"$31 per day - $.15 per mile", 
		"$45 per day - $.18 per mile", 
		 "$70 per day - $.25 per mile", 
		 "$90 per day - $.40 per mile"};

	//constant for free miles
	final int FREE_MILES_INTEGER = 100;

	//constant for new day to reset to 0
	final int RESET_INTEGER = 0;
	final float RESET_FLOAT = 0;

	//strings used to share and display info between classes
	private static String priceString;
	private static String daysRentedTimesDailyRateString;
	private static String dailyRateString;
	private static String dailyRatePlusMileageString;
	
	private static String economyCarsCountString = Integer.toString(economyCarsCountInteger);
	private static String midsizeCarsCountString = Integer.toString(economyCarsCountInteger);
	private static String fullsizeCarsCountString = Integer.toString(economyCarsCountInteger);
	private static String luxuryCarsCountString = Integer.toString(economyCarsCountInteger);	
	private static String totalCarsCountString = Integer.toString(economyCarsCountInteger);
	
	private static String economyDailyRatePlusMileageTotalString = valueDecimalFormat.format(economyDailyRatePlusMileageTotalFloat);
	private static String midsizeDailyRatePlusMileageTotalString = valueDecimalFormat.format(midsizeDailyRatePlusMileageTotalFloat);
	private static String fullsizeDailyRatePlusMileageTotalString = valueDecimalFormat.format(fullsizeDailyRatePlusMileageTotalFloat);
	private static String luxuryDailyRatePlusMileageTotalString = valueDecimalFormat.format(luxuryDailyRatePlusMileageTotalFloat);
	private static String dailyRatePlusMileageTotalString = valueDecimalFormat.format(dailyRatePlusMileageTotalFloat);
	
	private static String daysRentedTotalString;

	private static String summaryString;
	private static String modelSelectString;
	
	JComboBox carModelsPricesNoNavigationStringsComboBox = new JComboBox(CAR_MODELS_PRICES_NO_NAVIGATION_ARRAY_STRING);
	JComboBox carModelsPricesWithNavigationStringsComboBox = new JComboBox(CAR_MODELS_PRICES_WITH_NAVIGATION_ARRAY_STRING);

	JComboBox carModelsPricesNoNavigationIntsComboBox = new JComboBox(CAR_MODELS_PRICES_NO_NAVIGATION_VALUES_ARRAY_STRING);
	JComboBox carModelsPricesWithNavigationIntsComboBox = new JComboBox(CAR_MODELS_PRICES_WITH_NAVIGATION_VALUES_ARRAY_STRING);

	//Declare constants

	//Declare class variables
		
	//Constructors--same name as class, no return type
	public Calculation(int indexInt, int daysRentedInteger, int milesDrivenInteger, String navigationString, boolean validationBoolean)
	{
		setRate(indexInt, daysRentedInteger, milesDrivenInteger, navigationString, validationBoolean);		
	}
	public Calculation(int indexInt, String navigationString)
	{
		displayDailyRate(indexInt, navigationString);	
	}
	public Calculation()
	{
		setNewDay();		
	}
		
	//resets all values to 0
	public void setNewDay()
	{				
		//resets values to 0
		dailyRatePlusMileageFloat = RESET_FLOAT;	
		
		economyDailyRatePlusMileageTotalFloat = RESET_FLOAT;	
		economyCarsCountInteger = RESET_INTEGER;				
		midsizeDailyRatePlusMileageTotalFloat = RESET_FLOAT;	
		midsizeCarsCountInteger = RESET_INTEGER;	
		fullsizeDailyRatePlusMileageTotalFloat = RESET_FLOAT;	
		fullsizeCarsCountInteger = RESET_INTEGER;	
		luxuryDailyRatePlusMileageTotalFloat = RESET_FLOAT;	
		luxuryCarsCountInteger = RESET_INTEGER;	
		totalCarsCountInteger = RESET_INTEGER;				
		dailyRatePlusMileageTotalFloat = RESET_FLOAT;			
		daysRentedTotalInteger = RESET_INTEGER;				
			
		//converts them to string for display		
		dailyRatePlusMileageString = valueDecimalFormat.format(dailyRatePlusMileageFloat);

		economyDailyRatePlusMileageTotalString = valueDecimalFormat.format(economyDailyRatePlusMileageTotalFloat);
		economyCarsCountString = Integer.toString(economyCarsCountInteger);
		midsizeDailyRatePlusMileageTotalString = valueDecimalFormat.format(midsizeDailyRatePlusMileageTotalFloat);
		midsizeCarsCountString = Integer.toString(midsizeCarsCountInteger);
		fullsizeDailyRatePlusMileageTotalString = valueDecimalFormat.format(fullsizeDailyRatePlusMileageTotalFloat);
		fullsizeCarsCountString = Integer.toString(fullsizeCarsCountInteger);
		luxuryDailyRatePlusMileageTotalString = valueDecimalFormat.format(luxuryDailyRatePlusMileageTotalFloat);
		luxuryCarsCountString = Integer.toString(luxuryCarsCountInteger);
		dailyRatePlusMileageTotalString = valueDecimalFormat.format(dailyRatePlusMileageTotalFloat);		
		totalCarsCountString = Integer.toString(totalCarsCountInteger);				
		daysRentedTotalString = Integer.toString(daysRentedTotalInteger);
	}			

	//this sets the rate
	public void setRate(int indexInt, int daysRentedInteger, int milesDrivenInteger, String navigationString, boolean validationBoolean)
	{
		//for no navigation gets the price
		if (navigationString.equals("No"))
		{
			dailyRateString = CAR_MODELS_PRICES_NO_NAVIGATION_VALUES_ARRAY_STRING[indexInt].toString();			
		}					
		else //with navigation gets the price
		{
			dailyRateString = CAR_MODELS_PRICES_WITH_NAVIGATION_VALUES_ARRAY_STRING[indexInt].toString();
		}				

		dailyRateInteger = Integer.parseInt(dailyRateString);
		
		//calculate cost due to days rented times daily rate
		daysRentedTimesDailyRateFloat = daysRentedInteger * dailyRateInteger;
		daysRentedTimesDailyRateString = valueDecimalFormat.format(daysRentedTimesDailyRateFloat);

		//gets mileage rate by model
		mileageRateDouble = CAR_MODELS_MILEAGE_VALUES_ARRAY_DOUBLE[indexInt];	
		
		//calculate cost due to mileage
		if (milesDrivenInteger < 101){//cost if less than 101 miles driven
			mileageRateDouble = 0;
		}
		else
		{//cost if more than 100 miles driven
			mileageRateDouble = (milesDrivenInteger - FREE_MILES_INTEGER) * mileageRateDouble;
		}
		
		// add up cost due to mileage and daily rate
		dailyRatePlusMileageFloat = daysRentedTimesDailyRateFloat + mileageRateDouble;	

		//accumulate  the totals
		daysRentedTotalInteger += daysRentedInteger;		
		dailyRatePlusMileageTotalFloat += dailyRatePlusMileageFloat;
		
		dailyRatePlusMileageString = valueDecimalFormat.format(dailyRatePlusMileageFloat);//adding this hope it works
				
		daysRentedTotalString = Integer.toString(daysRentedTotalInteger);
		dailyRatePlusMileageTotalString = valueDecimalFormat.format(dailyRatePlusMileageTotalFloat);
												
		if (indexInt == 0 && validationBoolean == true){//makes sure that only purchasing triggers accumulation
			
			economyDailyRatePlusMileageTotalFloat += dailyRatePlusMileageFloat;
			economyCarsCountInteger++;
			totalCarsCountInteger++;
			modelSelectString = "Economy";
		}
		else if(indexInt == 1 && validationBoolean == true){
		
			midsizeDailyRatePlusMileageTotalFloat += dailyRatePlusMileageFloat;
			midsizeCarsCountInteger++;
			totalCarsCountInteger++;
			modelSelectString = "Midsize";			
		}
		else if(indexInt == 2 && validationBoolean == true){
		
			fullsizeDailyRatePlusMileageTotalFloat += dailyRatePlusMileageFloat;
			fullsizeCarsCountInteger++;
			totalCarsCountInteger++;
			modelSelectString = "Fullsize";
		}
		else if(indexInt == 3 && validationBoolean == true){
			luxuryDailyRatePlusMileageTotalFloat += dailyRatePlusMileageFloat;
			luxuryCarsCountInteger++;
			totalCarsCountInteger++;
			modelSelectString = "Luxury";
		}		
	
		economyCarsCountString = Integer.toString(economyCarsCountInteger);
		midsizeCarsCountString = Integer.toString(midsizeCarsCountInteger);
		fullsizeCarsCountString = Integer.toString(fullsizeCarsCountInteger);
		luxuryCarsCountString = Integer.toString(luxuryCarsCountInteger);
		totalCarsCountString = Integer.toString(totalCarsCountInteger);

		economyDailyRatePlusMileageTotalString = valueDecimalFormat.format(economyDailyRatePlusMileageTotalFloat);
		midsizeDailyRatePlusMileageTotalString = valueDecimalFormat.format(midsizeDailyRatePlusMileageTotalFloat);
		fullsizeDailyRatePlusMileageTotalString = valueDecimalFormat.format(fullsizeDailyRatePlusMileageTotalFloat);
		luxuryDailyRatePlusMileageTotalString = valueDecimalFormat.format(luxuryDailyRatePlusMileageTotalFloat);		
	}			

	//this method will set the incoming rate
	public void displayDailyRate(int indexInt, String navigationString)
	{
		if (navigationString.equals("Yes"))
		{
			priceString = carModelsPricesWithNavigationStringsComboBox.getItemAt(indexInt).toString();
		}
		else
		{
			priceString = carModelsPricesNoNavigationStringsComboBox.getItemAt(indexInt).toString();			
		}		
	}	
	
	//this get methods make the info accessible to the other class
	public static String getEconomyDailyRatePlusMileageTotalString()
	{
		return economyDailyRatePlusMileageTotalString;
	}
	public static String getMidsizeDailyRatePlusMileageTotalString()
	{
		return midsizeDailyRatePlusMileageTotalString;
	}
	public static String getFullsizeDailyRatePlusMileageTotalString()
	{
		return fullsizeDailyRatePlusMileageTotalString;
	}
	public static String getLuxuryDailyRatePlusMileageTotalString()
	{
		return luxuryDailyRatePlusMileageTotalString;
	}	
	public static String getSummaryString()
	{
		return summaryString;
	}
	public static String getTotalCarsCountString()
	{
		return totalCarsCountString;
	}
	public static String getEconomyCarsCountString()
	{
		return economyCarsCountString;
	}
	public static String getMidsizeCarsCountString()
	{
		return midsizeCarsCountString;
	}
	public static String getFullsizeCarsCountString()
	{
		return fullsizeCarsCountString;
	}
	public static String getLuxuryCarsCountString()
	{
		return luxuryCarsCountString;
	}
	public static String getDaysRentedTotalString()
	{
		return daysRentedTotalString;
	}
	public static String getDailyRatePlusMileageTotalString()
	{
		return dailyRatePlusMileageTotalString;
	}
	public static String getDailyRatePlusMileageString()
	{
		return dailyRatePlusMileageString;
	}
	public static String getDaysRentedTimesDailyRateString()
	{
		return daysRentedTimesDailyRateString;
	}
	//this method will return the daily rate as a String
	public static String getRateString()
	{
		return dailyRateString;
	}	
	public static String getDaysRentedTimesDailyRate()
	{				
		return daysRentedTimesDailyRateString;
	}	
	public static String getDailyRate()
	{
		return priceString;
	}
	public static String getModelSelectString()
	{
		return modelSelectString;
	}
	

}
	
	
	
	
	
	
	
	
	

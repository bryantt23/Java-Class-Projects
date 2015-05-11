/**
 *
 * @author kenneth Odoh
 */

/**
	This is used to control log-in and  log-out
*/

/**
	This is covered under GPL licence version 2 or higher
*/

public class Confirm
{
	private static boolean status;
	public static void setStatus(String input)
	{
        if (input.equalsIgnoreCase("confirmed"))
		{
            status = true; //Transaction confirmed
		}
		else
		{
            status = false; //Transaction denied
		}
	}
	public static boolean isConfirmed()
	{
		return status;
	}
}

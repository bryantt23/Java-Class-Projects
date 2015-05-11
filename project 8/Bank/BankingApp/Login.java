/**
 *
 * @author kenneth Odoh
 */
/**
	This is covered under GPL licence version 2 or higher
*/

/**
	This is used confirmation of bank transaction
*/

public class Login
{
	private static boolean status;
	public static void login(String input)
	{
        if (input.equalsIgnoreCase("in"))
		{
            status = true; //log in
		}
        else if (input.equalsIgnoreCase("out"))
		{
            status = false;	//log out
			System.exit(0); // exit normally
		}
	}
	public static boolean isLoggedIn()
	{
		return status;
	}
}

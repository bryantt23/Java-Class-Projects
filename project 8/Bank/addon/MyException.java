/**
 *
 * @author kenneth Odoh
 */

/**
	This is covered under GPL licence version 2 or higher
*/

import javax.swing.JOptionPane;

public class MyException extends Exception {

	public MyException(String result) {
		JOptionPane.showMessageDialog(null, result);
	}

}

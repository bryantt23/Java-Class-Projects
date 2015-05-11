/**
 *
 * @author kenneth Odoh
 */

/**
	This is covered under GPL licence version 2 or higher
*/

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.regex.PatternSyntaxException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
public class DisplayGenericTable extends JFrame
{
	// JDBC database URL, username and password
	static final String DRIVER = "com.mysql.jdbc.Driver";
	static final String DATABASE_URL = "jdbc:mysql://localhost/BankDB";
	static final String USERNAME = "jhtp7";
	static final String PASSWORD = "jhtp7";
	// default query retrieves all data from authors table
	private String DEFAULT_QUERY;
	private ResultSetTableModel tableModel;

	// create ResultSetTableModel and GUI
	public DisplayGenericTable(String title, String strQuery)
	{
		super(title );
		// create ResultSetTableModel and display database table
		
		try
		{
			DEFAULT_QUERY = strQuery;
			// create TableModel for results of query SELECT * FROM authors
			tableModel = new ResultSetTableModel( DRIVER, DATABASE_URL,
			USERNAME, PASSWORD, DEFAULT_QUERY );

			JButton submitButton = new JButton( "Submit Query" );
			submitButton.setVisible(false);

			// create JTable delegate for tableModel
			JTable resultTable = new JTable( tableModel );
			JLabel filterLabel = new JLabel( "Filter:" );
			final JTextField filterText = new JTextField();
			JButton filterButton = new JButton( "Apply Filter" );
			Box boxSouth =  Box.createHorizontalBox();
			boxSouth.add( filterLabel );
			boxSouth.add( filterText );
			boxSouth.add( filterButton );
			// place GUI components on content pane
			add( submitButton, BorderLayout.NORTH );
			add( new JScrollPane( resultTable ), BorderLayout.CENTER );
			add( boxSouth, BorderLayout.SOUTH );
			// create event listener for submitButton
			submitButton.addActionListener
			(	new ActionListener()
				{	// pass query to table model
					public void actionPerformed( ActionEvent event )
					{
						// perform a new query
						try
						{
							tableModel.setQuery(DEFAULT_QUERY );
						} // end try
						catch ( SQLException sqlException )
						{
							JOptionPane.showMessageDialog( null,
							sqlException.getMessage(), "Database error",
							JOptionPane.ERROR_MESSAGE );
							// try to recover from invalid user query
							// by executing default query
							tableModel.disconnectFromDatabase();
							System.exit( 1 ); // terminate application
						} // end outer catch
					} // end actionPerformed
				} // end ActionListener inner class
			); // end call to addActionListener
			final TableRowSorter< TableModel > sorter =
			new TableRowSorter< TableModel >( tableModel );
			resultTable.setRowSorter( sorter );
			setSize( 500, 250 ); // set window size
			setVisible( true ); // display window
			// create listener for filterButton
			filterButton.addActionListener
				(	new ActionListener()
					{	// pass filter text to listener
						public void actionPerformed( ActionEvent e )
						{
							String text = filterText.getText();
							if ( text.length() == 0 )
							sorter.setRowFilter( null );
							else
							{
								try
								{
									sorter.setRowFilter(
									RowFilter.regexFilter( text ) );
								} // end try
								catch ( PatternSyntaxException pse )
								{
									JOptionPane.showMessageDialog( null,
									"Bad regex pattern", "Bad regex pattern",
									JOptionPane.ERROR_MESSAGE );
								} // end catch
							} // end else
						} // end method actionPerfomed
					} // end annonymous inner class
				); // end call to addActionLister
		} // end try
		catch ( ClassNotFoundException classNotFound )
		{
			JOptionPane.showMessageDialog( null,
			"Database Driver not found", "Driver not found",
			JOptionPane.ERROR_MESSAGE );
		} // end catch
		catch ( SQLException sqlException )
		{
			JOptionPane.showMessageDialog( null, sqlException.getMessage(),
			"Database error", JOptionPane.ERROR_MESSAGE );
			// ensure database connection is closed
			tableModel.disconnectFromDatabase();
			System.exit( 1 ); // terminate application
		} // end catch
		// dispose of window when user quits application (this overrides
		// the default of HIDE_ON_CLOSE)
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		// ensure database connection is closed when user quits application
		addWindowListener(
			new WindowAdapter()
			{
					// disconnect from database and exit when window has closed
					public void windowClosed( WindowEvent event )
					{
						tableModel.disconnectFromDatabase();
					} // end method windowClosed
			} // end WindowAdapter inner class
		); // end call to addWindowListener
		
	} // end DisplayGenericTable constructor
} // end class DisplayGenericTable


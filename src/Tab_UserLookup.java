import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import connection.ExecuteSqlQuery;

import actions.userLookupAction;
import extras.AlternatingColorTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author moorejm
 * 
 */
public class Tab_UserLookup extends JPanel {

	private static JScrollPane lookupScrollPane;
	private static AlternatingColorTable lookupTable;
	private static JTextField userLookupFirstNameField;
	private static JTextField userLookupLastNameField;
	private static JTextField userLookupEmailField;
	private static JTextField userLookupUsernameField;
	private static Connection SQLConnect;
	private JButton btnSearch = new JButton("Search");
	private JButton btnCreate = new JButton("Create");

	private Object user_data[][] = { { null, null, null, null },
			{ null, null, null, null }, { null, null, null, null },
			{ null, null, null, null }, { null, null, null, null },
			{ null, null, null, null }, { null, null, null, null },
			{ null, null, null, null }, { null, null, null, null },
			{ null, null, null, null }, { null, null, null, null },
			{ null, null, null, null }, { null, null, null, null },
			{ null, null, null, null }, };
	private String user_col[] = { "Username", "First Name", "Last Name",
			"E-mail" };

	private DefaultTableModel model = new DefaultTableModel(user_data, user_col);
	private ArrayList<Object> departmentNames;

	public Tab_UserLookup(Connection sql, boolean admin) {
		SQLConnect = sql;
		initialize();
		 if (admin)
		setAdminView();
		 else setNonAdminView();
	}

	private void initialize() {
		setLayout(null);

		lookupScrollPane = new JScrollPane();
		lookupScrollPane.getViewport().setBackground(new Color(190, 190, 190));
		lookupScrollPane.setBounds(10, 11, 660, 261);
		add(lookupScrollPane);

		lookupTable = new AlternatingColorTable(model);
		// lookupTable.setBorder(new BevelBorder(BevelBorder.LOWERED,
		// Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
		// Color.LIGHT_GRAY));
		// RowSorter<TableModel> sorter = new
		// TableRowSorter<TableModel>(model);
		// lookupTable.setRowSorter(sorter);
		lookupScrollPane.setViewportView(lookupTable);
		lookupTable.setBackground(Color.WHITE);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(30, 286, 147, 14);
		add(lblUsername);

		userLookupUsernameField = new JTextField();
		userLookupUsernameField.setBounds(90, 283, 86, 20);
		add(userLookupUsernameField);
		userLookupUsernameField.setColumns(10);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(186, 286, 157, 14);
		add(lblFirstName);

		userLookupFirstNameField = new JTextField();
		userLookupFirstNameField.setBounds(257, 283, 86, 20);
		add(userLookupFirstNameField);
		userLookupFirstNameField.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(357, 286, 147, 14);
		add(lblLastName);

		userLookupLastNameField = new JTextField();
		userLookupLastNameField.setBounds(418, 283, 86, 20);
		add(userLookupLastNameField);
		userLookupLastNameField.setColumns(10);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(530, 286, 123, 14);
		add(lblEmail);

		userLookupEmailField = new JTextField();
		userLookupEmailField.setBounds(561, 283, 86, 20);
		add(userLookupEmailField);
		userLookupEmailField.setColumns(10);

		userLookupAction userLookup = new userLookupAction(lookupScrollPane,
				userLookupFirstNameField, userLookupLastNameField,
				userLookupEmailField, userLookupUsernameField, SQLConnect);
		btnSearch.addActionListener(userLookup);
		add(btnSearch);

		btnSearch.setBounds(10, 324, 333, 55);
		btnCreate.setBounds(353, 324, 317, 55);
		add(btnCreate);
	}

	public DefaultTableModel getModel() {
		return this.model;
	}

	private void setNonAdminView() {
		btnSearch.setBounds(253, 322, 173, 55);
		remove(btnCreate);
//		btnCreate.setVisible(false);
	}

	private void setAdminView() {
		add(btnCreate);
//		btnCreate.setVisible(true);
		btnSearch.setBounds(10, 324, 333, 55);
		btnCreate.setBounds(353, 324, 317, 55);
		getDeptNames();
		
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getDeptNames();
				CreateUserWindow.newCreateUserWindow(SQLConnect, departmentNames);
			}

		});
	}

	private void getDeptNames() {
		Object[][] departmentTableArray = ExecuteSqlQuery
				.getDepartmentOverview(SQLConnect);

		departmentNames = new ArrayList<Object>();
		for (int i = 0; i < departmentTableArray.length; i++) {
			departmentNames.add(departmentTableArray[i][1]);
		}
	}

}

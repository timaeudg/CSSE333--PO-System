import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.Color;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import actions.userLookupAction;

import connection.ExecuteSqlQuery;
import connection.LoggedInUserWrapper;
import extras.AlternatingColorTable;
import extras.NumberRenderer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author moorejm, timaeudg
 *
 */
public class NormalUserWindow extends JFrame {

	private JPanel contentPane;
	private JTable poTable;
	private JTable lookupTable;
	private static JTable departmentOverviewTable;
	private JTextField lookupFirstField;
	private JTextField lookupUserLastField;
	private JTextField lookupEmailField;
	private JTextField lookupUsernameField;
	private static JScrollPane userScrollPane;
	private static JScrollPane deptScrollPane;
	private static JScrollPane poSrollPane;

	private static Connection SQLConnect;
	private static LoggedInUserWrapper user;
	private static ArrayList<Object> departmentNames;
	private static Object[][] departmentTableArray;

	// /**
	// * Launch the application.
	// */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// NormalUserWindow frame = new NormalUserWindow();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the frame.
	 */
	public NormalUserWindow() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		// ///User Lookup//////////////////////////////////////////////////////

		JPanel tab_userLookup = new JPanel();
		tabbedPane.addTab("User Lookup", null, tab_userLookup, null);
		tab_userLookup.setLayout(null);

		userScrollPane = new JScrollPane();
		userScrollPane.setBounds(10, 11, 468, 178);
		tab_userLookup.add(userScrollPane);

		lookupTable = new JTable();
		userScrollPane.setViewportView(lookupTable);

		JLabel lblFname = new JLabel("First Name:");
		lblFname.setBounds(10, 210, 61, 14);
		tab_userLookup.add(lblFname);

		lookupFirstField = new JTextField();
		lookupFirstField.setColumns(10);
		lookupFirstField.setBounds(85, 207, 86, 20);
		tab_userLookup.add(lookupFirstField);

		JLabel lblLname = new JLabel("Last Name:");
		lblLname.setBounds(181, 210, 61, 14);
		tab_userLookup.add(lblLname);

		lookupUserLastField = new JTextField();
		lookupUserLastField.setColumns(10);
		lookupUserLastField.setBounds(252, 207, 86, 20);
		tab_userLookup.add(lookupUserLastField);

		lookupEmailField = new JTextField();
		lookupEmailField.setColumns(10);
		lookupEmailField.setBounds(252, 245, 86, 20);
		tab_userLookup.add(lookupEmailField);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(181, 248, 43, 14);
		tab_userLookup.add(lblEmail);

		lookupUsernameField = new JTextField();
		lookupUsernameField.setColumns(10);
		lookupUsernameField.setBounds(85, 245, 86, 20);
		tab_userLookup.add(lookupUsernameField);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 248, 61, 14);
		tab_userLookup.add(lblUsername);

		JButton button = new JButton("Search");
		userLookupAction userLookup = new userLookupAction(userScrollPane,
				lookupFirstField, lookupUserLastField, lookupEmailField,
				lookupUsernameField, SQLConnect);
		button.addActionListener(userLookup);
		button.setBounds(389, 226, 89, 23);
		tab_userLookup.add(button);

		// /////Payment
		// Orders////////////////////////////////////////////////////

		JPanel tab_PO = new JPanel();
		tabbedPane.addTab("Your Payment Orders", null, tab_PO, null);
		tab_PO.setLayout(null);

		poSrollPane = new JScrollPane();
		poSrollPane.setBounds(10, 11, 468, 227);
		tab_PO.add(poSrollPane);

		poTable = new AlternatingColorTable(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null },
						{ null, null, null, null, null, null }, },
				new String[] { "New column", "New column", "New column",
						"New column", "New column", "New column" }));
		poSrollPane.setViewportView(poTable);
		poTable.setBackground(Color.LIGHT_GRAY);

		JButton btnCreateNewPayment = new JButton("Create New Payment Order");
		btnCreateNewPayment.setBounds(158, 249, 165, 23);
		btnCreateNewPayment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				getDeptNames();
				CreatePaymentOrder.setVisible(SQLConnect, departmentNames,
						user.getUsername());

			}
		});
		tab_PO.add(btnCreateNewPayment);

		JPanel tab_DeptView = new JPanel();
		tabbedPane.addTab("Department Overview", null, tab_DeptView, null);
		tab_DeptView.setLayout(null);

		deptScrollPane = new JScrollPane();
		deptScrollPane.setBounds(10, 11, 470, 228);
		tab_DeptView.add(deptScrollPane);

//		Object[][] depart = ExecuteSqlQuery.getDepartmentOverview(SQLConnect);
//		String[] departColumns = new String[] { "Department ID",
//				"Department Name", "Total Budget", "Current Budget",
//				"Parent Department ID" };

//		DefaultTableModel updateModel = new DefaultTableModel(depart,
//				departColumns);
//		departmentOverviewTable = new AlternatingColorTable(updateModel);
		refreshDepartments();
	}

	public static void setVisible(Connection whaleConnect,
			LoggedInUserWrapper userInfo) {
		user = userInfo;
		SQLConnect = whaleConnect;
		NormalUserWindow window = new NormalUserWindow();
		window.setVisible(true);

	}

	public static void refreshDepartments() {
		String[] departColumns = new String[] { "Department ID",
				"Department Name", "Total Budget", "Current Budget",
				"Parent Department ID" };

		getDeptNames();

		// JTable updateTable = new JTable(departmentTableArray, departColumns);
		departmentOverviewTable = new AlternatingColorTable(new DefaultTableModel(
				departmentTableArray, departColumns));
//		departmentOverviewTable.setModel(new DefaultTableModel(
//				departmentTableArray, departColumns));

		formatDeptTable();
		deptScrollPane.setViewportView(departmentOverviewTable);
	}

	public static void getDeptNames() {
		departmentTableArray = ExecuteSqlQuery
				.getDepartmentOverview(SQLConnect);

		departmentNames = new ArrayList<Object>();
		for (int i = 0; i < departmentTableArray.length; i++) {
			departmentNames.add(departmentTableArray[i][1]);
		}
	}

	public static void formatDeptTable() {
		TableColumnModel m = departmentOverviewTable.getColumnModel();
		m.getColumn(0).setCellRenderer(NumberRenderer.getIntegerRenderer());
		m.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
		m.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());
	}
}

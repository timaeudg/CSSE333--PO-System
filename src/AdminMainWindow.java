import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import actions.userLookupAction;
import connection.ExecuteSqlQuery;
import connection.LoggedInUserWrapper;
import extras.AlternatingColorTable;
import extras.FormatRenderer;
import extras.NumberRenderer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;

import sun.swing.MenuItemLayoutHelper.ColumnAlignment;

public class AdminMainWindow {

	private JFrame POFrame;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private JTextField EditRemoveLookupField;

	private JTextField newEmailField;
	private JTextField newPasswordField;
	private JTextField newFirstNameField;
	private JTextField newLastNameField;
	private JTextField userLookupUsernameField;
	private JTextField userLookupFirstNameField;
	private JTextField userLookupLastNameField;
	private JTextField userLookupEmailField;
	private JTable lookupTable;
	private static JTable departmentOverviewTable;
	private static JTable userPaymentOrdersTable;
	private static JTable pendingTable;
	private JRadioButton createUser;
	private JRadioButton createPO;

	private static JComboBox<String> departmentSelect;
	private static Connection SQLConnect;
	private static LoggedInUserWrapper user;
	private JTextField newUsernameField;
	private static ArrayList<Object> departmentNames;
	private static Object[][] departmentTableArray;
	private static String[][] userPaymentArray;
	private static Object[][] pendingPaymentArray;

	public static void setVisible(Connection connect,
			LoggedInUserWrapper userInfo) {
		user = userInfo;
		AdminMainWindow window = new AdminMainWindow(connect);
		window.POFrame.setVisible(true);
		// SQLConnect = connect;
	}

	/**
	 * Create the application.
	 */
	public AdminMainWindow(Connection connect) {
		this.SQLConnect = connect;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		POFrame = new JFrame();
		POFrame.setTitle("P-O-System");
		POFrame.setBounds(100, 100, 700, 460);
		POFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		POFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("Create New Objects", null, layeredPane, null);

		createUser = new JRadioButton("User");
		buttonGroup.add(createUser);
		createUser.setBounds(35, 50, 109, 23);
		layeredPane.add(createUser);

		createPO = new JRadioButton("Payment Order");
		buttonGroup.add(createPO);
		createPO.setBounds(35, 88, 109, 23);
		layeredPane.add(createPO);

		JButton createButton = new JButton("Create!");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (createUser.isSelected()) {
					CreateUserWindow.newCreateUserWindow(SQLConnect,
							departmentNames);
				} else if (createPO.isSelected()) {
					CreatePaymentOrder.setVisible(SQLConnect, departmentNames,
							user.getUsername());
				} else {

				}
			}
		});
		createButton.setBounds(263, 334, 141, 49);
		layeredPane.add(createButton);

		DefaultTableModel pendingModel = new DefaultTableModel(new Object[][] {
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

		new String[] { "ID", "Reason", "Reimbursement Method", "",
				"New column", "New column" });

		JLayeredPane layeredPane_1 = new JLayeredPane();
		tabbedPane.addTab("Pending Reimbursements", null, layeredPane_1, null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 659, 304);
		layeredPane_1.add(scrollPane);
		pendingTable = new AlternatingColorTable(pendingModel);
		pendingTable
				.setModel(new DefaultTableModel(new Object[][] {
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null },
						{ null, null, null, null, null }, }, new String[] {
						"ID", "Reason", "Reimbursement Method", "Date",
						"Creator Username" }));
		pendingTable.getColumnModel().getColumn(0).setResizable(false);
		scrollPane.setViewportView(pendingTable);
		// pendingTable.setFont(new Font("Courier New", Font.PLAIN, 12));
		pendingTable.setBackground(Color.LIGHT_GRAY);

		departmentSelect = new JComboBox();
		departmentSelect.setBounds(10, 347, 159, 20);
		layeredPane_1.add(departmentSelect);

		JButton acceptButton = new JButton("Accept Reimbursement");
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = pendingTable.getSelectedRow();
				int id = (Integer) pendingTable.getValueAt(row, 0);
				ExecuteSqlQuery.acceptPaymentOrder(SQLConnect, id,
						user.getUsername());

				System.out.println(id);
				refreshPending();
				refreshPaymentOrders();
			}
		});
		acceptButton.setBounds(365, 346, 159, 23);
		layeredPane_1.add(acceptButton);

		JButton rejectButton = new JButton("Deny Reimbursement");
		rejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = pendingTable.getSelectedRow();
				int id = (Integer) pendingTable.getValueAt(row, 0);
				String username = (String) pendingTable.getValueAt(row, 4);
				System.out.println(id + " " + username);

				ExecuteSqlQuery.rejectPaymentOrder(SQLConnect, id, username);
				refreshPending();
				refreshPaymentOrders();
			}
		});
		rejectButton.setBounds(534, 346, 135, 23);
		layeredPane_1.add(rejectButton);

		JButton btnRefreshTable = new JButton("Refresh Table");
		btnRefreshTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminMainWindow.refreshPending();
				refreshPaymentOrders();
			}
		});
		btnRefreshTable.setBounds(179, 346, 120, 23);
		layeredPane_1.add(btnRefreshTable);

		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Edit/Remove User", null, layeredPane_2, null);

		EditRemoveLookupField = new JTextField();
		EditRemoveLookupField.setBounds(260, 11, 154, 20);
		layeredPane_2.add(EditRemoveLookupField);
		EditRemoveLookupField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Username:");

		lblNewLabel.setBounds(193, 14, 57, 14);
		layeredPane_2.add(lblNewLabel);

		newEmailField = new JTextField();
		newEmailField.setBounds(124, 259, 86, 20);
		layeredPane_2.add(newEmailField);
		newEmailField.setColumns(10);

		newPasswordField = new JTextField();
		newPasswordField.setBounds(124, 290, 86, 20);
		layeredPane_2.add(newPasswordField);
		newPasswordField.setColumns(10);

		newFirstNameField = new JTextField();
		newFirstNameField.setBounds(328, 259, 86, 20);
		layeredPane_2.add(newFirstNameField);
		newFirstNameField.setColumns(10);

		newLastNameField = new JTextField();
		newLastNameField.setBounds(328, 290, 86, 20);
		layeredPane_2.add(newLastNameField);
		newLastNameField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("New E-mail:");
		lblNewLabel_1.setBounds(57, 262, 57, 14);
		layeredPane_2.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("New Password: ");
		lblNewLabel_2.setBounds(37, 293, 77, 14);
		layeredPane_2.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("New First Name:");
		lblNewLabel_3.setBounds(239, 262, 86, 14);
		layeredPane_2.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("New Last Name:");
		lblNewLabel_4.setBounds(239, 293, 79, 14);
		layeredPane_2.add(lblNewLabel_4);

		JButton btnNewButton_3 = new JButton("Make Changes");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String oldUsername = EditRemoveLookupField.getText();
				String newEmail = newEmailField.getText();
				String newFirstName = newFirstNameField.getText();
				String newLastName = newLastNameField.getText();
				String newPassword = newPasswordField.getText();
				String newUsername = newUsernameField.getText();

				if (oldUsername.equals(user.getUsername())) {

					if (JOptionPane
							.showConfirmDialog(
									POFrame,
									"You are about to make edits to yourself, are you sure you would like to do this?") == 0) {
						ExecuteSqlQuery.editUser(oldUsername, newUsername,
								newFirstName, newLastName, newEmail,
								newPassword, SQLConnect);
					}
				} else {

					ExecuteSqlQuery.editUser(oldUsername, newUsername,
							newFirstName, newLastName, newEmail, newPassword,
							SQLConnect);

				}
			}
		});
		btnNewButton_3.setBounds(215, 338, 103, 23);
		layeredPane_2.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Remove User");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deletee = EditRemoveLookupField.getText();

				if (deletee.equals(user.getUsername())) {

					if (JOptionPane
							.showConfirmDialog(
									POFrame,
									"You are about to delete yourself\nIf you continue, you will be logged out and deleted from the database\nare you sure you would like to do this?") == 0) {
						ExecuteSqlQuery.removeUser(user.getUsername(), deletee,
								SQLConnect);
						try {
							SQLConnect.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						POFrame.dispose();
					}
				} else {

					ExecuteSqlQuery.removeUser(user.getUsername(), deletee,
							SQLConnect);

				}

			}
		});
		btnNewButton_4.setBounds(487, 323, 126, 52);
		layeredPane_2.add(btnNewButton_4);

		newUsernameField = new JTextField();
		newUsernameField.setBounds(487, 290, 126, 20);
		layeredPane_2.add(newUsernameField);
		newUsernameField.setColumns(10);

		JLabel lblNewUsername = new JLabel("New Username: ");
		lblNewUsername.setBounds(487, 262, 86, 14);
		layeredPane_2.add(lblNewUsername);

		JButton removeFromDepartment = new JButton("Remove From Department");
		removeFromDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = EditRemoveLookupField.getText();
				RemoveUserFromDepartment.setVisible(SQLConnect,
						departmentNames, user);
			}
		});
		removeFromDepartment.setBounds(57, 110, 189, 23);
		layeredPane_2.add(removeFromDepartment);

		JButton btnNewButton_6 = new JButton("Remove From Chair Position");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = EditRemoveLookupField.getText();
				RemoveUserFromChairperson.setVisible(SQLConnect,
						departmentNames, user);
			}
		});
		btnNewButton_6.setBounds(57, 144, 189, 23);
		layeredPane_2.add(btnNewButton_6);

		JButton addToDeparment = new JButton("Add To Department");
		addToDeparment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUserToDeparment.setVisible(SQLConnect, departmentNames,
						EditRemoveLookupField.getText());
			}
		});
		addToDeparment.setBounds(388, 110, 185, 23);
		layeredPane_2.add(addToDeparment);

		JButton addAsChairperson = new JButton("Add as Chairperson");
		addAsChairperson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = EditRemoveLookupField.getText();
				AddUserAsChairperson.setVisible(SQLConnect, departmentNames,
						user);
			}
		});
		addAsChairperson.setBounds(388, 144, 185, 23);
		layeredPane_2.add(addAsChairperson);

		// ===================================================================
		// User LookUp
		// ===================================================================

		JLayeredPane layeredPane_3 = new JLayeredPane();
		tabbedPane.addTab("User Lookup", null, layeredPane_3, null);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 660, 224);
		layeredPane_3.add(scrollPane_2);

		Object user_data[][] = { { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, };
		String user_col[] = { "Username", "First Name", "Last Name", "E-mail" };

		DefaultTableModel model = new DefaultTableModel(user_data, user_col);

		lookupTable = new AlternatingColorTable(model);
		lookupTable.setBorder(new BevelBorder(BevelBorder.LOWERED,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
				Color.LIGHT_GRAY));
		// RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		// lookupTable.setRowSorter(sorter);
		scrollPane_2.setViewportView(lookupTable);
		lookupTable.setBackground(Color.LIGHT_GRAY);

		userLookupFirstNameField = new JTextField();
		userLookupFirstNameField.setBounds(85, 282, 86, 20);
		layeredPane_3.add(userLookupFirstNameField);
		userLookupFirstNameField.setColumns(10);

		userLookupLastNameField = new JTextField();
		userLookupLastNameField.setBounds(252, 282, 86, 20);
		layeredPane_3.add(userLookupLastNameField);
		userLookupLastNameField.setColumns(10);

		userLookupEmailField = new JTextField();
		userLookupEmailField.setBounds(401, 282, 86, 20);
		layeredPane_3.add(userLookupEmailField);
		userLookupEmailField.setColumns(10);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(10, 285, 61, 14);
		layeredPane_3.add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(181, 285, 61, 14);
		layeredPane_3.add(lblLastName);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(348, 285, 43, 14);
		layeredPane_3.add(lblEmail);

		JButton btnSearch = new JButton("Search");
		userLookupAction userLookup = new userLookupAction(scrollPane_2,
				userLookupFirstNameField, userLookupLastNameField,
				userLookupEmailField, userLookupEmailField, SQLConnect);
		btnSearch.addActionListener(userLookup);
		btnSearch.setBounds(291, 342, 89, 23);
		layeredPane_3.add(btnSearch);

		userLookupUsernameField = new JTextField();
		userLookupUsernameField.setBounds(581, 282, 86, 20);
		layeredPane_3.add(userLookupUsernameField);
		userLookupUsernameField.setColumns(10);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(510, 285, 61, 14);
		layeredPane_3.add(lblUsername);

		// ===================================================================
		// Department Overview
		// ===================================================================

		JLayeredPane layeredPane_4 = new JLayeredPane();
		tabbedPane.addTab("Department Overview", null, layeredPane_4, null);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 11, 659, 309);
		layeredPane_4.add(scrollPane_3);

		Object[][] dept_data = new Object[][] { { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, };
		String[] dept_cols = { "Department name", "Budget", "New column",
				"New column" };

		DefaultTableModel dept_model = new DefaultTableModel(dept_data,
				dept_cols);

		departmentOverviewTable = new AlternatingColorTable(dept_model);
		departmentOverviewTable.setBackground(Color.LIGHT_GRAY);
		// DepartmentOverviewTable.setBorder(new
		// BevelBorder(BevelBorder.LOWERED, null, null,
		// null, null));
		departmentOverviewTable.setBorder(new BevelBorder(BevelBorder.LOWERED,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
				Color.LIGHT_GRAY));

		scrollPane_3.setViewportView(departmentOverviewTable);

		AdminMainWindow.refreshDepartments();

		JButton btnEditDepartments = new JButton("Edit Departments");
		btnEditDepartments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EditDepartmentsWindow.setVisible(SQLConnect, departmentNames);

			}
		});
		btnEditDepartments.setBounds(109, 360, 115, 23);
		layeredPane_4.add(btnEditDepartments);

		JButton btnAddDepartment = new JButton("Add Department");
		btnAddDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CreateDepartment.setVisible(SQLConnect, departmentNames);

			}
		});

		JButton btnRemoveDepartment = new JButton("Remove Department");
		btnRemoveDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DeleteDepartmentWindow.setVisible(SQLConnect, departmentNames);

			}
		});
		btnRemoveDepartment.setBounds(261, 360, 131, 23);
		layeredPane_4.add(btnRemoveDepartment);
		btnAddDepartment.setBounds(428, 360, 115, 23);
		layeredPane_4.add(btnAddDepartment);

		JLayeredPane layeredPane_5 = new JLayeredPane();
		tabbedPane.addTab("Your Payment Orders", null, layeredPane_5, null);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 11, 659, 372);
		layeredPane_5.add(scrollPane_4);

		userPaymentOrdersTable = new AlternatingColorTable(model);
		scrollPane_4.setViewportView(userPaymentOrdersTable);
		AdminMainWindow.refreshPaymentOrders();

	}

	public static void refreshDepartments() {
		String[] departColumns = new String[] { "Department ID",
				"Department Name", "Total Budget", "Current Budget",
				"Parent Department ID" };

		departmentTableArray = ExecuteSqlQuery
				.getDepartmentOverview(SQLConnect);

		departmentNames = new ArrayList<Object>();
		for (int i = 0; i < departmentTableArray.length; i++) {
			departmentNames.add(departmentTableArray[i][1]);
		}
		departmentSelect.setModel(new DefaultComboBoxModel(user.getChairs()
				.toArray()));

		// JTable updateTable = new JTable(departmentTableArray, departColumns);
		departmentOverviewTable.setModel(new DefaultTableModel(
				departmentTableArray, departColumns));
		TableColumnModel m = departmentOverviewTable.getColumnModel();
		m.getColumn(0).setCellRenderer(NumberRenderer.getIntegerRenderer());
		m.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
		m.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());

		departmentOverviewTable.repaint();

	}

	public static void refreshPaymentOrders() {
		String[] poColumns = new String[] { "Reason", "Reimbursement Method",
				"Date", "Origin Department", "Status",
				"Department of Current Status" };

		userPaymentArray = ExecuteSqlQuery.getUserPaymentOrders(SQLConnect,
				user.getUsername());

		// JTable updateTable = new JTable(userPaymentArray, departColumns);
		userPaymentOrdersTable.setModel(new DefaultTableModel(userPaymentArray,
				poColumns));
		userPaymentOrdersTable.repaint();

	}

	public static void refreshPending() {
		String[] poColumns = new String[] { "ID", "Reason",
				"Reimbursement Method", "Date", "Creator Username" };

		String currentDept = (String) departmentSelect.getSelectedItem();

		// System.out.println(currentDept);

		pendingPaymentArray = ExecuteSqlQuery.getPendingOrders(SQLConnect,
				currentDept);

		pendingTable.setModel(new DefaultTableModel(pendingPaymentArray,
				poColumns));
		pendingTable.repaint();

	}
}

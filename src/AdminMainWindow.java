import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import connection.ExecuteSqlQuery;
import connection.LoggedInUserWrapper;
import extras.AlternatingColorTable;
import extras.NumberRenderer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JComboBox;

import javax.swing.JPanel;

/**
 * @author timaeudg, moorejm
 *
 */
public class AdminMainWindow {

	private JFrame POFrame;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	private static JTable departmentOverviewTable;
	private static JTable userPaymentOrdersTable;
	private static JTable pendingTable;
	private JRadioButton createUser;
	private JRadioButton createPO;

	private static JComboBox<String> departmentSelect;
	private static Connection SQLConnect;
	private static LoggedInUserWrapper user;
	private static ArrayList<Object> departmentNames;
	private static Object[][] departmentTableArray;
	private static String[][] userPaymentArray;
	private static Object[][] pendingPaymentArray;
	private static JScrollPane poScrollPane;
	private static JScrollPane pendingScrollPane;
	private static JScrollPane deptScrollPane;

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
		POFrame.setBounds(100, 100, 690, 460);
		POFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		POFrame.setResizable(false);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		POFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// ===================================================================
		// User Function Tabs
		// ===================================================================

		Tab_UserLookup tab_Lookup = new Tab_UserLookup(SQLConnect, user.getChairs().size() > 0);
		Tab_EditRemove tab_EditRemove;

		// ==================================================================
		// Create New Objects
		// ==================================================================
		JPanel tab_Create = new JPanel();
		tabbedPane.addTab("Create New Objects", null, tab_Create, null);
		tab_Create.setLayout(null);

		createPO = new JRadioButton("Payment Order");
		buttonGroup.add(createPO);
		createPO.setBounds(239, 100, 99, 23);
		tab_Create.add(createPO);

		JButton createButton = new JButton("Create!");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 if (createPO.isSelected()) {
					CreatePaymentOrder.setVisible(SQLConnect, departmentNames,
							user.getUsername());
				} else {

				}
			}
		});
		createButton.setBounds(239, 229, 200, 60);
		tab_Create.add(createButton);

		// ===================================================================
		// Your Payment Orders
		// ===================================================================
		JPanel tab_PO = new JPanel();
		tabbedPane.addTab("Your Payment Orders", null, tab_PO, null);
		tab_PO.setLayout(null);

		poScrollPane = new JScrollPane();
		poScrollPane.setBounds(10, 11, 659, 372);
		poScrollPane.getViewport().setBackground(new Color(190, 190, 190));
		tab_PO.add(poScrollPane);

		userPaymentOrdersTable = new AlternatingColorTable(
				tab_Lookup.getModel());
		// poScrollPane.setViewportView(userPaymentOrdersTable);
		AdminMainWindow.refreshPaymentOrders();

		// ====================================================================
		// Pending Reimbursements
		// ====================================================================

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

		JPanel tab_Pending = new JPanel();
		tabbedPane.addTab("Pending Reimbursements", null, tab_Pending, null);
		tab_Pending.setLayout(null);

		pendingScrollPane = new JScrollPane();
		pendingScrollPane.setBounds(10, 11, 659, 304);
		pendingScrollPane.getViewport().setBackground(new Color(190, 190, 190));
		tab_Pending.add(pendingScrollPane);
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
		pendingScrollPane.setViewportView(pendingTable);
		// pendingTable.setFont(new Font("Courier New", Font.PLAIN, 12));
//		pendingTable.setBackground(Color.LIGHT_GRAY);

		departmentSelect = new JComboBox();
		departmentSelect.setBounds(10, 347, 159, 20);
		tab_Pending.add(departmentSelect);

		JButton acceptButton = new JButton("Accept Reimbursement");
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = pendingTable.getSelectedRow();
				int id = Integer.parseInt((String) pendingTable.getValueAt(row,
						0));
				ExecuteSqlQuery.acceptPaymentOrder(SQLConnect, id,
						user.getUsername());

				System.out.println(id);
				refreshPending();
				refreshPaymentOrders();
			}
		});
		acceptButton.setBounds(365, 346, 159, 23);
		tab_Pending.add(acceptButton);

		JButton rejectButton = new JButton("Deny Reimbursement");
		rejectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = pendingTable.getSelectedRow();
				int id = Integer.parseInt((String) pendingTable.getValueAt(row,
						0));
				String username = (String) pendingTable.getValueAt(row, 4);
				System.out.println(id + " " + username);

				ExecuteSqlQuery.rejectPaymentOrder(SQLConnect, id, username);
				refreshPending();
				refreshPaymentOrders();
			}
		});
		rejectButton.setBounds(534, 346, 135, 23);
		tab_Pending.add(rejectButton);

		JButton btnRefreshTable = new JButton("Refresh Table");
		btnRefreshTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshPending();
				refreshPaymentOrders();
			}
		});
		btnRefreshTable.setBounds(179, 346, 120, 23);
		tab_Pending.add(btnRefreshTable);

		// ===================================================================
		// Add User Function Tabs
		// ===================================================================

		tabbedPane.addTab("User Lookup", null, tab_Lookup, null);
		buildDeptTableArray(); // get department names
		tab_EditRemove = new Tab_EditRemove(POFrame, SQLConnect, user,
				departmentNames);
		tabbedPane.addTab("Edit/Remove User", null, tab_EditRemove, null);

		// ===================================================================
		// Department Overview
		// ===================================================================

		JPanel tab_Dept = new JPanel();
		tabbedPane.addTab("Department Overview", null, tab_Dept, null);
		tab_Dept.setLayout(null);

		deptScrollPane = new JScrollPane();
		deptScrollPane.setBounds(10, 11, 659, 309);
		deptScrollPane.getViewport().setBackground(new Color(190, 190, 190));
		tab_Dept.add(deptScrollPane);

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
//		departmentOverviewTable.setBackground(Color.LIGHT_GRAY);
		// DepartmentOverviewTable.setBorder(new
		// BevelBorder(BevelBorder.LOWERED, null, null,
		// null, null));
//		departmentOverviewTable.setBorder(new BevelBorder(BevelBorder.LOWERED,
//				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
//				Color.LIGHT_GRAY));

		AdminMainWindow.refreshDepartments();

		JButton btnEditDepartments = new JButton("Edit Departments");
		btnEditDepartments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EditDepartmentsWindow.setVisible(SQLConnect, departmentNames);

			}
		});
		btnEditDepartments.setBounds(10, 331, 215, 62);
		tab_Dept.add(btnEditDepartments);

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
		btnRemoveDepartment.setBounds(455, 331, 215, 62);
		tab_Dept.add(btnRemoveDepartment);
		btnAddDepartment.setBounds(235, 331, 210, 62);
		tab_Dept.add(btnAddDepartment);

	}

	public static void buildDeptTableArray() {
		departmentTableArray = ExecuteSqlQuery
				.getDepartmentOverview(SQLConnect);

		departmentNames = new ArrayList<Object>();
		for (int i = 0; i < departmentTableArray.length; i++) {
			departmentNames.add(departmentTableArray[i][1]);
		}
	}

	public static void refreshDepartments() {
		String[] departColumns = new String[] { "Department ID",
				"Department Name", "Total Budget", "Current Budget",
				"Parent Department ID" };

		buildDeptTableArray();

		departmentSelect.setModel(new DefaultComboBoxModel(user.getChairs()
				.toArray()));

		// JTable updateTable = new JTable(departmentTableArray, departColumns);
		departmentOverviewTable.setModel(new DefaultTableModel(
				departmentTableArray, departColumns));
		TableColumnModel m = departmentOverviewTable.getColumnModel();
		m.getColumn(0).setCellRenderer(NumberRenderer.getIntegerRenderer());
		m.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
		m.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());

		deptScrollPane.setViewportView(departmentOverviewTable);
		departmentOverviewTable.repaint();
	}

	public static void refreshPaymentOrders() {
		String[] poColumns = new String[] { "Reason", "Reimbursement Method",
				"Date", "Origin Department", "Status",
				"Department of Current Status" };

		userPaymentArray = ExecuteSqlQuery.getUserPaymentOrders(SQLConnect,
				user.getUsername());

		DefaultTableModel model = new DefaultTableModel(userPaymentArray,
				poColumns);
		userPaymentOrdersTable.setModel(model);

		poScrollPane.setViewportView(userPaymentOrdersTable);
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
		pendingScrollPane.setViewportView(pendingTable);
		pendingTable.repaint();
	}

//	class Tab_UserLookup extends JPanel {
//
//		
//
//		public Tab_UserLookup() {
//			super();
//			setLayout(null);
//
//			// JScrollPane scrollPane_2 = new JScrollPane();
//			lookupScrollPane.setBounds(10, 11, 660, 224);
//			add(lookupScrollPane);
//
//			lookupTable = new AlternatingColorTable(model);
//			lookupTable.setBorder(new BevelBorder(BevelBorder.LOWERED,
//					Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
//					Color.LIGHT_GRAY));
//			// RowSorter<TableModel> sorter = new
//			// TableRowSorter<TableModel>(model);
//			// lookupTable.setRowSorter(sorter);
//			lookupScrollPane.setViewportView(lookupTable);
//			lookupTable.setBackground(Color.LIGHT_GRAY);
//
//			userLookupFirstNameField = new JTextField();
//			userLookupFirstNameField.setBounds(238, 282, 86, 20);
//			add(userLookupFirstNameField);
//			userLookupFirstNameField.setColumns(10);
//
//			userLookupLastNameField = new JTextField();
//			userLookupLastNameField.setBounds(399, 282, 86, 20);
//			add(userLookupLastNameField);
//			userLookupLastNameField.setColumns(10);
//
//			userLookupEmailField = new JTextField();
//			userLookupEmailField.setBounds(551, 282, 86, 20);
//			add(userLookupEmailField);
//			userLookupEmailField.setColumns(10);
//
//			userLookupUsernameField = new JTextField();
//			userLookupUsernameField.setBounds(71, 282, 86, 20);
//			add(userLookupUsernameField);
//			userLookupUsernameField.setColumns(10);
//
//			JLabel lblUsername = new JLabel("Username:");
//			lblUsername.setBounds(10, 285, 61, 14);
//			add(lblUsername);
//
//			JLabel lblFirstName = new JLabel("First Name:");
//			lblFirstName.setBounds(167, 285, 61, 14);
//			add(lblFirstName);
//
//			JLabel lblLastName = new JLabel("Last Name:");
//			lblLastName.setBounds(338, 285, 61, 14);
//			add(lblLastName);
//
//			JLabel lblEmail = new JLabel("E-mail:");
//			lblEmail.setBounds(511, 285, 43, 14);
//			add(lblEmail);
//
//			JButton btnSearch = new JButton("Search");
//			userLookupAction userLookup = new userLookupAction(
//					lookupScrollPane, userLookupFirstNameField,
//					userLookupLastNameField, userLookupEmailField,
//					userLookupEmailField, SQLConnect);
//			btnSearch.addActionListener(userLookup);
//			btnSearch.setBounds(291, 342, 89, 23);
//			add(btnSearch);
//
//		}
//
//		public DefaultTableModel getModel() {
//			return this.model;
//		}
//	}
}

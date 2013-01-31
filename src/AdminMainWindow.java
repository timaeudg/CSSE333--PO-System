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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import connection.ExecuteSqlQuery;
import connection.LoggedInUserWrapper;
import extras.AlternatingColorTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminMainWindow {

	private JFrame POFrame;
	private JTextField PONumberField;
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
	private JTable departmentOverviewTable;
	private JTable table_2;

	private JRadioButton createUser;
	private JRadioButton createPO;
	private JRadioButton createDepartment;


	private static Connection SQLConnect;
	private static LoggedInUserWrapper user;
	private JTextField newUsernameField;
	private static ArrayList<String> departmentNames;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AdminMainWindow window = new AdminMainWindow();
//					window.POFrame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static void setVisible(Connection connect, LoggedInUserWrapper userInfo) {
		AdminMainWindow window = new AdminMainWindow(connect);
		window.POFrame.setVisible(true);
//		SQLConnect = connect;
		user = userInfo;
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
		
		String [][] depart = ExecuteSqlQuery.getDepartmentOverview(SQLConnect);
		departmentNames = new ArrayList<String>();
		for(int i = 0; i<depart.length;i++){
			departmentNames.add(depart[i][1]);
		}
		
		POFrame = new JFrame();
		POFrame.getContentPane().setBackground(Color.WHITE);
		POFrame.setTitle("P-O-System");
		POFrame.setForeground(Color.WHITE);
		POFrame.setBounds(100, 100, 700, 460);
		POFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.LIGHT_GRAY);
		POFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.LIGHT_GRAY);
		layeredPane.setForeground(Color.BLACK);
		tabbedPane.addTab("Create New Objects", null, layeredPane, null);
		tabbedPane.setForegroundAt(0, Color.BLACK);
		tabbedPane.setBackgroundAt(0, Color.LIGHT_GRAY);

		createUser = new JRadioButton("User");
		buttonGroup.add(createUser);
		createUser.setBounds(35, 50, 109, 23);
		layeredPane.add(createUser);

		createPO = new JRadioButton("Payment Order");
		buttonGroup.add(createPO);
		createPO.setBounds(35, 88, 109, 23);
		layeredPane.add(createPO);

		createDepartment = new JRadioButton("Department");
		buttonGroup.add(createDepartment);
		createDepartment.setBounds(35, 130, 109, 23);
		layeredPane.add(createDepartment);

		JButton btnNewButton_2 = new JButton("Create!");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (createUser.isSelected()) {
					CreateUserWindow.newCreateUserWindow(SQLConnect, departmentNames);
				}
				else if(createPO.isSelected()){
					CreatePaymentOrder.setVisible(SQLConnect);
				}
				else{
					
				}
			}
		});
		btnNewButton_2.setBounds(263, 334, 141, 49);
		layeredPane.add(btnNewButton_2);

		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Pending Reimbursements", null, layeredPane_1, null);

		PONumberField = new JTextField();
		PONumberField.setBounds(10, 347, 168, 20);
		layeredPane_1.add(PONumberField);
		PONumberField.setColumns(10);

		JButton btnNewButton = new JButton("Accept Reimbursement");
		btnNewButton.setBounds(219, 346, 159, 23);
		layeredPane_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Deny Reimbursement");
		btnNewButton_1.setBounds(388, 346, 135, 23);
		layeredPane_1.add(btnNewButton_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 659, 304);
		layeredPane_1.add(scrollPane);

		JTable PendingTable = new JTable();
		PendingTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		scrollPane.setViewportView(PendingTable);
		PendingTable.setFont(new Font("Courier New", Font.PLAIN, 12));
		PendingTable.setBackground(Color.LIGHT_GRAY);

		JLabel lblPaymentOrderNumbers = new JLabel("Payment Order Numbers:");
		lblPaymentOrderNumbers.setBounds(10, 322, 122, 14);
		layeredPane_1.add(lblPaymentOrderNumbers);

		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Edit/Remove User", null, layeredPane_2, null);

		EditRemoveLookupField = new JTextField();
		EditRemoveLookupField.setBounds(260, 11, 154, 20);
		layeredPane_2.add(EditRemoveLookupField);
		EditRemoveLookupField.setColumns(10);

//		JScrollPane scrollPane_1 = new JScrollPane();
//		scrollPane_1.setBounds(28, 64, 621, 180);
//		layeredPane_2.add(scrollPane_1);
//
//		JTable EditRemoveTable = new JTable();
//		EditRemoveTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
//		EditRemoveTable.setModel(new DefaultTableModel(
//			new Object[][] {
//				{null, null, null, null, null},
//				{null, null, null, null, null},
//				{null, null, null, null, null},
//				{null, null, null, null, null},
//				{null, null, null, null, null},
//				{null, null, null, null, null},
//				{null, null, null, null, null},
//				{null, null, null, null, null},
//				{null, null, null, null, null},
//				{null, null, null, null, null},
//				{null, null, null, null, null},
//			},
//			new String[] {
//				"New column", "New column", "New column", "New column", "New column"
//			}
//		) {
//			boolean[] columnEditables = new boolean[] {
//				false, true, true, true, true
//			};
//			public boolean isCellEditable(int row, int column) {
//				return columnEditables[column];
//			}
//		});
//		scrollPane_1.setViewportView(EditRemoveTable);
//		EditRemoveTable.setBackground(Color.LIGHT_GRAY);

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
				
				if(oldUsername.equals(user.getUsername())){
					
					if(JOptionPane.showConfirmDialog(POFrame, "You are about to make edits to yourself, are you sure you would like to do this?")==0){
						ExecuteSqlQuery.editUser(oldUsername, newUsername, newFirstName, newLastName, newEmail, newPassword, SQLConnect);
					}
				}
				else{
				
				ExecuteSqlQuery.editUser(oldUsername, newUsername, newFirstName, newLastName, newEmail, newPassword, SQLConnect);
				
				}
			}
		});
		btnNewButton_3.setBounds(215, 338, 103, 23);
		layeredPane_2.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Remove User");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deletee = EditRemoveLookupField.getText();
				
				if(deletee.equals(user.getUsername())){
					
					if(JOptionPane.showConfirmDialog(POFrame, "You are about to delete yourself\nIf you continue, you will be logged out and deleted from the database\nare you sure you would like to do this?")==0){
						ExecuteSqlQuery.removeUser(user.getUsername(), deletee, SQLConnect);
						try {
							SQLConnect.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						POFrame.dispose();
					}
				}
				else{
				
					ExecuteSqlQuery.removeUser(user.getUsername(), deletee, SQLConnect);
				
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
				
				RemoveUserFromDepartment.setVisible(SQLConnect, departmentNames);
			}
		});
		removeFromDepartment.setBounds(57, 110, 189, 23);
		layeredPane_2.add(removeFromDepartment);
		
		JButton btnNewButton_6 = new JButton("Remove From Chair Position");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RemoveUserFromChairperson.setVisible(SQLConnect, departmentNames);
			}
		});
		btnNewButton_6.setBounds(57, 144, 189, 23);
		layeredPane_2.add(btnNewButton_6);
		
		JButton addToDeparment = new JButton("Add To Department");
		addToDeparment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUserToDeparment.setVisible(SQLConnect, departmentNames, EditRemoveLookupField.getText());
			}
		});
		addToDeparment.setBounds(388, 110, 185, 23);
		layeredPane_2.add(addToDeparment);
		
		JButton addAsChairperson = new JButton("Add as Chairperson");
		addAsChairperson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = EditRemoveLookupField.getText();
				AddUserAsChairperson.setVisible(SQLConnect, departmentNames, user);
			}
		});
		addAsChairperson.setBounds(388, 144, 185, 23);
		layeredPane_2.add(addAsChairperson);

		//===================================================================
		// User LookUp
		//===================================================================

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
		
		DefaultTableModel model = new DefaultTableModel(user_data, user_col) {
			Class[] columnTypes = { String.class, String.class, String.class,
					String.class };

			public Class getColumnClass(int column) {
				return columnTypes[column];
			}
		};
		
		lookupTable = new AlternatingColorTable(model);
		lookupTable.setBorder(new BevelBorder(BevelBorder.LOWERED,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
				Color.LIGHT_GRAY));
//		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
//		lookupTable.setRowSorter(sorter);
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
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstName = userLookupFirstNameField.getText().replaceAll("\\*", "%");
				String lastName = userLookupLastNameField.getText().replaceAll("\\*", "%");
				String email = userLookupEmailField.getText().replaceAll("\\*", "%");
				String username = userLookupUsernameField.getText().replaceAll("\\*", "%");
				ResultSet rs = null;

				try {
					rs = ExecuteSqlQuery.lookupUsers(firstName, lastName,
							email, username, SQLConnect);
					if (rs != null) {

						ArrayList<String[]> rows = new ArrayList<String[]>();
						String[] row = new String[4];
						while (rs.next()) {
							for (int j = 1; j < 5; j++) {
								row[j - 1] = rs.getString(j);
							}
							rows.add(row);
							row = new String[4];
						}

						int numberOfRows = rows.size();
						String[][] data = new String[numberOfRows][4];

						// System.out.println(rows.toString());

						for (int k = 0; k < numberOfRows; k++) {
							data[k] = rows.get(k);
						}
						rs.close();
						String[] columns = new String[] { "Username",
								"First Name", "Last Name", "E-mail" };

						JTable updateTable = new JTable(data, columns);
						lookupTable.setModel(updateTable.getModel());
						lookupTable.repaint();

					} else {
						System.out.println("Herp Derp");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(291, 342, 89, 23);
		layeredPane_3.add(btnSearch);

		userLookupUsernameField = new JTextField();
		userLookupUsernameField.setBounds(581, 282, 86, 20);
		layeredPane_3.add(userLookupUsernameField);
		userLookupUsernameField.setColumns(10);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(510, 285, 61, 14);
		layeredPane_3.add(lblUsername);
		
		//===================================================================
		// Department Overview
		//===================================================================

		JLayeredPane layeredPane_4 = new JLayeredPane();
		tabbedPane.addTab("Department Overview", null, layeredPane_4, null);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 11, 659, 309);
		layeredPane_4.add(scrollPane_3);

		Object[][] dept_data = new Object[][] {
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null }, };
		String[] dept_cols = { "Department name", "Budget", "New column",
		"New column" };
		
		DefaultTableModel dept_model = new DefaultTableModel(dept_data, dept_cols) {
			Class[] columnTypes = { Integer.class, String.class, Double.class,
					Double.class, Integer.class };

			public Class getColumnClass(int column) {
				return columnTypes[column];
			}
		};
		
		departmentOverviewTable = new AlternatingColorTable(dept_model);
		departmentOverviewTable.setBackground(Color.LIGHT_GRAY);
//		DepartmentOverviewTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
//				null, null));
		departmentOverviewTable.setBorder(new BevelBorder(BevelBorder.LOWERED,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.LIGHT_GRAY,
				Color.LIGHT_GRAY));
		
		scrollPane_3.setViewportView(departmentOverviewTable);
		
		String[] departColumns = new String[] { "Department ID",
				"Department Name", "Total Budget", "Current Budget", "Parent Department ID" };
		
		JTable updateTable = new JTable(depart, departColumns);
		departmentOverviewTable.setModel(updateTable.getModel());
		departmentOverviewTable.repaint();
		
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

		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(new Object[][] {
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
				{ null, null, null, null, null }, }, new String[] { "Reason",
				"Cost", "Status", "New column", "New column" }));
		scrollPane_4.setViewportView(table_2);
		
		
	}
}

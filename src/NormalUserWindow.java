import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import com.sun.org.apache.bcel.internal.generic.LLOAD;

import connection.ExecuteSqlQuery;
import connection.LoggedInUserWrapper;
import extras.AlternatingColorTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class NormalUserWindow extends JFrame {

	
	private JPanel contentPane;
	private JTable table;
	private JTable lookupTable;
	private JTextField lookupFirstField;
	private JTextField lookupUserLastField;
	private JTextField lookupEmailField;
	private JTextField lookupUsernameField;
	private JTable table_2;
	
	private static Connection SQLConnect;
	private static LoggedInUserWrapper user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NormalUserWindow frame = new NormalUserWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		
		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("User Lookup", null, layeredPane, null);
		
		final JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 468, 178);
		layeredPane.add(scrollPane_1);
		
		lookupTable = new JTable();
		scrollPane_1.setViewportView(lookupTable);
		
		JLabel label = new JLabel("First Name:");
		label.setBounds(10, 210, 61, 14);
		layeredPane.add(label);
		
		lookupFirstField = new JTextField();
		lookupFirstField.setColumns(10);
		lookupFirstField.setBounds(85, 207, 86, 20);
		layeredPane.add(lookupFirstField);
		
		JLabel label_1 = new JLabel("Last Name:");
		label_1.setBounds(181, 210, 61, 14);
		layeredPane.add(label_1);
		
		lookupUserLastField = new JTextField();
		lookupUserLastField.setColumns(10);
		lookupUserLastField.setBounds(252, 207, 86, 20);
		layeredPane.add(lookupUserLastField);
		
		lookupEmailField = new JTextField();
		lookupEmailField.setColumns(10);
		lookupEmailField.setBounds(252, 245, 86, 20);
		layeredPane.add(lookupEmailField);
		
		JLabel label_2 = new JLabel("E-mail:");
		label_2.setBounds(181, 248, 43, 14);
		layeredPane.add(label_2);
		
		lookupUsernameField = new JTextField();
		lookupUsernameField.setColumns(10);
		lookupUsernameField.setBounds(85, 245, 86, 20);
		layeredPane.add(lookupUsernameField);
		
		JLabel label_3 = new JLabel("Username:");
		label_3.setBounds(10, 248, 61, 14);
		layeredPane.add(label_3);
		
		JButton button = new JButton("Search");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstName = lookupFirstField.getText().replaceAll("\\*", "%");
				String lastName = lookupUserLastField.getText().replaceAll("\\*", "%");
				String email = lookupEmailField.getText().replaceAll("\\*", "%");
				String username = lookupUsernameField.getText().replaceAll("\\*", "%");
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

						JTable updateTable = new AlternatingColorTable(new DefaultTableModel(data, columns));
//						lookupTable.setModel(updateTable.getModel());
						scrollPane_1.setViewportView(updateTable);
//						lookupTable.repaint();

					} else {
						System.out.println("Herp Derp");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
				
		});
		button.setBounds(389, 226, 89, 23);
		layeredPane.add(button);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		tabbedPane.addTab("Your Payment Orders", null, layeredPane_1, null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 468, 227);
		layeredPane_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBackground(Color.LIGHT_GRAY);
		table.setModel(
				new DefaultTableModel(
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
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		
		JButton btnCreateNewPayment = new JButton("Create New Payment Order");
		btnCreateNewPayment.setBounds(158, 249, 165, 23);
		layeredPane_1.add(btnCreateNewPayment);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Department Overview", null, layeredPane_2, null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 470, 228);
		layeredPane_2.add(scrollPane_2);
		
//		table_2 = new JTable();
		
		String [][] depart = ExecuteSqlQuery.getDepartmentOverview(SQLConnect);
		String[] departColumns = new String[] { "Department ID",
				"Department Name", "Total Budget", "Current Budget", "Parent Department ID" };
		
		DefaultTableModel updateModel = new DefaultTableModel(depart, departColumns);
		JTable updateTable = new AlternatingColorTable(updateModel);
		scrollPane_2.setViewportView(updateTable);
//		table_2 = updateTable;
//		table_2.repaint();
		
	}

	public static void setVisible(Connection whaleConnect,
			LoggedInUserWrapper userInfo) {
		user = userInfo;
		SQLConnect = whaleConnect;
		NormalUserWindow window = new NormalUserWindow();
		window.setVisible(true);
		
	}
}

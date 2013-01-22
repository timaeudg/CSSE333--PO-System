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


public class AdminMainWindow {

	private JFrame POFrame;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMainWindow window = new AdminMainWindow();
					window.POFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminMainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			
		}
		POFrame = new JFrame();
		POFrame.getContentPane().setBackground(Color.WHITE);
		POFrame.setTitle("P-O-System");
		POFrame.setForeground(Color.WHITE);
		POFrame.setBounds(100, 100, 700, 460);
		POFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		POFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.WHITE);
		layeredPane.setForeground(Color.BLACK);
		tabbedPane.addTab("Create New Objects", null, layeredPane, null);
		
		JButton btnNewButton_2 = new JButton("Create!");
		btnNewButton_2.setBounds(263, 334, 141, 49);
		layeredPane.add(btnNewButton_2);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("User");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(35, 50, 109, 23);
		layeredPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Payment Order");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(35, 88, 109, 23);
		layeredPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Something Else?");
		buttonGroup.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setBounds(35, 130, 109, 23);
		layeredPane.add(rdbtnNewRadioButton_2);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Pending Reimbursements", null, layeredPane_1, null);
		
		textField = new JTextField();
		textField.setBounds(10, 347, 168, 20);
		layeredPane_1.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Accept Reimbursement");
		btnNewButton.setBounds(219, 346, 159, 23);
		layeredPane_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Deny Reimbursement");
		btnNewButton_1.setBounds(388, 346, 135, 23);
		layeredPane_1.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 659, 304);
		layeredPane_1.add(scrollPane);
		
		JTextArea txtrThisIsStarting = new JTextArea();
		scrollPane.setViewportView(txtrThisIsStarting);
		txtrThisIsStarting.setEditable(false);
		txtrThisIsStarting.setFont(new Font("Courier New", Font.PLAIN, 12));
		txtrThisIsStarting.setLineWrap(true);
		txtrThisIsStarting.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblPaymentOrderNumbers = new JLabel("Payment Order Numbers:");
		lblPaymentOrderNumbers.setBounds(10, 322, 122, 14);
		layeredPane_1.add(lblPaymentOrderNumbers);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Edit/Remove User", null, layeredPane_2, null);
		
		textField_1 = new JTextField();
		textField_1.setBounds(260, 11, 154, 20);
		layeredPane_2.add(textField_1);
		textField_1.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(28, 64, 621, 180);
		layeredPane_2.add(scrollPane_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);
		textArea.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblCurrentUserInformation = new JLabel("Current User Information");
		scrollPane_1.setColumnHeaderView(lblCurrentUserInformation);
		
		JLabel lblNewLabel = new JLabel("User E-mail:");
		lblNewLabel.setBounds(193, 14, 57, 14);
		layeredPane_2.add(lblNewLabel);
		
		textField_2 = new JTextField();
		textField_2.setBounds(124, 259, 86, 20);
		layeredPane_2.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(124, 290, 86, 20);
		layeredPane_2.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(328, 259, 86, 20);
		layeredPane_2.add(textField_4);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setBounds(328, 290, 86, 20);
		layeredPane_2.add(textField_5);
		textField_5.setColumns(10);
		
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
		btnNewButton_3.setBounds(215, 338, 103, 23);
		layeredPane_2.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Remove User");
		btnNewButton_4.setBounds(493, 258, 126, 52);
		layeredPane_2.add(btnNewButton_4);
		
		JLayeredPane layeredPane_3 = new JLayeredPane();
		tabbedPane.addTab("User Lookup", null, layeredPane_3, null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 659, 260);
		layeredPane_3.add(scrollPane_2);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setEditable(false);
		scrollPane_2.setViewportView(textArea_1);
		textArea_1.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblUserInformation = new JLabel("User Information");
		scrollPane_2.setColumnHeaderView(lblUserInformation);
		
		textField_6 = new JTextField();
		textField_6.setBounds(89, 282, 86, 20);
		layeredPane_3.add(textField_6);
		textField_6.setColumns(10);
		
		textField_7 = new JTextField();
		textField_7.setBounds(294, 282, 86, 20);
		layeredPane_3.add(textField_7);
		textField_7.setColumns(10);
		
		textField_8 = new JTextField();
		textField_8.setBounds(495, 282, 86, 20);
		layeredPane_3.add(textField_8);
		textField_8.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(18, 285, 61, 14);
		layeredPane_3.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(223, 285, 61, 14);
		layeredPane_3.add(lblLastName);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(442, 285, 43, 14);
		layeredPane_3.add(lblEmail);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(291, 342, 89, 23);
		layeredPane_3.add(btnSearch);
		
		JLayeredPane layeredPane_4 = new JLayeredPane();
		tabbedPane.addTab("Department Overview", null, layeredPane_4, null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 11, 659, 372);
		layeredPane_4.add(scrollPane_3);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setEditable(false);
		scrollPane_3.setViewportView(textArea_2);
		textArea_2.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblDepartmentOverviewInformation = new JLabel("Department Overview Information");
		scrollPane_3.setColumnHeaderView(lblDepartmentOverviewInformation);
		
		JLayeredPane layeredPane_5 = new JLayeredPane();
		tabbedPane.addTab("New tab", null, layeredPane_5, null);
		
		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 11, 659, 372);
		layeredPane_5.add(scrollPane_4);
		
		JTextArea textArea_3 = new JTextArea();
		scrollPane_4.setViewportView(textArea_3);
		textArea_3.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblOwnedPaymentOrders = new JLabel("Owned Payment Orders");
		scrollPane_4.setColumnHeaderView(lblOwnedPaymentOrders);
	}
}

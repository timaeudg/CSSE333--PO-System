
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

import connection.ExecuteSqlQuery;
import connection.LineItemWrapper;
import connection.ReceiptBundles;


public class CreatePaymentOrder extends JFrame {

	private JPanel contentPane;
	private static Connection SQLConnect;
	private static JComboBox<String> departmentSelect;
	private static ArrayList<Object> departList;
	private static String username;
	private static JTextArea descriptionField;
	private static CreatePaymentOrder window;
	private static JFormattedTextField dateField;
	private static JLabel invalidLabel;
	private static ArrayList<LineItemWrapper> lineItems;
	private static JLabel lineSum;
	private static ArrayList<ReceiptBundles> receipts;
	private static JComboBox<String> methodOfRepayment;
	private static boolean admin;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreatePaymentOrder frame = new CreatePaymentOrder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void setVisible(Connection connect, ArrayList<Object> departs, String user, boolean adminWin) {
		SQLConnect = connect;
		departList=departs;
		username=user;
		admin = adminWin;
		receipts = new ArrayList<ReceiptBundles>();
		lineItems = new ArrayList<LineItemWrapper>();
		window = new CreatePaymentOrder();
		window.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public CreatePaymentOrder() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		departmentSelect = new JComboBox();
		departmentSelect.setBounds(235, 34, 136, 20);
		departmentSelect.setModel(new DefaultComboBoxModel(departList.toArray()));
		layeredPane.add(departmentSelect);
		
		lineSum = new JLabel("Receipts must be present to be a valid payment order");
		lineSum.setBounds(57, 249, 331, 50);
		layeredPane.add(lineSum);
		lineSum.setVisible(false);
		
		invalidLabel = new JLabel("Invalid Information: All fields must be completely filled out");
		invalidLabel.setBounds(57, 249, 289, 14);
		layeredPane.add(invalidLabel);
		invalidLabel.setVisible(false);
		
//		JComboBox comboBox = new JComboBox();
//		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Project", "Event", "Meal", "Inventory"}));
//		comboBox.setBounds(47, 34, 73, 20);
//		layeredPane.add(comboBox);
		
		DateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
	    dateField = new JFormattedTextField(dt);
		dateField.setBounds(235, 218, 86, 20);
		layeredPane.add(dateField);
		
		
		JLabel lblReimbursmentMethod = new JLabel("Reimbursment Method:");
		lblReimbursmentMethod.setBounds(47, 196, 171, 14);
		layeredPane.add(lblReimbursmentMethod);
		
		methodOfRepayment = new JComboBox();
		methodOfRepayment.setModel(new DefaultComboBoxModel(new String[] {"Credit", "Check"}));
		methodOfRepayment.setBounds(192, 190, 129, 20);
		layeredPane.add(methodOfRepayment);
		
		JLabel lblDateOfSubmission = new JLabel("Date of Submission (MM/DD/YYYY): ");
		lblDateOfSubmission.setBounds(47, 221, 171, 14);
		layeredPane.add(lblDateOfSubmission);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 83, 324, 93);
		layeredPane.add(scrollPane);
		
		descriptionField = new JTextArea();
		descriptionField.setLineWrap(true);
		scrollPane.setViewportView(descriptionField);
		
		JButton btnAddItem = new JButton("Add Receipt");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				CreateReceiptWindow.setVisible(receipts);
			}
		});
		btnAddItem.setBounds(57, 310, 100, 23);
		layeredPane.add(btnAddItem);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!receipts.isEmpty()){
				String descript = descriptionField.getText();
				System.out.println(lineItems);
				
				String department = (String) departmentSelect.getSelectedItem();
				String date = dateField.getText();
				
				String method = (String)methodOfRepayment.getSelectedItem();
				if(!method.isEmpty()&&!descript.isEmpty()&& !department.isEmpty() && !date.isEmpty()){
					int id = ExecuteSqlQuery.addPaymentOrder(SQLConnect, username, department, method, descript,date);
					try{
						ExecuteSqlQuery.addReceipts(SQLConnect, id, receipts);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					if(admin){
					AdminMainWindow.refreshPaymentOrders();
					AdminMainWindow.refreshPending();
					}
					else{
						NormalUserWindow.refreshPaymentOrders();
					}
					
					window.dispose();
				}
				else{
					invalidLabel.setVisible(true);
				}
				}
				else{
					lineSum.setVisible(true);
				}
				
			}
		});
		btnSubmit.setBounds(245, 310, 89, 23);
		layeredPane.add(btnSubmit);
		
//		JLabel lblCategory = new JLabel("Category:");
//		lblCategory.setBounds(47, 11, 60, 14);
//		layeredPane.add(lblCategory);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(47, 65, 60, 14);
		layeredPane.add(lblDescription);
		
		JLabel lblAffiliatedDepartment = new JLabel("Affiliated Department:");
		lblAffiliatedDepartment.setBounds(235, 11, 136, 14);
		layeredPane.add(lblAffiliatedDepartment);
	}
}

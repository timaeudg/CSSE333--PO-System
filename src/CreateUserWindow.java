import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import org.omg.CORBA.INITIALIZE;

import connection.ExecuteSqlQuery;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class CreateUserWindow extends JFrame {

	private JPanel contentPane;
	private JTextField LastNameField;
	private JTextField emailField;
	private JTextField firstNameField;
	private JTextField passwordField;
	private static Connection SQLConnect;
	private JTextField usernameField;
	private static JFrame createWindow;
	
	private static ArrayList<String> userChairs = new ArrayList<String>();
	private static ArrayList<String> userDepartments = new ArrayList<String>();
	
	private static ArrayList<Object> availableDepartments;
	
	private JLabel userInfoLabel;
	private static JLabel departmentLabel;	
	
	public static void newCreateUserWindow(Connection connect, ArrayList<Object> departments){
		createWindow = new CreateUserWindow(connect, departments);
		createWindow.setVisible(true);
	}
	
	public CreateUserWindow(Connection sql, ArrayList<Object> depts) {
		SQLConnect = sql;
		availableDepartments = depts;
		initialize();
	}

	private void initialize() {
		setTitle("Create New User");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 271, 390);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		LastNameField = new JTextField();
		LastNameField.setBounds(88, 61, 134, 20);
		layeredPane.add(LastNameField);
		LastNameField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setBounds(88, 92, 134, 20);
		layeredPane.add(emailField);
		emailField.setColumns(10);
		
		firstNameField = new JTextField();
		firstNameField.setBounds(88, 30, 134, 20);
		layeredPane.add(firstNameField);
		firstNameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setBounds(88, 123, 134, 20);
		layeredPane.add(passwordField);
		passwordField.setColumns(10);
		
		usernameField = new JTextField();
		usernameField.setBounds(88, 154, 134, 20);
		layeredPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name: ");
		lblFirstName.setBounds(10, 30, 68, 14);
		layeredPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(10, 61, 68, 14);
		layeredPane.add(lblLastName);
		
		JLabel lblEmail = new JLabel("E-mail: ");
		lblEmail.setBounds(10, 95, 46, 14);
		layeredPane.add(lblEmail);
		
		departmentLabel = new JLabel("User must belong to at least one department");
		departmentLabel.setBounds(10, 261, 225, 14);
		layeredPane.add(departmentLabel);
		departmentLabel.setVisible(false);
		
		JLabel lblNewLabel = new JLabel("Password: ");
		lblNewLabel.setBounds(10, 126, 68, 14);
		layeredPane.add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 157, 57, 14);
		layeredPane.add(lblUsername);
		
		userInfoLabel = new JLabel("Invalid User Information");
		userInfoLabel.setBounds(64, 317, 158, 14);
		layeredPane.add(userInfoLabel);
		userInfoLabel.setVisible(false);
		
		JButton chckbxChairperson = new JButton("Add Chair Position");
		chckbxChairperson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SelectChair.setVisible(SQLConnect, availableDepartments,userChairs);
				
				
			}
		});
		chckbxChairperson.setBounds(59, 197, 126, 23);
		layeredPane.add(chckbxChairperson);
		
		JButton addDepartmentsButton = new JButton();
		addDepartmentsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SelectDepartments.setVisible(SQLConnect, availableDepartments, userDepartments);
				
				
			}
		});
		addDepartmentsButton.setText("Add Departments");
		addDepartmentsButton.setBounds(59, 230, 126, 20);
		layeredPane.add(addDepartmentsButton);
		
		JButton btnAddAsUser = new JButton("Add as User");
		btnAddAsUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstName = firstNameField.getText();
				String lastName = LastNameField.getText();
				String email = emailField.getText();
				String password = passwordField.getText();
				String username = usernameField.getText();
				
				if(userDepartments.isEmpty()){
					departmentLabel.setVisible(true);
				}
				else{
				if(!ExecuteSqlQuery.addUser(firstName, lastName, email, password, username, SQLConnect)){
					userInfoLabel.setVisible(true);
				}
				else{
					if(!userChairs.isEmpty()){
					ExecuteSqlQuery.addChairToDepartment(SQLConnect, username, userChairs);
					}
					ExecuteSqlQuery.addUserToDepartment(SQLConnect, username, userDepartments);
					createWindow.dispose();
				}
			}
			}
		});
		btnAddAsUser.setBounds(59, 283, 126, 23);
		layeredPane.add(btnAddAsUser);
	}
}

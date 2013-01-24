import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;

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
	
	private JLabel userInfoLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateUserWindow frame = new CreateUserWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public static void newCreateUserWindow(Connection connect){
		SQLConnect = connect;
		JFrame window = new CreateUserWindow();
		window.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public CreateUserWindow() {
		setTitle("Create New User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 271, 341);
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
		
		JLabel lblNewLabel = new JLabel("Password: ");
		lblNewLabel.setBounds(10, 126, 68, 14);
		layeredPane.add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 157, 57, 14);
		layeredPane.add(lblUsername);
		
		userInfoLabel = new JLabel("Invalid User Information");
		userInfoLabel.setBounds(58, 268, 158, 14);
		layeredPane.add(userInfoLabel);
		userInfoLabel.setVisible(false);
		
		JCheckBox chckbxChairperson = new JCheckBox("Chairperson");
		chckbxChairperson.setBounds(10, 215, 83, 23);
		layeredPane.add(chckbxChairperson);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Accounting", "Public Relations", "Human Resources", "Administration"}));
		comboBox.setBounds(111, 216, 115, 20);
		layeredPane.add(comboBox);
		
		JButton btnAddAsUser = new JButton("Add as User");
		btnAddAsUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstName = firstNameField.getText();
				String lastName = LastNameField.getText();
				String email = emailField.getText();
				String password = passwordField.getText();
				String username = usernameField.getText();
				
				if(!ExecuteSqlQuery.addUser(firstName, lastName, email, password, username, SQLConnect)){
					userInfoLabel.setVisible(true);
				}
			}
		});
		btnAddAsUser.setBounds(58, 245, 110, 23);
		layeredPane.add(btnAddAsUser);
	}
}

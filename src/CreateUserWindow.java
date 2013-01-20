import java.awt.BorderLayout;
import java.awt.EventQueue;

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


public class CreateUserWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

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

	/**
	 * Create the frame.
	 */
	public CreateUserWindow() {
		setTitle("Create New User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 271, 280);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		textField = new JTextField();
		textField.setBounds(88, 61, 134, 20);
		layeredPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(88, 92, 134, 20);
		layeredPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(88, 30, 134, 20);
		layeredPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(88, 123, 134, 20);
		layeredPane.add(textField_3);
		textField_3.setColumns(10);
		
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
		
		JButton btnAddAsUser = new JButton("Add as User");
		btnAddAsUser.setBounds(54, 198, 110, 23);
		layeredPane.add(btnAddAsUser);
		
		JCheckBox chckbxChairperson = new JCheckBox("Chairperson");
		chckbxChairperson.setBounds(6, 168, 83, 23);
		layeredPane.add(chckbxChairperson);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Accounting", "Public Relations", "Human Resources", "Administration"}));
		comboBox.setBounds(107, 169, 115, 20);
		layeredPane.add(comboBox);
	}
}

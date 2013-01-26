import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;


public class CreateDepartment extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateDepartment frame = new CreateDepartment();
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
	public CreateDepartment() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 443, 291);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		textField = new JTextField();
		textField.setBounds(227, 33, 86, 20);
		layeredPane.add(textField);
		textField.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(227, 95, 86, 20);
		layeredPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblDepartmentName = new JLabel("Department Name: ");
		lblDepartmentName.setBounds(97, 36, 120, 14);
		layeredPane.add(lblDepartmentName);
		
		JLabel lblNewLabel = new JLabel("Parent Department: ");
		lblNewLabel.setBounds(97, 67, 104, 14);
		layeredPane.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(227, 64, 86, 20);
		layeredPane.add(comboBox);
		
		JLabel lblBudget = new JLabel("Budget: ");
		lblBudget.setBounds(97, 98, 46, 14);
		layeredPane.add(lblBudget);
		
		JButton btnCreateDepartment = new JButton("Create Department");
		btnCreateDepartment.setBounds(129, 179, 125, 23);
		layeredPane.add(btnCreateDepartment);
	}
}

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EditDepartmentsWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JComboBox textField_1;
	private JTextField textField_2;
	private static Connection SQLConnect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditDepartmentsWindow frame = new EditDepartmentsWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public static void setVisible(Connection connect) {
		EditDepartmentsWindow window = new EditDepartmentsWindow();
		window.setVisible(true);
		SQLConnect = connect;
	}
	
	
	/**
	 * Create the frame.
	 */
	public EditDepartmentsWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 403, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(205, 35, 122, 20);
		layeredPane.add(comboBox);
		
		JLabel lblSelectDepartmentTo = new JLabel("Select Department to Edit: ");
		lblSelectDepartmentTo.setBounds(40, 38, 130, 14);
		layeredPane.add(lblSelectDepartmentTo);
		
		textField = new JTextField();
		textField.setBounds(205, 88, 122, 20);
		layeredPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JComboBox();
		textField_1.setBounds(205, 132, 122, 20);
		layeredPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(205, 179, 122, 20);
		layeredPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewDepartmentName = new JLabel("New Department Name: ");
		lblNewDepartmentName.setBounds(40, 91, 130, 14);
		layeredPane.add(lblNewDepartmentName);
		
		JLabel lblNewParentDepartment = new JLabel("New Parent Department: ");
		lblNewParentDepartment.setBounds(40, 135, 130, 14);
		layeredPane.add(lblNewParentDepartment);
		
		JLabel lblNewBudget = new JLabel("New Budget: ");
		lblNewBudget.setBounds(40, 182, 65, 14);
		layeredPane.add(lblNewBudget);
		
		JButton btnMakeEdits = new JButton("Make Edits");
		btnMakeEdits.setBounds(205, 235, 89, 23);
		layeredPane.add(btnMakeEdits);
		
		JButton btnAddDepartment = new JButton("Add Department");
		btnAddDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateDepartment.setVisible(SQLConnect);
			}
		});
		btnAddDepartment.setBounds(40, 233, 111, 23);
		layeredPane.add(btnAddDepartment);
	}
}

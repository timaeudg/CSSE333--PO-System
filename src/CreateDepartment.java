import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.UIManager;

import connection.ExecuteSqlQuery;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class CreateDepartment extends JFrame {

	private JPanel contentPane;
	private JTextField departmentTextField;
	private JTextField budgetTextField;
	private static Connection SQLConnect;
	private static ArrayList<String> availableDepartments = new ArrayList<String>();
	private static JComboBox comboBox;
	private static JLabel invalidLabel;
	private static CreateDepartment window;
//	private static JFrame createWindow;


	
	public static void setVisible(Connection connect, ArrayList<String> depart) {
		availableDepartments= depart;
		window = new CreateDepartment();
		window.setVisible(true);
		SQLConnect = connect;
		System.out.println(availableDepartments.toString());
	}

	/**
	 * Create the frame.
	 */
	public CreateDepartment() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		setBounds(100, 100, 443, 291);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		departmentTextField = new JTextField();
		departmentTextField.setBounds(227, 33, 129, 20);
		layeredPane.add(departmentTextField);
		departmentTextField.setColumns(10);
		
		budgetTextField = new JTextField();
		budgetTextField.setBounds(227, 95, 129, 20);
		layeredPane.add(budgetTextField);
		budgetTextField.setColumns(10);
		
		invalidLabel = new JLabel("Invalid Department Information");
		invalidLabel.setBounds(118, 154, 160, 14);
		invalidLabel.setVisible(false);
		layeredPane.add(invalidLabel);
		
		JLabel lblDepartmentName = new JLabel("Department Name: ");
		lblDepartmentName.setBounds(97, 36, 120, 14);
		layeredPane.add(lblDepartmentName);
		
		JLabel lblNewLabel = new JLabel("Parent Department: ");
		lblNewLabel.setBounds(97, 67, 104, 14);
		layeredPane.add(lblNewLabel);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(availableDepartments.toArray()));
		comboBox.setBounds(227, 64, 129, 20);
		layeredPane.add(comboBox);
		
		JLabel lblBudget = new JLabel("Budget: ");
		lblBudget.setBounds(97, 98, 46, 14);
		layeredPane.add(lblBudget);
		
		JButton btnCreateDepartment = new JButton("Create Department");
		btnCreateDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String parent = (String) comboBox.getSelectedItem();
				String name = departmentTextField.getText();
				String budget = budgetTextField.getText();
				
				if(parent.isEmpty()|| name.isEmpty() || budget.isEmpty()||parent==null){
					invalidLabel.setVisible(true);
				}
				else{
					ExecuteSqlQuery.addDepartment(SQLConnect, name, parent, budget);
					AdminMainWindow.refreshDepartments();
					window.dispose();
				}
				
			}
		});
		btnCreateDepartment.setBounds(129, 179, 125, 23);
		layeredPane.add(btnCreateDepartment);
	}
}

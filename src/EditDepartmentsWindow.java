import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import connection.ExecuteSqlQuery;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EditDepartmentsWindow extends JFrame {

	private JPanel contentPane;
	private JTextField newNameField;
	private JComboBox parentComboBox;
	private JComboBox editComboBox;
	private JTextField newBudgetField;
	private static Connection SQLConnect;
	private static ArrayList<String> departmentsAvailable;
	private static JLabel parentInvalidLabel;
	
	public static void setVisible(Connection SQLConnection, ArrayList<String> departments){
		
		SQLConnect = SQLConnection;
		departmentsAvailable=departments;
		EditDepartmentsWindow window = new EditDepartmentsWindow();
		window.setVisible(true);
		
	}
	
	/**
	 * Create the frame.
	 */
	public EditDepartmentsWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		setBounds(100, 100, 403, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		editComboBox = new JComboBox();
		editComboBox.setModel(new DefaultComboBoxModel(departmentsAvailable.toArray()));
		editComboBox.setBounds(205, 35, 122, 20);
		layeredPane.add(editComboBox);
		
		JLabel lblSelectDepartmentTo = new JLabel("Select Department to Edit: ");
		lblSelectDepartmentTo.setBounds(40, 38, 130, 14);
		layeredPane.add(lblSelectDepartmentTo);
		
		parentInvalidLabel = new JLabel("Department cannot be made a parent of itself");
		parentInvalidLabel.setBounds(62, 214, 245, 14);
		layeredPane.add(parentInvalidLabel);
		parentInvalidLabel.setVisible(false);
		
		newNameField = new JTextField();
		newNameField.setBounds(205, 88, 122, 20);
		layeredPane.add(newNameField);
		newNameField.setColumns(10);
		
		parentComboBox = new JComboBox();
		parentComboBox.setBounds(205, 132, 122, 20);
		parentComboBox.setModel(new DefaultComboBoxModel(departmentsAvailable.toArray()));
		layeredPane.add(parentComboBox);
		
		newBudgetField = new JTextField();
		newBudgetField.setBounds(205, 179, 122, 20);
		layeredPane.add(newBudgetField);
		newBudgetField.setColumns(10);
		
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
		btnMakeEdits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String departToEdit = (String) editComboBox.getSelectedItem();
				String parentDepart = (String) parentComboBox.getSelectedItem();
				String budget = newBudgetField.getText();
				String newName = newNameField.getText();
				if(parentDepart.equals(departToEdit)){
					parentInvalidLabel.setVisible(true);
				}
				else{
					ExecuteSqlQuery.editDepartment(SQLConnect, departToEdit, parentDepart, budget, newName);
				}
			}
		});
		btnMakeEdits.setBounds(127, 239, 89, 23);
		layeredPane.add(btnMakeEdits);
	}
}

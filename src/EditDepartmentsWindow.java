import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.text.NumberFormat;
import java.text.ParseException;
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

import com.sun.java.swing.plaf.windows.resources.windows;

import connection.ExecuteSqlQuery;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;


public class EditDepartmentsWindow extends JFrame {

	private JPanel contentPane;
	private JTextField newNameField;
	private JComboBox parentComboBox;
	private JComboBox editComboBox;
	private JFormattedTextField newBudgetField;
	private static Connection SQLConnect;
	private static ArrayList<Object> departmentsAvailable;
	private static JLabel parentInvalidLabel;
	private static EditDepartmentsWindow window;
	
	public static void setVisible(Connection SQLConnection, ArrayList<Object> departments){
		
		SQLConnect = SQLConnection;
		departmentsAvailable=departments;
		window = new EditDepartmentsWindow();
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
		ArrayList<Object> departments = new ArrayList<Object>(departmentsAvailable);
		departments.add(0, "Don't Change");
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
		parentComboBox.setModel(new DefaultComboBoxModel(departments.toArray()));
		layeredPane.add(parentComboBox);
		
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		newBudgetField = new JFormattedTextField(currency);
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
				String budgetString = newBudgetField.getText();
				double budget;
				if(budgetString.isEmpty()){
					budget = -1;
				}
				else{
					 try {
						budget= NumberFormat.getCurrencyInstance().parse(budgetString).doubleValue();
					} catch (ParseException e){
						e.printStackTrace();
						budget=-1;
					}
				}
				String newName = newNameField.getText();
				if(parentDepart.equals(departToEdit)){
					parentInvalidLabel.setVisible(true);
				}
				else{
					ExecuteSqlQuery.editDepartment(SQLConnect, departToEdit, parentDepart,budget, newName);
					AdminMainWindow.refreshDepartments();
					window.dispose();
				}
			}
		});
		btnMakeEdits.setBounds(127, 239, 89, 23);
		layeredPane.add(btnMakeEdits);
	}
}

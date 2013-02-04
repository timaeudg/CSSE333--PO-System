import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLayeredPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.UIManager;

import connection.ExecuteSqlQuery;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class DeleteDepartmentWindow extends JFrame {

	private JPanel contentPane;
	private static Connection SQLConnect;
	private static ArrayList<String> departments;
	private static DeleteDepartmentWindow window;
	private static JComboBox<String> departmentCombo;

	
	public static void setVisible(Connection connect,ArrayList<String> availableDepartments){
		departments=availableDepartments;
		SQLConnect= connect;
		window = new DeleteDepartmentWindow();
		window.setVisible(true);
		
		
	}
	
	/**
	 * Create the frame.
	 */
	public DeleteDepartmentWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		setBounds(100, 100, 218, 268);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		departmentCombo = new JComboBox();
		departmentCombo.setBounds(20, 58, 137, 20);
		departmentCombo.setModel(new DefaultComboBoxModel(departments.toArray()));
		layeredPane.add(departmentCombo);
		
		JButton deleteButton = new JButton("Delete Department");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deleted = (String) departmentCombo.getSelectedItem();
				
				ExecuteSqlQuery.deleteDepartment(SQLConnect, deleted);
				AdminMainWindow.refreshDepartments();
				window.dispose();
			}
		});
		deleteButton.setBounds(20, 131, 137, 23);
		layeredPane.add(deleteButton);
	}
}

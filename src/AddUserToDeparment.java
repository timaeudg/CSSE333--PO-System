import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JComboBox;
import javax.swing.JButton;

import connection.ExecuteSqlQuery;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;


public class AddUserToDeparment extends JFrame {

	private JPanel contentPane;
	private static AddUserToDeparment window;
	private static Connection SQLConnect;
	private static ArrayList<Object> departments;
	private static JComboBox<String> dSelector;
	private static String username;
	private static JLabel lblCannotAddTo;

	public static void setVisible(Connection SQLConnection, ArrayList<Object> chairDeparts, String user){
		departments = chairDeparts;
		SQLConnect=SQLConnection;
		username = user;
		window = new AddUserToDeparment();
		window.setVisible(true);
	}
	/**
	 * Create the frame.
	 */
	public AddUserToDeparment() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		setBounds(100, 100, 172, 204);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		lblCannotAddTo = new JLabel("Cannot add to Root");
		lblCannotAddTo.setBounds(10, 67, 126, 14);
		layeredPane.add(lblCannotAddTo);
		lblCannotAddTo.setVisible(false);
		
		dSelector = new JComboBox();
		dSelector.setBounds(10, 36, 126, 20);
		dSelector.setModel(new DefaultComboBoxModel(departments.toArray()));
		layeredPane.add(dSelector);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String department = (String)dSelector.getSelectedItem(); 
				ArrayList<String> d = new ArrayList<String>();
				d.add(department);
				if(department.equals("root")){
					lblCannotAddTo.setVisible(true);
				}
				else{
				ExecuteSqlQuery.addUserToDepartment(SQLConnect, username, d);
				window.dispose();
				}
			}
		});
		submitButton.setBounds(10, 96, 126, 23);
		layeredPane.add(submitButton);
	}

}

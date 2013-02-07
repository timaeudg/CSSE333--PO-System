import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.UIManager;

import connection.LoggedInUserWrapper;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;


public class SelectChair extends JFrame {

	private JPanel contentPane;
	private static JComboBox<String> departments;
	private static SelectChair window;
	private static Connection SQLConnect;
	private static ArrayList<Object> departmentList;
	private static ArrayList<String> userChairsSelected;
	
	public static void setVisible(Connection SQLConnection, ArrayList<Object> depart, ArrayList<String> userChairs){
		SQLConnect = SQLConnection;
		departmentList = depart;
		userChairsSelected = userChairs;
		window = new SelectChair();
		window.setVisible(true);
		
		
	}

	/**
	 * Create the frame.
	 */
	public SelectChair() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		
		setBounds(100, 100, 215, 166);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		departments = new JComboBox<String>();
		departments.setModel(new DefaultComboBoxModel(departmentList.toArray()));
		departments.setBounds(39, 25, 101, 20);
		layeredPane.add(departments);
		
		JButton addChairButton = new JButton("Submit");
		addChairButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedDepartment = (String) departments.getSelectedItem();
				userChairsSelected.add(selectedDepartment);
				window.dispose();
			}
		});
		addChairButton.setBounds(39, 65, 101, 23);
		layeredPane.add(addChairButton);
	}
}

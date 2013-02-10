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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SelectDepartments extends JFrame {

	private JPanel contentPane;
	private static Connection SQLConnect;
	private static ArrayList<Object> availableDepart;
	private static ArrayList<String> userDepart;
	private static SelectDepartments window;
	private static JComboBox<String> dList;

	public static void setVisible(Connection connect, ArrayList<Object> available, ArrayList<String> departments){
		SQLConnect=connect;
		availableDepart=available;
		userDepart=departments;
		window = new SelectDepartments();
		window.setVisible(true);
	}
	/**
	 * Create the frame.
	 */
	public SelectDepartments() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 230, 218);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		dList = new JComboBox<String>();
		dList.setModel(new DefaultComboBoxModel(availableDepart.toArray()));
		dList.setBounds(40, 58, 113, 20);
		layeredPane.add(dList);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedDepartment = (String) dList.getSelectedItem();
				userDepart.add(selectedDepartment);
				window.dispose();
				
			}
		});
		btnNewButton.setBounds(40, 125, 113, 23);
		layeredPane.add(btnNewButton);
	}

}

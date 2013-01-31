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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class RemoveUserFromDeparment extends JFrame {

	private JPanel contentPane;
	private static AddUserAsChairperson window;
	private static Connection SQLConnect;
	private static ArrayList<String> departments;
	private static JComboBox<String> dSelector;
	

	public static void setVisible(Connection SQLConnection, ArrayList<String> chairDeparts){
		departments = chairDeparts;
		SQLConnect=SQLConnection;
		window = new AddUserAsChairperson();
		window.setVisible(true);
	}
	/**
	 * Create the frame.
	 */
	public RemoveUserFromDeparment() {
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
		
		dSelector = new JComboBox();
		dSelector.setBounds(10, 36, 126, 20);
		dSelector.setModel(new DefaultComboBoxModel(departments.toArray()));
		layeredPane.add(dSelector);
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String department = (String)dSelector.getSelectedItem();
			}
		});
		submitButton.setBounds(10, 96, 126, 23);
		layeredPane.add(submitButton);
	}

}

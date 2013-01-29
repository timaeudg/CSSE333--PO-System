

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;


public class LineItemWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private static Connection SQLConnect;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LineItemWindow frame = new LineItemWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void setVisible(Connection connect) {
		LineItemWindow window = new LineItemWindow();
		window.setVisible(true);
		SQLConnect = connect;
	}
	
	
	/**
	 * Create the frame.
	 */
	public LineItemWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 256);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		textField = new JTextField();
		textField.setBounds(34, 62, 86, 20);
		layeredPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(34, 118, 86, 20);
		layeredPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(55, 37, 46, 14);
		layeredPane.add(lblName);
		
		JLabel lblCost = new JLabel("Cost:");
		lblCost.setBounds(55, 93, 46, 14);
		layeredPane.add(lblCost);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(34, 177, 89, 23);
		layeredPane.add(btnSubmit);
		
		textField_2 = new JTextField();
		textField_2.setBounds(165, 178, 86, 20);
		layeredPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblLocationUrl = new JLabel("Location URL:");
		lblLocationUrl.setBounds(165, 153, 86, 14);
		layeredPane.add(lblLocationUrl);
		
		JLabel lblReceiptInformation = new JLabel("Receipt Information:");
		lblReceiptInformation.setBounds(161, 12, 103, 14);
		layeredPane.add(lblReceiptInformation);
		
		textField_3 = new JTextField();
		textField_3.setBounds(165, 62, 86, 20);
		layeredPane.add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(165, 118, 86, 20);
		layeredPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblStoreName = new JLabel("Store Name: ");
		lblStoreName.setBounds(165, 37, 86, 14);
		layeredPane.add(lblStoreName);
		
		JLabel lblTimeStamp = new JLabel("Time Stamp: ");
		lblTimeStamp.setBounds(165, 93, 86, 14);
		layeredPane.add(lblTimeStamp);
	}

}


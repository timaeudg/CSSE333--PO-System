

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class LineItemWindow extends JFrame {

	private JPanel contentPane;
	private static JTextField nameField;
	private static JTextField costField;
	private static JTextField urlField;
	private static JTextField storeNameField;
	private static JTextField timeStampField;
	private static Connection SQLConnect;
	private static ArrayList<LineItemWrapper> paymentLines;
	private static LineItemWindow window;
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

	public static void setVisible(Connection connect,ArrayList<LineItemWrapper> payments) {
		window = new LineItemWindow();
		window.setVisible(true);
		SQLConnect = connect;
		paymentLines=payments;
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
		
		nameField = new JTextField();
		nameField.setBounds(34, 62, 86, 20);
		layeredPane.add(nameField);
		nameField.setColumns(10);
		
		costField = new JTextField();
		costField.setBounds(34, 118, 86, 20);
		layeredPane.add(costField);
		costField.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(55, 37, 46, 14);
		layeredPane.add(lblName);
		
		JLabel lblCost = new JLabel("Cost:");
		lblCost.setBounds(55, 93, 46, 14);
		layeredPane.add(lblCost);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paymentLines.add(new LineItemWrapper(nameField.getText(), Double.parseDouble(costField.getText())));
				window.dispose();
			}
		});
		btnSubmit.setBounds(34, 177, 89, 23);
		layeredPane.add(btnSubmit);
		
		urlField = new JTextField();
		urlField.setBounds(165, 178, 86, 20);
		layeredPane.add(urlField);
		urlField.setColumns(10);
		
		JLabel lblLocationUrl = new JLabel("Location URL:");
		lblLocationUrl.setBounds(165, 153, 86, 14);
		layeredPane.add(lblLocationUrl);
		
		JLabel lblReceiptInformation = new JLabel("Receipt Information:");
		lblReceiptInformation.setBounds(161, 12, 103, 14);
		layeredPane.add(lblReceiptInformation);
		
		storeNameField = new JTextField();
		storeNameField.setBounds(165, 62, 86, 20);
		layeredPane.add(storeNameField);
		storeNameField.setColumns(10);
		
		timeStampField = new JTextField();
		timeStampField.setBounds(165, 118, 86, 20);
		layeredPane.add(timeStampField);
		timeStampField.setColumns(10);
		
		JLabel lblStoreName = new JLabel("Store Name: ");
		lblStoreName.setBounds(165, 37, 86, 14);
		layeredPane.add(lblStoreName);
		
		JLabel lblTimeStamp = new JLabel("Time Stamp: ");
		lblTimeStamp.setBounds(165, 93, 86, 14);
		layeredPane.add(lblTimeStamp);
	}

}


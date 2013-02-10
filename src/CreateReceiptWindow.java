import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

import connection.LineItemWrapper;
import connection.ReceiptBundles;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;


public class CreateReceiptWindow extends JFrame {

	private JPanel contentPane;
	private static JTextField nameField;
	private static JFormattedTextField timeField;
	private static JTextField locationField;
	private static CreateReceiptWindow window;
	private static ArrayList<ReceiptBundles> receipts;
	private static ArrayList<LineItemWrapper> lineItems;
	private static JLabel needLines;
	private static JLabel fieldsInvalid;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateReceiptWindow frame = new CreateReceiptWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public static void setVisible(ArrayList<ReceiptBundles> bundles){
		receipts = bundles;
		lineItems = new ArrayList<LineItemWrapper>();
		window = new CreateReceiptWindow();
		window.setVisible(true);
		
	}
	

	/**
	 * Create the frame.
	 */
	public CreateReceiptWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 197, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		JLabel lblReceiptInformation = new JLabel("Receipt Information:");
		lblReceiptInformation.setBounds(29, 24, 99, 14);
		layeredPane.add(lblReceiptInformation);
		
		JLabel label = new JLabel("Store Name: ");
		label.setBounds(39, 45, 86, 14);
		layeredPane.add(label);
		
		needLines = new JLabel("Line Items are Required");
		needLines.setBounds(10, 218, 151, 14);
		layeredPane.add(needLines);
		needLines.setVisible(false);
		
		fieldsInvalid = new JLabel("Fields cannot be empty");
		fieldsInvalid.setBounds(9, 235, 119, 14);
		layeredPane.add(fieldsInvalid);
		fieldsInvalid.setVisible(false);
		
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(39, 70, 86, 20);
		layeredPane.add(nameField);
		
		JLabel label_1 = new JLabel("Time Stamp: ");
		label_1.setBounds(39, 101, 86, 14);
		layeredPane.add(label_1);
		
		
		DateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
		timeField = new JFormattedTextField(dt);
		timeField.setColumns(10);
		timeField.setBounds(39, 126, 86, 20);
		layeredPane.add(timeField);
		
		JLabel label_2 = new JLabel("Location URL:");
		label_2.setBounds(39, 161, 86, 14);
		layeredPane.add(label_2);
		
		locationField = new JTextField();
		locationField.setColumns(10);
		locationField.setBounds(39, 186, 86, 20);
		layeredPane.add(locationField);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lineItems.isEmpty()){
					needLines.setVisible(true);
				}
				else{
				String storeName = nameField.getText();
				String time = timeField.getText();
				String place = locationField.getText();
				if(time.isEmpty()||place.isEmpty()||storeName.isEmpty()){
					fieldsInvalid.setVisible(false);
				}
				else 
				{
					ReceiptBundles current = new ReceiptBundles(storeName, place, time);
					current.setLineItems(lineItems);
					receipts.add(current);
					System.out.println(lineItems);
					window.dispose();
				}
			}
			}
		});
		btnNewButton.setBounds(34, 293, 89, 23);
		layeredPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add Line Item");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LineItemWindow.setVisible(lineItems);
			}
		});
		btnNewButton_1.setBounds(29, 259, 99, 23);
		layeredPane.add(btnNewButton_1);
	}
}

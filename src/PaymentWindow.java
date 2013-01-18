import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.JScrollPane;


public class PaymentWindow {

	private JFrame frmWhyIsThis;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaymentWindow window = new PaymentWindow();
					window.frmWhyIsThis.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PaymentWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e){
			
		}
		frmWhyIsThis = new JFrame();
		frmWhyIsThis.getContentPane().setBackground(Color.WHITE);
		frmWhyIsThis.setTitle("P-O-System");
		frmWhyIsThis.setForeground(Color.WHITE);
		frmWhyIsThis.setBounds(100, 100, 700, 460);
		frmWhyIsThis.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.WHITE);
		frmWhyIsThis.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.WHITE);
		layeredPane.setForeground(Color.BLACK);
		tabbedPane.addTab("Create New Objects", null, layeredPane, null);
		
		JButton btnNewButton_2 = new JButton("Create!");
		btnNewButton_2.setBounds(263, 334, 141, 49);
		layeredPane.add(btnNewButton_2);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("User");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(35, 50, 109, 23);
		layeredPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Payment Order");
		buttonGroup.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(35, 88, 109, 23);
		layeredPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("New radio button");
		buttonGroup.add(rdbtnNewRadioButton_2);
		rdbtnNewRadioButton_2.setBounds(35, 130, 109, 23);
		layeredPane.add(rdbtnNewRadioButton_2);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Pending Reimbursements", null, layeredPane_1, null);
		
		textField = new JTextField();
		textField.setBounds(10, 347, 168, 20);
		layeredPane_1.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Accept Reimbursement");
		btnNewButton.setBounds(219, 346, 159, 23);
		layeredPane_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Deny Reimbursement");
		btnNewButton_1.setBounds(388, 346, 135, 23);
		layeredPane_1.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 659, 304);
		layeredPane_1.add(scrollPane);
		
		JTextArea txtrThisIsStarting = new JTextArea();
		scrollPane.setViewportView(txtrThisIsStarting);
		txtrThisIsStarting.setText("This is starting text, I'm going to try and fill it in such a way to make things clear that I am testing them, so now it is time to do silly things\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n65r1b9685e1rb981er9b81\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
		txtrThisIsStarting.setEditable(false);
		txtrThisIsStarting.setFont(new Font("Courier New", Font.PLAIN, 12));
		txtrThisIsStarting.setLineWrap(true);
		txtrThisIsStarting.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblPaymentOrderNumbers = new JLabel("Payment Order Numbers:");
		lblPaymentOrderNumbers.setBounds(10, 322, 122, 14);
		layeredPane_1.add(lblPaymentOrderNumbers);
	}
}

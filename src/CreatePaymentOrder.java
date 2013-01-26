import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;


public class CreatePaymentOrder extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreatePaymentOrder frame = new CreatePaymentOrder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CreatePaymentOrder() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Project", "Event", "Meal", "Inventory"}));
		comboBox.setBounds(47, 34, 73, 20);
		layeredPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(47, 83, 324, 93);
		layeredPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setBounds(235, 187, 86, 20);
		layeredPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblReimbursmentAmountdont = new JLabel("Reimbursment Amount (don't put $): ");
		lblReimbursmentAmountdont.setBounds(47, 190, 178, 14);
		layeredPane.add(lblReimbursmentAmountdont);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.setBounds(47, 218, 89, 23);
		layeredPane.add(btnAddItem);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(235, 218, 89, 23);
		layeredPane.add(btnSubmit);
		
		JLabel lblCategory = new JLabel("Category:");
		lblCategory.setBounds(47, 11, 60, 14);
		layeredPane.add(lblCategory);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(47, 65, 60, 14);
		layeredPane.add(lblDescription);
	}
}

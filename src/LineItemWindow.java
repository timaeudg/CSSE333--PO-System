import java.awt.BorderLayout;
import java.awt.EventQueue;

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

	/**
	 * Create the frame.
	 */
	public LineItemWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 185, 305);
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
		textField_1.setBounds(34, 130, 86, 20);
		layeredPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(55, 37, 46, 14);
		layeredPane.add(lblName);
		
		JLabel lblCost = new JLabel("Cost:");
		lblCost.setBounds(55, 105, 46, 14);
		layeredPane.add(lblCost);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(31, 185, 89, 23);
		layeredPane.add(btnSubmit);
	}

}

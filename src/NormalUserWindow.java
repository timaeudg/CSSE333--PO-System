import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;


public class NormalUserWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NormalUserWindow frame = new NormalUserWindow();
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
	public NormalUserWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("User Lookup", null, layeredPane, null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 468, 178);
		layeredPane.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JLabel label = new JLabel("First Name:");
		label.setBounds(10, 210, 61, 14);
		layeredPane.add(label);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(85, 207, 86, 20);
		layeredPane.add(textField);
		
		JLabel label_1 = new JLabel("Last Name:");
		label_1.setBounds(181, 210, 61, 14);
		layeredPane.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(252, 207, 86, 20);
		layeredPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(252, 245, 86, 20);
		layeredPane.add(textField_2);
		
		JLabel label_2 = new JLabel("E-mail:");
		label_2.setBounds(181, 248, 43, 14);
		layeredPane.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(85, 245, 86, 20);
		layeredPane.add(textField_3);
		
		JLabel label_3 = new JLabel("Username:");
		label_3.setBounds(10, 248, 61, 14);
		layeredPane.add(label_3);
		
		JButton button = new JButton("Search");
		button.setBounds(389, 226, 89, 23);
		layeredPane.add(button);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		tabbedPane.addTab("Your Payment Orders", null, layeredPane_1, null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 468, 227);
		layeredPane_1.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setBackground(Color.LIGHT_GRAY);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		
		JButton btnCreateNewPayment = new JButton("Create New Payment Order");
		btnCreateNewPayment.setBounds(158, 249, 165, 23);
		layeredPane_1.add(btnCreateNewPayment);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Department Overview", null, layeredPane_2, null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 470, 228);
		layeredPane_2.add(scrollPane_2);
		
		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);
	}
}

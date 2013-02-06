

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

import connection.LineItemWrapper;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class LineItemWindow extends JFrame {

	private JPanel contentPane;
	private static JTextField nameField;
	private static JTextField costField;
	private static JLabel invalidLabel;
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

	public static void setVisible(ArrayList<LineItemWrapper> payments) {
		window = new LineItemWindow();
		window.setVisible(true);
		paymentLines=payments;
	}
	
	
	/**
	 * Create the frame.
	 */
	public LineItemWindow() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 178, 256);
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
		
		invalidLabel = new JLabel("Invalid inputs");
		invalidLabel.setBounds(43, 152, 81, 14);
		layeredPane.add(invalidLabel);
		invalidLabel.setVisible(false);
		
		JLabel lblCost = new JLabel("Cost:");
		lblCost.setBounds(55, 93, 46, 14);
		layeredPane.add(lblCost);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = nameField.getText();
				String cost = costField.getText();
				try{
					double parsed = Double.parseDouble(cost);
					if(cost.isEmpty()||name.isEmpty()){
						invalidLabel.setVisible(true);
					}
					else{
						paymentLines.add(new LineItemWrapper(name, parsed));
						window.dispose();
						
					}
				}
				catch(Exception e){
					invalidLabel.setVisible(true);
				}
			}
		});
		btnSubmit.setBounds(34, 177, 89, 23);
		layeredPane.add(btnSubmit);
		
		JLabel lblLineItemInfo = new JLabel("Line Item Information:");
		lblLineItemInfo.setBounds(10, 11, 132, 14);
		layeredPane.add(lblLineItemInfo);
	}

}


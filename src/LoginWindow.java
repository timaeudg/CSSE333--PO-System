import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;

import connection.*;

import java.awt.Font;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	public Connection whaleConnect =null;
	private static LoginWindow login;
	private JLabel lblUsernameOrPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		login = new LoginWindow();
		login.setVisible(true);
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public LoginWindow() {
		

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		whaleConnect = ExecuteSqlQuery.connectToWhale();
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		try {
			whaleConnect = DatabaseAdapter.getConnection(null, DatabaseAdapter.getConnectionInfo());
		} catch (SQLException e) {
			lblUsernameOrPassword = new JLabel("Unable to connect");
			lblUsernameOrPassword.setVisible(true);
			lblUsernameOrPassword.setBounds(15, 202, 211, 14);
			layeredPane.add(lblUsernameOrPassword);
			e.printStackTrace();
		}
		
		
		lblUsernameOrPassword = new JLabel("Username or Password incorrect");
		lblUsernameOrPassword.setVisible(false);
		lblUsernameOrPassword.setBounds(15, 202, 211, 14);
		layeredPane.add(lblUsernameOrPassword);
		
		textField = new JTextField();
		textField.setBounds(95, 116, 86, 20);
		layeredPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					String email = textField.getText();
					String password = new String(passwordField.getPassword());
					LoggedInUserWrapper user = ExecuteSqlQuery.login(email, password, whaleConnect);			
					if(user.getLogged()){
						if(user.getChairs().size()>0){
							AdminMainWindow.setVisible(whaleConnect, user);
						}
						else{
							NormalUserWindow.setVisible(whaleConnect, user);
						}
						login.dispose();
					}
					else{
						lblUsernameOrPassword.setVisible(true);
					}
					
					
				}
				
			}
		});
		passwordField.setBounds(95, 160, 86, 20);
		layeredPane.add(passwordField);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(15, 119, 70, 14);
		layeredPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(15, 166, 70, 14);
		layeredPane.add(lblPassword);
	
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String email = textField.getText();
				String password = new String(passwordField.getPassword());
				LoggedInUserWrapper user = ExecuteSqlQuery.login(email, password, whaleConnect);			
				if(user.getLogged()){
					if(user.getChairs().size()>0){
						AdminMainWindow.setVisible(whaleConnect, user);
					}
					else{
						NormalUserWindow.setVisible(whaleConnect, user);
					}
					login.dispose();
				}
				else{
					lblUsernameOrPassword.setVisible(true);
				}
				
				
			}
		});
		btnLogin.setBounds(92, 236, 89, 23);
		layeredPane.add(btnLogin);
		
		JLabel lblNewLabel = new JLabel("Payment-Order\r\n System!");
		lblNewLabel.setFont(new Font("Thames", Font.PLAIN, 23));
		lblNewLabel.setBounds(10, 22, 234, 83);
		layeredPane.add(lblNewLabel);
		
	}
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import connection.ExecuteSqlQuery;
import connection.LoggedInUserWrapper;
import javax.swing.JPasswordField;

import extras.Util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

/**
 * @author moorejm
 * 
 */
public class Tab_EditRemove extends JPanel {

	private static JTextField userNameEditField;
	private static JTextField newFirstNameField;
	private static JTextField newLastNameField;
	private static JTextField newEmailField;
	private static JTextField newPasswordField;
	private static JTextField newUsernameField;
	private static JTextField firstNameField;
	private static Connection SQLConnect;
	private static LoggedInUserWrapper user;
	private static ArrayList<Object> departmentNames;
	private static JFrame parentFrame;
	private JTextField textField;
	private JTextField LnametextField;
	private JTextField emailTextField;
	private JPasswordField passwordField;
	private JTextField verifyTextField;
	private String[] userInfo;

	/**
	 * Create the panel.
	 */

	public void initialize() {
		setLayout(null);

		JLabel lblUserName = new JLabel("Username:");

		lblUserName.setBounds(40, 82, 173, 14);
		add(lblUserName);

		userNameEditField = new JTextField();
		userNameEditField.setBounds(107, 79, 106, 20);
		add(userNameEditField);
		userNameEditField.setColumns(10);

		userNameEditField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						ResultSet rs = ExecuteSqlQuery.lookupUsers(null, null,
								null, userNameEditField.getText(), SQLConnect);

						if (rs != null) {
							userInfo = new String[4];

							if (!rs.isBeforeFirst()) {
								JOptionPane.showMessageDialog(
										parentFrame,
										"Username "
												+ userNameEditField.getText()
												+ " does not exist.");
							}

							else if (rs.next()) { // only one row of data
													// returned
								for (int j = 1; j < 5; j++) {
									userInfo[j - 1] = rs.getString(j);
								}

								// if (userInfo.length != 0) {
								// userInfo[0] = username
								firstNameField.setText(userInfo[1]);
								LnametextField.setText(userInfo[2]);
								emailTextField.setText(userInfo[3]);

								boolean enableNew = true;
								newUsernameField.setEnabled(enableNew);
								newFirstNameField.setEnabled(enableNew);
								newLastNameField.setEnabled(enableNew);
								newEmailField.setEnabled(enableNew);
								newPasswordField.setEnabled(enableNew);
								// }
							}

						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

			}
		});

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(40, 113, 173, 14);
		add(lblFirstName);

		// New

		firstNameField = new JTextField();
		firstNameField.setEditable(false);
		firstNameField.setBounds(107, 110, 106, 20);
		add(firstNameField);
		firstNameField.setColumns(10);

		JLabel lblNewUsername = new JLabel("New Username: ");
		lblNewUsername.setBounds(242, 82, 215, 14);
		add(lblNewUsername);

		newUsernameField = new JTextField();
		newUsernameField.setEnabled(false);
		newUsernameField.setBounds(331, 79, 126, 20);
		add(newUsernameField);
		newUsernameField.setColumns(10);

		JLabel lblNewFName = new JLabel("New First Name:");
		lblNewFName.setBounds(242, 113, 215, 14);
		add(lblNewFName);

		newFirstNameField = new JTextField();
		newFirstNameField.setEnabled(false);
		newFirstNameField.setBounds(331, 110, 126, 20);
		add(newFirstNameField);
		newFirstNameField.setColumns(10);

		JLabel lblNewLName = new JLabel("New Last Name:");
		lblNewLName.setBounds(242, 144, 215, 14);
		add(lblNewLName);

		newLastNameField = new JTextField();
		newLastNameField.setEnabled(false);
		newLastNameField.setBounds(331, 141, 126, 20);
		add(newLastNameField);
		newLastNameField.setColumns(10);

		JLabel lblNewEmail = new JLabel("New E-mail:");
		lblNewEmail.setBounds(242, 175, 215, 14);
		add(lblNewEmail);

		newEmailField = new JTextField();
		newEmailField.setEnabled(false);
		newEmailField.setBounds(331, 172, 126, 20);
		add(newEmailField);
		newEmailField.setColumns(10);

		JLabel lblNewPass = new JLabel("New Password: ");
		lblNewPass.setBounds(242, 206, 215, 14);
		add(lblNewPass);

		newPasswordField = new JTextField();
		newPasswordField.setEnabled(false);
		newPasswordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (newPasswordField.getText().length() <= 1
						&& e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					verifyTextField.setText("");
					verifyTextField.setEnabled(false);
				} else
					verifyTextField.setEnabled(true);
				// }
			}
		});
		newPasswordField.setBounds(331, 203, 126, 20);
		add(newPasswordField);
		newPasswordField.setColumns(10);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(40, 144, 173, 14);
		add(lblLastName);

		LnametextField = new JTextField();
		LnametextField.setEditable(false);
		LnametextField.setBounds(107, 141, 106, 20);
		add(LnametextField);
		LnametextField.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(40, 175, 173, 14);
		add(lblEmail);

		emailTextField = new JTextField();
		emailTextField.setEditable(false);
		emailTextField.setBounds(107, 172, 106, 20);
		add(emailTextField);
		emailTextField.setColumns(10);

		// passwordField = new JPasswordField();
		// passwordField.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseEntered(MouseEvent arg0) {
		// System.out.println(passwordField.getPassword());
		// passwordField.setText(new String(passwordField.getPassword()));
		// }
		//
		// @Override
		// public void mouseExited(MouseEvent e) {
		// if (user)
		// passwordField.setText(userInfo[0]);
		// }
		// });
		// passwordField.setEditable(false);
		// passwordField.setBounds(107, 203, 106, 20);
		// add(passwordField);

		JLabel verifylbl = new JLabel("Verify Password: ");
		verifylbl.setBounds(242, 234, 215, 14);
		add(verifylbl);

		JButton btnChange = new JButton("Make Changes");
		btnChange.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String oldUsername = userNameEditField.getText();
				String newUsername = newUsernameField.getText();
				String newFirstName = newFirstNameField.getText();
				String newLastName = newLastNameField.getText();
				String newEmail = newEmailField.getText();
				String newPassword = newPasswordField.getText();
				String verified = verifyTextField.getText();

				// Check for edits
				// If something is not empty, true
				boolean edits = (!newUsername.isEmpty() || !newEmail.isEmpty()
						|| !newFirstName.isEmpty() || !newLastName.isEmpty()||(!newPassword.isEmpty()&&!verified.isEmpty()));

				// if new email is given and valid, true
				boolean validEmail = !newEmail.isEmpty() ? Util
						.isValidEmailAddress(newEmail) : true;
				edits = edits && validEmail;

				// if new password is not verified, false
				if (!newPassword.isEmpty()
						&& !newPassword.equals(verifyTextField.getText())) {
					edits = false;
				}
				// edits = edits &&
				// (userNameEditField.getText().equals(verifyTextField.getText()));
				// noEdits = noEdits || newPassword.isEmpty();

				System.out.printf("%s, %s, %s, %s %s\n", newUsername.isEmpty(),
						newFirstName.isEmpty(), newLastName.isEmpty(),
						newEmail.isEmpty() || validEmail, newPassword.isEmpty());
				System.out.println("Make Changes: " + edits);

				if (oldUsername.equals(user.getUsername())) {

					if (JOptionPane
							.showConfirmDialog(
									parentFrame,
									"You are about to make edits to yourself, are you sure you would like to do this?") == 0) {
						if (edits
								&& ExecuteSqlQuery.editUser(oldUsername,
										newUsername, newFirstName, newLastName,
										newEmail, newPassword, SQLConnect)) {
							JOptionPane.showMessageDialog(parentFrame,
									"Changes to " + oldUsername
											+ " made successfully");
						}
					}
				} else {

					if (edits
							&& ExecuteSqlQuery.editUser(oldUsername,
									newUsername, newFirstName, newLastName,
									newEmail, newPassword, SQLConnect)) {
						JOptionPane.showMessageDialog(parentFrame,
								"Changes to " + oldUsername
										+ " made successfully");
					}

				}
			}
		});

		verifyTextField = new JTextField();
		verifyTextField.setEnabled(false);
		verifyTextField.setColumns(10);
		verifyTextField.setBounds(331, 231, 126, 20);
		add(verifyTextField);

		JButton addToDeparment = new JButton("Add To Department");
		addToDeparment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUserToDeparment.setVisible(SQLConnect, departmentNames,
						userNameEditField.getText());
			}
		});
		addToDeparment.setBounds(487, 78, 182, 23);
		add(addToDeparment);

		JButton removeFromDepartment = new JButton("Remove From Department");
		removeFromDepartment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String user = userNameEditField.getText();
				RemoveUserFromDepartment.setVisible(SQLConnect,
						departmentNames, user);
			}
		});
		removeFromDepartment.setBounds(487, 109, 182, 23);
		add(removeFromDepartment);

		JButton addAsChairperson = new JButton("Add as Chairperson");
		addAsChairperson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = userNameEditField.getText();
				AddUserAsChairperson.setVisible(SQLConnect, departmentNames,
						user);
			}
		});
		addAsChairperson.setBounds(487, 166, 182, 23);
		add(addAsChairperson);

		JButton removeFromChair = new JButton("Remove From Chair Position");
		removeFromChair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = userNameEditField.getText();
				RemoveUserFromChairperson.setVisible(SQLConnect,
						departmentNames, user);
			}
		});
		removeFromChair.setBounds(487, 200, 182, 23);
		add(removeFromChair);
		btnChange.setBounds(10, 334, 322, 50);
		add(btnChange);

		JButton btnRemove = new JButton("Remove User");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deletee = userNameEditField.getText();

				if (deletee.equals(user.getUsername())) {

					if (JOptionPane
							.showConfirmDialog(
									parentFrame,
									"You are about to delete yourself\nIf you continue, you will be logged out and deleted from the database\nare you sure you would like to do this?") == 0) {
						ExecuteSqlQuery.removeUser(user.getUsername(), deletee,
								SQLConnect);
						try {
							SQLConnect.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						parentFrame.dispose();
					}
				} else {

					ExecuteSqlQuery.removeUser(user.getUsername(), deletee,
							SQLConnect);

				}

			}
		});
		btnRemove.setBounds(347, 334, 322, 50);
		add(btnRemove);

		JLabel lblCurrent = new JLabel("Current");
		lblCurrent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCurrent.setBounds(114, 40, 59, 14);
		add(lblCurrent);

		JLabel lblNew = new JLabel("New");
		lblNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNew.setBounds(347, 40, 59, 14);
		add(lblNew);
	}

	public Tab_EditRemove(JFrame parent, Connection sql,
			LoggedInUserWrapper userWrapper, ArrayList<Object> deptNames) {
		parentFrame = parent;
		SQLConnect = sql;
		user = userWrapper;
		departmentNames = deptNames;

		initialize();
	}
}

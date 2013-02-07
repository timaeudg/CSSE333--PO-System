package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connection.ExecuteSqlQuery;
import extras.AlternatingColorTable;

public class userLookupAction implements ActionListener {

	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField emailField;
	private JTextField usernameField;
	private Connection SQLConnect;
	private JScrollPane scrollPane;
	
	public userLookupAction(JScrollPane scrollPane, JTextField fname, JTextField lname, JTextField email, JTextField username, Connection connection) {
		this.scrollPane = scrollPane;
		this.fNameField    = fname;
		this.lNameField    = lname;
		this.emailField    = email;
		this.usernameField = username;
		this.SQLConnect    = connection;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String firstName = fNameField.getText().replaceAll("\\*", "%");
		String lastName = lNameField.getText().replaceAll("\\*", "%");
		String email = emailField.getText().replaceAll("\\*", "%");
		String username = usernameField.getText().replaceAll("\\*", "%");
		ResultSet rs = null;

		try {
			rs = ExecuteSqlQuery.lookupUsers(firstName, lastName,
					email, username, SQLConnect);
			if (rs != null) {

				ArrayList<String[]> rows = new ArrayList<String[]>();
				String[] row = new String[4];
				while (rs.next()) {
					for (int j = 1; j < 5; j++) {
						row[j - 1] = rs.getString(j);
					}
					rows.add(row);
					row = new String[4];
				}

				int numberOfRows = rows.size();
				String[][] data = new String[numberOfRows][4];

				// System.out.println(rows.toString());

				for (int k = 0; k < numberOfRows; k++) {
					data[k] = rows.get(k);
				}
				rs.close();
				String[] columns = new String[] { "Username",
						"First Name", "Last Name", "E-mail" };

				JTable updateTable = new AlternatingColorTable(new DefaultTableModel(data, columns));
//				lookupTable.setModel(updateTable.getModel());
				scrollPane.setViewportView(updateTable);
//				lookupTable.repaint();

			} else {
				System.out.println("Herp Derp");
			}
		} catch (SQLException ex1) {
			ex1.printStackTrace();
		}
	}
}

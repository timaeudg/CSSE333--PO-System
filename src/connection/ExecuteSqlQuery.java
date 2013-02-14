package connection;

/**
 * Execute sql queries with java application.
 */
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.sun.xml.internal.ws.org.objectweb.asm.Type;

import extras.Util;

/**
 * Class for executing SQL Queries in our
 * 
 * @author moorejm, timaeudg
 * 
 */
public class ExecuteSqlQuery {

	/**
	 * Registers the SQL Server driver and returns the connection to the
	 * database
	 * 
	 * @return The SQL Server Database connection
	 */
	public static Connection connectToWhale() {
		Connection connection = null;

		try {
			DatabaseAdapter.registerSQLServerDriver();

			ConnectionInfo info = DatabaseAdapter.getConnectionInfo();
			connection = DatabaseAdapter.getConnection(null, info);

			// System.out.println("Driver Registered");

			connection = DatabaseAdapter.getConnection(null, info);

			// System.out.println("Connection Successful!");
		} catch (SQLException e) {
			System.out.println("Connection Error");
			e.printStackTrace();
		}

		return connection;
	}

	/**
	 * Constructs and returns a wrapper class for a logged-in user
	 * 
	 * @param email
	 * @param password
	 * @param connection
	 * @return a wrapper for a logged-in user
	 */
	public static LoggedInUserWrapper login(String email, String password,
			Connection connection) {

		String piecedString = "{ ? = call attemptlogin ( ? , ? ) }";
		String chairsString = "{ ? = call chairlist (?)} ";
		ResultSet rs = null;
		CallableStatement statement = null;
		CallableStatement secondStatment = null;
		boolean login = false;
		Integer[] departmentIDs = new Integer[10];
		LoggedInUserWrapper user;
		ArrayList<String> departments = new ArrayList<String>();
		try {
			statement = connection.prepareCall(piecedString);
			secondStatment = connection.prepareCall(chairsString);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, email);
			statement.setString(3, password);
			secondStatment.registerOutParameter(1, Types.INTEGER);
			secondStatment.setString(2, email);

			statement.execute();
			rs = secondStatment.executeQuery();

			login = true;
			// System.out.println("Login Successful!");
			while (rs.next()) {
				departments.add(rs.getString(1));
			}

			// departmentIDs=departments.toArray(departmentIDs);

			user = new LoggedInUserWrapper(email, departments, login);

			// for (Integer id : departments) {
			// System.out.print(id + " ");
			// }
			// System.out.println();

			statement.close();

		} catch (SQLException e) {
			login = false;
			System.err.println("Invalid username or password.");
			user = new LoggedInUserWrapper(email, departments, login);
		}

		return user;

	}

	public static ResultSet lookupUsers(String firstName, String lastName,
			String email, String username, Connection connection) {
		String query = " { ? = call lookupuser (? , ? , ? , ? ) }";
		ResultSet rs = null;
		try {
			CallableStatement statement = null;
			statement = connection.prepareCall(query);
			statement.setQueryTimeout(5);

			statement.registerOutParameter(1, Types.INTEGER);

			if (!(username == null || username.isEmpty())) {
				statement.setString(2, username);
			} else {
				statement.setString(2, null);
			}
			if (!(firstName == null || firstName.isEmpty())) {
				statement.setString(3, firstName);
			} else {
				statement.setString(3, null);
			}
			if (!(lastName == null || lastName.isEmpty())) {
				statement.setString(4, lastName);
			} else {
				statement.setString(4, null);
			}
			if (email != null && !email.isEmpty()) {
				statement.setString(5, email);
			} else {
				statement.setString(5, null);
			}

			rs = statement.executeQuery();

		} catch (Exception e) {
			System.err.println("User Lookup failed.");
			e.printStackTrace();
		}

		return rs;

	}

	public static boolean addUser(String firstName, String lastName,
			String email, String password, String username,
			Connection connection) {
		String query = "{ ? = call newuser ( ? , ? , ? , ? , ? ) }";
		CallableStatement statement = null;
		boolean added = true;

		try {
			statement = connection.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			if (firstName.isEmpty() || lastName.isEmpty()
					|| (email.isEmpty() || !Util.isValidEmailAddress(email))
					|| password.isEmpty() || username.isEmpty()) {
				throw new IllegalArgumentException();
			}
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setString(4, username);
			statement.setString(5, password);
			statement.setString(6, email);

			statement.execute();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
			added = false;
		}

		return added;
	}

	public static boolean addUserToDepartment(Connection connect,
			String username, ArrayList<String> departs) {
		boolean added = false;
		String query = "{ ? = call addusertodepartment (?, ?)}";
		CallableStatement statement = null;
		try {

			for (String department : departs) {
				statement = connect.prepareCall(query);
				statement.registerOutParameter(1, Types.INTEGER);
				statement.setString(2, username);
				statement.setString(3, department);
				statement.execute();
			}

			added = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return added;
	}

	public static boolean removeUser(String deleter, String deletee,
			Connection connection) {
		boolean removed = false;

		String query = "{ ? = call deleteuser (?, ?)}";
		CallableStatement statement = null;

		try {
			statement = connection.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, deleter);
			statement.setString(3, deletee);

			statement.execute();
			statement.close();
			removed = true;
		} catch (Exception e) {
			e.printStackTrace();
			removed = false;
		}

		return removed;
	}

	public static boolean removeUserFromDepartment(Connection connect,
			String username, String department) {
		String query = "{ ? = call removeuserfromdepartment (?,?) }";
		CallableStatement statement = null;
		boolean removed = false;
		try {
			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, username);
			statement.setString(3, department);

			statement.execute();
			removed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return removed;
	}

	public static boolean editUser(String oldUsername, String newUsername,
			String newFirstName, String newLastName, String newEmail,
			String newPassword, Connection connection) {
		boolean editted = false;

		String query = "{ ? = call modifyuser (?, ?, ?, ?, ?, ?)}";
		CallableStatement statement = null;

		try {
			statement = connection.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);

			if ((oldUsername == null || oldUsername.isEmpty())) {
				throw new IllegalArgumentException();
			} else {
				statement.setString(2, oldUsername);
			}
			if ((newUsername == null || newUsername.isEmpty())) {
				statement.setString(3, null);
			} else {
				statement.setString(3, newUsername);
			}

			if ((newFirstName == null || newFirstName.isEmpty())) {
				statement.setString(4, null);
			} else {
				statement.setString(4, newFirstName);
			}
			if ((newLastName == null || newLastName.isEmpty())) {
				statement.setString(5, null);
			} else {
				statement.setString(5, newLastName);
			}
			if ((newEmail == null || newEmail.isEmpty())) {
				statement.setString(6, null);
			} else {
				statement.setString(6, newEmail);
			}

			if ((newPassword == null || newPassword.isEmpty())) {
				statement.setString(7, null);
			} else {
				statement.setString(7, newPassword);
			}

			statement.execute();
			statement.close();
			editted = true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return editted;
	}

	public static boolean addChairToDepartment(Connection connect,
			String username, ArrayList<String> departs) {
		boolean added = false;
		String query = "{ ? = call addchairtodepartment (?, ?)}";
		CallableStatement statement = null;
		try {

			for (String department : departs) {
				statement = connect.prepareCall(query);
				statement.registerOutParameter(1, Types.INTEGER);
				statement.setString(2, username);
				statement.setString(3, department);
				added = statement.execute();
			}

//			added = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return added;
	}

	public static boolean addDepartment(Connection connect,
			String departmentName, String parent, double budget) {
		boolean added = false;

		String query = "{ ? = call newdepartment (?, ?, ?, ?)}";
		CallableStatement statement = null;
		try {
			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, departmentName);
			statement.setDouble(3, budget);
			statement.setDouble(4,budget);
			statement.setString(5, parent);

			statement.execute();
			added = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return added;
	}

	public static void editDepartment(Connection sQLConnect,
			String departToEdit, String parentDepart, double budget,
			String newName) {
		String query = "{ ? = call editdepartment (?,?,?,?) }";
		CallableStatement statement = null;
		try {
			statement = sQLConnect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, departToEdit);
			if (newName.isEmpty() || newName == null) {
				statement.setString(3, null);
			} else {
				statement.setString(3, newName);
			}
			if (parentDepart.equals("Don't Change")) {
				statement.setString(4, null);
			} else {
				statement.setString(4, parentDepart);
			}
			statement.setDouble(5, budget);
			statement.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void deleteDepartment(Connection sQLConnect, String deleted) {
		String query = "{ ? = call DeleteDepartment (?)}";
		CallableStatement statement = null;
		try {
			statement = sQLConnect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, deleted);
			System.out.println(deleted);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object[][] getDepartmentOverview(Connection connection) {

		String query = "{ ? = call alldepartmentinfo}";
		CallableStatement statement = null;
		ResultSet rs = null;
		Object[][] data = null;
		try {
			statement = connection.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			rs = statement.executeQuery();
			// DecimalFormat fmt = new DecimalFormat("$ ###,###.00");
			ArrayList<Object[]> rows = new ArrayList<Object[]>();
			Object[] row = new Object[5];
			while (rs.next()) {
				for (int j = 1; j < 6; j++) {
					if (j == 1 || j == 5) {
						int id = rs.getInt(j);
						row[j - 1] = id == 0 ? "" : id;
					}
						
					else if (j == 3 || j == 4)
						row[j - 1] = rs.getDouble(j);
					else
						row[j - 1] = rs.getString(j);
				}
				rows.add(row);
				row = new Object[5];
			}

			int numberOfRows = rows.size();
			data = new Object[numberOfRows][5];

			// System.out.println(rows.toString());

			for (int k = 0; k < numberOfRows; k++) {
				data[k] = rows.get(k);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public static boolean removeChairFromDepartment(Connection connect,
			String username, String department) {
		String query = "{ ? = call removechairfromdepartment (?,?) }";
		CallableStatement statement = null;
		boolean removed = false;
		try {
			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, username);
			statement.setString(3, department);

			statement.execute();
			removed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return removed;
	}

	public static ResultSet addReceipts(Connection connect, int paymentOrder,
			ArrayList<ReceiptBundles> receipts) {
		ResultSet rs = null;
		String receipQuery = "{ ? = call addreceipt (?,?,?,?)}";
		String lineQuery = "{? = call addlineitem (?,?,?)}";
		CallableStatement statement = null;
		int receiptID = -1;

		try {
			for (ReceiptBundles receipt : receipts) {
				statement = connect.prepareCall(receipQuery);
				statement.registerOutParameter(1, Types.INTEGER);
				System.out.println(receipt.getTimeStamp().toString());
				statement.setString(2, receipt.getTimeStamp());
				statement.setString(3, receipt.getStore());
				statement.setString(4, receipt.getPictureLocation().toString());
				statement.setInt(5, paymentOrder);
				rs = statement.executeQuery();
				rs.next();
				receiptID = rs.getInt(1);
				for (LineItemWrapper item : receipt.getLineItems()) {
					statement = connect.prepareCall(lineQuery);
					statement.registerOutParameter(1, Types.INTEGER);
					statement.setString(3, item.getItemName());
					statement.setDouble(2, item.getCostForItem());
					statement.setInt(4, receiptID);
					statement.execute();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rs;
	}

	public static int addPaymentOrder(Connection connect, String username,
			String depart, String method, String description, String date) {
		String query = "{ ? = call addpaymentorder (?,?,?, ?,?,?) }";
		CallableStatement statement = null;
		int id = -1;
		try {

			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, username);
			statement.setString(3, depart);
			statement.setString(4, method);
			statement.setString(5, description);
			statement.setString(6, date);
			statement.registerOutParameter(7, Types.INTEGER);

			statement.execute();
			id = statement.getInt(7);
			System.out.println(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;

	}

	public static String[][] getUserPaymentOrders(Connection connect,
			String username) {
		String query = "{ ? = call getuserpaymentorders (?)}";
		CallableStatement statement = null;
		ResultSet rs = null;
		String[][] data = null;
		try {
			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, username);
			rs = statement.executeQuery();

			ArrayList<String[]> rows = new ArrayList<String[]>();
			String[] row = new String[6];
			while (rs.next()) {
				for (int j = 1; j < 7; j++) {
					row[j - 1] = rs.getString(j);
				}
				rows.add(row);
				row = new String[6];
			}

			int numberOfRows = rows.size();
			data = new String[numberOfRows][6];

			 System.out.println(rows.toString());

			for (int k = 0; k < numberOfRows; k++) {
				data[k] = rows.get(k);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public static Object[][] getPendingOrders(Connection connect,
			String department) {
		String query = "{ ? = call getdepartmentpaymentorders (?)}";
		CallableStatement statement = null;
		ResultSet rs = null;
		Object[][] data = null;
		try {
			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, department);
			rs = statement.executeQuery();

			ArrayList<Object[]> rows = new ArrayList<Object[]>();
			Object[] row = new Object[5];
			while (rs.next()) {
				for (int j = 1; j < 6; j++) {
					row[j - 1] = rs.getString(j);
				}
				rows.add(row);
				row = new Object[5];
			}

			int numberOfRows = rows.size();
			data = new Object[numberOfRows][5];

			// System.out.println(rows.toString());

			for (int k = 0; k < numberOfRows; k++) {
				data[k] = rows.get(k);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;

	}

	public static void acceptPaymentOrder(Connection connect, int orderID,
			String username) {
		String query = "{ ? = call acceptpaymentorder (?,?) }";
		CallableStatement statement = null;
		try {
			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setInt(2, orderID);
			statement.setString(3, username);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;

	}

	public static void rejectPaymentOrder(Connection connect, int orderID,
			String username) {
		String query = "{ ? = call rejectpaymentorder (?,?) }";
		CallableStatement statement = null;
		try {
			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setInt(2, orderID);
			statement.setString(3, username);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;

	}
}
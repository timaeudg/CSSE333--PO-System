package connection;

/**
 * Execute sql queries with java application.
 */
import java.sql.*;
import java.util.ArrayList;
import extras.Util;

public class ExecuteSqlQuery {

	public static Connection connectToWhale() {
		Connection connection = null;

		try {
			DatabaseAdapter.registerSQLServerDriver();

			ConnectionInfo timaeudg = new ConnectionInfo("timaeudg", "KirisuteG0m3n");
			connection = DatabaseAdapter.getConnection(null, timaeudg);

//			System.out.println("Driver Registered");

			connection = DatabaseAdapter.getConnection(null, timaeudg);

//			System.out.println("Connection Successful!");
        	} catch (SQLException e) {
			System.out.println("Connection Error");
			e.printStackTrace();
		}

		return connection;
	}

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
		ArrayList<Integer> departments = new ArrayList<Integer>();
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
//				System.out.println("Login Successful!");
				while(rs.next()){
					departments.add(rs.getInt(1));
				}
				
//				departmentIDs=departments.toArray(departmentIDs);
				
				user = new LoggedInUserWrapper(email, departments, login);
				
//				for (Integer id : departments) {
//					System.out.print(id + " ");
//				}
//				System.out.println();
				
				
				
				statement.close();

		} catch (SQLException e) {
			login = false;
			e.printStackTrace();
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

	public static boolean editUser(String oldUsername, String newUsername,
			String newFirstName, String newLastName, String newEmail,
			String newPassword, Connection connection) {
		boolean removed = false;

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
			removed = true;
		} catch (Exception e) {
			e.printStackTrace();
			removed = false;
		}

		return removed;
	}

	public static String[][] getDepartmentOverview(Connection connection) {

		String query = "{ ? = call alldepartmentinfo}";
		CallableStatement statement = null;
		ResultSet rs = null;
		String[][] data = null;
		try {
			statement = connection.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			rs = statement.executeQuery();

			ArrayList<String[]> rows = new ArrayList<String[]>();
			String[] row = new String[5];
			while (rs.next()) {
				for (int j = 1; j < 6; j++) {
					row[j - 1] = rs.getString(j);
				}
				rows.add(row);
				row = new String[5];
			}

			int numberOfRows = rows.size();
			data = new String[numberOfRows][5];

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
				statement.execute();
			}

			added = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return added;
	}

	public static boolean addDepartment(Connection connect,
			String departmentName, String parent, String budget) {
		boolean added = false;

		String query = "{ ? = call newdepartment (?, ?, ?, ?)}";
		CallableStatement statement = null;
		try {
			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, departmentName);
			statement.setDouble(3, Double.parseDouble(budget));
			statement.setDouble(4, Double.parseDouble(budget));
			statement.setString(5, parent);

			statement.execute();
			added = true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return added;
	}

	public static void editDepartment(Connection sQLConnect,
			String departToEdit, String parentDepart, String budget,
			String newName) {
		String query = "{ ? call ";

	}

	public static void deleteDepartment(Connection sQLConnect, String deleted) {
		String query = "{ ? = call deletedepartment (?)}";
		CallableStatement statement = null;
		try {
			statement = sQLConnect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, deleted);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public static boolean removeUserFromDepartment(Connection connect, String username, String department){
		String query = "{ ? = call removeuserfromdepartment (?,?) }";
		CallableStatement statement = null;
		boolean removed =false;
		try{
			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, username);
			statement.setString(3, department);
			
			statement.execute();
			removed = true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return removed;
	}
	
	public static boolean removeChairFromDepartment(Connection connect, String username, String department){
		String query = "{ ? = call removechairfromdepartment (?,?) }";
		CallableStatement statement = null;
		boolean removed = false;
		try{
			statement = connect.prepareCall(query);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, username);
			statement.setString(3, department);
			
			statement.execute();
			removed=true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return removed;
	}
}
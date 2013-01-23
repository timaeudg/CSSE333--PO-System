/**
 * Execute sql queries with java application.
 */
import java.sql.*;

class ExecuteSqlQuery {

	public static void main(String[] args) {

		try {
			/*
			 * Create string of connection url within specified format with
			 * machine name, port number and database name. Here machine name id
			 * localhost and database name is usermaster.
			 */
			String connectionURL = "jdbc:jtds:sqlserver://whale.csse.rose-hulman.edu/POSystem";

			// declare a connection by using Connection interface
			Connection connection = null;

			// declare object of Statement interface that uses for executing sql
			// statements.
			Statement statement = null;
			String queryString = null;

			// declare a resultset that uses as a table for output data from that
			// table.
			ResultSet rs = null;
			int updateQuery = 0;

			// Load JBBC driver "com.mysql.jdbc.Driver".
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();

			/*
			 * Create a connection by using getConnection() method that takes
			 * parameters of string type connection url, user name and password
			 * to connect to database.
			 */
			connection = DriverManager.getConnection(connectionURL, "timaeudg",
					"KirisuteG0m3n");
			
			System.out.println("Connection Successful!\n");

			/*
			 * createStatement() is used for create statement object that is
			 * used for sending sql statements to the specified database.
			 */
			statement = connection.createStatement();

			// sql query of string type to create a data base.
			// String QueryString =
			// "CREATE TABLE user_master1(User_Id INTEGER NOT NULL AUTO_INCREMENT, "
			// +
			// "User_Name VARCHAR(25), UserId VARCHAR(20), User_Pwd VARCHAR(15), primary key(User_Id))";

			// updateQuery = statement.executeUpdate(queryString);

			// sql query to insert values in the specified table.
			// queryString =
			// "INSERT INTO User(ID, FirstName, LastName, Password, Email) VALUES ('1', 'guest','user','testing','steve@example.com')";
			// updateQuery = statement.executeUpdate(queryString);
			//
			// if (updateQuery != 0) {
			// System.out.println("table is created successfully and " +
			// updateQuery + " row is inserted.");
			// }

			// sql query to retrieve values from the specified table.
			// queryString = "SELECT * from User";
			// rs = statement.executeQuery(queryString);
			// while (rs.next()) {
			// System.out.println(rs.getInt(1) + "     " + rs.getString(2)
			// + "      " + rs.getString(3) + "      "
			// + rs.getString(4) + "     " + rs.getString(5) + "\n");
			// }

			// close all the connections.
			//rs.close();
			statement.close();
			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Unable to connect to database.");
		}
	}
}

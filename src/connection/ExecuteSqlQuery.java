package connection;
/**
 * Execute sql queries with java application.
 */
import java.sql.*;


public class ExecuteSqlQuery {
	public static void main(String[] args) {
		try {
			/*
			 * Create string of connection url within specified format with
			 * machine name, port number and database name. Here machine name id
			 * localhost and database name is usermaster.
			 */
			String connectionURL = "jdbc:jtds:sqlserver://whale.csse.rose-hulman.edu/POSystem";
			ConnectionInfo cricket = new ConnectionInfo("moorejm", "crikket007");
			DatabaseAdapter.registerSQLServerDriver();
			
			// declare a connection by using Connection interface
			Connection connection = null;
			
			connection = DatabaseAdapter.getConnection(null, cricket);
			
			// declare object of Statement interface that uses for executing sql
			// statements.
			PreparedStatement statement = null;
			String queryString = null;

			// declare a resultset that uses as a table for output data from that
			// table.
			ResultSet rs = null;
			int updateQuery = 0;
			queryString = "SELECT * FROM Users";

			// Load JBBC driver "com.mysql.jdbc.Driver". JTDS: net.sourceforge.jtds.jdbc.Driver
//			Class.forName("com.mysql.jdbc.Driver").newInstance();
			

			/*
			 * Create a connection by using getConnection() method that takes
			 * parameters of string type connection url, user name and password
			 * to connect to database.
			 */
//			connection = DriverManager.getConnection(connectionURL, "timaeudg",
//					"KirisuteG0m3n");
			
			System.out.println("Connection Successful!\n");

			/*
			 * createStatement() is used for create statement object that is
			 * used for sending sql statements to the specified database.
			 */
			statement = connection.prepareStatement(queryString);

			// sql query of string type to create a data base.
//			String QueryString =
//			"CREATE TABLE user_master1(User_Id INTEGER NOT NULL AUTO_INCREMENT, "
//			+
//			"User_Name VARCHAR(25), UserId VARCHAR(20), User_Pwd VARCHAR(15), primary key(User_Id))";
//
//			updateQuery = statement.executeUpdate(QueryString);

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
			
			statement.execute();
			
//			JDBC.processRows(statement);
			
			rs = lookupUsers("Alex", "", "", "", connection);
			
			
//			rs = statement.executeQuery();
			while (rs.next()) {
			System.out.println(rs.getString(1) + "     " + rs.getString(2)
			+ "      " + rs.getString(3) + "      "
			+ rs.getString(4) + "\n");
			}

			// close all the connections.
//			rs.close();
			statement.close();
			connection.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Unable to connect to database.");
		}
	}
	
	public static Connection connectToWhale(){
		Connection connection = null;	
		
		try {
			DatabaseAdapter.registerSQLServerDriver();
			System.out.println("Driver Registered");
			ConnectionInfo timaeudg = new ConnectionInfo("timaeudg", "KirisuteG0m3n");
			connection = DatabaseAdapter.getConnection(null, timaeudg);
			
			System.out.println("Connection Successful!");
		} catch (SQLException e) {
			System.out.println("Connection Error");
			e.printStackTrace();
		}
	
		
		return connection;
	}
	
	public static boolean login(String email, String password, Connection connection){
		
//		String part1 = "SELECT CASE WHEN EXISTS (SELECT * FROM [Users] WHERE Email= '";
//		String part2 = "' AND Password = '";
//		String part3 = "' ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END";
		String piecedString = "{ ? = call attemptlogin ( ? , ? ) }";
		ResultSet rs = null;
		CallableStatement statement = null;
		
		boolean login = false;
		try {
			statement = connection.prepareCall(piecedString);
			statement.registerOutParameter(1, Types.INTEGER);
			statement.setString(2, email);
			statement.setString(3, password);
			
			boolean connected = statement.execute();
			
			
			//rs.next();
//			if(derp){
				login = true;
				System.out.println("Login Successful!");
//			}
//			else{
//				System.out.println("Login Failed!");
//			}
				
				statement.close();
		} catch (SQLException e) {
			login = false;
			e.printStackTrace();
		}
		
		
		return login;
		
	}

	public static ResultSet lookupUsers(String firstName, String lastName,
			String email, String username , Connection connection) {
			String query = " { ? = call lookupuser (? , ? , ? , ? ) }";
			ResultSet rs = null;
			try{
				CallableStatement statement = null;
				statement = connection.prepareCall(query);
				statement.setQueryTimeout(5);
				
				statement.registerOutParameter(1, Types.INTEGER);
				
				if(!(username==null || username.isEmpty())){
					statement.setString(2, username);
				}
				else{
					statement.setString(2, null);
				}
				if(!(firstName==null || firstName.isEmpty())){
					statement.setString(3, firstName);
				}
				else{
					statement.setString(3, null);
				}
				if(!(lastName==null || lastName.isEmpty())){
					statement.setString(4, lastName);
				}
				else{
					statement.setString(4, null);
				}
				if(email!=null && !email.isEmpty()){
					statement.setString(5, email);
				}
				else{
					statement.setString(5, null);
				}
				
				rs = statement.executeQuery();
				
//				for(int j = 0; j<10000; j++){
//					if(!rs.next()){
//						System.out.println("empty");
//					}
////					rs.close();
//					rs = statement.getResultSet();
//				}
				
				
				
//				statement.close();
				
//				while(!rs.next()){
//					System.out.println("nada");
//					//statement.getResultSet();
//						statement.getMoreResults();
//						rs.close();
//						rs = statement.getResultSet();
//						System.out.println("still RS");
//					
//				}
				
				
			}
			catch(Exception e){
				System.err.println("User Lookup failed.");
				e.printStackTrace();
			}
			
			return rs;
		
	}

	public static boolean addUser(String firstName, String lastName, String email,
			String password, String username, Connection connection) {
		String query = "{ ? = call newuser ( ? , ? , ? , ? , ? ) }";
		CallableStatement statement = null;
		boolean added = true;
		
		try{
			statement = connection.prepareCall(query);
			statement.registerOutParameter(1,Types.INTEGER);
			statement.setString(2, firstName);
			statement.setString(3, lastName);
			statement.setString(4, username);
			statement.setString(5, password);
			statement.setString(6, email);
			
			statement.execute();
			statement.close();
			}
		catch(Exception e){
			e.printStackTrace();
			added = false;
		}
		
		return added;
	}
	
	
}


package connection;

import java.util.Scanner;

/**
 * Stores connection info for a user
 * @author moorejm
 *
 */
public class ConnectionInfo {

	private String user = null;
	private String pwd = null;
	
	/**
	 * Constructor taking a user's name and password
	 * @param user
	 * @param pwd
	 */
	public ConnectionInfo(String user, String pwd) {
		this.user = user;
		this.pwd = pwd;
	}
	
	/**
	 * Null constructor
	 */
	public ConnectionInfo() {
		this(null, null);
	}
	
	/**
	 * Get's the user's name
	 * @return the username for this ConnectionInfo
	 */
	public String getUsername() {
		return user;
	}

	/**
	 * Gets the user's password
	 * @return the password for this ConnectionInfo
	 */
	public String getPassword() {
		return pwd;
	}
	
	/**
	 * Gets whether the login for this user was cancelled
	 * @return false if ConnectionInfo fields are blank
	 */
	public boolean wasCancelled() {
		return (user == null && pwd == null) || (user.isEmpty() && pwd.isEmpty());
	}
	
	/**
	 * Prompts for ConnectionInfo username and password
	 * @param pwd The ConnectionInfo to store the information
	 */
	public static void prompt(ConnectionInfo pwd) {
		Scanner in = new Scanner(System.in);
		String user = null; 
		String pass = null;
		while (pwd.wasCancelled()) {
			System.out.println("Username: (c to cancel)");
			user = in.nextLine();
			if (user.equalsIgnoreCase("c")) {
				pwd.user = null;
				pwd.pwd = null;
				break;
			}
			System.out.println("Password: (c to cancel)");
			pass = in.nextLine();
			if (pass.equalsIgnoreCase("c")) {
				pwd.user = null;
				pwd.pwd = null;
				break;
			}
			pwd.user = user;
			pwd.pwd = pass;
		}
		in.close();	
	}
}

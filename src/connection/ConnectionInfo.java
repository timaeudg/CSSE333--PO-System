package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

/**
 * Stores connection info for a user
 * @author moorejm
 *
 */
public class ConnectionInfo {

	private String user = null;
	private String pwd = null;
	
	public ConnectionInfo(String user, String pwd) {
		this.user = user;
		this.pwd = pwd;
	}
	public ConnectionInfo() {
		this(null, null);
	}
	public String getUsername() {
		return user;
	}

	public String getPassword() {
		return pwd;
	}
	
	public boolean wasCancelled() {
		return (user == null && pwd == null) || (user.isEmpty() && pwd.isEmpty());
	}
	
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

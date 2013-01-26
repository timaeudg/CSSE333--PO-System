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

	private String user = "";
	private String pwd = "";
	
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
		return user.isEmpty() || pwd.isEmpty();
	}
	
	public static void prompt(ConnectionInfo pwd) {
			System.out.println("Username: ");
			Scanner in = new Scanner(System.in);
			pwd.user = in.nextLine();
			System.out.println("Password: ");
			pwd.pwd = in.nextLine();
	}
}

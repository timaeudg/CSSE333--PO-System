package connection;
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
	public String getUsername() {
		return user;
	}

	public String getPassword() {
		return pwd;
	}
	
	public boolean wasCancelled() {
		return user.isEmpty() || pwd.isEmpty();
	}
}

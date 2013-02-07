package connection;

import java.util.ArrayList;

/**
 * A wrapper for a logged-in user. Holds the username, the chairs the user is a
 * department of, and a boolean field for the status of the user.
 * 
 * @author timaeudg
 * 
 */
public class LoggedInUserWrapper {
	private String username;
	private ArrayList<String> chairs;
	private boolean loggedIn;

	/**
	 *LoggedInUserWrapper Constructor 
	 * @param username
	 * @param chairs
	 * @param logged
	 */
	public LoggedInUserWrapper(String username, ArrayList<String> chairs,
			boolean logged) {
		this.username = username;
		this.chairs = chairs;
		this.loggedIn = logged;
	}

	/**
	 * Get the username of this LoggedInUserWrapper
	 * @return the username of this LoggedInUserWrapper
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Gets a list of LoggedInUserWrapper
	 * @return a list of ChairIDs for the departments this user is a chair ofF
	 */
	public ArrayList<String> getChairs() {
		return this.chairs;
	}

	/**
	 * Return the status of this LoggedInUserWrapper
	 * @return true if this user is logged in
	 */
	public boolean getLogged() {
		return this.loggedIn;
	}

}

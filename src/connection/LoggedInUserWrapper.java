package connection;

import java.util.ArrayList;

public class LoggedInUserWrapper {
	private String username;
	private ArrayList<Integer> chairs;
	private boolean loggedIn;
	
	public LoggedInUserWrapper (String username, ArrayList<Integer> chairs, boolean logged){
		this.username=username;
		this.chairs = chairs;
		this.loggedIn = logged;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public ArrayList<Integer> getChairs(){
		return this.chairs;
	}
	
	public boolean getLogged(){
		return this.loggedIn;
	}
	
}

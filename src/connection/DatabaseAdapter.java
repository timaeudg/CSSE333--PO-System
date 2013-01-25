package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;


public class DatabaseAdapter {
	public static void main(String[] args) {
		String url = "jdbc:jtds:sqlserver://whale.csse.rose-hulman.edu/POSystem";
        String userName = "<username>";
		String password = "<password>";
		
		try {
			System.out.println("Username: ");
			Scanner in = new Scanner(System.in);
			userName = in.nextLine();
			System.out.println("Password: ");
			password = in.nextLine();
		    Class.forName("net.sourceforge.jtds.jdbc.Driver");
		    
			Connection conn = DriverManager.getConnection (url, userName,password);
		    System.out.println ("Connection successful");     
		    conn.close();
		} catch (Exception e) {
		    System.err.println ("Cannot connect to database server");
		    e.printStackTrace();
		}
	}
}

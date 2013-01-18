import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseAdapter {
	public static void main(String[] args) {
		String url = "jdbc:jtds:sqlserver://whale.csse.rose-hulman.edu/POSystem";
        String userName = "<username>";
		String password = "<password>";
		
		try {
		    Class.forName("net.sourceforge.jtds.jdbc.Driver");
		    
			Connection conn = DriverManager.getConnection (url, userName,password);
		    System.out.println ("Connection successful");     
		} catch (Exception e) {
		    System.err.println ("Cannot connect to database server");
		    e.printStackTrace();
		}
	}
}

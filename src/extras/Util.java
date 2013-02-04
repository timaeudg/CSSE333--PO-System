package extras;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	public static boolean isValidEmailAddress(String email) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		return  m.matches();
	}
	
	public static void main(String[] args) {
		System.out.println("Email checker: ");
		String email = "";
		email = "moorejm@rose-hulman.edu";
		System.out.printf("Testing: %s\t%s\n", email, isValidEmailAddress(email));
		email = "cricket.007@live.com";
		System.out.printf("Testing: %s\t%s\n", email, isValidEmailAddress(email));
		email = "crikket.007@gmail.com";
		System.out.printf("Testing: %s\t%s\n", email, isValidEmailAddress(email));
		email = "D@D.d";
		System.out.printf("Testing: %s\t%s\n", email, isValidEmailAddress(email));
	}
}

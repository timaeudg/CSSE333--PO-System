package extras;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities class
 * @author moorejm
 *
 */
public class Util {
	/**
	 * Checks an email using regex
	 * @param email
	 * @return true if email is a valid email string
	 */
	public static boolean isValidEmailAddress(String email) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		return  m.matches();
	}
	
	/**
	 * Tests valid email addresses
	 */
	private static void emailTest() {
		System.out.println("Email checker: ");
		String email = "";
		email = "moorejm@rose-hulman.edu";
		System.out.printf("Testing: %s\t%s\n", email, isValidEmailAddress(email));
		email = "cricket.007@live.com";
		System.out.printf("Testing: %s\t%s\n", email, isValidEmailAddress(email));
		email = "crikket.007@gmail.com";
		System.out.printf("Testing: %s\t%s\n", email, isValidEmailAddress(email));
		email = "test.com";
		System.out.printf("Testing: %s\t%s\n", email, isValidEmailAddress(email));
	}
}

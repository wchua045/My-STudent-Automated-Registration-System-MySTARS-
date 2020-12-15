package boundaries;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

import controllers.loginController;
import entities.User;
import exceptions.NotInAccessPeriodException;
import exceptions.UserPasswordInvalidException;

/**
 * Represents a boundary class for displaying login page
 * 
 * @author 
 *
 */
public class loginPage {
	/**
	 * Variable used to store username and password.
	 */
	private static String unEntry = "", pwEntry = "";
	/**
	 * Scanner to read input from user.
	 */
	private static Scanner scanner = new Scanner(System.in);
	/**
	 * Variable to store User object who logged in.
	 */
	//private static List<String> user = null;
	
	/**
	 * Variable to store whether user successfully logged in
	 */
	private static Boolean auth = false;
	

	public static void loginDisplay() {
		System.out.println("\tPlease login to continue");
		Console cs = System.console();
		
		while (auth == false) { //while user list is empty
			System.out.print("\tUsername : ");
			unEntry = scanner.nextLine().toUpperCase();
			//unEntry = cs.readLine("Username: ");//.toUpperCase();
			//System.out.print("Password : ");
			if (cs != null) {
				char[] passString = cs.readPassword("\tPassword : ");
				pwEntry = new String(passString);
			} else {
				System.out.print("\tPassword: ");
				pwEntry = scanner.nextLine();
				//System.out.println("Password entered: " + pwEntry);
			}
			try {
				auth = loginController.login(unEntry, pwEntry);
			}
			catch (UserPasswordInvalidException e) {
				System.out.println("\tInvalid login. Try again.");
				continue;
			}
			catch (NotInAccessPeriodException e) {
				System.out.println("\tNot in access period.");
			}
		}
	}
}

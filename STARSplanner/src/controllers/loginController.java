package controllers;

import java.util.Scanner;

import boundaries.STARSApp;
import boundaries.adminBoundary;
import boundaries.studentBoundary;
import entities.Admin;
import entities.Student;
import entities.User;
import exceptions.NotInAccessPeriodException;
import exceptions.UserPasswordInvalidException;
import controllers.manager.adminManager;
import controllers.manager.studentManager;

/**
 * This represents a controller which can take care of the login function to the MySTARS application.
 * It consists of functions which the loginBoundary uses to validate a user's credentials.
 * 
 * @author
 *
 */
public class loginController {
	/**
	 * Variable used to store username and password.
	 */
	private static String unEntry = "", pwEntry = "";
	/**
	 * Scanner to read input from user.
	 */
	private static Scanner scanner = new Scanner(System.in);
	/**
	 * Validate if given username and password is the same as the one in the
	 * database. It will first try to retrieve User from the database. Then it
	 * will ask the User object to check if the password entered by the user is
	 * correct. Returns the User object if successfully validated else returns
	 * null.
	 * 
	 * @param unEntry
	 *            Username that the user enters.
	 *
	 * @return User object of user that called the method.
	 */
	public static User findUser (String unEntry) {
        User currUser = new User();
        // Should search through the list of users

        // Create a queryStudent to search in StudentList by username
        Student queryStudent = new Student();
        queryStudent.setUsername(unEntry);
        studentManager studentMgr = new studentManager();
        currUser = studentMgr.find(queryStudent);

        // Create a queryAdmin to search in AdminList by username
		if (currUser == null) {
			Admin queryAdmin = new Admin();
			queryAdmin.setUsername(unEntry);
			adminManager adminMgr = new adminManager();
			currUser = adminMgr.find(queryAdmin);
		}

        // If matching user found
        if (currUser != null){
			return currUser;
			}
        else 
        	return null;
    
        }
	/**
	 * For user login. Returns true if login successfully if not return false.
	 * 
	 * @return true or false.
	 */
	public static boolean login(String unEntry, String pwEntry) throws UserPasswordInvalidException, NotInAccessPeriodException {
		User user;
		if (!unEntry.isEmpty() & !pwEntry.isEmpty()) {
			user = loginController.findUser(unEntry);
			if (user == null) {
				throw new UserPasswordInvalidException ("Invalid username or password. Please try again.");
			}
			String hashedPassword = User.generateHashedPassword(pwEntry);
//			System.out.println("hashedPassword: "+hashedPassword);
			
//			System.out.println("login() check: " + user);
//			System.out.println(user.getAccountType());
//			System.out.println("Validate result: " + user.validate(hashedPassword));
			
			// Try to log in --> Validate pw
			
			if (!user.validate(hashedPassword))
			{
				// Password is wrong -> deny log in
				throw new UserPasswordInvalidException ("Invalid username or password. Please try again.");
			} else {
				// Check usertype
				if (user.getAccountType().equals("S")) {
					// Check for access period
					if(accessDateTimeController.checkAccess(user)) {
//						Student student = new Student();
//				        student.setUsername(unEntry);
//				        studentManager studentMgr = new studentManager();
//				        student = studentMgr.find(student);
						studentBoundary.display(user.getUsername(), ((Student)user).getMatricNum());
					}

					else {
						throw new NotInAccessPeriodException("You are not allowed to access the system at this time. Please try again.");
						//System.out.println("\t	You are not allowed to access the system at this time. Please try again.");
						//return false;
					}
				} else if (user.getAccountType().equals("A")) {
					System.out.println("Welcome to admin");
					adminBoundary.display(user.getUsername());
				}
			}
		} else
			throw new UserPasswordInvalidException ("\t  Please enter username and password. Please try again.");
			//System.out.println("Please enter username and password.");
		return false;
	}
	
	/**
	 * Allows current user to logout and return to STARSApp Display.
	 */
	public static void logout() {
		STARSApp.main(null);
	}
}
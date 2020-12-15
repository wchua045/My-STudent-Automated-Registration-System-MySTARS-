package controllers;

import java.time.LocalDateTime;

import controllers.manager.accessDateTimeManager;
import entities.AccessDateTime;
import entities.User;
import entities.Student;

/**
 * Represents a controller which take care of the student's access period (Date and Time) for this MySTARS application. 
 * It consists of a function which can check a student's access period.
 */
public class accessDateTimeController {
	/**
	 * Checks if user is logging in at a time within their allowed access period.
	 * @param user 
	 * User object which is a Student and has programme and yearOfStudy fields initialised.
	 * @return true or false
	 */
    public static boolean checkAccess (User user) {
        // query for the correct access Date Time
		AccessDateTime queryAccess = new AccessDateTime();
		queryAccess.setProgramOfStudy(((Student)user).getProgramme());
		queryAccess.setYearOfStudy(((Student)user).getYearOfStudy());

		accessDateTimeManager accessMgr = new accessDateTimeManager();
		AccessDateTime accessInfo = accessMgr.find(queryAccess);

    	// get today's date
		if (accessInfo != null) {
			LocalDateTime currdate = LocalDateTime.now(); //for getting current date
			
			if (currdate.compareTo(accessInfo.getStartAccessPeriod()) >= 0 && currdate.compareTo(accessInfo.getEndAccessPeriod()) <= 0) { // Current Date is after startdate and current date is before enddate
				return true;
			} else { // not within the access period
				return false;
			}
		} else {
			System.out.println("Could not get access period for the school. ");
			return false;
		}
    }
}

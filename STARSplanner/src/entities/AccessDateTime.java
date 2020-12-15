package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Represents the access period within which a student of a programme and year can log in to the STARS Planner Portal. <br>
 * AccessDateTime Keys: Programme and Year of Study
 */

public class AccessDateTime implements EntityObject, Serializable {
	/**
	 * Programme (or degree) a student is enrolled in (e.g. CSC - Computer Science).
	 */
	private String programOfStudy;
	/**
	 * Year of the programme a student is currently in (e.g. Freshmen: yearOfStudy = 1).
	 */
	private int yearOfStudy;
	/**
	 * Start datetime of the access period (of a particular programme/year combination).
	 */
	public LocalDateTime startAccessPeriod;
	/**
	 * Start datetime of the access period (of a particular programme/year combination).
	 */
	public LocalDateTime endAccessPeriod;

	/**
	 * Empty AccessDateTime constructor for dummy variables.
	 */
	public AccessDateTime () {}

	/**
	 *
	 * @param p Programme of Study
	 * @param y Year of Study
	 * @param startAccess Start datetime of access period
	 * @param endAccess End datetime of access period
	 */
	public AccessDateTime(String p, int y, LocalDateTime startAccess, LocalDateTime endAccess) {
		programOfStudy = p;
		yearOfStudy = y;
		startAccessPeriod = startAccess;
		endAccessPeriod = endAccess;
	}

	// get and set methods
	public String getProgramOfStudy() {
		return programOfStudy;
	}
	public int getYearOfStudy() {
		return yearOfStudy;
	}
	public LocalDateTime getStartAccessPeriod() {
		return startAccessPeriod;
	}
	public LocalDateTime getEndAccessPeriod() {
		return endAccessPeriod;
	}

	public void setProgramOfStudy(String p) {
		programOfStudy = p;
	}
	public void setYearOfStudy(int y) {
		yearOfStudy = y;
	}
	public void setStartAccessPeriod(LocalDateTime startAccess) {
		startAccessPeriod = startAccess;
	}
	public void setEndAccessPeriod(LocalDateTime endAccess) {
		endAccessPeriod = endAccess;
	}
}
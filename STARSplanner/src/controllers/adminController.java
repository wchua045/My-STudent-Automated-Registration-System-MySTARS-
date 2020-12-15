
package controllers;

import java.time.LocalDateTime;
import java.time.format.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.String;

import controllers.manager.*;
import entities.AccessDateTime;
import entities.*;

/**
 * Represents a controller which will be used by an administrator. It contains
 * functions which an admin can do on MySTARS.
 * 
 * @author
 *
 */
public class adminController {
	private static Scanner sc;
	private static int choice;
	private static BasicManager dm;


	// 1. Edit Access Period
	/**
	 * A method called by an admin to edit student access period from different
	 * school.
	 */
	public static AccessDateTime editAccessPeriod(String POS, int YOS, String newValue, int choice) {
		dm = new accessDateTimeManager();
		AccessDateTime tempAccess = new AccessDateTime();

		tempAccess.setProgramOfStudy(POS);
		tempAccess.setYearOfStudy(YOS);
		AccessDateTime AccessObject = (AccessDateTime) dm.find(tempAccess);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		try {
			if (choice == 1) {
				AccessObject.setStartAccessPeriod(LocalDateTime.parse(newValue, formatter));
			} else if (choice == 2) {
				AccessObject.setEndAccessPeriod(LocalDateTime.parse(newValue, formatter));
			}
			dm.fileWrite();
		} catch (IllegalArgumentException e) {
			System.err.println("Invalid Input.");
		} catch (NullPointerException n){
			System.err.println("Access Datetime Entry does not exist.");
		} catch (DateTimeParseException d) {
			System.err.println("Invalid Date Entry.");
		}
		return AccessObject;
	}

	// 2. Adding student with relevant checks

	/**
	 * Checks if a student in the database already has the same username. <br>
	 * If so, username cannot be used for newly created student.
	 * @param Uname new username attempting to be assigned to newly created student.
	 * @return whether username can be used for newly created student.
	 */
	public static boolean checkUsername(String Uname){
		dm = new studentManager();
		Student tempStudent = new Student();
		tempStudent.setUsername(Uname);
		return dm.find(tempStudent) == null;
	}

	/**
	 * Checks if two entries of password match.
	 * @param pw1 First password entry by user.
	 * @param pw2 Second password entry by user.
	 * @return whether the two entries of the password are the same.
	 */
	public static boolean checkPassword(String pw1, String pw2){
		return pw1.equals(pw2);
	}

	/**
	 * Checks if the gender entry is valid.
	 * @param gender Gender input character value by user.
	 * @return whether the input character is either 'M' or 'F'.
	 */
	public static boolean checkGender(char gender) {
		return ((gender == 'M') ||(gender == 'F'));
	}

	/**
	 * Checks if a student in the database already has the same matricNum. <br>
	 * If so, matricNum cannot be used for newly created student.
	 * 	@param matric new matricNum attempting to be assigned to newly created student.
	 * 	@return whether matricNum can be used for newly created student.
	 */
	public static boolean checkMatric(String matric){
		studentManager sm = new studentManager();
		Student tempStudent = new Student();
		tempStudent.setMatricNum(matric);
		return sm.findByMatric(tempStudent) == null;
	}

	/**
	 * Adds a new Student to the database. <br>
	 * All input fields mirror that of the Student constructor.
	 * @return whether or not student is succesfully added.
	 */
	public static boolean addStudent(String username, String password, String name, char gender,
								  String matric, int yearofstudy, String programme, String school, String nationality, String email){
		dm = new studentManager();
		Student temp_student = new Student(username, password, name, gender, "S", matric, yearofstudy, programme, school, nationality, email);
		boolean result = dm.create(temp_student);
		dm.printAll();
		return result;
	}

	// 3. Adding Course

	/**
	 * Adds a new Course to the database. <br>
	 * All input fields mirror that of the Course constructor.
	 */
	public static void addCourse(String cc,  ArrayList<Index> initialIndexList, int AUs, String sch){
		dm = new courseManager();
		dm.create(new Course(cc, initialIndexList, AUs, sch));
	}

	/**
	 * Creates and a new Index instance. <br>
	 * All input fields mirror that of the Index constructor.
	 */
	public static Index addInitialIndexes(String cc, int index, int size, ArrayList<Lesson> Lessons){
		return new Index(cc, index, size, Lessons);
	}
	/**
	 * Creates a new Lesson instance. <br>
	 * All input fields mirror that of the Index constructor.
	 */
	public static Lesson makeLessons(String t, int d, String w, int start, int length, String location, String g){
		return new Lesson(t, d, w, start, length, location, g);
	}

	/**
	 * Checks the input lesson type validity.
	 * @param lesson_type lesson type input by user.
	 * @return whether the input lesson type is either 'LECTURE', 'LAB' or 'TUTORIAL'
	 */
	public static boolean checkLessonType(String lesson_type){
		return (lesson_type.equals("LECTURE")) || (lesson_type.equals("LAB")) || (lesson_type.equals("TUTORIAL"));
	}

	/**
	 * Checks the input lesson week validity.
	 * @param lesson_week lesson week input by user.
	 * @return whether the input lesson type is either 'ODD', 'EVEN' or 'BOTH'
	 */
	public static boolean checkLessonWeek(String lesson_week){
		return (lesson_week.equals("BOTH")) || (lesson_week.equals("ODD")) || (lesson_week.equals("EVEN"));
	}
	/**
	 * Checks if adding new Course with inputted course code is valid.
	 * @param courseCode course code input by the user.
	 * @return if Course with the same course code already exists in the database.
	 */
	public static boolean checkIfCourseExists(String courseCode) {
		dm = new courseManager();
		Course course = new Course();
		course.setCourseCode(courseCode);
		if(dm.find(course) == null)
			return true;
		else return false;
	}

	// 4. Updating Course

	/**
	 * Updates the Course code of a Course.
	 * @param old_cc old Course code of Course to be updated.
	 * @param new_cc new Course code.
	 * @return whether update was successful.
	 */
	public static boolean updateCourseCode(String old_cc, String new_cc){
		Course course = new Course();
		course.setCourseCode(old_cc);
		dm = new courseManager();
		Course real_course = (Course)dm.find(course);
		if (real_course == null)
			return false;
		else {
			real_course.setCourseCode(new_cc);
			for (Index index: real_course.getIndexList()){
				index.setCourseCode(new_cc);
			}
			dm.fileWrite();
			return true;
		}
	}

	/**
	 * Updates school of a Course.
	 * @param cc Course code of Course to be updated.
	 * @param new_sch new school value of Course.
	 * @return whether update was successful.
	 */
	public static boolean updateSchool(String cc, String new_sch){
		Course course = new Course();
		course.setCourseCode(cc);
		dm = new courseManager();
		if (dm.find(course) == null)
			return false;
		else {
			((Course)dm.find(course)).setSchool(new_sch);
			dm.fileWrite();
			return true;
		}
	}

	/**
	 * Updates number of AUs of a Course.
	 * @param cc Course code of Course to be updated.
	 * @param new_AUs new number of AUs of Course.
	 * @return whether update was successful.
	 */
	public static boolean updateAUs(String cc, int new_AUs){
		Course course = new Course();
		course.setCourseCode(cc);
		dm = new courseManager();
		if (dm.find(course) == null)
			return false;
		else {
			((Course)dm.find(course)).setNumAus(new_AUs);
			dm.fileWrite();
			return true;
		}
	}

	/**
	 * Adds new indexes to an existing Course.
	 * @param index new Index to be added to specified Course.
	 * @param cc Course code of Course to be updated.
	 * @return whether addition of index was successful.
	 */
	public static boolean addIndex(Index index, String cc){
		Course course = new Course();
		course.setCourseCode(cc);
		dm = new courseManager();
		Course real_course = (Course)dm.find(course);
		if (real_course == null)
			return false;
		else {
			real_course.addIndex(index);
			dm.fileWrite();
			return true;
		}
	}
	/**
	 * Removes existing indexes from an existing Course.
	 * @param index Index to be removed from specified Course.
	 * @param cc Course code of Course to be updated.
	 * @return whether deletion of index was successful.
	 */
	public static boolean removeIndex(Index index, String cc){
		Course course = new Course();
		course.setCourseCode(cc);
		dm = new courseManager();
		Course real_course = (Course)dm.find(course);
		if (real_course == null)
			return false;
		else {
			real_course.removeIndex(index);
			dm.fileWrite();
			return true;
		}
	}

	/**
	 * Checks vacancy of a entered index number.
	 * @param cc Course code of Course to be checked.
	 * @param index index of Index with vacancy to be checked.
	 * @return a string with vacancies against size of index.
	 */
	public static String checkVacancyAdmin(String cc, int index) {
		dm = new indexManager();
		Index tempIndex = new Index();

		tempIndex.setCourseCode(cc);
		tempIndex.setIndex(index);
		Index realIndex = (Index)dm.find(tempIndex);
		
		if (realIndex != null) {// Index admin wants to view by is valid and within a course
			String vacanciesPlusSize = Integer.toString(realIndex.getVacancies()) + "/" + Integer.toString(realIndex.getSize());
			return vacanciesPlusSize;
		}
		else 
			return null;
	}

	/**
	 * Prints a list of students by Index.
	 * @param cc Course code of index to print Students of.
	 * @param index Index to get a list of students in to test.
	 * @return whether print is successful.
	 */
	public static boolean printStudentListByIndex(String cc, int index) {
		indexManager im = new indexManager();
		Index tempIndex = new Index(); //

		tempIndex.setCourseCode(cc);
		tempIndex.setIndex(index);
		Index realIndex = im.find(tempIndex);
		if (realIndex != null) {// Index admin wants to view by is valid and within a course
			im.printByIndex(realIndex);
			return true;
		}
		else
			return false;
	}

	/**
	 * Prints a list of students by Course.
	 * @param cc Course code to print Students of.
	 * @return whether print is successful.
	 */
	public static boolean printStudentListByCourse(String cc) {
		indexManager im = new indexManager();
		dm = new courseManager();
		Course tempCourse = new Course();

		tempCourse.setCourseCode(cc);
		Course realCourse = (Course)dm.find(tempCourse);
		if (realCourse != null){ // Course admin wants to view by is valid
			im.printByCourse(realCourse);
			return true;
		}
		else
			return false;

	}
	/**
	 * Prints a list of all Courses.
	 */
	public static void printAllCourses(){
		dm = new courseManager();
		dm.printAll();
	}
	
}
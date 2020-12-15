package controllers;

import java.util.ArrayList;

import controllers.manager.courseManager;
import controllers.manager.indexManager;
import controllers.manager.studentManager;
import controllers.manager.swopManager;
import entities.Course;
import entities.Index;
import entities.PendingSwop;
import entities.Student;
import entities.User;
/**
 * Provides methods to create, drop, and handle swop requests by students. 
 * Both students who want to swap indexes with one another must be from the same course, and they must each already be in an index of that course. Then, they must enter
 * each other's matriculation number and password to ensure that no other third party can swap with their pending swaps. When trying to add a new swap, the system does
 * multiple checks to ensure that a pending swap added would be valid. This prevents conflicts when a pending swap is being carried out.
 * @author Daniel
 *
 */
public class swopController {
//	private static swopManager swopMgr = new swopManager();
//	private static studentManager studMgr = new studentManager();
//	private static indexManager indexMgr = new indexManager();


	/**
	 * Creates a new swop if a matching one does not already exist
	 * @param course Desired course code to swop
	 * @param fromMatric Matriculation number of current student.
	 * @param fromIndex Index of the current student (must be existing)
	 * @param toMatric Matriculation number of the swop partner
	 * @param toIndex Index that the current student wants to swop to
	 * @param toPW Password of the swop partner for validation of swop
	 * @return True if swop is successfully registered. False otherwise.
	 */
    public static boolean registerSwop (String course, String fromMatric, int fromIndex, String toMatric, int toIndex, String toPW) {
    	swopManager swopMgr = new swopManager();
    	studentManager studMgr = new studentManager();
    	indexManager indexMgr = new indexManager();
    	courseManager courseMgr = new courseManager();
    	
		Course courseObj = new Course();
		courseObj.setCourseCode(course);
		courseObj = courseMgr.find(courseObj);
		ArrayList<Index> indexList = new ArrayList<Index>();
		indexList = courseObj.getIndexList();
    	
    	// Instantiating one queryswop for query purposes
    	PendingSwop querySwop = new PendingSwop();
    	querySwop.setCourseCode(course);
        querySwop.setFromMatric(fromMatric);
        querySwop.setFromIndex(fromIndex);
        querySwop.setToMatric(toMatric);
        querySwop.setToIndex(toIndex);
        
        // Instantiating the relevant students
        Student fromStudent = new Student();
        fromStudent.setMatricNum(fromMatric);
        fromStudent = studMgr.findByMatric(fromStudent);
        
        Student toStudent = new Student();
        toStudent.setMatricNum(toMatric);
        toStudent = studMgr.findByMatric(toStudent);
        
        // Instantiating the relevant index
        Index fromIndexObj = new Index();
        fromIndexObj.setIndex(fromIndex);
        fromIndexObj.setCourseCode(course);
        fromIndexObj = indexMgr.find(fromIndexObj);
        
        Index toIndexObj = new Index();
        toIndexObj.setIndex(toIndex);
        toIndexObj.setCourseCode(course);
        toIndexObj = indexMgr.find(toIndexObj);
        
        // Check if fromStudent, toStudent, fromIndexObj, toIndexObj exist
        if (fromStudent == null ||
        	fromIndexObj == null) {
        	System.out.println("Can't find index in your registered courses. Please check and try again.");
        	return false;
        }
        if (toStudent == null ||
        	toIndexObj == null) {
        	System.out.println("Can't find swap partner or the requested course index. Please check and try again.");
        	return false;
        }
        
        // Check if both students in the swop have their respective indexes
        if(!swopController.bothStudentsHaveIndex(fromStudent, fromIndex, toStudent, toIndex)){
        	return false;
        }
        
        
        // Check if a swop created by fromMatric exists
        if (swopController.isSimilar(querySwop)) {
        	System.out.println("You have created a similar pending swop. Please check swops.");
        	return false;
        }
        // Check if "From" exists as the destination of a pending swop of someone else.
        else if (swopController.isDestination(querySwop)) {
        	System.out.println("Someone has created a pending swop with you. Please check friend's particulars.");
        	return false;
        }
        // Check if a valid swop has been created by another Student
        else if(swopController.isComplement(querySwop)) {
        	// swopIndex (this works)
        	indexMgr.swopIndex(querySwop);
        	
        	// swopStudentIndex
        	studMgr.swopStudentIndex(querySwop);
        	return true;
        }
        else {
            // Check if the pw given for the swop partner is valid
            String hashedPW = User.generateHashedPassword(toPW);
            if(toStudent == null || !toStudent.validate(hashedPW)) {
            	System.out.println("Invalid User/Password for swap partner");
            	return false;
            }
            
        	// Check if the swop clashes with "From" timetable and "To" timetable
            // Drop current index temporarily
            fromStudent.getTimetable().clearIndex(fromIndexObj);
            // Check for clash when you add the new index
            if (fromStudent.getTimetable().checkClash(toIndexObj)) {
            	System.out.println("The new index chosen clashes with your timetable");
            	return false;
            }
            
            
            // create a new swop
        	swopMgr.create(querySwop);
        	System.out.println("New swap has been successfully created");
        	return true;
        }
    }
    
    /**
     * Deletes the matching PendingSwop, if it exists. All fields must match.
	 * @param fromMatric Matriculation number of current student.
	 * @param fromIndex Index of the current student (must be existing)
	 * @param toMatric Matriculation number of the swop partner
	 * @param toIndex Index that the current student wants to swop to
	 * @return True if swop is found and deleted; False if swop not found.
     */
    public static boolean dropSwop (String course, String fromMatric, int fromIndex, String toMatric, int toIndex) {
    	swopManager swopMgr = new swopManager();
    	
    	// Instantiating one queryswop for query purposes
    	PendingSwop querySwop = new PendingSwop();
    	querySwop.setCourseCode(course);
        querySwop.setFromMatric(fromMatric);
        querySwop.setFromIndex(fromIndex);
        querySwop.setToMatric(toMatric);
        querySwop.setToIndex(toIndex);
    	
    	if(swopController.exists(querySwop)) {
            // if exists, delete the swop
            swopMgr.delete(querySwop);
            System.out.println("Swap has been deleted created");
            return true;
        }
        else {
            // Say swop not found
        	System.out.println("Swap not found!");
            return false;
        }
    }
    
    /**
     * Returns any PendingSwop that belongs to the current student.
     * @param matric Matriculation number of the student.
     * @return Pending Swops belonging to the student, as specified by matriculation number.
     */
    public static ArrayList<PendingSwop> getStudentSwopList (String matric) {
    	swopManager swopMgr = new swopManager();
    	PendingSwop querySwop = new PendingSwop();
    	querySwop.setFromMatric(matric);
    	return swopMgr.findMatric(querySwop);
    }
    

    /**
     * Check if the exact pending swop exists.
     * @param query A PendingSwop object containing courseCode, fromMatric, fromIndex, toMatric & toIndex. This was entered by the student.
     * @return True if exact match is found; False otherwise.
     */
    public static boolean exists (PendingSwop query) {
    	swopManager swopMgr = new swopManager();
    	PendingSwop foundSwop = swopMgr.find(query);

        if (foundSwop != null)
            return true;
        else
            return false;
    }
    
    /**
     * Checks if the current user already has a similar swop (registered course code & index matches)
     * @param query A PendingSwop object containing courseCode, fromMatric, fromIndex, toMatric & toIndex. This was entered by the student.
     * @return True if similar match is found; False otherwise.
     */
    public static boolean isSimilar (PendingSwop query) {
    	swopManager swopMgr = new swopManager();
        PendingSwop foundSwop = swopMgr.isSimilar(query);

        if (foundSwop != null)
            return true;
        else
            return false;
    }

    /**
     * Checks if the there is a pending swop which belongs to the swop partner. If found, we can perform the swop.
     * @param query A PendingSwop object containing courseCode, fromMatric, fromIndex, toMatric & toIndex. This was entered by the student.
     * @return True if a swop match is found; False otherwise.
     */
    public static boolean isComplement (PendingSwop query) {
    	swopManager swopMgr = new swopManager();
        
    	PendingSwop complement = new PendingSwop();
        complement.setCourseCode(query.getCourseCode());
        complement.setFromMatric(query.getToMatric());
        complement.setFromIndex(query.getToIndex());
        complement.setToMatric(query.getFromMatric());
        complement.setToIndex(query.getFromIndex());

        PendingSwop foundSwop = swopMgr.find(complement);

        if (foundSwop != null)
            return true;
        else
            return false;
    }
    
    /**
     * Check if another student already has created a pending swop with the current student as the swop partner. Used to prompt the current student to check partners's particulars
     * @param query A PendingSwop object containing courseCode, fromMatric, fromIndex, toMatric & toIndex. This was entered by the student.
     * @return True if student has a swop partner but pending swop does not match; False otherwise.
     */
    public static boolean isDestination (PendingSwop query) {
    	swopManager swopMgr = new swopManager();
    	PendingSwop destination = new PendingSwop();
    	destination.setToMatric(query.getFromMatric());
    	destination.setToIndex(query.getFromIndex());
    	
        PendingSwop foundSwop = swopMgr.findDestination(destination);

        if (foundSwop != null)
            return true;
        else
            return false;
    }
    
    /**
     * Checks if both students have their respective indexes. Otherwise, the pending swop would not be valid.
	 * @param fromMatric Matriculation number of current student.
	 * @param fromIndex Index of the current student (must be existing)
	 * @param toMatric Matriculation number of the swop partner
	 * @param toIndex Index that the current student wants to swop to
     * @return True if both students have their respective indexes; False otherwise.
     */
    public static boolean bothStudentsHaveIndex (Student fromStudent, int fromIndex, Student toStudent, int toIndex) {
    	Boolean fromStudentHasFromIndex = false;
        for (Index value : fromStudent.getIndexList()) {
        	if(value.getIndex() == fromIndex) {
        		fromStudentHasFromIndex = true;
        		break;
        	}
        }
        if (!fromStudentHasFromIndex) {
        	System.out.println("You do not have that index. Please try again.");
        	return false;
        }
        
        // Check if the destination (toStudent) has the toIndex
        Boolean toStudentHasToIndex = false;
        for (Index value : toStudent.getIndexList()) {
        	if(value.getIndex() == toIndex) {
        		toStudentHasToIndex = true;
        		break;
        	}
        }
        if (!toStudentHasToIndex) {
        	System.out.println("Swap partner does not belong to that index. Please try again.");
        	return false;
        }
        
        return true;
    }
}

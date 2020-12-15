package entities;
import java.util.*;
import java.io.Serializable;

/**
 * Represents a course of study a student can register into for a school term to earn credits that count towards their degree fulfillment. <br>
 * Course Key: courseCode
 */
public class Course implements EntityObject, Serializable{
	/**
	 * Identifies a unique course.
	 */
	private String courseCode;
	/**
	 * List of index objects under a certain course.
	 */
	private ArrayList<Index> indexList = new ArrayList<Index>();
	/**
	 * Number of AUs given for completing the course.
	 */
	private int numAus;
	/**
	 * School the course is administered under.
	 */
	private String school;

	/**
	 * Empty constructor for dummy course objects.
	 */
	public Course() {};

	/**
	 *
	 * @param courseC Unique course code.
	 * @param indexes List of index objects under the course.
	 * @param au Number of AUs.
	 * @param sch School the course is administered under.
	 */
	public Course(String courseC, ArrayList<Index> indexes, int au, String sch){
		courseCode = courseC;
		indexList = indexes;
		courseCode = courseC;
		numAus = au;
		school = sch;
	}

	/**
	 * Adds a new index to this course instance.
	 * @param index Index object to be added under this course instance.
	 */
	public void addIndex(Index index){
		indexList.add(index);
	}

	/**
	 * Removes an index from this course instance.
	 * @param index Index object to be removed from this course instance.
	 */
	public void removeIndex(Index index){
		for (Index i:indexList) {
			if (i.getIndex()==index.getIndex()) {
				indexList.remove(i);
				break;
			}
		}
	}

	//get and set methods
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public int getNumAus() {
		return numAus;
	}
	public void setNumAus(int numAus) {
		this.numAus = numAus;
	}
	public ArrayList<Index> getIndexList() {
		return indexList;
	}
	public String getSchool() {
		return school;
	
	}

	public void setIndexList(ArrayList<Index> indexList) {
		this.indexList = indexList;
	}

	public void setSchool(String school) {
		this.school = school;
	}
}

package entities;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.io.Serializable;

/**
 * Represents an index under a course a student can register under. <br>
 * Each index has a unique combination of class timings.
 * Index Key: index
 */
public class Index implements EntityObject, Serializable{
	/**
	 * Course which the index belongs to.
	 */
	private String courseCode;
	/**
	 * Unique index identifier string.
	 */
	private int index;
	/**
	 * Total number of students the index can accept.
	 */
	private int size;
	/**
	 * Additional number of students the index can accept, given the current total number of students registered under the index instance.
	 */
	private int vacancies;
	/**
	 * List of all student objects registered under an index instance.
	 */
	private ArrayList<Student> studentList = new ArrayList<Student>();
	/**
	 * List of all student objects placed in the waitlist of an index instance.
	 */
	private ArrayList <Student> waitList = new ArrayList<>();
	/**
	 * List of lesson objects of this index instance.
	 */
	private ArrayList<Lesson> lessonList = new ArrayList<Lesson>();

	/**
	 * Empty Index constructor for dummy Index objects.
	 */
	public Index() {}

	/**
	 *
	 * @param courseC Course code of the course which the index belongs to.
	 * @param i Unique index identifier string.
	 * @param s Total number of students the index can accept.
	 * @param lessons List of lessons of the new Index.
	 */
	public Index(String courseC, int i, int s, ArrayList<Lesson> lessons) {
		courseCode = courseC;
		index = i;
		size = s;
		vacancies = s;
		lessonList = lessons;
	}

	//set functions
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void setSize(int size) {
		this.size = size;
		this.vacancies = size - studentList.size();
	}
	public void setVacancies(int vacancies) {
		this.vacancies = vacancies;
		this.size = this.vacancies + studentList.size();
	}
	public void setStudentList(ArrayList<Student> studentList) { this.studentList = studentList; }

	// get functions
	public String getCourseCode() {
		return courseCode;
	}
	public int getIndex() { return index; }
	public int getSize() {
		return size;
	}
	public int getVacancies() {
		return vacancies;
	}
	public ArrayList<Student> getStudentList() { return studentList; }
	public ArrayList<Lesson> getLessonList() { return lessonList; }
	public ArrayList<Student> getWaitList() { return waitList; }

	public void addVacancies() {
		if (this.vacancies < this.size) {
			this.vacancies += 1;}
	}
	public void deleteVacancies() {
		if (this.vacancies > 0) {
			this.vacancies -= 1;
		}
	}
	public void addStudent(Student studToAdd){
		studentList.add(studToAdd);
		deleteVacancies();
	}
	
	public boolean removeStudent(String matric) {
		for(int i = 0; i<studentList.size(); i++) {
			if (studentList.get(i).getMatricNum().equals(matric)) {
				studentList.remove(studentList.get(i));
				addVacancies();
				return true;
			}
		}
		return false;
	}

	public void addStudentToWaitlist(Student studToAdd) {
		
		waitList.add(studToAdd); //do not need to add actual student object, just a dummy object instance. only matters when u register and deregister
		//studToAdd.getTimetable().placeWaitlistIndex(newIndex);
	}
	public Student removeStudentFromWaitlist() {
				Student student = waitList.remove(0); //removes first person in the queue
				return student;
	}
	
	public boolean checkWaitlist() { //returns if waitlist is empty or not
		if (waitList.size()>0)
			return true;
		else
			return false;
	}
}


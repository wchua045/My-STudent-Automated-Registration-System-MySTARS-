package controllers.manager;
import entities.*;

import java.util.ArrayList;

/**
 * Manager that manages Index entities.
 */
public class indexManager implements RegistrationManager{
	/**
	 * Temporary index object containing course code of the Course which give all Index objects this instance of indexManager manages.
	 */
    Index tempIndex = new Index();

	/**
	 * Constructs instance of indexManager.
	 */
	public indexManager() {}

	/**
	 * Finds real instance of Index.
	 * @param index dummy Index with instantiated key (i.e. username).
	 * @return real Index instance.
	 */
    public Index find(EntityObject index) {
    	courseManager cmanager = new courseManager();
        studentManager smanager = new studentManager();
		try {
			Course course = new Course();
			course.setCourseCode(((Index) index).getCourseCode());
			course = cmanager.find(course);
			for (Index value : course.getIndexList()) {
				if (value.getIndex() == ((Index) index).getIndex()) {
					return value;
				}
			}
			System.out.println("No Index of Number " + ((Index)index).getIndex() + " Found.");
			return null;
		} catch (NullPointerException e){
			return null;
		}
    }

	/**
	 * Creates real Index object instance.
	 * @param index Index object with all fields initialized.
	 * @return whether object is successfully created.
	 */
    public boolean create(EntityObject index) {
    	courseManager cmanager = new courseManager();
        
    	Course course = new Course();
    	course.setCourseCode(((Index)index).getCourseCode());
    	Course real_course = cmanager.find(course);
    	for ( Index curr_index : real_course.getIndexList()){
    		if (curr_index.getIndex() == ((Index) index).getIndex())
    			return false;
		}
        real_course.addIndex((Index)index);
        cmanager.fileWrite();
        return true;
    }

	/**
	 * Deletes real Index object.
	 * @param index dummy Index with instantiated key (i.e. username).
	 */
    public void delete(EntityObject index) {
    	courseManager cmanager = new courseManager();
        
        Course course = new Course();
        course.setCourseCode(((Index)index).getCourseCode());
        for(Index value : course.getIndexList()) {
            if ((value.getIndex() == ((Index)index).getIndex())) {
                cmanager.find(course).removeIndex((Index) index);
                cmanager.fileWrite();
            }
        }
    }

	/**
	 * Reads "Course.ser" file to retrieve and place stored Index objects into courseList in courseManager. <br>
	 * Contains some dummy data to initialize the program.
	 */
    public void fileRead() {
    	courseManager cm = new courseManager();
    	cm.fileRead();
	}

	/**
	 * Writes all Index objects in courseList into "Course.ser" file.
	 */
	public void fileWrite() {
		courseManager cm = new courseManager();
		cm.fileWrite();
	}


	/**
	 * Prints all fields of each Index object of Course code of tempIndex under courseList.
	 */
	public void printAll() {
    	String cc = tempIndex.getCourseCode();
    	courseManager cm = new courseManager();
    	Course temp_course = new Course();
    	temp_course.setCourseCode(cc);
        for(Index index: (cm.find(temp_course)).getIndexList()){
            System.out.print(index.getIndex());
        }
    }

    /**
     * Prints out list of Students based on selected index.
     * @param index Index object to print all students registered under.
     */
    public void printByIndex(Index index) {
    	System.out.format("|%-30s|%-7s|%-14s|%-20s|\n", "Name", "Gender", "Nationality", "Programme");
    	System.out.println("+--------------------------------------------------------------------------+");
        for(Student student: index.getStudentList()){
        	System.out.format("|%-30s|%-7s|%-14s|%-20s|\n", student.getName(), student.getGender(), student.getNationality(), 
        			student.getProgramme());
        }
        System.out.println("+--------------------------------------------------------------------------+");
    }

	/**
	 * Prints out list of Students based on selected Course.
	 * @param course Course object to print all students registered under.
	 */
    public void printByCourse(Course course) {
    	System.out.format("|%-30s|%-7s|%-14s|%-20s|%-6s|\n", "Name", "Gender", "Nationality", "Programme", "Index");
    	System.out.println("+---------------------------------------------------------------------------------+");
		for (Index index: course.getIndexList()) {
			for (Student student: index.getStudentList())
				System.out.format("|%-30s|%-7s|%-14s|%-20s|%-6d|\n", student.getName(), student.getGender(), student.getNationality(), 
	        			student.getProgramme(), index.getIndex());
        }
        System.out.println("+---------------------------------------------------------------------------------+");
    }

	/**
	 * Registers a student under a real Index object.
	 * @param index index with index and Course code fields instantiated.
	 * @param matric matricNum of real Student to be registered under an index.
	 * @return whether regristration is successful.
	 */
	public boolean register(EntityObject index, String matric) {
    	courseManager cmanager = new courseManager();
        studentManager smanager = new studentManager();
        
    	Student student = new Student();
    	student.setMatricNum(matric);
		student = smanager.findByMatric(student);
		
		Course course = new Course();
		course.setCourseCode(((Index)index).getCourseCode());
		course = cmanager.find(course);
		if (course == null) {
			System.out.println("Course does not exist!");
			return false;
		}
		ArrayList<Index> indexList = new ArrayList<Index>();
		indexList = course.getIndexList();
		
		for (Index val : indexList) {
			// search for the desired index
			if (val.getIndex() == ((Index)index).getIndex()) {
				// check vacancy
				if(val.getVacancies() <= 0) {
					// Adding student to waitlist
					val.addStudentToWaitlist(student); //if none, add to waitlist
					student.getTimetable().placeWaitlistIndex(val);
					student.setTotalAu(student.getTotalAu()+course.getNumAus()); // updating total theoretical AUs
		    		System.out.println("There are currently no vacancies for this index. You will be added to the waitlist of " + ((Index)index).getIndex());
		    		cmanager.fileWrite();
		    		smanager.fileWrite();
		    		return false;
		    		}
		    	else {
		    		val.addStudent(student);
		    		student.getTimetable().placeIndex(val);
		    		cmanager.fileWrite();
		    		smanager.fileWrite();
		    		return true;
		    	}
			}
		}
		System.out.println("Index not found!");
		return false;
    }

	/**
	 * Deregisters a student from a real Index object.
	 * @param index index with index and Course code fields instantiated.
	 * @param matric matricNum of real Student to be deregistered from an index.
	 * @return whether or note deregistration is successful.
	 */
    public boolean deregister(EntityObject index, String matric) {
    	courseManager cmanager = new courseManager();
        studentManager smanager = new studentManager();
        
    	Student s = new Student();
		s.setMatricNum(matric);
		s = smanager.findByMatric(s);
		
		Course course = new Course();
		course.setCourseCode(((Index)index).getCourseCode());
		course = cmanager.find(course); //find the course object containing this index
		ArrayList<Index> indexList = new ArrayList<Index>();
		indexList = course.getIndexList();
		
		for (Index val : indexList) {
			if (val.getIndex() == ((Index)index).getIndex()) {
				val.removeStudent(matric);
				cmanager.fileWrite();
				return true;
			}
		}
		System.out.println("Index not found.");
    	return false;
    }

	/**
	 * Updates studentList of Index object based on executed swop.
	 * @param swop Successful pendingSwop object with all fields initialised.
	 * @return whether swop is successful.
	 */
	public Boolean swopIndex (PendingSwop swop) {
		courseManager cmanager = new courseManager();
		studentManager smanager = new studentManager();

		Course courseQuery = new Course();
		courseQuery.setCourseCode(swop.getCourseCode());
		courseQuery = cmanager.find(courseQuery);


		// Initialise student obj
		Student owner = new Student();
		owner.setMatricNum(swop.getFromMatric());
		owner = smanager.findByMatric(owner);

		Student dest = new Student();
		dest.setMatricNum(swop.getToMatric());
		dest = smanager.findByMatric(dest);

		// Search thru indexList of the desired course and update accordingly
		for (Index val : courseQuery.getIndexList()) {
			if (val.getIndex() == swop.getToIndex()) {
				// add to destination index
				val.addStudent(owner);
				val.removeStudent(swop.getToMatric());
			}
			if (val.getIndex() == swop.getFromIndex()) {
				// remove from original index
				val.addStudent(dest);
				val.removeStudent(swop.getFromMatric());
			}
		}

		cmanager.fileWrite();
		return true;

	}

}

package controllers;
import java.util.ArrayList;
import controllers.manager.*;
import entities.*;

/**
 * 
 * Represents a controller which will be used by a student. It contains
 * functions which a student can do on MySTARS.
 * 
 * @author
 *
 */
public class studentController {
	BasicManager courseMgr;
	RegistrationManager studentMgr, indexMgr;
	private final int AULIMIT = 11; // for testing
	
	// 1. Check Courses Registered
	/**
	 * Find Courses & Indexes currently registered for the specified
	 * @param matric 
	 * Matriculation number of the student
	 * @return An ArrayList containing the course code and the index. 
	 * The index is stored in Pair.L and course code is stored in Pair.RR.
	 */
	public ArrayList<Pair> checkCoursesRegistered(String matric) { //print courses and index registered
		studentMgr = new studentManager();
		
		Student student = new Student();
		student.setMatricNum(matric);
		student = ((studentManager)studentMgr).findByMatric(student);
		
		ArrayList<Index> indexList = new ArrayList<Index>();
		indexList = student.getIndexList();
		ArrayList<Pair> pairList = new ArrayList<Pair>();
		if (indexList.size()!=0) {
			for (int i = 0; i < indexList.size(); i++) {
				Pair pair = new Pair();
				pair.setL(indexList.get(i).getIndex());
				pair.setRR(indexList.get(i).getCourseCode());
				pairList.add(pair);
			}
		}
		return pairList;
	}
	/**
	 * Gets the current total AUs for the student's registered + waitlisted courses. Helps students in course planning.
	 * @param matric 
	 * Matriculation number of the student
	 * @return Total AU for the specified student.
	 */
	public int getNumAus(String matric) {
		studentMgr = new studentManager();
		
		Student student = new Student();
		student.setMatricNum(matric);
		student = ((studentManager)studentMgr).findByMatric(student);
		if (student == null)
			return -1;
		
		return student.getTotalAu();
	}
	/**
	 * Finds Courses and Indexes in which the student is found to be on the waitlist
	 * @param matric 
	 * Matriculation number of the student
	 * @return An ArrayList containing the course code and the index. 
	 * The index is stored in Pair.L and course code is stored in Pair.RR.
	 */
	public ArrayList<Pair> checkCoursesWaitlisted(String matric) { // print courses and index currently on waitlist
		courseMgr = new courseManager();

		return ((courseManager)courseMgr).getWaitlistOfStudent(matric);	
	}
	
	// 2. Add a Course
	/**
	 * 
	 * @param courseCode
	 * course code that student wants to register for
	 * @param index
	 * index of course code that student wants to register for
	 * @param matric 
	 * Matriculation number of the student
	 * @return true or false
	 */
	public boolean registerCourse(String courseCode, int index, String matric) {
//		dm1 = new studentManager();
//		dm2 = new indexManager();
//		dm3 = new courseManager();
		
		studentMgr = new studentManager();
		indexMgr = new indexManager();
		courseMgr = new courseManager();
		
		boolean outputIndex = false, outputStudent = false, indexExists = false;
		
		Student student = new Student(); 
		student.setMatricNum(matric);
		student = ((studentManager)studentMgr).findByMatric(student);
		if(student == null) {
			System.out.println("Invalid login state. Please log in again");
			loginController.logout();
			return false;
		}
		
		Course course = new Course();
		course.setCourseCode(courseCode);
		course = ((courseManager)courseMgr).find(course);
		if(course == null)
			return false;

		ArrayList<Index> indexList = new ArrayList<Index>();
		indexList = course.getIndexList();
		if(indexList.size() == 0) {
			System.out.println(courseCode+" has no indexes yet. Please try again.");
			return false;
		}
		
		// Check if student is already registered in the course
		for(Index val : student.getIndexList()) {
			if(val.getCourseCode().equals(courseCode)) {
				System.out.println("You are already registered in this course. If you wish to change index, please go to [6. Change index of existing registered index].");
				return false;
				}
		}
		// Check if student is in the waitlist for the requested index
		for(int i =0; i< indexList.size(); i++ ) {
			if(indexList.get(i).getIndex() == index){
				indexExists = true; // index found
				// Check if in waitlist first
				for (Student s : indexList.get(i).getWaitList()) {
					if(s.getMatricNum().equals(student.getMatricNum())) {
						System.out.println("You are already in the waitlist of this index.");
						return false;
					}
				}
		// Check if student is in the waitlist for the another index in the same course
			for(int j =0; j< indexList.size(); j++ ) {
				ArrayList<Student> waitList = new ArrayList<Student>();
				waitList = indexList.get(j).getWaitList();
				for(int x = 0; x<waitList.size();x++){
					if(waitList.get(x).getMatricNum().equals(matric)){
							System.out.println("You are already in the waitlist of index " +indexList.get(j).getIndex() +" in this course.");
							System.out.println("Please go to [9. Drop waitlisted courses] to drop your waitlisted index if you wish to register to this index");
							return false;
						}
					}
			}
				
				TimeTable timetable = student.getTimetable();
				// check if student is going to exceed AU limit
				if (student.getTotalAu() + course.getNumAus() > AULIMIT) {
					System.out.println("You will exceed your total AUs. Please choose another course");
					return false;
				}
				else if(timetable.checkClash(indexList.get(i))) {
					System.out.println("There is a clash! You will not be registered to this course.");
					return false;
				}
				else {
					outputIndex = ((indexManager)indexMgr).register(indexList.get(i), matric); //add student to studentList in Index objecT
					if (outputIndex == false) // means students was added to waitlist
						break;
					outputStudent = ((studentManager)studentMgr).register(indexList.get(i), matric);  //add index to indexList in Student object
					break;
			    }
			}
		}
		
		// Check if index exists
		if (!indexExists) {
			// Does not exist
			System.out.println("Index "+index+" is not found! Try again.");
			return false;
		}

		if (outputStudent & outputIndex) {
			studentMgr.fileWrite(); // success
			return true;
		}
		else
			return false;
	}
	
	// 3. Drop a Course
	/**
	 * 
	 * @param courseCode
	 * course code that student wants to deregister from
	 * @param index
	 * index of course code that student wants to deregister from
	 * @param matric
	 * Matriculation number of the student
	 * @return true or false
	 */
	public boolean deregisterCourse(String courseCode, int index, String matric) {
//			dm1 = new studentManager();
//			dm2 = new indexManager();
//			dm3 = new courseManager();
//			rd1 = new studentManager();
//			rd2 = new indexManager();
		studentMgr = new studentManager();
		courseMgr = new courseManager();
		indexMgr = new indexManager();
		
		boolean outputIndex = false, outputStudent = false;
		//add check
		Student student = new Student(); 
		student.setMatricNum(matric);
		student = ((studentManager)studentMgr).findByMatric(student);  //find the student object containing this index
		if(student == null) {
			System.out.println("Invalid login state. Please log in again");
			loginController.logout();
			return false;
		}
		
		Course course = new Course();
		course.setCourseCode(courseCode);
		course = ((courseManager)courseMgr).find(course); //find the course object containing this index
		if(course == null)
			return false;
		
		ArrayList<Index> indexList = new ArrayList<Index>();
		indexList = course.getIndexList();
		
		for(int i =0; i< indexList.size(); i++ ) {
			if(indexList.get(i).getIndex() == index){
				//remove student from studentList in Index object
				outputIndex = ((indexManager)indexMgr).deregister(indexList.get(i), matric);
				//check if the index's waiting list has students
				boolean check = indexList.get(i).checkWaitlist();
				// if(check) transferStudentFromWaitlistToRegister(indexList.get(i));
				
				ArrayList<Index> studindexList = new ArrayList<Index>();
				studindexList = student.getIndexList();	
				for(int j =0; j< studindexList.size(); j++ ) {
					if (studindexList.get(j).getIndex() == index) {
					//remove index from indexList in Student object
						outputStudent = ((studentManager)studentMgr).deregister(studindexList.get(j), matric);					
						break;
					}
				} 
				
				//activates transfer of student to waiting list after deregister
				if (outputStudent == true && outputIndex == true && check)
					transferStudentFromWaitlistToRegister(indexList.get(i));
				break;
			}
		}	
		if(outputStudent == false || outputIndex == false) {
			System.out.println("Something went wrong, try again");
			return false;
		}
		else {
    		return true;
		}
			
	}
		
	/**
	 * 
	 * @param queryindex
	 * index of course code that has a student on the waitlist.
	 */
	public void transferStudentFromWaitlistToRegister(Index queryindex) {
//			rd2 = new indexManager();
//			rd1 = new studentManager();
//			studentManager sm = new studentManager();
		
		indexMgr = new indexManager();
		studentMgr = new studentManager();
		courseMgr = new courseManager();
		Student studentInfo;
		notificationController ncontroller = new notificationController();
		boolean outputM = false, outputS = false;
		
		Index index = ((indexManager)indexMgr).find(queryindex);
//		Student studentInfo = ((indexManager)indexMgr).getStudentFromWaitlist(index);
		
		// Find the relevant index and remove from the waitlist
		Course course = new Course();
		course.setCourseCode(queryindex.getCourseCode());
		course = ((courseManager)courseMgr).find(course); //find the course object containing this index
		if(course == null)
			return;
		
		for(Index idx : course.getIndexList()) {
			if(idx.getIndex() == queryindex.getIndex()) {
				studentInfo = idx.removeStudentFromWaitlist();
				courseMgr.fileWrite();
				
				// HANDLING special case: need to deregister an index from the same course in the event that the student has this course in both their registered indexlist and their waitlist.
				studentMgr.fileRead();
				Student realStudent = ((studentManager)studentMgr).findByMatric(studentInfo);
				Index indexToDereg = null;
				for (Index value : realStudent.getIndexList()) {
					// check if he has a course/ index matching this waitlisted course
					if (value.getCourseCode().equals(index.getCourseCode())) {
						// found same course already registered
						indexToDereg = value;
					}
				}
				// need to deregister him from the old index
				if(indexToDereg != null) {
					((indexManager)indexMgr).deregister(indexToDereg, realStudent.getMatricNum());
					((studentManager)studentMgr).deregister(indexToDereg, realStudent.getMatricNum());
				}
				outputM = ((indexManager)indexMgr).register(index, realStudent.getMatricNum());
			    outputS = ((studentManager)studentMgr).register(index, realStudent.getMatricNum());
			    
			    
			    if(outputM== true && outputS==true) {
		    		ncontroller.notifyByEmail(studentInfo.getEmail(), index.getCourseCode(), index.getIndex()); //send email notification
		    		studentMgr.fileWrite();
		    		//cmanager.fileWrite();
		    	}
			    return;
			}
		}
    	
	}
	
	// 5. Check vacancies available for an index
	/**
	 * 
	 * @param courseCode
	 * course code that user wants to check vacancies for
	 * @param index
	 * index of course code that user wants to check vacancies for
	 * @return single Pair object containing the course code and the index
	 */
	 public Pair indexVacancy(String courseCode, int index) {
//			dm3 = new courseManager();
		 courseMgr = new courseManager();
		 notificationController ncontroller = new notificationController();
			
		 Course course = new Course();
		 course.setCourseCode(courseCode);
		 course = ((courseManager)courseMgr).find(course);
		 if(course == null) 
			return null;
		 else {
		 ArrayList<Index> indexList = new ArrayList<Index>();
		 indexList = course.getIndexList();
		 Pair pair = new Pair();
		 boolean check = false;
		 for (int i = 0; i < indexList.size(); i++) {
			if(indexList.get(i).getIndex() == index) {
				pair.setL(indexList.get(i).getIndex());
				String vacanciesPlusSize = Integer.toString(indexList.get(i).getVacancies()) + "/" + Integer.toString(indexList.get(i).getSize());
				pair.setRR(vacanciesPlusSize);
				check = true;
				}
		 	}
		 if(check == false) 
			 return null;
		 else 
			 return pair;
		 }
	 }
	
	 // 4. Check vacancies available for all indexes in a course 
	 /**
	  * 
	  * @param courseCode
	  * course code that user wants to check vacancies for
	  * @return An ArrayList containing the course code and the index. 
	  * The index is stored in Pair.L and course code is stored in Pair.RR.
	  */
	 public ArrayList<Pair> vacancies(String courseCode) {  //remember a ArrayList<Pair> have to be created in studentBoundary to receive this
//		dm3 = new courseManager();
		courseMgr = new courseManager();
		notificationController ncontroller = new notificationController();
			
		Course course = new Course();
		course.setCourseCode(courseCode);
		course = ((courseManager)courseMgr).find(course);
		if(course == null) 
			return null;
		else {
			ArrayList<Index> indexList = new ArrayList<Index>();
			indexList = course.getIndexList();
			ArrayList<Pair> pairList = new ArrayList<Pair>();
			for (int i = 0; i < indexList.size(); i++) {
				Pair pair = new Pair();
				pair.setL(indexList.get(i).getIndex());
				String vacanciesPlusSize = Integer.toString(indexList.get(i).getVacancies()) + "/" + Integer.toString(indexList.get(i).getSize());
				pair.setRR(vacanciesPlusSize);
				pairList.add(pair);
			}
		
			return pairList;
		}
	 }
	 
	// 6. Change index of existing registered index
	 /**
	  * 
	  * @param courseCode
	  * course code that user want to change existing index for
	  * @param toIndex
	  * new index that user wants to register for
	  * @param matric
	  * matric number of student that calls this method
	  * @return true or false
	  */
	 public boolean changeIndex(String courseCode, int toIndex, String matric) {
		studentMgr = new studentManager();
		//dont need fromIndex cause deregisterCourse() looks thru indexList under
		// Student index.getCourseCode().equals(courseCode)
		boolean outputRegister=false, outputDeregister=false;
		int prevIndex; //just in case register fails, and index is alr deregistered, we use this to register student back
		
		Student student = new Student();
		student.setMatricNum(matric);
		student = ((studentManager)studentMgr).findByMatric(student);
		if(student == null) {
			System.out.println("Invalid login state. Please log in again");
			loginController.logout();
			return false;
		}
		
		//change to check if student is registered to this course
		for(Index val : student.getIndexList()) {
			if(val.getIndex() == toIndex) {
				System.out.println("You are already registered to " +toIndex+ " in " + courseCode+ ".");
				return false;
			}
		}
		
		ArrayList<Index> indexList = new ArrayList<Index>();
		indexList = student.getIndexList();
		for(int i=0; i<indexList.size();i++) {
			if(indexList.get(i).getCourseCode().equals(courseCode)) {
				prevIndex = indexList.get(i).getIndex();
				outputDeregister = deregisterCourse(courseCode, indexList.get(i).getIndex(), matric);
				outputRegister = registerCourse(courseCode, toIndex, matric);
				
				if(outputRegister == false) {
					registerCourse(courseCode, prevIndex, matric);
					return false;
				}
				else return true;
			}
		}
		// index from courseCode is not found in student's indexList
	return false;		
		
	}

	// 8. Print timetable
	 /**
	  * 
	  * @param matric
	  * matric number of student that called this method
	  */
	public void printTimeTable(String matric) {
		studentMgr = new studentManager();
		Student student = new Student();
		student.setMatricNum(matric);
		((studentManager)studentMgr).findByMatric(student).getTimetable().printTimeTable();
	}
	
	// 9. Drop waitlisted courses
	/**
	 * 
	 * @param courseCode
	 * course code that user wants to drop from waitlist
	 * @param index
	 * index of course that user want to drop from waitlist
	 * @param matric
	 * matric number of student that wants to drop the course
	 * @return true or false
	 */
	public boolean dropFromWaitlist(String courseCode, int index, String matric) {
		studentMgr = new studentManager();
		courseMgr = new courseManager();
		indexMgr = new indexManager();
		
		Student student = new Student();
		student.setMatricNum(matric);
		student = ((studentManager)studentMgr).findByMatric(student);
		
		Course course = new Course();
		course.setCourseCode(courseCode);
		course = ((courseManager)courseMgr).find(course); //find the course object containing this index
		if (course == null) {
			return false;
		}
		
		ArrayList<Index> indexList = new ArrayList<Index>();
		indexList = course.getIndexList();
		
		Index windex = new Index();
		for(int i =0; i< indexList.size(); i++ ) {
			if(indexList.get(i).getIndex() == index){
				//get index that student wants to drop
				windex = indexList.get(i); 
				
				for(Student s : indexList.get(i).getWaitList()) {
					if(s.getMatricNum().equals(matric)) {
						// found student
						indexList.get(i).getWaitList().remove(s);
						student.getTimetable().clearIndex(windex);
						student.setTotalAu(student.getTotalAu() - course.getNumAus());
						studentMgr.fileWrite();
						courseMgr.fileWrite();
						System.out.println("Drop from waitlist successful.");
						return true;
					}
				}
			}
		}
		
		System.out.println("Your index is not found in any waitlist. Try again.");
		return false;
	}
				
				//get index that student wants to drop
//				Index windex = indexList.get(i); 
				//get waitlist.
//				ArrayList <Student> waitList = new ArrayList<>();
//				waitList = windex.getWaitList();
//				if (!waitList.contains(student)) {
//					System.out.println("You are not waitlisted for this index in this course");
//					return false;
//				}
//				else { 
//					waitList.remove(student);
//					student.getTimetable().clearIndex(windex);
//					return true; 
//					}
//				}
//			
//			}
//		return false;
//	}

}

	
	
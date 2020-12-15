package controllers.manager;

import entities.*;

import java.util.ArrayList;
import java.io.*;
//import java.nio.file.*;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.FileWriter;
/**
 * This class manages all update, access and modification of data concerning the Student class and its attributes.
 * Student controller uses this to access and modify data of Student objects in response to the input requests taken in by studentBoundary classes
 * @author chua_
 *
 */

public class studentManager implements RegistrationManager{
	/**
	 * This is an array list of Students objects of students that are enrolled with the school.
	 */
	private ArrayList<Student> studentList = new ArrayList<Student>();
	/**
	 * Creates a student manager by allowing it to access and modify the file Student.ser
	 */
	public studentManager() {
		fileRead();
	}
	/**
	 * This function finds a specific student based on the matriculation number details brought in by the dummy Student object
	 * @param student
	 * This is an EntityObject object containing only the matriculation number of the student in question. This is used to identify the Student object that is being requested for.
	 * @return
	 * returns a Student object if found and null if not found
	 */
	public Student findByMatric(EntityObject student) {
		for (Student value : studentList) {
			if (value.getMatricNum().equals(((Student) student).getMatricNum())){
				return value;
			}
		}
		System.out.println("Student of Matriculation Number of " + ((Student)student).getMatricNum() + " not in database.");
		return null;
	}
	/**
	 * This function finds a specific student based on the username brought in by the dummy Student object
	 * @param student
	 * This is an EntityObject object containing only the username of the student in question. This is used to identify the Student object that is being requested for.
	 * @return
	 * returns a Student object if found and null if not found
	 */

	public Student find(EntityObject student) {
		for (Student value : studentList) {
			if (value.getUsername().equals(((Student) student).getUsername())) {
				return value;
			}
		}
		System.out.println("Student of Username " + ((Student)student).getUsername() + " not in database.");
		return null;
	}
	/**
	 * This function creates a new Student object, which details are brought in by the dummy Student object.
	 * @param student
	 * This is an EntityObject object containing only the username of a new student.
	 * @return
	 * returns a boolean value. returns true if creation is successful and false is not successful. 
	 */
    public boolean create(EntityObject student) {
        Student tempStudent = new Student();
        tempStudent.setUsername(((Student)student).getUsername());
        if(find(tempStudent)==null) {
			System.out.println("Creating Student of Username " + ((Student)student).getUsername());
            studentList.add((Student) student);
            fileWrite();
            if(find(((Student)student))!=null) return true;
        }
        return false;
    };
    /**
	 * This function creates a new Student object, which details are brought in by the dummy Student object.
	 * @param student
	 * This is an EntityObject object containing only the username of a new student.
	 * @return
	 * returns a boolean value. returns true if creation is successful and false is not successful. 
	 */
    public void delete(EntityObject student) {
        for(Student value : studentList) {
            if ((value.getMatricNum().equals(((Student)student).getMatricNum())))
                studentList.remove(value);
        }
        fileWrite();
    }
	/**
	 * Reads "Student.ser" file to retrieve and place stored Student objects into studentList. <br>
	 * Contains some dummy data to initialize the program.
	 */
	public void fileRead() {
        try{
            FileInputStream fileIn = new FileInputStream("Student.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
            studentList = (ArrayList<Student>)objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException f) {
            //System.out.println("Student file not found");
            // initialise dummy data
            // f.printStackTrace();
        	Student s = new Student();
        	s = new Student("WCHAN999","pw","Chan Wei Chang",'M',"S","U2042323A",1,"CSC","SCSE","Singaporean","WCHAN999@e.ntu.edu.sg");
        	this.create(s);
        	s = new Student("JIEEL210","swop2","Lim Jie En",'M',"S","U1838484D",3,"CE","SCSE","Singaporean","JIEEL210@e.ntu.edu.sg");
        	this.create(s);
        	s = new Student("SHTAN66","bun","Shawn Tan",'M',"S","U1921316F",2,"REP","SCSE","Singaporean","SHTAN66@e.ntu.edu.sg");
        	this.create(s);
        	s = new Student("DHIDE024","darkestday1200","Daichi Hideyoshi",'M',"S","U1721316F",4,"MAE","MAE","Japanese","DHIDE024@e.ntu.edu.sg");
        	this.create(s);
        	s = new Student("TQYING013","swop","Tan Qi Ying",'F',"S","U1824834F",1,"BCG","SCSE","Singaporean","TQYING013@e.ntu.edu.sg");
        	this.create(s);
        	s = new Student("JYEO11","immaStarBoy","Josephine Yeo",'F',"S","U1324832D",3,"CS","WKWSCI","Singaporean","JYEO11@e.ntu.edu.sg");
        	this.create(s);
        	s = new Student("ZIQI221","hellowtallow","Zi Qi",'F',"S","U1724832D",4,"ECON","SSS","Chinese","ZIQI221@e.ntu.edu.sg");
        	this.create(s);
        	s = new Student("WKAIJ221","ansIS42","Wang Kai Jian",'M',"S","U2024132D",1,"DA","ADM","Chinese","WKAIJ221@e.ntu.edu.sg");
        	this.create(s);
        	s = new Student("YGOH921","burgersandfries","Yvette Goh Qian Wei",'F',"S","U1923122D",2,"ACC","NBS","Malaysian","YGOH921@e.ntu.edu.sg");
        	this.create(s);
        	s = new Student("SAMS001","toogoodatgoodbyes","Sam Bradley Smith",'M',"S","U1819102D",1,"BUS","NBS","English","SAMS001@e.ntu.edu.sg");
        	this.create(s);

        } catch (ClassNotFoundException|IOException i) {
                i.printStackTrace();
        }
    }

	/**
	 * Writes all Student objects in studentList into "Student.ser" file.
	 */
	public void fileWrite() {
		try {
			FileOutputStream fileOut = new FileOutputStream("Student.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(studentList);
			out.close();
			fileOut.close();
			System.out.print("Serialized data is saved in Student.ser\n");
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * Prints some fields of each Student object in studentList.
	 */
	public void printAll() {
		System.out.format("|%-10s|%-14s|%-20s|%-7s|\n", "Username", "Matric Number", "Programme of Study", "School");
    	System.out.println("+------------------------------------------------------+");
		for (Student student : studentList) {
				System.out.format("|%-10s|%-14s|%-20s|%-7s|\n", student.getUsername(), student.getMatricNum(), student.getProgramme(), student.getSchool());
        }
        System.out.println("+------------------------------------------------------+");
	}

	/**
	 * Registers an index under indexList of Student object.
	 * @param index index with index and Course code fields instantiated.
	 * @param matric matricNum of real Student whose indexList should be updated.
	 * @return whether regristration is successful.
	 */
	public boolean register(EntityObject index, String matric) {
		courseManager cmanager = new courseManager();
		Course course = new Course();
		course.setCourseCode(((Index)index).getCourseCode());
		course = cmanager.find(course);
		
		for (Student value : studentList) {
			if (value.getMatricNum().equals(matric)) {
				value.addIndex((Index) index, course.getNumAus());
				return true; //if student with the matric exists
			}
		}
		return false;
	}

	/**
	 * Removes an index under indexList of Student object.
	 * @param index index with index and Course code fields instantiated.
	 * @param matric matricNum of real Student whose indexList should be updated.
	 * @return whether deregristration is successful.
	 */
	public boolean deregister(EntityObject index, String matric) {
		courseManager cmanager = new courseManager();
		Course course = new Course();
		course.setCourseCode(((Index) index).getCourseCode());
		course = cmanager.find(course);

		Student s = new Student();
		s.setMatricNum(matric);
		Student student = findByMatric(s);
		student.removeIndex((Index) index, course.getNumAus());
		fileWrite();
		return true;
	}

	/**
	 * Updates indexList of Student object based on executed swop.
	 * @param swop Successful pendingSwop object with all fields initialised.
	 * @return whether swop is successful.
	 */
	public void swopStudentIndex(PendingSwop swop) {
		Index fromIndex = new Index();
		Index toIndex = new Index();

		courseManager cmanager = new courseManager();
		Course course = new Course();
		course.setCourseCode(swop.getCourseCode());
		course = cmanager.find(course);

		// Search through studentList & remove the old indexes
		for (Student value : studentList) {
			if (value.getMatricNum().equals(swop.getFromMatric())) {
				// And remove the old index
				for (Index idx : value.getIndexList()) {
					if (idx.getIndex() == swop.getFromIndex()) {
						fromIndex = idx;
					}
				}
				value.removeIndex(fromIndex, course.getNumAus());
			} else if (value.getMatricNum().equals(swop.getToMatric())) {
				// And remove the old index
				for (Index idx : value.getIndexList()) {
					if (idx.getIndex() == swop.getToIndex()) {
						toIndex = idx;
					}
				}
				value.removeIndex(toIndex, course.getNumAus());
			}
		}
		
		// Search through studentList & add the new indexes
		for (Student value : studentList) {
			if (value.getMatricNum().equals(swop.getFromMatric())) {
				// Find From and give his new index
				value.addIndex(toIndex, course.getNumAus());
			}
			else if (value.getMatricNum().equals(swop.getToMatric())) {
				// Find To and give his new index
				value.addIndex(fromIndex, course.getNumAus());
			}
		}

		fileWrite();
	}
}

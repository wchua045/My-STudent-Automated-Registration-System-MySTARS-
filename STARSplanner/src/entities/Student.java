package entities;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *This class represents a Student object. It contains all the details that an undergraduate Student must have.
 *<br>
 *Student Key: Username or matricNum 
 * @author chua_
 *
 */
public class Student extends User implements EntityObject, Serializable{
	/**
	 * The email address of this Student used for successful course registration email
	 * notification.
	 */
	public String email;
	/**
	 * The matriculation number of this student used for unique identification of student for registration and deregistration of courses
	 */
	private String matricNum;
	/**
	 * The student year of study in the the programme
	 */
    private int yearOfStudy;
    /**
     * (Course)Programme that the student is currently taking
     */
    private String programme;
    /**
     * School of the programme that student is taking
     */
    private String school;
    /**
     * Nationality of student
     */
    private String nationality;
    /**
     * Array list of index objects for the indexes that the student is registered to.
     */
    private ArrayList<Index> indexList = new ArrayList<Index>();
    /**
     * Timetable of student which contains the date, time and venue of all courses that student registered and waitlist-ed to.
     */
    private TimeTable timetable = new TimeTable(indexList);
    /**
     * Total number of Academic Units(AU) that student has.
     * This corresponds to the sum of AUs of courses that students are registered or waitlist-ed to.
     */
    private int totalAu;
    /**
     * Creates a student
     */
    public Student(){
        super();
    }
//    public Student(String username, String password, String name, char gender, String accountType){
//    	super(username, password, name, gender, accountType);
//    }
    /**
     * Creates a student
     * @param username, password, name, gender, accountType
     * parameters of superclass
     * @param matric, yearOfStudy, programme, school, nationality, email
     * parameters of student class
     */
    public Student(String username, String password, String name, char gender, String accountType, 
    		String matric, int yearOfStudy, String programme, String school, String nationality, String email) {
    	super(username, password, name, gender, accountType);
    	this.matricNum = matric;
    	this.yearOfStudy = yearOfStudy;
    	this.programme = programme;
    	this.school = school;
    	this.nationality = nationality;
    	this.email = email;
    	this.totalAu = 0;
    }
    
//    public Student(String matric, String cc) {
//    	matricNum = matric;
//    	school = cc;
//    }
    
    public int getTotalAu() {
		return totalAu;
	}
    
	public void setTotalAu(int totalAu) {
		this.totalAu = totalAu;
	}
	
    public String getEmail() {
    	return email;
    }

	public void setEmail(String email) {
    	this.email = email;
    }

    public String getMatricNum() {
        return matricNum;
    }
    public void setMatricNum(String matricNum) {
        this.matricNum = matricNum;
    }
    
    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }
    
	public String getSchool() {
		return school;
	}
	
	public void setSchool(String school) {
		this.school = school;
	}	
	
    
    public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	/**
	 * This adds the index object of the course index that student is registered to the indexList attributes.
	 * @param index
	 * index object that student is registered to
	 * @param au
	 * number of AUs of the course that the index belongs to. Used to update the totalAu attribute.
	 */
	public void addIndex(Index index, int au) {
    	indexList.add(index);
        timetable.placeIndex(index);
        totalAu += au;
    }
	/**
	 * This removes the index object of the course index that student is registered to the indexList attributes.
	 * @param index
	 * 
	 * @param au
	 */
    public void removeIndex(Index index, int au) {
    	indexList.remove(index);
        timetable.clearIndex(index);
        totalAu -= au;
    }
    public ArrayList<Index> getIndexList() {
    	return indexList;
	}

	public TimeTable getTimetable() {
		return timetable;
	}
}

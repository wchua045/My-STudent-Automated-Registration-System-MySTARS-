package controllers.manager;

import java.io.*;
import entities.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Manager that manages Course entities.
 */
public class courseManager implements BasicManager{
    /**
     * List of all valid Course entities in the database.
     */
    private ArrayList<Course> courseList = new ArrayList<Course>();

    /**
     * Constructs a manager with a list of all valid Course entities in the database.
     */
    public courseManager() {
        fileRead();
    }

    /**
     * Finds real instance of Course.
     * @param course dummy Course with instantiated key (i.e. username).
     * @return real Course instance.
     */
    public Course find(EntityObject course){
        for (Course value : courseList) {
            if (value.getCourseCode().equals(((Course)course).getCourseCode())) {
            	return value;
            }
        }
        System.out.println("No Course of Course Code  " + ((Course)course).getCourseCode() + " Found.");
        return null;
    };

    /**
     * Creates real Course object instance.
     * @param course Course object with all fields initialized.
     * @return whether object is successfully created.
     */
    public boolean create(EntityObject course) {
        Course tempCourse = new Course();
        tempCourse.setCourseCode(((Course)course).getCourseCode());
        if(find(tempCourse)==null) {
            System.out.println("Creating Course of Course Code  " + ((Course)course).getCourseCode());
            courseList.add((Course) course);
            fileWrite();
            if(find(course)!=null) return true;
        }
        return false;
    };

    /**
     * Deletes real Course object.
     * @param course dummy Course with instantiated key (i.e. username).
     */
    public void delete(EntityObject course) {
        for(int i=0;i<courseList.size();i++) {
            if (courseList.get(i).getCourseCode().equals(((Course)course).getCourseCode())) {
                courseList.remove(i);
            } 
        }
        fileWrite();
    };

    /**
     * Removes index object from its real Course instance.
     * @param course dummy Course object with course code instantiated.
     * @param index index object to be removed from real Course instance.
     */
    public void removeIndex(Course course, Index index){
        find(course).removeIndex(index);
        fileWrite();
    }

    /**
     * Adds index object to a real Course instance.
     * @param course dummy Course object with course code instantiated.
     * @param index index object to be added to the real Course instance.
     */
    public void addIndex(Course course, Index index){
        find(course).addIndex(index);
        fileWrite();
    }

    /**
     * Retrieves a list of Pairs of indexes and the course codes of their corresponding course which a student is waitlisted under.
     * @param matric matricNum of Student object.
     * @return list of Pairs of indexes and the course codes
     */
    public ArrayList<Pair> getWaitlistOfStudent (String matric) {
    	ArrayList<Pair> resultPairlist = new ArrayList<Pair>();
    	Pair p;
    	
    	for(Course c : courseList) {
    		for(Index idx : c.getIndexList()) {
    			for(Student s : idx.getWaitList()) {
    				if (s.getMatricNum().equals(matric)) {
    					p = new Pair(idx.getIndex(), idx.getCourseCode());
    					resultPairlist.add(p);
    				}
    			}
    		}
    	}
    	return resultPairlist;
    }

    /**
     * Reads "Course.ser" file to retrieve and place stored Course objects into courseList. <br>
     * Contains some dummy data to initialize the program.
     */
    public void fileRead() {
        try{
            FileInputStream fileIn = new FileInputStream("Course.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            courseList = (ArrayList<Course>)objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException f) {
            //System.out.println("Course file not found");
            // Initialize dummy data (courses - CZ2002, CZ2003, CZ2004, CZ2005)
        	Lesson lessonA01 = new Lesson("LECTURE", 4, "BOTH", 2, 1, "LT1A", "G2");
            Lesson lessonA02 = new Lesson("TUTORIAL", 4, "BOTH", 7, 1, "TR18", "SS2");
            Lesson lessonA03 = new Lesson("LAB", 2, "ODD", 1, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10201 = new ArrayList<Lesson>(Arrays.asList(lessonA01, lessonA02, lessonA03));
            Index indexE1 = new Index("CZ2001", 10201, 30, Lesson_10201);
            ArrayList<Index> Index_2001 = new ArrayList<Index>(Arrays.asList(indexE1));
            Course courseE = new Course("CZ2001", Index_2001, 3, "SCSE");
        	
            Lesson lessonA11 = new Lesson("LECTURE", 1, "BOTH", 2, 1, "LT2A", "G1");
            Lesson lessonA12 = new Lesson("TUTORIAL", 3, "BOTH", 7, 1, "TR18", "SS1");
            Lesson lessonA13 = new Lesson("LAB", 2, "ODD", 1, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10205 = new ArrayList<Lesson>(Arrays.asList(lessonA11, lessonA12, lessonA13));
            Index indexA1 = new Index("CZ2002", 10205, 30, Lesson_10205);

            Lesson lessonA21 = new Lesson("LECTURE", 1, "BOTH", 2, 1, "LT2A", "G1");
            Lesson lessonA22 = new Lesson("TUTORIAL", 2, "BOTH", 4, 1, "TR17", "SS2");
            Lesson lessonA23 = new Lesson("LAB", 4, "EVEN", 5, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10206 = new ArrayList<Lesson>(Arrays.asList(lessonA21, lessonA22, lessonA23));
            Index indexA2 = new Index("CZ2002", 10206, 30, Lesson_10206);

            Lesson lessonA31 = new Lesson("LECTURE", 1, "BOTH", 2, 1, "LT2A", "G1");
            Lesson lessonA32 = new Lesson("TUTORIAL", 2, "BOTH", 3, 1, "TR15", "SS3");
            Lesson lessonA33 = new Lesson("LAB", 4, "ODD", 3, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10207 = new ArrayList<Lesson>(Arrays.asList(lessonA31, lessonA32, lessonA33));
            Index indexA3 = new Index("CZ2002", 10207, 30, Lesson_10207);
            ArrayList<Index> Index_2002 = new ArrayList<Index>(Arrays.asList(indexA1, indexA2, indexA3));
            Course courseA = new Course("CZ2002", Index_2002, 3, "SCSE");

            Lesson lessonB11 = new Lesson("LECTURE", 3, "BOTH", 3, 1, "LT2A", "G2");
            Lesson lessonB12 = new Lesson("TUTORIAL", 4, "BOTH", 6, 1, "TR18", "SS1");
            Lesson lessonB13 = new Lesson("LAB", 5, "ODD", 2, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10208 = new ArrayList<Lesson>(Arrays.asList(lessonB11, lessonB12, lessonB13));
            Index indexB1 = new Index("CZ2003", 10208, 30, Lesson_10208);

            Lesson lessonB21 = new Lesson("LECTURE", 3, "BOTH", 3, 1, "LT2A", "G2");
            Lesson lessonB22 = new Lesson("TUTORIAL", 1, "BOTH", 5, 1, "TR17", "SS2");
            Lesson lessonB23 = new Lesson("LAB", 4, "EVEN", 4, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10209 = new ArrayList<Lesson>(Arrays.asList(lessonB21, lessonB22, lessonB23));
            Index indexB2 = new Index("CZ2003", 10209, 30, Lesson_10209);

            Lesson lessonB31 = new Lesson("LECTURE", 3, "BOTH", 3, 1, "LT2A", "G2");
            Lesson lessonB32 = new Lesson("TUTORIAL", 2, "BOTH", 3, 1, "TR15", "SS3");
            Lesson lessonB33 = new Lesson("LAB", 2, "ODD", 6, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10210 = new ArrayList<Lesson>(Arrays.asList(lessonB31, lessonB32, lessonB33));
            Index indexB3 = new Index("CZ2003", 10210, 30, Lesson_10210);
            ArrayList<Index> Index_2003 = new ArrayList<Index>(Arrays.asList(indexB1, indexB2, indexB3));
            Course courseB = new Course("CZ2003", Index_2003, 3, "SCSE");

            Lesson lessonC11 = new Lesson("LECTURE", 4, "BOTH", 4, 1, "LT2A", "G3");
            Lesson lessonC12 = new Lesson("TUTORIAL", 1, "BOTH", 3, 1, "TR18", "SS1");
            Lesson lessonC13 = new Lesson("LAB", 2, "ODD", 2, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10211 = new ArrayList<Lesson>(Arrays.asList(lessonC11, lessonC12, lessonC13));
            Index indexC1 = new Index("CZ2004", 10211, 30, Lesson_10211);

            Lesson lessonC21 = new Lesson("LECTURE", 4, "BOTH", 4, 1, "LT2A", "G3");
            Lesson lessonC22 = new Lesson("TUTORIAL", 2, "BOTH", 5, 1, "TR17", "SS2");
            Lesson lessonC23 = new Lesson("LAB", 3, "EVEN", 4, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10212 = new ArrayList<Lesson>(Arrays.asList(lessonC21, lessonC22, lessonC23));
            Index indexC2 = new Index("CZ2004", 10212, 30, Lesson_10212);

            Lesson lessonC31 = new Lesson("LECTURE", 4, "BOTH", 4, 1, "LT2A", "G3");
            Lesson lessonC32 = new Lesson("TUTORIAL", 4, "BOTH", 3, 1, "TR15", "SS3");
            Lesson lessonC33 = new Lesson("LAB", 2, "ODD", 7, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10213 = new ArrayList<Lesson>(Arrays.asList(lessonC31, lessonC32, lessonC33));
            Index indexC3 = new Index("CZ2004", 10213, 1, Lesson_10213);
            ArrayList<Index> Index_2004 = new ArrayList<Index>(Arrays.asList(indexC1, indexC2, indexC3));
            Course courseC = new Course("CZ2004", Index_2004, 3, "SCSE");

            Lesson lessonD11 = new Lesson("LECTURE", 2, "BOTH", 4, 1, "LT2A", "G3");
            Lesson lessonD12 = new Lesson("TUTORIAL", 4, "BOTH", 7, 1, "TR18", "SS5");
            Lesson lessonD13 = new Lesson("LAB", 1, "ODD", 2, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10214 = new ArrayList<Lesson>(Arrays.asList(lessonD11, lessonD12, lessonD13));
            Index indexD1 = new Index("CZ2005", 10214, 30, Lesson_10214);

            Lesson lessonD21 = new Lesson("LECTURE", 2, "BOTH", 4, 1, "LT2A", "G3");
            Lesson lessonD22 = new Lesson("TUTORIAL", 5, "BOTH", 9, 1, "TR17", "SS4");
            Lesson lessonD23 = new Lesson("LAB", 3, "EVEN", 0, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10215 = new ArrayList<Lesson>(Arrays.asList(lessonD21, lessonD22, lessonD23));
            Index indexD2 = new Index("CZ2005", 10215, 30, Lesson_10215);

            Lesson lessonD31 = new Lesson("LECTURE", 2, "BOTH", 4, 1, "LT2A", "G3");
            Lesson lessonD32 = new Lesson("TUTORIAL", 4, "BOTH", 3, 1, "TR15", "SS8");
            Lesson lessonD33 = new Lesson("LAB", 2, "ODD", 8, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10216 = new ArrayList<Lesson>(Arrays.asList(lessonD31, lessonD32, lessonD33));
            Index indexD3 = new Index("CZ2005", 10216, 30, Lesson_10216);
            ArrayList<Index> Index_2005 = new ArrayList<Index>(Arrays.asList(indexD1, indexD2, indexD3));
            Course courseD = new Course("CZ2005", Index_2005, 3, "SCSE");
            //cz2003 10210 clashes with cz2004 10213
            
            Lesson lessonF11 = new Lesson("LECTURE", 3, "BOTH", 4, 1, "LT2A", "G6");
            Lesson lessonF12 = new Lesson("TUTORIAL", 1, "BOTH", 3, 1, "TR15", "SS6");
            Lesson lessonF13 = new Lesson("LAB", 1, "ODD", 8, 2, "SWL1", "SS1");
            ArrayList<Lesson> Lesson_10600 = new ArrayList<Lesson>(Arrays.asList(lessonF11, lessonF12, lessonF13));
            Index indexF3 = new Index("CZ2006", 10600, 30, Lesson_10600);
            ArrayList<Index> Index_2006 = new ArrayList<Index>(Arrays.asList(indexF3));
            Course courseF = new Course("CZ2006", Index_2006, 3, "SCSE");

            create(courseA);
            create(courseB);
            create(courseC);
            create(courseD);
            create(courseE);
            create(courseF);
        } catch (ClassNotFoundException|IOException i) {
                i.printStackTrace();
        }
    }
    /**
     * Writes all Course objects in courseList into "Course.ser" file.
     */
    public void fileWrite() {
        try {
            FileOutputStream fileOut = new FileOutputStream("Course.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(courseList);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + "Course.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    /**
     * Prints all fields of each Course object in courseList.
     */
    public void printAll() {
        System.out.println("+------------------------------------------------+");
        System.out.format("|%-10s|%-10s|%-5s|%-20s|\n", "Course", "School", "AUs", "Indexes/Vacancies");
        System.out.println("+------------------------------------------------+");
        for(Course course: courseList){
            if (course.getIndexList().size()>0){
                System.out.format("|%-10s|%-10s|%-5s|%-20s|\n", course.getCourseCode(), course.getSchool(), course.getNumAus(),
                        course.getIndexList().get(0).getIndex() + " (" + course.getIndexList().get(0).getVacancies()
                                + "/" + course.getIndexList().get(0).getSize() + ")");
                for (int i = 1; i < course.getIndexList().size(); i++) {
                    System.out.format("|%-10s|%-10s|%-5s|%-20s|\n", " ", " ", " ",
                            course.getIndexList().get(i).getIndex() + " (" + course.getIndexList().get(i).getVacancies()
                                    + "/" + course.getIndexList().get(i).getSize() + ")");
                }
            }
            else
                System.out.format("|%-10s|%-10s|%-5s|%-20s|\n", course.getCourseCode(), course.getSchool(), course.getNumAus(), " ");
        }
        System.out.println("+------------------------------------------------+");
    }
}

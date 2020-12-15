package controllers.manager;

import entities.EntityObject;
import entities.PendingSwop;

import java.io.*;
import java.util.ArrayList;

import controllers.studentController;

/**
 * Contains a list of PendingSwop that serve as the database for all pending swaps. Provides the implementation for methods specified in the BasicManager interface for PendingSwop entities.
 * Also provides helper methods that query the list of PendingSwops based on varying criteria, as need by the swopController. 
 * @author Daniel
 *
 */
public class swopManager implements BasicManager{
    private ArrayList<PendingSwop> swopList = new ArrayList<PendingSwop>();
	/**
	 * Constructor for swopManager. It performs a file read which gets the list of current PendingSwop from the file "PendingSwop.ser" and puts it in the ArrayList of PendingSwop.
	 */
    public swopManager () {
        fileRead();
    }

    /**
     * Implements find(Object) from Basic Manager. It finds based on the exact match of pending swap. Takes in CourseCode, fromMatric, fromIndex, toMatric, toIndex.
     * @param swop PendingSwop object loaded with the variables required.
     * @return The exact matching pending swop from the swopList. Returns null if none are found.
     */
    public PendingSwop find(EntityObject swop) {
        for (PendingSwop value : swopList) {
            if (value.getCourseCode().equals(((PendingSwop) swop).getCourseCode())
            && value.getFromMatric().equals(((PendingSwop) swop).getFromMatric())
            && value.getFromIndex() == ((PendingSwop) swop).getFromIndex()
            && value.getToMatric().equals(((PendingSwop) swop).getToMatric())
            && value.getToIndex() == ((PendingSwop) swop).getToIndex())
                return value;
        }
        return null;
    }

    /**
     * Finds based CourseCode, fromMatric & fromIndex to prevent the user from creating more than one swap for a specific index.
     * @param swop PendingSwop object loaded with the variables required.
     * @return The pending swop with matching owner information, ie fromMatric & fromIndex. Returns null if none are found.
     */
    public PendingSwop isSimilar (EntityObject swop) {
        for (PendingSwop value : swopList) {
            if (value.getCourseCode().equals(((PendingSwop) swop).getCourseCode())
            && value.getFromMatric().equals(((PendingSwop) swop).getFromMatric())
            && value.getFromIndex() == ((PendingSwop) swop).getFromIndex())
                return value;
        }
        return null;
    }
    
    /**
     * Finds based on fromMatric in order to display all the pending swaps that belong to the current student.
     * @param swop PendingSwop object loaded with the variables required.
     * @return An ArrayList of all PendingSwop that belong to the current student. Returns null if none are found.
     */
    public ArrayList<PendingSwop> findMatric (EntityObject swop) {
    	ArrayList<PendingSwop> resultList = new ArrayList<PendingSwop>();
    	for (PendingSwop value : swopList) {
    		if (value.getFromMatric().equals(((PendingSwop)swop).getFromMatric())) {
    			resultList.add(value);
    		}
    	}
    	
    	return resultList;
    }
    
    /**
     * Finds based on CourseCode, toMatric and toIndex to check if someone else has created a pending swap that is intended for the current student.
     * @param swop PendingSwop object loaded with the variables required.
     * @return The matching swap satisfying the above criteria. Returns null if none are found.
     */
    public PendingSwop findDestination (EntityObject swop) {
        for (PendingSwop value : swopList) {
            if (value.getCourseCode().equals(((PendingSwop) swop).getCourseCode())
            && value.getToMatric().equals(((PendingSwop) swop).getToMatric())
            && value.getToIndex() == ((PendingSwop) swop).getToIndex())
                return value;
        }
        return null;
    }

    /**
     * Implements create(Object) from BasicManager interface. Will create a new pending swap.
     * @param swop PendingSwop object loaded with the all its attributes required.
     * @return True.
     */
    public boolean create(EntityObject swop) {
        swopList.add((PendingSwop) swop);
        fileWrite();
        return true;
    }

    /**
     * Implements delete(Object) from BasicManager interface. Removes the specified pending swap.
     * @param swop PendingSwop object loaded with the all its attributes required.
     */
    public void delete(EntityObject swop) {
    	for (PendingSwop value : swopList) {
            if (value.getCourseCode().equals(((PendingSwop) swop).getCourseCode())
            && value.getFromMatric().equals(((PendingSwop) swop).getFromMatric())
            && value.getFromIndex() == ((PendingSwop) swop).getFromIndex()
            && value.getToMatric().equals(((PendingSwop) swop).getToMatric())
            && value.getToIndex() == ((PendingSwop) swop).getToIndex()) {
            	swopList.remove(value);
            	fileWrite();
            	return;
            }
        }
        
    }

    /**
     * Implements fileRead() from BasicManager interface. Reads from "PendingSwop.ser" and puts the PendingSwop into swopList.
     */
    public void fileRead() {
        try{
            FileInputStream fileIn = new FileInputStream("PendingSwop.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
            swopList = (ArrayList<PendingSwop>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException f) {
            //System.out.println("PendingSwop file not found");
            // initialise dummy data
            
        	PendingSwop pendSwop = new PendingSwop();
        	studentController scontroller = new studentController();
        	
        	pendSwop = new PendingSwop("CZ2002", "U1838484D", 10205, "U1921316F", 10206);
        	// username is JIEEL210, PW is swop2
        	// username is SHTAN66, toPW is bun
        	this.create(pendSwop);
//        	scontroller.registerCourse("CZ2002", 10205, "U1838484D");
//        	scontroller.registerCourse("CZ2002", 10206, "U1921316F");
        	
        	pendSwop = new PendingSwop("CZ2003", "U1824834F", 10210, "U1838484D", 10209); 
        	// username is TQYING013, PW is swop
        	// username is JIEEL210, toPW is swop2
        	this.create(pendSwop);
//        	scontroller.registerCourse("CZ2003", 10210, "U1824834F");
//        	scontroller.registerCourse("CZ2003", 10209, "U1838484D");
        	
        } catch (ClassNotFoundException|IOException i) {
                i.printStackTrace();
        }
    }
    
    /**
     * Implements fileWrite() from BasicManager interface. Writes swopList to file "PendingSwop.ser".
     */
    public void fileWrite()  {
		try {
            FileOutputStream fileOut = new FileOutputStream("PendingSwop.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(swopList);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in PendingSwop.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
		
    }
    
    /**
     * Implements printAll() from BasicManager interface. Prints out swopList.
     */
    public void printAll() {
    	System.out.println("Course       Swop from       Swop with       Swop to");
    	for(PendingSwop swop: swopList){
            System.out.println(swop.getCourseCode() + "       " + String.valueOf(swop.getFromIndex()) + "           " + swop.getToMatric() + "       " + String.valueOf(swop.getToIndex()));
        }
    }


}

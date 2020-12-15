package controllers.manager;
import entities.AccessDateTime;
import entities.Admin;
import entities.EntityObject;
import entities.Student;
import entities.AccessDateTime
		;

import java.io.*;
import java.time.*;
import java.util.ArrayList;

/**
 * Manager that manages AccessDateTime entities.
 */
public class accessDateTimeManager implements BasicManager{
	/**
	 * List of all valid AccessDateTime entities in the database.
	 */
	private ArrayList<AccessDateTime> accessList = new ArrayList<AccessDateTime>();

	/**
	 * Constructs a manager with a list of all valid AccessDateTime entities in the database.
	 */
	public accessDateTimeManager () {
		fileRead();
	}

	/**
	 * Finds real instance of AccessDateTime.
	 * @param access dummy AccessDateTime with instantiated keys (i.e. programOfStudy and yearOfStudy).
	 * @return real AccessDateTime instance.
	 */
	public AccessDateTime find(EntityObject access) {
		for (AccessDateTime value : accessList) {
			if (value.getProgramOfStudy().equals(((AccessDateTime) access).getProgramOfStudy())
				&& value.getYearOfStudy() == ((AccessDateTime) access).getYearOfStudy()) {
				return value;
			}
		}
		return null;
	}

	/**
	 * Creates real AccessDateTime object instance.
	 * @param access AccessDateTime object with all fields initialized.
	 * @return whether object is successfully created.
	 */
	public boolean create(EntityObject access) {
		for (AccessDateTime adt: accessList){
			if ((adt.getProgramOfStudy().equals(((AccessDateTime)access).getProgramOfStudy())) &&
			(adt.getYearOfStudy() == ((AccessDateTime)access).getYearOfStudy()))
				return false;
		}
		accessList.add((AccessDateTime) access);
		fileWrite();
		return true;
	}

	/**
	 * Deletes real AccessDateTime object.
	 * @param access dummy AccessDateTime with instantiated keys (i.e. programOfStudy and yearOfStudy).
	 */
	public void delete(EntityObject access) {
		for(AccessDateTime value : accessList) {
			if (value.getProgramOfStudy().equals(((AccessDateTime) access).getProgramOfStudy())
				&& value.getYearOfStudy() == ((AccessDateTime) access).getYearOfStudy())
				accessList.remove(value);
		}
		fileWrite();
	};

	/**
	 * Reads "AccessDateTime.ser" file to retrieve and place stored AccessDateTime objects into accessList. <br>
	 * Contains some dummy data to initialize the program.
	 */
	public void fileRead() {
        try{
            FileInputStream fileIn = new FileInputStream("AccessDateTime.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
            accessList = (ArrayList<AccessDateTime>)objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException f) {
			//System.out.println("Access file not found.");
			// Create dummy data
        	
        	/**
        	 * All are allowed to log in, except "CE", year = 3
        	 */
			AccessDateTime accDT = new AccessDateTime();
			accDT = new AccessDateTime("CSC", 1, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 00, 00), 
					LocalDateTime.of(2021, Month.DECEMBER, 1, 14, 00, 00));
			this.create(accDT);
			accDT = new AccessDateTime("CE", 3, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 00, 00), 
					LocalDateTime.of(2021, Month.DECEMBER, 1, 14, 00, 00));
			this.create(accDT);
			accDT = new AccessDateTime("REP", 2, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 00, 00), 
					LocalDateTime.of(2021, Month.DECEMBER, 1, 14, 00, 00));
			this.create(accDT);
			accDT = new AccessDateTime("MAE", 4, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 00, 00), 
					LocalDateTime.of(2021, Month.DECEMBER, 1, 14, 00, 00));
			this.create(accDT);
			accDT = new AccessDateTime("BCG", 1, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 00, 00), 
					LocalDateTime.of(2021, Month.DECEMBER, 1, 14, 00, 00));
			this.create(accDT);
			accDT = new AccessDateTime("CS", 3, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 00, 00), 
					LocalDateTime.of(2020, Month.AUGUST, 1, 14, 00, 00));
			this.create(accDT);
			accDT = new AccessDateTime("ECON", 4, LocalDateTime.of(2020, Month.DECEMBER, 1, 10, 00, 00), 
					LocalDateTime.of(2021, Month.DECEMBER, 1, 14, 00, 00));
			this.create(accDT);
			accDT = new AccessDateTime("DA", 1, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 00, 00), 
					LocalDateTime.of(2021, Month.DECEMBER, 1, 14, 00, 00));
			this.create(accDT);
			accDT = new AccessDateTime("ACC", 2, LocalDateTime.of(2020, Month.JANUARY, 1, 10, 00, 00), 
					LocalDateTime.of(2021, Month.DECEMBER, 1, 14, 00, 00));
			this.create(accDT);
			accDT = new AccessDateTime("BUS", 1, LocalDateTime.of(2020, Month.DECEMBER, 31, 10, 00, 00), 
					LocalDateTime.of(2020, Month.DECEMBER, 31, 14, 00, 00));
			this.create(accDT);
	
        } catch (ClassNotFoundException|IOException i) {
            i.printStackTrace();
        }
	}

	/**
	 * Writes all AccessDateTime objects in accessList into "AccessDateTime.ser" file.
	 */
	public void fileWrite() {
		try {
            FileOutputStream fileOut = new FileOutputStream("AccessDateTime.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(accessList);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in AccessDateTime.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
	}

	/**
	 * Prints all fields of each AccessDateTime object in accessList.
	 */
	public void printAll() {
		for(AccessDateTime access: accessList){
			System.out.println("Program of Study: " + access.getProgramOfStudy());
			System.out.println("Year of Study: " + access.getYearOfStudy());
			System.out.println("Start of Access Time: " + access.getStartAccessPeriod());
			System.out.println("End of Access Time: " + access.getEndAccessPeriod());
		}
	}
}

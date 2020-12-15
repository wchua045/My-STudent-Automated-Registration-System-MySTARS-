package controllers.manager;
import entities.*;
import entities.Admin;
import entities.Admin;

import java.io.*;
import java.util.ArrayList;

/**
 * Manager that manages Admin entities.
 */
public class adminManager implements BasicManager{
    /**
     * List of all valid Admin entities in the database.
     */
    private ArrayList<Admin> adminList = new ArrayList<Admin>();

    /**
     * Constructs a manager with a list of all valid Admin entities in the database.
     */
    public adminManager () {
        fileRead();
    };

    /**
     * Finds real instance of Admin.
     * @param admin dummy Admin with instantiated key (i.e. username).
     * @return real Admin instance.
     */
    public Admin find(EntityObject admin) {
        for (Admin value : adminList) {
                if (value.getUsername().equals(((Admin) admin).getUsername())) {
                    return value;
                }
            }
        System.out.println("No Admin of Username " + ((Admin)admin).getUsername() + " Found.");
        return null;
    }

    /**
     * Creates real Admin object instance.
     * @param admin Admin object with all fields initialized.
     * @return whether object is successfully created.
     */
    public boolean create(EntityObject admin) {
        Admin tempAdmin = new Admin();
        tempAdmin.setUsername(((Admin)admin).getUsername());
        if(find(tempAdmin)==null) {
            System.out.println("Creating Admin of Username " + ((Admin)admin).getUsername());
            adminList.add((Admin) admin);
            fileWrite();
            return true;
        }
        return false;
    };

    /**
     * Deletes real Admin object.
     * @param admin dummy Admin with instantiated key (i.e. username).
     */
    public void delete(EntityObject admin) {
        for(Admin value : adminList) {
            if ((value.getUsername().equals(((Admin) admin).getUsername())))
                adminList.remove(value);
        }
        fileWrite();
    };

    /**
     * Reads "Admin.ser" file to retrieve and place stored Admin objects into adminList. <br>
     * Contains some dummy data to initialize the program.
     */
    public void fileRead() {
        try{
            FileInputStream fileIn = new FileInputStream("Admin.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
            adminList = (ArrayList<Admin>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException f) {
            //System.out.println("Admin file not found");
            // initialise dummy data
            Admin tempAdmin = new Admin("ADMIN1","password","name_1",'M');
            Admin tempAdmin2 = new Admin("ADMIN2","password","name_2",'F');
            Admin tempAdmin3 = new Admin("ADMIN3","password","name_3",'F');
            create(tempAdmin);
            create(tempAdmin2);
            create(tempAdmin3);
        } catch (ClassNotFoundException|IOException i) {
                i.printStackTrace();
        }
    }
    /**
     * Writes all Admin objects in adminList into "Admin.ser" file.
     */
    public void fileWrite() {
        try {
            FileOutputStream fileOut = new FileOutputStream("Admin.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            
            out.writeObject(adminList);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + "Admin.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    /**
     * Prints all fields of each Admin object in adminList.
     */
    public void printAll() {
        for(Admin admin: adminList){
            System.out.print(admin.getUsername());
        }
    }

    ;
}

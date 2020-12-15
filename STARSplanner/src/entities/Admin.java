package entities;
import java.io.Serializable;
/**
 * This class represents an Administrator and contains all the attributes that an administrator must have.
 * Admin Key: username
 */
public class Admin extends User implements EntityObject, Serializable{
	/**
	 * Creates an administrator account
	 */
    public Admin(){ super(); }
    /**
     * Creates an administrator account for 
     * @param username, password, name, gender
     */
    public Admin(String username, String password, String name, char gender) {
    	super(username, password, name, gender, "A");
    }
}

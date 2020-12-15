package entities;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * This class represents a User and contains all the necessary attributes that a user must have.
 * @author chua_
 *User Key: username
 */
public class User implements EntityObject, Serializable{
	/**
	 * SALT used for password hashing.
	 */
	private static final String salt = "STARSplannerSALT";
	/**
	 * The username of this User which will be used for login.
	 */
	private String username;
	
	/**
	 * The account password of this User which will be used for login.
	 */
	private String password;
	
	/**
	 * The full name of this User.
	 */
	private String name;
	
	/**
	 * The gender of this User. '1' for Male, '2' for Female
	 */
	private char gender;
	
	/**
	 * The account type of this User. 'S' for Student or 'A' for Admin.
	 */
	private String accountType;
	/**
	 * Creates a User.
	 */
	public User() {}

	/**
	 * Creates a User.
	 * @param username, password, name, gender, usertype
	 */
	public User (String username, String password, String name, char gender, String accountType) {
		this.username = username;
		this.password = generateHashedPassword(password);
		this.name = name;
		this.gender = gender;
		this.accountType = accountType;
	}
	
	/** 
	 * Gets the username of this User.
	 * @return this User's username.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the password of this User.
	 * @return this User's password.
	 */
	public String getPassword() {
		return password;
	}

	/** 
	 * Gets the fullname of the User.
	 * @return this user's fullname.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the gender of this User.
	 * @return this user's gender.
	 */
	public char getGender() {
		return gender;
	}
	
	/**
	 * Gets the account type of this User.
	 * 
	 * @return this user's account type.
	 */
	public String getAccountType() {
		return accountType;
	}
	
	/**
	 * Changes the username of this User.
	 * @param username
	 * this User's new username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Changes the password of this User.
	 * @param password
	 * this User's new password
	 */
	public void setPassword(String password) {
		this.password = generateHash(password);
	}
	
	/**
	 * Changes the fullname of this User.
	 * @param name
	 * this User's new fullname.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** 
	 * Changes the gender of this User.
	 * @param gender 
	 * this User's new gender.
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}
	
	/**
	 * Changes the account type of this User.
	 * 
	 * @param accountType
	 * This User's new account type.
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	/**
	 * Validate if the password entered in is the same as this User's password.
	 * Return true if same else false.
	 * @param pwEntry
	 * the password entered by current User.
	 * @return true or false.
	 */
	public boolean validate(String pwEntry) {
		if (this.password.equals(pwEntry))
			return true;
		else
			return false;
	}
	
	/**
	 * Turns plain text into SHA-256 hash.
	 * 
	 * @param input
	 *            plain text.
	 * @return hash.
	 */
	private static String generateHash(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // use SHA-256 Algorithm
			byte[] encodedHash = md.digest(input.getBytes(StandardCharsets.UTF_8));
			for (int i = 0; i < encodedHash.length; i++) {
	            String hex = Integer.toHexString(0xff & encodedHash[i]);
	            if(hex.length() == 1) 
	            	hash.append('0');
	            hash.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			// failed to generate hash
		}

		return hash.toString();
	}

	/**
	 * Turns plain password into hashed password.
	 * 
	 * @param password
	 * plain password.
	 * @return hashed password.
	 */
	public static String generateHashedPassword(String password) {
		String saltedpw = salt + password;
		String hashedpw = generateHash(saltedpw);
		return hashedpw;
	}

}
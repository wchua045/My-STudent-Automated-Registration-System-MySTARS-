package entities;
/**
 * This class takes in an integer and a String object in pairs. It is used in the construction of lists containing pairs
 * @author chua_
 *
 */
public class Pair {
		/**
		 * this takes in an integer value
		 */
	    private int l;
	   /**
	    * this takes in a String
	    */
	    private String rr; 
	    /**
	     * creates a Pair of string and integer
	     * @param l, rr
	     */
	    public Pair(int l, String rr){
	        this.l = l; // index
	        this.rr = rr; //coursecode (for studentController.CoursesRegistered())
	    }
	    /**
	     * creates a Pair of string and integer
	     */
	    public Pair() {
			// TODO Auto-generated constructor stub
		}
		public int getL(){ return l; }
	    public String getRR() {return rr;}
	    public void setL(int l){ this.l = l; }
	    public void setRR(String rr) {this.rr = rr;}
}

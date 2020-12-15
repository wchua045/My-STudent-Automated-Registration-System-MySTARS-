package entities;

import java.io.Serializable;
/**
 * This class represents a pending swop - a swop that has yet to be matched to its corresponding swop
 * @author chua_
 *Pending Swop key: courseCode, fromMatric, fromIndex, toMatric, toIndex
 */
public class PendingSwop implements EntityObject, Serializable{
	/**
	 * course code of the index involved in swopping. Involved in the verification of corresponding pending swops.
	 */
    private String courseCode;
    /**
     * the matriculation number of studentA who submitted this swop
     */
	private String fromMatric;
	/**
	 * the index of a course that studentA is currently registered under
	 */
    private int fromIndex;
    /**
     * the matriculation number of student whom studentA would like to perform the swop with/swop index with.
     */
    private String toMatric;
    /**
     * the index of a course that studentA wishes to switch to. Index of which student of toMatric is currently registered to
     */
    private int toIndex;
    /**
     * creates a pending swop
     */
    public PendingSwop () {}
    /**
     * creates a pending swop
     * @param courseCode, fromMatric, fromIndex, toMatric, toIndex
     */
    public PendingSwop(String courseCode, String fromMatric, int fromIndex, String toMatric, int toIndex) {
		this.courseCode = courseCode;
		this.fromMatric = fromMatric;
		this.fromIndex = fromIndex;
		this.toMatric = toMatric;
		this.toIndex = toIndex;
	}

	public String getCourseCode() {
		return courseCode;
	}


	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}


	public String getFromMatric() {
        return fromMatric;
    }

    public void setFromMatric(String fromMatric) {
        this.fromMatric = fromMatric;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public String getToMatric() {
        return toMatric;
    }

    public void setToMatric(String toMatric) {
        this.toMatric = toMatric;
    }

    public int getToIndex() {
        return toIndex;
    }

    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }
}

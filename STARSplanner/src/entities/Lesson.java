package entities;
import java.sql.Time;
import java.io.Serializable;

/**
 * Represents a particular lesson details of a Index.
 */
public class Lesson implements EntityObject, Serializable{
	//declaring 1)type of lessons, 2)days in a week, 3) weeks in a semester, 4)startTime, endTime
	//5)venue of lesson 6)lesson group e.g. Lab group SSP5

	/**
	 * Week in which lesson is held (Both - both even and odd weeks).
	 */
	enum Week {
		EVEN(0), ODD(1),  BOTH(2);
	private int week;
	Week(int week) {this.week = week;}
	}
	private Week week;
	/**
	 * Type of lesson.
	 */
	enum Type {
		LAB,TUTORIAL,LECTURE;
	}
	private Type type;
	/**
	 * Day of the week on which lesson is held.
	 */
	private int day;
	/**
	 * Start timing of lesson - represented in integer form according to the following index: <br>
	 * startIndex = 0:830, 1:930, 2:1030, 3:1130, 4:1230, ..., 11:1930
	 */
	private int startIndex;
	/**
	 * Duration of lesson - in hours.
	 */
	private int duration;
	/**
	 * Location where lesson is held.
	 */
	private String venue;
	/**
	 * Group which students in the lesson are under.
	 */
	private String group;
	
	//constructor

	/**
	 * Empty constructor for dummy lesson object.
	 */
	public Lesson(){}

	/**
	 *
	 * @param t type of lesson.
	 * @param d day of week of lesson.
	 * @param w week of lesson (odd/even/both).
	 * @param start start time of lesson - represented by indexing method.
	 * @param length duration of lesson in hours.
	 * @param location location where lesson is held.
	 * @param g group which students in the lesson are under.
	 */
	public Lesson(String t, int d, String w, int start, int length, String location, String g) {
		Type type1 = Type.valueOf(t);
		type = type1;
		day = d;
		Week week1 = Week.valueOf(w);
		week = week1;
		startIndex = start; duration = length;
		venue = location;
		group = g;
	}

	//get and set methods
	public Type getType() { return type; }
	public int getDay() { return day; }
	public Week getWeek() { return week; }
	public int getStartIndex() { return startIndex; }
	public int getDuration() { return duration; }
	public String getVenue() { return venue; }
	public String getGroup() { return group; }

	public void setDay(int day) {this.day = day; }
	public void setDuration(int duration) {this.duration = duration;}
	public void setGroup(String group) {this.group = group; }
	public void setStartIndex(int startIndex) { this.startIndex = startIndex; }
	public void setType(Type type) { this.type = type; }
	public void setVenue(String venue) {this.venue = venue; }
	public void setWeek(Week week) { this.week = week; }
}

package entities;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.List;

/**
 * Representation of all lessons of all indexes the calling Student object is registered under. <br>
 * Key: 2D Odd and Even Timetable Arrays
 */
public class TimeTable implements Serializable {
    /**
     * Two (odd and even) 2D matrices of time of the day against day of the week.<br>
     * Each 'cell' of 2D matrix contains a list of relevant lesson attributes. <br>
     */
    private String [][][][] timetable = new String[2][12][6][5];

    /**
     * Constructs timetable based on all lessons of all indexes of a student.
     * @param indexList list of indexes calling student instance is registered under.
     */
    public TimeTable(ArrayList <Index> indexList){
        //initialize even timetable
        for (int i = 0; i < timetable[0].length; i++) {
            for (int j = 0; j < timetable[0][i].length; j++) {
                for (int k = 0; k < timetable[0][i][j].length; k++) {
                    timetable[0][i][j][k] = null;
                }
            }
        }
        //initialize odd timetable.
        for (int i = 0; i < timetable[1].length; i++) {
            for (int j = 0; j < timetable[1][i].length; j++) {
                for (int k = 0; k < timetable[1][i][j].length; k++) {
                    timetable[1][i][j][k] = null;
                }
            }
        }
        // place indexes in index list into timetable.
        for (Index index: indexList){
            for (Lesson lesson: index.getLessonList()) {
                if (lesson.getWeek() != Lesson.Week.ODD) {
                    for (int i = 0; i < lesson.getDuration(); i++) {
                        timetable[0][lesson.getStartIndex() + i][lesson.getDay()][0] = index.getCourseCode();
                        timetable[0][lesson.getStartIndex() + i][lesson.getDay()][1] = String.valueOf(lesson.getType());
                        timetable[0][lesson.getStartIndex() + i][lesson.getDay()][2] = lesson.getVenue();
                        timetable[0][lesson.getStartIndex() + i][lesson.getDay()][3] = lesson.getGroup();
                        timetable[0][lesson.getStartIndex() + i][lesson.getDay()][4] = "registered";
                    }
                }
                if (lesson.getWeek() != Lesson.Week.EVEN){
                    for (int i = 0; i < lesson.getDuration(); i++) {
                        timetable[1][lesson.getStartIndex() + i][lesson.getDay()][0] = index.getCourseCode();
                        timetable[1][lesson.getStartIndex() + i][lesson.getDay()][1] = String.valueOf(lesson.getType());
                        timetable[1][lesson.getStartIndex() + i][lesson.getDay()][2] = lesson.getVenue();
                        timetable[1][lesson.getStartIndex() + i][lesson.getDay()][3] = lesson.getGroup();
                        timetable[1][lesson.getStartIndex() + i][lesson.getDay()][4] = "registered";
                    }
                }
            }
        }
    }

    /**
     * Checks for clash between index student is attempting to register and their current timetable.
     * @param newIndex Index object student is attempting to register.
     * @return whether or not a clash is present (true - clash present, false - no clash present).
     */
    public boolean checkClash(Index newIndex){
        for (Lesson lesson: newIndex.getLessonList()){
            if (lesson.getWeek() != Lesson.Week.ODD) {
                for (int i = 0; i < lesson.getDuration(); i++) {
                    if (timetable[0][lesson.getStartIndex() + i][lesson.getDay()][4] != null)
                        return true; // there is a clash//
                }
            }
            if (lesson.getWeek() != Lesson.Week.EVEN){
                for (int i = 0; i < lesson.getDuration(); i++) {
                    if (timetable[1][lesson.getStartIndex() + i][lesson.getDay()][4] != null)
                        return true; // there is a clash//
                }
            }
        }
        return false; // no clash
    }

    /**
     * Places a lessons of a new index object student is registered under into the current timetable.
     * @param newIndex New index object student is registered under.
     */
    public void placeIndex(Index newIndex){
        for (Lesson lesson: newIndex.getLessonList()){
            if (lesson.getWeek() != Lesson.Week.ODD) {
                for (int i = 0; i < lesson.getDuration(); i++) {
                    timetable[0][lesson.getStartIndex() + i][lesson.getDay()][0] = newIndex.getCourseCode();
                    timetable[0][lesson.getStartIndex() + i][lesson.getDay()][1] = String.valueOf(lesson.getType());
                    timetable[0][lesson.getStartIndex() + i][lesson.getDay()][2] = lesson.getVenue();
                    timetable[0][lesson.getStartIndex() + i][lesson.getDay()][3] = lesson.getGroup();
                    timetable[0][lesson.getStartIndex() + i][lesson.getDay()][4] = "registered";
                }
            }
            if (lesson.getWeek() != Lesson.Week.EVEN) {
                for (int i = 0; i < lesson.getDuration(); i++) {
                    timetable[1][lesson.getStartIndex() + i][lesson.getDay()][0] = newIndex.getCourseCode();
                    timetable[1][lesson.getStartIndex() + i][lesson.getDay()][1] = String.valueOf(lesson.getType());
                    timetable[1][lesson.getStartIndex() + i][lesson.getDay()][2] = lesson.getVenue();
                    timetable[1][lesson.getStartIndex() + i][lesson.getDay()][3] = lesson.getGroup();
                    timetable[1][lesson.getStartIndex() + i][lesson.getDay()][4] = "registered";
                }
            }
        }
    }

    /**
     * Removes lessons of an existing Index object from the current timetable.
     * @param newIndex existing Index student is registered or waitlisted under.
     */
    public void clearIndex(Index newIndex) {
        for (Lesson lesson : newIndex.getLessonList()) {
            if (lesson.getWeek() != Lesson.Week.ODD) {
                for (int i = 0; i < lesson.getDuration(); i++) {
                    for (int k = 0; k < 5; k++) {
                        timetable[0][lesson.getStartIndex() + i][lesson.getDay()][k] = null;
                    }
                }
            }
            if (lesson.getWeek() != Lesson.Week.EVEN){
                for (int i = 0; i < lesson.getDuration(); i++) {
                    for (int k = 0; k < 5; k++) {
                        timetable[1][lesson.getStartIndex() + i][lesson.getDay()][k] = null;
                    }
                }
            }
        }
    }

    /**
     * Places a lessons of a new index object student is waitlisted under into the current timetable.
     * @param newIndex New index object student is waitlisted under.
     */
    public void placeWaitlistIndex(Index newIndex){
        for (Lesson lesson: newIndex.getLessonList()){
            if (lesson.getWeek() != Lesson.Week.ODD) {
                for (int i = 0; i < lesson.getDuration(); i++) {
                    timetable[0][lesson.getStartIndex() + i][lesson.getDay()][0] = newIndex.getCourseCode();
                    timetable[0][lesson.getStartIndex() + i][lesson.getDay()][1] = String.valueOf(lesson.getType());
                    timetable[0][lesson.getStartIndex() + i][lesson.getDay()][2] = lesson.getVenue();
                    timetable[0][lesson.getStartIndex() + i][lesson.getDay()][3] = lesson.getGroup();
                    timetable[0][lesson.getStartIndex() + i][lesson.getDay()][4] = "waitlisted";
                }
            }
            if (lesson.getWeek() != Lesson.Week.EVEN) {
                for (int i = 0; i < lesson.getDuration(); i++) {
                    timetable[1][lesson.getStartIndex() + i][lesson.getDay()][0] = newIndex.getCourseCode();
                    timetable[1][lesson.getStartIndex() + i][lesson.getDay()][1] = String.valueOf(lesson.getType());
                    timetable[1][lesson.getStartIndex() + i][lesson.getDay()][2] = lesson.getVenue();
                    timetable[1][lesson.getStartIndex() + i][lesson.getDay()][3] = lesson.getGroup();
                    timetable[1][lesson.getStartIndex() + i][lesson.getDay()][4] = "waitlisted";
                }
            }
        }
    }

    /**
     * Prints current timetable in readable formatting.
     */
    public void printTimeTable(){
        //ODD
         for (int n=0;n<2;n++){
             if(n==0)
                System.out.println("Even Timetable: ");
             else
                 System.out.println("Odd Timetable: ");
            System.out.println("+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
            System.out.format("|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|%-15s|\n", " ", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
            System.out.println("+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
            String[] lesson_timings = {"08:30-09:30", "09:30-10:30", "10:30-11:30", "11:30-12:30", "12:30-13:30", "13:30-14:30",
                    "14:30-15:30", "15:30-16:30", "16:30-17:30", "17:30-18:30", "18:30-19:30", "19:30-20:30"};
            for (int i = 0; i < timetable[n].length; i++) {
                System.out.format("|%-15s", lesson_timings[i]);
                for (int j = 0; j < timetable[n][i].length; j++) {
                    if (timetable[n][i][j][4] != null)
                        System.out.format("|%-15s", "<" + timetable[n][i][j][4] + ">");
                    else System.out.print("|               ");
                }
                System.out.print("|\n");
                System.out.format("|%-15s", "               ");
                for (int j = 0; j < timetable[n][i].length; j++) {
                    if (timetable[n][i][j][4] != null)
                        System.out.format("|%-15s", timetable[n][i][j][0] + ":" + timetable[n][i][j][1]);
                    else System.out.print("|               ");
                }
                System.out.print("|\n");
                System.out.format("|%-15s", "               ");
                for (int j = 0; j < timetable[n][i].length; j++) {
                    if (timetable[n][i][j][4] != null)
                        System.out.format("|%-15s", timetable[n][i][j][2] + " [" + timetable[n][i][j][3] + "]");
                    else System.out.print("|               ");
                }
                System.out.print("|\n");
                System.out.print("+---------------+---------------+---------------+---------------+---------------+---------------+---------------+");
                System.out.print("\n");
            }
        }
    }
}

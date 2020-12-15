package boundaries;
import entities.*;

import controllers.*;
import controllers.manager.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents a boundary class for displaying student options.
 * 
 * @author
 *
 */
public class studentBoundary {
    /**
     * Scanner to take in option.
     */
    private static Scanner scanner = new Scanner(System.in);
    /**
     * Choice selected by user.
     */
    private static int choice;
    /**
     * A method called to display administrator options.
     *
     * @param unEntry
     * Student who called this method.
     * @param matric
     * matric number of Student who called this method.
     */
    public static void display(String unEntry, String matric) {
    	studentController scontroller = new studentController();
        if (unEntry != null) {
            do {
				try {
					System.out.println("+-----------------------+\n" +
     				   			   "| Welcome "+unEntry+"\t|\n"+
     				   			   "+-----------------------+-------------------------------------+\n"+
     				   			   "|                      = Student Menu =                       |\n"+
     				   			   "|                                                             |\n"+
            					   "| Please select one of the options below:                     |\n"+
            					   "| 1. Check Courses Registered                                 |\n"+
            					   "| 2. Add a Course                                             |\n"+
            					   "| 3. Drop a Course                                            |\n"+
            					   "| 4. Check vacancies available for all indexes in a course    |\n"+
            					   "| 5. Check vacancies available for an index                   |\n"+
            					   "| 6. Change index of existing registered index                |\n"+
            					   "| 7. Swop index with another Student                          |\n"+
            					   "| 8. Print timetable                                          |\n"+
            					   "| 9. Drop waitlisted courses                                  |\n"+
            					   "| 0. Log out                                                  |\n"+
            					   "+-------------------------------------------------------------+");
					while (!scanner.hasNextInt()) {
						scanner.next();
						System.out.println("Please enter valid option:");
					}
					choice = scanner.nextInt();
					switch (choice) {
						case 1:
							ArrayList<Pair> courseList = scontroller.checkCoursesRegistered(matric);
							System.out.println(">Total AUs: "+scontroller.getNumAus(matric));
							if (courseList.size() == 0)
								System.out.println(" No Courses Registered.\n");
							else {
								System.out.println(" Registered courses:\n"+
												   "+---------------------+\n"+
												   "| Course Code | Index |");
								 for(int i = 0; i<courseList.size();i++) {
									System.out.println("| "+ courseList.get(i).getRR() +"      | "+ courseList.get(i).getL()+" |");
								 }
								 System.out.println("+---------------------+\n");
							}
							
							ArrayList<Pair> studentWaitList = scontroller.checkCoursesWaitlisted(matric);
							if (studentWaitList.size() == 0)
								System.out.println(" No Courses Waitlisted.\n");
							else {
								System.out.println(" Waitlisted courses:\n"+
										   		   "+---------------------+\n"+
										   		   "| Course Code | Index |");
								 for(int i = 0; i<studentWaitList.size();i++) {
									System.out.println("| "+ studentWaitList.get(i).getRR() +"      | "+ studentWaitList.get(i).getL()+" |");
								 }
								 System.out.println("+---------------------+\n");
							}
							break;
						case 2:
							System.out.println("Please key in Course code: ");
							String courseCode1 = scanner.next().toUpperCase();

							System.out.println("Please key in an index of " + courseCode1 + ": ");
							int index = scanner.nextInt();
							boolean regcheck = scontroller.registerCourse(courseCode1, index, matric);
							if (regcheck) //success
								System.out.println("Registration for " + courseCode1 + ", index " + index + " successful.");
							else //fail: student dont have this index/course
								System.out.println("Registration for " + courseCode1 + ", index " + index + " was not successful.");
							break;
						case 3:
							System.out.println("Please key in Course code: ");
							String courseCode2 = scanner.next().toUpperCase();
							System.out.println("Please key in your index of " + courseCode2 + ": ");
							int index1 = scanner.nextInt();

							boolean deregcheck = scontroller.deregisterCourse(courseCode2, index1, matric);
							if (!deregcheck) //fail: student does not have this index/course
								System.out.println("Deregistration from " + courseCode2 + "," + index1 + " was not succesful."); //success
							else System.out.println(courseCode2 + ", index " + index1 + " is dropped.");
							break;
						case 4:
							System.out.println("Please key in Course code: ");
							String courseCode3 = scanner.next().toUpperCase();
							ArrayList<Pair> vacancyList = new ArrayList<Pair>();
							vacancyList = scontroller.vacancies(courseCode3);
							if(vacancyList == null) System.out.println("The course code you entered does not exist."); 
							else {
								System.out.println("+--------+\n"+
												   "| "+courseCode3+" |\n"+
												   "+-------++--------------+\n"+
												   "| Index | Vacancies     |\n"+
												   "+-------+---------------+");
								for(int i = 0; i<vacancyList.size();i++) {
								System.out.println("| "+Integer.toString(vacancyList.get(i).getL())+" | "+vacancyList.get(i).getRR()+"\t\t|");
								}
								System.out.println("+-------+---------------+");
							}
							break;
						case 5:
							System.out.println("Please key in Course code: ");
							String courseCode5 = scanner.next().toUpperCase();
							System.out.println("Please key in the index of " + courseCode5 + " (vacancy): ");
							int index2 = scanner.nextInt();
							Pair indexVacancy = new Pair();
							indexVacancy = scontroller.indexVacancy(courseCode5, index2);

							if (indexVacancy == null)
								System.out.println("The course code/ index you entered does not exist.");
							else
								System.out.println("Number of vacancies for index " + Integer.toString(indexVacancy.getL()) + " of " + courseCode5 + " : " + indexVacancy.getRR());

							break;
						case 6:
							System.out.println("Please key in Course code: ");
							String courseCode4 = scanner.next().toUpperCase();
							System.out.println("Please key in an index of " + courseCode4 + " that you wish to switch to: ");
							int toIndex = scanner.nextInt();
							boolean check = scontroller.changeIndex(courseCode4, toIndex, matric);
							if (!check) System.out.println("Changing of index was not successful.");
							else System.out.println("Changing of index was successful.");
							break;
						case 7:
							int swopChoice = 999;
							int fromIndex;
							String fromCourse, toMatric, toPW;
							ArrayList<PendingSwop> studentSwopList = new ArrayList<PendingSwop>();

							while (swopChoice != 0) {
								// Prints current swaps that the Student logged in has
								studentSwopList = swopController.getStudentSwopList(matric);
								if (studentSwopList.size() > 0) {
                        		System.out.println("+---------------------------------------+\n"+
                        						   "| List of pending swaps for "+unEntry+"\t|\n"+
                        						   "+--------+-----------+-----------+------+--+\n"+
                        						   "| Course | Swop from | Swop with | Swop to |\n"+
                        						   "+--------+-----------+-----------+---------+");
                            	//System.out.println("Course       Swop from       Swop with       Swop to");
                            	for (PendingSwop value : studentSwopList) {
                            		System.out.println("| "+value.getCourseCode()+" | "+String.valueOf(value.getFromIndex())+"     | "+value.getToMatric() + " | " + String.valueOf(value.getToIndex())+"   |");
                            	}
                            	System.out.println("+--------+-----------+-----------+---------+");
								} else {
									System.out.println("No pending swaps for "+unEntry+":");
								}

								System.out.println("Please select one of the options below: ");
								System.out.println("1. Create a new swap");
								System.out.println("2. Remove an existing swap");
								System.out.println("0. Go back");
								swopChoice = scanner.nextInt();

								switch (swopChoice) {
									case 1:
										System.out.println("Please key in a currently enrolled Course code:");
										fromCourse = scanner.next().toUpperCase();
										System.out.println("Please key in your current index in " + fromCourse);
										fromIndex = scanner.nextInt();
										System.out.println("Please key in the matriculation number of the student you wish to swop with: ");
										toMatric = scanner.next().toUpperCase();
										System.out.println("Please key in course index that you wish to swop to: ");
										toIndex = scanner.nextInt();
										System.out.println("Please key in the password of the student you wish to swop with: ");
										scanner.nextLine();
										toPW = scanner.nextLine().trim();
										swopController.registerSwop(fromCourse, matric, fromIndex, toMatric, toIndex, toPW);
										break;
									case 2:
										System.out.println("Please key in a currently enrolled Course code:");
										fromCourse = scanner.next().toUpperCase();
										System.out.println("Please key in your current index in " + fromCourse);
										fromIndex = scanner.nextInt();
										System.out.println("Please key in the matriculation number of the student you wish to swop with: ");
										toMatric = scanner.next().toUpperCase();
										System.out.println("Please key in course index that you wish to swop to: ");
										toIndex = scanner.nextInt();
										System.out.print("Comfirm deletion (Y/N)?");
										String confirmation = scanner.next().trim().toUpperCase();
										if (confirmation.equals("Y"))
											swopController.dropSwop(fromCourse, matric, fromIndex, toMatric, toIndex);
										else
											continue;
										break;
									default:
										System.out.println("Please select an option above");
								}
							}

							break;
						case 8:
							scontroller.printTimeTable(matric);
							break;
						case 9:
							System.out.println("Please key your waitlisted Course code:");
							String waitCourse = scanner.next().toUpperCase();
							System.out.println("Please key in your waitlisted index in " + waitCourse);
							int waitIndex = scanner.nextInt();
//							boolean checkWait = 
							scontroller.dropFromWaitlist(waitCourse, waitIndex, matric);
//							if (checkWait)
//								System.out.println("Drop from waitlist unsuccessful.");
//							else System.out.println("Drop from waitlist successful.");
							break;
						case 0:
							loginController.logout();
							break;
						default:
							System.out.println("Please enter a valid option");
							break;
					}
				} catch (NullPointerException n){
					System.out.println("You will be redirected to the main menu. Please try again.");
				} catch (InputMismatchException ie){
					System.out.println("Invalid input type.");
					System.out.println("You will be redirected to the main menu. Please try again.");
				}

            } while (true);
        } else
            System.out.println("Error. You are not allowed to access this student menu.");
    }
}

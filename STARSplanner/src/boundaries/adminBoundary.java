package boundaries;

import java.util.ArrayList;

import java.util.InputMismatchException;
import java.util.Scanner;

import controllers.adminController;
import controllers.loginController;
import entities.*;

public class adminBoundary {
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
	 *            Username of user who called this method.
	 */
	public static void display(String unEntry) {
		if (unEntry != null) {
			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("Welcome " + unEntry);
			System.out.println();

			do {
				try 
				{System.out.println("Please select one of the options below: ");
				System.out.println("1. Edit Student Access Period");
				System.out.println("2. Add a Student");
				System.out.println("3. Add a course");
				System.out.println("4. Update a course");
				System.out.println("5. Check available slot for an index number");
				System.out.println("6. Print student list by index number");
				System.out.println("7. Print student list by course");
				System.out.println("8. Print all courses");
				System.out.println("0. Log out");
				while (!scanner.hasNextInt()) {
					scanner.next();
					System.out.println("Please enter valid option:");
				}
				choice = scanner.nextInt();
				switch (choice) {
					case 1:
						System.out.println("Please enter the program of study that you wish to edit: ");
						String POS = scanner.next().toUpperCase();
						System.out.println("Please enter the year of study that you wish to edit: ");
						int YOS = scanner.nextInt();
						System.out.println("Please enter the field you want to edit: ");
						System.out.println("1. Access Period Start Time");
						System.out.println("2. Access Period End Time");
						int update_choice = scanner.nextInt();
						if (update_choice == 1) {
							System.out.println("Please enter the new start time in the format \"yyyy-MM-dd HH:mm\":");
							String dummy = scanner.nextLine();
							String newST = scanner.nextLine();
							AccessDateTime NewST = adminController.editAccessPeriod(POS, YOS, newST, 1);
							if (NewST != null) {
								System.out.format("|%-10s|%-10s|%-20s|%-20s|\n", "Programme ", "Year ", "Access Start ", "Access End ");
								System.out.println("-----------------------------------------------------------------");
								System.out.format("|%-10s|%-10s|%-20s|%-20s|\n", POS, YOS, NewST.getStartAccessPeriod(), NewST.getEndAccessPeriod());
								System.out.println("-----------------------------------------------------------------");
							}
						} else if (update_choice == 2) {
							System.out.println("Please enter the new end time in the format \"yyyy-MM-dd HH:mm\":");
							String dummy = scanner.nextLine();
							String newET = scanner.nextLine();
							AccessDateTime NewET = adminController.editAccessPeriod(POS, YOS, newET, 2);
							if (NewET != null) {
								System.out.format("|%-10s|%-10s|%-20s|%-20s|\n", "Programme ", "Year ", "Access Start ", "Access End ");
								System.out.println("-----------------------------------------------------------------");
								System.out.format("|%-10s|%-10s|%-20s|%-20s|\n", POS, YOS, NewET.getStartAccessPeriod(), NewET.getEndAccessPeriod());
								System.out.println("-----------------------------------------------------------------");
							}
						}
						break;
					case 2:
						String pw1, pw2;
						System.out.println("Please enter the username for the Student: ");
						String S_username = scanner.next().toUpperCase();
						if (!adminController.checkUsername(S_username)) {
							System.out.println("Username already exists. Please enter a new username.");
							break;
						}
						while (true) {
							System.out.println("Please enter the password for the Student: ");
							pw1 = scanner.next().trim();
							System.out.println("Please confirm the password: ");
							pw2 = scanner.next().trim();
							if (!adminController.checkPassword(pw1, pw2)) {
								System.out.println("Error. The passwords you entered were not the same.");
							} else
								break;
						}
						scanner.nextLine();
						System.out.println("Please enter name of the Student:");
						String S_name = scanner.nextLine().toUpperCase();
						char S_gender;
						while (true) {
							System.out.println("Please enter the gender of the Student (Male = 'M', Female = 'F): ");
							S_gender = scanner.next().toUpperCase().charAt(0);
							if (adminController.checkGender(S_gender)) {
								break;
							}
							System.out.println("Invalid gender input. Please try again");
						}
						System.out.println("Please enter matriculation number of the Student: ");
						String S_matric = scanner.next().toUpperCase();
						if (!adminController.checkMatric(S_matric)) {
							System.out.println("Error. Matriculation number already exists.");
							break;
						}
						int year_of_study;
						while(true) {
							System.out.println("Please enter the year that the Student is in: ");
							year_of_study = scanner.nextInt();
							if ((1 <= year_of_study) && (year_of_study <= 5)) 
								break;
							else 
								System.out.println("Year of Study must be between 1 to 5. Please try again.");
						}
						System.out.println("Please enter the programme that the Student is taking: ");
						String programme = scanner.next().toUpperCase();
						System.out.println("Please enter the school that the Student is in: ");
						String school = scanner.next().toUpperCase();
						System.out.println("Please enter the student's nationality: ");
						String nationality = scanner.next();
						System.out.println("Please enter the student's email: ");
						String email = scanner.next();
						if (!adminController.addStudent(S_username, pw1, S_name, S_gender,
									S_matric, year_of_study, programme, school, nationality, email))
							System.out.println("Student is already added!");
						//add a find that checks every thing of a student object
						break;
					case 3:
						while (true) {
							try {
								String c_code;
								while (true) {
									System.out.println("Please enter the new course code: ");
									c_code = scanner.next().toUpperCase();
									boolean courseCheck = adminController.checkIfCourseExists(c_code);
									if(courseCheck) break;
									else 
										System.out.println("The course "+c_code+" already exists.");
								}
								System.out.println("Please enter the school: ");
								String c_sch = scanner.next().toUpperCase();
								System.out.println("Please enter the number of AUs: ");
								int c_AUs = scanner.nextInt();
								System.out.println("Please enter the number of Initial indexes: ");
								int num_indexes = scanner.nextInt();
								ArrayList<Index> c_Indexes = new ArrayList<Index>();

								while (true) {
									try {
										for (int i = 1; i <= num_indexes; i++) {
											System.out.println("Please enter the index " + i + " number: ");
											int i_index = scanner.nextInt();
											System.out.println("Please enter the class size of the index " + i + ": ");
											int i_size = scanner.nextInt();
											System.out.println("Please enter the number of Lessons: ");
											int num_lessons = scanner.nextInt();
											ArrayList<Lesson> i_Lessons = new ArrayList<Lesson>();
											while (true) {
												try {
													for (int j = 1; j <= num_lessons; j++) {
														String l_type, l_week;
														while(true){
															System.out.println("Please enter the type of Lesson " + j + " (LAB/TUTORIAL/LECTURE): ");
															l_type = scanner.next().toUpperCase();
															if(adminController.checkLessonType(l_type))
																break;
															System.out.println("Invalid lesson type. Please try again.");
														}
														System.out.println("Please enter the day of the week of the lesson (0 to 5 where 0-Monday): ");
														int l_day = scanner.nextInt();
														while(true){
															System.out.println("Please enter the week of the lesson (EVEN/ODD/BOTH): ");
															l_week = scanner.next().toUpperCase();
															if (adminController.checkLessonWeek(l_week))
																break;
															System.out.println("Invalid lesson week. Please try again.");
														}
														System.out.println("Start times: 0 - 0830, 1 - 0930, 2 - 1030, 3 - 1130, 4 - 1230, 5-1330" +
																" 6 - 1430, 7 - 1530, 8 - 1630, 9 - 1730, 10 - 1830, 11 - 1930");
														System.out.println("Please enter the start time of the lesson (0 to 11): ");
														int l_time = scanner.nextInt();
														System.out.println("Please enter the duration of the lesson (in hours): ");
														int l_length = scanner.nextInt();
														System.out.println("Please enter the venue of the lesson: ");
														String l_location = scanner.next();
														System.out.println("Please enter the lesson group: ");
														String l_group = scanner.next().toUpperCase();
														i_Lessons.add(adminController.makeLessons(l_type, l_day, l_week, l_time, l_length, l_location, l_group));
													}
													c_Indexes.add(adminController.addInitialIndexes(c_code, i_index, i_size, i_Lessons));
													break;
												} catch (InputMismatchException|IllegalArgumentException ie) {
													System.out.println("Invalid lesson input entered. Please enter all lesson details of this index again.");
													String dummy = scanner.nextLine();
												}
											}
										}
										adminController.addCourse(c_code, c_Indexes, c_AUs, c_sch);
										adminController.printAllCourses();
										break;
									} catch (InputMismatchException|IllegalArgumentException ie) {
										System.out.println("Invalid index input entered. Please enter all index details of this course again.");
										String dummy = scanner.nextLine();
									}
								}
								break;
							} catch (InputMismatchException|IllegalArgumentException ie) {
								System.out.println("Invalid input entered. Please enter course details again.");
								String dummy = scanner.nextLine();
							}
						}
						break;
					case 4:
						System.out.println("Enter the Course Code you want to update.");
						String cc = scanner.next().toUpperCase();
						System.out.println("Enter the attribute you want to update.");
						System.out.println("1. Course Code");
						System.out.println("2. School");
						System.out.println("3. Number of AUs");
						System.out.println("4. Add Index");
						System.out.println("5: Remove Index");
						update_choice = scanner.nextInt();
						switch (update_choice) {
							case 1:
								System.out.println("Enter the new course code: ");
								String new_cc = scanner.next().toUpperCase();
								if (adminController.updateCourseCode(cc, new_cc))
									System.out.println("Course Code updated!");
								else System.out.println("Course update unsuccessful.");
								break;
							case 2:
								System.out.println("Enter the new school: ");
								String new_sch = scanner.next().toUpperCase();
								if (adminController.updateSchool(cc, new_sch)) System.out.println("School updated!");
								else System.out.println("Course to change not found!");
								break;
							case 3:
								System.out.println("Enter the number of AUs: ");
								int new_AUs = scanner.nextInt();
								if (adminController.updateAUs(cc, new_AUs))
									System.out.println("Number of AUs updated!");
								else System.out.println("Course to change not found!");
								break;
							case 4:
								System.out.println("Please enter the index number: ");
								int new_index = scanner.nextInt();
								System.out.println("Please enter the size of the index: ");
								int new_size = scanner.nextInt();
								System.out.println("Please enter the number of Lessons: ");
								int num_lessons = scanner.nextInt();
								ArrayList<Lesson> new_Lessons = new ArrayList<Lesson>();
								for (int i = 0; i < num_lessons; i++) {
									System.out.println("Please enter the type of Lesson (LAB/TUTORIAL/LECTURE): ");
									String type = scanner.next().toUpperCase();
									System.out.println("Please enter the day of the week of the lesson (0 to 5 where 0-Monday): ");
									int day = scanner.nextInt();
									System.out.println("Please enter the week of the lesson (EVEN/ODD/BOTH): ");
									String week = scanner.next().toUpperCase();
									System.out.println("Start times: 0 - 0830, 1 - 0930, 2 - 1030, 3 - 1130, 4 - 1230, 5-1330" +
											" 6 - 1430, 7 - 1530, 8 - 1630, 9 - 1730, 10 - 1830, 11 - 1930");
									System.out.println("Please enter the start time of the lesson (0 to 11): ");
									int time = scanner.nextInt();
									System.out.println("Please enter the duration of the lesson (in hours): ");
									int length = scanner.nextInt();
									System.out.println("Please enter the venue of the lesson: ");
									String location = scanner.next();
									System.out.println("Please enter the lesson group: ");
									String group = scanner.next().toUpperCase();
									new_Lessons.add(adminController.makeLessons(type, day, week, time, length, location, group));
								}
								if (adminController.addIndex(new Index(cc, new_index, new_size, new_Lessons), cc))
									System.out.println("Index added to course.");
								else System.out.println("Course to change not found!");
								break;
							case 5:
								System.out.println("Please enter the index number: ");
								int r_index = scanner.nextInt();
								Index remove_index = new Index();
								remove_index.setIndex(r_index);
								if (adminController.removeIndex(remove_index, cc))
									System.out.println("Index removed from course.");
								else System.out.println("Course to change not found!");
								break;
							default:
								System.out.println("Invalid Choice.");
						}
						break;
					case 5:
						System.out.println("Enter the course of the index you wish to check vacancies for: ");
						cc = scanner.next().toUpperCase();
						System.out.println("Enter the index you wish to check vacancies for: ");
						int index = scanner.nextInt();
						String vacancies = adminController.checkVacancyAdmin(cc, index);
						if (vacancies == null)
							System.out.println("Index to change not found!");
						else
							System.out.println("Number of vacancies of course " + cc + ": " + vacancies);
						break;
					case 6:
						System.out.println("Enter the course of the index you wish to view the students by: ");
						cc = scanner.next().toUpperCase();
						System.out.println("Enter the index you wish to view the students by: ");
						index = scanner.nextInt();
						adminController.printStudentListByIndex(cc, index);
						break;
					case 7:
						System.out.println("Enter the course you wish to view the students by: ");
						cc = scanner.next().toUpperCase();
						adminController.printStudentListByCourse(cc);
						break;
					case 8:
						System.out.println("All existing courses: ");
						adminController.printAllCourses();
						break;
					case 0:
						loginController.logout();
						break;
					default:
						System.out.println("Please enter a valid option");
						break;
					}
				} catch (NullPointerException n) {
					System.out.println("You will be redirected to the main menu. Please try again.");
				} catch (InputMismatchException ie) {
					System.out.println("Invalid input type.");
					System.out.println("You will be redirected to the main menu. Please try again.");
				}
			} while (choice > 0 || choice < 8);
		} else
			System.out.println("Error. You are not allowed to access this admin menu.");
	}
}

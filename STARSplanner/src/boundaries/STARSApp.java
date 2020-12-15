package boundaries;

/**
 * Represents the main boundary class for MySTARS
 * 
 * @author
 *
 */
public class STARSApp {
    public static void main (String[] args) {
        System.out.println(
        		"+----------------- CZ2002 OODP ------------------+\r\n" +
        		"|    _ _  ___  _ _    ___  ___  ___  ___  ___    |\r\n" + 
        		"|   | \\ ||_ _|| | |  / __>|_ _|| . || . \\/ __>   |\r\n" + 
        		"|   |   | | | | ' |  \\__ \\ | | |   ||   /\\__ \\   |\r\n" + 
        		"|   |_\\_| |_| `___'  <___/ |_| |_|_||_\\_\\<___/   |\r\n" + 
        		"|       ___  _    ___  _ _  _ _  ___  ___        |\r\n" + 
        		"|      | . \\| |  | . || \\ || \\ || __>| . \\       |\r\n" + 
        		"|      |  _/| |_ |   ||   ||   || _> |   /       |\r\n" + 
        		"|      |_|  |___||_|_||_\\_||_\\_||___>|_\\_\\       |\r\n" + 
        		"|                                                |\r\n" +
        		"+------------------------------------------------+\r\n" +
        		"           Welcome to NTU STARS Planner!");
        
//        Admin tempAdmin = new Admin("ADMIN1","password","name_1",'M');
//        Admin tempAdmin2 = new Admin("ADMIN2","password","name_2",'F');
//        adminManager adminMgr = new adminManager();
//        adminMgr.create(tempAdmin);
//        adminMgr.create(tempAdmin2);
        
        loginPage.loginDisplay();
    }
}

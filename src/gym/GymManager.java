package gym;

import constants.Constants;
import enums.Time;
import enums.Location;
import fitness_classes.ClassSchedule;
import fitness_classes.FitnessClass;
import member.Family;
import member.Member;
import date.Date;
import member.MemberDatabase;
import member.Premium;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.SortedMap;


/**
 * This class is the user interface for Gym Manager. The client can input commands via the console / terminal to
 * interact with the database and the fitness classes. See {@link #run() run()} for more details on valid commands.<br><br>
 * **Please Note: There is only one memberDataBase instance.**
 * @author Hasnain Ali, Carolette Saguil
 */
public class GymManager {
    /**
     * Default constructor; not used.
     */
    public GymManager() {

    }

    /**
     * Adds a member to the member database.
     * Fails if: Date of birth is invalid, date of birth is in the future, member is younger than 18 years old, expiration
     * date is invalid, location is invalid, or if member is already in the database.
     * @param lineParts String array for command inputted; allows for easier parsing of command.
     * @param memberDatabase The member database that was created for all current members. Only one exists.
     */
    private void addMember (String[] lineParts, MemberDatabase memberDatabase) {

        Member member = null;
        Date expire = new Date();

        if (lineParts[0].equals("A")) {
            expire.incrementMonth(3);
            member = new Member(lineParts[1], lineParts[2], new Date(lineParts[3]), expire,
                    Location.returnEnumFromString(lineParts[4]));
        } else if (lineParts[0].equals("AF")) {
            expire.incrementMonth(3);
            member = new Family(lineParts[1], lineParts[2], new Date(lineParts[3]), expire,
                    Location.returnEnumFromString(lineParts[4]), Constants.FAMILY_GUEST_PASSES);
        } else if (lineParts[0].equals("AP")) {
            expire.incrementYear(1);
            member = new Premium(lineParts[1], lineParts[2], new Date(lineParts[3]), expire,
                    Location.returnEnumFromString(lineParts[4]), Constants.PREMIUM_GUEST_PASS);
        }

        assert member != null;
        if (!member.getDob().isValid()) {
            System.out.printf("DOB %s: invalid calendar date!\n", member.getDob());
            return;
        } else if (!member.getDob().checkIfDobIsFuture()) {
            System.out.printf("DOB %s: cannot be today or a future date.\n", member.getDob());
            return;
        } else if (!member.getDob().checkMemberAge()) {
            System.out.printf("DOB %s: must be 18 or older to join!\n", member.getDob());
            return;
        } else if (!member.getExpire().isValid()) {
            System.out.printf("Expiration Date %s: invalid calendar date!\n", member.getExpire());
            return;
        } else if (member.getLocation() == null) {
            System.out.printf("%s: invalid location!\n", lineParts[4]);
            return;
        }

        if (memberDatabase.add(member)) {
            System.out.printf("%s %s added.\n", member.getFname(), member.getLname());
        } else {
            System.out.printf("%s %s is already in the database.\n", member.getFname(), member.getLname());
        }
    }

    private boolean loadFitnessClasses(ClassSchedule classSchedule) {
        try {
            File file = new File(Constants.CLASS_SCHEDULE_FROM_CONTENT_ROOT);
            Scanner sc = new Scanner(file);
            System.out.print("-Fitness classes loaded-");

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(" ");
                System.out.println(Arrays.toString(line));

                String className = line[0];
                String instructorName = line[1];
                Time time = Time.returnTimeEnumFromString(line[2]);
                Location location = Location.returnEnumFromString(line[3]);

                FitnessClass fitnessClass = new FitnessClass(time, className, instructorName, location);
                System.out.println(fitnessClass);

                classSchedule.addClass(fitnessClass);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return false;
        }

        System.out.print("-end of class list-");

        return true;
    }
    
    private void printFitnessClassSchedule(ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
        System.out.println("-Fitness classes-");

        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            System.out.print(fitnessClasses[x]);
        }
    }

    /**
     * Removes a member from the database.
     * If the member is not in the database, they will fail to be removed. If they are, they will be removed successfully.
     * @param lineParts String array for command inputted; allows for easier parsing of command.
     * @param memberDatabase The member database that was created for all current members.
     */
    private void removeMember(String[] lineParts, MemberDatabase memberDatabase) {
        Member member;
        String fName = lineParts[1];
        String lName = lineParts[2];
        Date dob = new Date(lineParts[3]);
        member = new Member(fName, lName, dob, null, null);
        Member memberToRemove = memberDatabase.getMember(memberDatabase.find(member));
        if (memberToRemove == null) {
            System.out.printf("%s %s is not in the database.\n", lineParts[1], lineParts[2]);
            return;
        } else if (memberDatabase.remove(memberToRemove)) {
            System.out.printf("%s %s removed.\n", lineParts[1], lineParts[2]);
            return;
        }

        System.out.printf("%s %s was unable to be removed.\n", lineParts[1], lineParts[2]);
    }

/*    *//**
     * Based on {@code className}, the appropriate index for the class is returned.
     * @param className The name of the class to return the index from.
     * @return The index of the class based on the {@code className}. -1 is the class was not found.
     */
    private int returnClassIndex(ClassSchedule classSchedule, String className, String instructorName, Location location) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
        FitnessClass fitnessClass = new FitnessClass(null, className, instructorName, location);

        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            if (fitnessClass.equals(fitnessClasses[x])) {
                return x;
            }
        }

        return Constants.NOT_FOUND;
    }

    /**
     * Checks a member into a particular fitness class. The class must exist, they must have a valid date of birth,
     * exist in the member database, not have an expired membership, not have already checked in, and not have a time conflict with another class.
     * @param lineParts String array for command inputted; allows for easier parsing of command.
     * @param memberDatabase The member database that was created for all current members.
     * @param classSchedule Array consisting of all fitness classes. fitnessClass[0] = Pilates, fitnessClass[1] = Spinning, fitnessClass[2] = Cardio
     */
    private void checkInMember(String[] lineParts, MemberDatabase memberDatabase, ClassSchedule classSchedule) {
        String className = lineParts[1];
        String instructorName = lineParts[2];
        Location location = Location.returnEnumFromString(lineParts[3]);
        String fName = lineParts[4];
        String lName = lineParts[5];
        Date dob = new Date(lineParts[6]);

        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();

        int classIndex = returnClassIndex(classSchedule, className, instructorName, location);

        if (classIndex == Constants.NOT_FOUND) {
            System.out.printf("%s class does not exist.\n", lineParts[1]);
            return;
        } else if (!dob.isValid()) {
            System.out.printf("DOB %s: invalid calendar date!\n", dob);
            return;
        }

        Member member = new Member(fName, lName, dob, null, null);
        Member memberToCheckIn = memberDatabase.getMember(memberDatabase.find(member));

        if (memberToCheckIn == null) {
            System.out.printf("%s %s %s does not exist in database.\n", fName, lName, dob);
            return;
        } else if (fitnessClasses[classIndex].checkIfMemberExpired(memberToCheckIn)) {
            System.out.printf("%s %s %s membership expired.\n", fName, lName, memberToCheckIn.getDob());
            return;
        } else if ( !(fitnessClasses[classIndex].checkForTimeConflict(memberToCheckIn)) ) {
            System.out.printf("%s time conflict -- %s %s has already checked into %s\n",
                    fitnessClasses[classIndex].getClassName(), fname, lname, fitnessClasses[classIndex].findTimeConflictClass(memberToCheckIn));
            return;
        } else if ( !(fitnessClasses[classIndex].findMemberInClass(memberToCheckIn) == Constants.NOT_FOUND) ){
            System.out.printf("%s %s has already checked into %s.\n", fname, lname, fitnessClasses[classIndex].getClassName());
            return;
        }

        fitnessClasses[classIndex].checkIn(memberToCheckIn);

        System.out.printf("%s %s checked into %s.\n", memberToCheckIn.getFname(), memberToCheckIn.getLname(), fitnessClasses[classIndex].getClassName());
    }

    /**
     * Drops a particular member from the class. They must be in the class, have a valid date of birth, and be wanting to drop a class that exists.
     * @param lineParts String array for command inputted; allows for easier parsing of command.
     * @param memberDatabase The member database that was created for all current members.
     * @param fitnessClasses Array consisting of all fitness classes. fitnessClass[0] = Pilates, fitnessClass[1] = Spinning, fitnessClass[2] = Cardio
     */
/*    private void dropClass(String[] lineParts, MemberDatabase memberDatabase, FitnessClass[] fitnessClasses) {
        int classIndex = returnClassIndex(lineParts[1]);
        String fname = lineParts[2];
        String lname = lineParts[3];
        Date dob = new Date(lineParts[4]);

        Member memberToDrop = memberDatabase.getMember(memberDatabase.find(fname, lname, dob));

        if (classIndex == Constants.NOT_FOUND) {
            System.out.printf("%s class does not exist.\n", lineParts[1]);
            return;
        } else if (!dob.isValid()) {
            System.out.printf("DOB %s: invalid calendar date!\n", dob);
            return;
        } else if (memberToDrop == null || fitnessClasses[classIndex].findMemberInClass(memberToDrop) == Constants.NOT_FOUND) {
            System.out.printf("%s %s is not a participant in %s\n", fname, lname, fitnessClasses[classIndex].getClassName());
            return;
        }

        fitnessClasses[classIndex].dropClass(memberToDrop);
        System.out.printf("%s %s dropped %s\n", fname, lname, lineParts[1]);
    }*/

    /**
     * Starts running the Gym Manager UI blocking while waiting for valid commands. Using the String.split() method,
     * this method parses any input given to it via standard input using Java Scanner. Valid inputs are as follows:
     *
     * <br>
     * <br>
     * <i>***Please Note do not include | in your commands, these are simply here for readability purposes***</i>
     * <br>
     * <br>
     *
     * <ul>
     *     <li><b>A | First_Name | Last_Name | Date_Of_Birth | Expiration_Date | Location</b></li>
     *          <ul>
     *              <li>Appends member to database</li>
     *          </ul>
     *
     *     <li><b>R | First_Name | Last_Name | Date_Of_Birth</b></li>
     *          <ul>
     *              <li>Removes member from the database.</li>
     *          </ul>
     *
     *     <li><b>P</b></li>
     *          <ul>
     *              <li>Print current Member Database</li>
     *          </ul>
     *
     *      <li><b>PC</b></li>
     *      <ul>
     *          <li>Print current Member Database sorted by county</li>
     *      </ul>
     *
     *      <li><b>PN</b></li>
     *      <ul>
     *          <li>Print current Member Database sorted by last name, first name</li>
     *      </ul>
     *
     *      <li><b>PD</b></li>
     *      <ul>
     *          <li>Print current Member Database sorted by membership expiration dates.</li>
     *      </ul>
     *
     *      <li><b>S</b></li>
     *      <ul>
     *          <li>Print fitness class schedule</li>
     *      </ul>
     *
     *      <li><b>C | Class_Name | First_Name | Last_Name | Date_Of_Birth</b></li>
     *      <ul>
     *          <li>Check a person in the database into a particular existing fitness class</li>
     *      </ul>
     *
     *       <li><b>D | Class_Name | First_Name | Last_Name | Date_Of_Birth</b></li>
     *       <ul>
     *           <li>Drops member from a particular fitness class.</li>
     *       </ul>
     *
     *       <li><b>Q</b></li>
     *       <ul>
     *          <li>Quits/Terminates user interface (kills program).</li>
     *       </ul>
     * </ul>
     */
    public void run() {
        System.out.println("Gym Manager Running...");
        Scanner sc = new Scanner(System.in);
        String input;
        MemberDatabase memberDatabase = new MemberDatabase();
        ClassSchedule classSchedule = new ClassSchedule();

        while ( !((input = sc.nextLine()).equals("Q")) ) {
            String[] lineParts = input.split(" ");

            if (lineParts[0].equals("A") || lineParts[0].equals("AF") || lineParts[0].equals("AP")) { //add member
                addMember(lineParts, memberDatabase);
            } else if (lineParts[0].equals("PF")) {
                memberDatabase.printWithMembershipFee();
            } else if (lineParts[0].equals("R")) {
                removeMember(lineParts, memberDatabase);
            } else if (lineParts[0].equals("P")) {
                memberDatabase.print();
            } else if (lineParts[0].equals("PC")) {
                memberDatabase.printByCounty();
            } else if (lineParts[0].equals("PN")) {
                memberDatabase.printByName();
            } else if (lineParts[0].equals("PD")) {
                memberDatabase.printByExpirationDate();
            } else if (lineParts[0].equals("S")) {
                this.printFitnessClassSchedule(classSchedule);
            } else if (lineParts[0].equals("C")) {
                checkInMember(lineParts, memberDatabase, fitnessClasses);
            } else if (lineParts[0].equals("CG")) {
                continue;
            } else if (lineParts[0].equals("D")) {
                //dropClass(lineParts, memberDatabase, fitnessClasses);
            } else if (lineParts[0].equals("DG")) {
                continue;
            } else if (lineParts[0].equals("LS")) {
                loadFitnessClasses(classSchedule);
            } else if (lineParts[0].equals("")) {
                continue;
            } else {
                System.out.printf("%s is an invalid command!\n", lineParts[0]);
            }

            System.out.println();
        }

        System.out.println("Gym Manager terminated.");
    }
}

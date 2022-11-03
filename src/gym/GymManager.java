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
import java.util.Objects;
import java.util.Scanner;


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
            expire.incrementMonth(Constants.MONTHS_SET_TO_EXPIRE);
            member = new Member(lineParts[1], lineParts[2], new Date(lineParts[3]), expire,
                    Location.returnEnumFromString(lineParts[4]));
        } else if (lineParts[0].equals("AF")) {
            expire.incrementMonth(Constants.MONTHS_SET_TO_EXPIRE);
            member = new Family(lineParts[1], lineParts[2], new Date(lineParts[3]), expire,
                    Location.returnEnumFromString(lineParts[4]), Constants.FAMILY_GUEST_PASSES);
        } else if (lineParts[0].equals("AP")) {
            expire.incrementYear(Constants.YEAR_SET_TO_EXPIRE);
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
            System.out.printf("%s: invalid location!", lineParts[4]);
            return;
        }

        if (memberDatabase.add(member)) {
            System.out.printf("%s %s added.\n", member.getFname(), member.getLname());
        } else {
            System.out.printf("%s %s is already in the database.\n", member.getFname(), member.getLname());
        }
    }

    /**
     * Loads in fitness class to class schedule from the file classSchedule.txt.
     * Prints out fitness classes loaded in.
     * @param classSchedule Class schedule to fill.
     * @return true if fitness classes are loaded in, false otherwise.
     */
    private boolean loadFitnessClasses(ClassSchedule classSchedule) {
        try {
            File file = new File(Constants.CLASS_SCHEDULE_FROM_CONTENT_ROOT);
            Scanner sc = new Scanner(file);
            System.out.println("-Fitness classes loaded-");

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split("\\s+");

                String className = line[0];
                String instructorName = line[1];
                Time time = Time.returnTimeEnumFromTimeOfDay(line[2]);
                Location location = Location.returnEnumFromString(line[3]);

                FitnessClass fitnessClass = new FitnessClass(time, className, instructorName, location);
                System.out.print(fitnessClass);

                classSchedule.addClass(fitnessClass);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return false;
        }

        System.out.print("-end of class list-\n");

        return true;
    }

    /**
     * Loads in historical members to member database from memberList.txt.
     * Prints out members loaded in.
     * @param memberDatabase Member Database to fill.
     * @return true if members are loaded in, false otherwise.
     */
    public boolean loadHistoricalMembers(MemberDatabase memberDatabase) {
        try {
            File file = new File(Constants.MEMBER_LIST_FROM_CONTENT_ROOT);
            Scanner sc = new Scanner(file);
            System.out.println("-List of Members Loaded-");

            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split("\\s+");
                Member member = new Member(line[0], line[1], new Date(line[2]), new Date(line[3]), Location.returnEnumFromString(line[4]));
                memberDatabase.add(member);
                System.out.println(member);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            return false;
        }

        System.out.print("-end of list-\n");

        return true;
    }

    /**
     * Prints out class schedule.
     * @param classSchedule Class schedule to be printed.
     */
    private void printFitnessClassSchedule(ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();

        if (classSchedule.getNumClasses() <= 0) {
            System.out.println("Fitness class schedule is empty.");
            return;
        }

        System.out.println("-Fitness classes-");

        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            System.out.print(fitnessClasses[x]);
        }
        System.out.println("-end of class list-");
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

    /**
     * @param location Location to check.
     * @return Returns whether the location is valid based on String input.
     */
    private boolean checkValidLocation(String location) {
        return Location.returnEnumFromString(location) != null;
    }

    /**
     * Checks if class name is valid.
     * @param className Class name to check.
     * @param classSchedule Class Schedule to check.
     * @return true if class name is in class schedule, false otherwise.
     */
    private boolean checkValidClassName(String className, ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            if (fitnessClasses[x].getClassName().equalsIgnoreCase(className)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param instructorName Name of instructor
     * @param classSchedule Schedule of all fitness classes
     * @return Returns whether instructor name is valid.
     */
    private boolean checkValidInstructor(String instructorName, ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            if (fitnessClasses[x].getInstructorName().equalsIgnoreCase(instructorName)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param classSchedule Schedule of classes
     * @param className Class name
     * @param instructorName Instructors name
     * @param location Location
     * @return Returns index of the specific class in {@code classSchedule}.
     */
    private int returnClassIndex(ClassSchedule classSchedule, String className, String instructorName, Location location) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
        FitnessClass fitnessClass = new FitnessClass(null, className, instructorName, location);

        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            if (fitnessClass.equalsNonVerbose(fitnessClasses[x])) {
                return x;
            }
        }

        return Constants.NOT_FOUND;
    }

    /**
     * @param classSchedule Schedule of all fitness classes
     * @param memberToAdd The member we want to check for a time conflict
     * @param fitnessClass The fitness class desired to check in the {@code classSchedule} for a time conflict
     * @return The fitness class that has a time conflict in {@code fitnessClass}.
     */
    private FitnessClass checkForTimeConflict(ClassSchedule classSchedule, Member memberToAdd, FitnessClass fitnessClass) {
        Time time = Time.returnTimeFromString(fitnessClass.getTime());
        FitnessClass[] allClasses = classSchedule.getAllClasses();

        for (int x = 0; x < classSchedule.getNumClasses(); x++) {
            if (fitnessClass.equalsNonVerbose(allClasses[x])) {
                continue;
            }
            if (allClasses[x].findMemberInClass(memberToAdd) != Constants.NOT_FOUND && allClasses[x].getTime().equalsIgnoreCase(time.getTime())) {
                return allClasses[x];
            }
        }

        return null;
    }

    /**
     * @param lineParts The command line input as an array.
     * @param member The member we want to verify
     * @param classSchedule The schedule of all classes.
     * @return Whether the general input (standardized for check in and check out) is valid or not.
     */
    public boolean checkGeneralInput(String[] lineParts, Member member, ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();

        if (!checkValidClassName(lineParts[1], classSchedule)) {
            System.out.printf("%s - class does not exist.\n", lineParts[1]);
            return false;
        } else if (!checkValidInstructor(lineParts[2], classSchedule)) {
            System.out.printf("%s — instructor does not exist.\n", lineParts[2]);
            return false;
        } else if (!checkValidLocation(lineParts[3])) {
            System.out.printf("%s — location does not exist.\n", lineParts[3]);
            return false;
        }

        int classIndex = returnClassIndex(classSchedule, lineParts[1], lineParts[2], Location.returnEnumFromString(lineParts[3]));

        if (classIndex == Constants.NOT_FOUND) {
            System.out.printf("%s class by %s does not exist.\n", lineParts[1], lineParts[2]);
            return false;
        } else if (!(new Date(lineParts[6]).isValid())) {
            System.out.printf("DOB %s: invalid calendar date!\n", new Date(lineParts[6]));
            return false;
        } else if (member == null) {
            System.out.printf("%s %s %s does not exist in database.\n", lineParts[4], lineParts[5], new Date(lineParts[6]));
            return false;
        } else if (fitnessClasses[classIndex].checkIfMemberExpired(member)) {
            System.out.printf("%s %s %s membership expired.\n", lineParts[4], lineParts[5], member.getDob());
            return false;
        }

        return true;
    }

    /**
     * @param lineParts The command line input as an array.
     * @param memberToCheckIn The member to check in, but must first verify.
     * @param classSchedule The schedule of all classses.
     * @return Whether the more specific inputs and conditions of check-in are valid or not. Only meant to be used for checking in.
     */
    private boolean checkFitnessClassesWithCheckInClass(String[] lineParts, Member memberToCheckIn, ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();

        if (!checkGeneralInput(lineParts, memberToCheckIn, classSchedule)) {
            return false;
        }

        int classIndex = returnClassIndex(classSchedule, lineParts[1], lineParts[2], Location.returnEnumFromString(lineParts[3]));

        if (!(memberToCheckIn instanceof Family) && !memberToCheckIn.getLocation().equals(fitnessClasses[classIndex].getLocation())) {
            System.out.printf("%s %s checking in %s - standard membership location restriction", lineParts[4], lineParts[5], Location.returnEnumFromString(lineParts[3]));
            return false;
        } else if ( !(fitnessClasses[classIndex].findMemberInClass(memberToCheckIn) == Constants.NOT_FOUND) ) {
            System.out.printf("%s %s has already checked into %s.\n", lineParts[4], lineParts[5], fitnessClasses[classIndex].getClassName());
            return false;
        }

        FitnessClass timeConflict = this.checkForTimeConflict(classSchedule, memberToCheckIn, fitnessClasses[classIndex]);

        if (timeConflict != null) {
            System.out.printf("%s time conflict -- %s %s has already checked into %s\n", fitnessClasses[classIndex].getClassName(), lineParts[4], lineParts[5], Objects.requireNonNull(this.checkForTimeConflict(classSchedule, memberToCheckIn, fitnessClasses[classIndex])).getClassName());
            return false;
        }

        return true;
    }


    /**
     * Checks in a member.
     * @param lineParts The command line input as an array.
     * @param memberDatabase The database of all members.
     * @param classSchedule The schedule of all the classes.
     */
    private void checkInMember(String[] lineParts, MemberDatabase memberDatabase, ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
        Member memberToCheckIn = memberDatabase.getMember(memberDatabase.find(new Member(lineParts[4], lineParts[5], new Date(lineParts[6]), null, null)));

        if (!checkFitnessClassesWithCheckInClass(lineParts, memberToCheckIn, classSchedule)) {
            return;
        }

        int classIndex = returnClassIndex(classSchedule, lineParts[1], lineParts[2], Location.returnEnumFromString(lineParts[3]));

        fitnessClasses[classIndex].checkIn(memberToCheckIn);
        System.out.printf("%s %s checked into %s", memberToCheckIn.getFname(), memberToCheckIn.getLname(), fitnessClasses[classIndex]);
    }

    /**
     * Drops a guest from a fitness class and determines on whether they are allowed to and valid to drop out.
     * @param lineParts The command line input as an array.
     * @param memberDatabase Database of all members
     * @param classSchedule Class Schedule
     */
    public void dropGuestFromClass(String[] lineParts, MemberDatabase memberDatabase, ClassSchedule classSchedule) {
        String className = lineParts[1];
        String instructorName = lineParts[2];
        Location location = Location.returnEnumFromString(lineParts[3]);
        String firstName = lineParts[4];
        String lastName = lineParts[5];
        Date dob = new Date(lineParts[6]);

        Member member = new Member(firstName, lastName, dob, null, location);
        member = memberDatabase.getMember(memberDatabase.find(member));

        if (!checkGeneralInput(lineParts, member, classSchedule)) {
            return;
        }

        if (member instanceof Family) {
            FitnessClass[] fitnessClasses = classSchedule.getAllClasses();

            for (int x = 0; x < classSchedule.getNumClasses(); x++) {
                if (fitnessClasses[x].findGuestMemberInClass(member) != Constants.NOT_FOUND &&
                        fitnessClasses[x].getClassName().equalsIgnoreCase(className) &&
                        fitnessClasses[x].getInstructorName().equalsIgnoreCase(instructorName) &&
                        fitnessClasses[x].getLocation().equals(location)) { // If they are in the class

                    ((Family) member).setGuestPasses((short) (((Family) member).getGuestPasses() + 1));
                    fitnessClasses[x].dropGuestMember(member);
                    System.out.printf("%s %s (guest) is done with %s\n", member.getFname(), member.getLname(), fitnessClasses[x].getClassName());
                    return;
                }
            }

            System.out.println("Invalid class!");
        } else {
            System.out.println("Standard Membership - No guests to check out!");
        }
    }

    /**
     * Checks in a guest into a specific class and checks whether they are allowed or not to check in.
     * @param className Class Name
     * @param instructorName Instructor Name
     * @param location Gym Location
     * @param firstName First Name
     * @param lastName Last Name
     * @param dob Date of Birth as Date Object
     * @param memberDatabase Database of all members
     * @param classSchedule Schedule of all classes
     */
    public void checkInGuest(String className, String instructorName, Location location, String firstName, String lastName, Date dob, MemberDatabase memberDatabase, ClassSchedule classSchedule) {
        Member member = new Member(firstName, lastName, dob, null, location);
        member = memberDatabase.getMember(memberDatabase.find(member));

        if (member instanceof Family) { // If member is a family instance, that means it may also be a Premium. Either way, they have guest permissions
            FitnessClass[] fitnessClasses = classSchedule.getAllClasses();
            FitnessClass fitnessClassToCheckInto;

            if (((Family) member).getGuestPasses() <= 0) {
                System.out.printf("%s %s ran out of guest passes\n", member.getFname(), member.getLname());
                return;
            }

            if (!member.getLocation().equals(location)) {
                System.out.printf("%s %s Guest checking in %s - guest location restricted\n", firstName, lastName, location);
                return;
            }

            for (int x = 0; x < classSchedule.getNumClasses(); x++) {
                if (fitnessClasses[x].getClassName().equalsIgnoreCase(className) &&
                        fitnessClasses[x].getInstructorName().equalsIgnoreCase(instructorName) && fitnessClasses[x].getLocation().equals(location)) {
                    fitnessClassToCheckInto = fitnessClasses[x];

                    ((Family) member).setGuestPasses((short) (((Family) member).getGuestPasses() - 1));
                    fitnessClassToCheckInto.checkInGuestMember(member);
                    System.out.printf("%s %s (guest) checked into %s\n", member.getFname(), member.getLname(), fitnessClasses[x]);
                    return;
                }
            }

            System.out.println("Invalid class!");
        } else {
            System.out.println("Standard membership - guest check-in is not allowed.");
        }
    }

    /**
     * Drops a particular member from the class. They must be in the class, have a valid date of birth, and be wanting to drop a class that exists.
     * @param lineParts String array for command inputted; allows for easier parsing of command.
     * @param memberDatabase The member database that was created for all current members.
     * @param classSchedule Array consisting of all fitness classes. fitnessClass[0] = Pilates, fitnessClass[1] = Spinning, fitnessClass[2] = Cardio
     */
    private void dropClass(String[] lineParts, MemberDatabase memberDatabase, ClassSchedule classSchedule) {
        FitnessClass[] fitnessClasses = classSchedule.getAllClasses();

        Member memberToRemove = memberDatabase.getMember(memberDatabase.find(new Member(lineParts[4], lineParts[5], new Date(lineParts[6]), null, null)));

        if (!checkGeneralInput(lineParts, memberToRemove, classSchedule)) {
            return;
        }

        int classIndex = returnClassIndex(classSchedule, lineParts[1], lineParts[2], Location.returnEnumFromString(lineParts[3]));

        if (classIndex == Constants.NOT_FOUND) {
            System.out.println("Class Not Found");
            return;
        }

        if (classSchedule.getSpecificClass(classIndex).findMemberInClass(memberToRemove) == Constants.NOT_FOUND) {
            System.out.printf("%s %s did not check in\n", memberToRemove.getFname(), memberToRemove.getLname());
            return;
        }


        fitnessClasses[classIndex].dropClass(memberToRemove);
        System.out.printf("%s %s is done with %s", memberToRemove.getFname(), memberToRemove.getLname(), fitnessClasses[classIndex].getClassName());
    }

    /**
     * Starts running the Gym Manager UI blocking while waiting for valid commands. Using the {@code String.split()} method,
     * this method parses any input given to it via standard input using Java Scanner. Valid inputs are as follows:
     *
     * <br>
     * <br>
     * <i>***Please Note do not include | in your commands, these are simply here for readability purposes***</i>
     * <br>
     * <br>
     *
     * <ul>
     *     <li><b>A | First_Name | Last_Name | Date_Of_Birth | Location</b></li>
     *          <ul>
     *              <li>Appends standard member to database</li>
     *          </ul>
     *
     *     <li><b>AF | First_Name | Last_Name | Date_Of_Birth | Location</b></li>
     *          <ul>
     *              <li>Appends family member to database</li>
     *          </ul>
     *
     *     <li><b>AP | First_Name | Last_Name | Date_Of_Birth | Location</b></li>
     *          <ul>
     *              <li>Appends premium member to database</li>
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
     *      <li><b>PF</b></li>
     *          <ul>
     *              <li>Print current Member Database with membership fee</li>
     *          </ul>
     *
     *      <li><b>PC</b></li>
     *          <ul>
     *              <li>Print current Member Database sorted by county</li>
     *          </ul>
     *
     *      <li><b>PN</b></li>
     *          <ul>
     *              <li>Print current Member Database sorted by last name, first name</li>
     *          </ul>
     *
     *      <li><b>PD</b></li>
     *          <ul>
     *              <li>Print current Member Database sorted by membership expiration dates.</li>
     *          </ul>
     *
     *      <li><b>S</b></li>
     *          <ul>
     *              <li>Print fitness class schedule</li>
     *          </ul>
     *
     *      <li><b>C | Class_Name | Instructor_Name | Location |First_Name |Last_Name | Date_Of_Birth</b></li>
     *          <ul>
     *              <li>Check a member in the database into a particular existing fitness class</li>
     *          </ul>
     *
     *      <li><b>CG | Class_Name | Instructor_Name | Location |First_Name |Last_Name | Date_Of_Birth</b></li>
     *          <ul>
     *              <li>Check a guest of a family or premium member in the database into a particular existing fitness class</li>
     *          </ul>
     *
     *      <li><b>D | Class_Name | Instructor_Name | Location | First_Name | Last_Name | Date_Of_Birth</b></li>
     *          <ul>
     *              <li>Drops member from a particular fitness class.</li>
     *          </ul>
     *
     *       <li><b>DG | Class_Name | Instructor_Name | Location | First_Name | Last_Name | Date_Of_Birth</b></li>
     *          <ul>
     *              <li>Drops guest of a family member from a particular fitness class.</li>
     *          </ul>
     *
     *       <li><b>LS</b></li>
     *          <ul>
     *              <li>Loads in fitness classes to class schedule from classSchedule.txt.</li>
     *          </ul>
     *
     *       <li><b>LM</b></li>
     *          <ul>
     *              <li>Loads in historical members to member database from memberList.txt.</li>
     *          </ul>
     *
     *       <li><b>Q</b></li>
     *          <ul>
     *              <li>Quits/Terminates user interface (kills program).</li>
     *          </ul>
     * </ul>
     */
    public void run() {
        System.out.println("Gym Manager Running...");
        Scanner sc = new Scanner(System.in);
        String input;
        MemberDatabase memberDatabase = new MemberDatabase();
        ClassSchedule classSchedule = new ClassSchedule();

        while ( !((input = sc.nextLine()).equals("Q")) ) {
            String[] lineParts = input.split("\\s+");

            if (lineParts[0].equals("A") || lineParts[0].equals("AF") || lineParts[0].equals("AP")) {
                this.addMember(lineParts, memberDatabase);
            } else if (lineParts[0].equals("R")) {
                this.removeMember(lineParts, memberDatabase);
            } else if (lineParts[0].equals("PF")) {
                memberDatabase.printWithMembershipFee();
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
                checkInMember(lineParts, memberDatabase, classSchedule);
            } else if (lineParts[0].equals("CG")) {
                this.checkInGuest(lineParts[1], lineParts[2], Location.returnEnumFromString(lineParts[3]), lineParts[4], lineParts[5], new Date(lineParts[6]), memberDatabase, classSchedule);
            } else if (lineParts[0].equals("D")) {
                dropClass(lineParts, memberDatabase, classSchedule);
            } else if (lineParts[0].equals("DG")) {
                this.dropGuestFromClass(lineParts, memberDatabase, classSchedule);
            } else if (lineParts[0].equals("LS")) {
                this.loadFitnessClasses(classSchedule);
            } else if (lineParts[0].equals("LM")) {
                this.loadHistoricalMembers(memberDatabase);
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

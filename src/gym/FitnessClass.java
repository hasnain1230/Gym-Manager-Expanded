package gym;

import enums.Time;
import member.Member;
import date.Date;
import constants.Constants;

/**
 * This class defines a fitness class and has access to all other fitness classes that exist.
 * The fitness classes are pre-defined and constant, but the properties of those fitness classes like the people in those
 * fitness classes is variable. This class facilitates that entire process.
 * @author Hasnain Ali, Carolette Saguil
 */
public class FitnessClass {
    /**
     * Corresponding to {@code membersInClass}; stores how many people are in each class corresponding to the main {@code membersInClass} database.
     */
    private static int[] classSize;
    /**
     * Stores all the members in all the fitness classes.
     * <ul>
     *     <li>membersInClass[0] stores Pilates class members</li>
     *     <li>membersInClass[1] stores Spinning class members</li>
     *     <li>membersInClass[2] stores Cardio class members</li>
     * </ul>
     *
     * Each index stores in membersInClass[index] stores the members in that class.
     */
    private static Member[][] membersInClass;
    /**
     * Stores the current FitnessClass's instance Time enum for the specific class. Effectively, this lets the current
     * FitnessClass instance store the name of the current fitness class.
     */
    private final Time FITNESS_CLASS;
    /**
     * Stores all the Time enums for all the FitnessClasses. This is effectively, so we can get the name of any time conflicting
     * classes.
     */
    private final Time[] ALL_CLASSES;

    /**
     * Initializes a 2D static array for the members in the class. Creates a {@code membersInClass} 2D array for
     * {@code membersInClass[x]} where {@code x} is the class index for the fitness class and {@code membersInClass[x][y]}
     * where y is the index of the member in that particular fitness class. Thus, for example, {@code membersInClass[0][0]}
     * represents the first member in the Pilates class since {@code membersInClass[0]} represents all the students in
     * Pilates.
     *      <ul>
     *          <b>membersInClass[0] = Pilates, membersInClass[1] = Spinning, membersInClass[2] = Cardio</b>
     *      </ul>
     * This constructor stores {@code time} in the class too for future use. Additionally, this class stores how big
     * each class in the {@code membersInClass} database is so far, in case it need to dynamically grow the class later.
     * Additionally, this class stores all the times defined in {@code Time enum} to be used for later. The indexes for each
     * class are the same as above where 0 is Pilates, 1 is Spinning, and 2 is Cardio. These values do not change throughout the program.
     * @param time Time enum for specifically designed for the fitness class.
     */
    public FitnessClass(Time time) {
        membersInClass = new Member[Constants.NUMBER_OF_CLASSES][Constants.ARRAY_DEFAULT_SIZE];
        this.FITNESS_CLASS = time;
        classSize = new int[Constants.NUMBER_OF_CLASSES];

        this.ALL_CLASSES = new Time[Constants.NUMBER_OF_CLASSES];
        this.ALL_CLASSES[0] = Time.PILATES;
        this.ALL_CLASSES[1] = Time.SPINNING;
        this.ALL_CLASSES[2] = Time.CARDIO;
    }

    /**
     * Grows the particular fitness class database for the current fitness class when the current database is full.
     * Size limited by JVM memory.
     */
    private void grow() {
        Member[] newMembersInClass = new Member[classSize[this.FITNESS_CLASS.getClassIndex()] + Constants.ARRAY_INCREMENT_SIZE];

        for (int x = 0; x < classSize[this.FITNESS_CLASS.getClassIndex()]; x++) {
            newMembersInClass[x] = membersInClass[this.FITNESS_CLASS.getClassIndex()][x];
        }

        membersInClass[this.FITNESS_CLASS.getClassIndex()] = newMembersInClass;
    }

    /**
     * @return Name of Fitness Class.
     */
    public String getClassName() {
        return this.FITNESS_CLASS.getClassName();
    }

    /**
     * Looks for member in the corresponding fitness class database.
     * @param member Member to find the database index for.
     * @return index of the member in the fitness class database, -1 otherwise.
     */
    public int findMemberInClass(Member member) {
        for (int i = 0; i < classSize[this.FITNESS_CLASS.getClassIndex()]; i++) {
            if (membersInClass[this.FITNESS_CLASS.getClassIndex()][i] != null && membersInClass[this.FITNESS_CLASS.getClassIndex()][i].equals(member)) {
                return i;
            }
        }

        return Constants.NOT_FOUND;
    }

    /**
     * Checks if membership has expired. <br> **Please note, if membership expires today, the membership is considered expired**
     * @param member The member to check for an expired membership.
     * @return true if membership has expired, false otherwise.
     */
    public boolean checkIfMemberExpired(Member member) {
        // if expiration date has same date as today or is before today return true because membership is expired
        return member.getExpire().compareTo(new Date()) <= 0;
    }

    /**
     * Finds the class name of the {@code member} that has the time conflict.
     * @param member The member that has the particular time conflict.
     * @return The class name that has the time conflict for {@code member}. null if that member was not found. Should be used
     * in conjunction with {@code checkForTimeConflict()}
     */
    public String findTimeConflictClass(Member member) {
        for (int x = 0; x < membersInClass.length; x++) {
            for (int y = 0; y < membersInClass[x].length; y++) {
                if (this.FITNESS_CLASS.getClassIndex() == x) {
                    continue;
                }

                if (membersInClass[x][y] != null && membersInClass[x][y].equals(member) && this.ALL_CLASSES[x].getTime().equals(this.FITNESS_CLASS.getTime())) {
                    return Time.returnEnumFromIndex(x).getClassName();
                }
            }
        }

        return null;
    }

    /**
     * Checks if there is a time conflict.
     * @param member Member to check if they have a time conflict.
     * @return <b>false</b> if there is a time conflict; <b>true</b> otherwise.
     */
    public boolean checkForTimeConflict(Member member) {
        for (int x = 0; x < membersInClass.length; x++) {
            for (int y = 0; y < membersInClass[x].length; y++) {
                if (this.FITNESS_CLASS.getClassIndex() == x) {
                    continue;
                }

                if (membersInClass[x][y] != null && membersInClass[x][y].equals(member) && this.ALL_CLASSES[x].getTime().equals(this.FITNESS_CLASS.getTime())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks a member into a fitness class database. If that classes database is filled, this method will grow that
     * class database dynamically without any effort from the client / driver.
     * @param member Member to check into the particular fitness class database.
     * @return <b>true</b> if the member was successfully checked in; <b>false</b> otherwise.
     */
    public boolean checkIn(Member member) { // Assuming that they allowed to do this if they are calling this method.
        if (!checkIfMemberExpired(member) && findMemberInClass(member) == Constants.NOT_FOUND) { //FIXME: add back time conflict when sure it's working
            if (classSize[this.FITNESS_CLASS.getClassIndex()] == membersInClass[this.FITNESS_CLASS.getClassIndex()].length) {
                this.grow();
            }


            membersInClass[this.FITNESS_CLASS.getClassIndex()][classSize[this.FITNESS_CLASS.getClassIndex()]] = member;
            classSize[this.FITNESS_CLASS.getClassIndex()]++;
            return true;
        }

        return false;
    }

    /**
     * Drops member from the fitness class database. Fails if member is not found with no return type.
     * @param member Member to drop from class
     */
    public void dropClass(Member member) {
        int index = this.findMemberInClass(member);

        if (index == Constants.NOT_FOUND) {
            return;
        }

        for (int i = index; i < classSize[this.FITNESS_CLASS.getClassIndex()] - 1; i++) {
            membersInClass[this.FITNESS_CLASS.getClassIndex()][i] = membersInClass[this.FITNESS_CLASS.getClassIndex()][i + 1];
        }

        membersInClass[this.FITNESS_CLASS.getClassIndex()][classSize[this.FITNESS_CLASS.getClassIndex()] - 1] = null;
        classSize[this.FITNESS_CLASS.getClassIndex()]--;
    }

    /**
     * Prints fitness class schedule. If class is empty, no participants are printed. If class has participants,
     * those members and their membership related information is printed.
     */
    public static void printClassSchedule() {
        System.out.println("-Fitness classes-");

        for (int x = 0; x < membersInClass.length; x++) {
            if (x == 0) {
                Time time = Time.PILATES;
                System.out.printf("%s - %s %s\n", time.getClassName(), time.getInstructor(), time.getTime());
            } else if (x == 1) {
                Time time = Time.SPINNING;
                System.out.printf("%s - %s %s\n", time.getClassName(), time.getInstructor(), time.getTime());
            } else if (x == 2) {
                Time time = Time.CARDIO;
                System.out.printf("%s - %s %s\n", time.getClassName(), time.getInstructor(), time.getTime());
            }

            if (classSize[x] > 0) {
                System.out.println("\t** participants **");
            }

            for (int y = 0; y < classSize[x]; y++) {
                System.out.printf("\t %s\n", membersInClass[x][y]);
            }
        }
    }
}



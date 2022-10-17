package fitness_classes;

import enums.Location;
import enums.Time;
import member.Member;
import date.Date;
import constants.Constants;
import java.util.ArrayList;

/**
 * This class defines a fitness class and has access to all other fitness classes that exist.
 * The fitness classes are pre-defined and constant, but the properties of those fitness classes like the people in those
 * fitness classes is variable. This class facilitates that entire process.
 * @author Hasnain Ali, Carolette Saguil
 */
public class FitnessClass extends ClassSchedule {
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
    private ArrayList<Member> membersInClass;
    /**
     * Stores the current FitnessClass's instance Time enum for the specific class. Effectively, this lets the current
     * FitnessClass instance store the name of the current fitness class.
     */
    private final Time TIME;
    private final String CLASS_NAME;
    private final String INSTRUCTOR_NAME;
    private final Location LOCATION;


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
    public FitnessClass(Time time, String className, String instructorName, Location location) {
        this.membersInClass = new ArrayList<>();
        this.TIME = time;
        this.CLASS_NAME = className;
        this.INSTRUCTOR_NAME = instructorName;
        this.LOCATION = location;
    }

    public String getTime() {
        return this.TIME.getTime();
    }

    /**
     * @return Name of Fitness Class.
     */
    public String getClassName() {
        return this.CLASS_NAME;
    }

    public String getInstructorName() {
        return this.INSTRUCTOR_NAME;
    }

    public ArrayList<Member> getMembersInClass() {
        return this.membersInClass;
    }

    /**
     * Looks for member in the corresponding fitness class database.
     * @param member Member to find the database index for.
     * @return index of the member in the fitness class database, -1 otherwise.
     */
    public int findMemberInClass(Member member) {
        for (int i = 0; i < this.membersInClass.size(); i++) {
            if (this.membersInClass.get(i).equals(member)) {
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
        return member.getExpire().compareTo(new Date()) <= 0;
    }

    /**
     * Checks if there is a time conflict.
     * @param member Member to check if they have a time conflict.
     * @return <b>false</b> if there is a time conflict; <b>true</b> otherwise.
     */
/*
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
*/

    /**
     * Checks a member into a fitness class database. If that classes database is filled, this method will grow that
     * class database dynamically without any effort from the client / driver.
     * @param member Member to check into the particular fitness class database.
     * @return <b>true</b> if the member was successfully checked in; <b>false</b> otherwise.
     */
    public boolean checkIn(Member member) { // Assuming that they allowed to do this if they are calling this method.
        if (!checkIfMemberExpired(member) && findMemberInClass(member) == Constants.NOT_FOUND) {
            this.membersInClass.add(member);
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

        this.membersInClass.remove(member);
    }

    /**
     * Prints fitness class schedule. If class is empty, no participants are printed. If class has participants,
     * those members and their membership related information is printed.
     */
    @Override
    public String toString() {
//        System.out.println("-Fitness classes-");
        String returnString = String.format("%s - %s %s\n", this.CLASS_NAME, this.INSTRUCTOR_NAME, this.TIME.getTime());
        StringBuilder sb = new StringBuilder(returnString);

        for (Member inClass : this.membersInClass) {
            sb.append(String.format("\t %s\n", inClass));
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        FitnessClass fitnessClass = (FitnessClass) obj;

        return this.CLASS_NAME.equals(fitnessClass.getClassName()) && this.INSTRUCTOR_NAME.equals(fitnessClass.getInstructorName())
                && this.TIME.getTime().equals(fitnessClass.getTime()) && this.membersInClass.equals(fitnessClass.getMembersInClass());
    }
}



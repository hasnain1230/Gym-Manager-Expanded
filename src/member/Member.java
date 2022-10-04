package member;

import date.Date;
import enums.Location;

/**
 * This class defines all the characteristics of a Member. These include:
 * <ul> <b>
 *     <li>String First Name</li>
 *     <li>String Last Name</li>
 *     <li>Date Date of Birth</li>
 *     <li>Date Expiration Date</li>
 *     <li>Location Gym Location</li>
 * </ul> </b>
 * @author Hasnain Ali, Carolette Saguil
 */
public class Member implements Comparable<Member> {
    /**
     * First name
     */
    private final String fname;
    /**
     * Last Name
     */
    private final String lname;
    /**
     * Date of Birth
     */
    private final Date dob;
    /**
     * Expiration Date
     */
    private final Date expire;
    /**
     * Gym Location
     */
    private final Location location;

    /**
     * Constructor that instantiates the state of a Member.
     * @param fname Member's first name.
     * @param lname Member's last name.
     * @param dob Member's date of birth.
     * @param expire Member's expiration date.
     * @param location Member's location.
     */
    public Member(String fname, String lname, Date dob, Date expire, Location location) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = expire;
        this.location = location;
    }

    /**
     * @return Returns member's first name.
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * @return Returns member's last name.
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * @return Returns member's date of birth.
     */
    public Date getDob() {
        return this.dob;
    }

    /**
     * @return Returns member's expiration date.
     */
    public Date getExpire() {
        return this.expire;
    }

    /**
     * @return Returns member's location.
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * @return Return string representation of member in the format firstName lastName, DOB: mm/dd/yy,
     * Membership expires: mm/dd/yy, Location: location
     */
    @Override
    public String toString() {
        if (this.expire.compareTo(new Date()) <= 0) { // If membership has expired.
            return String.format("%s %s, DOB: %s, Membership expired: %s, Location: %s",
                    this.fname, this.lname, this.dob, this.expire, this.location);
        }
        return String.format("%s %s, DOB: %s, Membership expires: %s, Location: %s",
                this.fname, this.lname, this.dob, this.expire, this.location);
    }


    /**
     * @param obj Member object to compare.
     * @return Whether the {@code obj} is equal to the current instance of member when comparing first names, last names,
     * and dates of births.
     */
    @Override
    public boolean equals(Object obj) {
        Member member = (Member) obj;

        return this.fname.equalsIgnoreCase(member.getFname()) && this.lname.equalsIgnoreCase(member.getLname())
                && this.dob.compareTo(member.getDob()) == 0;
    }


    /**
     * Compares two members together by last name then first name.
     * @param member Member to be compared.
     * @return 0 if the members' names are the same, a value less than 0 if the current instance member's name is
     * lexicographically less than the member argument's name, and a value greater than 0 if the current instance member's name
     * is lexicographically greater than the member argument's name.
     */
    @Override
    public int compareTo(Member member) {
        String name = String.format("%s %s", this.getLname(), this.getFname());
        String memberName = String.format("%s %s", member.getLname(), member.getFname());

        return name.compareTo(memberName);
    }

    /**
     * This testbed main in Member.java is to test all methods related Member, especially {@code Member.compareTo()}.
     * @param args Empty arguments; no arguments from the command line should be passed here.
     */
    public static void main(String[] args) {
        Member memberToTest1 = new Member("Rick", "Astely", new Date("04/20/1969"),
                new Date("10/07/2022"), Location.PISCATAWAY);

        // ************************ Testing Getters ************************
        System.out.println(memberToTest1.getFname());
        System.out.println(memberToTest1.getLname());
        System.out.println(memberToTest1.getDob());
        System.out.println(memberToTest1.getExpire());
        System.out.println(memberToTest1.getLocation());

        System.out.println();

        // ************************ Testing Member.toString() ************************
        System.out.println(memberToTest1); // Calls toString by default

        System.out.println();
        // ************************ Testing Member.equals() ************************
        Member memberToTest2 = new Member("Rick", "Astely", new Date("04/20/1969"),
                new Date("10/07/2022"), Location.PISCATAWAY);
        Member memberToTest3 = new Member("Rick", "Astely", new Date("06/09/1969"),
                new Date("10/07/2022"), Location.SOMERVILLE);

        System.out.println(memberToTest1.equals(memberToTest2)); // Should print true
        System.out.println(memberToTest2.equals(memberToTest3)); // Should print false because different DOB

        System.out.println();

        // ************************ Testing Member.compareTo() ************************

        Member memberToTest4 = new Member("Barack", "Obama", new Date("8/4/1961"),
                new Date("10/07/2022"), Location.SOMERVILLE);
        Member memberToTest5 = new Member("Kayne", "Obama", new Date("8/4/1961"),
                new Date("10/07/2022"), Location.SOMERVILLE);

        System.out.println(memberToTest1.compareTo(memberToTest2)); // Names are the same, hence 0
        System.out.println(memberToTest2.compareTo(memberToTest3)); // Names are the same, hence 0

        System.out.println(memberToTest4.compareTo(memberToTest1)); // Prints positive number since Obama comes after Astely
        System.out.println(memberToTest1.compareTo(memberToTest4)); // Prints negative number since Astely comes before Obama

        System.out.println(memberToTest5.compareTo(memberToTest4));
        System.out.println(memberToTest4.compareTo());




    }
}

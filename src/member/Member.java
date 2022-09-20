package member;

import date.Date;

import javax.xml.stream.Location;

public class Member implements Comparable<Member> {
    private String fname;
    private String lname;
    private Date dob; // TODO: Create Date class
    private Date expire;
    private Location location; // TODO: Create location class

    public Member(String fname, String lname, Date dob, Date expire, Location location) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = expire;
        this.location = location;
    }

    public String getFname() {
        return this.fname;
    }

    public String getLname() {
        return this.lname;
    }

    public Date getDob() {
        return this.dob;
    }

    public Date getExpire() {
        return this.expire;
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public String toString() {

        return this.fname + " " + this.lname + ", DOB:" + this.dob +
                ", Membership expires:" + this.expire + ", Location:" + this.location;
    }

    @Override
    public boolean equals(Object obj) {
        Member member = (Member) obj;

        return this.fname.equals(member.getFname()) && this.lname.equals(member.getLname())
                && this.dob.equals(member.getDob());
    }


    // TODO: Edit following documentation
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param member the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */

    @Override
    public int compareTo(Member member) { // QUESTION: Compare Last Name First name or First Name Last Name?
        String name = this.getLname() + this.getFname();
        String memberName = member.getLname() + member.getFname();

        return name.compareTo(memberName);
    }
}

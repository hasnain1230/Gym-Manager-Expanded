package member;

import date.Date;
import enums.Location;

public class Member implements Comparable<Member> {
    private final String fname;
    private final String lname;
    private final Date dob; // TODO: Create Date class
    private final Date expire;
    private final Location location; // TODO: Create location class

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


    @Override
    public int compareTo(Member member) { // QUESTION: Compare Last Name First name or First Name Last Name?
        String name = this.getLname() + this.getFname();
        String memberName = member.getLname() + member.getFname();

        return name.compareTo(memberName);
    }
}

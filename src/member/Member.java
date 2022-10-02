package member;

import date.Date;
import enums.Location;

public class Member implements Comparable<Member> {
    private final String fname;
    private final String lname;
    private final Date dob;
    private final Date expire;
    private final Location location;

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
        return String.format("%s %s, DOB: %s, Membership expires: %s, Location: %s",
                this.fname, this.lname, this.dob, this.expire, this.location);
    }

    @Override
    public boolean equals(Object obj) {
        Member member = (Member) obj;

        return this.fname.equals(member.getFname()) && this.lname.equals(member.getLname())
                && this.dob.compareTo(member.getDob()) == 0;
    }


    @Override
    public int compareTo(Member member) {
        String name = String.format("%s %s", this.getLname(), this.getFname());
        String memberName = String.format("%s %s", member.getLname(), member.getFname());

        return name.compareTo(memberName);
    }

    // TODO: Write Testbed main here
}

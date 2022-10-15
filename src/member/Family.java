package member;

import constants.Constants;
import date.Date;
import enums.Location;

public class Family extends Member {
    private short guestPasses;

    /**
     * Constructor that instantiates the state of a Member.
     *
     * @param fname    Member's first name.
     * @param lname    Member's last name.
     * @param dob      Member's date of birth.
     * @param expire   Member's expiration date.
     * @param location Member's location.
     */
    public Family(String fname, String lname, Date dob, Date expire, Location location, short guestPasses) {
        super(fname, lname, dob, expire, location);
        this.guestPasses = guestPasses;
    }

    public boolean setGuestPasses(short guestPasses) {
        if (guestPasses < 1) {
            return false;
        }

        this.guestPasses = guestPasses;
        return true;
    }

    public short getGuestPasses() {
        return this.guestPasses;
    }

    @Override
    public double getMembershipFee() {
        return Constants.FAMILY_ONE_TIME_MEMBER_FEE + (Constants.FAMILY_MONTHLY_FEE * Constants.FAMILY_MEMBERSHIP_PAYMENT_FREQUENCY);
    }

    /*
    Override A Method:
    You redefine a method from the parent class because children are always better

    Overload A Method:
    Name is the same, but diff parameters
    public void setTime(int hour, int second);
    public void setTime(String hour, String second);
     */

    @Override
    public String toString() {
        if (super.getExpire().compareTo(new Date()) <= 0) { // If membership has expired.
            return String.format("%s %s, DOB: %s, Membership expired: %s, Location: %s, (Family) guest-pass remaining = %d",
                    super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), this.guestPasses);
        }
        return String.format("%s %s, DOB: %s, Membership expires: %s, Location: %s, (Family) guest-pass remaining = %d",
                super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), this.guestPasses);
    }
}

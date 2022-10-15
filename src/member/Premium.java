package member;

import constants.Constants;
import date.Date;
import enums.Location;

public class Premium extends Family {
    /**
     * Constructor that instantiates the state of a Member.
     *
     * @param fname    Member's first name.
     * @param lname    Member's last name.
     * @param dob      Member's date of birth.
     * @param expire   Member's expiration date.
     * @param location Member's location.
     */
    public Premium(String fname, String lname, Date dob, Date expire, Location location, short guestPasses) {
        super(fname, lname, dob, expire, location, guestPasses);
    }

    @Override
    public double getMembershipFee() {
        return Constants.PREMIUM_MEMBER_ONE_TIME_FEE + (Constants.PREMIUM_MONTHLY_FEE * Constants.PREMIUM_MEMBERSHIP_PAYMENT_FREQUENCY);
    }

    @Override
    public String toString() {
        if (super.getExpire().compareTo(new Date()) <= 0) { // If membership has expired.
            return String.format("%s %s, DOB: %s, Membership expired: %s, Location: %s, (Premium) guest-pass remaining = %d",
                    super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), super.getGuestPasses());
        }
        return String.format("%s %s, DOB: %s, Membership expires: %s, Location: %s, (Premium) guest-pass remaining = %d",
                super.getFname(), super.getLname(), super.getDob(), super.getExpire(), super.getLocation(), super.getGuestPasses());
    }
}

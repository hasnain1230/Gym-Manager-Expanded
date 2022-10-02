package date;

import java.util.Calendar;
import constants.Constants;
import member.Member;

public class Date implements Comparable<Date> {

    private final int YEAR;
    private final int MONTH;
    private final int DAY;

    public Date() { // default constructor 1 for today's date
        Calendar calendar = Calendar.getInstance();

        this.YEAR = calendar.get(Calendar.YEAR);
        this.MONTH = calendar.get(Calendar.MONTH) + 1;
        this.DAY = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public Date(String date) { //constructor 2 for input date mm/dd/yyyy
        String[] dateParts = date.split("/");
        this.YEAR = Integer.parseInt(dateParts[2]);
        this.DAY = Integer.parseInt(dateParts[1]);
        this.MONTH = Integer.parseInt(dateParts[0]);
    }

    public int getYear() {
        return this.YEAR;
    }

    public int getMonth() {
        return this.MONTH;
    }

    public int getDay() {
        return this.DAY;
    }

    @Override
    public int compareTo(Date date) {
        // When this is higher than date, be positive!
        // this == 01/01/2020
        // date == 01/01/2024
        // this.Date.compareTo(date) -----> -1

        // this == 01/01/1970
        // date == 01/01/1970
        // this.Date.compareTo(date) (when both dates are the same) ------> 0

        // this == 01/01/2020
        // date == 01/01/2018
        // this.Date.compareTo(date) --------> 1


        if (this.YEAR == date.getYear() && this.MONTH == date.getMonth() && this.DAY == date.getDay()) {
            return 0;
        }

        if (this.YEAR > date.getYear()) {
            return 1;
        } else if (this.YEAR == date.getYear()) {
            if (this.MONTH > date.getMonth()) {
                return 1;
            } else if (this.MONTH == date.getMonth()) {
                if (this.DAY > date.getDay()) {
                    return 1;
                }
            }
        }

        return -1;
    }

    public boolean checkIfDobIsFuture() {
        Date today = new Date();

        if (this.compareTo(today) >= 0) { // This means their DOB is in the future and/or today
            return false;
        }

        return true;
    }

    public boolean checkMemberAge() {
        Date today = new Date();
        int currentYear = today.getYear();
        int currentMonth = today.getMonth();
        int currentDay = today.getDay();

        int yearDifference = currentYear - this.YEAR;

        if (yearDifference > Constants.MINIMUM_AGE) {
            return true;
        } else if (yearDifference == Constants.MINIMUM_AGE) {
            if ((currentMonth - this.MONTH) > 0) {
                return true;
            } else if ((currentMonth - this.MONTH) == 0) {
                if ((currentDay - this.DAY) >= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isLeapYear(int year) {
        if (year % Constants.QUADRENNIAL == 0) {
            if (year % Constants.CENTENNIAL == 0) {
                if (year % Constants.QUATERCENTENNIAL == 0) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean isValid() {
        if (this.MONTH < Constants.MIN_MONTH || this.MONTH > Constants.MAX_MONTH) {
            return false;
        }

        if (this.DAY < Constants.MIN_DAYS){
            return false;
        }

        if (isLeapYear(this.YEAR) && this.MONTH == Constants.FEBRUARY && this.DAY > Constants.MAX_LEAP_FEB_DAYS) {
            return false;
        } else if (!isLeapYear(this.YEAR) && this.MONTH == Constants.FEBRUARY && this.DAY > Constants.MAX_NORM_FEB_DAYS) {
            return false;
        }

        if (this.MONTH == Constants.APRIL || this.MONTH == Constants.JUNE || this.MONTH == Constants.SEPTEMBER || this.MONTH == Constants.NOVEMBER) {
            if (this.DAY > Constants.MAX_DAYS_ONE) {
                return false;
            }
        } else if ( this.MONTH != Constants.FEBRUARY && this.DAY > Constants.MAX_DAYS_TWO) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return String.format("%d/%d/%d", this.MONTH, this.DAY, this.YEAR);
    }

    public static void main(String[] args) {
        // FIXME: Write proper test cases
        Date d = new Date("01/31/2001");
        Date d1 = new Date("01/30/2001");

        System.out.println(d1); //should give 1

        System.out.println(d.YEAR);
        System.out.println(d.MONTH);
        System.out.println(d.DAY);

        System.out.println(d1.YEAR);
        System.out.println(d1.MONTH);
        System.out.println(d1.DAY);

    }
}

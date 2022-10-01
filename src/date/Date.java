package date;

import java.util.Calendar;
import constants.Constants;

public class Date implements Comparable<Date> {

    private int year;
    private int month;
    private int day;

    // Use another class to define all constants
    // public static final
    // FIXME: Constants are all caps!

    public Date() { // default constructor 1 for today's date
        Calendar calendar = Calendar.getInstance();

        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1; //FIXME: check if 1 here is a magic number
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public Date(String date) { //constructor 2 for input date mm/dd/yyyy
        String[] dateParts = date.split("/");
        this.year = Integer.parseInt(dateParts[2]);
        this.month = Integer.parseInt(dateParts[0]);
        this.day = Integer.parseInt(dateParts[1]);
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
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

        if (this.year == date.getYear() && this.month == date.getMonth() && this.day == date.getMonth()){
            return 0;
        }

        if (this.year > date.getYear()) {
            return 1;
        } else if (this.year == date.getYear()) {
            if (this.month > date.getMonth()) {
                return 1;
            } else if (this.month == date.getMonth()) {
                if (this.day > date.getDay()) {
                    return 1;
                }
            }
        }

        return -1;
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
        if (this.month < Constants.MIN_MONTH || this.month > Constants.MAX_MONTH) { //checks if month is in between 1 to 12, if not return false
            return false;
        }

        if (this.day < Constants.MIN_DAYS){ //checks if day is less than 1
            return false;
        }

        if (isLeapYear(this.year) && this.month == Constants.FEBRUARY && this.day > Constants.MAX_LEAP_FEB_DAYS) { //if its feb, and leap year, and day is greater than 29 return false
            return false;
        } else if (!isLeapYear(this.year) && this.month == Constants.FEBRUARY && this.day > Constants.MAX_NORM_FEB_DAYS) { //if its feb, and not leap year, and day is greater than 28 return false
            return false;
        }

        if (this.month == Constants.APRIL || this.month == Constants.JUNE || this.month == Constants.SEPTEMBER || this.month == Constants.NOVEMBER) { //if its april, june, sep, nov
            if (this.day > Constants.MAX_DAYS_ONE) { //if these months have a date greater than 30 then not valid
                return false;
            }
        } else if ( this.month != Constants.FEBRUARY && this.day > Constants.MAX_DAYS_TWO) { //if it is any other month that is not feb and date is greater than 31 then it is not valid
            return false;
        }

        return true; //if passes all tests then return true
    }

    @Override
    public String toString() {
        return String.format("%d/%d/%d", this.month, this.day, this.year);
    }

    public static void main(String[] args) {
        Date d = new Date("01/31/2001");
        Date d1 = new Date("01/30/2001");

        System.out.println(d.compareTo(d1)); //should give 1

        System.out.println(d.year);
        System.out.println(d.month);
        System.out.println(d.day);

        System.out.println(d1.year);
        System.out.println(d1.month);
        System.out.println(d1.day);

    }
}

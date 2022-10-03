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

    /**
     * @return Returns year.
     */
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
        if (this.YEAR < Constants.MIN_YEAR) {
            return false;
        }

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

        Date today = new Date();

        //Test cases for isValid()

        //Test Case #1
        Date notValidYear = new Date("1/1/800");
        System.out.println(notValidYear.isValid());

        //Test Case #2
        Date notValidMonth = new Date("13/1/2022");
        System.out.println(notValidMonth.isValid());

        //Test Case #3
        Date notValidMonth2 = new Date("0/1/2022");
        System.out.println(notValidMonth2.isValid());

        //Test Case #4
        Date nonLeapYear = new Date("2/29/2021");
        System.out.println(nonLeapYear.isValid());

        //Test Case #5
        Date nonLeapYear2 = new Date("2/28/2021");
        System.out.println(nonLeapYear2.isValid());

        //Test Case #6
        Date leapYear = new Date("2/29/2020");
        System.out.println(leapYear.isValid());

        //Test Case #7
        Date leapYear2 = new Date("2/30/2020");
        System.out.println(leapYear2.isValid());

        //Test Case #8
        Date janDay = new Date("1/32/2020");
        System.out.println(janDay.isValid());

        //Test Case #9
        Date marDay = new Date("3/31/2020");
        System.out.println(marDay.isValid());

        //Test Case #10
        Date mayDay = new Date("5/32/2020");
        System.out.println(mayDay.isValid());

        //Test Case #11
        Date julyDay = new Date("7/31/2020");
        System.out.println(julyDay.isValid());

        //Test Case #12
        Date augDay = new Date("8/32/2020");
        System.out.println(augDay.isValid());

        //Test Case #13
        Date octDay = new Date("10/31/2020");
        System.out.println(octDay.isValid());

        //Test Case #14
        Date decDay = new Date("12/32/2020");
        System.out.println(decDay.isValid());

        //Test Case #15
        Date aprDay = new Date("4/30/2020");
        System.out.println(aprDay.isValid());

        //Test Case #16
        Date juneDay = new Date("6/31/2020");
        System.out.println(juneDay.isValid());

        //Test Case #17
        Date sepDay = new Date("9/30/2020");
        System.out.println(sepDay.isValid());

        //Test Case #18
        Date novDay = new Date("11/31/2020");
        System.out.println(novDay.isValid());

        //Test Case #19
        Date notValidDay = new Date("9/0/2020");
        System.out.println(notValidDay.isValid());

        //Test cases for compareTo()

        //Test Case #1
        Date past = new Date("9/1/2022");
        System.out.println(past.compareTo(today));

        //Test Case #2
        Date today2 = new Date();
        System.out.println(today2.compareTo(today));

        //Test Case #3
        Date future = new Date("11/1/2022");
        System.out.println(future.compareTo(today));

        // Test Cases for checkIfDobIsFuture()

        //Test Case #1
        System.out.println(past.checkIfDobIsFuture());

        //Test Case #2
        System.out.println(future.checkIfDobIsFuture());

        //Test cases for checkMemberAge()

        //Test Case #1
        Date adult = new Date("9/26/1990");
        System.out.println(adult.checkMemberAge());

        //Test Case #2
        Date young = new Date("9/26/2022");
        System.out.println(young.checkMemberAge());

    }
}

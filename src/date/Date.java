package date;

import java.util.Calendar;
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    private static final int minMonth = 1;
    private static final int maxMonth = 12;
    private static final int maxDaysOne = 30;
    private static final int maxDaysTwo = 31;
    private static final int maxNormFebDays = 28;
    private static final int maxLeapFebDays = 29;


    public Date() { // default constructor 1 for today's date
        this.year = Calendar.YEAR;
        this.month = Calendar.MONTH;
        this.day = Calendar.DATE;
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
        return 0;
    }

    private boolean isLeapYear(int year) {
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    return true;
                }
            } else {
                return true;
            }
        }

        return false;
    }

    public boolean isValid() {
        if (this.month < minMonth || this.month > maxMonth) { //checks if month is in between 1 to 12, if not return false
            return false;
        }

        return true; //if passes all tests then return true
    }

    public static void main(String[] args) {
        Date d = new Date("1/1/2022");

        System.out.println(d.day);
        System.out.println(d.month);
        System.out.println(d.year);
    }
}

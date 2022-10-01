package gym;

import enums.Location;
import enums.Time;
import member.Member;
import date.Date;
import member.MemberDatabase;

public class FitnessClass {
    private int memberInClassSize;
    private Member[] membersInClass;
    private String className;
    private String instructorName;
    private Time time;

    public FitnessClass(String className, String instructorName, Time time) {
        this.className = className;
        this.instructorName = instructorName;
        this.time = time;
        this.membersInClass = new Member[4]; // FIXME: Magic Number
        this.memberInClassSize = 0;
    }

    private void grow() {
        Member[] newMembersInClass = new Member[this.memberInClassSize + 4]; // FIXME: Magic Number

        for (int x = 0; x < this.memberInClassSize; x++) {
            newMembersInClass[x] = this.membersInClass[x];
        }

        this.membersInClass = newMembersInClass;
    }

    public String getClassName() {
        return className;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public Time getTime() {
        return time;
    }

    public int checkIfMemberInClass(Member member) {
        for(int i = 0; i < this.memberInClassSize; i++) {
            if (this.membersInClass[i].equals(member)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkIfMemberExpired(Member member) { // Have they died yet? :D
        //NEEDS TO:
        //check if their membership is expired or not: DONE
        //check if member already taking class, if so false:
        //check if member is taking another class at same time, if so false: maybe in UI we'll see later

        Date today = new Date();

        if (member.getExpire().compareTo(today) <= 0) { //if expiration date has same date as today or is before today return true because membership is expired
            return true;
        }

        return false;
    }

    public void checkIn(Member member) { // We are assuming that they allowed to do this if they are calling this method.
        if (this.memberInClassSize == this.membersInClass.length) {
            this.grow();
        } else {
            this.membersInClass[this.memberInClassSize] = member;
            this.memberInClassSize++;
        }
    }

    public void dropClass(Member member) {
        int index = this.checkIfMemberInClass(member);

        for( int i = index; i < this.memberInClassSize - 1; i--){
            this.membersInClass[i] = this.membersInClass[i + 1];
        }

        this.membersInClass[this.memberInClassSize - 1] = null;
        this.memberInClassSize--;
    }

    public static void main (String[] args) {
        Date dob = new Date("10/11/1976");
        Date expire = new Date("10/23/2022");

        Member a = new Member("anna", "saguil", dob, expire, Location.EDISON);

        FitnessClass pilates = new FitnessClass("pilates", "jennifer", Time.MORNING);
    }



}



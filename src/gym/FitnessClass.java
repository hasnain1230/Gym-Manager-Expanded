package gym;

import enums.Time;
import member.Member;
import date.Date;
import constants.Constants;

public class FitnessClass {
    private static int[] classSize;
    private static Member[][] membersInClass;
    private final Time FITNESS_CLASS;
    private final Time[] ALL_CLASSES;

    public FitnessClass(Time fitnessClassHelperEnum) {
        membersInClass = new Member[Constants.NUMBER_OF_CLASSES][Constants.ARRAY_DEFAULT_SIZE];
        this.FITNESS_CLASS = fitnessClassHelperEnum;
        classSize = new int[Constants.NUMBER_OF_CLASSES];

        this.ALL_CLASSES = new Time[Constants.NUMBER_OF_CLASSES];
        this.ALL_CLASSES[0] = Time.PILATES;
        this.ALL_CLASSES[1] = Time.SPINNING;
        this.ALL_CLASSES[2] = Time.CARDIO;
    }

    private void grow() {
        Member[] newMembersInClass = new Member[classSize[this.FITNESS_CLASS.getClassIndex()] + Constants.ARRAY_INCREMENT_SIZE];

        for (int x = 0; x < classSize[this.FITNESS_CLASS.getClassIndex()]; x++) {
            newMembersInClass[x] = membersInClass[this.FITNESS_CLASS.getClassIndex()][x];
        }

        membersInClass[this.FITNESS_CLASS.getClassIndex()] = newMembersInClass;
    }

    public String getClassName() {
        return this.FITNESS_CLASS.getClassName();
    }

    public int findMemberInClass(Member member) {
        for (int i = 0; i < classSize[this.FITNESS_CLASS.getClassIndex()]; i++) {
            if (membersInClass[this.FITNESS_CLASS.getClassIndex()][i] != null && membersInClass[this.FITNESS_CLASS.getClassIndex()][i].equals(member)) {
                return i;
            }
        }

        return Constants.NOT_FOUND;
    }


    public int findMemberInClass(String fname, String lname, Date dob) {
        for(int i = 0; i < classSize[this.FITNESS_CLASS.getClassIndex()]; i++) {
            if (membersInClass[this.FITNESS_CLASS.getClassIndex()][i] != null &&
                    membersInClass[this.FITNESS_CLASS.getClassIndex()][i].getFname().equals(fname) &&
                    membersInClass[this.FITNESS_CLASS.getClassIndex()][i].getLname().equals(lname) &&
                    membersInClass[this.FITNESS_CLASS.getClassIndex()][i].getDob().compareTo(dob) == 0) {
                return i;
            }
        }

        return Constants.NOT_FOUND;
    }

    public Member getMemberInClass(int index) {
        if (index == Constants.NOT_FOUND) {
            return null;
        }

        return membersInClass[this.FITNESS_CLASS.getClassIndex()][index];
    }

    public boolean checkIfMemberExpired(Member member) { // Have they died yet :(
        Date today = new Date();

        if (member.getExpire().compareTo(today) <= 0) { //if expiration date has same date as today or is before today return true because membership is expired
            return true;
        }

        return false;
    }

    public String findTimeConflictClass(Member member) {
        for (int x = 0; x < membersInClass.length; x++) {
            for (int y = 0; y < membersInClass[x].length; y++) {
                if (this.FITNESS_CLASS.getClassIndex() == x) {
                    continue;
                }

                if (membersInClass[x][y] != null && membersInClass[x][y].equals(member) && this.ALL_CLASSES[x].getTime().equals(this.FITNESS_CLASS.getTime())) {
                    return Time.returnEnumFromIndex(x).getClassName();
                }
            }
        }

        return null;
    }

    public boolean checkForTimeConflict(Member member) {
        for (int x = 0; x < membersInClass.length; x++) {
            for (int y = 0; y < membersInClass[x].length; y++) {
                if (this.FITNESS_CLASS.getClassIndex() == x) {
                    continue;
                }

                if (membersInClass[x][y] != null && membersInClass[x][y].equals(member) && this.ALL_CLASSES[x].getTime().equals(this.FITNESS_CLASS.getTime())) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkIn(Member member) { // We are assuming that they allowed to do this if they are calling this method.
        if (!checkIfMemberExpired(member) && findMemberInClass(member) == Constants.NOT_FOUND) { //FIXME: add back time conflict when sure it's working
            if (classSize[this.FITNESS_CLASS.getClassIndex()] == membersInClass[this.FITNESS_CLASS.getClassIndex()].length) {
                this.grow();
            }


            membersInClass[this.FITNESS_CLASS.getClassIndex()][classSize[this.FITNESS_CLASS.getClassIndex()]] = member;
            classSize[this.FITNESS_CLASS.getClassIndex()]++;
            return true;
        }

        return false;
    }

    public void dropClass(Member member) {
        int index = this.findMemberInClass(member);

        if (index == Constants.NOT_FOUND) {
            return;
        }

        for (int i = index; i < classSize[this.FITNESS_CLASS.getClassIndex()] - 1; i++) {
            membersInClass[this.FITNESS_CLASS.getClassIndex()][i] = membersInClass[this.FITNESS_CLASS.getClassIndex()][i + 1];
        }

        membersInClass[this.FITNESS_CLASS.getClassIndex()][classSize[this.FITNESS_CLASS.getClassIndex()] - 1] = null;
        classSize[this.FITNESS_CLASS.getClassIndex()]--;
    }

    public void printMembersInClass() {
        for (Member member : membersInClass[this.FITNESS_CLASS.getClassIndex()]) {
            System.out.println(member);
        }
    }

    public static void printClassSchedule() { // TODO: Write this with the participants
        System.out.println("-Fitness classes-");
        Time fche;

        for (int x = 0; x < membersInClass.length; x++) {
            if (x == 0) {
                Time time = Time.PILATES;
                System.out.printf("%s - %s %s\n", time.getClassName(), time.getInstructor(), time.getTime());
            } else if (x == 1) {
                Time time = Time.SPINNING;
                System.out.printf("%s - %s %s\n", time.getClassName(), time.getInstructor(), time.getTime());
            } else if (x == 2) {
                Time time = Time.CARDIO;
                System.out.printf("%s - %s %s\n", time.getClassName(), time.getInstructor(), time.getTime());
            }

            if (classSize[x] > 0) {
                System.out.println("\t** participants **");
            }

            for (int y = 0; y < classSize[x]; y++) {
                System.out.printf("\t %s", membersInClass[x][y]);
            }
        }

    }
}



package gym;

import enums.FitnessClassHelperEnum;
import enums.Time;
import member.Member;
import date.Date;
import constants.Constants;

public class FitnessClass {
    private int memberInClassSize;
    private static Member[][] membersInClass;
    private final String CLASS_NAME;
    private final FitnessClassHelperEnum FITNESS_CLASS;
    private final FitnessClassHelperEnum[] ALL_CLASSES;

    public FitnessClass(String className, FitnessClassHelperEnum fitnessClassHelperEnum) {
        this.CLASS_NAME = className;
        membersInClass = new Member[fitnessClassHelperEnum.getClassIndex()][Constants.ARRAY_DEFAULT_SIZE];
        this.memberInClassSize = 0;
        this.FITNESS_CLASS = fitnessClassHelperEnum;

        this.ALL_CLASSES = new FitnessClassHelperEnum[Constants.NUMBER_OF_CLASSES];
        this.ALL_CLASSES[0] = FitnessClassHelperEnum.PILATES;
        this.ALL_CLASSES[1] = FitnessClassHelperEnum.SPINNING;
        this.ALL_CLASSES[2] = FitnessClassHelperEnum.CARDIO;
    }

    private void grow() {
        Member[] newMembersInClass = new Member[this.memberInClassSize + Constants.ARRAY_INCREMENT_SIZE];

        for (int x = 0; x < this.memberInClassSize; x++) {
            newMembersInClass[x] = membersInClass[this.FITNESS_CLASS.getClassIndex()][x];
        }

        membersInClass[this.FITNESS_CLASS.getClassIndex()] = newMembersInClass;
    }

    public String getClassName() {
        return CLASS_NAME;
    }

    // FIXME: Whatever
/*    public Time getTime() {
        return TIME;
    }*/

    public int findMemberInClass(Member member) {
        for(int i = 0; i < this.memberInClassSize; i++) {
            if (membersInClass[this.FITNESS_CLASS.getClassIndex()][i].equals(member)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkIfMemberExpired(Member member) { // Have they died yet :(
        Date today = new Date();

        if (member.getExpire().compareTo(today) <= 0) { //if expiration date has same date as today or is before today return true because membership is expired
            return true;
        }

        return false;
    }

    public boolean checkForTimeConflict(Member member) {
        for (int x = 0; x < membersInClass.length; x++) {
            for (int y = 0; y < membersInClass[x].length; y++) {
                if (this.FITNESS_CLASS.getClassIndex() == x) {
                    continue;
                }
                if (membersInClass[x][y].equals(member) && this.ALL_CLASSES[x].getTime().equals(this.FITNESS_CLASS.getTime())) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkIn(Member member) { // We are assuming that they allowed to do this if they are calling this method.
        if (checkForTimeConflict(member) && checkIfMemberExpired(member) && findMemberInClass(member) != -1) {
            if (this.memberInClassSize == membersInClass.length) {
                this.grow();
            }

            membersInClass[this.FITNESS_CLASS.getClassIndex()][this.memberInClassSize] = member;
            this.memberInClassSize++;
            return true;
        }

        return false;
    }

    public void dropClass(Member member) {
        int index = this.findMemberInClass(member);

        for (int i = index; i < this.memberInClassSize - 1; i--) {
            membersInClass[i] = membersInClass[i + 1];
        }

        membersInClass[this.memberInClassSize - 1] = null;
        this.memberInClassSize--;
    }

    public void printMembersInClass() {
        for (Member member : membersInClass[this.FITNESS_CLASS.getClassIndex()]) {
            System.out.println(member);
        }
    }

    public void printClassSchedule() { // TODO: Write this with the participants


    }
}



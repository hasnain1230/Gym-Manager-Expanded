package gym;

import constants.Constants;
import enums.Time;
import enums.Location;
import member.Member;
import date.Date;
import member.MemberDatabase;

import java.util.Scanner;


public class GymManager {
    private void addMember (String[] lineParts, MemberDatabase memberDatabase) {
        Member member = new Member(lineParts[1], lineParts[2], new Date(lineParts[3]), new Date(lineParts[4]), Location.returnEnumFromString(lineParts[5]));

        if (!member.getDob().isValid()) {
            System.out.printf("DOB %s: invalid calendar date!\n", member.getDob());
            return;
        } else if (!member.getDob().checkIfDobIsFuture()) {
            System.out.printf("DOB %s: cannot be today or a future date.\n", member.getDob());
            return;
        } else if (!member.getDob().checkMemberAge()) {
            System.out.printf("DOB %s: must be 18 or older to join!\n", member.getDob());
            return;
        } else if (!member.getExpire().isValid()) {
            System.out.printf("Expiration Date %s: invalid calendar date!\n", member.getExpire());
            return;
        } else if (member.getLocation() == null) { // TODO: Make sure this is valid.
            System.out.printf("%s: invalid location!\n", lineParts[5]);
            return;
        }

        if (memberDatabase.add(member)) {
            System.out.printf("%s %s added.\n", member.getFname(), member.getLname());
        } else {
            System.out.printf("%s %s is already in the database.\n", member.getFname(), member.getLname());
        }
    }

    private void removeMember(String[] lineParts, MemberDatabase memberDatabase) {
        Member memberToRemove = memberDatabase.getMember(memberDatabase.find(lineParts[1], lineParts[2], new Date(lineParts[3])));
        if (memberToRemove == null) {
            System.out.printf("%s %s is not in the database.\n", lineParts[1], lineParts[2]);
            return;
        } else if (memberDatabase.remove(memberToRemove)) {
            System.out.printf("%s %s removed.\n", lineParts[1], lineParts[2]);
            return;
        }

        System.out.printf("%s %s was unable to be removed.\n", lineParts[1], lineParts[2]);
    }

    private void instantiateFitnessClasses(FitnessClass[] fitnessClasses) {
        fitnessClasses[0] = new FitnessClass(Time.PILATES);
        fitnessClasses[1] = new FitnessClass(Time.SPINNING);
        fitnessClasses[2] = new FitnessClass(Time.CARDIO);
    }

    private int returnClassIndex(String className) {
        if (className.equalsIgnoreCase("pilates")) {
            return Time.PILATES.getClassIndex();
        } else if (className.equalsIgnoreCase("spinning")) {
            return Time.SPINNING.getClassIndex();
        } else if (className.equalsIgnoreCase("cardio")) {
            return Time.CARDIO.getClassIndex();
        } else {
            return -1;
        }
    }

    private void checkInMember (String[] lineParts, MemberDatabase memberDatabase, FitnessClass[] fitnessClasses) {
        int classIndex = returnClassIndex(lineParts[1]);
        String fname = lineParts[2];
        String lname = lineParts[3];
        Date dob = new Date(lineParts[4]);

        if (classIndex == -1) {
            System.out.printf("%s class does not exist.\n", lineParts[1]);
            return;
        } else if (!dob.isValid()) {
            System.out.printf("DOB %s: invalid calendar date!\n", dob);
            return;
        }

        Member memberToCheckIn = memberDatabase.getMember(memberDatabase.find(fname, lname, dob));

        if (memberToCheckIn == null) {
            System.out.printf("%s %s %s does not exist in database.\n", fname, lname, dob);
            return;
        } else if (fitnessClasses[classIndex].checkIfMemberExpired(memberToCheckIn)) {
            System.out.printf("%s %s %s membership expired.\n", fname, lname, memberToCheckIn.getDob());
            return;
        } else if ( !(fitnessClasses[classIndex].checkForTimeConflict(memberToCheckIn)) ) {
            System.out.printf("%s time conflict -- %s %s has already checked into %s\n", fitnessClasses[classIndex].getClassName(), fname, lname, fitnessClasses[classIndex].findTimeConflictClass(memberToCheckIn));
            return;
        } else if ( !(fitnessClasses[classIndex].findMemberInClass(memberToCheckIn) == Constants.NOT_FOUND) ){
            System.out.printf("%s %s has already checked into %s.\n", fname, lname, fitnessClasses[classIndex].getClassName());
            return;
        }

        fitnessClasses[classIndex].checkIn(memberToCheckIn);

        System.out.printf("%s %s checked into %s.\n", memberToCheckIn.getFname(), memberToCheckIn.getLname(), fitnessClasses[classIndex].getClassName());
    }

    private void dropClass(String[] lineParts, MemberDatabase memberDatabase, FitnessClass[] fitnessClasses) {
        int classIndex = returnClassIndex(lineParts[1]);
        String fname = lineParts[2];
        String lname = lineParts[3];
        Date dob = new Date(lineParts[4]);

        Member memberToDrop = memberDatabase.getMember(memberDatabase.find(fname, lname, dob));

        if (classIndex == Constants.NOT_FOUND) {
            System.out.printf("%s class does not exist.\n", lineParts[1]);
            return;
        } else if (!dob.isValid()) {
            System.out.printf("DOB %s: invalid calendar date!\n", dob);
            return;
        } else if (memberToDrop == null || fitnessClasses[classIndex].findMemberInClass(memberToDrop) == Constants.NOT_FOUND) {
            System.out.printf("%s %s is not a participant in %s\n", fname, lname, fitnessClasses[classIndex].getClassName());
            return;
        }

        fitnessClasses[classIndex].dropClass(memberToDrop);
        System.out.printf("%s %s dropped %s\n", fname, lname, lineParts[1]);
    }

    public void run() {
        System.out.println("Gym Manager Running...");
        Scanner sc = new Scanner(System.in);
        String input;
        MemberDatabase memberDatabase = new MemberDatabase();
        FitnessClass[] fitnessClasses = new FitnessClass[Constants.NUMBER_OF_CLASSES];
        instantiateFitnessClasses(fitnessClasses);
        //pilates = fitnessClasses[0], spinning = fitnessClasses[1], cardio = fitnessClasses[2]

        while ( !((input = sc.nextLine()).equals("Q")) ) { // FIXME: Deal with spaces and empty characters
            String[] lineParts = input.split(" "); // While we could use String Tokenizer here, the split method is more elegant.

            if (lineParts[0].equals("A")) { // This is working now!
                addMember(lineParts, memberDatabase);
            } else if (lineParts[0].equals("R")) { // This works
                removeMember(lineParts, memberDatabase);
            } else if (lineParts[0].equals("P")) { // Works
                memberDatabase.print();
            } else if (lineParts[0].equals("PC")) { // Works
                memberDatabase.printByCounty();
            } else if (lineParts[0].equals("PN")) { // Works
                memberDatabase.printByName();
            } else if (lineParts[0].equals("PD")) { // WOrks
                memberDatabase.printByExpirationDate();
            } else if (lineParts[0].equals("S")) { // No works ~ Working on it now...
                FitnessClass.printClassSchedule();
            } else if (lineParts[0].equals("C")) { //Need to debug
                checkInMember(lineParts, memberDatabase, fitnessClasses);
            } else if (lineParts[0].equals("D")) { //Need to test
                dropClass(lineParts, memberDatabase, fitnessClasses);
            } else if (lineParts[0].equals("")) {
                continue;
            } else {
                System.out.printf("%s is an invalid command!\n", lineParts[0]);
            }
        }

        System.out.println("Gym Manager terminated.");
    }

    public static void main(String[] args) {
        GymManager gymManager = new GymManager();

        gymManager.run();
    }
}

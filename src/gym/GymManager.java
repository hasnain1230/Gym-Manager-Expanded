package gym;

import constants.Constants;
import enums.FitnessClassHelperEnum;
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

    }



    private void checkInMember (String[] lineParts, MemberDatabase memberDatabase) {


        String fname = lineParts[2];
        String lname = lineParts[3];
        Date dob = new Date(lineParts[4]);

        FitnessClass fc = new FitnessClass(lineParts[1], FitnessClassHelperEnum.returnEnumFromString(lineParts[1]));

        if (!dob.isValid()) {
            System.out.printf("DOB %s: invalid calendar date!\n", dob);
        }

        int index = memberDatabase.find(fname, lname, dob);

        if (index == Constants.NOT_FOUND) {
            System.out.printf("%s %s %s is not in database", fname, lname, dob);
        } else{
            Member member = memberDatabase.getMember(index);

        }


    }

    public void run() {
        System.out.println("Gym Manager Running...");
        Scanner sc = new Scanner(System.in);
        String input;
        MemberDatabase memberDatabase = new MemberDatabase();

        while ( !((input = sc.nextLine()).equals("Q")) ) {
            String[] lineParts = input.split(" "); // While we could use String Tokenizer here, the split method is more elegant.

            if (lineParts[0].equals("A")) { // This is working now!
                addMember(lineParts, memberDatabase);
            } else if (lineParts[0].equals("R")) {

            } else if (lineParts[0].equals("P")) {
                memberDatabase.print();
            } else if (lineParts[0].equals("PC")) {
                memberDatabase.printByCounty();
            } else if (lineParts[0].equals("PN")) {
                memberDatabase.printByName();
            } else if (lineParts[0].equals("PD")) {
                memberDatabase.printByExpirationDate();
            } else if (lineParts[0].equals("S")) {

            } else if (lineParts[0].equals("C")) {

            } else if (lineParts[0].equals("D")) {

            }
        }
        System.out.println("Gym Manager terminated.");
    }

    public static void main(String[] args) {
        GymManager gymManager = new GymManager();

        gymManager.run();
    }
}

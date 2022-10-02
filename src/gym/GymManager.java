package gym;

import enums.Location;
import member.Member;
import date.Date;
import member.MemberDatabase;

import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GymManager {

    public boolean checkIfMemberIsValid (Member m, MemberDatabase md) {

        //FIXME: wasn't sure how to print straight from string format so i did it through concat fix if you want to
        if (!m.getDob().isValid()) {
            System.out.println("DOB:" + m.getDob() + " invalid calendar date!");
            return false;
        } else if(!m.getExpire().isValid()) {
            System.out.println("Expiration Date:" + m.getExpire() + " invalid calendar date!");
            return false;
        } else if(!md.checkMemberAge(m)) {
            System.out.println("DOB:" + m.getDob() + " must be 18 or older to join!");
            return false;
        } else if(m.getLocation() == null) {
            System.out.println("location is not valid!");
        }

        return true;
    }

    public void run() {
        System.out.println("Gym Manager Running...");
        Scanner sc = new Scanner(System.in);
        String input;
        MemberDatabase md = new MemberDatabase();

        while ( !((input = sc.nextLine()).equals("Q")) ) {
            /*StringTokenizer st = new StringTokenizer(input, " ");

            while (st.hasMoreTokens()) {
                st.nextToken();
            }

             */

            String[] lineParts = input.split(" ");

            if (lineParts[0].equals("A")) {
                Member m = new Member(lineParts[1], lineParts[2], new Date(lineParts[3]), new Date(lineParts[4]), Location.returnEnumFromString(lineParts[5]));
                if (checkIfMemberIsValid(m, md)) {
                    if (md.add(m)) {
                        System.out.println(m.getFname() + " " + m.getLname() + " added."); //FIXME: make this proper
                    } else{
                        System.out.println(m.getFname() + " " + m.getLname() + " is already in database."); //FIXME: make this proper
                    }
                }
            } else if (lineParts[0].equals("R")) {
                Member m = new Member(lineParts[1], lineParts[2], new Date(lineParts[3]), new Date(lineParts[4]), Location.returnEnumFromString(lineParts[5]));
                md.remove(m);
            } else if (lineParts[0].equals("P")) {
                md.print();
            } else if (lineParts[0].equals("PC")) {
                md.printByCounty();
            } else if (lineParts[0].equals("PN")) {
                md.printByName();
            } else if (lineParts[0].equals("PD")) {
                md.printByExpirationDate();
            } else if (lineParts[0].equals("C")) {

            }



        }

        System.out.println("Gym Manager terminated.");
    }

    public static void main(String[] args) {
        GymManager gymManager = new GymManager();

        gymManager.run();
    }
}

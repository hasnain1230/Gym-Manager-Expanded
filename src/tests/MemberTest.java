package tests;

import date.Date;
import enums.Location;
import member.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void getFname() {
        Member m = new Member("Carolette", "Saguil", new Date("09/26/2002"), new Date("09/26/2100"), Location.PISCATAWAY);

        String fname = m.getFname();

        assertEquals(fname, "Carolette");

        Member m1 = new Member("Hasnain", "Ali", new Date("09/26/2002"), new Date("09/26/2100"), Location.PISCATAWAY);

        String fname1 = m1.getFname();

        assertEquals(fname1, "Hasnain");
    }
}
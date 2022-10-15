package tests;

import date.Date;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTest {

    @Test
    void incrementMonth() {
        Date date = new Date();
        Date dateTing = new Date("1/1/2022");

        date.incrementMonth(5);
        dateTing.incrementMonth(10);

        assertEquals(date.getMonth(), 3);
        assertEquals(date.getYear(), 2023);
        assertEquals(dateTing.getYear(), 2022);
        assertEquals(dateTing.getMonth(), 11);
    }
}
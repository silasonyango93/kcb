package bank.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UtilTest {

    @Test
    public void isTodayTest() {

        Date dateToday = null;
        Calendar calendar = Calendar.getInstance();
        dateToday = calendar.getTime();
        assertTrue(Util.isToday(dateToday));
    }


    @Test
    public void getToday_works_as_expected() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String todayDateString = dtf.format(now);

        assertEquals(todayDateString, Util.getToday());
    }
}

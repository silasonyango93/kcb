package bank.util;

import org.apache.commons.lang.time.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Util {
    public static boolean checkTwoDatesAreSimilar(Date firstDate, Date secondDate){
        return DateUtils.isSameDay(firstDate, secondDate);
    }



    public static boolean isToday(Date date) {
        Date dateToday = null;
        Calendar calendar = Calendar.getInstance();
        dateToday = calendar.getTime();
        return checkTwoDatesAreSimilar(dateToday, date);
    }

    public static String getToday() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }
}

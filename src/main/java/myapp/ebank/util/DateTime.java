package myapp.ebank.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The type Date time.
 */
public class DateTime {
    /**
     * Gets date time.
     *
     * @return the date time
     * @throws ParseException the parse exception
     */
    public static Date getDateTime() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }

    /**
     * Gets date.
     *
     * @return the date
     * @throws ParseException the parse exception
     */
    public static Date getDate() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = formatter.format(cal.getTime());
        System.out.println("Calendar date is " + date);
        return formatter.parse(date);
    }


    /**
     * Gets date in string.
     *
     * @return the date in string
     */
    public static String getDateInString() {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        return formatter.format(cal.getTime());
    }

    /**
     * Gets time.
     *
     * @return the time
     * @throws ParseException the parse exception
     */
    public static Date getTime() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }

    /**
     * Gets expire time.
     *
     * @param expireTime the expire time
     * @return the expire time
     * @throws ParseException the parse exception
     */
    public static Date getExpireTime(int expireTime) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expireTime);
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }

    /**
     * Gets due date.
     *
     * @param days the days
     * @return the due date
     * @throws ParseException the parse exception
     */
    public static Date getDueDate(int days) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }


}

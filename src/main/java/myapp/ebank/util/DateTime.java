package myapp.ebank.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
    /**
     * get current date and time
     *
     * @return
     * @throws ParseException
     * @author Fawad
     */
    public static Date getDateTime() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }

    /**
     * get current date only
     *
     * @return
     * @throws ParseException
     * @author Fawad
     */
    public static Date getDate() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = formatter.format(cal.getTime());
        System.out.println("Calendar date is " + date);
        return formatter.parse(date);
    }



    /**
     * get current date in String only
     *
     * @return
     * @throws ParseException
     * @author Fawad
     */
    public static String getDateInString() {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        return formatter.format(cal.getTime());
    }

    /**
     * get current time only
     *
     * @return
     * @throws ParseException
     * @author Fawad
     */
    public static Date getTime() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }

    /**
     * add  minutes in the current time (as specify during function call) which will be then use as token verification expiry time
     *
     * @return
     * @throws ParseException
     * @author Fawad
     */
    public static Date getExpireTime(int expireTime) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expireTime);
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }

    /**
     * add 5 days  in the current Date which will be then use as due date
     *
     * @return
     * @throws ParseException
     * @author Fawad
     */
    public static Date getDueDate(int days) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }


}

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
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
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
        return formatter.parse(date);
    }

    /**
     * get current time only
     *
     * @return
     * @throws ParseException
     * @author Fawad
     */
    public static Date getTime() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }

    /**
     * add 2 minutes in the current time which will be then use as token verification expiry time
     *
     * @return
     * @throws ParseException
     * @author Fawad
     */
    public static Date getExpireTime() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 2);
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }
}

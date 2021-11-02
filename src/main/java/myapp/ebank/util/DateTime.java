package myapp.ebank.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
    /**
     *  get current date and time
     * @author Fawad
     * @return
     * @throws ParseException
     */
    public static Date getDateTime() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }

    /**
     * This method is adding 2 minutes in the current time which will be use as token verification time
     * @author Fawad
     * @return
     * @throws ParseException
     */
    public static Date getExpireTime() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 2);
        String date = formatter.format(cal.getTime());
        return formatter.parse(date);
    }
}

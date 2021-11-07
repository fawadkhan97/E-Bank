package myapp.ebank.util;

import java.sql.Date;
import java.text.ParseException;

public class SqlDate {


    public static Date getDateInSqlFormat() throws ParseException {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        System.out.println("utilDate:" + utilDate);
        System.out.println("sqlDate:" + sqlDate);
        return sqlDate;
    }

}

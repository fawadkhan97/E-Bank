package myapp.ebank.util;

import java.sql.Date;

public class SqlDate {


    public static Date getDateInSqlFormat() {
        java.util.Date utilDate = new java.util.Date();
        return new java.sql.Date(utilDate.getTime());
    }

}

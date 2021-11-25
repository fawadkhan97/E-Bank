package myapp.ebank.util;

import java.sql.Date;

/**
 * The type Sql date.
 */
public class SqlDate {


    /**
     * Gets date in sql format.
     *
     * @return the date in sql format
     */
    public static Date getDateInSqlFormat() {
        java.util.Date utilDate = new java.util.Date();
        return new java.sql.Date(utilDate.getTime());
    }

}

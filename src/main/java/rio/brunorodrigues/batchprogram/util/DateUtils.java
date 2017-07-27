package rio.brunorodrigues.batchprogram.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat BRAZILLIAN_DATE = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat BRAZILLIAN_DATETIME = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();

        return calendar.getTime();
    }

    public static String toYYYYMMDDDateFormat() {
        Calendar calendar = Calendar.getInstance();

        return FORMATTER.format(calendar.getTime());
    }

    public  static Date toStringDate(String value) throws Exception {
        try {
            return BRAZILLIAN_DATE.parse(value);
        } catch (ParseException e) {
            throw  new Exception("Ocurr error converting date");
        }
    }

    public  static String toDateString(Date value) throws Exception {
        return BRAZILLIAN_DATE.format(value);
    }

    public static String toDateTimeString(Date value) throws Exception {
        return BRAZILLIAN_DATETIME.format(value);
    }

}

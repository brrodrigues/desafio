package rio.brunorodrigues.batchprogram.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyyMMdd");

    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();

        return calendar.getTime();
    }

    public static String toYYYYMMDDDateFormat() {
        Calendar calendar = Calendar.getInstance();

        return FORMATTER.format(calendar.getTime());
    }
}

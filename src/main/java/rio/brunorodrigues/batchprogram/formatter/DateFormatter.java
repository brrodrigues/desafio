package rio.brunorodrigues.batchprogram.formatter;



import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {

    static SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Date parse(String s, Locale locale) throws ParseException {

        Date converted = FORMATTER.parse(s);

        return converted;
    }

    @Override
    public String print(Date date, Locale locale) {
        return FORMATTER.format(date);
    }
}

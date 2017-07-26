package rio.brunorodrigues.batchprogram.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExporterUtils {

    public enum FORMATTER {

        DDMMYYYY {
            @Override
            public SimpleDateFormat converter() {
                return new SimpleDateFormat("ddMMyyyy");
            }
        };

        public abstract SimpleDateFormat converter();
    }

    public static String toDateString(Date value, FORMATTER format) {
        return format.converter().format(value);
    }

    public static String toStringSpaceLength(String value, int tamanho) {
        if (value == null){
            value = "";
        }
        return String.format("%-"+tamanho+"s",value);
    }

    private static String toStringZeroLength(String value, int tamanho){

        if(value == null)
            value = "0";

        return String.format("%-0"+tamanho+"s",value);

    }

    public static String toNumberStringZeroLength(Number value, int tamanho, int decimal){

        if (value == null)
            value = BigDecimal.ZERO;

        BigDecimal newValue = new BigDecimal(value.toString()).multiply(BigDecimal.TEN.multiply(new BigDecimal(decimal)));

        return toStringZeroLength(newValue.toString(),tamanho);

    }

    public static String toNumberStringZeroLength(Number value, int tamanho){


        if (value == null)
            value = BigDecimal.ZERO;

        BigDecimal newValue = new BigDecimal(value.toString());

        return toStringZeroLength(newValue.toString(),tamanho);

    }


}

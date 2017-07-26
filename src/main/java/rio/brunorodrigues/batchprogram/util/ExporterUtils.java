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

    public static String doRPAD(String value, int tamanho) {
        if (value == null) {
            value = " ";
        }
        return String.format("%" + tamanho + "s", value);
    }
    
    public static String doLPAD(String value, int tamanho) {
        if (value == null) {
            value = " ";
        }
        return String.format("%" + tamanho + "s", value);
    }

    private static String doLPADZero(String value, int tamanho) {

        if (value == null) {
            value = "0";
        }
        Integer valueNew = new Integer(value);

        return String.format("%0" + tamanho + "d", valueNew);

    }
//    private static String doRPADZero(String value, int tamanho) {
//
//        if (value == null) {
//            value = "0";
//        }
//        Integer valueNew = new Integer(value);
//
//        return String.format("%-0" + tamanho + "d", valueNew);
//
//    }

    public static String doLPADZero(Number value, int tamanho, int decimal) {

        if (value == null) {
            value = BigDecimal.ZERO;
        }

        BigDecimal newValue = new BigDecimal(value.toString().replaceAll("[^0-9]", ""));

        return doLPADZero(newValue.toString(), tamanho);

    }

    public static String doLPADZero(Number value, int tamanho) {

        if (value == null) {
            value = BigDecimal.ZERO;
        }

        BigDecimal newValue = new BigDecimal(value.toString());

        return doLPADZero(newValue.toString(), tamanho);

    }

}

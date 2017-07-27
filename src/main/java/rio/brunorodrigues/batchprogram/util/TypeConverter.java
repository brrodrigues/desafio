package rio.brunorodrigues.batchprogram.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import rio.brunorodrigues.batchprogram.exception.ErrorParseException;

public class TypeConverter {

	private static final Logger LOG = Logger.getLogger(TypeConverter.class.getName());

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

	public static Date toStringDate(String value) throws ErrorParseException {

		Date newDate = null;
		try {
			newDate = DATE_FORMAT.parse(value);
			return newDate;
		} catch (ParseException e) {
			LOG.log(Level.SEVERE, "Nao foi possivel converter a string {0} para date", value);
			throw new ErrorParseException(String.format("Nao foi possivel converter a string %s para date", value));
		}

	}

	public static BigDecimal toStringBigDecimal(String value) throws ErrorParseException {

		BigDecimal newBigDecimal = BigDecimal.ZERO;
		try {
			newBigDecimal = new BigDecimal(value);
			return newBigDecimal;
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Nao foi possivel converter a string {0} para decimal", value);
			throw new ErrorParseException(String.format("Nao foi possivel converter a string %s para decimal", value));
		}
		

	}

	public static String toString(Integer value) {

		if (value == null){
			return "";
		}

		return value.toString();
	}

	public static String toString(Number value) {

		if (value == null){
			return "";
		}

		BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2);

		return bigDecimal.toString();
	}

	public  static String toString(Date value) throws Exception {
		return DateUtils.toDateString(value);
	}

	public  static String toStringDateTime(Date value) throws Exception {
		return DateUtils.toDateString(value);
	}

	public static Integer toStringInteger(String value) throws ErrorParseException {

		Integer newBigDecimal = null;
		try {
			newBigDecimal = Integer.valueOf(value);
			return newBigDecimal;
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Nao foi possivel converter a string {0} para integer", value);
			throw new ErrorParseException(String.format("Nao foi possivel converter a string %s para integer", value));
		}
		

	}



}

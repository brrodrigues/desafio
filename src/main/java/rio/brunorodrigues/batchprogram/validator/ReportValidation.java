package rio.brunorodrigues.batchprogram.validator;


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class ReportValidation implements Validator {

    private SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

        String dateValue = (String) o;

        try {
            Date processDate = FORMATTER.parse(dateValue);
        } catch (ParseException e) {
            e.printStackTrace();

        }


    }
}

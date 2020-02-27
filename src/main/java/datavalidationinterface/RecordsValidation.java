package datavalidationinterface;

import java.util.logging.Logger;

public interface RecordsValidation {
    String INT_REGEX="^[0-9]*$";
    String STRING_REGEX="^[a-z\\sA-Z]*$";
    String EMAIL_REGEX="^[A-Z][A-Z0-9]*@GMAIL.COM$";
    int MAX_AGE=150;
    Logger LOGGER = Logger.getLogger(RecordsValidation.class.getName());
    boolean nameValidation(String name);
    boolean ageValidation(int age);
    boolean phoneNumberValidation(String phoneNo);
    boolean emailIdValidation(String emailId);

    boolean checkNegative(int n);
}

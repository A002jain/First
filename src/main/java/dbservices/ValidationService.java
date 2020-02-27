package dbservices;

import datavalidationinterface.RecordsValidation;

public class ValidationService implements RecordsValidation {

    public boolean ageValidation(int age) {
        boolean bool=true;
        if(age<0 || age>MAX_AGE)
            bool=false;
        LOGGER.info("Age Validation:"+bool);
        return bool;
    }

    public boolean emailIdValidation(String emailId) {
       boolean bool =((emailId != null)
                && (!emailId.equals(""))
                && (emailId.matches(EMAIL_REGEX)));
       LOGGER.info("Email Validation:"+bool);
       return bool;
    }

    @Override
    public boolean checkNegative(int n) {
        boolean bool=(n>0);
        LOGGER.info("Negative Value:"+!bool);
        return  bool;
    }


    public boolean nameValidation(String name) {
        boolean bool=   ((name != null)
                && (!name.equals(""))
                && (name.matches(STRING_REGEX)));
        LOGGER.info("Name Validation:"+bool);
        return bool;
    }

    public boolean phoneNumberValidation(String phoneNo) {
        boolean bool=false;
        if(phoneNo.length()==10 && phoneNo.matches(INT_REGEX)){
                bool=true;
        }
        LOGGER.info("Phone No. Validation:"+bool);
        return bool;
    }
}

  /* public boolean nameValidation(String name) {
        int ch;
        String[] nameParts=name.split(" ");
        if(nameParts.length<3) {
            for(String str:nameParts){
                char[] x = str.toCharArray();
                for (int i = 0; i < x.length; i++) {
                    ch = x[i];
                    if (!((ch >= ConstantData.UPPER_A && ch <= ConstantData.UPPER_Z) || (ch >= ConstantData.LOWER_A && ch <= ConstantData.LOWER_Z))) {
                        System.out.println("Invalid Name");
                        return false;
                    }
                }
            }
        }
    }*/
 /*  public boolean emailIdValidation(String emailId) {
        int i=emailId.indexOf(ConstantData.AT_THE_RATE);
        if(emailId.substring(0,1).matches(ConstantData.STRING_REGEX) && emailId.length()<64 && !emailId.contains(" ") && i!=-1){
            if(emailId.substring(i).matches(ConstantData.EMAIL_REGEX));
                return true;
        }
        System.out.println("Invalid EmailId");
        return false;
   }*/




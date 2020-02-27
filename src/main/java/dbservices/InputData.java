package dbservices;
import inputDataInterface.InputDataInterface;

import java.util.InputMismatchException;

public class InputData implements InputDataInterface {

    @Override
    public int intInput(String s) {
        int i;
        System.out.print(s);
        try {
            i = scan.nextInt();
        }catch (Exception e){
            return -1;
        }
        return i;
    }

    @Override
    public String stringInput(String s) {
        String string;
        System.out.print(s);
        string=scan.next().trim();
        return string;
    }
}

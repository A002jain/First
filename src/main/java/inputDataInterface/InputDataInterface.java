package inputDataInterface;

import java.util.Scanner;

public interface InputDataInterface {
    Scanner scan=new Scanner(System.in).useDelimiter("\n");

    int intInput(String s);
    String stringInput(String s);
}

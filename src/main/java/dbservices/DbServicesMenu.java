package dbservices;
import java.util.Scanner;
public class DbServicesMenu {

    public static void main(String[] args)  {
        CrudService crudService=new CrudService();
        Scanner scan =new Scanner(System.in);
        scan.useDelimiter("\n");
        String ch;
        while (true){
            menu();
            ch=scan.next().trim();
            if(ch.equals("1")){

                crudService.create();

            }else if(ch.equals("2")){

                crudService.display();

            }else if(ch.equals("3")){

                crudService.update();

            }else if(ch.equals("4")){

                crudService.delete();

            } else if(ch.equals("5")){

                crudService.search();

            }else{
                    crudService.close();
                    System.exit(0);
            }
        }
    }
    public static void menu(){
        System.out.println("1:Create");
        System.out.println("2:Display");
        System.out.println("3:Update");
        System.out.println("4:Delete");
        System.out.println("5:Search");
        System.out.println("Press any other key to:Exit");
        System.out.print("Enter Your Choice:");
    }
}

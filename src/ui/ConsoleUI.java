package ui;

import java.util.Scanner;

public class ConsoleUI {



    public int  mainMenu(){

        System.out.println("Choose between the following options: ");
        System.out.println("  1. Authenticate customer");
        System.out.println("  2. Open account");
        System.out.print("Option: ");

        Scanner reader =new Scanner(System.in);
        int input = reader.nextInt();
        return input;
    }

    public void authenticateCustomerMenu(){
        System.out.println("Insert customer's username: ");
        System.out.println("Insert customer's password: ");

        Scanner reader =new Scanner(System.in);
        String input = reader.next();


    }

    public void openAccountMenu(){


    }
}

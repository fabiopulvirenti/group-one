package ui;

import accounts.Customer;

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
        System.out.println("Options:");
        System.out.println("  1. New customer");
        System.out.println("  2. Existing customer");
        System.out.print("Choose the option:");

        Scanner reader =new Scanner(System.in);
        int input = reader.nextInt();

        if (input == 1){
            System.out.println("Insert customer name: ");
            String customerName = reader.next();
            System.out.println("Has the customer provided a Photo ID?[Y/N]");
            String photoIDInput =reader.next();
            System.out.println("Has the customer provided a valid address-based ID?[Y/N]");
            String addressIDInput =reader.next();
            if (photoIDInput.equalsIgnoreCase("Y") && addressIDInput.equalsIgnoreCase("Y")) {
                System.out.println("Create customer username");
                String customerUsername =reader.next();

                System.out.println("Create customer password");
                String customerPassword = reader.next();

                Customer customer = new Customer(customerName,customerUsername,customerPassword);
                customer.setValidatedAddress(true);
                customer.setValidatedPhotoId(true);
                customer.storeNewCustomer();

                openAccountWithCustomer(customer);

            } else {
                System.out.println("It's not possible to proceed.");
            }

        } else if (input == 2){
            System.out.println("Insert customer ID: ");
            int inputCustomerID = reader.nextInt();
            //TODO: retrieve customer from file using Input customer ID
            // the parameter of the next method is expecting the variable customer that is initialized by the line above
            // openAccountWithCustomer(customer);

        }



    }

    private void openAccountWithCustomer(Customer customer){


    }


}

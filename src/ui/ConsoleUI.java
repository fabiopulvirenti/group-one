package ui;

import Tools.FileTools;
import accounts.Customer;

import java.util.Scanner;

public class ConsoleUI {

    public void mainEntry() {
        int optionGiven = mainMenu();
        switch (optionGiven) {
            case 1:
                authenticateCustomerMenu();
                break;
            case 2:
                openAccountMenu();
                break;
        }
    }


    public int mainMenu() {

        System.out.println("Choose between the following options: ");
        System.out.println("  1. Authenticate customer");
        System.out.println("  2. Open account");
        System.out.print("Option: ");

        Scanner reader = new Scanner(System.in);
        int input = reader.nextInt();
        return input;
    }

    // The function authenticate the customer, and returns an instance of Customer with the data of the given username
    // if correctly authenticated, or null if the customer was not authenticated.
    public Customer authenticateCustomerMenu() {
        Scanner reader = new Scanner(System.in);

        System.out.println("Insert customer's username: ");
        String inputUserName = reader.next();

        System.out.println("Insert customer's password: ");
        String inputPassword = reader.next();

        FileTools fileTools = new FileTools();
        boolean userExist = fileTools.UserExist(inputUserName);

        if (userExist) {
            String password = fileTools.ReadPassword(inputUserName);
            if (inputPassword.equals(password)) {
                System.out.println("customer authenticated correctly");

                int id = fileTools.ReadId(inputUserName);
                Customer customer = new Customer(id, inputUserName, inputPassword, null);

                return customer;
            } else {
                System.out.println("The credentials are not correct");
            }
        } else {
            System.out.println("The credentials are not correct");
        }

        return null;
    }

    public void openAccountMenu() {
        System.out.println("Customer Menu");
        System.out.println("  1. New customer");
        System.out.println("  2. Existing customer");
        System.out.print("Choose the option:");

        Scanner reader = new Scanner(System.in);
        int input = reader.nextInt();

        switch (input) {
            case 1:
                System.out.println("Insert customer name: ");
                String customerName = reader.next();

                System.out.println("Has the customer provided a Photo ID?[Y/N]");
                String photoIDInput = reader.next();

                System.out.println("Has the customer provided a valid address-based ID?[Y/N]");
                String addressIDInput = reader.next();

                if (photoIDInput.equalsIgnoreCase("Y") && addressIDInput.equalsIgnoreCase("Y")) {
                    System.out.println("Create customer username");
                    String customerUsername = reader.next();

                    System.out.println("Create customer password");
                    String customerPassword = reader.next();

                    Customer customer = new Customer(customerUsername, customerPassword, null);
                    customer.setValidatedAddress(true);
                    customer.setValidatedPhotoId(true);
                    customer.storeNewCustomer();

                    openAccountWithCustomer(customer);

                } else {
                    System.out.println("It's not possible to proceed.");
                }
                break;

            case 2:
                Customer customer = authenticateCustomerMenu();
                if( customer != null) {
                   openAccountWithCustomer(customer);
                }
                break;
        }


    }

    private void openAccountWithCustomer(Customer customer) {
        System.out.println("Open Account menu");
        System.out.println("  1. Personal Account");
        System.out.println("  2. ISA Account");
        System.out.println("  3. Business Account");
        System.out.print("Choose the option: ");

        Scanner reader = new Scanner(System.in);
        int input = reader.nextInt();

        switch (input) {
            case 1:
                openPersonalAccount(customer);
                break;
            case 2:
                openISAAccount(customer);
                break;
            case 3:
                openBusinessAccount(customer);
        }

    }

    private void openPersonalAccount(Customer customer) {
        System.out.println("How much does the customer want to deposit?");
        Scanner reader = new Scanner(System.in);
        String balanceInput = reader.next();

        float balance = Float.parseFloat(balanceInput.trim());

    }

    private void openISAAccount(Customer customer) {
        //TODO: Younis
    }

    private void openBusinessAccount(Customer customer) {
        // TODO: Joshua
    }
}

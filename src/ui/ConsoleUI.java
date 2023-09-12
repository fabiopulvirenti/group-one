package ui;

import Tools.FileTools;
import accounts.Customer;
import accounts.PersonalAccount;

import java.util.Scanner;

public class ConsoleUI {

    Scanner reader = new Scanner(System.in);
    HelperUI helper = new HelperUI();

    public void mainEntry() {
        System.out.println("####################################");
        System.out.println("#  Welcome to ACME Banking System  #");
        System.out.println("####################################");
        System.out.println("");

        boolean whileTrue = true;
        while(whileTrue) {
            int optionGiven = mainMenu();
            switch (optionGiven) {
                case 1:
                    authenticateCustomerMenu();
                    break;
                case 2:
                    openAccountMenu();
                    break;
                case 3:
                    helper.askForHelpMainEntry();
                    break;
                case 4:
                    whileTrue=false;
                    break;
            }
        }

        System.out.println("");
        System.out.println("");
        System.out.println("#################################");
        System.out.println("#           Goodbye!            #");
        System.out.println("#  Thanks for Banking with us!  #");
        System.out.println("#################################");
    }

    public int mainMenu() {

        System.out.println("Choose between the following options: ");
        System.out.println("  1. Authenticate customer");
        System.out.println("  2. Open account");
        System.out.println("  3. Help");
        System.out.println("  4. Exit");
        System.out.println("");
        System.out.print("Option: ");

        int inputValue=0;
        boolean notValid=true;
        while (notValid) {
            String input = reader.next();
            try {
                inputValue = Integer.parseInt(input);
                if (inputValue<1 || inputValue >4){
                    System.out.print("Value not valid. Insert again. Expected 1  2 or 3 : ");
                } else {
                    notValid = false;
                }
            } catch (Exception e) {
                System.out.print("Value not valid. Insert again. Expected 1 or 2 or 3: ");
            }
        }

        return inputValue;
    }

    // The function authenticate the customer, and returns an instance of Customer with the data of the given username
    // if correctly authenticated, or null if the customer was not authenticated.
    public Customer authenticateCustomerMenu() {

        System.out.println("Insert customer's username: ");
        String inputUserName = reader.next();

        System.out.println("Insert customer's password: ");
        String inputPassword = reader.next();

        FileTools fileTools = new FileTools();
        boolean userExist = fileTools.UserExist(inputUserName);

        if (userExist) {
            String password = fileTools.ReadPassword(inputUserName);
            if (inputPassword.equals(password)) {
                System.out.println("Customer authenticated correctly");

                int id = fileTools.ReadId(inputUserName);
                Customer customer = new Customer(id, inputUserName, inputPassword, null);

                System.out.println("Current customer is: " + customer.getUserName());

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
        boolean whileTrue = true;
        while (whileTrue) {
            System.out.println("Customer Menu");
            System.out.println("  1. New customer");
            System.out.println("  2. Existing customer");
            System.out.println("  3. Help");
            System.out.print("Choose the option:");

            int input;
            String inputValue = reader.nextLine();
            try {
                input = Integer.parseInt(inputValue);
            } catch (Exception e) {
                System.out.println("Option not valid. Choose between 1 and 3.");
                continue;
            }

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
                        whileTrue = false;

                    } else {
                        System.out.println("It's not possible to proceed, because photo Id and address-based ID need to be validated.");
                    }
                    break;

                case 2:
                    Customer customer = authenticateCustomerMenu();
                    if (customer != null) {
                        openAccountWithCustomer(customer);
                        whileTrue = false;
                    }
                    break;
                case 3:
                    helper.askForHelpOpenAccountMenu();
                    whileTrue = false;
                    break;
                default:
                    System.out.println("Option not valid. Choose between 1 and 3.");
                    break;
            }
        }

    }

    private void openAccountWithCustomer(Customer customer) {
        boolean whileTrue = true;
        while (whileTrue) {
            System.out.println("Open Account menu");
            System.out.println("  1. Personal Account");
            System.out.println("  2. ISA Account");
            System.out.println("  3. Business Account");
            System.out.println("  4. Help");
            System.out.println("");
            System.out.print("Choose the option: ");

            int input;
            String inputValue = reader.nextLine();
            try {
                input = Integer.parseInt(inputValue);
            } catch (Exception e) {
                System.out.println("Option not valid. Choose between 1 and 4.");
                continue;
            }

            switch (input) {
                case 1:
                    openPersonalAccount(customer);
                    whileTrue = false;
                    break;
                case 2:
                    openISAAccount(customer);
                    whileTrue = false;
                    break;
                case 3:
                    openBusinessAccount(customer);
                    whileTrue = false;
                    break;
                case 4:
                    helper.askForHelpOpenAccountWithCustomer();
                    whileTrue = false;
                    break;
                default:
                    System.out.println("Invalid input. Choose between 1 and 4.");
                    break;
            }
        }

    }

    private void openPersonalAccount(Customer customer) {
        boolean whileTrue = true;
        while (whileTrue) {
            System.out.println("How much does the customer want to deposit?");

            float balance;
            String balanceInput = reader.nextLine();
            try {
                balance = Float.parseFloat(balanceInput.trim());
            } catch (Exception e) {
                System.out.println("Please insert a valid number.");
                continue;
            }

            if (balance < 1) {
                System.out.println("Deposit lower than minimum.");
                System.out.println("Minimum deposit is 1 GBP");
            } else {
                PersonalAccount personalAccount = new PersonalAccount(customer.getCustomerID(), balance, customer.getCustomerName());
                personalAccount.storeAccount();
                whileTrue = false;
            }
        }

    }

    private void openISAAccount(Customer customer) {
        //TODO: Younis
    }

    private void openBusinessAccount(Customer customer) {
        // TODO: Joshua
    }
}

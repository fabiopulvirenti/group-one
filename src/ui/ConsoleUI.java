package ui;

import Tools.FileTools;
import accounts.AccountType;
import accounts.Customer;
import accounts.ISAAccount;
import accounts.PersonalAccount;

import java.util.Scanner;

public class ConsoleUI {

    Scanner reader = new Scanner(System.in);
    HelperUI helper = new HelperUI();
    FileTools fileTools = new FileTools();

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
                    if (fileTools.AccountExists(customer.getCustomerID(), AccountType.ISA)){
                        System.out.println("This customer already has an ISA account. Each customer can only have one ISA account.");
                    } else {
                        openISAAccount(customer);
                    }
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
        boolean validAnswer = false;
        String ukResident;
        boolean ukRes;

        String crownServant;
        boolean crownSer;

        //Who is the account for
        String accountHolder = "";
        do {
            System.out.println("""
                    Who is this account for?
                    1. Yourself
                    2. On behalf of someone who is mentally challenged
                    """);
            System.out.print("Please enter the correct number: ");
            accountHolder = reader.nextLine().trim();
            if (accountHolder.equals("1") || accountHolder.equals("2")) {
                validAnswer = true;
            } else {
                System.out.println("Please enter a valid number.\n");
            }
        } while (!validAnswer);

        if (accountHolder.equals("2")) {
            //Apply on behalf of someone who is mentally challenged
            ISAAccount isaAccount = new ISAAccount(0, 0f, "", 0, "", false, false);
            validAnswer = false;
            String choice = "";
            //Where do they live
            do {
                System.out.println("""
                        Where do you live?
                        1. Northern Ireland
                        2. Scotland
                        3. England or Wales
                        """);
                System.out.print("Please enter the associated number: ");
                choice = reader.nextLine().trim();
                if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                    validAnswer = true;
                } else {
                    System.out.println("Please enter a valid number.\n");
                }
            } while (!validAnswer);
            String countryName = "";
            if (choice.equals("1")) {
                countryName = "Northern Ireland";
            } else if (choice.equals("2")) {
                countryName = "Scotland";
            } else {
                countryName = "England or Wales";
            }
            System.out.println(isaAccount.mentallyChallengedApplication(countryName));
        } else {
            //Creating account for customer

            //Is customer a UK resident
            validAnswer = false;
            do {
                System.out.print("Are you a UK resident? (Yes/No): ");
                ukResident = reader.nextLine().trim().toLowerCase();
                if (ukResident.equals("yes") || ukResident.equals("no")) {
                    validAnswer = true;
                } else {
                    System.out.println("Please enter a valid answer.\n");
                }
            } while (!validAnswer);

            if (ukResident.equals("yes")) {
                ukRes = true;
            } else {
                ukRes = false;
            }
            //Is customer a crown servant
            validAnswer = false;
            do {
                System.out.print("Are you a crown servant? (Yes/No): ");
                crownServant = reader.nextLine().trim().toLowerCase();
                if (crownServant.equals("yes") || crownServant.equals("no")) {
                    validAnswer = true;
                } else {
                    System.out.println("Please enter a valid answer.\n");
                }
            } while (!validAnswer);

            if (crownServant.equals("yes")) {
                crownSer = true;
            } else {
                crownSer = false;
            }

            //Get customer age
            int customerAge = customer.getCustomerAge();

            //Get balance for new isa account
            float isaNewBalance = 0f;
            validAnswer = false;
            do {
                System.out.print("Please enter the balance for the new ISA account: ");
                String isaBalance = reader.nextLine();
                try {
                    isaNewBalance = Float.parseFloat(isaBalance);
                    validAnswer = true;
                } catch (Exception e) {
                    System.out.println("Please enter a valid balance.");
                }
            } while (!validAnswer);
            //Ask which type of isa account
            String isaType = "";
            String accountChoice = "";
            do {
                System.out.println("""
                        There are four types of ISA accounts:
                        Cash (C)
                        Stocks and Shares (S)
                        Innovative Finance (F)
                        Lifetime (L)
                        """);
                System.out.print("What would you like to do? Please enter the associated letter: ");
                accountChoice = reader.nextLine();
                validAnswer = true;
                if (accountChoice.trim().equalsIgnoreCase("c")) {
                    isaType = "cash";
                } else if (accountChoice.trim().equalsIgnoreCase("s")) {
                    isaType = "stocks and shares";
                } else if (accountChoice.trim().equalsIgnoreCase("f")) {
                    isaType = "innovative finance";
                } else if (accountChoice.trim().equalsIgnoreCase("l")) {
                    isaType = "lifetime";
                } else {
                    validAnswer = false;
                    System.out.println("Please try again.\n");
                }
            } while (!validAnswer);
            //Create isa account
            ISAAccount isaAccount = new ISAAccount(customer.getCustomerID(), isaNewBalance, customer.getCustomerName(), customerAge, isaType, ukRes, crownSer);
            //Check if user meets requirements for the ISA account
            if (isaAccount.validateAccount()) {
                System.out.println("New " + isaType.toLowerCase() + " ISA account created.");
            } else {
                System.out.println("You do not meet the requirements to open a " + isaType.toLowerCase() + " ISA account. Please check the requirements and try again.");
            }
            //Adding account to file
            char isaChoice = accountChoice.charAt(0);
            fileTools.StoreAccount(customer.getCustomerID(), AccountType.ISA, isaNewBalance, isaChoice);
        }
    }

    private void openBusinessAccount(Customer customer) {
        // TODO: Joshua
    }
}

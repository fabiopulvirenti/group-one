package ui;

import Tools.FileTools;
import accounts.*;

import java.time.LocalDate;
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

        reader.close();
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

        //FileTools fileTools = new FileTools();
        boolean userExist = fileTools.UserExist(inputUserName);

        if (userExist) {
            String password = fileTools.ReadPassword(inputUserName);
            if (inputPassword.equals(password)) {
                System.out.println("Customer authenticated correctly");

                int id = fileTools.ReadId(inputUserName);
                Customer customer = new Customer(id, inputUserName, inputPassword, fileTools.ReadYearOfBirth(inputUserName));

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
            String inputValue = reader.next();
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

                    String yearOfBirth = "";
                    boolean validAnswer = false;
                    do {
                        System.out.print("Please enter the customer's year of birth: ");
                        yearOfBirth = reader.next();
                        int year;
                        try {
                            year = Integer.parseInt(yearOfBirth);
                            if (yearOfBirth.length() == 4 && year >= 1900 && year <= (LocalDate.now().getYear())) {
                                validAnswer = true;
                            } else {
                                System.out.println("Please enter a valid year. [YYYY]");
                            }
                        } catch (Exception e) {
                            System.out.println("Please enter a valid year. [YYYY]");
                        }
                    } while (!validAnswer);

                    if (photoIDInput.equalsIgnoreCase("Y") && addressIDInput.equalsIgnoreCase("Y")) {

                        String customerUsername="";
                        while (true) {
                            System.out.println("Create customer username");
                            customerUsername = reader.next();

                            if(fileTools.UserExist(customerUsername)){
                                System.out.println("Username exist, please pick a new one.");
                            } else {
                                break;
                            }
                        }

                        System.out.println("Create customer password");
                        String customerPassword = reader.next();

                        Customer customer = new Customer(customerUsername, customerPassword, yearOfBirth);
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
                        if (fileTools.AccountExists(customer.getCustomerID(), AccountType.BUSINESS)) {
                            BusinessAccount businessAccount = new BusinessAccount(customer.getCustomerID());
                            businessAccount.annualCharge();
                        }
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
            String inputValue = reader.next();
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
                    if (fileTools.AccountExists(customer.getCustomerID(), AccountType.BUSINESS)) {
                        System.out.println("This customer already has a business account. Each business can only have one account");
                    } else {
                        openBusinessAccount(customer);
                    }
                    whileTrue = false;
                    break;
                case 4:
                    helper.askForHelpOpenAccountWithCustomer();
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
            String balanceInput = reader.next();
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
                    3. Help
                    """);
            System.out.print("Please enter the correct number: ");
            accountHolder = reader.next().trim();
            if (accountHolder.equals("1") || accountHolder.equals("2")) {
                validAnswer = true;
            } else if (accountHolder.equals("3")) {
                helper.askForHelpOpenISAAccount();
            } else {
                    System.out.println("Please enter a valid number.");

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
                choice = reader.next().trim();
                if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                    validAnswer = true;
                } else {
                    System.out.println("Please enter a valid number.");
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
                ukResident = reader.next().trim().toLowerCase();
                if (ukResident.equals("yes") || ukResident.equals("no")) {
                    validAnswer = true;
                } else {
                    System.out.println("Please enter a valid answer.");
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
                crownServant = reader.next().trim().toLowerCase();
                if (crownServant.equals("yes") || crownServant.equals("no")) {
                    validAnswer = true;
                } else {
                    System.out.println("Please enter a valid answer.");
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
                String isaBalance = reader.next();
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
                accountChoice = reader.next();
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
                    System.out.println("Please try again.");
                }
            } while (!validAnswer);
            //Create isa account
            ISAAccount isaAccount = new ISAAccount(customer.getCustomerID(), isaNewBalance, customer.getCustomerName(), customerAge, isaType, ukRes, crownSer);
            //Check if user meets requirements for the ISA account
            if (isaAccount.validateAccount(isaType.toLowerCase(),ukRes,crownSer,customerAge)) {
                System.out.println("New " + isaType.toLowerCase() + " ISA account created.");
                //Adding account to file
                char isaChoice = accountChoice.charAt(0);
                fileTools.StoreAccount(customer.getCustomerID(), AccountType.ISA, isaNewBalance, isaChoice);
            } else {
                System.out.println("You do not meet the requirements to open a " + isaType.toLowerCase() + " ISA account. Please check the requirements and try again.");
            }
        }
    }

    private void openBusinessAccount(Customer customer) {
        String userInput;

        System.out.println("Do you currently own an existing business? Y / N");
        userInput = reader.nextLine();
        if (!userInput.trim().equalsIgnoreCase("y")) {
            System.out.println("This account can only be opened up to customers with proof of an existing business");
            return;
        }

        System.out.println("Does the business exist an enterprise, plc, charity or public sector? Y / N");
        userInput = reader.nextLine();
        if (!userInput.trim().equalsIgnoreCase("y")) {
            System.out.println("These businesses cannot open a business account. Please refer to a different department.");
            return;
        }

        do {
            System.out.println("""
                            What kind of business account do you want to set up:
                            1. Sole Trader - Simpler to set up, but you're personally responsible for your debts and accounting.
                            2. Limited Company - Finances are separate from your personal finances, but there are more reporting and management responsibilities.
                            3. Partnership - A partnership is the simplest way for 2 or more people to run a business together. You both share responsibilities and debt.
                            4. Go back.
                            """);
            userInput = reader.nextLine();

            try {
                if (Integer.parseInt(userInput) == 4) {
                    return;
                } else if (Integer.parseInt(userInput) < 0 || Integer.parseInt(userInput) > 4) {
                    System.out.println("Please enter a input between 1 - 4");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid input. A number between 1 - 4.");
                continue;
            }
            try {
                System.out.println("Please enter the balance for the business account: ");
                userInput = reader.nextLine();
                BusinessAccount businessAccount = new BusinessAccount(customer.getCustomerID(), Float.parseFloat(userInput), customer.getCustomerName());
                businessAccount.storeAccount();
                System.out.println("Business account has been set up.");
                return;
            } catch (Exception e) {
                System.out.println("Please enter a valid numerical input for the balance.");
            }
        } while (true);



    }
}

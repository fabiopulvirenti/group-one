package Tools;

import accounts.AccountType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;


public class FileTools {
    // Declares private files for storing the path of storage files
    private final Path filePath;
    private final Path userPath;
    private final Path accountPath;
    private final Path transactionPath;


    // Creates the class and makes sure the files and path exists
    public FileTools() {
        this.filePath = Paths.get("../group-one/files");
        userPath = Paths.get(filePath + "/users.txt");
        accountPath = Paths.get(filePath + "/accounts.txt");
        transactionPath = Paths.get(filePath + "/transactions.txt");
        CreateDirectory(filePath);
    }

    // Creates the class and makes sure the files and path exists in the file directory provided
    public FileTools(String fileDir) {
        this.filePath = Paths.get(fileDir);
        userPath = Paths.get(filePath + "/users.txt");
        accountPath = Paths.get(filePath + "/accounts.txt");
        transactionPath = Paths.get(filePath + "/transactions.txt");
        CreateDirectory(filePath);
    }

    // Creates the directories for the files if they don't exist already
    private void CreateDirectory(Path path) {
        try {
            Files.createDirectories(path);
            Files.createFile(userPath);
            Files.createFile(accountPath);
            Files.createFile(transactionPath);

            // System.out.println("Directory created.");
        } catch (FileAlreadyExistsException e) {
            //System.out.println("Directory or file exists.");
        }
        catch (IOException e) {
            System.err.println("Directory could not be created. " + e.getMessage());
        }
    }

    // Read from file functions that return boolean

    // Checks if the given file is empty
    // If the file is empty it returns true
    // If the file has contents it returns false
    private boolean FileEmpty(Path path) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(path)))) {
            if (!scanner.hasNext()) {
                // System.out.println("File is empty");
                scanner.close();
                return true;
            } else {
                // System.out.println("File has contents");
                return false;
            }
        } catch (IOException e) {
            // If the file cannot be read or doesn't exist, flag an error and return false
            System.err.println("Could not read from user file." + e.getMessage());
            return false;
        }
    }

    // Checks if the user exists
    // If the user exists it returns true
    // If the user doesn't exist it returns false
    public boolean UserExist(int id) { // I had to change from private to public because I need to use it within the ConsoleUI.
        try (Scanner scanner = new Scanner(new File(String.valueOf(userPath)))) {
            // Declare the variables used and give the scanner the delimiter of ','
            String currentId;
            boolean userExists = false;
            scanner.useDelimiter(",");

            // If there is no scanner next line, file is empty
            if (!scanner.hasNext()) {
                // System.err.println("User file is empty");
                scanner.close();
                return userExists;
            }

            // Scans through the file checking for the id provided
            while (scanner.hasNextLine()) {
                currentId = scanner.next();
                // If id is found, the user exists
                if (currentId.equals(String.valueOf(id))) {
                    userExists = true;
                    break;
                }
                scanner.nextLine();
            }

            // closes the scanner and returns true or false if the user exists
            scanner.close();
            return userExists;

        } catch (IOException e) {
            // If the file cannot be read or doesn't exist, flag an error and return false
            System.err.println("Could not read from user file." + e.getMessage());
            return false;
        }
    }

    // Overload function for the UserExists function
    // Takes the username instead of the id, output is the same
    // If the user exists it returns true
    // If the user doesn't exist it returns false
    public boolean UserExist(String username) { // I had to change from private to public because I need to use it within the ConsoleUI.
        try (Scanner scanner = new Scanner(new File(String.valueOf(userPath)))) {
            // Declare the variables used and give the scanner the delimiter of ','
            String currentUsername;
            boolean userExists = false;
            scanner.useDelimiter("[,\\n]");

            // If there is no scanner next line, file is empty
            if (!scanner.hasNext()) {
                // System.err.println("User file is empty");
                scanner.close();
                return userExists;
            }

            // Scans through the file checking for the id provided
            while (scanner.hasNextLine()) {
                scanner.next();
                currentUsername = scanner.next();
                // If username is found, the user exists
                if (currentUsername.equals(username)) {
                    userExists = true;
                    break;
                }
                scanner.nextLine();
            }
            // closes the scanner and returns true or false if the user exists
            scanner.close();
            return userExists;

        } catch (IOException e) {
            // If the file cannot be read or doesn't exist, flag an error and return false
            System.err.println("Could not read from user file." + e.getMessage());
            return false;
        }
    }

    // Checks if the account exists for a user
    // If the user has an account of the type, returns true
    // If the user does not have an accout of this type, returns false
    public boolean AccountExists(int id, AccountType type) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(accountPath)))) {
            //Declare the variables used and give the scanner a delimiter of ','
            String currentId;
            String currentType;
            boolean accountExists = false;
            scanner.useDelimiter("[,\\n]");
            // If there is no scanner next line, the file is empty
            if (!scanner.hasNextLine()) {
                // System.err.println("Account file is empty");
                scanner.close();
                return accountExists;
            }

            // Scans through the file to check if an id matches the one provided
            // if so, checks to see if the account is the same as the one provided
            while (scanner.hasNextLine()) {
                currentId = scanner.next();
                if (currentId.equals(String.valueOf(id))) {
                    currentType = scanner.next();
                    // If the id and type are the same, then the account exists
                    if (currentType.equals(String.valueOf(type))) {
                        accountExists = true;
                        break;
                    }
                }
                scanner.nextLine();
            }

            // Closes the scanner and returns a boolean, true if the account exists and false if it doesn't
            scanner.close();
            return accountExists;

        } catch (IOException e) {
            // If the file cannot be read or doesn't exist, flag an error and return false
            System.err.println("Could not read from account file." + e.getMessage());
            return false;
        }
    }
    // Checks if the amount account exists for a user
    // If the user has the amount of accounts provided, returns true
    // If the user does not have the amount of accounts provided, returns false
    public boolean AccountExists(int id, int accountNum) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(accountPath)))) {
            //Declare the variables used and give the scanner a delimiter of ','
            String currentId;
            int currentNum;
            boolean accountExists = false;
            scanner.useDelimiter("[,\\n]");
            // If there is no scanner next line, the file is empty
            if (!scanner.hasNextLine()) {
                // System.err.println("Account file is empty");
                scanner.close();
                return accountExists;
            }

            // Scans through the file to check if an id matches the one provided
            // if so, checks to see if the account is the same as the one provided
            while (scanner.hasNextLine()) {
                currentId = scanner.next();
                if (currentId.equals(String.valueOf(id))) {
                    scanner.next();
                    scanner.next();
                    currentNum = Integer.parseInt(scanner.next());
                    if (currentNum == accountNum) {
                        accountExists = true;
                        break;
                    }
                }
                scanner.nextLine();
            }

            // Closes the scanner and returns a boolean, true if the account exists and false if it doesn't
            scanner.close();
            return accountExists;

        } catch (IOException e) {
            // If the file cannot be read or doesn't exist, flag an error and return false
            System.err.println("Could not read from account file." + e.getMessage());
            return false;
        }
    }


    // Read from file functions that return data

    // Function takes the username of the user and returns the id associated with the account
    // Returns 0 if the id doesn't exist or file fails to open
    public int ReadId(String username) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(userPath)))) {
            // Declare the variables used and give the scanner a delimiter of ','
            String currentUsername;
            String previousId;
            int userId = 0;
            scanner.useDelimiter("[,\\n]");

            // Checks if user exists
            if (!UserExist(username)) {
                System.err.println("User doesn't exist");
                return userId;
            }

            // If the scanner has no next line, the file is empty
            if (!scanner.hasNext()) {
                // System.err.println("User file is empty");
                scanner.close();
                return userId;
            }

            // Scans the file to check if the file contains the username provided
            while (scanner.hasNextLine()) {
                previousId = scanner.next();
                currentUsername = scanner.next();
                // If the file does contain the user, it stores the id to the account
                if (currentUsername.equals(username)) {
                    userId = Integer.parseInt(previousId);
                    break;
                }
                scanner.nextLine();
            }

            // Closes the scanner and returns the id associated
            scanner.close();
            return userId;

        } catch (IOException e) {
            // If the file fails to open, give an error and return null
            System.err.println("Could not read from user file." + e.getMessage());
            return 0;
        }
    }

    // Functions take the id of the user and returns the username associated with the id
    // Returns 0 if the user doesn't exist or the file fails to open
    public String ReadUsername(int id) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(userPath)))) {
            // Declare the variables used and give the scanner a delimiter of ','
            String currentId;
            String userResults = "0";
            scanner.useDelimiter("[,\\n]");

            // Checks to see if the user exists
            if (!UserExist(id)) {
                System.err.println("User doesn't exist");
                return userResults;
            }

            // If the scanner has no next line, the file is empty
            if (!scanner.hasNext()) {
                // System.err.println("User file is empty");
                scanner.close();
                return userResults;
            }

            // Scans the file to check if the file contains the id provided
            while (scanner.hasNextLine()) {
                currentId = scanner.next();
                // If the file contains the id, stores the password of the account
                if (currentId.equals(String.valueOf(id))) {
                    userResults = scanner.next();
                    break;
                }
                scanner.nextLine();
            }

            // Closes the scanner and returns the password associated
            scanner.close();
            return userResults;

        } catch (IOException e) {
            // If the file fails to open, give an error and return null
            System.err.println("Could not read from user file." + e.getMessage());
            return "0";
        }
    }
    // Functions take the username of the user and returns the password associated with the username
    // Returns 0 if the user doesn't exist or file fails to open
    public String ReadPassword(String username) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(userPath)))) {
            // Declare the variables used and give the scanner a delimiter of ','
            String currentUsername;
            String userPassword = "0";
            scanner.useDelimiter("[,\\n]");

            if (!UserExist(username)) {
                System.err.println("User doesn't exist");
                return userPassword;
            }

            // If the scanner has no next line, the file is empty
            if (!scanner.hasNext()) {
                // System.err.println("User file is empty");
                scanner.close();
                return userPassword;
            }


            // Scans the file to check if the file contains the username provided
            while (scanner.hasNextLine()) {
                scanner.next();
                currentUsername = scanner.next();
                // If the file contains the username, returns the password
                if (currentUsername.equals(username)) {
                    userPassword = scanner.next();
                    break;
                }
                scanner.nextLine();
            }

            // Closes the scanner and returns the password associated
            scanner.close();
            return userPassword;

        } catch (IOException e) {
            // If the file fails to open, give an error and return null
            System.err.println("Could not read from user file." + e.getMessage());
            return null;
        }
    }

    // Functions take the username of the user and returns the year of birth associated with the username
    // Returns 0 if the user doesn't exist or file fails to open
    public String ReadYearOfBirth(String username) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(userPath)))) {
            // Declare the variables used and give the scanner a delimiter of ','
            String currentUsername;
            String yearOfBirth = "0";
            scanner.useDelimiter("[,\\n]");

            if (!UserExist(username)) {
                System.err.println("User doesn't exist");
                return yearOfBirth;
            }

            // If the scanner has no next line, the file is empty
            if (!scanner.hasNext()) {
                // System.err.println("User file is empty");
                scanner.close();
                return yearOfBirth;
            }


            // Scans the file to check if the file contains the username provided
            while (scanner.hasNextLine()) {
                scanner.next();
                currentUsername = scanner.next();
                scanner.next();
                // If the file contains the username, returns the year
                if (currentUsername.equals(username)) {
                    yearOfBirth = scanner.next();
                    break;
                }
                scanner.nextLine();
            }

            // Closes the scanner and returns the year associated
            scanner.close();
            return yearOfBirth;

        } catch (IOException e) {
            // If the file fails to open, give an error and return null
            System.err.println("Could not read from user file." + e.getMessage());
            return null;
        }
    }

    // Function takes the id and account type of the user and returns the balance associated
    // Returns 0 if the account doesn't exist or if the file fails to open
    public String ReadAccountFile(int id, AccountType type) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(accountPath)))) {
            // Declare the variables needed and give the scanner a delimiter of ','
            String currentId;
            String currentType;
            String userResults = "0";
            scanner.useDelimiter("[,\\n]");

            // Checks to see if the account exists
            if (!AccountExists(id, type)) {
                System.err.println("Account doesn't exist");
                return userResults;
            }


            // If the scanner has no next line, the file is empty
            if (!scanner.hasNext()) {
                // System.err.println("The account file is empty");
                scanner.close();
                return userResults;
            }

            // Scans through the file and checks to see if it contains the id and type provided
            while (scanner.hasNextLine()) {
                currentId = scanner.next();
                if (currentId.equals(String.valueOf(id))) {
                    currentType = scanner.next();
                    // If the file contains the id and type, returns the balance of the account
                    if (currentType.equals(String.valueOf(type))) {
                        userResults = scanner.next();
                        break;
                    }

                }
                scanner.nextLine();
            }

            // Closes the scanner and returns the results
            scanner.close();
            return userResults;

        } catch (IOException e) {
            // If the file fails to open, show an error and return null
            System.err.println("Could not read from account file." + e.getMessage());
            return null;
        }
    }
    // Personal account read file overload
    // Function takes the id and account num of the user and returns the balance associated with that personal account
    // Returns 0 if the account doesn't exist or if the file fails to open
    public String ReadAccountFile(int id, int accountNum) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(accountPath)))) {
            // Declare the variables needed and give the scanner a delimiter of ','
            String currentId;
            String currentBalance;
            String currentNum;
            String userResults = "0";
            scanner.useDelimiter("[,\\n]");

            // Checks to see if the account exists
            if (!AccountExists(id, accountNum)) {
                System.err.println("Account doesn't exist");
                return userResults;
            }


            // If the scanner has no next line, the file is empty
            if (!scanner.hasNext()) {
                // System.err.println("The account file is empty");
                scanner.close();
                return userResults;
            }

            // Scans through the file and checks to see if it contains the id and type provided
            while (scanner.hasNextLine()) {
                currentId = scanner.next();
                if (currentId.equals(String.valueOf(id))) {
                    scanner.next();
                    currentBalance = scanner.next();
                    currentNum = scanner.next();
                    // If the file contains the id and type, returns the balance of the account
                    if (Integer.parseInt(currentNum) == accountNum) {
                        userResults = currentBalance;
                        break;
                    }

                }
                scanner.nextLine();
            }

            // Closes the scanner and returns the results
            scanner.close();
            return userResults;

        } catch (IOException e) {
            // If the file fails to open, show an error and return null
            System.err.println("Could not read from account file." + e.getMessage());
            return null;
        }
    }

    public char ReadISAType(int id) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(accountPath)))) {
            // Declare the variables needed and give the scanner a delimiter of ','
            String currentId;
            String currentType;
            char userResults = '0';
            scanner.useDelimiter("[,\\n]");

            // Checks to see if the account exists
            if (!AccountExists(id, AccountType.ISA)) {
                System.err.println("Account doesn't exist");
                return userResults;
            }

            // If the scanner has no next line, the file is empty
            if (!scanner.hasNext()) {
                // System.err.println("The account file is empty");
                scanner.close();
                return userResults;
            }

            // Scans through the file and checks to see if it contains the id and the isa type
            while (scanner.hasNextLine()) {
                currentId = scanner.next();
                if (currentId.equals(String.valueOf(id))) {
                    currentType = scanner.next();
                    // If the file contains the id and isa type, returns the type of isa account
                    if (currentType.equals(String.valueOf(AccountType.ISA))) {
                        scanner.next();
                        userResults = scanner.next().charAt(0);
                        break;
                    }
                }
                scanner.nextLine();
            }

            // Closes the scanner and returns the results
            scanner.close();
            return userResults;

        } catch (IOException e) {
            // If the file fails to open, show an error and return null
            System.err.println("Could not read from account file." + e.getMessage());
            return '0';
        }
    }

    // Function takes the id and account type and returns an array of strings
    // userResult[0] = old balance | userResult[1] = new balance
    // Legacy code being kept incase needed for future
//    public String[] ReadTransaction(int id, AccountType type) {
//        try (Scanner scanner = new Scanner(new File(String.valueOf(transactionPath)))) {
//            // Delcare the variable required and give the scanner a delimiter of ','
//            String currentId = "";
//            String currentType = "";
//            scanner.useDelimiter(",|\n");
//
//            // If the scanner has no next line, the file is empty
//            if (!scanner.hasNext()) {
//                // System.err.println("The transaction file is empty");
//                scanner.close();
//                return null;
//            }
//
//            // Declares the user results array and stores the first id and type of the account
//            String[] userResults = new String[2];
//
//            // Scans the file to see if it contains the id provided
//            // if so checks to see if the account is also the same as provided
//            while (scanner.hasNextLine()) {
//                currentId = scanner.next();
//                if (currentId.equals(String.valueOf(id))) {
//                    currentType = scanner.next();
//                    if (currentType.equals(String.valueOf(type))) {
//                        userResults[0] = scanner.next();
//                        userResults[1] = scanner.next();
//                        break;
//                    }
//                }
//                scanner.nextLine();
//            }
//
//            // closes the scanner and returns the balances stored
//            scanner.close();
//            return userResults;
//
//        } catch (IOException e) {
//            // If the file fails to open, show up an error and return null
//            System.err.println("Could not read from transaction file." + e.getMessage());
//            return null;
//        }
//    }

    // Writing to file functions

    // Writes to the user file, used for creating new users
    public void StoreUser(int id, String username, String password, String yearOfBirth) {
        try (BufferedWriter writer = Files.newBufferedWriter(userPath, StandardCharsets.UTF_8, APPEND)) {

            // Checks if the user exists, if so flags an error
            // if the file is empty, stores the user on the first line
            // else store the user on the next line
            if (UserExist(username) || UserExist(id))
                System.err.println("User already exists.");
            else if (FileEmpty(userPath))
                writer.write("" + id + ',' + username + ',' + password + "," + yearOfBirth);
            else
                writer.write("\n" + id + ',' + username + ',' + password + "," + yearOfBirth);
        } catch (IOException e) {
            // If the file can't be opened, flags up an error
            System.err.println("User could not be stored. " + e.getMessage());
        }
    }

    // Writes to the account file, used for creating new accounts for users.
    // Users can have multiple personal accounts
    // Users can only have one isa or business account
    public void StoreAccount(int id, AccountType type, float balance, int accountNum, char isaType) {
        try (BufferedWriter writer = Files.newBufferedWriter(accountPath, StandardCharsets.UTF_8, APPEND)) {

            // Checks if the account type is personal
            if (type == AccountType.PERSONAL) {
                // If the file is empty, writes the account on the first line of the file
                // else writes the account on the next line of the file
                if (AccountExists(id, accountNum))
                    System.err.println("Account already exists.");
                else if (FileEmpty(accountPath))
                    writer.write("" + id + ',' + type + ',' + balance + ',' + accountNum + ',' + isaType);
                else
                    writer.write("\n" + id + ',' + type + ',' + balance + ',' + accountNum + ',' + isaType);
            } else {
                // If the account exists, flag an error that the account already exists
                // else if the file is empty, writes the account on the first line of the file
                // else writes the account on the next line of the file
                if (AccountExists(id, type))
                    System.err.println("Account already exists.");
                else if (FileEmpty(accountPath))
                    writer.write("" + id + ',' + type + ',' + balance + ',' + 0 + ',' + isaType);
                else
                    writer.write("\n" + id + ',' + type + ',' + balance + ',' + 0 + ',' + isaType);
            }
        } catch (IOException e) {
            // If the file can't be opened, flags up an error
            System.err.println("Account could not be stored. " + e.getMessage());
        }
    }

    // Overload function for the function above
    // Assigns the account num to 0
    // Assigns isaType to null if not entered
    public void StoreAccount(int id, AccountType type, float balance) {
        this.StoreAccount(id, type, balance, 0, 'n');
    }
    // Overload function for the function above
    // Assigns isaType to null if not entered
    public void StoreAccount(int id, AccountType type, float balance, int accountNum) {
        this.StoreAccount(id, type, balance, accountNum, 'n');
    }
    // Overload function for the function above
    // Assigns account num to 0 if not entered
    public void StoreAccount(int id, AccountType type, float balance, char isaType) {
        this.StoreAccount(id, type, balance, 0, isaType);
    }


    // Writes to the transaction file, used for recording transactions of accounts
    public void RecordTransaction(int id, AccountType type, float newBalance) {
        try (BufferedWriter writer = Files.newBufferedWriter(transactionPath, StandardCharsets.UTF_8, APPEND)) {

            // Checks if the account exists
            if (!AccountExists(id, type)) {
                System.err.println("Account doesn't exist.");
                writer.close();
                return;
            }

            // If the file is empty, writes the transaction on the first line of the file
            // else writes the transaction on the next line of the file
            if (FileEmpty(transactionPath)) {
                String balance;
                balance = ReadAccountFile(id, type);
                writer.write("" + id + ',' + type + ',' + 0 + ',' + balance + ',' + newBalance);
                writer.close();
                UpdateAccount(id, type, balance, newBalance);
            }
            else {
                String balance;
                balance = ReadAccountFile(id, type);
                writer.write("\n" + id + ',' + type + ',' + 0 + ',' + balance + ',' + newBalance);
                writer.close();
                UpdateAccount(id, type, balance, newBalance);
            }
        } catch (IOException e) {
            // If the file can't be opened, flags up an error
            System.err.println("Transaction could not be recorded. " + e.getMessage());
        }
    }
    // Personal account overload
    // Writes to the transaction file, used for recording transactions of accounts
    public void RecordTransaction(int id, int accountNum, float newBalance) {
        try (BufferedWriter writer = Files.newBufferedWriter(transactionPath, StandardCharsets.UTF_8, APPEND)) {

            // Checks if the account exists
            if (!AccountExists(id, accountNum)) {
                System.err.println("Account doesn't exist.");
                writer.close();
                return;
            }

            // If the file is empty, writes the transaction on the first line of the file
            // else writes the transaction on the next line of the file
            if (FileEmpty(transactionPath)) {
                String balance;
                balance = ReadAccountFile(id, accountNum);
                writer.write("" + id + ',' + AccountType.PERSONAL + ',' + accountNum + ',' + balance + ',' + newBalance);
                writer.close();
                UpdateAccount(id, accountNum, balance, newBalance);
            }
            else {
                String balance;
                balance = ReadAccountFile(id, accountNum);
                writer.write("\n" + id + ',' + AccountType.PERSONAL + ',' + accountNum + ',' + balance + ',' + newBalance);
                writer.close();
                UpdateAccount(id, accountNum, balance, newBalance);
            }
        } catch (IOException e) {
            // If the file can't be opened, flags up an error
            System.err.println("Transaction could not be recorded. " + e.getMessage());
        }
    }
    // Updates the account when a transaction is made
    private void UpdateAccount(int id, AccountType type, String oldBalance, float newBalance) {
        try {
            // Declare the variables used for the function
            Scanner scanner = new Scanner(new File(String.valueOf(accountPath)));
            StringBuilder oldString = new StringBuilder();
            String newString;

            // Checks if the file is empty
            if (!scanner.hasNext()) {
                // System.err.println("The account file is empty");
                scanner.close();
                return;
            }

            oldString.append(scanner.nextLine());

            // Stores all the information inside the file
            while(scanner.hasNextLine()) {
                oldString.append("\n").append(scanner.nextLine());
            }

            scanner.close();

            // Replaces the account balance with the new balance
            newString = oldString.toString().replace(id + "," + type + "," +  oldBalance, id + "," + type + "," + newBalance);

            // Writes the file out with the new information
            BufferedWriter writer = Files.newBufferedWriter(accountPath, StandardCharsets.UTF_8);
            writer.write(newString);
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not read from transaction file." + e.getMessage());
        }
    }
    // Personal account overload
    // Updates the account when a transaction is made
    private void UpdateAccount(int id, int accountNum, String oldBalance, float newBalance) {
        try {
            // Declare the variables used for the function
            Scanner scanner = new Scanner(new File(String.valueOf(accountPath)));
            StringBuilder oldString = new StringBuilder();
            String newString;

            // Checks if the file is empty
            if (!scanner.hasNext()) {
                // System.err.println("The account file is empty");
                scanner.close();
                return;
            }

            oldString.append(scanner.nextLine());

            // Stores all the information inside the file
            while(scanner.hasNextLine()) {
                oldString.append("\n").append(scanner.nextLine());
            }

            scanner.close();

            // Replaces the account balance with the new balance
            newString = oldString.toString().replace(id + "," + AccountType.PERSONAL + "," +  oldBalance + "," + accountNum, id + "," + AccountType.PERSONAL + "," + newBalance + "," + accountNum);

            // Writes the file out with the new information
            BufferedWriter writer = Files.newBufferedWriter(accountPath, StandardCharsets.UTF_8);
            writer.write(newString);
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not read from transaction file." + e.getMessage());
        }
    }


}
package Tools;

import accounts.AccountType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
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

            System.out.println("Directory created.");
        } catch (FileAlreadyExistsException e) {
            //System.out.println("Directory or file exists.");
        }
        catch (IOException e) {
            System.err.println("Directory could not be created. " + e.getMessage());
        }
    }

    // Read from file functions that return boolean
    // Checks the user file for the id provided and returns true if the user exists
    // and returns false if the user doesn't
    private boolean UserExist(int id) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(userPath)))) {
            // Declare the variables used and give the scanner the delimiter of ','
            String currentId;
            boolean userExists = false;
            scanner.useDelimiter(",");
            // If there is no scanner next line, file is empty
            if (!scanner.hasNext()) {
                System.err.println("User file is empty");
                return userExists;
            }

            // Records the first id in the file
            currentId = scanner.next();
            scanner.nextLine();

            // Scans through the file checking for the id provided
            while (scanner.hasNextLine() && !currentId.equals(String.valueOf(id))) {
                currentId = scanner.next();
                scanner.nextLine();
            }

            // If id is found returns true
            if (currentId.equals(String.valueOf(id))) {
                userExists = true;
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

    // Checks the account file to see if an account for a user exists, returns true if
    // they exist and returns false if not
    private boolean AccountExists(int id, AccountType type) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(accountPath)))) {
            //Declare the variables used and give the scanner a delimiter of ','
            String currentId;
            String currentType;
            boolean accountExists = false;
            scanner.useDelimiter(",");
            // If there is no scanner next line, the file is empty
            if (!scanner.hasNextLine()) {
                System.err.println("Account file is empty");
                return accountExists;
            }

            // Records the first id and account type in the file
            currentId = scanner.next();
            currentType = scanner.next();
            scanner.nextLine();

            // Scans through the file to check if an id matches the one provided
            // if so, checks to see if the account is the same as the one provided
            while (scanner.hasNextLine() && !(currentId.equals(String.valueOf(id)) && currentType.equals(String.valueOf(type)))) {
                currentId = scanner.next();
                if (currentId.equals(String.valueOf(id))) {
                    currentType = scanner.next();
                }
                scanner.nextLine();
            }

            // if both the id and account type match the ones provided, then the account exists
            if (currentId.equals(String.valueOf(id)) && currentType.equals(String.valueOf(type))) {
                accountExists = true;
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

    // Functions take the id of the user and returns the password associated with the id
    public String ReadUserFile(int id) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(userPath)))) {
            // Declare the variables used and give the scanner a delimiter of ','
            String currentId;
            String userResults = null;
            scanner.useDelimiter(",");

            // If the scanner has no next line, the file is empty
            if (!scanner.hasNext()) {
                System.err.println("User file is empty");
                return userResults;
            }

            // Stores the first id of the file
            currentId = scanner.next();

            // Scans the file to check if the file contains the id provided
            while (scanner.hasNextLine() && !currentId.equals(String.valueOf(id))) {
                scanner.nextLine();
                currentId = scanner.next();
            }

            // If the file does contain the id, it stores the password to the account
            if (currentId.equals(String.valueOf(id))) {
                userResults = scanner.next();
            }

            // Closes the scanner and returns the password associated
            scanner.close();
            return userResults;

        } catch (IOException e) {
            // If the file fails to open, give an error and return null
            System.err.println("Could not read from user file." + e.getMessage());
            return null;
        }
    }

    // Function takes the id and account type of the user and returns the balance associated
    public String ReadAccountFile(int id, AccountType type) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(accountPath)))) {
            // Declare the variables needed and give the scanner a delimiter of ','
            String currentId;
            String currentType;
            String userResults = null;
            scanner.useDelimiter(",");

            // If the scanner has no next line, the file is empty
            if (!scanner.hasNext()) {
                System.err.println("The account file is empty");
                return userResults;
            }

            // Stores the first id and type of the file
            currentId = scanner.next();
            currentType = scanner.next();

            // Scans through the file and checks to see if it contains the id provided
            // if so, checks to see if the id has the account provided also
            while (scanner.hasNextLine() && !(currentId.equals(String.valueOf(id)) && currentType.equals(String.valueOf(type)))) {
                scanner.nextLine();
                currentId = scanner.next();
                if (currentId.equals(String.valueOf(id))) {
                    currentType = scanner.next();
                }
            }

            // If the file contains both the id and account, it stores the balance associated with the account
            if (currentId.equals(String.valueOf(id)) && currentType.equals(String.valueOf(type))) {
                userResults = scanner.next();
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

    // Function takes the id and account type and returns an array of strings
    // userResult[0] = old balance | userResult[1] = new balance
    public String[] ReadTransaction(int id, AccountType type) {
        try (Scanner scanner = new Scanner(new File(String.valueOf(transactionPath)))) {
            // Delcare the variable required and give the scanner a delimiter of ','
            String currentId;
            String currentType;
            scanner.useDelimiter(",");

            // If the scanner has no next line, the file is empty
            if (!scanner.hasNext()) {
                System.err.println("The transaction file is empty");
                return null;
            }

            // Declares the user results array and stores the first id and type of the account
            String[] userResults = new String[2];
            currentId = scanner.next();
            currentType = scanner.next();

            // Scans the file to see if it contains the id provided
            // if so checks to see if the account is also the same as provided
            while (scanner.hasNextLine() && !(currentId.equals(String.valueOf(id)) && currentType.equals(String.valueOf(type)))) {
                scanner.nextLine();
                currentId = scanner.next();
                if (currentId.equals(String.valueOf(id))) {
                    currentType = scanner.next();
                }
            }

            // If both the id and the account type are the same as provided
            // stores the old balance of the account and the new balance of the account
            // from the transaction
            if (currentId.equals(String.valueOf(id)) && currentType.equals(String.valueOf(type))) {
                userResults[0] = scanner.next();
                userResults[1] = scanner.next();
            }

            // closes the scanner and returns the balances stored
            scanner.close();
            return userResults;

        } catch (IOException e) {
            // If the file fails to open, show up an error and return null
            System.err.println("Could not read from transaction file." + e.getMessage());
            return null;
        }
    }

    // Writing to file functions

    // Writes to the user file, used for creating new users
    public void StoreUser(int id, String password) {
        try (BufferedWriter writer = Files.newBufferedWriter(userPath, Charset.forName("UTF-8"), APPEND)) {
            // Checks if the user exists, if so flags an error
            // else store the user in the file
            if (UserExist(id))
                System.err.println("User already exists.");
            else
                writer.write("" + id + ',' + password + '\n');
        } catch (IOException e) {
            // If the file can't be opened, flags up an error
            System.err.println("User could not be stored. " + e.getMessage());
        }
    }

    // Writes to the account file, used for creating new accounts for users.
    // Users can have multiple accounts
    public void StoreAccount(int id, AccountType type, float balance) {
        try (BufferedWriter writer = Files.newBufferedWriter(accountPath, Charset.forName("UTF-8"), APPEND)) {
            // Checks if the account exists, if so flags an error
            // else store the user in the file
            if (AccountExists(id, type))
                System.err.println("Account already exists.");
            else
                writer.write("" + id + ',' + type + ',' + balance + '\n');
        } catch (IOException e) {
            // If the file can't be opened, flags up an error
            System.err.println("Account could not be stored. " + e.getMessage());
        }
    }
    private void UpdateAccount(int id, AccountType type, String oldBalance, float newBalance) {
        try {
            Scanner scanner = new Scanner(new File(String.valueOf(accountPath)));
            String oldString;
            String newString;

            if (!scanner.hasNext()) {
                System.err.println("The account file is empty");
                return;
            }

            oldString = scanner.nextLine() + "\n";

            while(scanner.hasNextLine()) {
                oldString += scanner.nextLine() + "\n";
            }

            scanner.close();

            newString = oldString.replace("" + id + ',' + type + ',' + oldBalance + '\n', "" + id + ',' + type + ',' + newBalance + '\n');

            BufferedWriter writer = Files.newBufferedWriter(accountPath, Charset.forName("UTF-8"));
            writer.write(newString);
            writer.close();

        } catch (IOException e) {
            System.err.println("Could not read from transaction file." + e.getMessage());
        }
    }

    // Writes to the transaction file, used for recording transactions of accounts
    public void RecordTransaction(int id, AccountType type, float newBalance) {
        try (BufferedWriter writer = Files.newBufferedWriter(transactionPath, Charset.forName("UTF-8"), APPEND)) {
            // Writes a new transaction to the file
            if (AccountExists(id, type)) {
                String balance;
                balance = ReadAccountFile(id, type);
                balance = balance.replace("\n", "");
                writer.write("" + id + ',' + type + ',' + balance + ',' + newBalance + '\n');
                UpdateAccount(id, type, balance, newBalance);
            }
            else
                System.err.println("Account doesn't exist.");
        } catch (IOException e) {
            // If the file can't be opened, flags up an error
            System.err.println("Transaction could not be recorded. " + e.getMessage());
        }
    }


}
package Tools;

import accounts.AccountType;
import org.junit.jupiter.api.Test;

class FileToolsTest {

    @Test
    public void CreatingNewDirectory() {
        var tools = new FileTools();
    }

    @Test
    public void CreatingNewDirectoryWithInput() {
        var tools = new FileTools("../group-one/files/tempdir");
    }


    // Keep an eye on this one. Sometimes flags up an error when creating the directory
    // Possibly a space was entered in a file before the entries were put in
    // Only appeared once, but worth looking out for
    @Test
    public void WritingToUserFile(){
        var tools = new FileTools();
        tools.StoreUser(1, "Frankie", "Password");
    }
    @Test
    public void WritingAnotherUser() {
        var tools = new FileTools();
        tools.StoreUser(2, "Jeff","Passyword");
    }
    @Test
    public void WritingToUserSameID() {
        var tools = new FileTools();
        tools.StoreUser(1, "Frankie","Password");
    }
    @Test
    public void ReadingUserFile() {
        var tools = new FileTools();
        System.out.println(tools.ReadUsername(1));
    }

    @Test
    public void WritingToAccountsFile(){
        var tools = new FileTools();
        tools.StoreAccount(1, AccountType.PERSONAL, 30f);
    }
    @Test
    public void WritingASecondAccountSameUser() {
        var tools = new FileTools();
        tools.StoreAccount(1, AccountType.BUSINESS, 60f);
    }
    @Test
    public void WritingToAnAccountThatExists() {
        var tools = new FileTools();
        tools.StoreAccount(1, AccountType.PERSONAL, 30f);
    }
    @Test
    public void WritingAnISAAccount() {
        var tools = new FileTools();
        tools.StoreAccount(2, AccountType.ISA, 21f, 'f');
    }
    @Test
    public void ReadingAccountFile() {
        var tools = new FileTools();
        System.out.println(tools.ReadAccountFile(1, AccountType.PERSONAL));
    }

    @Test
    public void WritingToTransactionFile() {
        var tools = new FileTools();
        tools.RecordTransaction(1, AccountType.PERSONAL, 30f);

    }

    @Test
    public void WritingToTransactionFileAgain() {
        var tools = new FileTools();
        tools.RecordTransaction(1, AccountType.PERSONAL, 50f);
    }

    @Test
    public void WritingToIncorrectTransactionAccount() {
        var tools = new FileTools();
        tools.RecordTransaction(1, AccountType.ISA, 30f);
    }
    @Test
    public void ReadingTransactionFile() {
        var tools = new FileTools();
        String[] balances;
        balances = tools.ReadTransaction(1, AccountType.PERSONAL);
        System.out.println("Old balance: " + balances[0] +
                            "New balance: " + balances[1]);
    }
}
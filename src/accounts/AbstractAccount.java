package accounts;

import Tools.FileTools;

public abstract class AbstractAccount {

    private int customerId;

    private float balance;

    private String customerName;

    private AccountType type;

    public AbstractAccount(int customerId, float balance, String customerName, AccountType type) {
        this.customerId = customerId;
        this.balance = balance;
        this.customerName = customerName;
        this.type = type;
    }

    // Overload method for abstract account.
    // Constructor used for pulling information from an existing account
    public AbstractAccount(int customerId, AccountType type) {
        this.customerId = customerId;
        this.type = type;
        FileTools tools = new FileTools();
        this.customerName = tools.ReadUsername(customerId);
        this.balance = Float.parseFloat(tools.ReadAccountFile(customerId, type));
    }
    // Overload method for abstract account.
    // Constructor used for pulling information from a personal account
    public AbstractAccount(int customerId, int accountNum) {
        this.customerId = customerId;
        this.type = AccountType.PERSONAL;
        FileTools tools = new FileTools();
        this.customerName = tools.ReadUsername(customerId);
        this.balance = Float.parseFloat(tools.ReadAccountFile(customerId, accountNum));
    }



    public int getCustomerId() {
        return customerId;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public AccountType getType() {
        return type;
    }
}

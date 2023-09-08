package accounts;

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

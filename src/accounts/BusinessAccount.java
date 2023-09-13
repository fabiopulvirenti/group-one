package accounts;

import Tools.FileTools;

public class BusinessAccount extends AbstractAccount{

    public BusinessAccount(int customerId, float balance, String customerName) {
        super(customerId, balance, customerName, AccountType.BUSINESS);
    }

    public void storeAccount() {
        FileTools tools = new FileTools();
        tools.StoreAccount(getCustomerId(), getType(), getBalance());
    }
}

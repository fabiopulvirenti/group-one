package accounts;

import Tools.FileTools;

public class PersonalAccount extends AbstractAccount{


    public PersonalAccount(int customerId, float balance, String customerName) {
        super(customerId, balance, customerName, AccountType.PERSONAL);
    }


    public void storeAccount() {
        FileTools fileTools = new FileTools();
        fileTools.StoreAccount(getCustomerId(), getType(), getBalance());
    }
}

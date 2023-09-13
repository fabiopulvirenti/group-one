package accounts;

import Tools.FileTools;

public class PersonalAccount extends AbstractAccount{


    public PersonalAccount(int customerId, float balance, String customerName) {
        super(customerId, balance, customerName, AccountType.PERSONAL);
    }


    public void storeAccount() {
        int tempAccountId;
        FileTools fileTools = new FileTools();
        tempAccountId = fileTools.GetNextAccountId(getCustomerId());
        fileTools.StoreAccount(getCustomerId(), getType(), getBalance(), (tempAccountId+1));
    }
}

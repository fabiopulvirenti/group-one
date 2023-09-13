package accounts;

import Tools.FileTools;

import java.time.LocalDate;
import java.time.Month;

public class BusinessAccount extends AbstractAccount{
    boolean chargeTaken = true;

    public BusinessAccount(int customerId, float balance, String customerName) {
        super(customerId, balance, customerName, AccountType.BUSINESS);
    }
    // Overload constructor
    // Used for pulling information from an existing account
    public BusinessAccount(int customerId) {
        super(customerId, AccountType.BUSINESS);
    }

    public void storeAccount() {
        FileTools tools = new FileTools();
        tools.StoreAccount(getCustomerId(), getType(), getBalance());
    }

    public void annualCharge() {
        LocalDate currentdate = LocalDate.now();
        if (currentdate.getDayOfMonth() == 1 && currentdate.getMonth() == Month.JANUARY) {
            if (!chargeTaken) {
                setBalance(getBalance() - 120);
                FileTools tools = new FileTools();
                tools.RecordTransaction(getCustomerId(), AccountType.BUSINESS, (getBalance()-120));
                chargeTaken = true;
            }
        } else {
            chargeTaken = false;
        }
    }
}

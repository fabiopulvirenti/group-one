package accounts;

public class PersonalAccount extends AbstractAccount{


    public PersonalAccount(int customerId, float balance, String customerName) {
        super(customerId, balance, customerName, AccountType.PERSONAL);
    }


}

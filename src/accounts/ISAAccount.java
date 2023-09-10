package accounts;

public class ISAAccount extends AbstractAccount{

    private float apr = 2.75f;
    private String isaType;
    private int customerAge;
    private boolean ukResident;
    private boolean crownServant;
    public ISAAccount (int customerID, float balance, String customerName, int customerAge, String isaType, boolean ukResident, boolean crownServant) {
        super(customerID, balance, customerName, AccountType.ISA);

        this.isaType = isaType;
        this.customerAge = customerAge;
        this.ukResident = ukResident;
        this.crownServant = crownServant;
    }

    public String mentallyChallengedApplication(String countryName){
        if (countryName.equals("Northern Ireland")) {
            return ("Application made to the Office of Care and Protection.");
        } else if (countryName.equals("Scotland")) {
            return ("Application made to the Office of the Public Guardian in Scotland.");
        } else {
            return ("Application made to the Court of Protection.");
        }
    }
}

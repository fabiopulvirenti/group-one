package accounts;

import Tools.FileTools;

/**
 * Inherits from the class AbstractAccount. Used to create ISA accounts for customers.
 * Customer details are used to see if they meet the requirements and an account is
 * created if requirements are met.
 */
public class ISAAccount extends AbstractAccount{

    private float apr = 2.75f;
    private String isaType;
    private int customerAge;
    private boolean ukResident;
    private boolean crownServant;

    /**
     * Constructor for the ISAAccount class.
     *
     * @param customerID Customer's ID
     * @param balance The balance in the account
     * @param customerName Customer's name
     * @param customerAge Customer's age
     * @param isaType ISA Account type
     * @param ukResident Whether customer is a UK resident
     * @param crownServant Whether customer is a crown servant
     */
    public ISAAccount (int customerID, float balance, String customerName, int customerAge, String isaType, boolean ukResident, boolean crownServant) {
        super(customerID, balance, customerName, AccountType.ISA);
        this.isaType = isaType;
        this.ukResident = ukResident;
        this.crownServant = crownServant;

        //this line should not be here
        FileTools fileTools = new FileTools();
        // Check if customer already has ISA account
        if (fileTools.AccountExists(customerID, AccountType.ISA)) {
            System.out.println("Access to account granted.");
        }
    }

    /**
     * Check if the customer meets the requirements to open an ISA account.
     *
     * @return True or False dependent on if requirements are met.
     */
    public boolean validateAccount(){
        boolean valid = false;
        if (isaType.equalsIgnoreCase("cash")){
            return ((customerAge>=16) && (ukResident || crownServant));
        } else if (isaType.equalsIgnoreCase("stocks and shares") || isaType.equalsIgnoreCase("innovative finance")) {
            return ((customerAge >= 18) && (ukResident || crownServant));
        } else if (isaType.equalsIgnoreCase("lifetime")) {
            return ((customerAge >= 18 && customerAge < 40) && (ukResident || crownServant));
        }
        return valid;
    }

    /**
     * Make an application to the relevant authorities on behalf of someone "who lacks the mental capacity"
     * to open and manage an account themselves.
     *
     * @param countryName Checking where the customer resides. Process is country dependent.
     * @return Lets user know an application has been made to the relevant authorities.
     */
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

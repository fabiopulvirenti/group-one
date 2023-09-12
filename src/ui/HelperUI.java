package ui;

// this class will implement the helper system
public class HelperUI {

    public void askForHelpMainEntry() {
        System.out.println("Choose option 1. to authenticate the customer by username and password, for existing customer.");
        System.out.println("Choose option 2. to open an account for new customers or existing ones.");
    }

    public void askForHelpOpenAccountMenu() {
        System.out.println("Choose option 1. to create a new customer in the system.");
        System.out.println("Choose option 2. to continue with an existing customer through their authentication.");
    }

    public void askForHelpOpenAccountWithCustomer(){
        System.out.println("Choose option 1. to open a Personal account for the current customer");
        System.out.println("Choose option 2. to open an ISA account for the current customer, if the customer does not have one already.");
        System.out.println("Choose option 3. to open a Business account for the current customer, if the customer does not have one already.");
    }

}

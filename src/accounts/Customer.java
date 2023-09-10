package accounts;


import java.util.Random;

public class Customer {

    private String customerName;

    private String userName;

    private int customerID;

    private String password;


    private boolean validatedPhotoId;

    private boolean validatedAddress;

    public Customer(String customerName, String userName, String password) {
        this.customerName = customerName;
        this.userName = userName;
        this.password = password;
    }
    public boolean isValidatedPhotoId() {
        return validatedPhotoId;
    }

    public void setValidatedPhotoId(boolean validatedPhotoId) {
        this.validatedPhotoId = validatedPhotoId;
    }

    public boolean isValidatedAddress() {
        return validatedAddress;
    }

    public void setValidatedAddress(boolean validatedAddress) {
        this.validatedAddress = validatedAddress;
    }

    public void storeNewCustomer(){
        Random rand = new Random();
        this.customerID = rand.nextInt(10000);
        // TODO: storing the customer into the file

    }

}

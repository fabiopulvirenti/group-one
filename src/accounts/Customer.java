package accounts;


import Tools.FileTools;

import java.util.Random;

public class Customer {

   private String customerName;

    private String userName;

    private int customerID;

    private String password;

    private String yearOfBirth;


    private boolean validatedPhotoId;

    private boolean validatedAddress;

    public Customer(/*String customerName,*/ String userName, String password, String yearOfBirth) {
//        this.customerName = customerName;
        this.userName = userName;
        this.password = password;
        this.yearOfBirth =yearOfBirth;
    }

    public Customer(int customerID, String userName, String password, String yearOfBirth) {
        this.customerID =customerID;
        this.userName = userName;
        this.password = password;
        this.yearOfBirth =yearOfBirth;
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
        FileTools fileTools = new FileTools();
        fileTools.StoreUser(customerID, userName,password);
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}

package accounts;


import Tools.FileTools;

import java.time.LocalDate;

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
        FileTools fileTools = new FileTools();
        this.customerID = (fileTools.GetNextId()+1);

        fileTools.StoreUser(customerID, userName, password, yearOfBirth);

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

    public String getUserName(){
        return this.userName;
    }

    public int getCustomerAge() {
        LocalDate date = LocalDate.now();
        return (date.getYear() - Integer.valueOf(yearOfBirth));
    }
}

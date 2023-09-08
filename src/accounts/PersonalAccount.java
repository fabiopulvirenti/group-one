package accounts;

public class PersonalAccount extends AbstractAccount{

    private boolean validatedPhotoId;

    private boolean validatedAddress;

    public PersonalAccount(int customerId, float balance, String customerName,boolean validatedPhotoId,boolean validatedAddress) {
        super(customerId, balance, customerName, AccountType.PERSONAL);

        this.validatedPhotoId =validatedPhotoId;
        this.validatedAddress =validatedAddress;
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
}

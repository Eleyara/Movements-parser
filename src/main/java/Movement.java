import java.text.SimpleDateFormat;
import java.util.Date;

public class Movement {
    private final String accountType;
    private final String accountNumber;
    private final String currency;
    private final Date movementDate;
    private final String movementReference;
    private final MovementDescription movementDescription;
    private final double income;
    private final double expense;

    private static String dateFormat = "dd.MM.yyyy";

    public Movement(String accountType, String accountNumber, String currency, Date movementDate, String movementReference, String movementDescription, Double income, Double expense){
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.movementDate = movementDate;
        this.movementReference = movementReference;
        this.movementDescription = getDescriptionFromString(movementDescription);
        this.income = income;
        this.expense = expense;
    }

    public String getAccountType(){
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getMovementDate() {
        return movementDate;
    }

    public String getMovementReference() {
        return movementReference;
    }

    public MovementDescription getMovementDescription() {
        return movementDescription;
    }

    public double getIncome() {
        return income;
    }

    public double getExpense() {
        return expense;
    }

    public String ToString(){
        return "Движение: " +
                accountType + " " +
                accountNumber + " " +
                currency + " " +
                new SimpleDateFormat(dateFormat).format(movementDate) + " " +
                movementReference + " " +
                income + " " +
                expense + " " +
                movementDescription.ToString();
    }

    private MovementDescription getDescriptionFromString (String description){

        try {
            description = description.trim();
            String accountNumber = description.substring(0, MovementDescription.accountNumberLength);
            description = removeFromStart(description, MovementDescription.accountNumberLength);
            int codeIndex = description.contains("(") ? description.indexOf("(") : description.length() - MovementDescription.codeLength;
            String code = description.substring(codeIndex);
            description = removeFromEnd(description, code.length());
            String currency = getInfoFromEnd(description, MovementDescription.currencyLength);
            description = removeFromEnd(description, MovementDescription.currencyLength);
            int sumLength = getLastWordLength(description, " ");
            double sum = Double.parseDouble(getInfoFromEnd(description, sumLength));
            description = removeFromEnd(description, sumLength);
            Date operationDate = (new SimpleDateFormat(dateFormat)).parse(getInfoFromEnd(description, MovementDescription.dateLength));
            description = removeFromEnd(description, MovementDescription.dateLength);
            Date innerDate = (new SimpleDateFormat(dateFormat)).parse(getInfoFromEnd(description, MovementDescription.dateLength));
            description = removeFromEnd(description, MovementDescription.dateLength);
            String purpose = getInfoFromEnd(description, getLastWordLength(description, "\\"));
            return new MovementDescription(accountNumber, description, innerDate, operationDate, sum, currency, code, purpose);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return new MovementDescription("", "", new Date(), new Date(), 0, "", "", "");
    }

    public static String removeFromStart(String description, int length){
        return description.substring(length + 1).trim();
    }

    public static String getInfoFromEnd(String description, int length){
        return description.substring(description.length() - length);
    }

    public static String removeFromEnd(String description, int length){
        return description.substring(0, description.length() - length - 1).trim();
    }

    public static int getLastWordLength(String description, String separator){
        return description.length() - description.lastIndexOf(separator) - 1;
    }


}

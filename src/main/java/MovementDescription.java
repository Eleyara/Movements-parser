import java.text.SimpleDateFormat;
import java.util.Date;

public class MovementDescription {

    private final String accountNumber;
    private final String details;
    private final Date innerDate;
    private final Date operationDate;
    private final double sum;
    private final String currency;
    private final String code;
    private final String purpose;

    private String dateFormat = "dd.MM.yyyy";

    public final static int accountNumberLength = 16;
    public final static int codeLength = 7;
    public final static int currencyLength = 3;
    public final static int dateLength = 8;

    public MovementDescription(String accountNumber, String details, Date innerDate, Date operationDate, double sum, String currency, String code, String purpose){
        this.accountNumber = accountNumber;
        this.details = details;
        this.innerDate = innerDate;
        this.operationDate = operationDate;
        this.sum = sum;
        this.currency = currency;
        this.code = code;
        this.purpose = purpose;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getDetails() {
        return details;
    }

    public Date getInnerDate() {
        return innerDate;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public double getSum() {
        return sum;
    }


    public String getCurrency() {
        return currency;
    }

    public String getCode() {
        return code;
    }

    public int getAccountNumberSymbolsAmount() {
        return accountNumberLength;
    }

    public int getCodeSymbolsAmount() {
        return codeLength;
    }

    public String getPurpose() {
        return purpose;
    }

    public String ToString(){
        return "*Details: " +
                accountNumber + " " +
                details + " " +
                new SimpleDateFormat(dateFormat).format(innerDate) + " " +
                new SimpleDateFormat(dateFormat).format(operationDate) + " " +
                sum + " " +
                currency + " " +
                code + " *" +
                "Purpose: " +
                purpose;

    }
}

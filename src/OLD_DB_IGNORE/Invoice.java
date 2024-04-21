package OLD_DB_IGNORE;
/*class for: invoices
 * student name: abigail murray
 * student number: c00260073*/
import java.math.BigDecimal;
import java.sql.Date;

public class Invoice {
    private int invoiceID;
    private int customerID;
    private Date invoiceDate;
    private BigDecimal invoiceAmount;

    /*constructor methods*/
    public Invoice() {}

    public Invoice(int invoiceID, int customerID, Date invoiceDate, BigDecimal invoiceAmount) {
        this.invoiceID = invoiceID;
        this.customerID = customerID;
        this.invoiceDate = invoiceDate;
        this.invoiceAmount = invoiceAmount;
    }

    // Getters and setters
    //invoiceID
    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }
    //customerID
    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    //invoiceDate
    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    //invoiceAmount
    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
}//end class

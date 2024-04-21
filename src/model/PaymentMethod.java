package model;

public class PaymentMethod {

    private int paymentMethodID;
    private int customerID;
    private String cardNumber;
    private String expiry;
    private String securityCode;
    private String nameOnCard;

    //constructors
    public PaymentMethod(){

    }

    public PaymentMethod(int paymentMethodID, int customerID, String cardNumber, String expiry, String securityCode, String nameOnCard){
        this.paymentMethodID= paymentMethodID;
        this.customerID= customerID;
        this.cardNumber= cardNumber;
        this.expiry = expiry;
        this.securityCode= securityCode;
        this.nameOnCard= nameOnCard;
    }

    //getter and setter methods

    public int getCustomerID(){
        return customerID;
    }
    public void setCustomerID(int customerID){
        this.customerID= customerID;
    }

    public int getPaymentMethodID(){
        return paymentMethodID;
    }
    public void setPaymentMethodID(int paymentMethodID){
        this.paymentMethodID= paymentMethodID;
    }

    public String getCardNumber(){
        return cardNumber;
    }
    public void setCardNumber(String cardNumber){
        this.cardNumber= cardNumber;
    }

    public String getExpiry(){
        return expiry;
    }
    public  void setExpiry(String expiry){
        this.expiry= expiry;
    }

    public String getSecurityCode(){
        return securityCode;
    }
    public void setSecurityCode(String securityCode){
        this.securityCode= securityCode;
    }

    public String getNameOnCard (){
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard){
        this.nameOnCard= nameOnCard;
    }


    // toString method for debugging purposes
    @Override
    public String toString() {
        return "" +
                "card Number=" + cardNumber
              ;
    }
}//end payment class

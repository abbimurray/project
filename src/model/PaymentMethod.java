package model;

public class PaymentMethod {

    private int paymentMethodID;
    private int customerID;
    private String cardNumber;
    private String expiry;
    private String securityCode;
    private String nameOnCard;

    //constructors

    /**
     * Default constructor for payment method - takes no parameters
     */
    public PaymentMethod(){

    }

    /**
     * Constructor for payment methods which takes parameters
     * @param paymentMethodID
     * @param customerID
     * @param cardNumber
     * @param expiry
     * @param securityCode
     * @param nameOnCard
     */

    public PaymentMethod(int paymentMethodID, int customerID, String cardNumber, String expiry, String securityCode, String nameOnCard){
        this.paymentMethodID= paymentMethodID;
        this.customerID= customerID;
        this.cardNumber= cardNumber;
        this.expiry = expiry;
        this.securityCode= securityCode;
        this.nameOnCard= nameOnCard;
    }

    //getter and setter methods

    /**
     * Getter method - returns customerID related to the payment method
     * @return customerID
     */
    public int getCustomerID(){
        return customerID;
    }

    /**
     * Setter method to set the customerID- related to payment method
     * @param customerID
     */
    public void setCustomerID(int customerID){
        this.customerID= customerID;
    }

    /**
     * Getter method to return paymentMethodID
     * @return paymentMethodID
     */
    public int getPaymentMethodID(){
        return paymentMethodID;
    }

    /**
     * Setter method to set the paymentID
     * @param paymentMethodID
     */
    public void setPaymentMethodID(int paymentMethodID){
        this.paymentMethodID= paymentMethodID;
    }


    /**
     * Getter method to get the card number
     * @return cardNumber
     */
    public String getCardNumber(){
        return cardNumber;
    }

    /**
     * setter method to set the card number
     * @param cardNumber
     */
    public void setCardNumber(String cardNumber){
        this.cardNumber= cardNumber;
    }


    /**
     * Getter method to get the expiry date of the payment method
     * @return expiry MM/YY
     */
    public String getExpiry(){
        return expiry;
    }

    /**
     * Setter method to set the expiry of the card
     * @param expiry
     */
    public  void setExpiry(String expiry){
        this.expiry= expiry;
    }


    /**
     * getter method to get the security code
     * @return security code
     */
    public String getSecurityCode(){
        return securityCode;
    }

    /**
     * setter method to set the security code of the payment method
     * @param securityCode
     */
    public void setSecurityCode(String securityCode){
        this.securityCode= securityCode;
    }


    /**
     * getter method to get the name on the card of the payment method
     * @return nameOnCard
     */
    public String getNameOnCard (){
        return nameOnCard;
    }


    /**
     * SETTER method to set the name on the card - payment method
     * @param nameOnCard
     */
    public void setNameOnCard(String nameOnCard){
        this.nameOnCard= nameOnCard;
    }


    // toString method for debugging purposes

    /**
     * to string method for payment method
     * @return card number
     */
    @Override
    public String toString() {
        return "" +
                "card Number=" + cardNumber
              ;
    }
}//end payment class

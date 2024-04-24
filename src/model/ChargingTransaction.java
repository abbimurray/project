//Student number:C00260073, Student name: Abigail Murray, Semester two

package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ChargingTransaction {
    private int transactionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal energyConsumed;
    private BigDecimal totalCost;
    private int chargerID;
    private int customerID;

    /**
     * Constructor method for charging transactions
     * @param transactionId
     * @param startTime
     * @param endTime
     * @param energyConsumed
     * @param totalCost
     * @param chargerID
     * @param customerID
     */
    // Constructor
    public ChargingTransaction(int transactionId, LocalDateTime startTime, LocalDateTime endTime, BigDecimal energyConsumed, BigDecimal totalCost, int chargerID, int customerID) {
        this.transactionId = transactionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.energyConsumed = energyConsumed;
        this.totalCost = totalCost;
        this.chargerID = chargerID;
        this.customerID = customerID;
    }

    // Getters

    /**
     * getter method to get the transctionID
     * @return transactionID
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * getter method to get the starttime of the transaction
     * @return startTime of transaction
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * getter method to return the endTime of the transaction
     * @return endTime of the transaction
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * getter method to return the energy consumed during a charging session
     * @return energyConsumed
     */
    public BigDecimal getEnergyConsumed() {
        return energyConsumed;
    }

    /**
     * getter method to get the total cost of the charging session
     * @return totalCost
     */
    public BigDecimal getTotalCost() {
        return totalCost;
    }

    /**
     * getter method to return the charger id
     * @return chargerID
     */
    public int getChargerID() {
        return chargerID;
    }

    /**
     * get the customner id of customer used for  charging transactions
     * @return CcustomerID
     */
    public int getCustomerID() {
        return customerID;
    }

}//end  class

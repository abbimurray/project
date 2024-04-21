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
    public int getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public BigDecimal getEnergyConsumed() {
        return energyConsumed;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public int getChargerID() {
        return chargerID;
    }

    public int getCustomerID() {
        return customerID;
    }

}//end  class

package controller;

import model.ChargingStationModel;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class ChargerController {
    private ChargingStationModel model;

    public ChargerController(ChargingStationModel model) {
        this.model = model;
    }


   public boolean startChargingSession(int chargerID, int customerID) {
       if (model.checkChargerAvailability(chargerID)) {
           LocalDateTime sessionStartTime = LocalDateTime.now();//set start time to now
           BigDecimal rate = model.fetchChargerCostPerKWH(chargerID); //get the rate

           int transactionID = model.createChargingTransaction(sessionStartTime, chargerID, customerID,rate);
           if (transactionID != -1) {
               if (model.updateChargerStatus(chargerID, "In-Use", sessionStartTime, customerID)) {
                   System.out.println("Session started for charger " + chargerID + " and customer " + customerID);
                   return true;
               }
           }
       }
       System.out.println("Session could not be started for charger " + chargerID);
       return false;
   }

    public boolean endChargingSession(int chargerID, int transactionID) {
        LocalDateTime startTime = model.fetchStartTimeForTransaction(transactionID);
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal durationHours = model.calculateDurationHours(startTime, endTime);
        BigDecimal energyConsumed = model.calculateEnergyConsumed(durationHours, chargerID);
        BigDecimal totalCost = model.calculateTotalCost(energyConsumed, chargerID);
        model.updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost);
        model.updateChargerStatus(chargerID, "Available", null, null);
        System.out.println("Session ended for charger " + chargerID + " with transaction ID " + transactionID);
        return true;
    }

}//END CLASS


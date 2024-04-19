package mvc_view;

import model.ChargingStationModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class StartSessionForm extends JFrame {
    private Timer timer;
    private LocalDateTime startTime;
    private int chargerID;
    private ChargingStationModel model;
    private int customerID;
    private int transactionID;

    public StartSessionForm(int customerID, int chargerID, ChargingStationModel model) {
        this.customerID = customerID;
        this.chargerID = chargerID;
        this.model = model;
        this.startTime = LocalDateTime.now();
        this.transactionID = model.startChargingSession(chargerID, customerID);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 100);
        setLayout(new BorderLayout());

        JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        add(timerLabel, BorderLayout.CENTER);

        JButton stopButton = new JButton("Stop Session");
        stopButton.addActionListener(e -> endSession());
        add(stopButton, BorderLayout.SOUTH);

        timer = new Timer(1000, e -> {
            Duration duration = Duration.between(startTime, LocalDateTime.now());
            timerLabel.setText(String.format("Session Duration: %02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart()));
            if (duration.toMinutes() >= 10) {  // End session after 10 minutes
                timer.stop();
                endSession();
            }
        });
        timer.start();
        setVisible(true);
    }

    private void endSession() {
        timer.stop();
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal durationHours = model.calculateDurationHours(startTime, endTime);
        BigDecimal energyConsumed = model.calculateEnergyConsumed(durationHours, chargerID);
        BigDecimal totalCost = model.calculateTotalCost(energyConsumed, chargerID);
        model.updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost);
        model.updateChargerStatus(chargerID, "Available", null, null);
        JOptionPane.showMessageDialog(this, "Thank you for charging, your total cost is: " + totalCost);
        dispose();
        new FindChargingStationForm().setVisible(true);  // Assuming this is the correct way to navigate back
    }




}


/*
import model.ChargingStationModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class StartSessionForm extends JFrame {
    private Timer timer;
    private LocalDateTime startTime;
    private int chargerID;
    private ChargingStationModel model;
    private int customerID;
    private int transactionID;

    public StartSessionForm(int customerID, int chargerID, ChargingStationModel model) {
        this.customerID = customerID;
        this.chargerID = chargerID;
        this.model = model;
        this.startTime = LocalDateTime.now();
        this.transactionID = startChargingTransaction();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 100);
        setLayout(new BorderLayout());

        JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        add(timerLabel, BorderLayout.CENTER);

        JButton stopButton = new JButton("Stop Session");
        stopButton.addActionListener(e -> endSession());
        add(stopButton, BorderLayout.SOUTH);

        timer = new Timer(1000, e -> {
            Duration duration = Duration.between(startTime, LocalDateTime.now());
            timerLabel.setText(String.format("Session Duration: %02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart()));
            if (duration.toHours() >= 1) {
                timer.stop();
                endSession();
            }
        });
        timer.start();
        setVisible(true);
    }

    private int startChargingTransaction() {
        model.updateChargerStatus(chargerID, "In-Use", startTime, customerID);
        return 1;
    }

    private void endSession() {
        timer.stop();
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal energyConsumed = new BigDecimal(calculateEnergyConsumed(startTime, endTime));
        BigDecimal totalCost = calculateCost(energyConsumed);
        model.updateChargerStatus(chargerID, "Available", null, null);
        model.updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost);
        dispose();
    }

    private double calculateEnergyConsumed(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return (duration.getSeconds() / 3600.0) * 11.0;  // Assuming 11 kW/h
    }

    private BigDecimal calculateCost(BigDecimal energyConsumed) {
        BigDecimal rate = new BigDecimal("0.682");
        return energyConsumed.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
*/
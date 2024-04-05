package mvc_view;
import controller.ChargerRatePower;
import model.ChargingStationModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class StartSessionForm extends JFrame {
    private LocalDateTime startTime;
    private Timer timer;
    private JLabel timerLabel;

    private int chargerID;
    private int customerID;

    private ChargingStationModel model;
    private int transactionID;

    public StartSessionForm(int customerID, int chargerID, ChargingStationModel model) {
        this.customerID = customerID;
        this.chargerID = chargerID;
        this.model = model;
        this.transactionID = model.startSession(chargerID, customerID); // Assuming this returns the transaction ID
        initializeUI();
    }


    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new FlowLayout());

        timerLabel = new JLabel("00:00:00");
        add(timerLabel);

        JButton stopSessionButton = new JButton("Stop Session");
        stopSessionButton.addActionListener(this::stopSession);
        add(stopSessionButton);

        setVisible(true);
        startSession();
    }

    private void startSession() {
        startTime = LocalDateTime.now();
        // Start the timer to update the stopwatch every second
        timer = new Timer(1000, e -> updateTimer());
        timer.start();

    }


    private void updateTimer() {
        Duration duration = Duration.between(startTime, LocalDateTime.now());
        long seconds = duration.getSeconds();
        timerLabel.setText(String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60));
    }

    private BigDecimal calculateCost(Duration duration) {
        // Example calculation: cost per kWh is a fixed value, and energy consumption rate is based on the charger type
        BigDecimal costPerKWH = new BigDecimal("0.15"); // Example cost
        BigDecimal hours = new BigDecimal(duration.getSeconds()).divide(new BigDecimal(3600), 2, BigDecimal.ROUND_HALF_UP);
        return costPerKWH.multiply(hours); // Returns the total cost
    }


    private void stopSession(ActionEvent event) {

        timer.stop();
        LocalDateTime endTime = LocalDateTime.now();

        // Use the model instance to calculate duration hours
        BigDecimal durationHours = model.calculateDurationHours(startTime, endTime);

        // Assuming you need chargerID, ensure it's available in this scope
        ChargerRatePower ratePower = model.fetchChargerRateAndPower(chargerID);
        BigDecimal energyConsumed = ratePower.getKw().multiply(durationHours);
        BigDecimal totalCost = energyConsumed.multiply(ratePower.getCostPerKWH());

        // Now use the model instance to update the transaction
        model.updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost);

        JOptionPane.showMessageDialog(this, "Session Ended. Total Cost: " + totalCost);
        this.dispose(); // Optionally, navigate back to StationDetailsForm
    }
}
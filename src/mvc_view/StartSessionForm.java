package mvc_view;

import model.ChargingStationModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Duration;

public class StartSessionForm extends JFrame {
    private Timer timer;
    private LocalDateTime startTime;
    private int chargerID;
    private int customerID;
    private int transactionID;
    private ChargingStationModel model;
    private JLabel timerLabel;  // JLabel to display the timer

    public StartSessionForm(int customerID, int chargerID, ChargingStationModel model) {
        this.customerID = customerID;
        this.chargerID = chargerID;
        this.model = model;
        setupUI();
    }

    private void setupUI() {
        setTitle("Start Session");
        setSize(300, 200);  // Adjusted the size to better fit components
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton startButton = new JButton("Start Session");
        JButton endButton = new JButton("End Session");
        endButton.setEnabled(false); // Disable end session button until session starts

        timerLabel = new JLabel("00:00:00");  // Initialize the timer label
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));

        startButton.addActionListener(e -> startSession(endButton, startButton));
        endButton.addActionListener(this::endSession);

        add(timerLabel);
        add(startButton);
        add(endButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startSession(JButton endButton, JButton startButton) {
        if (model.checkChargerAvailability(chargerID)) {
            startTime = LocalDateTime.now();
            BigDecimal rate = model.fetchChargerCostPerKWH(chargerID);
            transactionID = model.createChargingTransaction(startTime, chargerID, customerID, rate);
            if (transactionID != -1 && model.updateChargerStatus(chargerID, "In-Use", startTime, customerID)) {
                startButton.setEnabled(false);
                endButton.setEnabled(true);
                startTimer(endButton);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to start session.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Charger is currently unavailable.", "Unavailable", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void endSession(ActionEvent e) {
        if (timer != null) {
            timer.stop();
        }
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal durationHours = model.calculateDurationHours(startTime, endTime);
        BigDecimal energyConsumed = model.calculateEnergyConsumed(durationHours, chargerID);
        BigDecimal totalCost = model.calculateTotalCost(energyConsumed, chargerID);

        model.updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost);
        model.updateChargerStatus(chargerID, "Available", null, null);

        JOptionPane.showMessageDialog(this, "Session ended. Total Cost: " + totalCost);
        dispose(); // Close the window
    }

    private void startTimer(JButton endButton) {
        timer = new Timer(1000, e -> updateTimerDisplay());  // Update every second
        timer.start();
        endButton.addActionListener(e -> {
            timer.stop();
            endSession(e);
        });
    }

    private void updateTimerDisplay() {
        Duration duration = Duration.between(startTime, LocalDateTime.now());
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

}//end





/*package mvc_view;

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

    /*public StartSessionForm(int customerID, int chargerID, ChargingStationModel model) {
        this.customerID = customerID;
        this.chargerID = chargerID;
        this.model = model;
        this.startTime = LocalDateTime.now();//change this to irish time?
        this.transactionID = model.startChargingSession(chargerID, customerID);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        add(timerLabel, BorderLayout.CENTER);

        JButton stopButton = new JButton("Stop Session");
        stopButton.addActionListener(e -> endSession());
        add(stopButton, BorderLayout.SOUTH);

        timer = new Timer(1000, e -> {
            Duration duration = Duration.between(startTime, LocalDateTime.now());
            timerLabel.setText(String.format("Session Duration: %02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart()));
            if (duration.toMinutes() >= 5) {  // End session after 5 minutes-- just for display of concept-- would be after 1 hour IRL
                timer.stop();
                endSession();
            }
        });
        timer.start();
        setVisible(true);
    }
    public void startSession() {
        try {
            // Set start time to now
            LocalDateTime sessionStartTime = LocalDateTime.now();
            // Fetch rate based on chargerID
            BigDecimal rate = model.fetchChargerCostPerKWH(chargerID);

            // Attempt to start the charging session and create a transaction
            int transactionID = model.startChargingSession(chargerID, customerID, sessionStartTime, rate);

            if (transactionID != -1) {
                this.transactionID = transactionID;  // Set the global transactionID for this form
                // Update UI to reflect the session start
                JOptionPane.showMessageDialog(this, "Session started successfully with Transaction ID: " + transactionID);
                // Start the timer to update the session duration
                startTime = sessionStartTime;
                timer.start();
            } else {
                // Inform the user of failure
                JOptionPane.showMessageDialog(this, "Failed to start session, please try again or contact support.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during session start: " + e.getMessage());
        }
    }*/
/*

    public StartSessionForm(int customerID, int chargerID, ChargingStationModel model) {
        this.customerID = customerID;
        this.chargerID = chargerID;
        this.model = model;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        add(timerLabel, BorderLayout.CENTER);

        // Start Session button
        JButton startButton = new JButton("Start Session");
        startButton.addActionListener(e -> startSession(timerLabel));
        add(startButton, BorderLayout.NORTH);

        JButton stopButton = new JButton("Stop Session");
        stopButton.addActionListener(e -> endSession());
        stopButton.setEnabled(false); // Disabled until session starts
        add(stopButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void startSession(JLabel timerLabel) {
        LocalDateTime sessionStartTime = LocalDateTime.now();
        BigDecimal rate = model.fetchChargerCostPerKWH(chargerID);
        int transactionID = model.startChargingSession(chargerID, customerID, sessionStartTime, rate);

        if (transactionID != -1) {
            this.transactionID = transactionID;
            this.startTime = sessionStartTime;
            JOptionPane.showMessageDialog(this, "Session started successfully with Transaction ID: " + transactionID);
            timer = new Timer(1000, e -> {
                Duration duration = Duration.between(startTime, LocalDateTime.now());
                timerLabel.setText(String.format("Session Duration: %02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart()));
            });
            timer.start();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to start session, please try again or contact support.");
        }
    }
    private void endSession() {
        timer.stop();
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal durationHours = model.calculateDurationHours(startTime, endTime); //calculate duration in hours
        BigDecimal energyConsumed = model.calculateEnergyConsumed(durationHours, chargerID);//calc energy consumed
        BigDecimal totalCost = model.calculateTotalCost(energyConsumed, chargerID);//calc totalcost of transaction
        model.updateChargingTransaction(transactionID, endTime, energyConsumed, totalCost);//update chargerTransaction
        model.updateChargerStatus(chargerID, "Available", null, null);//set status and other fields back in chargers
        System.out.print("updated using model.updatechargingstation and updatechargerstatus");

        JOptionPane.showMessageDialog(this, "Thank you for charging, your total cost is: " + totalCost);
        dispose();//dispose this form
        new FindChargingStationForm().setVisible(true);  //back to findchargingstationform
    }*/
//end of startsessionform class

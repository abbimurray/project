//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;

import controller.UserSession;
import model.ChargingStationModel;
import utils.UIUtils;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Duration;

public class StartSessionForm extends JFrame {
    private Timer timer; //timer
    private LocalDateTime startTime;
    private int chargerID;
    private int customerID;
    private int transactionID;
    private ChargingStationModel model;
    private JLabel timerLabel;  // to display the timer

    //constructor method
    public StartSessionForm(int customerID, int chargerID, ChargingStationModel model) {
        this.customerID = customerID;
        this.chargerID = chargerID;
        this.model = model;
        setupUI();
    }
    private void setupUI() {
        setTitle("Manage Session");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }


    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));  // Mint green background

        ImageIcon stationIcon = new ImageIcon("src/images/charging-station.png");
        JLabel iconLabel = new JLabel(stationIcon);
        headerPanel.add(iconLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Manage Session", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Sign out icon
        ImageIcon signOutIcon = new ImageIcon("src/images/log-out.png");
        JLabel signOutLabel = new JLabel(signOutIcon);
        signOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signOutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserSession.getInstance().clearSession();
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        headerPanel.add(signOutLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new FlowLayout());
        mainPanel.setBackground(Color.WHITE);
        JButton startButton = new JButton("Start Session");
        UIUtils.customizeButton(startButton);
        JButton endButton = new JButton("End Session");
        UIUtils.customizeButton(endButton);
        endButton.setEnabled(false);

        timerLabel = new JLabel("00:00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));

        startButton.addActionListener(e -> startSession(endButton, startButton));
        endButton.addActionListener(this::endSession);

        mainPanel.add(timerLabel);
        mainPanel.add(startButton);
        mainPanel.add(endButton);

        return mainPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        JButton backButton = new JButton("Go Back");
        UIUtils.customizeButton(backButton);
        backButton.addActionListener(e -> {
            dispose(); // Assuming you have a previous form to return to
        });
        footerPanel.add(backButton);
        return footerPanel;
    }

    //method for start
    /**
     * Initiates a charging session if the charger is available.
     *
     * checks the availability of a specified charger. If available, it records the start time,
     * fetches the charging rate, and creates a charging transaction. It then updates the charger's status to "In-Use".
     * Upon successful initiation, it disables the start button, enables the end button, and starts the session timer.
     * If any part of the process fails, it displays an error message.
     *
     * @param endButton The button to end the charging session, which is enabled upon successful session start.
     * @param startButton The button to start the charging session, which is disabled when the session starts.
     */
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


    //method for end session
    /**
     * Ends the ongoing charging session and calculates the cost based on the duration and energy consumed.
     *
     * This method stops the timer if it's running, records the end time, and calculates the total duration,
     * energy consumed, and cost of the session. It then updates the transaction and charger status in the database.
     * Finally, it displays the total cost to the user and closes the session window.
     *
     * @param e The ActionEvent triggered by the end session button, used to stop the session timer and update transaction details.
     */
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

        JOptionPane.showMessageDialog(this, "Session ended. Total Cost: €" + totalCost);
        dispose(); // Close the window
    }


    /**
     * Starts a timer that updates a display every second.
     *
     * triggers an update to the timer display every second.
     * adds an action listener to the end session button to stop the timer and end the session when clicked.
     *
     * @param endButton The end session button that stops the timer and triggers session ending.
     */
    private void startTimer(JButton endButton) {
        timer = new Timer(1000, e -> updateTimerDisplay());  // Update every second
        timer.start();
        endButton.addActionListener(e -> {
            timer.stop();
            endSession(e);
        });
    }



    Yes, adding these methods to the Javadoc documentation is important, especially since they manage significant functionalities related to starting and ending charging sessions, handling timers, and updating UI elements. Documenting these methods helps ensure that the codebase is easily understandable and maintainable. Here's how you could document each of these methods:

    Documentation for startSession:
    java
    Copy code
    /**
     * Initiates a charging session if the charger is available.
     *
     * This method checks the availability of a specified charger. If available, it records the start time,
     * fetches the charging rate, and creates a charging transaction. It then updates the charger's status to "In-Use".
     * Upon successful initiation, it disables the start button, enables the end button, and starts the session timer.
     * If any part of the process fails, it displays an error message.
     *
     * @param endButton The button to end the charging session, which is enabled upon successful session start.
     * @param startButton The button to start the charging session, which is disabled when the session starts.
     */
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
    Documentation for endSession:
    java
    Copy code
    /**
     * Ends the ongoing charging session and calculates the cost based on the duration and energy consumed.
     *
     * This method stops the timer if it's running, records the end time, and calculates the total duration,
     * energy consumed, and cost of the session. It then updates the transaction and charger status in the database.
     * Finally, it displays the total cost to the user and closes the session window.
     *
     * @param e The ActionEvent triggered by the end session button, used to stop the session timer and update transaction details.
     */
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

        JOptionPane.showMessageDialog(this, "Session ended. Total Cost: €" + totalCost);
        dispose(); // Close the window
    }
    Documentation for startTimer and updateTimerDisplay:
    java
    Copy code
    /**
     * Starts a timer that updates a display every second.
     *
     * This method initializes a Timer that triggers an update to the timer display every second.
     * It also adds an action listener to the end session button to stop the timer and end the session when clicked.
     *
     * @param endButton The end session button that stops the timer and triggers session ending.
     */
    private void startTimer(JButton endButton) {
        timer = new Timer(1000, e -> updateTimerDisplay());  // Update every second
        timer.start();
        endButton.addActionListener(e -> {
            timer.stop();
            endSession(e);
        });
    }

    /**
     * Updates the timer display with the time since the session started.
     *
     * This method calculates the duration from the start time to the current time and updates the timer label
     * on the UI to show elapsed hours, minutes, and seconds in HH:mm:ss format.
     */
    private void updateTimerDisplay() {
        Duration duration = Duration.between(startTime, LocalDateTime.now());
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

}//end start session class




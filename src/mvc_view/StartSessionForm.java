package mvc_view;

import controller.UserSession;
import model.ChargingStationModel;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

}//end startsessionform




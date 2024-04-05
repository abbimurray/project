package mvc_view;

import controller.UserSession;
import controller.ChargerController;
import model.Charger;
import model.ChargingStation;
import model.ChargingStationModel;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StationDetailsForm extends JFrame {
    private ChargingStation selectedStation;
    private List<Charger> chargers;
    private ChargerController chargerController;

    private ChargingStationModel model;

    public StationDetailsForm(ChargingStation selectedStation) {
        this.selectedStation = selectedStation;
        this.model = new ChargingStationModel(); // Initialize the model
        this.chargerController = new ChargerController(model);
        //this.chargerController = new ChargerController(new ChargingStationModel());
        setTitle("Station Details - " + selectedStation.getAddress());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE); // Set background colour to white
        fetchChargersForStation(); // Ensure this is called before initializeComponents
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createChargersPanel(), BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Stations List");
        UIUtils.customizeButton(backButton); // Customize button appearance
        backButton.addActionListener(e -> {
            this.dispose(); // Close the current StationDetailsForm
            EventQueue.invokeLater(() -> {
                FindChargingStationForm findChargingStationForm = new FindChargingStationForm();
                findChargingStationForm.setVisible(true); // Show the FindChargingStationForm
            });
        });

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        footerPanel.add(backButton);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green background

        // Icon on the left
        ImageIcon stationIcon = new ImageIcon("src/images/charging-station.png");
        JLabel iconLabel = new JLabel(stationIcon);
        headerPanel.add(iconLabel, BorderLayout.WEST);

        // Title in the center
        JLabel titleLabel = new JLabel("Station Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);


        // Sign Out Icon on the right corner
        ImageIcon signOutIcon = new ImageIcon("src/images/log-out.png");
        JLabel signOutLabel = new JLabel(signOutIcon);
        signOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signOutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Logout action
                UserSession.getInstance().clearSession(); // Clear user session
                dispose(); // Close the current form
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true); // Show the login form again
            }
        });

        headerPanel.add(signOutLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createChargersPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (chargers.isEmpty()) {
            panel.add(new JLabel("No chargers available at this station."));
            return panel;
        }

        for (Charger charger : chargers) {
            JPanel chargerPanel = new JPanel();
            chargerPanel.setBackground(Color.WHITE);
            chargerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel chargerLabel = new JLabel("Charger ID: " + charger.getChargerID() + ", Type: " + charger.getChargerType() + ", Status: " + charger.getStatus() + ", Power: " + charger.getKw() + " kW, Cost: " + charger.getCostPerKWH() + " per kWh");
            chargerPanel.add(chargerLabel);

            /*JButton startSessionButton = new JButton("Start Session");
            startSessionButton.addActionListener(e -> ChargingStationModel.startSession(charger));
            UIUtils.customizeButton(startSessionButton); // Apply custom styles
            chargerPanel.add(startSessionButton);*/

            JButton startSessionButton = new JButton("Start Session");


            startSessionButton.addActionListener(e -> {
                int customerID = UserSession.getInstance().getCustomerID(); // Assuming you have a method to get this
                if (chargerController.startChargingSession(charger.getChargerID(), customerID)) {
                    StartSessionForm startSessionForm = new StartSessionForm(customerID, charger.getChargerID(), model);
                    startSessionForm.setVisible(true);
                    StationDetailsForm.this.dispose(); // Close the current form
                } else {
                    JOptionPane.showMessageDialog(this, "Charger is currently unavailable.", "Session Not Started", JOptionPane.ERROR_MESSAGE);
                }
            });

            chargerPanel.add(startSessionButton);



            panel.add(chargerPanel);
        }

        return panel;
    }


    private void fetchChargersForStation() {
        // This method should fetch chargers for the selected station
        ChargingStationModel model = new ChargingStationModel();
        this.chargers = model.getChargersByStationId(selectedStation.getStationID());
    }
}
    /*private void handleChargerAction(Charger charger) {
        if ("Available".equals(charger.getStatus())) {
            // Assuming StartSessionForm takes a Charger object and the StationDetailsForm itself (for callback purposes)
            StartSessionForm startSessionForm = new StartSessionForm(customerID,chargerID);
            startSessionForm.setVisible(true);
            this.setVisible(false); // Optionally hide the StationDetailsForm
        } else {
            // For "Reserve" action or others
            JOptionPane.showMessageDialog(this, "Charger is not available for starting a session.");
        }
    }*/




/*private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Station ID: " + selectedStation.getStationID()));
        panel.add(new JLabel("Address: " + selectedStation.getAddress()));
        panel.add(new JLabel("County: " + selectedStation.getCounty()));
        panel.add(new JLabel("Number of Chargers: " + selectedStation.getNumberOfChargers()));
        return panel;
    }*/

    /*private JPanel createChargersPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (chargers.isEmpty()) {
            panel.add(new JLabel("No chargers available at this station."));
            return panel;
        }

        for (Charger charger : chargers) {
            JPanel chargerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            chargerPanel.setBackground(Color.WHITE);
            chargerPanel.add(new JLabel("Charger ID: " + charger.getChargerID() + ", Type: " + charger.getChargerType() + ", Status: " + charger.getStatus() + ", Power: " + charger.getKw() + " kW, Cost: " + charger.getCostPerKWH() + " per kWh"));

            JButton actionButton = new JButton(charger.getStatus().equals("Available") ? "Start Session" : "Reserve");
            actionButton.addActionListener(e -> handleChargerAction(charger));
            chargerPanel.add(actionButton);
            UIUtils.customizeButton(actionButton); // Apply custom styles
            panel.add(chargerPanel);
        }

        return panel;
    }*/




    /*private void handleChargerAction(Charger charger) {
        // Implement action handling for starting a session or reserving a charger
        String message = charger.getStatus().equals("Available") ? "Starting session for Charger ID: " + charger.getChargerID() : "Reserving Charger ID: " + charger.getChargerID();
        JOptionPane.showMessageDialog(this, message);
    }*/


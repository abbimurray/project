package mvc_view;

import controller.UserSession;
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

    public StationDetailsForm(ChargingStation selectedStation) {
        this.selectedStation = selectedStation;
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
/*
    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Station ID: " + selectedStation.getStationID()));
        panel.add(new JLabel("Address: " + selectedStation.getAddress()));
        panel.add(new JLabel("County: " + selectedStation.getCounty()));
        panel.add(new JLabel("Number of Chargers: " + selectedStation.getNumberOfChargers()));
        return panel;
    }*/

    private JPanel createChargersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (chargers.isEmpty()) {
            panel.add(new JLabel("No chargers available at this station."));
            return panel;
        }

        for (Charger charger : chargers) {
            JPanel chargerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            chargerPanel.add(new JLabel("Charger ID: " + charger.getChargerID() + ", Type: " + charger.getChargerType() + ", Status: " + charger.getStatus() + ", Power: " + charger.getKw() + " kW, Cost: " + charger.getCostPerKWH() + " per kWh"));

            JButton actionButton = new JButton(charger.getStatus().equals("Available") ? "Start Session" : "Reserve");
            actionButton.addActionListener(e -> handleChargerAction(charger));
            chargerPanel.add(actionButton);

            panel.add(chargerPanel);
        }

        return panel;
    }


    private void fetchChargersForStation() {
        // This method should fetch chargers for the selected station
        ChargingStationModel model = new ChargingStationModel();
        this.chargers = model.getChargersByStationId(selectedStation.getStationID());
    }

    private void handleChargerAction(Charger charger) {
        // Implement action handling for starting a session or reserving a charger
        String message = charger.getStatus().equals("Available") ? "Starting session for Charger ID: " + charger.getChargerID() : "Reserving Charger ID: " + charger.getChargerID();
        JOptionPane.showMessageDialog(this, message);
    }

}
package mvc_view;

import model.Charger;
import model.ChargingStation;
import model.ChargingStationModel;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
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

        fetchChargersForStation(); // Ensure this is called before initializeComponents
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        // Now that chargers is initialized, the rest of the method can safely access it
        add(createHeaderPanel(), BorderLayout.NORTH);  // Header Panel with Station Details
        add(createChargersPanel(), BorderLayout.CENTER);// Main Content Panel with Chargers Details

        // Footer Panel with Back Button
        JButton backButton = new JButton("Back to Stations List");
        backButton.addActionListener(e -> this.dispose());
        JPanel footerPanel = new JPanel();
        footerPanel.add(backButton);
        add(footerPanel, BorderLayout.SOUTH);

    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Station ID: " + selectedStation.getStationID()));
        panel.add(new JLabel("Address: " + selectedStation.getAddress()));
        panel.add(new JLabel("County: " + selectedStation.getCounty()));
        panel.add(new JLabel("Number of Chargers: " + selectedStation.getNumberOfChargers()));
        return panel;
    }

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
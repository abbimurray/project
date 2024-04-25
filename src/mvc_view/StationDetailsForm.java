//Student number:C00260073, Student name: Abigail Murray, Semester two

package mvc_view;

//imports from my other packages
import controller.UserSession;
import model.Charger;
import model.ChargingStation;
import model.ChargingStationModel;
import utils.UIUtils;

//other imports
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StationDetailsForm extends JFrame {
    private ChargingStation selectedStation;
    private List<Charger> chargers;
    private ChargingStationModel model;

    public StationDetailsForm(ChargingStation selectedStation) {
        this.selectedStation = selectedStation;
        this.model = new ChargingStationModel(); // Initialize the model
        setTitle("Station Details - " + selectedStation.getAddress());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE); // Set background colour to white
        fetchChargersForStation();
        initializeComponents();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createChargersPanel(), BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Stations List");
        UIUtils.customizeButton(backButton);
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
        ImageIcon stationIcon = new ImageIcon(getClass().getResource("/images/charging-station.png"));
        JLabel iconLabel = new JLabel(stationIcon);
        headerPanel.add(iconLabel, BorderLayout.WEST);

        // Title in the center
        JLabel titleLabel = new JLabel("Station Details", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);


        // Sign Out Icon on the right corner
        ImageIcon signOutIcon = new ImageIcon(getClass().getResource("/images/log-out.png"));
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

            JButton startSessionButton = new JButton("Start Session");
            UIUtils.customizeButton(startSessionButton);

            //start session button will just take customer to the startsessionform
            startSessionButton.addActionListener(e -> {
                // Retrieve the customer ID from the user session
                int customerID = UserSession.getInstance().getCustomerID();

                // Open the StartSessionForm with the necessary parameters
                StartSessionForm startSessionForm = new StartSessionForm(customerID, charger.getChargerID(), model);
                startSessionForm.setVisible(true);
            });

            chargerPanel.add(startSessionButton);
            panel.add(chargerPanel);
        }

        return panel;
    }


    /**
     * Fetches the list of chargers associated with the selected charging station.
     *
     * This method retrieves the chargers belonging to the currently selected charging station
     * from the database using the ChargingStationModel. It updates the 'chargers' field
     * of the class
     *
     */

    private void fetchChargersForStation() {
        // This method should fetch chargers for the selected station
        ChargingStationModel model = new ChargingStationModel();
        this.chargers = model.getChargersByStationId(selectedStation.getStationID());
    }

}//end
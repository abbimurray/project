//Student number:C00260073, Student name: Abigail Murray, Semester two

package mvc_view;

//imports from other packages
import controller.UserSession;
import model.ChargingStation;
import model.ChargingStationModel;
import utils.UIUtils;
import utils.LoggerUtility;

import mvc_view.exceptions.DataRetrievalException;
import mvc_view.exceptions.InvalidInputException;
//imports
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;

public class FindChargingStationForm extends JFrame {
    private JComboBox<String> countyComboBox; //combo box for selecting a county

    private DefaultListModel<ChargingStation> stationListModel;//list model  for managing ChargingStation objects
    private JList<ChargingStation> stationList;// list -- the GUI component.

    private JButton viewDetailsButton;

    public FindChargingStationForm() {
        setTitle("| PowerFlow | EV Charging System | Find Charging Stations |");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
        populateCounties();
    }





   private void initializeUI() {
        getContentPane().setLayout(new BorderLayout(10, 10));// 10 pixel gaps in both dimensions
        getContentPane().setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green color

        ImageIcon leftIcon = new ImageIcon("src/images/search.png");
        JLabel leftLabel = new JLabel(leftIcon);


        JLabel titleLabel = new JLabel("Search for Charging Stations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));


        // Sign Out Icon on the right corner
        ImageIcon signOutIcon = new ImageIcon("src/images/log-out.png");
        JLabel signOutLabel = new JLabel(signOutIcon);
        signOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signOutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Logout action
                UserSession.getInstance().clearSession(); // Clear user session
                dispose(); // Close the dashboard
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true); // Show the login form again
            }
        });

        //Adding header panels
        headerPanel.add(leftLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(signOutLabel, BorderLayout.EAST);
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // Wrapper Panel for centering components
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();   //using gridbagconstarints for positioning components -- more detailed positioning
        gbc.gridwidth = GridBagConstraints.REMAINDER;//Remainder -- each newly added compoenet will be the last in its row
        gbc.anchor = GridBagConstraints.NORTH;///* anchor set to NORTH -- to align components to the top of their display area

        // County Selection
        JLabel selectCountyLabel = new JLabel("Select a county:");
        selectCountyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        wrapperPanel.add(selectCountyLabel, gbc);

        countyComboBox = new JComboBox<>();
        countyComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        countyComboBox.setMaximumSize(new Dimension(200, 25));
        wrapperPanel.add(countyComboBox, gbc);//Add county selection label and combo box to the wrapper panel using the grid bag constraints.

        // Adding a vertical space
        gbc.insets = new Insets(10, 0, 10, 0); // Top, left, bottom, right padding
        wrapperPanel.add(Box.createVerticalStrut(20), gbc); // Add vertical space

        // Reset insets to default for subsequent components
        gbc.insets = new Insets(0, 0, 0, 0);


        // Station List Label and List
        JLabel stationListLabel = new JLabel("Stations based on county selected:");
        stationListLabel.setFont(new Font("Arial", Font.BOLD, 16));
        wrapperPanel.add(stationListLabel, gbc);


        stationListModel = new DefaultListModel<>();
        stationList = new JList<>(stationListModel);
        stationList.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane listScrollPane = new JScrollPane(stationList);
        listScrollPane.setPreferredSize(new Dimension(600, 200));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //Wraps the station list in a scroll pane, sets its preferred size, and modifies the grid bag constraints to fill horizontally
        wrapperPanel.add(listScrollPane, gbc);
        stationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //triggers when the selection changes
        // If the selection is valid and not adjusting, it retrieves the selected ChargingStation object
        stationList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !stationList.isSelectionEmpty()) {
                ChargingStation selectedStation = stationList.getSelectedValue(); // Directly gets the selected ChargingStation object
                System.out.println("Selected Station: " + selectedStation); // for debugging.
                EventQueue.invokeLater(() -> {
                    StationDetailsForm detailsForm = new StationDetailsForm(selectedStation);
                    detailsForm.setVisible(true);
                    this.setVisible(false); // Hide this form
                });
            }
        });

        // Adding components to the content pane
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(wrapperPanel, BorderLayout.CENTER);

        // View Details Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        viewDetailsButton = new JButton("View Details");
        UIUtils.customizeButton(viewDetailsButton);
        buttonPanel.add(viewDetailsButton);


        viewDetailsButton.addActionListener(e -> {
            ChargingStation selectedStation = stationList.getSelectedValue();
            System.out.println("Selected Station: " + selectedStation); // Debugging line
            if (selectedStation != null) {
                StationDetailsForm detailsForm = new StationDetailsForm(selectedStation);
                detailsForm.setVisible(true);
                this.setVisible(false);//temporarily hide the find station form
            } else {
                JOptionPane.showMessageDialog(this, "Please select a station from the list.", "No Station Selected", JOptionPane.WARNING_MESSAGE);
            }
        });


        JButton btnReturnToDashboard = new JButton("Return to Dashboard");
        UIUtils.customizeButton(btnReturnToDashboard);
        btnReturnToDashboard.addActionListener(e -> {
            // Action to return to the dashboard
            dispose(); // Close the current view
            CustomerDashboard dashboard = new CustomerDashboard();
            dashboard.setVisible(true);
        });

        buttonPanel.add(btnReturnToDashboard);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Populate counties combo box and add action listener to the combo box
        populateCounties();
        countyComboBox.addActionListener(e -> {
            String selectedCounty = (String) countyComboBox.getSelectedItem();
            if (selectedCounty != null && !selectedCounty.isEmpty()) {
                populateStations(selectedCounty); // Populate stations based on selected county
            }
        });
    }


    /**
     * Handles the action triggered by selecting a county from a combo box, and populates the station list accordingly.
     * This method retrieves the currently selected county from a combo box and passes it to another method
     * to populate stations.
     * @param e The {@link ActionEvent} triggered by the user's selection action, not used directly in the method.
     */
    private void populateStationsAction(ActionEvent e) {
        String selectedCounty = (String) countyComboBox.getSelectedItem();
        populateStations(selectedCounty);
    }


    /**
     * Handles the action triggered by selecting a charging station from the list, displaying its details.
     *retrieves the charging station currently selected in the station list. If a station is selected,
     * it shows a dialog containing detailed information about the station. If no station is selected,
     * the method does nothing.
     *
     * @param e The {@link ActionEvent} triggered by the user's view details action, typically from a button click
     *          or similar user interaction.
     */
    private void viewDetailsAction(ActionEvent e) {
        ChargingStation selectedStation = stationList.getSelectedValue();
        if (selectedStation != null) {
            JOptionPane.showMessageDialog(this, "Details for: " + selectedStation);
        }
    }


    /**
     * Populates the countyComboBox with distinct county names retrieved from the ChargingStationModel.
     * fetches a list of unique counties where charging stations are available and populates
     * the countyComboBox for user selection. It ensures the comboBox is cleared before adding new items to
     * prevent duplication and ensure current data representation.
     */
    private void populateCounties() {
        ChargingStationModel model = new ChargingStationModel();
        List<String> counties = model.getDistinctCounties();
        countyComboBox.removeAllItems(); // Clear the comboBox before adding new items
        for (String county : counties) {
            countyComboBox.addItem(county);
        }
    }

    /**
     * Populates the station list model with stations based on the selected county.
     * This method retrieves a list of charging stations from the ChargingStationModel that are located within
     * the specified county and updates the stationListModel to reflect this. Before adding new stations, the
     * list is cleared to ensure that it only displays stations relevant to the selected county.
     *
     * @param county The county based on which the stations are filtered and retrieved.
     */
    private void populateStations(String county) {
        ChargingStationModel model = new ChargingStationModel();
        List<ChargingStation> stations = model.getStationsByCounty(county);
        stationListModel.clear(); // Clear the list before adding new items
        for (ChargingStation station : stations) {
            stationListModel.addElement(station); // Add each station to the list model
        }
    }

}//end







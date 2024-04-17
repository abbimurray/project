package mvc_view;

import controller.UserSession;
import model.ChargingStation;
import model.ChargingStationModel;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FindChargingStationForm extends JFrame {
    private JComboBox<String> countyComboBox;
    //private JList<String> stationList;
    //private DefaultListModel<String> stationListModel;

    private DefaultListModel<ChargingStation> stationListModel;//using objects - to directly use the selected station without having to fetch it again from the database or map it from a string representation.
    private JList<ChargingStation> stationList;

    private JButton viewDetailsButton;

    public FindChargingStationForm() {
        setTitle("Find Charging Stations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
        populateCounties();
    }




    private void initializeUI() {
        getContentPane().setLayout(new BorderLayout(10, 10));
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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        // County Selection
        JLabel selectCountyLabel = new JLabel("Select a county:");
        selectCountyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        wrapperPanel.add(selectCountyLabel, gbc);

        countyComboBox = new JComboBox<>();
        countyComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        countyComboBox.setMaximumSize(new Dimension(200, 25));
        wrapperPanel.add(countyComboBox, gbc);

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
        wrapperPanel.add(listScrollPane, gbc);
        //stationList = new JList<>(stationListModel);
        stationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        stationList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !stationList.isSelectionEmpty()) {
                ChargingStation selectedStation = stationList.getSelectedValue(); // Directly gets the selected ChargingStation object
                System.out.println("Selected Station: " + selectedStation); // This is just for debugging.
                EventQueue.invokeLater(() -> {
                    StationDetailsForm detailsForm = new StationDetailsForm(selectedStation);
                    detailsForm.setVisible(true);
                    this.setVisible(false); // Hide this form, assuming 'this' refers to the FindChargingStationForm instance
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
    private void populateStationsAction(ActionEvent e) {
        String selectedCounty = (String) countyComboBox.getSelectedItem();
        populateStations(selectedCounty);
    }

    private void viewDetailsAction(ActionEvent e) {
        ChargingStation selectedStation = stationList.getSelectedValue();
        if (selectedStation != null) {
            JOptionPane.showMessageDialog(this, "Details for: " + selectedStation);
        }
    }

    private void populateCounties() {
        ChargingStationModel model = new ChargingStationModel();
        List<String> counties = model.getDistinctCounties();
        countyComboBox.removeAllItems(); // Clear the comboBox before adding new items
        for (String county : counties) {
            countyComboBox.addItem(county);
        }
    }


    private void populateStations(String county) {
        ChargingStationModel model = new ChargingStationModel();
        List<ChargingStation> stations = model.getStationsByCounty(county);
        stationListModel.clear(); // Clear the list before adding new items
        for (ChargingStation station : stations) {
            stationListModel.addElement(station); // Add each station to the list model
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FindChargingStationForm().setVisible(true));
    }
}










/*

import model.ChargingStationModel;
import model.ChargingStation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;

public class FindChargingStationForm extends JFrame {
    private JComboBox<String> countyComboBox;
    private JComboBox<String> addressComboBox;
    private JButton searchButton;

    public FindChargingStationForm() {
        setTitle("Find Charging Stations");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
    }

    private void initializeUI() {
        getContentPane().setBackground(Color.WHITE); // Set the background of the form to white
        setLayout(new BorderLayout()); // Use BorderLayout for better positioning of components


        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green color
        JLabel titleLabel = new JLabel("Find Charging Stations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        ImageIcon icon = new ImageIcon("src/images/search.png");
        JLabel iconLabel = new JLabel(icon);
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Main content panel
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.WHITE); // background is white
        mainContentPanel.setLayout(new FlowLayout()); // Use FlowLayout

        // Add components to mainContentPanel
        countyComboBox = new JComboBox<>();
        addressComboBox = new JComboBox<>();
        searchButton = new JButton("Search");

        mainContentPanel.add(new JLabel("Select County:"));
        mainContentPanel.add(countyComboBox);
        mainContentPanel.add(new JLabel("Select Address:"));
        mainContentPanel.add(addressComboBox);
        mainContentPanel.add(searchButton);

        // Add panels to the form
        add(headerPanel, BorderLayout.NORTH); // Add the header at the top
        add(mainContentPanel, BorderLayout.CENTER); // Add the main content in the center
///
        // Populate counties and set up actions as before
        populateCounties();
        countyComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedCounty = (String) countyComboBox.getSelectedItem();
                populateAddresses(selectedCounty);
            }
        });
        searchButton.addActionListener(this::searchAction);
    }
        ///
        private void populateCounties() {
            ChargingStationModel model = new ChargingStationModel();
            List<String> counties = model.getDistinctCounties();
            countyComboBox.removeAllItems(); // Clear existing items
            for (String county : counties) {
                countyComboBox.addItem(county);
            }
        }

    private void populateAddresses(String county) {
        ChargingStationModel model = new ChargingStationModel();
        List<ChargingStation> stations = model.getStationsByCounty(county);
        addressComboBox.removeAllItems(); // Clear existing items
        for (ChargingStation station : stations) {
            addressComboBox.addItem(station.getAddress());
        }
    }*/
    /*private void populateCounties() {
        ChargingStationModel model = new ChargingStationModel();
        List<String> counties = model.getDistinctCounties(); // Correct method call
        countyComboBox.removeAllItems(); // Clear existing items
        for (String county : counties) {
            countyComboBox.addItem(county);
        }
    }


    private void populateAddresses(String county) {
        ChargingStationModel model = new ChargingStationModel();
        List<ChargingStation> stations = model.getStationsByCounty(county);
        addressComboBox.removeAllItems(); // Clear existing items
        for (ChargingStation station : stations) {
            addressComboBox.addItem(station.getAddress());
        }
    }*/
/*
    private void searchAction(ActionEvent e) {
        String selectedAddress = (String) addressComboBox.getSelectedItem();
        // Perform search operation based on selected address
        JOptionPane.showMessageDialog(this, "Selected Address: " + selectedAddress);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FindChargingStationForm().setVisible(true));
    }
}*/
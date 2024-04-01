//package mvc_view;
//
//import model.ChargingStation;
//import model.ChargingStationModel;
//import utils.UIUtils;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.util.List;
//
//public class FindChargingStationForm extends JFrame {
//    private JComboBox<String> countyComboBox;
//    private JList<String> stationList;
//    private DefaultListModel<String> stationListModel;
//    private JButton viewDetailsButton;
//
//    public FindChargingStationForm() {
//        setTitle("Find Charging Stations");
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        initializeUI();
//        populateCounties();
//    }

    /*private void initializeUI() {
       // getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());


        // Header Panel Setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green background
        ImageIcon searchIcon = new ImageIcon("src/images/search.png");
        JLabel iconLabel = new JLabel(searchIcon);
        JLabel titleLabel = new JLabel("Search for charging stations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(36, 35, 37)); // Custom text colour

        // Adding components to the header panel
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Adding the header panel to the content pane
        getContentPane().add(headerPanel, BorderLayout.NORTH);

        // County ComboBox
        countyComboBox = new JComboBox<>();
        countyComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        countyComboBox.addActionListener(e -> {
            String selectedCounty = (String) countyComboBox.getSelectedItem();
            populateStations(selectedCounty);
        });

        // Station List
        stationListModel = new DefaultListModel<>();
        stationList = new JList<>(stationListModel);
        stationList.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(stationList);

        // View Details Button
        viewDetailsButton = new JButton("View Details");
        UIUtils.customizeButton(viewDetailsButton);
        viewDetailsButton.addActionListener(this::viewDetailsAction);


        // Adding components to the content pane
        JPanel comboBoxPanel = new JPanel(new BorderLayout());
        comboBoxPanel.add(countyComboBox, BorderLayout.CENTER);
        comboBoxPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(comboBoxPanel, BorderLayout.NORTH);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(viewDetailsButton, BorderLayout.SOUTH);

//    }*/
//    private void initializeUI() {
//        getContentPane().setLayout(new BorderLayout());
//        getContentPane().setBackground(Color.WHITE);
//
//        // Initialize the model before any UI components that might trigger actions using it
//        stationListModel = new DefaultListModel<>();
//
//        // Header Panel Setup
//        JPanel headerPanel = new JPanel(new BorderLayout());
//        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green background
//        ImageIcon searchIcon = new ImageIcon("src/images/search.png");
//        JLabel iconLabel = new JLabel(searchIcon);
//        JLabel titleLabel = new JLabel("Search for Charging Stations", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        titleLabel.setForeground(new Color(36, 35, 37)); // Custom text color
//
//        headerPanel.add(iconLabel, BorderLayout.WEST);
//        headerPanel.add(titleLabel, BorderLayout.CENTER);
//        getContentPane().add(headerPanel, BorderLayout.NORTH);
//
//        // Central Panel Setup (including the county selection)
//        JPanel centralPanel = new JPanel();
//        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
//        JLabel selectCountyLabel = new JLabel("Select a county:");
//        selectCountyLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        centralPanel.add(selectCountyLabel);
//
//        countyComboBox = new JComboBox<>();
//        countyComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
//        centralPanel.add(countyComboBox);
//
//        // Ensure this panel is added in a way that respects your layout wishes
//        getContentPane().add(centralPanel, BorderLayout.CENTER);
//
//        // Station List Setup
//        stationList = new JList<>(stationListModel);
//        stationList.setFont(new Font("Arial", Font.PLAIN, 16));
//        JScrollPane scrollPane = new JScrollPane(stationList);
//        // Consider where and how you want to add this list to your layout
//
//        // View Details Button Setup
//        viewDetailsButton = new JButton("View Details");
//        UIUtils.customizeButton(viewDetailsButton);
//        viewDetailsButton.addActionListener(this::viewDetailsAction);
//
//        // Button Panel Setup
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        buttonPanel.setBackground(Color.WHITE); // Match the form's background color
//        buttonPanel.add(viewDetailsButton);
//        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
//
//        populateCounties(); // Populate the counties combo box
//    }
//    private void initializeUI() {
//        //getContentPane().setLayout(new BorderLayout(10, 10));
//        //getContentPane().setBackground(Color.WHITE);
//        getContentPane().setLayout(new BorderLayout());
//        getContentPane().setBackground(Color.WHITE);
//
//        // Header Panel
//        JPanel headerPanel = new JPanel(new BorderLayout());
//        headerPanel.setBackground(new Color(204, 255, 204));
//        ImageIcon searchIcon = new ImageIcon("src/images/search.png");
//        JLabel iconLabel = new JLabel(searchIcon);
//        JLabel titleLabel = new JLabel("Search for Charging Stations", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        headerPanel.add(iconLabel, BorderLayout.WEST);
//        headerPanel.add(titleLabel, BorderLayout.CENTER);
//
//        // Central Panel for selection and station list
//        JPanel centralPanel = new JPanel();
//        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
//        centralPanel.setBackground(Color.WHITE);
//
//        // Adjust the size and alignment of the combo box
//        countyComboBox = new JComboBox<>();
//        countyComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
//        countyComboBox.setMaximumSize(new Dimension(200, 30)); // Set max size to limit width
//        countyComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
//// Label for the combo box
//        JLabel selectCountyLabel = new JLabel("Select a county:");
//        selectCountyLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        selectCountyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        centralPanel.add(selectCountyLabel);
//        centralPanel.add(countyComboBox);
//
//        // Label for the station list
//        JLabel stationListLabel = new JLabel("Stations based on county selected:");
//        stationListLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        stationListLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        // Station list setup
//        stationListModel = new DefaultListModel<>();
//        stationList = new JList<>(stationListModel);
//        stationList.setFont(new Font("Arial", Font.PLAIN, 16));
//        JScrollPane listScrollPane = new JScrollPane(stationList);
//        listScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
//        listScrollPane.setMaximumSize(new Dimension(200, 100)); // Limit the size of the scroll pane
//
//        centralPanel.add(stationListLabel);
//        centralPanel.add(listScrollPane);
//
//        // Adjust the View Details button
//        viewDetailsButton = new JButton("View Details");
//        UIUtils.customizeButton(viewDetailsButton);
//        viewDetailsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        // Add everything to the central panel
//        centralPanel.add(viewDetailsButton);
//
//        getContentPane().add(centralPanel, BorderLayout.CENTER);
//        // Central Panel for county selection
//        //JPanel centralPanel = new JPanel();
////        centralPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
////        centralPanel.setBackground(Color.WHITE);
//
////        // County Selection Setup
////        JLabel selectCountyLabel = new JLabel("Select a county:");
////        selectCountyLabel.setFont(new Font("Arial", Font.BOLD, 16));
////        centralPanel.add(selectCountyLabel);
////
////        countyComboBox = new JComboBox<>();
////        countyComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
////        countyComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
////        centralPanel.add(countyComboBox);
//
//
//        //
//        // JLabel selectCountyLabel = new JLabel("Select a county:");
//        //selectCountyLabel.setFont(new Font("Arial", Font.BOLD, 16));
//        // centralPanel.add(selectCountyLabel);
//
//        //countyComboBox = new JComboBox<>();
//        // countyComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
//        //countyComboBox.setPreferredSize(new Dimension(200, 25)); // Adjust as needed
//        //centralPanel.add(countyComboBox);
//
//        countyComboBox.addActionListener(e -> {
//            String selectedCounty = (String) countyComboBox.getSelectedItem();
//            System.out.println("Selected County: " + selectedCounty); // Debug print
//            populateStations(selectedCounty);
//        });
//
////        // ScrollPane for Station List
////        stationListModel = new DefaultListModel<>();
////        stationList = new JList<>(stationListModel);
////        stationList.setFont(new Font("Arial", Font.PLAIN, 16));
////        JScrollPane scrollPane = new JScrollPane(stationList);
////        scrollPane.setPreferredSize(new Dimension(200, 150)); // Adjust as needed
////
////        // View Details Button
////        viewDetailsButton = new JButton("View Details");
////        UIUtils.customizeButton(viewDetailsButton);
////        viewDetailsButton.addActionListener(this::viewDetailsAction);
////        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
////        buttonPanel.add(viewDetailsButton);
////
////        // Add components to the frame
////        getContentPane().add(headerPanel, BorderLayout.NORTH);
////        getContentPane().add(centralPanel, BorderLayout.CENTER);
////        getContentPane().add(scrollPane, BorderLayout.EAST);
////        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
//    }
//
//
//    private void populateStationsAction(ActionEvent e) {
//        String selectedCounty = (String) countyComboBox.getSelectedItem();
//        populateStations(selectedCounty);
//    }
//
//    private void viewDetailsAction(ActionEvent e) {
//        String selectedStation = stationList.getSelectedValue();
//        if (selectedStation != null) {
//            JOptionPane.showMessageDialog(this, "Details for: " + selectedStation);
//        }
//    }
//
//    private void populateCounties() {
//        ChargingStationModel model = new ChargingStationModel();
//        List<String> counties = model.getDistinctCounties();
//        countyComboBox.removeAllItems(); // Clear the comboBox before adding new items
//        for (String county : counties) {
//            countyComboBox.addItem(county);
//        }
//    }
//
//    private void populateStations(String county) {
//        System.out.println("Populating stations for county: " + county);
//        stationListModel.clear(); // Clear the list before adding new items
//        ChargingStationModel model = new ChargingStationModel();
//        List<ChargingStation> stations = model.getStationsByCounty(county);
//        for (ChargingStation station : stations) {
//            stationListModel.addElement(station.toString()); // Make sure ChargingStation.toString() provides meaningful information
//        }
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new FindChargingStationForm().setVisible(true));
//    }
//}
//
//
//
//






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
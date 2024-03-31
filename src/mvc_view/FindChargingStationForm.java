package mvc_view;

import model.ChargingStation;
import model.ChargingStationModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FindChargingStationForm extends JFrame {
    private JComboBox<String> countyComboBox;
    private JList<String> stationList;
    private DefaultListModel<String> stationListModel;
    private JButton viewDetailsButton;

    public FindChargingStationForm() {
        setTitle("Find Charging Stations");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
        populateCounties();
    }

    private void initializeUI() {
        getContentPane().setLayout(new BorderLayout(5, 5));

        countyComboBox = new JComboBox<>();
        countyComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCounty = (String) countyComboBox.getSelectedItem();
                populateStations(selectedCounty);
            }
        });

        stationListModel = new DefaultListModel<>();
        stationList = new JList<>(stationListModel);
        JScrollPane scrollPane = new JScrollPane(stationList);

        viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStation = stationList.getSelectedValue();
                if (selectedStation != null) {
                    // Handle viewing details for the selected station
                    JOptionPane.showMessageDialog(FindChargingStationForm.this, "Details for: " + selectedStation);
                }
            }
        });

        getContentPane().add(countyComboBox, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(viewDetailsButton, BorderLayout.SOUTH);
    }


    private void populateCounties() {
        ChargingStationModel model = new ChargingStationModel();
        List<String> counties = model.getDistinctCounties();
        for (String county : counties) {
            countyComboBox.addItem(county);
        }
    }
    private void populateStations(String county) {
        ChargingStationModel model = new ChargingStationModel();
        List<ChargingStation> stations = model.getStationsByCounty(county);
        for (ChargingStation station : stations) {
            stationListModel.addElement(station.toString()); // Assuming ChargingStation.toString() is overridden
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FindChargingStationForm().setVisible(true);
            }
        });
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
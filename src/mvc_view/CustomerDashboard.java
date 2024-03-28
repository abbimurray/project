
package mvc_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerDashboard extends JFrame {

    public CustomerDashboard() {
        setTitle("Customer Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Example color

        // Header label
        JLabel titleLabel = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Dashboard symbol/icon on the header
        ImageIcon dashboardIcon = new ImageIcon("src/images/dashboard.png");
        JLabel iconLabel = new JLabel(dashboardIcon);

        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 cols, gaps

        // Adding sections with icons
        addSection(mainPanel, "My Profile", "src/images/myprofileicon.png");
        addSection(mainPanel, "Search for Charging Stations", "src/images/search.png");
        addSection(mainPanel, "Reserve a Charger", "src/images/reserved.png");
        addSection(mainPanel, "Charging History", "src/images/history.png");
        addSection(mainPanel, "Payments", "src/images/transactionsicon.png");
        addSection(mainPanel, "Customer Support", "src/images/customer-support.png");

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void addSection(JPanel panel, String labelText, String iconPath) {
        JPanel sectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        ImageIcon icon = new ImageIcon(iconPath);
        JLabel labelIcon = new JLabel(icon);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        sectionPanel.add(labelIcon);
        sectionPanel.add(label);

        // Make the section clickable
        sectionPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand cursor
        sectionPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                sectionPanelMouseClicked(evt, labelText); // Call method when mouse clicked
            }
        });

        panel.add(sectionPanel);
    }

    private void sectionPanelMouseClicked(MouseEvent evt, String labelText) {
        // Switch based on the labelText to determine which section was clicked
        switch (labelText) {
            case "My Profile":
                openMyAccount();
                break;
            case "Search for Charging Stations":
                // Assume openSearchForChargingStations is a method that handles this action
                openSearchForChargingStations();
                break;
            case "Reserve a Charger":
                // Assume openReserveACharger is a method that handles this action
                openReserveACharger();
                break;
            case "Charging History":
                // Assume openChargingHistory is a method that handles this action
                openChargingHistory();
                break;
            case "Payments":
                // Assume openPayments is a method that handles this action
                openPayments();
                break;
            case "Customer Support":
                // Assume openCustomerSupport is a method that handles this action
                openCustomerSupport();
                break;
            default:
                System.out.println("Unknown Section");
        }
    }

    private void openMyAccount() {
        System.out.println("Opening My Profile");
        MyAccount myAccountForm = new MyAccount(loggedInCustomerEmail);
        myAccountForm.setVisible(true);
        CustomerDashboard.this.setVisible(false); // Temporarily hide the dashboard

    }

    private void openSearchForChargingStations() {
        System.out.println("Opening Search for Charging Stations...");

    }

    private void openReserveACharger() {
        System.out.println("Opening Reserve a Charger...");
        // Implementation to open the reservation section
    }

    private void openChargingHistory() {
        System.out.println("Opening Charging History...");
        // Implementation to open the charging history section
    }

    private void openPayments() {
        System.out.println("Opening Payments...");
        // Implementation to open the payments section
    }

    private void openCustomerSupport() {
        System.out.println("Opening Customer Support...");
        // Implementation to open the customer support section
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerDashboard().setVisible(true));
    }
}


/*
package mvc_view;

import javax.swing.*;
import java.awt.*;

public class CustomerDashboard extends JFrame {

    public CustomerDashboard() {
        setTitle("Customer Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Example color

        // Header label
        JLabel titleLabel = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Dashboard symbol/icon on the header
        ImageIcon dashboardIcon = new ImageIcon("src/images/dashboard.png");
        JLabel iconLabel = new JLabel(dashboardIcon);

        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 cols, gaps

        // Adding sections with icons
        addSection(mainPanel, "My Profile", "src/images/myprofileicon.png");
        addSection(mainPanel, "Search for Charging Stations", "src/images/search.png");
        addSection(mainPanel, "Reserve a Charger", "src/images/reserved.png");
        addSection(mainPanel, "Charging History", "src/images/history.png");
        addSection(mainPanel, "Payments", "src/images/transactionsicon.png");
        addSection(mainPanel, "Customer Support", "src/images/customer-support.png");

        add(headerPanel, BorderLayout.NORTH);
       add(mainPanel, BorderLayout.CENTER);
    }
    private void addSection(JPanel panel, String labelText, String iconPath) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Set a border around the section panel
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Icon
        ImageIcon icon = new ImageIcon(iconPath);
        JLabel labelIcon = new JLabel(icon);

        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        sectionPanel.add(labelIcon);
        sectionPanel.add(label);

        panel.add(sectionPanel);
    }
/*
    private void addSection(JPanel panel, String labelText, ImageIcon icon) {
        // Section panel with FlowLayout for horizontal arrangement
        JPanel sectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // 10px padding
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Optional: Add a border

        JLabel labelIcon = new JLabel(icon);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        // Add the components to the section panel
        sectionPanel.add(labelIcon);
        sectionPanel.add(label);

        // Add the section panel to the main panel
        panel.add(sectionPanel);
    }
*/



/*

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerDashboard().setVisible(true));
    }
}
*/
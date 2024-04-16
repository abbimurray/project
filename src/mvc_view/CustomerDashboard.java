
package mvc_view;

import controller.UserSession;

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
        headerPanel.setBackground(new Color(204, 255, 204)); //Mint color

        // Header label
        JLabel titleLabel = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Dashboard symbol/icon on the header
        ImageIcon dashboardIcon = new ImageIcon("src/images/dashboard.png");
        JLabel iconLabel = new JLabel(dashboardIcon);

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

        //add to header panel
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(signOutLabel, BorderLayout.EAST);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 cols, gaps

        // Adding sections with icons
        addSection(mainPanel, "My Profile", "src/images/myprofileicon.png");
        addSection(mainPanel, "Search for Charging Stations", "src/images/search.png");
        addSection(mainPanel, "Reserve a Charger", "src/images/reserved.png");
        addSection(mainPanel, "Charging History", "src/images/history.png");
        addSection(mainPanel, "Payment Methods", "src/images/transactionsicon.png");
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
                openSearchForChargingStations();
                break;
            case "Reserve a Charger":
                // need to do
                openReserveACharger();
                break;
            case "Charging History":
                // need to do
                openChargingHistory();
                break;
            case "Payment Methods":
                // need to do
                openPayments();
                break;
            case "Customer Support":
                // need to do
                openCustomerSupport();
                break;
            default:
                System.out.println("Unknown Section");
        }
    }

    private void openMyAccount() {
        System.out.println("Opening My Profile");//printing to terminal
        String userEmail = UserSession.getInstance().getUserEmail();
        MyAccount myAccountForm = new MyAccount(userEmail);
        myAccountForm.setVisible(true);//make my account form visible
        CustomerDashboard.this.setVisible(false); // Temporarily hide the dashboard

    }

    private void openSearchForChargingStations() {
        System.out.println("Opening Search for Charging Stations...");//printing to terminal
        FindChargingStationForm findChargingStationForm = new FindChargingStationForm();  // Creating an instance of the FindChargingStationForm
        findChargingStationForm.setVisible(true);//make find charging station form visible
         this.setVisible(false);//Temporarily hide the dashboard

    }

    private void openReserveACharger() {
        System.out.println("Opening Reserve a Charger...");//printing to terminal
        ReservationManagementForm reservationManagementForm = new ReservationManagementForm();
        reservationManagementForm.setVisible(true);
        this.setVisible(false);//temporarily hide dashboard
    }

    private void openChargingHistory() {
        System.out.println("Opening Charging History...");//printing to terminal

        // Implementation to open the charging history section
    }

    private void openPayments() {
        System.out.println("Opening Payments...");//printing to terminal
        // Implementation to open the payments section
        PaymentMethodsForm paymentMethodsForm = new PaymentMethodsForm();
        paymentMethodsForm.setVisible(true);
        this.setVisible(false);//temporarily hide dashboard
    }

    private void openCustomerSupport() {
        System.out.println("Opening Customer Support...");//printing to terminal
        // Implementation to open the customer support section
        CustomerServiceForm customerServiceForm = new CustomerServiceForm();
        customerServiceForm.setVisible(true);
        this.setVisible(false);//temporarily hide dashboard
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerDashboard().setVisible(true));
    }
}


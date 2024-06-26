//Student number:C00260073, Student name: Abigail Murray, Semester two

package mvc_view;

//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
//imports from other packages
import controller.UserSession;
import mvc_view.exceptions.ResourceNotFoundException;
import mvc_view.exceptions.SessionExpiredException;
import utils.LoggerUtility;

public class CustomerDashboard extends JFrame {

    public CustomerDashboard() {
        setTitle("| PowerFlow | EV Charging System | Customer Dashboard |");
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
        headerPanel.setBackground(new Color(204, 255, 204)); //Mint

        // Header label
        JLabel titleLabel = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Dashboard symbol/icon on the header
        ImageIcon dashboardIcon = new ImageIcon(getClass().getResource("/images/dashboard.png"));
        JLabel iconLabel = new JLabel(dashboardIcon);

        // Sign Out Icon on the right corner
        ImageIcon signOutIcon = new ImageIcon(getClass().getResource("/images/log-out.png"));
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
        addSection(mainPanel, "My Profile", "/images/myprofileicon.png");
        addSection(mainPanel, "Search for Charging Stations", "/images/search.png");
        addSection(mainPanel, "Reserve a Charger", "/images/reserved.png");
        addSection(mainPanel, "Charging History", "/images/history.png");
        addSection(mainPanel, "Payment Methods", "/images/transactionsicon.png");
        addSection(mainPanel, "Customer Support", "/images/customer-support.png");

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void addSection(JPanel panel, String labelText, String iconPath) {
        JPanel sectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

          // Load the icon from the classpath
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        JLabel labelIcon = new JLabel(icon);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
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
                openReserveACharger();
                break;
            case "Charging History":
                openChargingHistory();
                break;
            case "Payment Methods":
                openPayments();
                break;
            case "Customer Support":
                openCustomerSupport();
                break;
            default:
                System.out.println("Unknown Section");
        }
    }



    private void openMyAccount() {
        try {
            System.out.println("Opening My Profile");
            String userEmail = UserSession.getInstance().getUserEmail();
            if (userEmail == null) {
                throw new ResourceNotFoundException("User profile not found.");
            }
            MyAccount myAccountForm = new MyAccount(userEmail);
            myAccountForm.setVisible(true);
            this.setVisible(false);
        } catch (ResourceNotFoundException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            LoggerUtility.log(Level.SEVERE, "Error accessing My Account", ex);
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
            LoggerUtility.log(Level.SEVERE, "Unexpected error in accessing My Account", ex);
        }
    }


    private void openSearchForChargingStations() {
            System.out.println("Opening Search for Charging Stations...");//printing to terminal
            //Implementation for finding a charger - search,start and end charging session
            FindChargingStationForm findChargingStationForm = new FindChargingStationForm();// Creating an instance of the FindChargingStationForm
            findChargingStationForm.setVisible(true);//make find charging station form visible
            this.setVisible(false);//Temporarily hide the dashboard
    }

    private void openReserveACharger() {
        System.out.println("Opening Reserve a Charger...");//printing to terminal
        //Implementation for reservations management - add, cancel, update, view
        ReservationManagementForm reservationManagementForm = new ReservationManagementForm();
        reservationManagementForm.setVisible(true);
        this.setVisible(false);//temporarily hide dashboard
    }

    private void openChargingHistory() {
        System.out.println("Opening Charging History...");//printing to terminal
        // Implementation to view the charging transactions - view only
        ViewChargingTransactions viewChargingTransactions= new ViewChargingTransactions();
        viewChargingTransactions.setVisible(true);
        this.setVisible(false);//temporarily hide dashboard
    }

    private void openPayments() {
        System.out.println("Opening Payments...");//printing to terminal
        // Implementation to open the payments section - add , update, delete, view
        PaymentMethodsForm paymentMethodsForm = new PaymentMethodsForm();
        paymentMethodsForm.setVisible(true);
        this.setVisible(false);//temporarily hide dashboard
    }

    private void openCustomerSupport() {
        System.out.println("Opening Customer Support...");//printing to terminal
        // Implementation to open the customer support section - view only
        CustomerServiceForm customerServiceForm = new CustomerServiceForm();
        customerServiceForm.setVisible(true);
        this.setVisible(false);//temporarily hide dashboard
    }



}//end


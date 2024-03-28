package mvc_view;
import model.Customer;
import model.CustomerModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MyAccount extends JFrame {
    private String customerEmail; // To hold the logged-in customer's email
    private CustomerModel customerModel;
    private JButton btnViewDetails, btnUpdateDetails, btnDeleteAccount, btnSignOut;

    public MyAccount(String customerEmail) {
        this.customerEmail = customerEmail;
        this.customerModel = new CustomerModel();

        setTitle("My Profile");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeButtons(); // Initialize buttons first
        initializeUI();

        setLocationRelativeTo(null);
    }

    private void initializeButtons() {
        btnViewDetails = new JButton("View My Details");
        btnUpdateDetails = new JButton("Update My Details");
        btnDeleteAccount = new JButton("Delete My Account");
        btnSignOut = new JButton("Sign Out");

        // Set font for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        btnViewDetails.setFont(buttonFont);
        btnUpdateDetails.setFont(buttonFont);
        btnDeleteAccount.setFont(buttonFont);
        btnSignOut.setFont(buttonFont);

        // Now it's safe to add action listeners after buttons are initialized
        btnViewDetails.addActionListener(this::viewDetailsAction);
        // Add listeners for other buttons similarly
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Header panel setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint color
        JLabel titleLabel = new JLabel("My Profile", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        ImageIcon dashboardIcon = new ImageIcon("src/images/myaccount.png");
        JLabel iconLabel = new JLabel(dashboardIcon);

        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonsPanel.add(btnViewDetails);
        buttonsPanel.add(btnUpdateDetails);
        buttonsPanel.add(btnDeleteAccount);
        buttonsPanel.add(btnSignOut);



        // Go Back to Dashboard Button
        JButton goBackButton = new JButton("Go Back to Dashboard");
        goBackButton.addActionListener(e -> {
            dispose(); // Close the current view
            EventQueue.invokeLater(() -> { // Properly launch the dashboard on the EDT
                CustomerDashboard dashboard = new CustomerDashboard();
                dashboard.setVisible(true);
            });
        });


        // Layout the components
        add(headerPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(); // Use a panel to layout the button if needed
        bottomPanel.add(goBackButton);
        add(bottomPanel, BorderLayout.SOUTH);

    }


    private void viewDetailsAction(ActionEvent e) {
        Customer customer = customerModel.getCustomerByEmail(customerEmail);
        if (customer != null) {
            ViewMyDetails detailsPage = new ViewMyDetails(customer);
            detailsPage.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Customer details not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Main method and other methods remain the same
}


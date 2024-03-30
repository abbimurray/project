package mvc_view;
import model.Customer;
import model.CustomerModel;
import utils.UIUtils;

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
        // Initialize buttons with icons
        btnViewDetails = new JButton(" View My Details", new ImageIcon("src/images/view.png"));
        btnUpdateDetails = new JButton(" Update My Details", new ImageIcon("src/images/update.png"));
        btnDeleteAccount = new JButton(" Delete My Account", new ImageIcon("src/images/delete.png"));
        btnSignOut = new JButton("Sign Out");


        // Set font for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        btnSignOut.setFont(buttonFont);

        // Customize buttons
        customizeButton(btnViewDetails);
        customizeButton(btnUpdateDetails);
        customizeButton(btnDeleteAccount);

        // add action listeners after buttons are initialized
        // Action listener for viewing details
        btnViewDetails.addActionListener(this::viewDetailsAction);
        // Action listener for updating details
        btnUpdateDetails.addActionListener(e -> updateDetailsAction());
        //Action listener for deleting customer
        btnDeleteAccount.addActionListener(e -> deleteAccountAction());

    }
    //styling for menu buttons
    private void customizeButton(JButton button) {
        button.setBackground(new Color(63, 97, 45)); //dark green colour
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }


    private void initializeUI() {
        getContentPane().setBackground(Color.WHITE); // Set the background of the content pane to white

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
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonsPanel.setBackground(Color.WHITE); // Ensure this panel's background is also white
        // Add some padding around the panel to move it down a bit
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        buttonsPanel.add(btnViewDetails);
        buttonsPanel.add(btnUpdateDetails);
        buttonsPanel.add(btnDeleteAccount);

        //Bottom panel with sign out and go back to dashboard
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE); // Set the background color to white
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Added spacing between buttons

        JButton btnReturnToDashboard = new JButton("Return to Dashboard");
        UIUtils.customizeButton(btnReturnToDashboard);
        btnReturnToDashboard.addActionListener(e -> {
            // Action to return to the dashboard
            dispose(); // Close the current view
            CustomerDashboard dashboard = new CustomerDashboard();
            dashboard.setVisible(true);
        });

        UIUtils.customizeButton(btnSignOut);
        btnSignOut.addActionListener(e -> {
            // Action to sign out
            dispose(); // Close the current view
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });

    bottomPanel.add(btnReturnToDashboard);
    bottomPanel.add(btnSignOut);

    // Layout the components
        add(headerPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH); // Added bottomPanel to the SOUTH
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
    private void updateDetailsAction() {
        Customer customer = customerModel.getCustomerByEmail(customerEmail);
        if (customer != null) {
            UpdateMyDetails updateDetailsPage = new UpdateMyDetails(customer);
            updateDetailsPage.setVisible(true);
            this.setVisible(false); // Optionally hide the MyAccount form
        } else {
            JOptionPane.showMessageDialog(this, "Unable to fetch customer details for update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAccountAction() {
        // Ask for confirmation before deleting the account
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Delete Account",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            boolean deleted = customerModel.deleteCustomerByEmail(customerEmail);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Your account has been successfully deleted.", "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the MyAccount form
                // Optionally, redirect to the login page or close the application
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete your account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }




}//end class


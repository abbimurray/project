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

    // Updated constructor to accept customerEmail
    public MyAccount(String customerEmail) {
        this.customerEmail = customerEmail;
        this.customerModel = new CustomerModel(); // Initialize your model (consider making this a singleton or passing it as a parameter)

        setTitle("My Profile");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeUI();
        setLocationRelativeTo(null);
    }

    private void initializeUI() {
        // Your UI initialization code, modify btnViewDetails's action listener
        btnViewDetails.addActionListener(this::viewDetailsAction);
        // Other initialization code remains the same
    }

    private void viewDetailsAction(ActionEvent e) {
        Customer customer = customerModel.getCustomerByEmail(customerEmail);
        if (customer != null) {
            // For demonstration, let's use a JOptionPane to show details
            JOptionPane.showMessageDialog(this,
                    "Email: " + customer.getEmail() +
                            "\nFirst Name: " + customer.getFirstName() +
                            "\nLast Name: " + customer.getLastName() +
                            "\nPhone: " + customer.getPhone(),
                    "My Details", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Customer details not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method and other methods remain the same
}

/*package mvc_view;

import model.Customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MyAccount extends JFrame {
    private JButton btnViewDetails, btnUpdateDetails, btnDeleteAccount, btnSignOut;

    public MyAccount() {
        setTitle("My Profile");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to DISPOSE_ON_CLOSE
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Header panel setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint color
        JLabel titleLabel = new JLabel("My Profile", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        ImageIcon dashboardIcon = new ImageIcon("src/images/myaccount.png"); // Ensure this path is correct
        JLabel iconLabel = new JLabel(dashboardIcon);
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // 4 buttons, vertically aligned
        btnViewDetails = new JButton("View My Details");
        btnUpdateDetails = new JButton("Update My Details");
        btnDeleteAccount = new JButton("Delete My Account");
        btnSignOut = new JButton("Sign Out");


        // Set font for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18); // Font set to Arial Bold, size 18
        btnViewDetails.setFont(buttonFont);
        btnUpdateDetails.setFont(buttonFont);
        btnDeleteAccount.setFont(buttonFont);
        btnSignOut.setFont(buttonFont);


        buttonsPanel.add(btnViewDetails);
        buttonsPanel.add(btnUpdateDetails);
        buttonsPanel.add(btnDeleteAccount);
        buttonsPanel.add(btnSignOut);

        // Add action listeners (placeholders)
        btnViewDetails.addActionListener(this::viewDetailsAction);
        btnUpdateDetails.addActionListener(this::updateDetailsAction);
        btnDeleteAccount.addActionListener(this::deleteAccountAction);
        btnSignOut.addActionListener(this::signOutAction);

        // Adding panels to the JFrame
        add(headerPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
    }

    private void viewDetailsAction(ActionEvent e) {

    }

    private void updateDetailsAction(ActionEvent e) {
        // Placeholder - Implement action to update details
    }

    private void deleteAccountAction(ActionEvent e) {
        // Placeholder - Implement confirmation and deletion logic
    }

    private void signOutAction(ActionEvent e) {
        // Placeholder - Implement sign out logic
    }

    // Main method for demonstration purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyAccount().setVisible(true));
    }
}









/*
import java.awt.event.ActionListener;


//NB CANT EDIT EMAIL AS IT IS PRIMARY KEY
public class MyAccount extends JFrame {
    private JTextField txtFirstName, txtLastName, txtEmail, txtPhone;
    private JPasswordField txtPassword;
    private JButton btnSaveChanges, btnChangePassword, btnDeleteAccount, btnSignOut;

    public MyAccount() {

        setTitle("Customer Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
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

        // Information Panel for user details
        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Adjust layout as needed
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtPassword = new JPasswordField();

        // Add components to infoPanel
        infoPanel.add(new JLabel("First Name:"));
        infoPanel.add(txtFirstName);
        infoPanel.add(new JLabel("Last Name:"));
        infoPanel.add(txtLastName);
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(txtEmail);
        txtEmail.setEditable(false); // Since email is a primary key and can't be edited
        infoPanel.add(new JLabel("Phone:"));
        infoPanel.add(txtPhone);
        infoPanel.add(new JLabel("Password:"));
        infoPanel.add(txtPassword);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        btnSaveChanges = new JButton("Save Changes");
        btnChangePassword = new JButton("Change Password");
        btnDeleteAccount = new JButton("Delete Account");
        btnSignOut = new JButton("Sign Out");

        // Add buttons to buttonsPanel
        buttonsPanel.add(btnSaveChanges);
        buttonsPanel.add(btnChangePassword);
        buttonsPanel.add(btnDeleteAccount);
        buttonsPanel.add(btnSignOut);

        // Adding panels to the JFrame
        add(headerPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER); // Adjust as needed for layout
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}*/
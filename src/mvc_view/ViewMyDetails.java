//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;
import controller.UserSession;
import model.Customer;
import utils.UIUtils;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import mvc_view.exceptions.CustomerDetailsNotFoundException;
import mvc_view.exceptions.InputValidationException;
import utils.LoggerUtility;

import java.util.logging.Level;


public class ViewMyDetails extends JFrame {
    private Customer customer;

    public ViewMyDetails(Customer customer) {
        setTitle("Viewing My Details");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (customer == null) {
            LoggerUtility.log(Level.SEVERE, "Customer details are null", new CustomerDetailsNotFoundException("Customer details not provided."));
            JOptionPane.showMessageDialog(null, "Customer details not provided", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;  // Stop further execution
        }

        this.customer = customer;

        //direct validation
        try {
            if (customer.getFirstName() == null || customer.getLastName() == null || customer.getEmail() == null) {
                throw new InputValidationException("One or more required fields are empty.");
            }
            initializeUI(); // Initialize the UI if all checks pass
        } catch (InputValidationException e) {
            LoggerUtility.log(Level.SEVERE, "Invalid customer data", e);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Data Error", JOptionPane.ERROR_MESSAGE);
            dispose(); // Dispose the frame and stop further execution
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10)); // Add some spacing

        // Icon
        ImageIcon userIcon = new ImageIcon("src/images/myprofileicon.png");
        JLabel iconLabel = new JLabel(userIcon);

        // Title Label
        JLabel titleLabel = new JLabel("Viewing My Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Header Panel with BorderLayout for better control
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));
        headerPanel.add(iconLabel, BorderLayout.WEST);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));//vertical gap to push title down a bit
        titlePanel.setBackground(new Color(204, 255, 204)); // Match the header panel's background
        titlePanel.add(titleLabel);
        headerPanel.add(titlePanel, BorderLayout.CENTER);

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
        headerPanel.add(signOutLabel, BorderLayout.EAST);

        // Form-like Details Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); // Set to white for the form look
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the form


        // Styling for labels and details
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        Font detailFont = new Font("Arial", Font.PLAIN, 18);

        // Initialize GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        //for customerID Label
        JLabel customerIDLabel = new JLabel("Customer ID: ");
        customerIDLabel.setFont(labelFont);
        formPanel.add(customerIDLabel, gbc);
        //customerID value
        JLabel customerIDValue = new JLabel(String.valueOf(customer.getCustomerID()));
        customerIDValue.setFont(detailFont); // Apply detail font
        formPanel.add(customerIDValue, gbc);
        //  for First Name Label
        JLabel firstNameLabel = new JLabel("First Name: ");
        firstNameLabel.setFont(labelFont);
        formPanel.add(firstNameLabel, gbc);
        //  for First Name Value
        JLabel firstNameValue = new JLabel(customer.getFirstName());
        firstNameValue.setFont(detailFont);
        formPanel.add(firstNameValue, gbc);
        //  for Last Name Label
        JLabel lastNameLabel = new JLabel("Last Name: ");
        lastNameLabel.setFont(labelFont);
        formPanel.add(lastNameLabel, gbc);
        //  for Last Name Value
        JLabel lastNameValue = new JLabel(customer.getLastName());
        lastNameValue.setFont(detailFont);
        formPanel.add(lastNameValue, gbc);
        //  for Email Label
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel, gbc);
        //  for Email value
        JLabel emailValue = new JLabel(customer.getEmail());
        emailValue.setFont(detailFont);
        formPanel.add(emailValue, gbc);
        //  for Phone Label
        JLabel phoneLabel = new JLabel("Phone: ");
        phoneLabel.setFont(labelFont);
        formPanel.add(phoneLabel, gbc);
        //for phone value
        JLabel phoneValue = new JLabel(customer.getPhone());
        phoneValue.setFont(detailFont);
        formPanel.add(phoneValue, gbc);

        // Wrap formPanel in another panel to center it on screen
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(formPanel);

        // Go Back Button
        JButton goBackButton = new JButton("Go Back to My Profile");
        UIUtils.customizeButton(goBackButton);
        goBackButton.addActionListener(e -> dispose()); // Close this window
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(goBackButton);
        //add panels
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }


}//end class
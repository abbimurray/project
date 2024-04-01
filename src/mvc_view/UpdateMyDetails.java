package mvc_view;

import controller.UserSession;
import model.Customer;
import model.CustomerModel;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateMyDetails extends JFrame {
    private Customer customer;
    private JTextField customerIDField, firstNameField, lastNameField, emailField, phoneField;
    private JButton saveButton, cancelButton;
    private CustomerModel customerModel;
    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/EVCharging";
    private final String DATABASE_USER = "root";
    private final String DATABASE_PASSWORD = "pknv!47A";


    public UpdateMyDetails(Customer customer) {
        this.customer = customer;
        this.customerModel = new CustomerModel();
        initializeUI();
        setTitle("Update My Details");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Header Panel with Icon and Label
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint color

        ImageIcon icon = new ImageIcon("src/images/myaccount.png");
        JLabel iconLabel = new JLabel(icon);
        headerPanel.add(iconLabel, BorderLayout.WEST);

        JLabel headerLabel = new JLabel("Update My Details", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(204, 255, 204)); //mint colour
        titlePanel.add(headerLabel);
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


        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); // Background color for the form

        // Adjusting GridBagLayout constraints for better alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Adjust how components are added to GridBagLayout
        int row = 0; // Track rows for placement

        // Ensure text fields are initialized here before any action listeners that use them
        customerIDField = new JTextField(customer.getCustomerID());
        firstNameField = new JTextField(customer.getFirstName());
        lastNameField = new JTextField(customer.getLastName());
        emailField = new JTextField(customer.getEmail());
        phoneField = new JTextField(customer.getPhone());

        // Font Definitions
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 18);

        // Adding Form Fields with Labels and Text Fields
        addFormField(formPanel, "Customer ID:", String.valueOf(customer.getCustomerID()), row++, labelFont, textFieldFont, false);
        addFormField(formPanel, "First Name:", customer.getFirstName(), row++, labelFont, textFieldFont);
        addFormField(formPanel, "Last Name:", customer.getLastName(), row++, labelFont, textFieldFont);
        addFormField(formPanel, "Email:", customer.getEmail(), row++, labelFont, textFieldFont);
        addFormField(formPanel, "Phone:", customer.getPhone(), row++, labelFont, textFieldFont);


        // Button Panel
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        UIUtils.customizeButton(saveButton); // Assuming UIUtils.customizeButton applies the desired styling
        saveButton.addActionListener(e -> saveChanges());

        cancelButton = new JButton("Cancel");
        UIUtils.customizeButton(cancelButton);
        cancelButton.addActionListener(e -> dispose());

        JButton goBackButton = new JButton("Go Back to MyProfile");
        UIUtils.customizeButton(goBackButton);
        goBackButton.addActionListener(e -> {
            dispose();
            MyAccount myAccount = new MyAccount(customer.getEmail());
            myAccount.setVisible(true);
        });

        buttonPanel.add(goBackButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(e -> saveChanges());
    }

    //////
// Adjust the helper method to accommodate row parameter and simplify GridBagConstraints usage
    private void addFormField(JPanel panel, String labelText, String fieldValue, int row, Font labelFont, Font textFieldFont) {
        addFormField(panel, labelText, fieldValue, row, labelFont, textFieldFont, true);
    }

    private void addFormField(JPanel panel, String labelText, String fieldValue, int row, Font labelFont, Font textFieldFont, boolean editable) {
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0; // Label in the first column
        gbcLabel.gridy = row;
        gbcLabel.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        panel.add(label, gbcLabel);

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.gridx = 1; // Field in the second column
        gbcField.gridy = row;
        gbcField.weightx = 1.0; // Give extra space to the text field
        gbcField.fill = GridBagConstraints.HORIZONTAL;

        JTextField textField = new JTextField(fieldValue, 20);
        textField.setFont(textFieldFont);
        textField.setEditable(editable);
        panel.add(textField, gbcField);
    }


    /////
    /*private void saveChanges() {
        try {
            if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || phoneField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            customer.setFirstName(firstNameField.getText());
            customer.setLastName(lastNameField.getText());
            customer.setPhone(phoneField.getText());
            // Assuming password and salt are not being updated here. If they are, gather those values as well.

            boolean updated = customerModel.updateCustomer(customer);
            if (updated) {
                JOptionPane.showMessageDialog(this, "Details updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update details", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/
    private void saveChanges() {
        // Example of getting data from GUI text fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        Connection connection = null;
        PreparedStatement pstat = null;

        try {
            // Establish connection to the database
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);

            // Prepare your SQL statement using input from GUI fields
            String updateQuery = "UPDATE customer_accounts SET firstName=?, lastName=?, email=?, phone=? WHERE customerID=?";
            pstat = connection.prepareStatement(updateQuery);

            // Set parameters from the GUI fields
            pstat.setString(1, firstName);
            pstat.setString(2, lastName);
            pstat.setString(3, email);
            pstat.setString(4, phone);
            pstat.setInt(5, customer.getCustomerID());

            // Execute update
            int affectedRows = pstat.executeUpdate();

            if (affectedRows > 0) {
                // Show success message
                JOptionPane.showMessageDialog(null, "Customer details updated successfully.");
            } else {
                // Show failure message
                JOptionPane.showMessageDialog(null, "Failed to update customer details.");
            }
        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, "Update failed: " + sqlException.getMessage());
            sqlException.printStackTrace();
        } finally {
            try {
                if (pstat != null) pstat.close();
                if (connection != null) connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
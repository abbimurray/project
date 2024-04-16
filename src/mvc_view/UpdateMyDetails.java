/*package mvc_view;


import controller.CustomerController;
import controller.UserSession;
import model.Customer;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UpdateMyDetails extends JFrame {
    private Customer customer;
    private JTextField firstNameField, lastNameField, emailField, phoneField;
    private JButton saveButton, cancelButton, goBackButton;
    private CustomerController customerController;

    public UpdateMyDetails(Customer customer) {
        this.customer = customer;
        this.customerController = new CustomerController();
        initializeUI();
        setTitle("Update My Details");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        add(setupHeaderPanel(), BorderLayout.NORTH);
        add(setupFormPanel(), BorderLayout.CENTER);
        add(setupButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel setupHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));
        headerPanel.add(new JLabel(new ImageIcon("src/images/myaccount.png")), BorderLayout.WEST);

        JLabel headerLabel = new JLabel("Update My Details", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        JLabel signOutLabel = new JLabel(new ImageIcon("src/images/log-out.png"));
        signOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signOutLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                UserSession.getInstance().clearSession();
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        headerPanel.add(signOutLabel, BorderLayout.EAST);
        return headerPanel;
    }

    private JPanel setupFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        int row = 0;

        firstNameField = new JTextField(customer.getFirstName(), 15);
        lastNameField = new JTextField(customer.getLastName(), 15);
        emailField = new JTextField(customer.getEmail(), 15);
        phoneField = new JTextField(customer.getPhone(), 15);

        addFormField(formPanel, "First Name:", firstNameField, row++, new Font("Arial", Font.BOLD, 18));
        addFormField(formPanel, "Last Name:", lastNameField, row++, new Font("Arial", Font.BOLD, 18));
        addFormField(formPanel, "Email:", emailField, row++, new Font("Arial", Font.BOLD, 18));
        addFormField(formPanel, "Phone:", phoneField, row++, new Font("Arial", Font.BOLD, 18));

        return formPanel;
    }

    private void addFormField(JPanel panel, String labelText, JTextField textField, int row, Font font) {
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = row;
        gbcLabel.anchor = GridBagConstraints.WEST;

        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, gbcLabel);

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.gridx = 1;
        gbcField.gridy = row;
        gbcField.weightx = 1.0;
        gbcField.fill = GridBagConstraints.HORIZONTAL;

        textField.setFont(font);
        panel.add(textField, gbcField);
    }

    private JPanel setupButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        goBackButton = new JButton("Go Back to My Profile");

        UIUtils.customizeButton(saveButton);
        UIUtils.customizeButton(cancelButton);
        UIUtils.customizeButton(goBackButton);

        saveButton.addActionListener(e -> saveChanges());
        cancelButton.addActionListener(e -> dispose());
        goBackButton.addActionListener(e -> {
            dispose();
            MyAccount myAccountForm = new MyAccount(customer.getEmail());
            myAccountForm.setVisible(true);
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(goBackButton);

        return buttonPanel;
    }

    private void saveChanges() {
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setEmail(emailField.getText());
        customer.setPhone(phoneField.getText().replaceAll("[^\\d\\s]", "")); // Validates to only digits and spaces

        String result = customerController.updateCustomerDetails(customer);
        JOptionPane.showMessageDialog(this, result);
        if (result.contains("successfully")) {
            dispose();
            new UpdateMyDetails(customer).setVisible(true); // Reopen the update form with refreshed data
        }
    }
}*/
/////////////////////////////////////
package mvc_view;
import controller.CustomerController;
import controller.UserSession;
import model.Customer;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UpdateMyDetails extends JFrame {
    private Customer customer;
    private JTextField firstNameField, lastNameField, emailField, phoneField;
    private JButton saveButton, cancelButton;
    private CustomerController customerController;

    public UpdateMyDetails(Customer customer) {
        this.customer = customer;
        this.customerController = new CustomerController();
        initializeUI();
        setTitle("Update My Details");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));
        ImageIcon icon = new ImageIcon("src/images/myaccount.png");
        JLabel iconLabel = new JLabel(icon);
        headerPanel.add(iconLabel, BorderLayout.WEST);

        JLabel headerLabel = new JLabel("Update My Details", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        ImageIcon signOutIcon = new ImageIcon("src/images/log-out.png");
        JLabel signOutLabel = new JLabel(signOutIcon);
        signOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signOutLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                UserSession.getInstance().clearSession();
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        headerPanel.add(signOutLabel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        int row = 0;

        // Initialize text fields
        firstNameField = new JTextField(customer.getFirstName(), 20);
        lastNameField = new JTextField(customer.getLastName(), 20);
        emailField = new JTextField(customer.getEmail(), 20);
        phoneField = new JTextField(customer.getPhone(), 20);

        // Adding Form Fields with Labels and Text Fields
        addFormField(formPanel, "First Name:", firstNameField, row++, new Font("Arial", Font.BOLD, 18));
        addFormField(formPanel, "Last Name:", lastNameField, row++, new Font("Arial", Font.BOLD, 18));
        addFormField(formPanel, "Email:", emailField, row++, new Font("Arial", Font.BOLD, 18));
        addFormField(formPanel, "Phone:", phoneField, row++, new Font("Arial", Font.BOLD, 18));
        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        saveButton = new JButton("Save");
        UIUtils.customizeButton(saveButton);
        saveButton.addActionListener(e -> saveChanges());

        cancelButton = new JButton("Cancel");
        UIUtils.customizeButton(cancelButton);
        cancelButton.addActionListener(e -> dispose());


        // Go Back Button
        JButton goBackButton = new JButton("Go Back to My Profile");
        UIUtils.customizeButton(goBackButton);
        goBackButton.addActionListener(e -> {
            // Dispose the current window
            dispose();
            // Open the MyAccount form
            MyAccount myAccountForm = new MyAccount(customer.getEmail());  // Assuming MyAccount needs the email to initialize
            myAccountForm.setVisible(true);
        });

// Add the goBackButton to your button panel
        buttonPanel.add(goBackButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);



    }

    private void addFormField(JPanel panel, String labelText, JTextField textField, int row, Font font) {
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = row;
        gbcLabel.anchor = GridBagConstraints.WEST;
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, gbcLabel);

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.gridx = 1;
        gbcField.gridy = row;
        gbcField.weightx = 1.0;
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        textField.setFont(font);
        panel.add(textField, gbcField);
    }


    private void saveChanges() {
        // Set customer details from fields
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setEmail(emailField.getText());
        customer.setPhone(phoneField.getText());

        // Use controller to update details
        String result = customerController.updateCustomerDetails(customer);
        JOptionPane.showMessageDialog(this, result);
        if (result.contains("successfully")) {
            // Fetch the latest customer data from the database (this part needs a correct method)
            Customer updatedCustomer = customerController.getCustomerByEmail(customer.getEmail()); // Assuming this method exists
            if (updatedCustomer != null) {
                updateFormFields(updatedCustomer);
                JOptionPane.showMessageDialog(this, "Details updated. The form has been refreshed with the latest information.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch updated details.");
            }
        }
    }

    private void updateFormFields(Customer updatedCustomer) {
        // Refresh the current customer object and UI fields with the updated data
        this.customer = updatedCustomer;
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        emailField.setText(customer.getEmail());
        phoneField.setText(customer.getPhone());
    }





}//end class


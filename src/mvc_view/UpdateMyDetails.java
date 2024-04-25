////Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;
import controller.CustomerController;
import controller.UserSession;
import model.Customer;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UpdateMyDetails extends JFrame {
    private Customer customer;
    private JTextField firstNameField, lastNameField, phoneField;
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
        ImageIcon userIcon = new ImageIcon(getClass().getResource("/images/myprofileicon.png"));
        JLabel iconLabel = new JLabel(userIcon);
        headerPanel.add(iconLabel, BorderLayout.WEST);

        JLabel headerLabel = new JLabel("Update My Details", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Sign Out Icon on the right corner
        ImageIcon signOutIcon = new ImageIcon(getClass().getResource("/images/log-out.png"));
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
        phoneField = new JTextField(customer.getPhone(), 20);

        // Adding Form Fields with Labels and Text Fields
        addFormField(formPanel, "First Name:", firstNameField, row++, new Font("Arial", Font.BOLD, 18));
        addFormField(formPanel, "Last Name:", lastNameField, row++, new Font("Arial", Font.BOLD, 18));
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
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the  fields
                firstNameField.setText("");
                lastNameField.setText("");
                phoneField.setText("");
            }
        });

        // Go Back Button
        JButton goBackButton = new JButton("Go Back to My Profile");
        UIUtils.customizeButton(goBackButton);
        goBackButton.addActionListener(e -> {
            // Dispose the current window
            dispose();
            // Open the MyAccount form
            MyAccount myAccountForm = new MyAccount(customer.getEmail());
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


    /**
     * Saves changes made to customer details.
     *
     * This method retrieves the updated customer details from the input fields
     * in the UI, sets them to the current customer object, and then uses the
     * customer controller to update these details in the database.
     *
     */
    private void saveChanges() {
        // Set customer details from fields
        customer.setFirstName(firstNameField.getText());
        customer.setLastName(lastNameField.getText());
        customer.setPhone(phoneField.getText());

        // Use controller to update details
        String result = customerController.updateCustomerDetails(customer);
        JOptionPane.showMessageDialog(this, result);
        if (result.contains("successfully")) {
            // Fetch the latest customer data from the database (this part needs a correct method)
            Customer updatedCustomer = customerController.getCustomerByEmail(customer.getEmail());
            if (updatedCustomer != null) {
                updateFormFields(updatedCustomer);
                JOptionPane.showMessageDialog(this, "Details updated. The form has been refreshed with the latest information.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to fetch updated details.");
            }
        }
    }



    /**
     * Updates the UI fields with the details of the provided Customer object.
     *
     * This method updates the text fields in the UI with the details (first name,
     * last name, and phone) of the provided Customer object.
     *
     * @param updatedCustomer The Customer object containing the updated details.
     */
    private void updateFormFields(Customer updatedCustomer) {
        // Refresh the current customer object and UI fields with the updated data
        this.customer = updatedCustomer;
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        phoneField.setText(customer.getPhone());
    }


}//end class
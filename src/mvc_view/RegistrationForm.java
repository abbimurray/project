package mvc_view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Customer;/*import customer class in model*/
import model.CustomerModel;/*import classes in model*/

import utils.HashingUtils;
import utils.ValidationUtils; /*importing validation class for email and password*/

public class RegistrationForm extends JDialog {
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField phoneTextField;
    private JButton registerButton;
    private JButton cancelButton;
    private JPanel registerPanel;

    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Register");
        setSize(600, 600);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        initializeUI();
    }

    private void initializeUI() {
        registerPanel = new JPanel(new GridLayout(5, 2));

        firstNameTextField = new JTextField();
        lastNameTextField = new JTextField();
        emailTextField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        phoneTextField = new JTextField();
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        /*labels and inputs*/
        registerPanel.add(new JLabel("First Name:"));
        registerPanel.add(firstNameTextField);
        registerPanel.add(new JLabel("Last Name:"));
        registerPanel.add(lastNameTextField);
        registerPanel.add(new JLabel("Email:"));
        registerPanel.add(emailTextField);
        registerPanel.add(new JLabel("Phone:"));
        registerPanel.add(phoneTextField);
        registerPanel.add(new JLabel("Password:"));
        registerPanel.add(passwordField);
        registerPanel.add(new JLabel("Confirm Password:"));
        registerPanel.add(confirmPasswordField);
        /*buttons*/
        registerPanel.add(cancelButton);
        registerPanel.add(registerButton);

        add(registerPanel, BorderLayout.CENTER);

        // Event listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform registration
                register();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the dialog
            }
        });
    }


    private void register() {
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String phone = phoneTextField.getText().trim();

        //validation to ensure all fields are filled in
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||phone.isEmpty()|| password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //email format validation
        if (!ValidationUtils.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // password strength validation
        if (!ValidationUtils.isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, "Password does not meet the security criteria.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //hashing password
        // Hash the password with a new salt
        String salt = HashingUtils.getSalt();
        String hashedPassword = HashingUtils.hashPasswordWithSHA256(password, salt);

        // Create a Customer object
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(firstName);
        newCustomer.setLastName(lastName);
        newCustomer.setEmail(email);
        newCustomer.setPhone(phone);
        newCustomer.setPassword(hashedPassword); // Store the hashed password
        newCustomer.setSalt(salt);
        // Use CustomerModel to save the new customer
        CustomerModel customerModel = new CustomerModel();
        boolean isRegistered = customerModel.addCustomer(newCustomer);

        if (isRegistered) {
            JOptionPane.showMessageDialog(this, "Registration successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close the registration form
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }







}//end of class

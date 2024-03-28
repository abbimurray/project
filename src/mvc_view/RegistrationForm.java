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

    private JTextField emailTextField = new JTextField(30);
    private JPasswordField passwordField = new JPasswordField(30);
    private JPasswordField confirmPasswordField = new JPasswordField(30);
    private JTextField firstNameTextField = new JTextField(30);
    private JTextField lastNameTextField = new JTextField(30);
    private JTextField phoneTextField = new JTextField(30);
    private JButton registerButton = new JButton("Register");
    private JButton cancelButton = new JButton("Cancel");



    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("Registration Form");
        setSize(800, 600);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        initializeUI();
    }

    private void initializeUI()
    {
        //main panel setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        getContentPane().add(mainPanel);

        // Header panel setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green background
        headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 20)); // Decrease the height of the title panel
        ImageIcon logoIcon = new ImageIcon("src/images/car_logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        JLabel titleLabel = new JLabel("Register as a User", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(36, 35, 37));
        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel);

        //form panel setup
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4);
        formPanel.setBackground(Color.WHITE);


        // Create and set font for each label
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(labelFont);
        formPanel.add(firstNameLabel, gbc);
        firstNameTextField.setFont(textFieldFont);
        formPanel.add(firstNameTextField, gbc);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(labelFont);
        formPanel.add(lastNameLabel, gbc);
        lastNameTextField.setFont(textFieldFont);
        formPanel.add(lastNameTextField, gbc);
       //

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel, gbc);
        emailTextField.setFont(textFieldFont);
        formPanel.add(emailTextField, gbc);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        formPanel.add(phoneLabel, gbc);
        phoneTextField.setFont(textFieldFont);
        formPanel.add(phoneTextField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        formPanel.add(passwordLabel, gbc);
        passwordField.setFont(textFieldFont);
        formPanel.add(passwordField, gbc);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(labelFont);
        formPanel.add(confirmPasswordLabel, gbc);
        confirmPasswordField.setFont(textFieldFont);
        formPanel.add(confirmPasswordField, gbc);
        ///////////
        //button Panel setup
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        customizeButton(cancelButton);
        customizeButton(registerButton);

        //add form panel and button panel to main panel
        mainPanel.add(formPanel);
        mainPanel.add(buttonPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
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

    private void customizeButton(JButton button) {
        Color mintGreen = new Color(204, 255, 204); // Mint green color
        button.setOpaque(true);
        button.setBackground(mintGreen); // Set background to mint green
        button.setForeground(new Color(36, 35, 37)); // Set text color
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font to Arial, Bold, size 16
        // Optional: if you want the buttons to have a uniform size, you can set a preferred size
        button.setPreferredSize(new Dimension(100, 40)); // Adjust width and height as needed
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

            // Open the CustomerDashboard
            CustomerDashboard dashboard = new CustomerDashboard();
            dashboard.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}//end of class

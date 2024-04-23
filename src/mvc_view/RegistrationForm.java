
//Student number:C00260073, Student name: Abigail Murray, Semester two


package mvc_view;

//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//imported classes from other packages
import model.Customer;
import model.CustomerModel;
import controller.UserSession;
import utils.HashingUtils;
import utils.UIUtils;
import utils.ValidationUtils;

public class RegistrationForm extends JDialog {

    private JTextField emailTextField = new JTextField(30);
    private JPasswordField passwordField = new JPasswordField(30);
    private JPasswordField confirmPasswordField = new JPasswordField(30);
    private JTextField firstNameTextField = new JTextField(30);
    private JTextField lastNameTextField = new JTextField(30);
    private JTextField phoneTextField = new JTextField(30);
    private JButton registerButton = new JButton("Register");
    private JButton cancelButton = new JButton("Cancel");
    private JButton backButton = new JButton("Back to Login");



    public RegistrationForm(JFrame parent) {
        super(parent);
        setTitle("| PowerFlow | EV Charging System | Registration Form |");
        setSize(800, 600);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        initializeUI();
    }

    private void initializeUI() {
        //main panel setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        getContentPane().add(mainPanel);

        // Header panel setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green background
        headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 20)); // height of title panel
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
        // for firstName
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(labelFont);
        formPanel.add(firstNameLabel, gbc);
        firstNameTextField.setFont(textFieldFont);
        formPanel.add(firstNameTextField, gbc);
        //for lastName
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(labelFont);
        formPanel.add(lastNameLabel, gbc);
        lastNameTextField.setFont(textFieldFont);
        formPanel.add(lastNameTextField, gbc);
        //for email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel, gbc);
        emailTextField.setFont(textFieldFont);
        formPanel.add(emailTextField, gbc);
        //for phone
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        formPanel.add(phoneLabel, gbc);
        phoneTextField.setFont(textFieldFont);
        formPanel.add(phoneTextField, gbc);
        //for password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        formPanel.add(passwordLabel, gbc);
        passwordField.setFont(textFieldFont);
        formPanel.add(passwordField, gbc);
        //for confirmPassword
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(labelFont);
        formPanel.add(confirmPasswordLabel, gbc);
        confirmPasswordField.setFont(textFieldFont);
        formPanel.add(confirmPasswordField, gbc);


        //button Panel setup
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);
        UIUtils.customizeButton(cancelButton);
        UIUtils.customizeButton(registerButton);
        UIUtils.customizeButton(backButton);

        //add form panel and button panel to main panel
        mainPanel.add(formPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));//create space

        // Event listeners for buttons
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
                // Clear the fields
                emailTextField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");
                firstNameTextField.setText("");
                lastNameTextField.setText("");
                phoneTextField.setText("");

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the registration form
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
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
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
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

        //name validation
        if (!ValidationUtils.isValidName(firstName) || !ValidationUtils.isValidName(lastName)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid name.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!ValidationUtils.isValidPhone(phoneTextField.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
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

            // Retrieve the full customer details, including the customerID, to store in the session
            Customer registeredCustomer = customerModel.getCustomerByEmail(email);
            if (registeredCustomer != null) {
                // Update the UserSession with the registered customer's details
                UserSession.getInstance().setUserEmail(registeredCustomer.getEmail());
                UserSession.getInstance().setCustomerID(registeredCustomer.getCustomerID());
                UserSession.getInstance().setFirstName(registeredCustomer.getFirstName());
                UserSession.getInstance().setLastName(registeredCustomer.getLastName());

                // Open the CustomerDashboard
                CustomerDashboard dashboard = new CustomerDashboard();
                dashboard.setVisible(true);

            } else {
                // case where the user couldn't be retrieved after registration
                JOptionPane.showMessageDialog(this, "There was a problem retrieving your account details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}//end of class

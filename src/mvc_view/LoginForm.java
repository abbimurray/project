package mvc_view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import model.Customer;/*import customer class in model*/
import model.CustomerModel;/*import classes in model*/
import utils.ValidationUtils; /*importing validation class for email and password*/
import utils.HashingUtils;/*import class for hashing passwords*/
public class LoginForm extends JFrame {
    // add email and password fields,and buttons
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;//register will take them to register as new user
    private JButton cancelButton;  //reset


    public LoginForm() {
        setTitle("EV Charging System Login");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            // set a Look and Feel that handles color changes better
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
    }
    private void initializeUI() {
        JSplitPane splitpane = new JSplitPane();
        splitpane.setDividerLocation(400);//1/2 of the width
        splitpane.setEnabled(false);// can't move divider

        //Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(204, 255, 204));//MINT GREEN
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        ImageIcon logoIcon = new ImageIcon("src/mvc_view/electric-car-5.png"); //add logo to login page
        JLabel logoLabel = new JLabel(logoIcon);
        JLabel evChargingLabel = new JLabel("EV Charging");
        evChargingLabel.setForeground(new Color(36,35,37));
        evChargingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        evChargingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logoLabel);
        leftPanel.add(evChargingLabel);
        leftPanel.add(Box.createVerticalGlue());

        //Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 50)));// Add some space at the top

        JLabel loginLabel = new JLabel("Login in");
        loginLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rightPanel.add(loginLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // space

        JLabel welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        rightPanel.add(welcomeLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 60))); // space BETWEEN THE LOGIN +WELCOME MESSAGE AND THE TEXT FIELDS

        //Images
        ImageIcon keyIcon = new ImageIcon("src/GUI/Images/key.png");
        JLabel emailIconLabel = new JLabel(new ImageIcon("src/GUI/Images/email.png"));
        JLabel passwordIconLabel = new JLabel(new ImageIcon("src/GUI/Images/lock.png"));


        // Email Field
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        emailPanel.setBackground(Color.WHITE);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Set left alignment
        emailPanel.add(emailLabel);

        JPanel emailTextFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailTextField = new JTextField(20);
        emailTextField.setMaximumSize(new Dimension(300, 30)); // Set fixed width and height
        emailTextFieldPanel.add(emailIconLabel); // Add email icon to the left
        emailTextFieldPanel.add(emailTextField);
        emailPanel.add(emailTextFieldPanel);
        emailTextFieldPanel.setBackground(Color.WHITE);
        emailPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space

        rightPanel.add(emailPanel);

        // Password Field
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setBackground(Color.WHITE);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Set left alignment
        passwordPanel.add(passwordLabel);

        JPanel passwordTextFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(300, 30)); // Set fixed width and height
        passwordTextFieldPanel.add(passwordIconLabel); // Add password icon to the left
        passwordTextFieldPanel.add(passwordField);
        passwordPanel.add(passwordTextFieldPanel);
        passwordTextFieldPanel.setBackground(Color.WHITE);

        rightPanel.add(passwordPanel);


        //Buttons
      //  registerButton = new JButton("Register Here");//reset fields

        // Create a panel to hold the buttons - login and cancel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        // Set the background color of the button panel to white
        buttonPanel.setBackground(Color.WHITE);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setOpaque(true);
        loginButton.setBackground(new Color(204, 255, 204));
        loginButton.setForeground( new Color(36,35,37)); // Custom text color
        loginButton.setFont(new Font("Arial", Font.BOLD, 16)); // Custom font style
        buttonPanel.add(loginButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.setOpaque(true);
        cancelButton.setBackground(new Color(204, 255, 204));
        cancelButton.setForeground(new Color(36,35,37)); // Custom text color
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16)); // Custom font style
        buttonPanel.add(cancelButton);

        // Add the button panel to the right panel
        rightPanel.add(buttonPanel);

        //Register here button - not on button panel
        JLabel notUserLabel = new JLabel("Not already a user?");
        notUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(notUserLabel);
        registerButton = new JButton("Register Here");//reset fields
        customizeButton(registerButton);
        rightPanel.add(registerButton);

        // To add spacing at the bottom, add vertical glue after the last component
        rightPanel.add(Box.createVerticalGlue()); // Pushes everything up, adding space at the bottom

        //center components in right panel
        for (Component component : rightPanel.getComponents()) {
            ((JComponent) component).setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        splitpane.setLeftComponent(leftPanel);
        splitpane.setRightComponent(rightPanel);
        splitpane.setDividerSize(0);//removes visible divider line


        splitpane.setBackground(Color.WHITE);
        getContentPane().setBackground(Color.WHITE);
        //add split pane to jframe
        getContentPane().add(splitpane, BorderLayout.CENTER);


        // Event listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform login validation
                login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open registration form
                openRegistrationForm();
            }
        });


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the email and password fields
                emailTextField.setText("");
                passwordField.setText("");
            }
        });


    }


    // Custom method to customize buttons
    private void customizeButton(JButton button) {
        button.setOpaque(true);
        button.setBackground(new Color(204, 255, 204)); // Mint green
        button.setForeground(new Color(36, 35, 37)); // Custom text color
        button.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private void login() {
        String email = emailTextField.getText().trim();
        String inputPassword = new String(passwordField.getPassword());

        if (!ValidationUtils.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CustomerModel customerModel = new CustomerModel();
        Customer customer = customerModel.getCustomerByEmail(email);

        if (customer != null && customer.getSalt() != null) {
            String hashedInputPassword = HashingUtils.hashPasswordWithSHA256(inputPassword, customer.getSalt());

            if (hashedInputPassword.equals(customer.getPassword())) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Here, you can proceed to the next part of your application
                //add in opendashboard
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }




    private void openRegistrationForm() {
        // Instantiate and show the registration form
        RegistrationForm registrationForm = new RegistrationForm(this);
        registrationForm.setVisible(true);
        // hide the login form
        this.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}

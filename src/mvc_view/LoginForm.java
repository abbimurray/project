package mvc_view;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.UserSession;
import model.Customer;/*import customer class in model*/
import model.CustomerModel;/*import classes in model*/
import utils.UIUtils;
import utils.ValidationUtils; /*importing validation class for email and password*/
import utils.HashingUtils;/*import class for hashing passwords*/
public class LoginForm extends JFrame {
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;//register will take them to register as new user
    private JButton cancelButton;  //reset form


    public LoginForm() {
        setTitle("EV Charging System Login");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());//handles colour changes better
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
        ImageIcon logoIcon = new ImageIcon("src/images/car_logo.png"); //logo for login page
        JLabel logoLabel = new JLabel(logoIcon);
        JLabel evChargingLabel = new JLabel("EV Charging");
        evChargingLabel.setForeground(new Color(36,35,37));
        evChargingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        evChargingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //add to left panel
        leftPanel.add(Box.createVerticalGlue());//create space
        leftPanel.add(logoLabel);
        leftPanel.add(evChargingLabel);
        leftPanel.add(Box.createVerticalGlue());

        //Right Panel
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(Box.createRigidArea(new Dimension(0, 50)));//to create space at the top

        JLabel loginLabel = new JLabel("Login ");
        loginLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rightPanel.add(loginLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); //to create  space

        JLabel welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        rightPanel.add(welcomeLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 60))); // space between login message and text fields

        //Images
        ImageIcon keyIcon = new ImageIcon("src/GUI/Images/key.png");
        JLabel emailIconLabel = new JLabel(new ImageIcon("src/images/email.png"));
        JLabel passwordIconLabel = new JLabel(new ImageIcon("src/images/lock.png"));

        // Email Field
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        emailPanel.setBackground(Color.WHITE);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Set to  left alignment
        emailPanel.add(emailLabel);
        JPanel emailTextFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailTextField = new JTextField(20);
        emailTextField.setMaximumSize(new Dimension(300, 30)); // Set to fixed width and height
        emailTextFieldPanel.add(emailIconLabel); // Add email icon to the left
        emailTextFieldPanel.add(emailTextField);
        emailPanel.add(emailTextFieldPanel);
        emailTextFieldPanel.setBackground(Color.WHITE);
        emailPanel.add(Box.createRigidArea(new Dimension(0, 10))); // to add space
        rightPanel.add(emailPanel);//add to right panel

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
        rightPanel.add(passwordPanel);//add to right panel

        // Create a panel to hold the buttons - login and cancel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);// background color of the button panel set to white

        // Login Button
        loginButton = new JButton("Login");
        UIUtils.customizeButton(loginButton);
        buttonPanel.add(loginButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        UIUtils.customizeButton(cancelButton);
        buttonPanel.add(cancelButton);

        // Add the button panel to the right panel
        rightPanel.add(buttonPanel);

        //Register here button - not on button panel
        JLabel notUserLabel = new JLabel("Not already a user?");
        notUserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(notUserLabel);
        registerButton = new JButton("Register Here");
        UIUtils.customizeButton(registerButton);
        rightPanel.add(registerButton);

        rightPanel.add(Box.createVerticalGlue()); // adding space at the bottom

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

    private void login() {
        String email = emailTextField.getText().trim();
        String inputPassword = new String(passwordField.getPassword());

        if (!ValidationUtils.isValidEmail(email)) {//unsuccessful login
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CustomerModel customerModel = new CustomerModel();
        Customer customer = customerModel.getCustomerByEmail(email);

        if (customer != null && customer.getSalt() != null) {
            String hashedInputPassword = HashingUtils.hashPasswordWithSHA256(inputPassword, customer.getSalt());

            if (hashedInputPassword.equals(customer.getPassword())) {//successful login
                // setting the logged-in user's email in UserSession, it can be used then
                UserSession.getInstance().setUserEmail(email);

                UserSession session = UserSession.getInstance();
               // session.setUserEmail(email);

                //get other customer details
                session.setCustomerID(customer.getCustomerID());
                session.setFirstName(customer.getFirstName());
                session.setLastName(customer.getLastName());

                // Hide the LoginForm
                this.setVisible(false);
                //this.dispose(); // Dispose of the login form if you no longer need it

                // Open the CustomerDashboard
                CustomerDashboard dashboard = new CustomerDashboard();
                dashboard.setVisible(true);

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
}//end of class

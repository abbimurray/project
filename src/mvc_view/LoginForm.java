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
    // private JPanel loginPanel;

    public LoginForm() {
        setTitle("EV Charging System Login");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
    }

    private void initializeUI() {
        JSplitPane splitpane = new JSplitPane();
        splitpane.setDividerLocation(400);//1/3 of the width
        splitpane.setEnabled(false);// can't move divider

        //Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(204, 255, 204));//MINT GREEN
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        //add logo to login page
        ImageIcon logoIcon = new ImageIcon("src/mvc_view/electric-car-5.png");
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

        // Add some space at the top
        rightPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        JLabel loginLabel = new JLabel("Login in");
        loginLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        rightPanel.add(loginLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space

        JLabel welcomeLabel = new JLabel("Welcome back!");
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        rightPanel.add(welcomeLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space


        ImageIcon keyIcon = new ImageIcon("src/GUI/Images/key.png");
        JLabel emailIconLabel = new JLabel(new ImageIcon("src/GUI/Images/email.png"));
        JLabel passwordIconLabel = new JLabel(new ImageIcon("src/GUI/Images/password-3.png"));


        // emailTextField = new JTextField(15); // Reduce size of email field
        //passwordField = new JPasswordField(15);

        //rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Additional spacer if needed
        //rightPanel.add(imageLabel);

        // Email Field
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        emailPanel.add(emailLabel);

        emailTextField = new JTextField(20);
        emailTextField.setMaximumSize(new Dimension(300, 30)); // Set fixed width and height
        emailPanel.add(emailIconLabel); // Add email icon to the left
        emailPanel.add(emailTextField);

        rightPanel.add(emailPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space

        // Password Field
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordPanel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(300, 30)); // Set fixed width and height
        passwordPanel.add(passwordIconLabel); // Add password icon to the left
        passwordPanel.add(passwordField);

        rightPanel.add(passwordPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space




        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        registerButton = new JButton("Register Here");//reset fields



        //rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        //rightPanel.add(emailIconLabel);
        //rightPanel.add(emailTextField);
        //rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adjust spacing
        //rightPanel.add(passwordIconLabel);
        //rightPanel.add(passwordField);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adjust spacing
        rightPanel.add(loginButton);
        rightPanel.add(cancelButton);
        rightPanel.add(new JLabel("Not already a user?"));
        rightPanel.add(registerButton);


        //adding email and password box to right panel
        // rightPanel.add(Box.createRigidArea(new Dimension(0, 50)));//spacer
        // rightPanel.add(new JLabel("Email:"));
        //rightPanel.add(emailTextField);
        //rightPanel.add(new JLabel("Password:"));
        //rightPanel.add(passwordField);
        //rightPanel.add(loginButton);
        //rightPanel.add(cancelButton);
        //rightPanel.add(new JLabel("Not already a user?"));
        //rightPanel.add(registerButton);


        //center components in right panel
        for (Component component : rightPanel.getComponents()) {
            ((JComponent) component).setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        splitpane.setLeftComponent(leftPanel);
        splitpane.setRightComponent(rightPanel);
        splitpane.setDividerSize(0);//removes visible divider line

        //add split pane to jframe
        getContentPane().add(splitpane, BorderLayout.CENTER);



        //add(loginPanel, BorderLayout.CENTER);

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


//Student number:C00260073, Student name: Abigail Murray, Semester two

package mvc_view;

//imports
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//imports from my other packages
import controller.UserSession;
import model.Customer;
import model.CustomerModel;
import utils.UIUtils;


public class MyAccount extends JFrame {
    private Customer customer;

    private String customerEmail; // To hold the logged-in customer's email
    private CustomerModel customerModel;
    private JButton btnViewDetails, btnUpdateDetails, btnDeleteAccount;

    public MyAccount(String customerEmail) {
        this.customerEmail = customerEmail;
        this.customerModel = new CustomerModel();
        this.customer = fetchCustomerDetails(customerEmail); // Fetch every time it's opened
        if (this.customer == null) {
            JOptionPane.showMessageDialog(this, "Customer details not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        initializeButtons();
        initializeUI();
        setTitle("| PowerFlow | EV Charging System | My Profile |");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    private Customer fetchCustomerDetails(String email) {
        return customerModel.getCustomerByEmail(email);
    }

    private void initializeButtons() {
        // Initialize buttons with icons
        btnViewDetails = new JButton(" View My Details", new ImageIcon("src/images/view.png"));
        btnUpdateDetails = new JButton(" Update My Details", new ImageIcon("src/images/updated.png"));
        btnDeleteAccount = new JButton(" Delete My Account", new ImageIcon("src/images/delete.png"));

        // Customize buttons
        customizeButton(btnViewDetails);
        customizeButton(btnUpdateDetails);
        customizeButton(btnDeleteAccount);

        // add action listeners after buttons are initialized
        // Action listener for viewing details
        btnViewDetails.addActionListener(this::viewDetailsAction);
        // Action listener for updating details
        btnUpdateDetails.addActionListener(e -> updateDetailsAction());
        //Action listener for deleting customer
        btnDeleteAccount.addActionListener(e -> deleteAccountAction());

    }
    //styling for menu buttons
    private void customizeButton(JButton button) {
        button.setBackground(new Color(63, 97, 45)); //dark green colour
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }


    private void initializeUI() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Header panel setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint color
        JLabel titleLabel = new JLabel("My Profile", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        ImageIcon dashboardIcon = new ImageIcon("src/images/myprofileicon.png");
        JLabel iconLabel = new JLabel(dashboardIcon);

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

        //add to header panel
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(signOutLabel, BorderLayout.EAST);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);
        // Add some padding around the panel to move it down a bit
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        buttonsPanel.add(btnViewDetails);
        buttonsPanel.add(btnUpdateDetails);
        buttonsPanel.add(btnDeleteAccount);

        //Bottom panel with go back to dashboard
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Added spacing between buttons

        JButton btnReturnToDashboard = new JButton("Return to Dashboard");
        UIUtils.customizeButton(btnReturnToDashboard);
        btnReturnToDashboard.addActionListener(e -> {
            // Action to return to the dashboard
            dispose(); // Close the current form
            CustomerDashboard dashboard = new CustomerDashboard();
            dashboard.setVisible(true);
        });

    bottomPanel.add(btnReturnToDashboard);

    // Layout the panels
        add(headerPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
}


    private void viewDetailsAction(ActionEvent e) {
        Customer customer = customerModel.getCustomerByEmail(customerEmail);
        if (customer != null) {
            ViewMyDetails detailsPage = new ViewMyDetails(customer);
            detailsPage.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Customer details not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void updateDetailsAction() {
        Customer customer = customerModel.getCustomerByEmail(customerEmail);
        if (customer != null) {
            UpdateMyDetails updateDetailsPage = new UpdateMyDetails(customer);
            updateDetailsPage.setVisible(true);
            this.setVisible(false); // hide the MyAccount form
        } else {
            JOptionPane.showMessageDialog(this, "Unable to fetch customer details for update.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAccountAction() {
        // Ask for confirmation before deleting the account
        int confirmation = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete your account? This action cannot be undone.",
                "Delete Account",
                JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            boolean deleted = customerModel.deleteCustomerByEmail(customerEmail);
            if (deleted) {
                JOptionPane.showMessageDialog(this, "Your account has been successfully deleted.", "Account Deleted", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Close the MyAccount form
                // redirect to the login page or close the application
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete your account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}//end class


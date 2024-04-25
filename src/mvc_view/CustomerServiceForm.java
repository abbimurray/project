//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;

//imports from other packages
import controller.UserSession;
import utils.LoggerUtility;
import utils.UIUtils;
import mvc_view.exceptions.ResourceNotFoundException;

//imports
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;

public class CustomerServiceForm extends JFrame {

    private FAQ faqPanel;  // Declare the FAQ panel

    public CustomerServiceForm() {
        setTitle("| PowerFlow | EV Charging System | Customer Service |");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //initializeHeader();
        //setupContentPanel();
        //setupBottomPanel();

        //setVisible(true);
        try {
            initializeHeader();
            setupContentPanel();
            setupBottomPanel();
        } catch (ResourceNotFoundException e) {
            LoggerUtility.log(Level.SEVERE, "Failed to load resources", e);
            JOptionPane.showMessageDialog(this, "Failed to load necessary resources: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose(); // Close the window if critical resources fail to load
            return;
        }

        setVisible(true);
    }

    private void initializeHeader() throws ResourceNotFoundException {
        try {
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(new Color(204, 255, 204)); // Mint green background

            ImageIcon leftIcon = new ImageIcon(getClass().getResource("/images/customer-support.png"));
            JLabel leftLabel = new JLabel(leftIcon);
            headerPanel.add(leftLabel, BorderLayout.WEST);

            JLabel titleLabel = new JLabel("Customer Service", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            headerPanel.add(titleLabel, BorderLayout.CENTER);

            ImageIcon signOutIcon = new ImageIcon(getClass().getResource("/images/log-out.png"));
            JLabel signOutLabel = new JLabel(signOutIcon);
            signOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            signOutLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    UserSession.getInstance().clearSession();
                    dispose();
                    LoginForm loginForm = new LoginForm();
                    loginForm.setVisible(true);
                }
            });
            headerPanel.add(signOutLabel, BorderLayout.EAST);

            add(headerPanel, BorderLayout.NORTH);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Failed to initialize the header with necessary icons.Resources not found");
        }
    }


    private void setupContentPanel() throws ResourceNotFoundException {
        try {
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBackground(Color.WHITE);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Create space

            faqPanel = new FAQ();  // Instantiate the FAQ panel
            contentPanel.add(faqPanel, BorderLayout.CENTER);

            Font arialFont = new Font("Arial", Font.PLAIN, 16);

            // Contact Information
            JPanel contactPanel = new JPanel(new GridLayout(4, 1)); // Grid layout to stack contact info vertically
            contactPanel.setBackground(new Color(204, 255, 204)); // Mint background

            TitledBorder titledBorder = BorderFactory.createTitledBorder("Contact Us");
            titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 24)); // Font for the title
            contactPanel.setBorder(titledBorder);

            JLabel emailLabel = new JLabel("Email: support@powerflow.com");
            emailLabel.setFont(arialFont);
            contactPanel.add(emailLabel);

            JLabel phoneLabel = new JLabel("Phone: 123-456-7890");
            phoneLabel.setFont(arialFont);
            contactPanel.add(phoneLabel);

            JLabel websiteLabel = new JLabel("Website: www.powerflowevcharging.com");
            websiteLabel.setFont(arialFont);
            contactPanel.add(websiteLabel);

            contentPanel.add(contactPanel, BorderLayout.SOUTH);
            add(contentPanel, BorderLayout.CENTER);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Failed to set up the content panel with FAQ data and contact information.");
        }
    }

    private void setupBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnGoBack = new JButton("Return to Dashboard");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> {
            dispose();
            CustomerDashboard dashboard = new CustomerDashboard();
            dashboard.setVisible(true);
        });

        bottomPanel.add(btnGoBack);

        add(bottomPanel, BorderLayout.SOUTH);
    }

}//end class
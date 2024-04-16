package mvc_view;

import controller.UserSession;
import utils.UIUtils;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerServiceForm extends JFrame {

    private FAQ faqPanel;  // Declare the FAQ panel

    public CustomerServiceForm() {
        setTitle("Customer Service");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeHeader();
        setupContentPanel();
        setupBottomPanel();

        setVisible(true);
    }

    private void initializeHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green background

        ImageIcon leftIcon = new ImageIcon("src/images/customer-support.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Customer Service", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        ImageIcon signOutIcon = new ImageIcon("src/images/log-out.png");
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
    }

    private void setupContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        faqPanel = new FAQ();  // instantiate the FAQ panel
        contentPanel.add(faqPanel, BorderLayout.CENTER);

        Font arialFont = new Font("Arial", Font.PLAIN, 16);
        Font titleFont = new Font("Arial", Font.BOLD, 24);
        // Contact Information
        JPanel contactPanel = new JPanel(new GridLayout(4, 1)); // Grid layout to stack contact info vertically
        contactPanel.setBackground(new Color(204,255,204));

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Contact Us");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 24)); // font for the title
        // Apply the TitledBorder to the contactPanel
        contactPanel.setBorder(titledBorder);

        JLabel emailLabel = new JLabel("Email: support@evcharge.com");
        emailLabel.setFont(arialFont);
        contactPanel.add(emailLabel);

        JLabel phoneLabel = new JLabel("Phone: 123-456-7890");
        phoneLabel.setFont(arialFont);
        contactPanel.add(phoneLabel);

        JLabel websiteLabel = new JLabel("Website: www.evcharge.com");
        websiteLabel.setFont(arialFont);
        contactPanel.add(websiteLabel);

        contentPanel.add(contactPanel, BorderLayout.SOUTH);


        add(contentPanel, BorderLayout.CENTER);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomerServiceForm::new);
    }
}

/*
import controller.UserSession;
import utils.UIUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerServiceForm extends JFrame {
    public CustomerServiceForm() {
        setTitle("Customer Service");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeHeader();


        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        JButton callButton = new JButton("Call Us");
        JButton emailButton = new JButton("Email Us");
        JButton messageButton = new JButton("Send a Message");
        contentPanel.add(callButton);
        contentPanel.add(emailButton);
        contentPanel.add(messageButton);


        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        JButton btnGoBack = new JButton("Return to Dashboard");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> {
            // Action to return to the dashboard
            dispose(); // Close the current view
            CustomerDashboard dashboard = new CustomerDashboard();
            dashboard.setVisible(true);
        });

        bottomPanel.add(btnGoBack);//add button to bottom panel


        // Add panels to frame
        add(contentPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initializeHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green background

        // Left icon
        ImageIcon leftIcon = new ImageIcon("src/images/customer-support.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        // Title
        JLabel titleLabel = new JLabel("Customer Service", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Sign out icon
        ImageIcon signOutIcon = new ImageIcon("src/images/log-out.png");
        JLabel signOutLabel = new JLabel(signOutIcon);
        signOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signOutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Logout action
                UserSession.getInstance().clearSession(); // Clear user session
                dispose(); // Close this window
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true); // Show the login form again
            }
        });
        headerPanel.add(signOutLabel, BorderLayout.EAST);

        this.add(headerPanel, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomerServiceForm::new);
    }
}*/

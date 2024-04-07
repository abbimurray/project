package mvc_view;


import controller.UserSession;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReservationManagementForm extends JFrame {
    private JButton btnViewReservations, btnUpdateReservations, btnDeleteReservations, btnAddReservations;

    public ReservationManagementForm() {
        setTitle("Manage Reservations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeButtons(); // Initialize buttons first
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
    }


    private void initializeButtons() {
        // Initialize buttons with icons
        btnAddReservations = new JButton("Add Reservation", new ImageIcon("src/images/add.png"));
        btnViewReservations = new JButton(" View My Reservations", new ImageIcon("src/images/view.png"));
        btnUpdateReservations = new JButton(" Update My Reservations", new ImageIcon("src/images/update.png"));
        btnDeleteReservations = new JButton(" Delete My Reservations", new ImageIcon("src/images/delete.png"));

        // Set font for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        // Customize buttons
        customizeButton(btnAddReservations);
        customizeButton(btnViewReservations);
        customizeButton(btnUpdateReservations);
        customizeButton(btnDeleteReservations);

        // add action listeners after buttons are initialized
        //Action listener for adding reservation
        btnAddReservations.addActionListener(e -> {
            EventQueue.invokeLater(() -> {
                new AddNewReservationForm().setVisible(true);
            });
        });
        // Action listener for viewing details
//        btnViewReservations.addActionListener(e -> {
//            EventQueue.invokeLater(() -> {
//                ViewReservationsForm viewReservationsForm = new ViewReservationsForm();
//                viewReservationsForm.setVisible(true);
//            });
//        });


        btnViewReservations.addActionListener(e -> {
            ViewReservationsForm viewReservationsForm = new ViewReservationsForm();
            viewReservationsForm.setVisible(true);
        });
        // Action listener for updating details
        // btnUpdateReservations.addActionListener(e -> updateReservationsAction());
        //Action listener for deleting customer
        // btnDeleteReservations.addActionListener(e -> deleteReservationsAction());

    }

    //styling for menu buttons
    private void customizeButton(JButton button) {
        button.setBackground(new Color(63, 97, 45)); //dark green colour
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    private void initializeUI() {
        getContentPane().setBackground(Color.WHITE); // Set the background of the content pane to white
        setLayout(new BorderLayout());

        // Header panel setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint color
        JLabel titleLabel = new JLabel("Manage Reservations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        ImageIcon dashboardIcon = new ImageIcon("src/images/reserved.png");
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
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonsPanel.setBackground(Color.WHITE); // Ensure this panel's background is also white
        // Add some padding around the panel to move it down a bit
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        buttonsPanel.add(btnAddReservations);
        buttonsPanel.add(btnViewReservations);
        buttonsPanel.add(btnUpdateReservations);
        buttonsPanel.add(btnDeleteReservations);

        //Bottom panel with go back to dashboard
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE); // Set the background color to white
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Added spacing between buttons

        JButton btnReturnToDashboard = new JButton("Return to Dashboard");
        UIUtils.customizeButton(btnReturnToDashboard);
        btnReturnToDashboard.addActionListener(e -> {
            // Action to return to the dashboard
            dispose(); // Close the current view
            CustomerDashboard dashboard = new CustomerDashboard();
            dashboard.setVisible(true);
        });

        bottomPanel.add(btnReturnToDashboard);

        // Layout the components
        add(headerPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
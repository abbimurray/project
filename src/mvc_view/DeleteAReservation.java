/*package mvc_view;

import controller.ReservationController;
import model.Reservation;
import utils.UIUtils;
import controller.UserSession;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DeleteAReservation extends JFrame {
    private JComboBox<Reservation> reservationComboBox;
    private JButton deleteButton;

    private ReservationController reservationController;

    public DeleteAReservation() {
        reservationController = new ReservationController();

        initializeUI();
    }

    private void initializeHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green

        // Left icon
        ImageIcon leftIcon = new ImageIcon("src/images/reserved.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        // Title
        JLabel titleLabel = new JLabel("Delete A Reservation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Sign out icon
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
        headerPanel.add(signOutLabel, BorderLayout.EAST);

        this.add(headerPanel, BorderLayout.NORTH);
    }
    private void initializeUI() {
        setTitle("Delete Reservation");
        setSize(800, 600);
        setLayout(new BorderLayout()); // Change layout to BorderLayout
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Initialize header panel
        initializeHeader();

        // Panel for form fields
        JPanel formPanel = new JPanel(new BorderLayout()); // Use BorderLayout
        formPanel.setBackground(Color.WHITE);



        // Create an empty border to move the components down
        int topPadding = 200; // top padding
        int bottomPadding = 200; // bottom padding
        formPanel.setBorder(BorderFactory.createEmptyBorder(topPadding, 0, bottomPadding, 0));

        formPanel.setBorder(BorderFactory.createEmptyBorder(topPadding, 0, 0, 0));

        List<Reservation> reservations = reservationController.getReservationsForCustomer(UserSession.getInstance().getCustomerID());
        reservationComboBox = new JComboBox<>(reservations.toArray(new Reservation[0]));

        reservationComboBox.setPreferredSize(new Dimension(200, 30));
        reservationComboBox.setMaximumSize(new Dimension(200, 30));
        reservationComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Reservation) {
                    Reservation reservation = (Reservation) value;
                    setText(String.format("ID: %d - %s", reservation.getReservationID(), reservation.getReservationDateTime().toString()));
                }
                return this;
            }
        });

        Font labelFont = new Font("Arial", Font.BOLD, 20); // Create a Font object for label

        JLabel selectLabel = new JLabel("Select Reservation to Delete:");
        selectLabel.setFont(labelFont);

        // Panel for label and combo box
        JPanel labelComboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center align the label and combo box
        labelComboBoxPanel.setBackground(Color.WHITE); // Set background color to white
        labelComboBoxPanel.add(selectLabel);
        labelComboBoxPanel.add(reservationComboBox);

        formPanel.add(labelComboBoxPanel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center align the button panel
        buttonPanel.setBackground(Color.WHITE);

        deleteButton = new JButton("Delete");
        UIUtils.customizeButton(deleteButton);
        deleteButton.addActionListener(this::deleteReservationAction);
        buttonPanel.add(deleteButton);

        JButton btnGoBack = new JButton("Go Back");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> dispose()); // Close this window
        buttonPanel.add(btnGoBack);

        formPanel.add(buttonPanel, BorderLayout.CENTER); // Add button panel to the center of formPanel

        add(formPanel, BorderLayout.CENTER); // Add formPanel to the center of the frame
    }*/



    /*private void initializeUI() {
        setTitle("Delete Reservation");
        setSize(800, 600);
        setLayout(new BorderLayout()); // Change layout to BorderLayout
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Initialize header panel
        initializeHeader();

        // Panel for form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS)); // Use BoxLayout with Y_AXIS alignment
        formPanel.setBackground(Color.WHITE);

        // Create an empty border with the desired space between the header and formPanel
        int topPadding = 50; // create space between the header and the fields of the form
        formPanel.setBorder(BorderFactory.createEmptyBorder(topPadding, 0, 0, 0));

        List<Reservation> reservations = reservationController.getReservationsForCustomer(UserSession.getInstance().getCustomerID());
        reservationComboBox = new JComboBox<>(reservations.toArray(new Reservation[0]));

        reservationComboBox.setPreferredSize(new Dimension(200, 30));
        reservationComboBox.setMaximumSize(new Dimension(200,30));
        reservationComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Reservation) {
                    Reservation reservation = (Reservation) value;
                    setText(String.format("ID: %d - %s", reservation.getReservationID(), reservation.getReservationDateTime().toString()));
                }
                return this;
            }
        });


        Font labelFont = new Font("Arial", Font.BOLD, 20); // Create a Font object for label
        JLabel selectLabel = new JLabel("Select Reservation to Delete:");
        selectLabel.setFont(labelFont);
        formPanel.add(selectLabel);

        formPanel.add(reservationComboBox);

        deleteButton = new JButton("Delete");
        UIUtils.customizeButton(deleteButton);
        deleteButton.addActionListener(this::deleteReservationAction);
        formPanel.add(deleteButton);

        // Add a rigid area to create some space between the form components and the "Go Back" button
        formPanel.add(Box.createVerticalStrut(200));

        // Panel for "Go Back" button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.setBackground(Color.WHITE);
        JButton btnGoBack = new JButton("Go Back");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> dispose()); // Close this window
        backButtonPanel.add(btnGoBack);

        // Add "Go Back" button panel to formPanel
        formPanel.add(backButtonPanel);

        add(formPanel, BorderLayout.CENTER);
    }*/

    /*private void initializeUI() {
        setTitle("Delete Reservation");
        setSize(800, 600);
        setLayout(new BorderLayout()); // Change layout to BorderLayout
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Initialize header panel
        initializeHeader();

        // Panel for form fields
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // FlowLayout for the form fields
        formPanel.setBackground(Color.WHITE);

        List<Reservation> reservations = reservationController.getReservationsForCustomer(UserSession.getInstance().getCustomerID());
        reservationComboBox = new JComboBox<>(reservations.toArray(new Reservation[0]));
        reservationComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Reservation) {
                    Reservation reservation = (Reservation) value;
                    setText(String.format("ID: %d - %s", reservation.getReservationID(), reservation.getReservationDateTime().toString()));
                }
                return this;
            }
        });
        formPanel.add(new JLabel("Select Reservation to Delete:"));
        formPanel.add(reservationComboBox);

        deleteButton = new JButton("Delete");
        UIUtils.customizeButton(deleteButton);
        deleteButton.addActionListener(this::deleteReservationAction);
        formPanel.add(deleteButton);

        //go back button at bottom
        JButton btnGoBack = new JButton("Go Back");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> dispose()); // Close this window
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backPanel.setBackground(Color.WHITE);
        backPanel.add(btnGoBack);
        formPanel.add(backPanel, BorderLayout.SOUTH);
        add(formPanel,BorderLayout.CENTER);
    }*/
/*

    private void deleteReservationAction(ActionEvent event) {
        Reservation selectedReservation = (Reservation) reservationComboBox.getSelectedItem();
        if (selectedReservation != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this reservation?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = reservationController.deleteReservation(selectedReservation.getReservationID());
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Reservation deleted successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete reservation.");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeleteAReservation().setVisible(true));
    }
}*/
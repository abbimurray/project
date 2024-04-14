package mvc_view;

import controller.ReservationController;
import controller.UserSession;
import model.Reservation;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ViewReservationsForm extends JFrame {
    private ReservationController reservationController;

    public ViewReservationsForm() {
        setTitle("View Reservations");
        setSize(800, 600);
        setLayout(new BorderLayout());
        initializeUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeUI() {
        // Header setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green
        JLabel iconLabel = new JLabel(new ImageIcon("src/images/reserved.png"));
        headerPanel.add(iconLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Viewing My Reservations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel signOutLabel = new JLabel(new ImageIcon("src/images/log-out.png"));
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

        // Displaying reservations using JTextArea inside JScrollPane
        reservationController = new ReservationController();
        int currentUserId = UserSession.getInstance().getCustomerID();
        List<Reservation> reservations = reservationController.getReservationsForCustomer(currentUserId);

        JTextArea reservationsTextArea = new JTextArea();
        reservationsTextArea.setEditable(false);
        reservationsTextArea.setFont(new Font("Arial", Font.PLAIN, 18));

        StringBuilder sb = new StringBuilder();
        for (Reservation reservation : reservations) {
            sb.append("Reservation ID: ").append(reservation.getReservationID())
                    .append(", Station ID: ").append(reservation.getStationID())
                    .append(", Charger ID: ").append(reservation.getChargerID())
                    .append(", Date and Time: ")
                    .append(reservation.getReservationDateTime() != null ? reservation.getReservationDateTime().toString() : "Not set")
                    .append(", Status: ").append(reservation.getStatus()).append("\n");
        }

        reservationsTextArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(reservationsTextArea);
        add(scrollPane, BorderLayout.CENTER);

        // Go Back Button at the bottom
        JButton goBackButton = new JButton("Go Back");
        UIUtils.customizeButton(goBackButton);
        goBackButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(goBackButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}

/*
import controller.ReservationController;
import controller.UserSession;
import model.Reservation;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ViewReservationsForm extends JFrame {
    private JTextArea reservationsTextArea;
    private ReservationController reservationController;

    public ViewReservationsForm() {
        setTitle("View Reservations");
        setSize(800, 600);
        setLayout(new BorderLayout());
        initializeUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeUI() {

        setLayout(new BorderLayout(10, 10)); // Add some spacing
        // Icon
        ImageIcon userIcon = new ImageIcon("src/images/reserved.png");
        JLabel iconLabel = new JLabel(userIcon);

        // Title Label
        JLabel titleLabel = new JLabel("Viewing My Reservations");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Header Panel with BorderLayout for better control
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));
        headerPanel.add(iconLabel, BorderLayout.WEST);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));//vertical gap to push title down a bit
        titlePanel.setBackground(new Color(204, 255, 204)); // Match the header panel's background
        titlePanel.add(titleLabel);
        headerPanel.add(titlePanel, BorderLayout.CENTER);

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


//////need to change this section - maybe use Jtable instead of text area as this doesn't look great
        reservationController = new ReservationController();
        reservationsTextArea = new JTextArea(10, 50);
        reservationsTextArea.setEditable(false); // Make the text area non-editable
        JScrollPane scrollPane = new JScrollPane(reservationsTextArea);
        int currentUserId = UserSession.getInstance().getCustomerID();
        System.out.println("Current User ID: " + currentUserId);
        List<Reservation> reservations = reservationController.getReservationsForCustomer(currentUserId);

        System.out.println("Reservations: " + reservations);//debug

        StringBuilder sb = new StringBuilder();
        for (Reservation reservation : reservations) {
            sb.append("Reservation ID: ").append(reservation.getReservationID())
                    .append(", Station ID: ").append(reservation.getStationID())
                    .append(", Charger ID: ").append(reservation.getChargerID())
                    .append(", Date and Time: ")
                    .append(reservation.getReservationDateTime() != null ? reservation.getReservationDateTime().toString() : "Not set")
                    .append(", Status: ").append(reservation.getStatus()).append("\n");
        }

        reservationsTextArea.setText(sb.toString());
        add(scrollPane, BorderLayout.CENTER);

        //////

        // Go Back Button
        JButton goBackButton = new JButton("Go Back");
        UIUtils.customizeButton(goBackButton);
        goBackButton.addActionListener(e -> dispose()); // Close this window
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE); // Set to white for the form look
        bottomPanel.add(goBackButton);

        //add panels
        add(bottomPanel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);

    }



}//end class*/

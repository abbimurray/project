package mvc_view;


import controller.ReservationController;
import controller.UserSession;
import model.Reservation;

import utils.UIUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class AddNewReservationForm extends JFrame {
    private JTextField txtStationID;

    private JTextField txtChargerID;
    private JTextField txtDateTime;
    private JButton btnSave;
    private JButton btnCancel;


    public AddNewReservationForm() {
        setTitle("Add New Reservation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600); // This should work, ensure it's not overridden by pack()

        initializeHeader();
        initializeFormFieldsAndFooter();

        setLocationRelativeTo(null); // Center the form
    }


    private void initializeHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green

        // Left icon
        ImageIcon leftIcon = new ImageIcon("src/images/reserved.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        // Title
        JLabel titleLabel = new JLabel("Add New Reservation", SwingConstants.CENTER);
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
    private void initializeFormFieldsAndFooter() {
        this.getContentPane().setBackground(Color.WHITE); // Set form background to white

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        // Station ID Label and Field
        JLabel lblStationID = new JLabel("Station ID:");
        lblStationID.setFont(fieldFont);
        fieldsPanel.add(lblStationID, gbc);

        gbc.gridx = 1;
        txtStationID = new JTextField(20);
        txtStationID.setFont(fieldFont);
        fieldsPanel.add(txtStationID, gbc);

        // Reset for next row
        gbc.gridx = 0;
        gbc.gridy++;

        // Charger ID Label and Field
        JLabel lblChargerID = new JLabel("Charger ID:");
        lblChargerID.setFont(fieldFont);
        fieldsPanel.add(lblChargerID, gbc);

        gbc.gridx = 1;
        txtChargerID = new JTextField(20);
        txtChargerID.setFont(fieldFont);
        fieldsPanel.add(txtChargerID, gbc);

        // Reset for next row
        gbc.gridx = 0;
        gbc.gridy++;

        // Date and Time Label and Field
        JLabel lblDateTime = new JLabel("Date and Time (YYYY-MM-DD HH:MM):");
        lblDateTime.setFont(fieldFont);
        fieldsPanel.add(lblDateTime, gbc);

        gbc.gridx = 1;
        txtDateTime = new JTextField(20);
        txtDateTime.setFont(fieldFont);
        fieldsPanel.add(txtDateTime, gbc);

        // Footer Panel setup
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);

        // Button Panel for Save and Cancel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align buttons to the right
        buttonPanel.setBackground(Color.WHITE); // Match the form's background

        // Initialize the Save button
        btnSave = new JButton("Save");
        UIUtils.customizeButton(btnSave); // method sets the desired styling
        btnSave.addActionListener(this::saveReservationAction);
        buttonPanel.add(btnSave);

        // Initialize the Cancel button
        btnCancel = new JButton("Cancel");
        UIUtils.customizeButton(btnCancel);
        btnCancel.addActionListener(e -> dispose());
        buttonPanel.add(btnCancel);

        // back button
        JButton backToReservationManagementButton = new JButton("Back to Reservation Management");
        UIUtils.customizeButton(backToReservationManagementButton);
        backToReservationManagementButton.addActionListener(e -> {
            this.dispose(); // Close the current form

        });
        footerPanel.add(backToReservationManagementButton, BorderLayout.CENTER);
        footerPanel.add(buttonPanel, BorderLayout.SOUTH); // Add the buttonPanel to the footerPanel

        // Adding fieldsPanel and footerPanel to the form
        this.add(fieldsPanel, BorderLayout.CENTER);
        this.add(footerPanel, BorderLayout.SOUTH);
    }


    /*
    {

        add(new JLabel("Station ID:"));
        txtStationID = new JTextField();
        add(txtStationID);

        add(new JLabel("Charger ID:"));
        txtChargerID = new JTextField();
        add(txtChargerID);

        add(new JLabel("Date and Time (YYYY-MM-DD HH:MM):"));
        txtDateTime = new JTextField();
        add(txtDateTime);

        btnSave = new JButton("Save");
        btnSave.addActionListener(this::saveReservationAction);
        add(btnSave);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);

        pack();
        setLocationRelativeTo(null);
    }*/


    private void saveReservationAction(ActionEvent event) {
        int customerID = UserSession.getInstance().getCustomerID();
        try {
            int stationID = Integer.parseInt(txtStationID.getText().trim());
            int chargerID = Integer.parseInt(txtChargerID.getText().trim());
            LocalDateTime reservationDateTime = LocalDateTime.parse(txtDateTime.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            ReservationController controller = new ReservationController();
            if (!controller.isChargerAvailableAtStation(chargerID, stationID)) {
                JOptionPane.showMessageDialog(this, "No charger ID matching this at this station or charger not available.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String status = "Reserved";
            Reservation newReservation = new Reservation(reservationDateTime, status, stationID, customerID, chargerID);

            if (controller.addReservation(newReservation)) {
                JOptionPane.showMessageDialog(this, "Reservation added successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add reservation.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers for station ID and charger ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date and time format. Please use 'yyyy-MM-dd HH:mm'.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}//END CLASS
    /*private void saveReservationAction(ActionEvent event) {
        // Retrieve customerID from the UserSession
        int customerID = UserSession.getInstance().getCustomerID();

        try {
            //  text fields  for stationID and reservationDateTime
            int stationID = Integer.parseInt(txtStationID.getText().trim());
            int chargerID = Integer.parseInt(txtChargerID.getText().trim());

            // Assuming dateTime input is in the format "yyyy-MM-dd HH:mm"
            LocalDateTime reservationDateTime = LocalDateTime.parse(txtDateTime.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String status = "Confirmed";//set the status in reservations table

            // Create the Reservation object with the collected data
            Reservation newReservation = new Reservation();
            newReservation.setStationID(stationID);
            newReservation.setReservationDateTime(reservationDateTime);
            newReservation.setCustomerID(customerID);
            newReservation.setStatus(status);
            newReservation.setChargerID(chargerID);

            // Use the ReservationController to add the reservation to the database
            ReservationController controller = new ReservationController();
            if (controller.addReservation(newReservation)) {
                JOptionPane.showMessageDialog(this, "Reservation added successfully.");
                dispose(); // Optionally close the form or clear fields for a new entry
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add reservation.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid station ID. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date and time format. Please follow the format 'yyyy-MM-dd HH:mm'.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving the reservation. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/



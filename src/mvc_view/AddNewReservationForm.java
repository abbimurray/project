//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;

import controller.ReservationController;
import controller.UserSession;
import model.Reservation;
import utils.LoggerUtility;
import utils.UIUtils;
import mvc_view.exceptions.InvalidDateRangeException;
import mvc_view.exceptions.InvalidInputException;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;


public class AddNewReservationForm extends JFrame {
    private JTextField txtStationID, txtChargerID, txtStartTime, txtEndTime;
    private JButton btnSave, btnGoBack;


    public AddNewReservationForm() {
        setTitle("Add New Reservation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);
        initializeHeader();
        initializeFormFields();
        setLocationRelativeTo(null); // Center on screen
    }

    private void initializeHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green background

        // Left icon
        JLabel leftIcon = new JLabel(new ImageIcon("src/images/reserved.png"));
        headerPanel.add(leftIcon, BorderLayout.WEST);

        // Title
        JLabel titleLabel = new JLabel("Add New Reservation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Sign out icon
        JLabel signOutIcon = new JLabel(new ImageIcon("src/images/log-out.png"));
        signOutIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signOutIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserSession.getInstance().clearSession();
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        headerPanel.add(signOutIcon, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);
    }
    private void initializeFormFields() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Arial", Font.BOLD, 16); // Define the font

        JLabel lblStationID = new JLabel("Station ID:");
        lblStationID.setFont(labelFont);
        formPanel.add(lblStationID);
        txtStationID = new JTextField();
        formPanel.add(txtStationID);

        JLabel lblChargerID = new JLabel("Charger ID:");
        lblChargerID.setFont(labelFont);
        formPanel.add(lblChargerID);
        txtChargerID = new JTextField();
        formPanel.add(txtChargerID);

        JLabel lblStartTime = new JLabel("Start Time (yyyy-MM-dd HH:mm):");
        lblStartTime.setFont(labelFont);
        formPanel.add(lblStartTime);
        txtStartTime = new JTextField();
        formPanel.add(txtStartTime);

        JLabel lblEndTime = new JLabel("End Time (yyyy-MM-dd HH:mm):");
        lblEndTime.setFont(labelFont);
        formPanel.add(lblEndTime);
        txtEndTime = new JTextField();
        formPanel.add(txtEndTime);

        btnSave = new JButton("Save");
        UIUtils.customizeButton(btnSave);
        btnSave.addActionListener(this::saveReservationAction);
        formPanel.add(btnSave);

        btnGoBack = new JButton("Go Back");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> {
            dispose(); // Close current form
            ReservationManagementForm managementForm = new ReservationManagementForm();
            managementForm.setVisible(true); // Open the Reservation Management Form
        });
        formPanel.add(btnGoBack);

        add(formPanel, BorderLayout.CENTER);
    }



    /**
     * Parses the input string to extract an integer ID for a station or charger.
     *
     *  attempts to convert the string input into an integer. It is designed to be used
     * for parsing IDs for entities such as stations or chargers
     * If the input string cannot be parsed into an integer due to format issues, it throws an InvalidInputException.
     *
     * @param input The string input that needs to be parsed into an integer ID.
     * @param type A string describing the type of ID being parsed, e.g., "station" or "charger".
     * @return int The parsed integer ID from the input string.
     * @throws InvalidInputException If the input string is not a valid integer, indicating an error in ID format.
     */
private int parseStationOrChargerID(String input, String type) throws InvalidInputException {
    try {
        return Integer.parseInt(input);
    } catch (NumberFormatException e) {
        throw new InvalidInputException("Invalid " + type + " ID: " + input);
    }
}



    /**
     * Handles saving a new reservation.
     *
     * processes customer inputs from a form, validates them, and attempts to add a new reservation.
     * It checks if the provided station and charger IDs are valid integers, the reservation times are in the future,
     * and the end time is after the start time. It then creates a reservation and attempts to add it using the
     * ReservationController. Feedback is provided to the user through dialog messages.
     *
     * @param e The ActionEvent triggered by the user's action.
     * @throws NumberFormatException if the station or charger IDs are not valid integers.
     * @throws InvalidInputException if the station or charger IDs are invalid after parsing.
     * @throws DateTimeParseException if the start or end time inputs cannot be parsed into valid LocalDateTime objects.
     * @throws InvalidDateRangeException if the start or end time are in the past, or if the end time is before the start time.
     */
    private void saveReservationAction(ActionEvent e) {
        try {
            int stationID = parseStationOrChargerID(txtStationID.getText().trim(), "station");
            int chargerID = parseStationOrChargerID(txtChargerID.getText().trim(), "charger");
            LocalDateTime startTime = LocalDateTime.parse(txtStartTime.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime endTime = LocalDateTime.parse(txtEndTime.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            // Check if the start time or end time is in the past
            LocalDateTime now = LocalDateTime.now();
            if (startTime.isBefore(now) || endTime.isBefore(now)) {
                throw new InvalidDateRangeException("Reservation times must be in the future.");
            }

            if (startTime.isAfter(endTime)) {
                throw new InvalidDateRangeException("End time must be after start time.");
            }

            Reservation reservation = new Reservation();
            reservation.setStationID(stationID);
            reservation.setChargerID(chargerID);
            reservation.setCustomerID(UserSession.getInstance().getCustomerID());
            reservation.setReservationStartTime(startTime);
            reservation.setReservationEndTime(endTime);
            reservation.setStatus("Reserved");

            ReservationController controller = new ReservationController();
            if (controller.addReservation(reservation)) {
                JOptionPane.showMessageDialog(this, "Reservation added successfully.");
                dispose(); // Close the form
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add reservation.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException | InvalidInputException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for station and charger IDs.", "Input Error", JOptionPane.ERROR_MESSAGE);
            LoggerUtility.log(Level.WARNING, "Invalid input for station or charger ID", ex);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid dates in the format 'yyyy-MM-dd HH:mm'.", "Input Error", JOptionPane.ERROR_MESSAGE);
            LoggerUtility.log(Level.WARNING, "Date time parsing failed", ex);
        } catch (InvalidDateRangeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            LoggerUtility.log(Level.WARNING, "Invalid date range", ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
            LoggerUtility.log(Level.SEVERE, "Unexpected error", ex);
        }
    }



    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new AddNewReservationForm().setVisible(true));
    }
}


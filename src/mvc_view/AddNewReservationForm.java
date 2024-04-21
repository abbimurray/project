//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;

import controller.ReservationController;
import controller.UserSession;
import model.Reservation;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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


    private void saveReservationAction(ActionEvent e) {
        try {
            int stationID = Integer.parseInt(txtStationID.getText().trim());
            int chargerID = Integer.parseInt(txtChargerID.getText().trim());
            LocalDateTime startTime = LocalDateTime.parse(txtStartTime.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime endTime = LocalDateTime.parse(txtEndTime.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if (startTime.isAfter(endTime)) {
                JOptionPane.showMessageDialog(this, "End time must be after start time.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Reservation reservation = new Reservation();
            reservation.setStationID(stationID);
            reservation.setChargerID(chargerID);
            reservation.setCustomerID(UserSession.getInstance().getCustomerID()); // UserSession holds the logged-in user info
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
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for station and charger IDs.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid dates in the format 'yyyy-MM-dd HH:mm'.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new AddNewReservationForm().setVisible(true));
    }
}


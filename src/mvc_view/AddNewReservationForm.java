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
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel instructionLabel = new JLabel("Fill in the reservation fields below");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        fieldsPanel.add(instructionLabel, gbc);

        gbc.gridy++;

        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        JLabel lblStationID = new JLabel("Station ID:");
        lblStationID.setFont(fieldFont);
        gbc.gridy++;
        fieldsPanel.add(lblStationID, gbc);

        txtStationID = new JTextField(20);
        txtStationID.setFont(fieldFont);
        gbc.gridx = 1;
        fieldsPanel.add(txtStationID, gbc);

        JLabel lblChargerID = new JLabel("Charger ID:");
        lblChargerID.setFont(fieldFont);
        gbc.gridx = 0;
        gbc.gridy++;
        fieldsPanel.add(lblChargerID, gbc);

        txtChargerID = new JTextField(20);
        txtChargerID.setFont(fieldFont);
        gbc.gridx = 1;
        fieldsPanel.add(txtChargerID, gbc);

        JLabel lblDateTime = new JLabel("Date and Time (YYYY-MM-DD HH:MM):");
        lblDateTime.setFont(fieldFont);
        gbc.gridx = 0;
        gbc.gridy++;
        fieldsPanel.add(lblDateTime, gbc);

        txtDateTime = new JTextField(20);
        txtDateTime.setFont(fieldFont);
        gbc.gridx = 1;
        fieldsPanel.add(txtDateTime, gbc);


        // Increase space before adding buttons
        gbc.gridy++;
        gbc.insets = new Insets(20, 10, 10, 10); // Increase top margin

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        btnSave = new JButton("Save");
        btnCancel = new JButton("Cancel");
        UIUtils.customizeButton(btnSave);
        UIUtils.customizeButton(btnCancel);

        Dimension buttonSize = new Dimension(Math.max(btnSave.getPreferredSize().width, btnCancel.getPreferredSize().width),
                Math.max(btnSave.getPreferredSize().height, btnCancel.getPreferredSize().height));
        btnSave.setPreferredSize(buttonSize);
        btnCancel.setPreferredSize(buttonSize);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        btnSave.addActionListener(this::saveReservationAction);
        btnCancel.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        fieldsPanel.add(buttonPanel, gbc);

        mainPanel.add(fieldsPanel, BorderLayout.CENTER);

        //
        // Go Back button at the bottom
        JButton btnGoBack = new JButton("Go Back");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> dispose()); // Close this window
        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backPanel.setBackground(Color.WHITE);
        backPanel.add(btnGoBack);
        mainPanel.add(backPanel, BorderLayout.SOUTH);

        //



        this.add(mainPanel, BorderLayout.CENTER);
    }


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


package mvc_view;

import controller.ReservationController;
import model.Reservation;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UpdateReservation extends JFrame {
    private JTextField txtStationID, txtChargerID, txtStartTime, txtEndTime;
    private JButton btnSave, btnCancel;
    private ReservationController controller;
    private Reservation currentReservation;
    private JComboBox<Reservation> reservationComboBox;

    public UpdateReservation() {
        controller = new ReservationController();
        setTitle("Update Reservation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        initializeHeader();
        add(createFormPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    private void initializeHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green

        ImageIcon leftIcon = new ImageIcon("src/images/update.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Update Reservation", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        ImageIcon signOutIcon = new ImageIcon("src/images/log-out.png");
        JLabel signOutLabel = new JLabel(signOutIcon);
        signOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signOutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        headerPanel.add(signOutLabel, BorderLayout.EAST);

        this.add(headerPanel, BorderLayout.NORTH);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(Box.createVerticalStrut(20)); // Space before the first element

        // Drop-down menu to select the reservation to update
        reservationComboBox = new JComboBox<>();
        loadReservations();
        formPanel.add(reservationComboBox);

        txtStationID = new JTextField(10);
        formPanel.add(createLabeledField("Station ID:", txtStationID));

        txtChargerID = new JTextField(10);
        formPanel.add(createLabeledField("Charger ID:", txtChargerID));

        txtStartTime = new JTextField(10);
        formPanel.add(createLabeledField("Start Time (yyyy-MM-dd HH:mm):", txtStartTime));

        txtEndTime = new JTextField(10);
        formPanel.add(createLabeledField("End Time (yyyy-MM-dd HH:mm):", txtEndTime));

        return formPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSave = new JButton("Save");
        btnSave.addActionListener(this::saveReservation);
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> dispose());

        footerPanel.add(btnSave);
        footerPanel.add(btnCancel);
        return footerPanel;
    }

    private void loadReservations() {
        // Load reservations from the database and add them to the reservationComboBox
    }

    private JPanel createLabeledField(String label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(textField);
        return panel;
    }

    private void saveReservation(ActionEvent e) {
        // Implementation of save logic
    }
}


/*public class UpdateReservation extends JFrame {
    private JTextField txtReservationID;
    private JTextField txtNewDateTime;
    private JButton btnSave;
    private JButton btnCancel;

    public UpdateReservation(int reservationID) {
        setTitle("Update Reservation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);

        initializeForm(reservationID);
        setLocationRelativeTo(null);
    }

    private void initializeForm(int reservationID) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        JLabel lblReservationID = new JLabel("Reservation ID:");
        lblReservationID.setFont(fieldFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        fieldsPanel.add(lblReservationID, gbc);

        txtReservationID = new JTextField(20);
        txtReservationID.setFont(fieldFont);
        txtReservationID.setEditable(false);
        txtReservationID.setText(String.valueOf(reservationID));
        gbc.gridx = 1;
        fieldsPanel.add(txtReservationID, gbc);

        JLabel lblNewDateTime = new JLabel("New Date and Time (YYYY-MM-DD HH:MM):");
        lblNewDateTime.setFont(fieldFont);
        gbc.gridx = 0;
        gbc.gridy++;
        fieldsPanel.add(lblNewDateTime, gbc);

        txtNewDateTime = new JTextField(20);
        txtNewDateTime.setFont(fieldFont);
        gbc.gridx = 1;
        fieldsPanel.add(txtNewDateTime, gbc);

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

        btnSave.addActionListener(e -> saveReservationAction(reservationID));
        btnCancel.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        fieldsPanel.add(buttonPanel, gbc);

        mainPanel.add(fieldsPanel, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void saveReservationAction(int reservationID) {
        try {
            LocalDateTime newDateTime = LocalDateTime.parse(txtNewDateTime.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            ReservationController controller = new ReservationController();
            Reservation existingReservation = controller.getReservationById(reservationID);
            existingReservation.setReservationDateTime(newDateTime);

            if (controller.updateReservation(existingReservation)) {
                JOptionPane.showMessageDialog(this, "Reservation updated successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update reservation.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date and time format. Please use 'yyyy-MM-dd HH:mm'.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}*/

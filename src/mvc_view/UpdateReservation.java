//Student number:C00260073, Student name: Abigail Murray, Semester two

package mvc_view;

import controller.ReservationController;
import controller.UserSession;
import model.Reservation;
import utils.LoggerUtility;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;

public class UpdateReservation extends JFrame {
    private JLabel lblStationID, lblChargerID;
    private JTextField txtStartTime, txtEndTime;
    private JButton btnSave, btnGoBack;
    private ReservationController controller;
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
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));  // Mint green

        ImageIcon leftIcon = new ImageIcon("src/images/reserved.png");
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
                UserSession.getInstance().clearSession();
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        headerPanel.add(signOutLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.add(Box.createVerticalStrut(20));

        reservationComboBox = new JComboBox<>();
        loadReservations();
        reservationComboBox.addActionListener(this::populateFields);
        formPanel.add(createLabeledComponent("Select Reservation:", reservationComboBox));

        lblStationID = new JLabel();
        lblChargerID = new JLabel();
        formPanel.add(createLabeledComponent("Station ID:", lblStationID));
        formPanel.add(createLabeledComponent("Charger ID:", lblChargerID));

        txtStartTime = new JTextField();
        txtStartTime.setColumns(15);
        txtStartTime.setPreferredSize(new Dimension(200, 30));
        txtEndTime = new JTextField();
        txtEndTime.setColumns(15);
        txtEndTime.setPreferredSize(new Dimension(200, 30));
        formPanel.add(createLabeledComponent("Start Time (yyyy-MM-dd HH:mm):", txtStartTime));
        formPanel.add(createLabeledComponent("End Time (yyyy-MM-dd HH:mm):", txtEndTime));

        return formPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSave = new JButton("Save");
        UIUtils.customizeButton(btnSave);
        btnSave.addActionListener(this::saveReservation);

        btnGoBack = new JButton("Go Back");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> {
            dispose(); // Close current form
            ReservationManagementForm managementForm = new ReservationManagementForm();
            managementForm.setVisible(true); // Open the Reservation Management Form
        });

        footerPanel.add(btnSave);
        footerPanel.add(btnGoBack);
        return footerPanel;
    }

    private JPanel createLabeledComponent(String label, Component component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(jLabel);
        panel.add(component);
        return panel;
    }

    private void loadReservations() {
        int customerID = UserSession.getInstance().getCustomerID();
        DefaultComboBoxModel<Reservation> model = new DefaultComboBoxModel<>();
        controller.getReservationsForCustomer(customerID).forEach(model::addElement);
        reservationComboBox.setModel(model);
    }

    private void populateFields(ActionEvent e) {
        Reservation selectedReservation = (Reservation) reservationComboBox.getSelectedItem();
        if (selectedReservation != null) {
            lblStationID.setText(String.valueOf(selectedReservation.getStationID()));
            lblChargerID.setText(String.valueOf(selectedReservation.getChargerID()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            txtStartTime.setText(selectedReservation.getReservationStartTime() != null ? selectedReservation.getReservationStartTime().format(formatter) : "");
            txtEndTime.setText(selectedReservation.getReservationEndTime() != null ? selectedReservation.getReservationEndTime().format(formatter) : "");
        }
    }

    private void saveReservation(ActionEvent e) {
        try {
            Reservation selectedReservation = (Reservation) reservationComboBox.getSelectedItem();
            LocalDateTime startTime = LocalDateTime.parse(txtStartTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime endTime = LocalDateTime.parse(txtEndTime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            if (startTime.isAfter(endTime)) {
                JOptionPane.showMessageDialog(this, "End time must be after start time.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            selectedReservation.setReservationStartTime(startTime);
            selectedReservation.setReservationEndTime(endTime);

            if (controller.updateReservation(selectedReservation)) {
                JOptionPane.showMessageDialog(this, "Reservation updated successfully.");
                loadReservations();  // Refresh the ComboBox to reflect the update
            } else {
                throw new IllegalStateException("Failed to update reservation.");
            }
        } catch (DateTimeParseException ex) {
            LoggerUtility.log(Level.WARNING, "Invalid date format", ex);
            JOptionPane.showMessageDialog(this, "Please enter valid dates in the format 'yyyy-MM-dd HH:mm'.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            LoggerUtility.log(Level.SEVERE, "Unexpected error updating reservation", ex);
            JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


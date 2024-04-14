package mvc_view;

import controller.ReservationController;
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

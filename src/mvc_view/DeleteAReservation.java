
package mvc_view;

import controller.ReservationController;
import controller.UserSession;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DeleteAReservation extends JFrame {
    private JComboBox<Reservation> reservationComboBox;
    private JButton btnDelete, btnCancel;
    private ReservationController controller;

    public DeleteAReservation() {
        controller = new ReservationController();
        setTitle("Cancel Reservation");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        reservationComboBox = new JComboBox<>();
        loadReservations();
        add(reservationComboBox, BorderLayout.CENTER);

        btnDelete = new JButton("Cancel Reservation");
        btnDelete.addActionListener(this::deleteReservation);
        btnCancel = new JButton("Back");
        btnCancel.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadReservations() {
        DefaultComboBoxModel<Reservation> model = new DefaultComboBoxModel<>();
        int customerId = UserSession.getInstance().getCustomerID();
        java.util.List<Reservation> reservations = controller.getReservationsForCustomer(customerId);
        reservations.forEach(model::addElement);
        reservationComboBox.setModel(model);
    }

    private void deleteReservation(ActionEvent e) {
        Reservation selectedReservation = (Reservation) reservationComboBox.getSelectedItem();
        if (selectedReservation != null && controller.deleteReservation(selectedReservation.getReservationID())) {
            JOptionPane.showMessageDialog(this, "Reservation canceled successfully.");
            loadReservations(); // Refresh the list after deletion
        } else {
            JOptionPane.showMessageDialog(this, "Failed to cancel reservation.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new DeleteAReservation().setVisible(true));
    }
}

//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;

import controller.ReservationController;
import controller.UserSession;
import model.Reservation;
import utils.LoggerUtility;
import utils.UIUtils;
import mvc_view.exceptions.ReservationNotFoundException;
import mvc_view.exceptions.ReservationDeletionException;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;

public class DeleteAReservation extends JFrame {
    private JComboBox<Reservation> reservationComboBox;
    private JButton btnDelete, btnGoBack;
    private ReservationController controller;

    public DeleteAReservation() {
        controller = new ReservationController();
        setTitle("Cancel Reservation");
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
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green

        ImageIcon leftIcon = new ImageIcon("src/images/reserved.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Cancel Reservation", SwingConstants.CENTER);
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
        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.add(Box.createVerticalStrut(50)); // Add some space

        reservationComboBox = new JComboBox<>();
        loadReservations();
        reservationComboBox.setPreferredSize(new Dimension(200, 30));
        btnDelete = new JButton("Cancel Reservation");
        UIUtils.customizeButton(btnDelete);
        btnDelete.addActionListener(this::deleteReservation);

        formPanel.add(new JLabel("Select Reservation to Cancel:"));
        formPanel.add(reservationComboBox);
        formPanel.add(btnDelete);

        return formPanel;
    }

    private JPanel createFooterPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);

        btnGoBack = new JButton("Go Back ");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> dispose()); // Close this window
        bottomPanel.add(btnGoBack);

        return bottomPanel;
    }

    private void loadReservations() {
        DefaultComboBoxModel<Reservation> model = new DefaultComboBoxModel<>();
        int customerId = UserSession.getInstance().getCustomerID();
        java.util.List<Reservation> reservations = controller.getReservationsForCustomer(customerId);
        reservations.forEach(model::addElement);
        reservationComboBox.setModel(model);
    }



    private void deleteReservation(ActionEvent e) {
        try {
            Reservation selectedReservation = (Reservation) reservationComboBox.getSelectedItem();
            if (selectedReservation == null) {
                throw new ReservationNotFoundException("No reservation selected.");
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this reservation?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (!controller.deleteReservation(selectedReservation.getReservationID())) {
                    throw new ReservationDeletionException("Failed to cancel reservation.");
                }
                JOptionPane.showMessageDialog(this, "Reservation canceled successfully.");
                loadReservations(); // Refresh the list after deletion
            }
        } catch (ReservationNotFoundException | ReservationDeletionException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            LoggerUtility.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
            LoggerUtility.log(Level.SEVERE, "Unexpected error in deleting reservation", ex);
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new DeleteAReservation().setVisible(true));
    }
}



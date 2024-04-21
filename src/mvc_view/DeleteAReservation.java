//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;

import controller.ReservationController;
import controller.UserSession;
import model.Reservation;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
        Reservation selectedReservation = (Reservation) reservationComboBox.getSelectedItem();
        if (selectedReservation != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this reservation?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION && controller.deleteReservation(selectedReservation.getReservationID())) {
                JOptionPane.showMessageDialog(this, "Reservation canceled successfully.");
                loadReservations(); // Refresh the list after deletion
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel reservation.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new DeleteAReservation().setVisible(true));
    }
}



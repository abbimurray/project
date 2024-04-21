//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;

import controller.ReservationController;
import model.Reservation;
import controller.UserSession;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewReservationsForm extends JFrame {
    private ReservationController controller;

    public ViewReservationsForm() {
        controller = new ReservationController();
        setTitle("View Reservations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));
        JLabel titleLabel = new JLabel("View Reservations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Left icon
        ImageIcon leftIcon = new ImageIcon("src/images/reserved.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        // Sign out icon
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
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JScrollPane createContentPanel() {
        List<Reservation> reservations = controller.getReservationsForCustomer(UserSession.getInstance().getCustomerID());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        if (reservations.isEmpty()) {
            contentPanel.add(new JLabel("No reservations found."));
        } else {
            for (Reservation reservation : reservations) {
                contentPanel.add(createReservationPanel(reservation));
            }
        }
        return new JScrollPane(contentPanel);
    }

    private JPanel createReservationPanel(Reservation reservation) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);

        String startTime = reservation.getReservationStartTime() == null ? "N/A" : reservation.getReservationStartTime().toString();
        String endTime = reservation.getReservationEndTime() == null ? "N/A" : reservation.getReservationEndTime().toString();

        panel.add(new JLabel("Reservation ID: " + reservation.getReservationID()));
        panel.add(new JLabel("Start Time: " + startTime));
        panel.add(new JLabel("End Time: " + endTime));
        panel.add(new JLabel("Status: " + reservation.getStatus()));
        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        JButton backButton = new JButton(" Go Back");
        backButton.addActionListener(e -> dispose());
        UIUtils.customizeButton(backButton);
        footerPanel.add(backButton);
        return footerPanel;
    }
}


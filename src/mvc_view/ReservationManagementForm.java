//Student number:C00260073, Student name: Abigail Murray, Semester two

package mvc_view;

//imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//imports from my other packages
import controller.UserSession;
import utils.UIUtils;

public class ReservationManagementForm extends JFrame {
    private JButton btnViewReservations, btnUpdateReservations, btnDeleteReservations, btnAddReservations;

    public ReservationManagementForm() {
        setTitle("| PowerFlow | EV Charging System | Manage Reservations |");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeButtons();
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
    }

    private void initializeButtons() {
        // Initialize buttons with icons
        btnAddReservations = new JButton("Add Reservation", new ImageIcon("src/images/add.png"));
        btnViewReservations = new JButton("View My Reservations", new ImageIcon("src/images/view.png"));
        btnUpdateReservations = new JButton("Update My Reservations", new ImageIcon("src/images/updated.png"));
        btnDeleteReservations = new JButton("Delete My Reservations", new ImageIcon("src/images/delete.png"));

        // Set font and customize buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        customizeButton(btnAddReservations);
        customizeButton(btnViewReservations);
        customizeButton(btnUpdateReservations);
        customizeButton(btnDeleteReservations);

        // Add action listeners
        btnAddReservations.addActionListener(e -> new AddNewReservationForm().setVisible(true));

        btnViewReservations.addActionListener(e -> {
            ViewReservationsForm viewForm = new ViewReservationsForm();
            viewForm.setVisible(true);
        });

     btnUpdateReservations.addActionListener(e -> {
         UpdateReservation updateReservation = new UpdateReservation();
         updateReservation.setVisible(true);
             });

     btnDeleteReservations.addActionListener(e->{
         DeleteAReservation deleteAReservation = new DeleteAReservation();
         deleteAReservation.setVisible(true);
     });
    }

    private void customizeButton(JButton button) {
        button.setBackground(new Color(63, 97, 45));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    private void initializeUI() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Header panel setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));
        JLabel titleLabel = new JLabel("Manage Reservations", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JLabel iconLabel = new JLabel(new ImageIcon("src/images/reserved.png"));
        JLabel signOutLabel = new JLabel(new ImageIcon("src/images/log-out.png"));
        signOutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signOutLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                UserSession.getInstance().clearSession();
                dispose();
                new LoginForm().setVisible(true);
            }
        });

        // Add components to header panel
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(signOutLabel, BorderLayout.EAST);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        buttonsPanel.add(btnAddReservations);
        buttonsPanel.add(btnViewReservations);
        buttonsPanel.add(btnUpdateReservations);
        buttonsPanel.add(btnDeleteReservations);

        // Bottom panel with go-back-to-dashboard button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(Color.WHITE);

        JButton btnReturnToDashboard = new JButton("Return to Dashboard");
        UIUtils.customizeButton(btnReturnToDashboard);
        btnReturnToDashboard.addActionListener(e -> {
            // Action to return to the dashboard
            dispose(); // Close the current view
            CustomerDashboard dashboard = new CustomerDashboard();
            dashboard.setVisible(true);
        });

        bottomPanel.add(btnReturnToDashboard);

        // Layout the components
        add(headerPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

}//end class

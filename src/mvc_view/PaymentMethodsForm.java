//Student number:C00260073, Student name: Abigail Murray, Semester two

package mvc_view;

//imports from other packages
import controller.UserSession;
import utils.UIUtils;

//imports
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PaymentMethodsForm extends JFrame{

    private JButton btnViewPayMethods, btnUpdatePayMethods, btnDeletePayMethods, btnAddPayMethods;

    public PaymentMethodsForm() {
        setTitle("| PowerFlow | EV Charging System | Manage Payment Methods |");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initializeButtons();
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
    }//end constructor

    private void initializeButtons() {
        // Initialize buttons with icons
        btnAddPayMethods = new JButton("Add A new Payment Method", new ImageIcon(getClass().getResource("/images/add.png")));
        btnViewPayMethods = new JButton(" View My Payment Methods", new ImageIcon(getClass().getResource("/images/view.png")));
        btnUpdatePayMethods = new JButton(" Update My Payment Methods", new ImageIcon(getClass().getResource("/images/updated.png")));
        btnDeletePayMethods = new JButton(" Delete My Payment Methods", new ImageIcon(getClass().getResource("/images/delete.png")));

        // Set font for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 20);

        // Customize buttons
        customizeButton(btnAddPayMethods);
        customizeButton(btnViewPayMethods);
        customizeButton(btnUpdatePayMethods);
        customizeButton(btnDeletePayMethods);

        // add action listeners after buttons are initialized
        //Action listener for adding reservation
        btnAddPayMethods.addActionListener(e -> {
            EventQueue.invokeLater(() -> {
               new AddPayMethod().setVisible(true);//Invoke AddNewReservation form
            });
        });
        // Action listener for viewing details
        btnViewPayMethods.addActionListener(e -> {
            ViewPaymentMethods viewPaymentMethods = new ViewPaymentMethods();
            viewPaymentMethods.setVisible(true);
        });


        // Action listener for updating details
        btnUpdatePayMethods.addActionListener(e->{
            UpdatePayMethod updatePayMethod = new UpdatePayMethod();
            updatePayMethod.setVisible(true);
        });

        // Action listener for deleting reservations
        btnDeletePayMethods.addActionListener(e -> {
            EventQueue.invokeLater(() -> {
                new DeletePaymentMethod().setVisible(true); // Invoke DeleteAReservation Form
            });
        });
    }

    //styling for menu buttons
    private void customizeButton(JButton button) {
        button.setBackground(new Color(63, 97, 45)); //dark green colour
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    private void initializeUI() {
        getContentPane().setBackground(Color.WHITE); // Set the background of the content pane to white
        setLayout(new BorderLayout());

        // Header panel setup
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint color
        JLabel titleLabel = new JLabel("Manage Payment Methods", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        // Header panel setup
        ImageIcon dashboardIcon = new ImageIcon(getClass().getResource("/images/paymethod.png"));
        JLabel iconLabel = new JLabel(dashboardIcon);

        //sign out
        ImageIcon signOutIcon = new ImageIcon(getClass().getResource("/images/log-out.png"));
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


        //add to header panel
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);


        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);
        // Add some padding around the panel to move it down a bit
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        buttonsPanel.add(btnAddPayMethods);
        buttonsPanel.add(btnViewPayMethods);
        buttonsPanel.add(btnUpdatePayMethods);
        buttonsPanel.add(btnDeletePayMethods);

        //Bottom panel with go back to dashboard
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE); // Set the background color to white
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Added spacing between buttons

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

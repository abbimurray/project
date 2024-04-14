package mvc_view;

import controller.PaymentMethodController;
import controller.UserSession;
import model.PaymentMethod;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewPaymentMethods extends JFrame {
    private List<PaymentMethod> paymentMethods;

    public ViewPaymentMethods() {
        setTitle("My Payment Methods");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        PaymentMethodController controller = new PaymentMethodController();
        paymentMethods = controller.getPaymentMethodsForCustomer(UserSession.getInstance().getCustomerID());

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));  // Mint green background

        // Left icon
        ImageIcon leftIcon = new ImageIcon("src/images/paymethod.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        // Title
        JLabel titleLabel = new JLabel("View Payment Method(s)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

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

        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        if (paymentMethods.isEmpty()) {
            contentPanel.add(new JLabel("No payment methods found."));
        } else {
            for (PaymentMethod method : paymentMethods) {
                JPanel panel = createPaymentMethodPanel(method);
                contentPanel.add(panel);
            }
        }
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        UIUtils.customizeButton(backButton);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createPaymentMethodPanel(PaymentMethod method) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Card Number: " + method.getCardNumber()));
        panel.add(new JLabel("Expiry: " + method.getExpiry()));

        return panel;
    }
}

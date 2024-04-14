package mvc_view;

import controller.PaymentMethodController;
import controller.UserSession;
import model.PaymentMethod;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class DeletePaymentMethod extends JFrame {
    private JComboBox<PaymentMethod> paymentMethodComboBox;
    private JButton deleteButton;
    private PaymentMethodController paymentMethodController;

    public DeletePaymentMethod() {
        paymentMethodController = new PaymentMethodController();
        initializeUI();
    }

    private void initializeHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green

        ImageIcon leftIcon = new ImageIcon("src/images/paymethod.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Delete Payment Method", SwingConstants.CENTER);
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

        this.add(headerPanel, BorderLayout.NORTH);
    }

    private void initializeUI() {
        setTitle("Delete Payment Method");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeHeader();

        JPanel formPanel = new JPanel(new FlowLayout());
        formPanel.setBackground(Color.WHITE);

        List<PaymentMethod> paymentMethods = paymentMethodController.getPaymentMethodsForCustomer(UserSession.getInstance().getCustomerID());
        paymentMethodComboBox = new JComboBox<>(paymentMethods.toArray(new PaymentMethod[0]));
        paymentMethodComboBox.setPreferredSize(new Dimension(200, 30));

        deleteButton = new JButton("Delete");
        UIUtils.customizeButton(deleteButton);
        deleteButton.addActionListener(this::deletePaymentMethodAction);

        formPanel.add(new JLabel("Select Payment Method to Delete:"));
        formPanel.add(paymentMethodComboBox);
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.CENTER);




        // Go Back button at the bottom
        JButton btnGoBack = new JButton("Go Back");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> dispose()); // Close this window
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(btnGoBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void deletePaymentMethodAction(ActionEvent event) {
        PaymentMethod selectedMethod = (PaymentMethod) paymentMethodComboBox.getSelectedItem();
        if (selectedMethod != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this payment method?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean deleted = paymentMethodController.deletePaymentMethod(selectedMethod.getPaymentMethodID());
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Payment method deleted successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete payment method.");
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeletePaymentMethod().setVisible(true));
    }
}

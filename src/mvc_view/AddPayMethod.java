package mvc_view;

import controller.UserSession;
import controller.PaymentMethodController;
import model.PaymentMethod;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddPayMethod extends JFrame {
    private JTextField cardNumberField, expiryField, nameOnCardField;
    private JPasswordField securityCodeField;
    private JButton submitButton, backButton;

    public AddPayMethod() {
        setTitle("Add Payment Method");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
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
        JLabel titleLabel = new JLabel("Add Payment Method", SwingConstants.CENTER);
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

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); // Set background to white
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 30, 10, 30);

        // Adding form fields with customized fonts
        addFormField(formPanel, "Card Number:", cardNumberField = new JTextField(20), gbc);
        addFormField(formPanel, "Expiry Date (MM/YYYY):", expiryField = new JTextField(7), gbc);
        addFormField(formPanel, "Security Code:", securityCodeField = new JPasswordField(4), gbc);
        addFormField(formPanel, "Name on Card:", nameOnCardField = new JTextField(20), gbc);

        submitButton = new JButton("Submit");
        UIUtils.customizeButton(submitButton);
        submitButton.addActionListener(this::processAddPaymentMethod);
        formPanel.add(submitButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Back Button at the bottom
        backButton = new JButton("Back to Payment Methods");
        UIUtils.customizeButton(backButton);
        backButton.addActionListener(e -> {
            PaymentMethodsForm paymentMethodsForm = new PaymentMethodsForm();
            paymentMethodsForm.setVisible(true);
            dispose();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(label, gbc);
        panel.add(field, gbc);
    }

    private void processAddPaymentMethod(ActionEvent e) {
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setCustomerID(UserSession.getInstance().getCustomerID());
        paymentMethod.setCardNumber(cardNumberField.getText());
        paymentMethod.setExpiry(expiryField.getText());
        paymentMethod.setSecurityCode(new String(securityCodeField.getPassword()));
        paymentMethod.setNameOnCard(nameOnCardField.getText());

        PaymentMethodController controller = new PaymentMethodController();
        if (controller.addPaymentMethod(paymentMethod)) {
            JOptionPane.showMessageDialog(this, "Payment Method Added Successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to Add Payment Method.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


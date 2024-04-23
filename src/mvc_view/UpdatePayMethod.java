//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;

import controller.PaymentMethodController;
import controller.UserSession;
import model.PaymentMethod;
import utils.UIUtils;
import utils.LoggerUtility;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Level;

public class UpdatePayMethod extends JFrame {
    private JComboBox<PaymentMethod> paymentMethodComboBox;
    private JTextField cardNumberField, nameOnCardField;
    private JButton updateButton, backButton;
    private PaymentMethodController paymentMethodController;

    public UpdatePayMethod() {
        paymentMethodController = new PaymentMethodController();
        initializeUI();
    }

    private void initializeHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green

        ImageIcon leftIcon = new ImageIcon("src/images/paymethod.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Update Payment Method", SwingConstants.CENTER);
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
        setTitle("Update Payment Method");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeHeader();

        // Font settings
        Font arialBold16 = new Font("Arial", Font.BOLD, 16);

        // Main form panel with a BoxLayout for vertical stacking
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        // Add some vertical space before the first element
        formPanel.add(Box.createVerticalStrut(50));

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboPanel.setBackground(Color.WHITE);
        JLabel comboLabel = new JLabel("Select Payment Method:");
        comboLabel.setFont(arialBold16);
        comboPanel.add(comboLabel);

        List<PaymentMethod> paymentMethods = paymentMethodController.getPaymentMethodsForCustomer(UserSession.getInstance().getCustomerID());
        paymentMethodComboBox = new JComboBox<>(paymentMethods.toArray(new PaymentMethod[0]));
        paymentMethodComboBox.setFont(arialBold16);
        paymentMethodComboBox.setPreferredSize(new Dimension(200, 30));
        paymentMethodComboBox.addActionListener(this::populateFields);
        comboPanel.add(paymentMethodComboBox);
        formPanel.add(comboPanel);

        JPanel cardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cardPanel.setBackground(Color.WHITE);
        JLabel cardLabel = new JLabel("Card Number:");
        cardLabel.setFont(arialBold16);
        cardPanel.add(cardLabel);

        cardNumberField = new JTextField(20);
        cardNumberField.setFont(arialBold16);
        cardPanel.add(cardNumberField);
        formPanel.add(cardPanel);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        namePanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel("Name on Card:");
        nameLabel.setFont(arialBold16);
        namePanel.add(nameLabel);

        nameOnCardField = new JTextField(20);
        nameOnCardField.setFont(arialBold16);
        namePanel.add(nameOnCardField);
        formPanel.add(namePanel);

        updateButton = new JButton("Update");
        updateButton.setFont(arialBold16);
        UIUtils.customizeButton(updateButton);
        updateButton.addActionListener(this::updatePaymentMethodAction);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(updateButton);
        formPanel.add(buttonPanel);

        add(formPanel, BorderLayout.CENTER);

        // Bottom Panel with Back Button
        backButton = new JButton("Back to Payment Method Dashboard");
        backButton.setFont(arialBold16);
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


    private void populateFields(ActionEvent e) {
        PaymentMethod selectedMethod = (PaymentMethod) paymentMethodComboBox.getSelectedItem();
        if (selectedMethod != null) {
            cardNumberField.setText(selectedMethod.getCardNumber());
            nameOnCardField.setText(selectedMethod.getNameOnCard());
        }
    }


    private void updatePaymentMethodAction(ActionEvent event) {
        PaymentMethod selectedMethod = (PaymentMethod) paymentMethodComboBox.getSelectedItem();
        if (selectedMethod == null) {
            JOptionPane.showMessageDialog(this, "No payment method selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cardNumber = cardNumberField.getText();
        String nameOnCard = nameOnCardField.getText();

        try {
            if (!cardNumber.equals(selectedMethod.getCardNumber()) || !nameOnCard.equals(selectedMethod.getNameOnCard())) {
                selectedMethod.setCardNumber(cardNumber);
                selectedMethod.setNameOnCard(nameOnCard);

                String validationResult = paymentMethodController.validatePaymentMethod(selectedMethod);
                if (!validationResult.isEmpty()) {
                    JOptionPane.showMessageDialog(this, validationResult, "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean updated = paymentMethodController.updatePaymentMethod(selectedMethod);
                if (updated) {
                    JOptionPane.showMessageDialog(this, "Payment method updated successfully.");
                } else {
                    throw new Exception("Failed to update payment method."); // Trigger an error log
                }
            } else {
                JOptionPane.showMessageDialog(this, "No changes detected. Please modify the fields before updating.");
            }
        } catch (Exception e) {
            LoggerUtility.log(Level.SEVERE, "Error updating payment method", e);
            JOptionPane.showMessageDialog(this, "An error occurred while updating the payment method: " + e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }





}//end class

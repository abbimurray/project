//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;

import controller.PaymentMethodController;
import controller.UserSession;
import model.PaymentMethod;
import utils.UIUtils;
import utils.LoggerUtility;

import mvc_view.exceptions.PaymentMethodDeletionException;


import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

        // Add some vertical space before the first element
        formPanel.add(Box.createVerticalStrut(300));

        List<PaymentMethod> paymentMethods = paymentMethodController.getPaymentMethodsForCustomer(UserSession.getInstance().getCustomerID());
        paymentMethodComboBox = new JComboBox<>(paymentMethods.toArray(new PaymentMethod[0]));
        paymentMethodComboBox.setPreferredSize(new Dimension(200, 30));

        deleteButton = new JButton("Delete");
        UIUtils.customizeButton(deleteButton);
        deleteButton.addActionListener(this::deletePaymentMethodAction);

        JLabel deleteLabel = new JLabel("Select Payment Method to Delete:");
        deleteLabel.setFont(new Font("Arial", Font.BOLD, 16));  // Setting the font
        formPanel.add(deleteLabel);
        formPanel.add(paymentMethodComboBox);
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.CENTER);


        // Go Back button at the bottom
        JButton btnGoBack = new JButton("Back to Payment Method Dashboard");
        UIUtils.customizeButton(btnGoBack);
        btnGoBack.addActionListener(e -> dispose()); // Close this window
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(btnGoBack);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void deletePaymentMethodAction(ActionEvent event) {
        PaymentMethod selectedMethod = (PaymentMethod) paymentMethodComboBox.getSelectedItem();
        if (selectedMethod == null) {
            LoggerUtility.log(Level.WARNING, "No payment method selected for deletion.", new IllegalStateException("No selection made"));
            JOptionPane.showMessageDialog(this, "Please select a payment method to delete.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this payment method?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean deleted = paymentMethodController.deletePaymentMethod(selectedMethod.getPaymentMethodID());
                if (!deleted) {
                    throw new PaymentMethodDeletionException("Failed to delete payment method due to a server error.");
                }
                JOptionPane.showMessageDialog(this, "Payment method deleted successfully.");
                dispose();
            } catch (PaymentMethodDeletionException ex) {
                LoggerUtility.log(Level.SEVERE, ex.getMessage(), ex);
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Deletion Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }






}//end class
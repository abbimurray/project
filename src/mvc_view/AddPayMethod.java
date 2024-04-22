//Student number:C00260073, Student name: Abigail Murray, Semester two
package mvc_view;

//imports from other packages
import controller.UserSession;
import controller.PaymentMethodController;
import model.PaymentMethod;
import utils.UIUtils;
//imports for exceptions
import mvc_view.exceptions.InvalidCardNumberException;
import mvc_view.exceptions.CardExpiredException;
import mvc_view.exceptions.InvalidSecurityCodeException;
import utils.LoggerUtility;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

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
        backButton = new JButton("Back to Payment Method Dashboard");
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
        try {
            String cardNumber = cardNumberField.getText();
            if (!cardNumber.matches("\\d{16}")) {
                throw new InvalidCardNumberException("Invalid card number. Please enter a valid 16 digit card number.");
            }

            String expiryDate = expiryField.getText();
            if (!expiryDate.matches("^(0[1-9]|1[0-2])/\\d{2}$") || !isValidExpiryDate(expiryDate)) {
                throw new CardExpiredException("Invalid or expired card. Please enter a valid and non-expired card MM/YY.");
            }

            String securityCode = new String(securityCodeField.getPassword());
            if (!securityCode.matches("\\d{3,4}")) {
                throw new InvalidSecurityCodeException("Invalid security code. Please enter a valid 3 or 4 digit CVV.");
            }

            PaymentMethod paymentMethod = new PaymentMethod();
            paymentMethod.setCustomerID(UserSession.getInstance().getCustomerID());
            paymentMethod.setCardNumber(cardNumber);
            paymentMethod.setExpiry(expiryDate);
            paymentMethod.setSecurityCode(securityCode);
            paymentMethod.setNameOnCard(nameOnCardField.getText());

            PaymentMethodController controller = new PaymentMethodController();
            String result = controller.addPaymentMethod(paymentMethod);

            if ("success".equals(result)) {
                JOptionPane.showMessageDialog(this, "Payment Method Added Successfully!");
                dispose();
            } else {
                throw new Exception("Database error occurred while adding payment method.");
            }
        } catch (InvalidCardNumberException | CardExpiredException | InvalidSecurityCodeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            LoggerUtility.log(Level.WARNING, ex.getMessage(), ex);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
            LoggerUtility.log(Level.SEVERE, "Unexpected error in adding payment method", ex);
        }
    }

    private boolean isValidExpiryDate(String expiryDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
            sdf.setLenient(false);
            Date expiry = sdf.parse(expiryDate);
            Date now = new Date();
            return expiry.after(now);
        } catch (ParseException e) {
            return false;
        }
    }


}//end class


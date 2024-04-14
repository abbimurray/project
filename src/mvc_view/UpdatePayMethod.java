package mvc_view;

import controller.PaymentMethodController;
import model.PaymentMethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePayMethod extends JFrame {
    private JTextField cardNumberField, expiryField, nameOnCardField, paymentMethodIdField;
    private JButton updateButton;

    public UpdatePayMethod() {
        setTitle("Update Payment Method");
        setSize(300, 300);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Payment Method ID:"));
        paymentMethodIdField = new JTextField(20);
        add(paymentMethodIdField);

        add(new JLabel("Card Number:"));
        cardNumberField = new JTextField(20);
        add(cardNumberField);

        add(new JLabel("Expiry Date:"));
        expiryField = new JTextField(5);
        add(expiryField);

        add(new JLabel("Name on Card:"));
        nameOnCardField = new JTextField(20);
        add(nameOnCardField);

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setPaymentMethodID(Integer.parseInt(paymentMethodIdField.getText()));
                paymentMethod.setCardNumber(cardNumberField.getText());
                paymentMethod.setExpiry(expiryField.getText());
                paymentMethod.setNameOnCard(nameOnCardField.getText());

                PaymentMethodController controller = new PaymentMethodController();
                if (controller.updatePaymentMethod(paymentMethod)) {
                    JOptionPane.showMessageDialog(null, "Payment Method Updated Successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Update Payment Method.");
                }
            }
        });
        add(updateButton);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
    }
}

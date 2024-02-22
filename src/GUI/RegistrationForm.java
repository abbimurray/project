package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationForm extends JDialog {
    private JLabel add_icon;
    private JLabel title;
    private JLabel userLabel;
    private JTextField tfUsername;
    private JLabel emailLabel;
    private JTextField tfEmail;
    private JLabel pwLabel;
    private JPasswordField pwf1;
    private JLabel pfLabel2;
    private JPasswordField pwf2;
    private JButton btnSubmit;
    private JButton btnCancel;
    private JPanel registerPanel;

    public RegistrationForm (JFrame parent)
    {
        super(parent);/*call parent instructure which requires jframe*/
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);

        /*button for creating new user*/
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        /*button to cancel new user*/
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);

    }

    private void registerUser() {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registration Form");
        RegistrationForm myForm = new RegistrationForm(frame);
        System.out.println("Registration Form");
    }
}

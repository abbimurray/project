package GUI;

import javax.swing.*;
import java.awt.*;

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
        setVisible(true);

    }

    public static void Main (String[]args){
        RegistrationForm myForm = new RegistrationForm();
    }
}

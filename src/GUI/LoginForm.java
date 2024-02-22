package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog {
    private JLabel iconLogin;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnOk;
    private JButton btnCancel;
    private JPanel loginPanel;

    public LoginForm(JFrame parent){

        super(parent);
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = tfUsername.getText();
                String password = String.valueOf(pfPassword.getPassword());


               user= getAuthenticatedUser(username, password);
               if (user !=null)
               {
                   dispose();
               }
               else
               {
                    JOptionPane.showMessageDialog(LoginForm.this, "Email or Password invalid", "Try Again", JOptionPane.ERROR_MESSAGE);
               }
            }
        });
    }

    public User user;

    //method to get authenticated user
    private User getAuthenticatedUser(String username, String password){
        User user=null;

        final String DB_URL ="jdbc:mysql://localhost:3306/EVCharging";
        final String USERNAME="root";
        final String PASSWORD="pknv!47A";

        PreparedStatement pstat = null;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);


            pstat = conn.prepareStatement("SELECT * FROM user_accounts WHERE username=? AND password=?" );
            pstat.setString(1, username);
            pstat.setString(2, password);

            ResultSet resultSet = pstat.executeQuery();

            if (resultSet.next())
            {
                user = new User();
                user.userName = resultSet.getString("username");
                user.password = resultSet.getString("password");

            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Login Form");
        LoginForm loginForm= new LoginForm(frame);
        System.out.println("Login Form");
    }
}
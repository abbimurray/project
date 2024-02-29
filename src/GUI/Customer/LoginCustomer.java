package GUI.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginCustomer extends JDialog {
    private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JButton btnLoginCust;
    private JLabel PasswordLabel;
    private JLabel emailLabel;
    private JPanel customerLoginPanel;
    private JLabel iconImage;
    private JPanel iconBackPanel;
    private JLabel customerLoginLabel;
    private JPanel customerLoginBlueBackground;
    private JButton btnCancel;

    public LoginCustomer(JFrame parent) {

        super(parent);
        setContentPane(customerLoginPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnLoginCust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = tfEmail.getText();
                String password = String.valueOf(pfPassword.getPassword());


                customer = getAuthenticatedCustomer (email, password);
                if (customer !=null)
                {
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(LoginCustomer.this, "Email or Password invalid", "Try Again", JOptionPane.ERROR_MESSAGE);
                }
            }



        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public Customer customer;

    //method to get authenticated user
    private Customer getAuthenticatedCustomer(String email, String password){
        Customer customer=null;

        final String DB_URL ="jdbc:mysql://localhost:3306/EVCharging";
        final String USERNAME="root";
        final String PASSWORD="pknv!47A";

        PreparedStatement pstat = null;
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);


            pstat = connection.prepareStatement("SELECT * FROM customer_accounts WHERE email=? AND password=?" );
            pstat.setString(1, email);
            pstat.setString(2, password);

            ResultSet resultSet = pstat.executeQuery();

            if (resultSet.next())
            {
                customer = new Customer();

                customer.setEmail(email);
                customer.setPassword(password);


            }

            pstat.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return customer;
    }

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Login Customer Form");
        LoginCustomer loginCustomer= new LoginCustomer(frame);
        System.out.println("Login Form");
        Customer customer= loginCustomer.customer;
        if(customer != null)
        {
            System.out.println("Successful Authentication of: " + customer.getEmail());
            System.out.println("Email: " + customer.getEmail());
        }
        else {
            System.out.println("Authentication cancelled");
        }
        // Exit the program after the dialog is closed
        System.exit(0);
    }
}

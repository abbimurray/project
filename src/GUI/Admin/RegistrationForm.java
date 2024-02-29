package GUI.Admin;
/*registration form to register new admin-> management*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
    private JPanel registerAdminPanel;

    public RegistrationForm (JFrame parent)
    {
        super(parent);/*call parent instructure which requires jframe*/
        setTitle("Create a new user account(admin)");
        setContentPane(registerAdminPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        /*button for creating new admin*/
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        /*button to cancel new admin*/
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);

    }

    //register new admin user
    private void registerUser() {
        String userName = tfUsername.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pwf1.getPassword());
        String confirmPassword = String.valueOf(pwf2.getPassword());

        /*if any fields aren't entered*/
        if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please enter all fields", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        /*if passwords don't match*/
        if(!password.equals(confirmPassword))
        {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

       userAdmin = addUserAccounts(userName, email, password);
        if (userAdmin != null)
        {
            dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Failed to register new user", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    public UserAdmin userAdmin;
    private UserAdmin addUserAccounts(String userName, String email, String password)
    {
        UserAdmin userAdmin= null;/*if method fails it will return null*/
        final String DB_URL ="jdbc:mysql://localhost:3306/EVCharging";
        final String USERNAME="root";
        final String PASSWORD="pknv!47A";

        Connection connection= null;
        PreparedStatement pstat = null;
        int i=0;

        try {
            //establish connection to database
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            //create prepared statement for inserting into table
            pstat = connection.prepareStatement("INSERT INTO user_accounts (username, password, email) VALUES (?,?,?)");
            pstat.setString(1, userName);
            pstat.setString(2, password);
            pstat.setString(3, email);


            //insert row into table
            int addedRows = pstat.executeUpdate();
            if (addedRows>0)
            {
                userAdmin = new UserAdmin();
                userAdmin.setUsername(userName);
                userAdmin.setEmail(email);
                userAdmin.setPassword(password);

            }

            pstat.close();
            connection.close();

            //insert data into database
            /*i = pstat.executeUpdate();//if successful will return value of 1
            System.out.println(i + " :record successfully added to the database (in the user_accounts table).");
*/

        }catch(Exception e){
            e.printStackTrace();
        }

        return userAdmin;

    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Registration Form");
        RegistrationForm myForm = new RegistrationForm(frame);
        System.out.println("Registration Form");
        UserAdmin userAdmin = myForm.userAdmin;
        if(userAdmin != null)
        {
            System.out.println("Successful Registration of:  " + userAdmin.getUsername());

        }
        else
        {
            System.out.println("Registration cancelled");
        }
        // Exit the program after the dialog is closed
        System.exit(0);
    }
}

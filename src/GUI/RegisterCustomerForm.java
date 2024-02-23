package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegisterCustomerForm extends JDialog{
    private JPanel registerCustomerPanel;
    private JLabel titlecust;
    private JPasswordField pfConfirm;
    private JPasswordField pfPassword;
    private JTextField tfPhone;
    private JTextField tfEmail;
    private JTextField tfLastName;
    private JTextField tfFirstName;
    private JButton btnSubmit;
    private JButton btnCancel;


    //constructor
    public RegisterCustomerForm(JFrame parent){
        super(parent);/*call parent instructure which requires jframe*/
        setTitle("Create a new customer account");
        setContentPane(registerCustomerPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerCustomer();
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

    private void registerCustomer() {
        String firstName = tfFirstName.getText();
        String lastName = tfLastName.getText();
        String email = tfEmail.getText();
        String phone = tfPhone.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword =String.valueOf(pfConfirm.getPassword());

        /*if any fields aren't entered*/
        if (firstName.isEmpty() || lastName.isEmpty()|| email.isEmpty() ||phone.isEmpty()||  password.isEmpty() || confirmPassword.isEmpty())
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

        customer= addCustomerAccounts(firstName,lastName, email,phone, password);
        if (customer != null)
        {
            dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Failed to register new customer", "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }


    }
    public Customer customer;
    private Customer addCustomerAccounts(String firstName,String lastName, String email,String phone, String password) {
        Customer customer = null;/*if method fails it will return null*/

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
            pstat = connection.prepareStatement("INSERT INTO customer_accounts (firstName, lastName, email, phone, password) VALUES (?,?,?,?,?)");
            pstat.setString(1, firstName);
            pstat.setString(2, lastName);
            pstat.setString(3, email);
            pstat.setString(4, phone);
            pstat.setString(5, password);

            //insert row into table
            int addedRows = pstat.executeUpdate();
            if (addedRows>0)
            {
                customer = new Customer();
                customer.setFirstName(firstName);
                customer.setLastName(lastName);
                customer.setEmail(email);
                customer.setPhone(phone);
                customer.setPassword(password);


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


        return customer;

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registration Form for cUSTOMER");
        RegisterCustomerForm myCustomerForm = new RegisterCustomerForm(frame);
        System.out.println("Registration Form for Customer");
        Customer customer = myCustomerForm.customer;
        if(customer != null)
        {
            System.out.println("Successful Registration of:  " + customer.getFirstName() +  customer.getLastName());

        }
        else
        {
            System.out.println("Registration cancelled");
        }
        // Exit the program after the dialog is closed
        System.exit(0);
    }

}//end class

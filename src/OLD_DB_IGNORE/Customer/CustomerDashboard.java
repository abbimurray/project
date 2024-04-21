package OLD_DB_IGNORE.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDashboard {
    private JButton btnMyAccount;
    private JButton btnMyTransactions;
    private JButton btnSearch;
    private JButton btnReserveCharger;
    private JButton btnLogout;

    public CustomerDashboard() {

        //when clicked on My Account display options to view, update (or delete) customer account
        btnMyAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}

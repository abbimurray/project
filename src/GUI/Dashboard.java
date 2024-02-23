package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class Dashboard extends JFrame {
    private CardLayout cardLayout;

    public Dashboard() {
        super("Customer Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Initialize the CardLayout
        cardLayout = new CardLayout();
        JPanel centerPanel = new JPanel(cardLayout);
        add(centerPanel, BorderLayout.CENTER);

        // Add components to the dashboard here
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(4, 1));
        add(leftPanel, BorderLayout.WEST);

        JButton accountButton = new JButton("View Account");
        leftPanel.add(accountButton);

        JButton transactionsButton = new JButton("View Transactions");
        leftPanel.add(transactionsButton);

        JButton searchButton = new JButton("Search Charging Stations");
        leftPanel.add(searchButton);

        JButton reserveButton = new JButton("Reserve Station");
        leftPanel.add(reserveButton);

        // Add the account panel
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new BorderLayout());
        JTable accountTable = new JTable(new DefaultTableModel(new String[]{"Column 1", "Column 2"}, 0));
        JScrollPane scrollPane = new JScrollPane(accountTable);
        accountPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(accountPanel, "account");

        // Add the transactions panel
        JPanel transactionsPanel = new JPanel();
        transactionsPanel.setLayout(new BorderLayout());
        JTable transactionsTable = new JTable(new DefaultTableModel(new String[]{"Column 1", "Column 2"}, 0));
        scrollPane = new JScrollPane(transactionsTable);
        transactionsPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(transactionsPanel, "transactions");

        // Add action listeners for the buttons here
        accountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display the user's account information
                cardLayout.show(centerPanel, "account");
            }
        });

        transactionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Display the user's transaction history
                cardLayout.show(centerPanel, "transactions");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Dashboard dashboard = new Dashboard();
                dashboard.setVisible(true);
            }
        });
    }
}

//Student number:C00260073, Student name: Abigail Murray, Semester two

package mvc_view;

//imports from other packages
import model.ChargingTransaction;
import model.ChargingStationModel;
import controller.UserSession;
import utils.UIUtils;
import utils.LoggerUtility;
import mvc_view.exceptions.TransactionsNotFoundException;

//imports
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;


public class ViewChargingTransactions extends JFrame {
    private List<ChargingTransaction> transactions;


    public ViewChargingTransactions() {
        setTitle("| PowerFlow | EV Charging System | Charging History |");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            String customerId = String.valueOf(UserSession.getInstance().getCustomerID());
            if (customerId == null || customerId.isEmpty()) {
                throw new IllegalStateException("Session is invalid or expired.");
            }
            ChargingStationModel model = new ChargingStationModel();
            transactions = model.getTransactionsForCustomer(Integer.parseInt(customerId));
            if (transactions.isEmpty()) {
                throw new TransactionsNotFoundException("No transactions found for customer ID: " + customerId);
            }
            initializeUI();
        } catch (IllegalStateException | TransactionsNotFoundException e) {
            LoggerUtility.log(Level.SEVERE, "Failed to retrieve transactions or session invalid", e);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        }
    }
    private void initializeUI() {
        setLayout(new BorderLayout());
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Mint green background



        ImageIcon leftIcon = new ImageIcon(getClass().getResource("/images/history.png"));
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Charging History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        ImageIcon signOutIcon = new ImageIcon(getClass().getResource("/images/log-out.png"));
        JLabel signOutLabel = new JLabel(signOutIcon);
        signOutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signOutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UserSession.getInstance().clearSession();
                dispose();
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });
        headerPanel.add(signOutLabel, BorderLayout.EAST);

        return headerPanel;
    }

    private JScrollPane createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        if (transactions.isEmpty()) {
            contentPanel.add(new JLabel("No transactions found."));
        } else {
            for (ChargingTransaction transaction : transactions) {
                JPanel panel = createTransactionPanel(transaction);
                contentPanel.add(panel);
            }
        }
        return new JScrollPane(contentPanel);
    }

    private JPanel createTransactionPanel(ChargingTransaction transaction) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        panel.add(new JLabel("Transaction ID: " + transaction.getTransactionId()));
        panel.add(new JLabel("Date: " + transaction.getStartTime().toString()));
        panel.add(new JLabel("Total Cost: €" + transaction.getTotalCost()));
        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        JButton backButton = new JButton("Return to Dashboard");
        UIUtils.customizeButton(backButton);
        backButton.addActionListener(e -> {
            this.dispose(); // Close the current form
            CustomerDashboard dashboard = new CustomerDashboard(); // Create an instance of the CustomerDashboard
            dashboard.setVisible(true); // Make the dashboard visible
        });
        footerPanel.add(backButton);
        return footerPanel;
    }

}//end class

package mvc_view;

import model.ChargingTransaction;
import model.ChargingStationModel;
import controller.UserSession;
import utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ViewChargingTransactions extends JFrame {
    private List<ChargingTransaction> transactions;

    public ViewChargingTransactions() {
        setTitle("Charging History");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ChargingStationModel model = new ChargingStationModel();
        transactions = model.getTransactionsForCustomer(UserSession.getInstance().getCustomerID());

        initializeUI();
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

        ImageIcon leftIcon = new ImageIcon("src/images/history.png");
        JLabel leftLabel = new JLabel(leftIcon);
        headerPanel.add(leftLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Charging History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        ImageIcon signOutIcon = new ImageIcon("src/images/log-out.png");
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
        JButton backButton = new JButton("Back");
        UIUtils.customizeButton(backButton);
        backButton.addActionListener(e -> {
            this.dispose(); // Close the current form
            CustomerDashboard dashboard = new CustomerDashboard(); // Create an instance of the CustomerDashboard
            dashboard.setVisible(true); // Make the dashboard visible
        });
        footerPanel.add(backButton);
        return footerPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewChargingTransactions().setVisible(true));
    }
}


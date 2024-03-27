
package mvc_view;
import javax.swing.*;
import java.awt.*;

public class CustomerDashboard extends JFrame {

    public CustomerDashboard() {
        setTitle("Customer Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204)); // Example color

        // Header label
        JLabel titleLabel = new JLabel("Customer Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Dashboard symbol/icon on the header
        ImageIcon dashboardIcon = new ImageIcon("src/images/dashboard.png");
        JLabel iconLabel = new JLabel(dashboardIcon);

        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 cols, gaps

        // Adding sections with icons
        addSection(mainPanel, "My Profile", "src/images/myprofileicon.png");
        addSection(mainPanel, "Search for Charging Stations", "src/images/search.png");
        addSection(mainPanel, "Reserve a Charger", "src/images/reserved.png");
        addSection(mainPanel, "Charging History", "src/images/history.png");
        addSection(mainPanel, "Payments", "src/images/transactionsicon.png");
        addSection(mainPanel, "Customer Support", "src/images/customer-support.png");

        add(headerPanel, BorderLayout.NORTH);
       add(mainPanel, BorderLayout.CENTER);
    }
    private void addSection(JPanel panel, String labelText, String iconPath) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Set a border around the section panel
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        // Icon
        ImageIcon icon = new ImageIcon(iconPath);
        JLabel labelIcon = new JLabel(icon);

        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        sectionPanel.add(labelIcon);
        sectionPanel.add(label);

        panel.add(sectionPanel);
    }
/*
    private void addSection(JPanel panel, String labelText, ImageIcon icon) {
        // Section panel with FlowLayout for horizontal arrangement
        JPanel sectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // 10px padding
        sectionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // Optional: Add a border

        JLabel labelIcon = new JLabel(icon);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 18));

        // Add the components to the section panel
        sectionPanel.add(labelIcon);
        sectionPanel.add(label);

        // Add the section panel to the main panel
        panel.add(sectionPanel);
    }
*/





    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomerDashboard().setVisible(true));
    }
}

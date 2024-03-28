package mvc_view;
import model.Customer;
import javax.swing.*;
import java.awt.*;

public class ViewMyDetails extends JFrame {
    private Customer customer; // Assuming you have a Customer class that holds user details

    public ViewMyDetails(Customer customer) {
        this.customer = customer;
        initializeUI();
        setTitle("Viewing My Details");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeUI() {
        setLayout(new BorderLayout(10, 10)); // Add some spacing

        // Icon
        ImageIcon userIcon = new ImageIcon("src/images/myaccount.png"); // Ensure this path is correct
        JLabel iconLabel = new JLabel(userIcon);

        // Title Label
        JLabel titleLabel = new JLabel("Viewing My Details");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Header Panel with BorderLayout for better control
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));
        headerPanel.add(iconLabel, BorderLayout.WEST);

        // Ensuring the title is centered even with the icon on the left
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setBackground(new Color(204, 255, 204)); // Match the header panel's background
        titlePanel.add(titleLabel);
        headerPanel.add(titlePanel, BorderLayout.CENTER);

        // Form-like Details Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE); // Set to white for the form look
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the form

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Margins between components

        // Styling for labels and details
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font detailFont = new Font("Arial", Font.PLAIN, 18);

        // Adding details - Adjust as per your requirement
        formPanel.add(new JLabel("First Name: "), gbc);
        formPanel.add(new JLabel(customer.getFirstName()) {{
            setFont(detailFont);
        }}, gbc);
        formPanel.add(new JLabel("Last Name: "), gbc);
        formPanel.add(new JLabel(customer.getLastName()) {{
            setFont(detailFont);
        }}, gbc);
        formPanel.add(new JLabel("Email: "), gbc);
        formPanel.add(new JLabel(customer.getEmail()) {{
            setFont(detailFont);
        }}, gbc);
        formPanel.add(new JLabel("Phone: "), gbc);
        formPanel.add(new JLabel(customer.getPhone()) {{
            setFont(detailFont);
        }}, gbc);

        // Wrap formPanel in another panel to center it on screen
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(formPanel);

        // Go Back Button
        JButton goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(e -> dispose()); // Close this window
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(goBackButton);

        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }


}
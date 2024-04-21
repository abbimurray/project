//Student number:C00260073, Student name: Abigail Murray, Semester two
//customised button
package utils;
import javax.swing.*;
import java.awt.*;

public class UIUtils {
    public static void customizeButton(JButton button) {
        button.setOpaque(true);
        button.setBackground(new Color(204, 255, 204)); // Mint green
        button.setForeground(new Color(36, 35, 37)); // Custom text color
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }
}

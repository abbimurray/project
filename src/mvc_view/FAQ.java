package mvc_view;

import javax.swing.*;
import java.awt.*;

public class FAQ extends JPanel {
    private JList<String> faqList;
    private JTextArea answerArea;
    private FAQ faq;

    public FAQ() {
        setLayout(new BorderLayout());
        String[] questions = {
                "How do I find available charging stations?", //q1
                "What types of charging ports are available?",//q2
                "How do I start a charging session?",//q3
                "Who can I contact for more help?"//q4
        };

        faqList = new JList<>(questions);
        faqList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        faqList.addListSelectionListener(e -> displayAnswer(faqList.getSelectedIndex()));

        JScrollPane listScroller = new JScrollPane(faqList);
        listScroller.setPreferredSize(new Dimension(200, 200));

        answerArea = new JTextArea();
        answerArea.setEditable(false);
        answerArea.setWrapStyleWord(true);
        answerArea.setLineWrap(true);
        JScrollPane textScroller = new JScrollPane(answerArea);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroller, textScroller);
        splitPane.setDividerLocation(200);
        splitPane.setPreferredSize(new Dimension(500, 300));

        add(splitPane, BorderLayout.CENTER);
    }

    private void displayAnswer(int questionIndex) {
        String answer = "";
        switch (questionIndex) {
            case 0:
                answer = "You can find available charging stations by using our app's \"Search for Station\" feature";
                break;//a1
            case 1:
                answer = "Our stations support various types of charging ports including CCS, CHAdeMO, and Type 2";
                break;//a2
            case 2:
                answer = "To start a charging session, connect your vehicle to the charger, open our app and press start session";
                break;//a3
            // Add all other cases here
            case 3:
                answer = "Our customer service team is available 24/7. Our contact details are below";
                break;//q4
        }
        answerArea.setText(answer);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FAQs");
        FAQ faqPanel = new FAQ();
        frame.setContentPane(faqPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

//Student number:C00260073, Student name: Abigail Murray, Semester two
//this class is used with the customer service form

package mvc_view;

//imports
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class FAQ extends JPanel {
    private JList<String> faqList;
    private JTextArea answerArea;
    private FAQ faq;

    public FAQ() {
        setLayout(new BorderLayout());
        String[] questions = {// Initialize a String array with FAQ questions
                "How do I find available charging stations?", //q1
                "What types of charging ports are available?",//q2
                "How do I start a charging session?",//q3
                "Who can I contact for more help?"//q4
        };

        faqList = new JList<>(questions);// Initialize the JList with the array of questions
        faqList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// SINGLE_SELECTION, allows only 1 item to be selected at a time
        faqList.addListSelectionListener(e -> displayAnswer(faqList.getSelectedIndex()));// Adds a listener that calls displayAnswer when a question is selected


        JScrollPane listScroller = new JScrollPane(faqList);// Wrap the JList in a JScrollPane ( scrollable if the list is longer than the display area).
        listScroller.setPreferredSize(new Dimension(200, 200));

        //initialize and configure answerArea, set to be non-editable and to wrap lines and words
        answerArea = new JTextArea();
        answerArea.setEditable(false);
        answerArea.setWrapStyleWord(true);
        answerArea.setLineWrap(true);
        JScrollPane textScroller = new JScrollPane(answerArea);//Wraps answerArea in another JScrollPane.

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScroller, textScroller);//Creates JSplitPane that divides the panel horizontally between listScroller and textScroller
        splitPane.setDividerLocation(200);
        splitPane.setPreferredSize(new Dimension(500, 300));

        add(splitPane, BorderLayout.CENTER);
    }


    // method displayAnswer --  updates answerArea based on the question selected
    //Each case corresponds to an index in the questions array.
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

            case 3:
                answer = "Our customer service team is available 24/7. Our contact details are below";
                break;//a4
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

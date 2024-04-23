//Student number:C00260073, Student name: Abigail Murray, Semester two
//this class is used with the customer service form

package mvc_view;

//imports
import mvc_view.exceptions.FAQDataException;
import mvc_view.exceptions.FAQInitializationException;
import utils.LoggerUtility;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Level;

public class FAQ extends JPanel {
    private JList<String> faqList;
    private JTextArea answerArea;
    private FAQ faq;




    public FAQ() throws FAQInitializationException {
        try {
            setLayout(new BorderLayout());
            initializeComponents();
        } catch (Exception e) {
            throw new FAQInitializationException("Failed to initialize FAQ components.", e);
        }
    }
        private void initializeComponents() {
            String[] questions = {
                    "How do I find available charging stations?",
                    "What types of charging ports are available?",
                    "How do I start a charging session?",
                    "Who can I contact for more help?"
            };

            faqList = new JList<>(questions);// Initialize the JList with the array of questions
            faqList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// SINGLE_SELECTION, allows only 1 item to be selected at a time
            faqList.addListSelectionListener(e -> { // Adds a listener that calls displayAnswer when a question is selected
                try {
                    displayAnswer(faqList.getSelectedIndex());
                } catch (Exception ex) {
                    LoggerUtility.log(Level.SEVERE, "Error displaying FAQ answer.", ex);
                }
            });


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
//    private void displayAnswer(int questionIndex) {
//        String answer = "";
//        switch (questionIndex) {
//            case 0:
//                answer = "You can find available charging stations by using our app's \"Search for Station\" feature";
//                break;//a1
//            case 1:
//                answer = "Our stations support various types of charging ports including CCS, CHAdeMO, and Type 2";
//                break;//a2
//            case 2:
//                answer = "To start a charging session, connect your vehicle to the charger, open our app and press start session";
//                break;//a3
//
//            case 3:
//                answer = "Our customer service team is available 24/7. Our contact details are below";
//                break;//a4
//        }
//        answerArea.setText(answer);
//    }



    private void displayAnswer(int questionIndex) throws FAQDataException {
        String answer = "";
        switch (questionIndex) {
            case 0:
                answer = "You can find available charging stations by using our app's \"Search for Station\" feature";
                break;
            case 1:
                answer = "Our stations support various types of charging ports including CCS, CHAdeMO, and Type 2";
                break;
            case 2:
                answer = "To start a charging session, connect your vehicle to the charger, open our app and press start session";
                break;
            case 3:
                answer = "Our customer service team is available 24/7. Our contact details are below";
                break;
            default:
                throw new FAQDataException("Invalid FAQ index accessed: " + questionIndex);
        }
        answerArea.setText(answer);
    }


    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("FAQs");
            FAQ faqPanel = new FAQ();
            frame.setContentPane(faqPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (FAQInitializationException e) {
            LoggerUtility.log(Level.SEVERE, "Failed to launch FAQ application.", e);
        }
    }
}

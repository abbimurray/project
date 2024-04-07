package mvc_view;


import controller.ReservationController;
import controller.UserSession;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewReservationsForm extends JFrame {
    private JTextArea reservationsTextArea;
    private ReservationController reservationController;

    public ViewReservationsForm() {
        setTitle("View Reservations");
        setSize(800, 600);
        setLayout(new BorderLayout());
        initializeUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeUI() {
        reservationController = new ReservationController();
        reservationsTextArea = new JTextArea(10, 50);
        reservationsTextArea.setEditable(false); // Make the text area non-editable
        JScrollPane scrollPane = new JScrollPane(reservationsTextArea);
        int currentUserId = UserSession.getInstance().getCustomerID();
        System.out.println("Current User ID: " + currentUserId);
        List<Reservation> reservations = reservationController.getReservationsForCustomer(currentUserId);

        System.out.println("Reservations: " + reservations);//debug

        StringBuilder sb = new StringBuilder();
        for (Reservation reservation : reservations) {
            sb.append("Reservation ID: ").append(reservation.getReservationID())
                    .append(", Station ID: ").append(reservation.getStationID())
                    .append(", Charger ID: ").append(reservation.getChargerID())
                    .append(", Date and Time: ")
                    .append(reservation.getReservationDateTime() != null ? reservation.getReservationDateTime().toString() : "Not set")
                    .append(", Status: ").append(reservation.getStatus()).append("\n");
        }

        reservationsTextArea.setText(sb.toString());
        add(scrollPane, BorderLayout.CENTER);
    }
}
/*public class ViewReservationsForm extends JFrame {
    private JTable reservationsTable;
    private ReservationController reservationController;

    public ViewReservationsForm() {
        setTitle("View Reservations");
        setSize(800, 600);
        setLayout(new BorderLayout());
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeUI() {
        reservationController = new ReservationController();
        String[] columnNames = {"Reservation ID", "Station ID", "Charger ID", "Date and Time", "Status"};
        int currentUserId = UserSession.getInstance().getCustomerID();
        List<Reservation> reservations = reservationController.getReservationsForCustomer(currentUserId);

        Object[][] data = new Object[reservations.size()][5];
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            data[i][0] = reservation.getReservationID();
            data[i][1] = reservation.getStationID();
            data[i][2] = reservation.getChargerID();
            data[i][3] = reservation.getReservationDateTime() != null ? reservation.getReservationDateTime().toString() : "Not set";
            data[i][4] = reservation.getStatus();
        }

        reservationsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(reservationsTable);
        reservationsTable.setFillsViewportHeight(true);

        add(scrollPane, BorderLayout.CENTER);
    }
}
*/



/*
public class ViewReservationsForm extends JFrame {
    private JTable reservationsTable;
    private ReservationController reservationController;

    public ViewReservationsForm() {
        setTitle("View Reservations");
        setSize(800, 600);
        setLayout(new BorderLayout());
        initializeUI();
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initializeUI() {
        reservationController = new ReservationController();
        String[] columnNames = {"Reservation ID", "Station ID", "Charger ID", "Date and Time", "Status"};

        // Fetch the reservations for the current user
        int currentUserId = UserSession.getInstance().getCustomerID(); // Assuming you store the user ID in the session
        List<Reservation> reservations = reservationController.getReservationsByCustomerId(currentUserId);

        Object[][] data = new Object[reservations.size()][5];
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            data[i][0] = reservation.getReservationID();
            data[i][1] = reservation.getStationID();
            data[i][2] = reservation.getChargerID();
            // Use a ternary operator to check for null
            data[i][3] = reservation.getReservationDateTime() != null ? reservation.getReservationDateTime().toString() : "Not set";
            data[i][4] = reservation.getStatus();
        }

        reservationsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(reservationsTable);
        reservationsTable.setFillsViewportHeight(true);

        add(scrollPane, BorderLayout.CENTER);
    }
}*/
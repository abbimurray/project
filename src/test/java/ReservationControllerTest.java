import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import model.Reservation;
import dao.ReservationDao;
import controller.ReservationController;

class ReservationControllerTest {

    @Mock
    private ReservationDao reservationDao;

    @InjectMocks
    private ReservationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetReservationsForCustomer() {
        // Setup
        Reservation reservation = new Reservation();
        reservation.setReservationID(1);
        reservation.setCustomerID(1);
        when(reservationDao.getReservationsByCustomerId(1)).thenReturn(Arrays.asList(reservation));

        // Execution
        List<Reservation> results = controller.getReservationsForCustomer(1);

        // Assertions
        assertFalse(results.isEmpty());
        assertEquals(1, results.get(0).getReservationID());
    }

    @Test
    void testAddReservation_ChargerAvailable() {
        // Setup
        Reservation reservation = new Reservation();
        reservation.setChargerID(1);
        reservation.setReservationStartTime(LocalDateTime.now());
        reservation.setReservationEndTime(LocalDateTime.now().plusHours(2));

        when(reservationDao.isChargerAvailable(1, reservation.getReservationStartTime(), reservation.getReservationEndTime())).thenReturn(true);
        when(reservationDao.addReservation(reservation)).thenReturn(true);
        when(reservationDao.updateChargerStatus(1, "Reserved")).thenReturn(true);

        // Execution
        boolean result = controller.addReservation(reservation);

        // Assertions
        assertTrue(result);
    }

    @Test
    void testAddReservation_ChargerNotAvailable() {
        // Setup
        Reservation reservation = new Reservation();
        reservation.setChargerID(1);
        reservation.setReservationStartTime(LocalDateTime.now());
        reservation.setReservationEndTime(LocalDateTime.now().plusHours(2));

        when(reservationDao.isChargerAvailable(1, reservation.getReservationStartTime(), reservation.getReservationEndTime())).thenReturn(false);

        // Execution
        boolean result = controller.addReservation(reservation);

        // Assertions
        assertFalse(result);
    }

    @Test
    void testDeleteReservation_Successful() {
        // Setup
        when(reservationDao.getChargerIDForReservation(1)).thenReturn(1);
        when(reservationDao.deleteReservation(1)).thenReturn(true);
        when(reservationDao.updateChargerStatus(1, "Available")).thenReturn(true);

        // Execution
        boolean result = controller.deleteReservation(1);

        // Assertions
        assertTrue(result);
    }

    @Test
    void testDeleteReservation_Failure() {
        // Setup
        when(reservationDao.getChargerIDForReservation(1)).thenReturn(-1);

        // Execution
        boolean result = controller.deleteReservation(1);

        // Assertions
        assertFalse(result);
    }

    @Test
    void testUpdateReservation_ChargerAvailable() {
        // Setup
        Reservation reservation = new Reservation();
        reservation.setChargerID(1);
        reservation.setReservationStartTime(LocalDateTime.now());
        reservation.setReservationEndTime(LocalDateTime.now().plusHours(2));

        when(reservationDao.isChargerAvailable(1, reservation.getReservationStartTime(), reservation.getReservationEndTime())).thenReturn(true);
        when(reservationDao.updateReservation(reservation)).thenReturn(true);

        // Execution
        boolean result = controller.updateReservation(reservation);

        // Assertions
        assertTrue(result);
    }

    @Test
    void testUpdateReservation_ChargerNotAvailable() {
        // Setup
        Reservation reservation = new Reservation();
        reservation.setChargerID(1);
        reservation.setReservationStartTime(LocalDateTime.now());
        reservation.setReservationEndTime(LocalDateTime.now().plusHours(2));

        when(reservationDao.isChargerAvailable(1, reservation.getReservationStartTime(), reservation.getReservationEndTime())).thenReturn(false);

        // Execution
        boolean result = controller.updateReservation(reservation);

        // Assertions
        assertFalse(result);
    }
}

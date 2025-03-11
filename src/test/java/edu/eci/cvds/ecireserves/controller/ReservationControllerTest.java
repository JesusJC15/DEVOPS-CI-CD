package edu.eci.cvds.ecireserves.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.eci.cvds.ecireserves.dto.ReservationDTO;
import edu.eci.cvds.ecireserves.enums.ReservationStatus;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.model.Reservation;
import edu.eci.cvds.ecireserves.service.ReservationService;

class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllReservations_ShouldReturnListOfReservations() {
        List<Reservation> reservations = Arrays.asList(new Reservation("1", "u1", "l1", LocalDate.now(), LocalTime.now(), 30, "description", ReservationStatus.AGENDADA));
        when(reservationService.getAllReservations()).thenReturn(reservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getAllReservations();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, response.getBody().getData().size());
        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void getReservationsByUserId_ShouldReturnReservations() {
        List<Reservation> reservations = Arrays.asList(new Reservation("1", "user1", "lab1", LocalDate.now(), LocalTime.of(10, 0), 60, "Study", ReservationStatus.AGENDADA));
        when(reservationService.getReservationsByUserId("user1")).thenReturn(reservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByUserId("user1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, response.getBody().getData().size());
        verify(reservationService, times(1)).getReservationsByUserId("user1");
    }

    @Test
    void getReservationsByLaboratoryId_ShouldReturnReservations() {
        List<Reservation> reservations = Arrays.asList(new Reservation("1", "user1", "lab1", LocalDate.now(), LocalTime.of(10, 0), 120, "Study", ReservationStatus.AGENDADA));
        when(reservationService.getReservationsByLaboratoryId("lab1")).thenReturn(reservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByLaboratoryId("lab1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, response.getBody().getData().size());
        verify(reservationService, times(1)).getReservationsByLaboratoryId("lab1");
    }

    @Test
    void getReservationsByStatus_ShouldReturnReservations() {
        List<Reservation> reservations = Arrays.asList(new Reservation("1", "user1", "lab1", LocalDate.now(), LocalTime.of(10, 0), 30, "Study", ReservationStatus.AGENDADA));
        when(reservationService.getReservationsByStatus(ReservationStatus.AGENDADA)).thenReturn(reservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByStatus(ReservationStatus.AGENDADA);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, response.getBody().getData().size());
        verify(reservationService, times(1)).getReservationsByStatus(ReservationStatus.AGENDADA);
    }

    @Test
    void createReservation_ShouldReturnCreatedReservation() throws EciReservesException {
        ReservationDTO reservationDTO = new ReservationDTO("1", "user1", "lab1", LocalDate.now(), LocalTime.of(10, 0), 30, "Study");
        Reservation createdReservation = new Reservation("1", "user1", "lab1", LocalDate.now(), LocalTime.of(10, 0), 30, "Study", ReservationStatus.AGENDADA);
        when(reservationService.createReservation(reservationDTO)).thenReturn(createdReservation);

        ResponseEntity<ApiResponse<Reservation>> response = reservationController.createReservation(reservationDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("1", response.getBody().getData().getId());
        verify(reservationService, times(1)).createReservation(reservationDTO);
    }

    @Test
    void updateReservation_ShouldReturnUpdatedReservation() throws EciReservesException {
        ReservationDTO reservationDTO = new ReservationDTO("1", "user1", "lab1", LocalDate.now(), LocalTime.of(11, 0), 30, "Updated Study");
        Reservation updatedReservation = new Reservation("1", "user1", "lab1", LocalDate.now(), LocalTime.of(11, 0), 30, "Updated Study", ReservationStatus.AGENDADA);
        when(reservationService.updateReservation("1", reservationDTO)).thenReturn(updatedReservation);

        ResponseEntity<ApiResponse<Reservation>> response = reservationController.updateReservation("1", reservationDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Updated Study", response.getBody().getData().getPurpose());
        verify(reservationService, times(1)).updateReservation("1", reservationDTO);
    }

    @Test
    void deleteReservation_ShouldReturnSuccessMessage() throws EciReservesException {
        doNothing().when(reservationService).deleteReservation("1");

        ResponseEntity<ApiResponse<Void>> response = reservationController.deleteReservation("1");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        verify(reservationService, times(1)).deleteReservation("1");
    }

    @Test
    void getReservationsByUserIdAndStatus_ShouldReturnReservations() {
        List<Reservation> reservations = Arrays.asList(
            new Reservation("1", "user1", "lab1", LocalDate.now(), LocalTime.of(10, 0), 30, "Study", ReservationStatus.AGENDADA)
        );
        when(reservationService.getReservationsByUserIdAndStatus("user1", ReservationStatus.AGENDADA)).thenReturn(reservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByUserIdAndStatus("user1", ReservationStatus.AGENDADA);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, response.getBody().getData().size());
        verify(reservationService, times(1)).getReservationsByUserIdAndStatus("user1", ReservationStatus.AGENDADA);
    }

    @Test
    void getReservationsByDuration_ShouldReturnReservations() {
        List<Reservation> reservations = Arrays.asList(
            new Reservation("1", "user1", "lab1", LocalDate.now(), LocalTime.of(10, 0), 60, "Study", ReservationStatus.AGENDADA)
        );
        when(reservationService.getReservationsByDuration(60)).thenReturn(reservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByDuration(60);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, response.getBody().getData().size());
        verify(reservationService, times(1)).getReservationsByDuration(60);
    }

    @Test
    void getReservationsByDate_ShouldReturnReservations() {
        LocalDate date = LocalDate.now();
        List<Reservation> reservations = Arrays.asList(
            new Reservation("1", "user1", "lab1", date, LocalTime.of(10, 0), 30, "Study", ReservationStatus.AGENDADA)
        );
        when(reservationService.getReservationsByDate(date)).thenReturn(reservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByDate(date);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, response.getBody().getData().size());
        verify(reservationService, times(1)).getReservationsByDate(date);
    }

    @Test
    void getReservationsByStartTime_ShouldReturnReservations() {
        LocalTime startTime = LocalTime.of(10, 0);
        List<Reservation> reservations = Arrays.asList(
            new Reservation("1", "user1", "lab1", LocalDate.now(), startTime, 30, "Study", ReservationStatus.AGENDADA)
        );
        when(reservationService.getReservationsByStartTime(startTime)).thenReturn(reservations);

        ResponseEntity<ApiResponse<List<Reservation>>> response = reservationController.getReservationsByStartTime(startTime);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, response.getBody().getData().size());
        verify(reservationService, times(1)).getReservationsByStartTime(startTime);
    }

}

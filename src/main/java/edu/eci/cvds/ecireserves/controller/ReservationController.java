package edu.eci.cvds.ecireserves.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ecireserves.dto.ReservationDTO;
import edu.eci.cvds.ecireserves.enums.ReservationStatus;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.model.Reservation;
import edu.eci.cvds.ecireserves.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Reservation>>> getAllReservations() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas obtenidas exitosamente", reservationService.getAllReservations()));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByUserId(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas del usuario encontradas", reservationService.getReservationsByUserId(userId)));
    }

    @GetMapping("/laboratory/{laboratoryId}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByLaboratoryId(@PathVariable("laboratoryId") String laboratoryId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas del laboratorio encontradas", reservationService.getReservationsByLaboratoryId(laboratoryId)));
    }

    @GetMapping("status/{status}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByStatus(@PathVariable("status") ReservationStatus status) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas por estado encontradas", reservationService.getReservationsByStatus(status)));
    }

    @GetMapping("user/{userId}/status/{status}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByUserIdAndStatus(@PathVariable("userId") String userId, @PathVariable("status") ReservationStatus status) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas encontradas", reservationService.getReservationsByUserIdAndStatus(userId, status)));
    }

    @GetMapping("date/{date}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByDate(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas por fecha encontradas", reservationService.getReservationsByDate(date)));
    }

    @GetMapping("startsTime/{startTime}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByStartTime(@PathVariable("startTime") LocalTime startTime) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas por hora de inicio encontradas", reservationService.getReservationsByStartTime(startTime)));
    }

    @GetMapping("duration/{duration}")
    public ResponseEntity<ApiResponse<List<Reservation>>> getReservationsByDuration(@PathVariable("duration") int duration) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas por duración encontradas", reservationService.getReservationsByDuration(duration)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Reservation>> createReservation(@RequestBody ReservationDTO reservationDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reserva creada", reservationService.createReservation(reservationDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Reservation>> updateReservation(@PathVariable("id") String id, @RequestBody ReservationDTO reservationDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Reserva actualizada", reservationService.updateReservation(id, reservationDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReservation(@PathVariable("id") String id) throws EciReservesException {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reserva eliminada", null));
    }
}

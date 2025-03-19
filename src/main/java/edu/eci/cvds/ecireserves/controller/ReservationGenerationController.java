package edu.eci.cvds.ecireserves.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.model.Reservation;
import edu.eci.cvds.ecireserves.service.ReservationGenerationService;


@RestController
@RequestMapping("/api/reservationsGeneration")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationGenerationController {

    @Autowired
    private final ReservationGenerationService reservationGenerationService;
    public ReservationGenerationController(ReservationGenerationService reservationGenerationService) {
        this.reservationGenerationService = reservationGenerationService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Reservation>>> getAllReservations(){
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas obtenidas exitosamente", reservationGenerationService.getAllReservations()));
    }

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<Integer>> generateRandomReservations(){
        int generatedReservations = reservationGenerationService.generateRandomReservations();
        ApiResponse<Integer> apiResponse = new ApiResponse<>(true, "Número de reservas generadas exitosamente", generatedReservations);
        return ResponseEntity.ok(apiResponse);
    }
    
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAllReservations(){
        reservationGenerationService.deleteAllReservations();
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservas eliminadas exitosamente", null));
    }
}

package edu.eci.cvds.ecireserves.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.cvds.ecireserves.enums.ReservationStatus;
import edu.eci.cvds.ecireserves.model.Reservation;
import edu.eci.cvds.ecireserves.repository.ReservationRepository;

@Service
public class ReservationGenerationService {

    @Autowired
    private final ReservationRepository reservationRepository;
    public ReservationGenerationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    private final Random random = new Random();

    private final String[] purposes = { "Clase", "Estudio", "Trabajo en grupo", "Proyecto", "Otro"};

    private final String[] userIds = { "user1", "user2", "user3", "user4", "user5", "user6", "user7", "user8", "user9", "user10"};

    private final String[] laboratoryIds = { "lab1", "lab2", "lab3", "lab4", "lab5"};

    public int generateRandomReservations(){
        int numberOfReservations = random.nextInt(901) + 100;
        List<Reservation> reservations = new ArrayList<>();

        for(int i = 0; i < numberOfReservations; i++){
            String userId = userIds[random.nextInt(userIds.length)];
            String laboratoryId = laboratoryIds[random.nextInt(laboratoryIds.length)];
            LocalDate date = LocalDate.now().plusDays(random.nextInt(30));
            LocalTime startTime = LocalTime.of(random.nextInt(24), random.nextInt(13) + 7, 0);
            int duration = (random.nextInt(6) + 1) * 30;
            String purpose = purposes[random.nextInt(purposes.length)];
            ReservationStatus status = ReservationStatus.values()[random.nextInt(ReservationStatus.values().length)];
            int priority = random.nextInt(5) + 1;

            reservations.add(new Reservation(UUID.randomUUID().toString(), userId, laboratoryId, date, startTime, duration, priority, purpose, status));
        }

        reservationRepository.saveAll(reservations);
        return numberOfReservations;
    }

    public List<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public void deleteAllReservations(){
        reservationRepository.deleteAll();
    }
}

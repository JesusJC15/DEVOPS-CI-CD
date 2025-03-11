package edu.eci.cvds.ecireserves.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.eci.cvds.ecireserves.enums.DaysOfWeek;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "laboratories")
public class Laboratory {
    @Id
    private String id;

    private String classroom;
    private String name;
    private int capacity;
    private String description;
    private DaysOfWeek day;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private List<TimeSlot> timeSlots = new ArrayList<>();
    private List<Boolean> availables = new ArrayList<>();

    /**
     * Add a  valid time slot to the laboratory and set it as unavailable
     * @param startTime
     * @param endTime
     * @throws EciReservesException 
    */
    public void addTimeSlot(LocalTime startTime, LocalTime endTime) throws EciReservesException {
        if (startTime.isBefore(openingTime) || endTime.isAfter(closingTime) || !startTime.isBefore(endTime)) {
            throw new EciReservesException(EciReservesException.INVALID_TIMESLOT);
        }

        for (TimeSlot slot : timeSlots) {
            if (startTime.isBefore(slot.getEndTime()) && endTime.isAfter(slot.getStartTime())) {
                throw new EciReservesException(EciReservesException.TIMESLOT_OVERLAPS);
            }
        }

        timeSlots.add(new TimeSlot(startTime, endTime));
        availables.add(false);
    }

    /**
     * Remove a time slot from the laboratory and add a new one
     * @param startTime
     * @param endTime
     * @param newStartTime
     * @param newEndTime
     * @throws EciReservesException
     */
    public void removeTimeSlot(LocalTime startTime, LocalTime endTime, LocalTime newStartTime, LocalTime newEndTime) throws EciReservesException {
        TimeSlot removedSlot = null;
        int removedIndex = -1;
    
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot slot = timeSlots.get(i);
            if (slot.getStartTime().equals(startTime) && slot.getEndTime().equals(endTime)) {
                removedSlot = slot;
                removedIndex = i;
                timeSlots.remove(i);
                availables.remove(i);
                break;
            }
        }
    
        if (removedSlot == null) {
            throw new EciReservesException(EciReservesException.TIMESLOT_NOT_FOUND);
        }
    
        try {
            addTimeSlot(newStartTime, newEndTime);
        } catch (EciReservesException e) {
            timeSlots.add(removedIndex, removedSlot);
            availables.add(removedIndex, false);
            throw e;
        }
    }
    
    /**
     * Remove a time slot from the laboratory
     * @param startTime
     * @param endTime
     * @throws EciReservesException
     */
    public void removeTimeSlot(LocalTime startTime, LocalTime endTime) throws EciReservesException {
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot slot = timeSlots.get(i);
            if (slot.getStartTime().equals(startTime) && slot.getEndTime().equals(endTime)) {
                timeSlots.remove(i);
                availables.remove(i);
                return;
            }
        }
        throw new EciReservesException(EciReservesException.TIMESLOT_NOT_FOUND);
    }
    
}
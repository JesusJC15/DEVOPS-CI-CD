package edu.eci.cvds.ecireserves.model;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.eci.cvds.ecireserves.exception.EciReservesException;

class LaboratoryTest {

    private Laboratory laboratory;

    @BeforeEach
    void setUp() {
        laboratory = new Laboratory();
        laboratory.setOpeningTime(LocalTime.of(8, 0));
        laboratory.setClosingTime(LocalTime.of(18, 0));
    }

    @Test
    void testAddValidTimeSlot() throws EciReservesException {
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        laboratory.addTimeSlot(startTime, endTime);
        assertEquals(1, laboratory.getTimeSlots().size());
        assertEquals(startTime, laboratory.getTimeSlots().get(0).getStartTime());
        assertEquals(endTime, laboratory.getTimeSlots().get(0).getEndTime());
    }

    @Test
    void testAddTimeSlotBeforeOpening() {
        LocalTime startTime = LocalTime.of(7, 30);
        LocalTime endTime = LocalTime.of(9, 0);
        EciReservesException exception = assertThrows(EciReservesException.class, () -> laboratory.addTimeSlot(startTime, endTime));
        assertEquals(EciReservesException.INVALID_TIMESLOT, exception.getMessage());
    }

    @Test
    void testAddTimeSlotAfterClosing() {
        LocalTime startTime = LocalTime.of(17, 30);
        LocalTime endTime = LocalTime.of(18, 30);
        EciReservesException exception = assertThrows(EciReservesException.class, () -> laboratory.addTimeSlot(startTime, endTime));
        assertEquals(EciReservesException.INVALID_TIMESLOT, exception.getMessage());
    }

    @Test
    void testAddTimeSlotWithSameStartAndEnd() {
        LocalTime startTime = LocalTime.of(10, 0);
        EciReservesException exception = assertThrows(EciReservesException.class, () -> laboratory.addTimeSlot(startTime, startTime));
        assertEquals(EciReservesException.INVALID_TIMESLOT, exception.getMessage());
    }

    @Test
    void testAddTimeSlotWithStartAfterEnd() {
        LocalTime startTime = LocalTime.of(11, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        EciReservesException exception = assertThrows(EciReservesException.class, () -> laboratory.addTimeSlot(startTime, endTime));
        assertEquals(EciReservesException.INVALID_TIMESLOT, exception.getMessage());
    }

    @Test
    void testAddDuplicateTimeSlot() throws EciReservesException {
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);
        laboratory.addTimeSlot(startTime, endTime);
        EciReservesException exception = assertThrows(EciReservesException.class, () -> laboratory.addTimeSlot(startTime, endTime));
        assertEquals(EciReservesException.TIMESLOT_OVERLAPS, exception.getMessage());
    }

    @Test
    void testAddOverlappingTimeSlot() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 0));
        EciReservesException exception = assertThrows(EciReservesException.class, () -> laboratory.addTimeSlot(LocalTime.of(9, 30), LocalTime.of(10, 30)));
        assertEquals(EciReservesException.TIMESLOT_OVERLAPS, exception.getMessage());
    }

    @Test
    void testRemoveExistingTimeSlot() throws EciReservesException {
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);
        laboratory.addTimeSlot(startTime, endTime);
        laboratory.removeTimeSlot(startTime, endTime);
        assertTrue(laboratory.getTimeSlots().isEmpty());
    }

    @Test
    void testTimeSlotsAndAvailablesSync() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 0));
        laboratory.addTimeSlot(LocalTime.of(10, 30), LocalTime.of(11, 30));
        List<TimeSlot> slots = laboratory.getTimeSlots();
        List<Boolean> availables = laboratory.getAvailables();
        assertEquals(slots.size(), availables.size());
    }

    @Test
    void shouldAddValidTimeSlot() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0));
        assertEquals(1, laboratory.getTimeSlots().size());
    }

    @Test
    void shouldThrowExceptionWhenTimeSlotOutOfOpeningHours() {
        EciReservesException exception = assertThrows(EciReservesException.class, () -> 
            laboratory.addTimeSlot(LocalTime.of(7, 0), LocalTime.of(9, 0))
        );
        assertEquals(EciReservesException.INVALID_TIMESLOT, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTimeSlotOverlaps() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0));

        EciReservesException exception = assertThrows(EciReservesException.class, () -> 
            laboratory.addTimeSlot(LocalTime.of(11, 0), LocalTime.of(13, 0))
        );
        assertEquals(EciReservesException.TIMESLOT_OVERLAPS, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTimeSlotAlreadyExists() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0));

        EciReservesException exception = assertThrows(EciReservesException.class, () -> 
            laboratory.addTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0))
        );
        assertEquals(EciReservesException.TIMESLOT_OVERLAPS, exception.getMessage());
    }

    @Test
    void shouldRemoveExistingTimeSlot() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0));
        laboratory.removeTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0));
        assertEquals(0, laboratory.getTimeSlots().size());
    }

    @Test
    void shouldThrowExceptionWhenRemovingNonExistentTimeSlot() {
        EciReservesException exception = assertThrows(EciReservesException.class, () -> 
            laboratory.removeTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0))
        );
        assertEquals(EciReservesException.TIMESLOT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldReplaceTimeSlotCorrectly() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0));
        laboratory.removeTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0), LocalTime.of(14, 0), LocalTime.of(16, 0));
        
        assertEquals(1, laboratory.getTimeSlots().size());
        assertEquals(LocalTime.of(14, 0), laboratory.getTimeSlots().get(0).getStartTime());
    }

    @Test
    void shouldRestoreTimeSlotIfNewTimeSlotIsInvalid() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0));

        EciReservesException exception = assertThrows(EciReservesException.class, () -> 
            laboratory.removeTimeSlot(LocalTime.of(10, 0), LocalTime.of(12, 0), LocalTime.of(7, 0), LocalTime.of(9, 0))
        );
        assertEquals(EciReservesException.INVALID_TIMESLOT, exception.getMessage());

        assertEquals(1, laboratory.getTimeSlots().size());
        assertEquals(LocalTime.of(10, 0), laboratory.getTimeSlots().get(0).getStartTime());
    }
}

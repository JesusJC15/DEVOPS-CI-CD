package edu.eci.cvds.ecireserves.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.cvds.ecireserves.dto.LaboratoryDTO;
import edu.eci.cvds.ecireserves.enums.DaysOfWeek;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.Laboratory;
import edu.eci.cvds.ecireserves.repository.LaboratoryRepository;

@Service
public class LaboratoryService {
    @Autowired
    private LaboratoryRepository laboratoryRepository;

    /**
     * Get all laboratories
     * @return List of laboratories
     */
    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    /**
     * Get laboratory by id
     * @param id Laboratory id
     * @return Laboratory
     * @throws EciReservesException
     */
    public Laboratory getLaboratoryById(String id) throws EciReservesException {
        return laboratoryRepository.findById(id).orElseThrow(() -> new EciReservesException(EciReservesException.LABORATORY_NOT_FOUND));
    }

    /**
     * Get laboratories by classroom
     * @param classroom
     * @return List of laboratories
     */
    public List<Laboratory> getLaboratoriesByClassroom(String classroom) {
        return laboratoryRepository.findByClassroom(classroom);
    }

    /**
     * Get laboratories by name
     * @param name
     * @return List of laboratories
     */
    public List<Laboratory> getLaboratoriesByName(String name) {
        return laboratoryRepository.findByName(name);
    }

    /**
     * Get laboratories by capacity
     * @param capacity
     * @return List of laboratories
     */
    public List<Laboratory> getLaboratoriesByCapacity(int capacity) {
        return laboratoryRepository.findByCapacity(capacity);
    }

    /**
     * Get laboratories by day
     * @param day
     * @return List of laboratories
     */
    public List<Laboratory> getLaboratoriesByDay(DaysOfWeek day) {
        return laboratoryRepository.findByDay(day);
    }

    /**
     * Get laboratories by opening time
     * @param openingTime
     * @return List of laboratories
     */
    public List<Laboratory> getLaboratoriesByOpeningTime(LocalTime openingTime) {
        return laboratoryRepository.findByOpeningTime(openingTime);
    }

    /**
     * Create a new laboratory
     * @param laboratoryDTO Laboratory data
     * @return Laboratory
     * @throws EciReservesException
     */
    public Laboratory createLaboratory(LaboratoryDTO laboratoryDTO) throws EciReservesException{
        if(laboratoryRepository.findById(laboratoryDTO.getId()).isPresent()){
            throw new EciReservesException(EciReservesException.LABORATORY_ALREADY_EXISTS);
        }else{
            Laboratory laboratory = new Laboratory();
            laboratory.setId(laboratoryDTO.getId());
            laboratory.setClassroom(laboratoryDTO.getClassroom());
            laboratory.setName(laboratoryDTO.getName());
            laboratory.setCapacity(laboratoryDTO.getCapacity());
            laboratory.setDescription(laboratoryDTO.getDescription());
            laboratory.setDay(laboratoryDTO.getDay());
            laboratory.setOpeningTime(laboratoryDTO.getOpeningTime());
            laboratory.setClosingTime(laboratoryDTO.getClosingTime());
            
            return laboratoryRepository.save(laboratory);
        }
    }

    /**
     * Update laboratory
     * @param id Laboratory id
     * @param laboratoryDTO Laboratory data
     * @return Laboratory
     * @throws EciReservesException
     */
    public Laboratory updateLaboratory(String id, LaboratoryDTO laboratoryDTO) throws EciReservesException {
        Laboratory laboratory = laboratoryRepository.findById(id).orElseThrow(() -> new EciReservesException(EciReservesException.LABORATORY_NOT_FOUND));
        if(laboratoryDTO.getClassroom() != null) laboratory.setClassroom(laboratoryDTO.getClassroom());
        if(laboratoryDTO.getName() != null) laboratory.setName(laboratoryDTO.getName());
        if(laboratoryDTO.getCapacity() != 0) laboratory.setCapacity(laboratoryDTO.getCapacity());
        if(laboratoryDTO.getDescription() != null) laboratory.setDescription(laboratoryDTO.getDescription());
        if(laboratoryDTO.getDay() != null) laboratory.setDay(laboratoryDTO.getDay());
        if(laboratoryDTO.getOpeningTime() != null) laboratory.setOpeningTime(laboratoryDTO.getOpeningTime());
        if(laboratoryDTO.getClosingTime() != null) laboratory.setClosingTime(laboratoryDTO.getClosingTime());

        return laboratoryRepository.save(laboratory);
    }

    /**
     * Delete laboratory
     * @param id Laboratory id
     * @throws EciReservesException
     */
    public void deleteLaboratory(String id) throws EciReservesException {
        if(!laboratoryRepository.existsById(id)){
            throw new EciReservesException(EciReservesException.LABORATORY_NOT_FOUND);
        }
        laboratoryRepository.deleteById(id);
    }
}

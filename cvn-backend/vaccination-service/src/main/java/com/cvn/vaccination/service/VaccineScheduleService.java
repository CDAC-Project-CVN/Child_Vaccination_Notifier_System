package com.cvn.vaccination.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cvn.vaccination.client.UserServiceClient;
import com.cvn.vaccination.dto.internal.ChildInfoResponse;
import com.cvn.vaccination.dto.response.VaccineScheduleResponse;
import com.cvn.vaccination.entity.Vaccine;
import com.cvn.vaccination.entity.VaccineSchedule;
import com.cvn.vaccination.enums.VaccinationStatus;
import com.cvn.vaccination.exception.ResourceNotFoundException;
import com.cvn.vaccination.repository.VaccineRepository;
import com.cvn.vaccination.repository.VaccineScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VaccineScheduleService {
	
    private final VaccineScheduleRepository vaccineScheduleRepository;
    private final VaccineRepository vaccineRepository;
    
    private final UserServiceClient userServiceClient;   // OpenFeign client
    
    
    /*
     * Generate vaccination schedule for newly registered child
     */
    
    public void generateSchedulesForChild(Long childId) {
    	// accessing child dob using OpenFeign
    	ChildInfoResponse child = userServiceClient.getChildInfo(childId); 
    	
    	generateSchedule(child.getChildId(), child.getDateOfBirth());
    }
    
    private VaccineSchedule createSchedule(Long childId, Vaccine vaccine, LocalDate dateOfBirth, Integer doseNumber) {

        VaccineSchedule schedule = new VaccineSchedule();
        schedule.setMyChildId(childId);
        schedule.setMyVaccine(vaccine);
        schedule.setDoseNumber(doseNumber);
        schedule.setStatus(VaccinationStatus.PENDING);
        schedule.setDueDate(calculateDueDate(vaccine, dateOfBirth, doseNumber));
        return schedule;
    }
    
    private LocalDate calculateDueDate(Vaccine vaccine, LocalDate dob, int doseNumber) {
        return dob.plusDays(
                vaccine.getRequiredAgeDays() + ((doseNumber - 1) * 28));
    }
    
    
    /*
     * Complete vaccination schedule of a child
     */
    
    @Transactional(readOnly = true)
    public List<VaccineScheduleResponse> getChildSchedule(Long childId) {

        return vaccineScheduleRepository
                .findByMyChildId(childId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    private VaccineScheduleResponse mapToResponse(VaccineSchedule schedule) {

        VaccineScheduleResponse response = new VaccineScheduleResponse();
        response.setScheduleId(schedule.getId());
        response.setChildId(schedule.getMyChildId());
        response.setVaccineId(schedule.getMyVaccine().getId());
        response.setVaccineName(schedule.getMyVaccine().getName());
        response.setDoseNumber(schedule.getDoseNumber());
        response.setDueDate(schedule.getDueDate());
        response.setStatus(schedule.getStatus());
        response.setCompletedDate(schedule.getCompletedDate());
        return response;
    }
    
    
    /*
     * Pending vaccinations
     */
    
    @Transactional(readOnly = true)
    public List<VaccineScheduleResponse> getPendingSchedules(Long childId) {

        return vaccineScheduleRepository
                .findByMyChildIdAndStatus(childId, VaccinationStatus.PENDING)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    
    /*
     * Completed vaccinations
     */
    
    @Transactional(readOnly = true)
    public List<VaccineScheduleResponse> getCompletedSchedules(Long childId) {

        return vaccineScheduleRepository
                .findByMyChildIdAndStatus(childId, VaccinationStatus.COMPLETED)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    
    /*
     * Called by AppointmentService after successful vaccination
     */
    
    public void markScheduleCompleted(Long scheduleId) {

        VaccineSchedule schedule = getScheduleEntity(scheduleId);
        schedule.setStatus(VaccinationStatus.COMPLETED);
        schedule.setCompletedDate(LocalDate.now());
        vaccineScheduleRepository.save(schedule);
    }
    
    private VaccineSchedule getScheduleEntity(Long scheduleId) {

        return vaccineScheduleRepository
                .findById(scheduleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vaccination schedule not found."));
    }
    
    
    
    @Transactional(readOnly = true)
    public VaccineScheduleResponse getScheduleById(
            Long scheduleId) {

        return mapToResponse(
                getScheduleEntity(scheduleId));
    }
    
    
    @Transactional
    public void generateSchedule(Long childId,
                                 LocalDate dateOfBirth) {

    	List<Vaccine> vaccines = vaccineRepository.findAll();
		List<VaccineSchedule> schedules = new ArrayList<>();
		for (Vaccine vaccine : vaccines) {
			for (int dose = 1; dose <= vaccine.getNumberOfDoses(); dose++) {
				schedules.add(createSchedule(childId, vaccine, dateOfBirth, dose));
			}
		}
		vaccineScheduleRepository.saveAll(schedules);
    }
}

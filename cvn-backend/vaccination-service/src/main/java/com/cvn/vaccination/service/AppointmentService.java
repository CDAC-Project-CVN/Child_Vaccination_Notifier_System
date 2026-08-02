package com.cvn.vaccination.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cvn.vaccination.dto.request.BookAppointmentRequest;
import com.cvn.vaccination.dto.response.AppointmentResponse;
import com.cvn.vaccination.entity.Appointment;
import com.cvn.vaccination.entity.VaccineSchedule;
import com.cvn.vaccination.enums.AppointmentStatus;
import com.cvn.vaccination.enums.VaccinationStatus;
import com.cvn.vaccination.exception.InvalidRequestException;
import com.cvn.vaccination.exception.ResourceAlreadyExistsException;
import com.cvn.vaccination.exception.ResourceNotFoundException;
import com.cvn.vaccination.repository.AppointmentRepository;
import com.cvn.vaccination.repository.VaccineScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {
	
    private final AppointmentRepository appointmentRepository;
    private final VaccineScheduleRepository vaccineScheduleRepository;
    private final VaccineScheduleService vaccineScheduleService;
    private final ModelMapper modelMapper;
    
    
    /*
     * Book Appointment
     */
    
    public AppointmentResponse bookAppointment(BookAppointmentRequest request) {
        VaccineSchedule schedule = getSchedule(request.getScheduleId());
        if (schedule.getStatus() == VaccinationStatus.COMPLETED) {
            throw new InvalidRequestException("Vaccination is already completed.");
        }
        
        boolean alreadyBooked = appointmentRepository.existsByMyVaccineScheduleAndStatus(
                schedule, AppointmentStatus.SCHEDULED);
        if (alreadyBooked) {
            throw new InvalidRequestException(
                    "Appointment already booked for this vaccination schedule.");
        }
        validateTimeSlot(request.getAppointmentTime());

        boolean slotOccupied =
                appointmentRepository
                        .existsByMyClinicIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
                                request.getClinicId(),
                                request.getAppointmentDate(),
                                request.getAppointmentTime(),
                                AppointmentStatus.CANCELLED);

        if (slotOccupied) {
            throw new ResourceAlreadyExistsException(
                    "Selected time slot is already booked.");
        }
        
        Appointment appointment = new Appointment();
        appointment.setMyVaccineSchedule(schedule);
        appointment.setMyClinicId(request.getClinicId());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment = appointmentRepository.save(appointment);
        return mapToResponse(appointment);
    }
    
    private VaccineSchedule getSchedule(Long id) {
        return vaccineScheduleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vaccination schedule not found."));
    }
    
    
    /*
     * Get appointment
     */
    
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long appointmentId) {
        return mapToResponse(getAppointment(appointmentId));
    }
    
    private Appointment getAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found."));
    }
    
    
    /*
     * Clinic appointment
     */
    
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByClinic(Long clinicId) {

        return appointmentRepository
                .findByMyClinicId(clinicId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    
    /*
     * Today's appointments
     */
    
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByDate(Long clinicId, LocalDate date) {

        return appointmentRepository
                .findByMyClinicIdAndAppointmentDate(clinicId, date)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    
    /*
     * Cancel appointment
     */
    
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = getAppointment(appointmentId);
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }
    
    
    /*
     * Complete vaccination
     */
    
    public AppointmentResponse completeVaccination(Long appointmentId, String administeredBy) {

        Appointment appointment = getAppointment(appointmentId);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointment.setAdministeredBy(administeredBy);
        appointmentRepository.save(appointment);
        vaccineScheduleService
                .markScheduleCompleted(appointment
                        .getMyVaccineSchedule()
                        .getId());
        return mapToResponse(appointment);
    }
    
    
    private AppointmentResponse mapToResponse(Appointment appointment) {
        AppointmentResponse response = modelMapper.map(appointment, AppointmentResponse.class);
        response.setAppointmentId(appointment.getId());
        response.setScheduleId(appointment.getMyVaccineSchedule().getId());
        response.setClinicId(appointment.getMyClinicId());
        return response;
    }
    
    
    @Transactional(readOnly = true)
    public List<LocalTime> getAvailableSlots(
            Long clinicId,
            LocalDate appointmentDate) {

        List<LocalTime> allSlots = generateSlots();

        List<Appointment> appointments =
                appointmentRepository
                        .findByMyClinicIdAndAppointmentDate(
                                clinicId,
                                appointmentDate);

        Set<LocalTime> bookedSlots =
                appointments.stream()
                        .filter(a -> a.getStatus() != AppointmentStatus.CANCELLED)
                        .map(Appointment::getAppointmentTime)
                        .collect(Collectors.toSet());

        return allSlots.stream()
                .filter(slot -> !bookedSlots.contains(slot))
                .toList();
    }
    
    private List<LocalTime> generateSlots() {

        List<LocalTime> slots = new ArrayList<>();

        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        while (start.isBefore(end)) {

            slots.add(start);

            start = start.plusMinutes(15);
        }

        return slots;
    }
    
    private void validateTimeSlot(LocalTime time) {

        if (time.getMinute() % 15 != 0) {
            throw new InvalidRequestException(
                    "Appointments can only be booked in 15-minute slots.");
        }

        if (time.isBefore(LocalTime.of(9, 0))
                || !time.isBefore(LocalTime.of(17, 0))) {

            throw new InvalidRequestException(
                    "Appointment time is outside clinic working hours.");
        }
    }
}

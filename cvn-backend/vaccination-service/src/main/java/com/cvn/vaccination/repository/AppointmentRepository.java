package com.cvn.vaccination.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvn.vaccination.entity.Appointment;
import com.cvn.vaccination.entity.VaccineSchedule;
import com.cvn.vaccination.enums.AppointmentStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	
    List<Appointment> findByMyClinicId(Long clinicId);

    List<Appointment> findByMyClinicIdAndAppointmentDate(
            Long clinicId,
            LocalDate appointmentDate);

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByMyVaccineScheduleId(Long scheduleId);

    List<Appointment> findByMyClinicIdAndStatus(
            Long clinicId,
            AppointmentStatus status);

    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);
    
    boolean existsByMyVaccineSchedule(VaccineSchedule schedule);

    boolean existsByMyClinicIdAndAppointmentDateAndAppointmentTimeAndStatusNot(
            Long clinicId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus status);

	boolean existsByMyVaccineScheduleAndStatus(VaccineSchedule schedule, AppointmentStatus scheduled);

}

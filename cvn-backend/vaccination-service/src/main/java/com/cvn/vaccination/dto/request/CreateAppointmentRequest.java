package com.cvn.vaccination.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAppointmentRequest {

    @NotNull(message = "Schedule Id is required.")
    private Long scheduleId;

    @NotNull(message = "Clinic Id is required.")
    private Long clinicId;

    @NotNull(message = "Appointment date is required.")
    @FutureOrPresent
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required.")
    private LocalTime appointmentTime;
}
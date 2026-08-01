package com.cvn.vaccination.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cvn.vaccination.enums.AppointmentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentResponse {

    private Long appointmentId;

    private Long scheduleId;

    private Long clinicId;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private AppointmentStatus status;

    private String administeredBy;
}
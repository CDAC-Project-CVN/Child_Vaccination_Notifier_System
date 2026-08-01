package com.cvn.vaccination.dto.request;

import com.cvn.vaccination.enums.AppointmentStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAppointmentStatusRequest {

    @NotNull(message = "Status is required.")
    private AppointmentStatus status;

    private String administeredBy;
}
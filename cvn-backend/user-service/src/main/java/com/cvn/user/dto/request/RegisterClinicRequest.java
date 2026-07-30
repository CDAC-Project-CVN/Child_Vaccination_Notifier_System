package com.cvn.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterClinicRequest extends UserRegisterRequest {

    @NotBlank(message = "Clinic name is required")
    private String clinicName;

    @NotBlank(message = "License number is required")
    private String licenseNumber;
}

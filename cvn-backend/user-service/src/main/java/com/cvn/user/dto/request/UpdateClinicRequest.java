package com.cvn.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClinicRequest {
	@NotBlank(message = "Clinic name is required.")
    @Size(max = 100, message = "Clinic name cannot exceed 100 characters.")
    private String clinicName;

    @NotBlank(message = "License number is required.")
    private String licenseNumber;

    @NotBlank(message = "Phone number is required.")
    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Invalid phone number."
    )
    private String phone;

    @NotBlank(message = "City is required.")
    private String city;

    @NotBlank(message = "State is required.")
    private String state;

    @NotBlank(message = "Pincode is required.")
    @Pattern(
        regexp = "^\\d{6}$",
        message = "Pincode must be 6 digits."
    )
    private String pincode;

    private Double latitude;

    private Double longitude;
}

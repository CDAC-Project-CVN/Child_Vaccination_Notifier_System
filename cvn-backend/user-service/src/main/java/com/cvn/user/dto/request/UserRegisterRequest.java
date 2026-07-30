package com.cvn.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
	@NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$",
             message = "Invalid phone number")
    private String phone;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^\\d{6}$",
             message = "Invalid pincode")
    private String pincode;

    private Double latitude;

    private Double longitude;
}

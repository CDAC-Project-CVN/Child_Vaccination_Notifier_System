package com.cvn.user.dto.request;

import java.time.LocalDate;

import com.cvn.user.enums.BloodGroup;
import com.cvn.user.enums.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateChildRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    private String lastName;
    
    private LocalDate dateOfBirth;

    private Gender gender;

    private BloodGroup bloodGroup;

    private String photoUrl;
}
package com.cvn.user.dto.response;

import java.time.LocalDate;

import com.cvn.user.enums.BloodGroup;
import com.cvn.user.enums.Gender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildResponse {

    private Long childId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private Gender gender;

    private BloodGroup bloodGroup;

    private String photoUrl;
}
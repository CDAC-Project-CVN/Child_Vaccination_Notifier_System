package com.cvn.user.dto.response;

import com.cvn.user.enums.Role;
import com.cvn.user.enums.UserStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParentProfileResponse {

    private Long userId;

    private String email;

    private String phone;

    private Role role;

    private UserStatus status;

    private String firstName;

    private String lastName;

    private String city;

    private String state;

    private String pincode;

    private Double latitude;

    private Double longitude;
}
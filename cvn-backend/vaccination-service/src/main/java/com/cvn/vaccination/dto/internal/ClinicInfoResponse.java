package com.cvn.vaccination.dto.internal;

import com.cvn.vaccination.enums.ClinicStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClinicInfoResponse {

    private Long clinicId;

    private String clinicName;

    private ClinicStatus status;

    private String city;

    private String state;
}

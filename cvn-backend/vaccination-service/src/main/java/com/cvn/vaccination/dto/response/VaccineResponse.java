package com.cvn.vaccination.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaccineResponse {

    private Long id;

    private String name;

    private String description;

    private String diseasePrevented;

    private Integer requiredAgeDays;

    private Integer numberOfDoses;

    private Boolean mandatory;
}
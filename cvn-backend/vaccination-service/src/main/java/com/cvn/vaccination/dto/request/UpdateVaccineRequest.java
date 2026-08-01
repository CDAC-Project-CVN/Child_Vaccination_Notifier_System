package com.cvn.vaccination.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVaccineRequest {

    @NotBlank(message = "Vaccine name is required.")
    private String name;

    private String description;

    @NotBlank(message = "Disease prevented is required.")
    private String diseasePrevented;

    @NotNull
    @Min(0)
    private Integer requiredAgeDays;

    @NotNull
    @Min(1)
    private Integer numberOfDoses;

    @NotNull
    private Boolean mandatory;
}
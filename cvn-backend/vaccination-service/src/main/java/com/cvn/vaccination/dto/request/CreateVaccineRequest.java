package com.cvn.vaccination.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVaccineRequest {

    @NotBlank(message = "Vaccine name is required.")
    private String name;

    private String description;

    @NotBlank(message = "Disease prevented is required.")
    private String diseasePrevented;

    @NotNull(message = "Required age is required.")
    @Min(value = 0)
    private Integer requiredAgeDays;

    @NotNull(message = "Number of doses is required.")
    @Min(value = 1)
    private Integer numberOfDoses;

    @NotNull(message = "Mandatory field is required.")
    private Boolean mandatory;
}
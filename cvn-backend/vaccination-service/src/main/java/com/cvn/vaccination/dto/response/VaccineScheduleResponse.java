package com.cvn.vaccination.dto.response;

import java.time.LocalDate;

import com.cvn.vaccination.enums.VaccinationStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VaccineScheduleResponse {

    private Long scheduleId;

    private Long childId;

    private Long vaccineId;

    private String vaccineName;

    private LocalDate dueDate;

    private Integer doseNumber;

    private VaccinationStatus status;

    private LocalDate completedDate;
}
package com.cvn.vaccination.event;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildRegisteredEvent {

    private Long childId;

    private LocalDate dateOfBirth;
}
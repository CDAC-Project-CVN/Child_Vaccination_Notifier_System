package com.cvn.user.dto.internal;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChildInfoResponse {

    private Long childId;

    private String firstName;
    
    private String lastName;

    private LocalDate dateOfBirth;

    private Long parentId;
}
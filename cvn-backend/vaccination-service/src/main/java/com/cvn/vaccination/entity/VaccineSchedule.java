package com.cvn.vaccination.entity;

import java.time.LocalDate;

import com.cvn.vaccination.enums.VaccinationStatus;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vaccine_schedules")
@AttributeOverride(name = "id", column = @Column(name = "schedule_id"))
public class VaccineSchedule extends BaseClass {
	
	@Column(name = "child_id", nullable = false)
    private Long myChildId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine myVaccine;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VaccinationStatus status = VaccinationStatus.PENDING;

    @Column(name = "dose_number", nullable = false)
    private Integer doseNumber;

    @Column(name = "completed_date")
    private LocalDate completedDate;

	public VaccineSchedule(Long myChildId, LocalDate dueDate, VaccinationStatus status, Integer doseNumber,
			LocalDate completedDate) {
		super();
		this.myChildId = myChildId;
		this.dueDate = dueDate;
		this.status = status;
		this.doseNumber = doseNumber;
		this.completedDate = completedDate;
	}
    
}

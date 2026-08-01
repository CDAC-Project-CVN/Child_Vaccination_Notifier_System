package com.cvn.vaccination.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cvn.vaccination.enums.AppointmentStatus;

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
@Table(name = "appointments")
@AttributeOverride(name = "id", column = @Column(name = "appointment_id"))
public class Appointment extends BaseClass {
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id", nullable = false)
	private VaccineSchedule myVaccineSchedule;
	
	@Column(name = "clinic_id", nullable = false)
	private Long myClinicId;
	
	@Column(name = "appointment_date", nullable = false)
	private LocalDate appointmentDate;
	
	@Column(name = "appointment_time", nullable = false)
	private LocalTime appointmentTime;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AppointmentStatus status = AppointmentStatus.SCHEDULED;
	
	@Column(name = "administered_by", length = 100)
    private String administeredBy;

	public Appointment(Long myClinicId, LocalDate appointmentDate, LocalTime appointmentTime, AppointmentStatus status,
			String administeredBy) {
		super();
		this.myClinicId = myClinicId;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.status = status;
		this.administeredBy = administeredBy;
	}
	
}

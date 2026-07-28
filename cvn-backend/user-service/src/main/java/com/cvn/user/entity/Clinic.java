package com.cvn.user.entity;

import com.cvn.user.enums.ClinicStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = "myUser")
//@Builder
@Entity
@Table(name = "clinics")
public class Clinic extends BaseClass {
	
	@OneToOne(fetch = FetchType.LAZY)    // cascade = CascadeType.ALL
    @JoinColumn(name = "clinic_id", nullable = false, unique = true)
	@MapsId
    private User myUser;
	
	@Column(name = "clinic_name", nullable = false)
    private String clinicName;
	
	@Column(name = "license_number", nullable = false, unique = true)
	private String licenseNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "clinic_status", nullable = false)
	private ClinicStatus status;
	
	//@Column(name = "appointment_time")
	//private int appointmentTime;

	//private int fees;
	
	//@OneToMany(mappedBy = "myClinic", cascade = CascadeType.ALL)  // fetch = FetchType.EAGER
	//private List<Appointment> myAppointments = new ArrayList<>();

	public Clinic(String clinicName, String licenseNumber, ClinicStatus status) {
		super();
		this.clinicName = clinicName;
		this.licenseNumber = licenseNumber;
		this.status = status;
	}
	
}

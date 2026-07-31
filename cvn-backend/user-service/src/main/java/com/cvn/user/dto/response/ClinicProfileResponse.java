package com.cvn.user.dto.response;

import com.cvn.user.enums.ClinicStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicProfileResponse {
	private Long clinicId;
	
	private String clinicName;
	
	private String licenseNumber;
	
	private String email;
	
	private String phone;
	
	private ClinicStatus status;
	
	private String city;
	
	private String state;
	
	private String pincode;
	
	private Double latitude;
	
	private Double longitude;
}

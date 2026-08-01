package com.cvn.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvn.user.dto.request.UpdateClinicRequest;
import com.cvn.user.dto.response.ApiResponse;
import com.cvn.user.dto.response.ClinicProfileResponse;
import com.cvn.user.service.ClinicService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clinics")
@RequiredArgsConstructor
public class ClinicController {

	private final ClinicService clinicService;
	
	@GetMapping("/profile")
	public ResponseEntity<ApiResponse<ClinicProfileResponse>> getClinicProfile() {

	    ClinicProfileResponse response = clinicService.getClinicProfile();

	    return ResponseEntity.ok(
	            ApiResponse.<ClinicProfileResponse>builder()
	                    .success(true)
	                    .message("Clinic profile fetched successfully.")
	                    .data(response)
	                    .build()
	    );
	}
	
	@PutMapping("/profile")
	public ResponseEntity<ApiResponse<ClinicProfileResponse>> updateClinicProfile(
	        @Valid @RequestBody UpdateClinicRequest request) {

	    ClinicProfileResponse response =
	            clinicService.updateClinicProfile(request);

	    return ResponseEntity.ok(
	            ApiResponse.<ClinicProfileResponse>builder()
	                    .success(true)
	                    .message("Clinic profile updated successfully.")
	                    .data(response)
	                    .build()
	    );
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<ClinicProfileResponse>>> getAllApprovedClinics() {

	    List<ClinicProfileResponse> response =
	            clinicService.getAllApprovedClinics();

	    return ResponseEntity.ok(
	            ApiResponse.<List<ClinicProfileResponse>>builder()
	                    .success(true)
	                    .message("Approved clinics fetched successfully.")
	                    .data(response)
	                    .build()
	    );
	}
	
	@GetMapping("/{clinicId}")
	public ResponseEntity<ApiResponse<ClinicProfileResponse>> getClinicById(
	        @PathVariable Long clinicId) {

	    ClinicProfileResponse response =
	            clinicService.getClinicById(clinicId);

	    return ResponseEntity.ok(
	            ApiResponse.<ClinicProfileResponse>builder()
	                    .success(true)
	                    .message("Clinic fetched successfully.")
	                    .data(response)
	                    .build()
	    );
	}
	
}

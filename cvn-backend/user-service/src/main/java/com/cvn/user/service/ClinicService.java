package com.cvn.user.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cvn.user.dto.request.UpdateClinicRequest;
import com.cvn.user.dto.response.ClinicProfileResponse;
import com.cvn.user.entity.Clinic;
import com.cvn.user.entity.User;
import com.cvn.user.enums.ClinicStatus;
import com.cvn.user.exception.ResourceNotFoundException;
import com.cvn.user.repository.ClinicRepository;
import com.cvn.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ClinicService {
	private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;
    
    private Clinic getLoggedInClinic() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));

        return clinicRepository.findByMyUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Clinic not found."));
    }
    
    @Transactional(readOnly = true)
    public ClinicProfileResponse getClinicProfile() {

        Clinic clinic = getLoggedInClinic();

        return mapToClinicProfileResponse(clinic);
    }
    
    public ClinicProfileResponse updateClinicProfile(
            UpdateClinicRequest request) {

        Clinic clinic = getLoggedInClinic();

        User user = clinic.getMyUser();

        clinic.setClinicName(request.getClinicName());
        clinic.setLicenseNumber(request.getLicenseNumber()); // not appropriate in real 

        user.setPhone(request.getPhone());

        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setPincode(request.getPincode());

        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());

        clinicRepository.save(clinic);

        return mapToClinicProfileResponse(clinic);
    }
    
    @Transactional(readOnly = true)
    public List<ClinicProfileResponse> getAllApprovedClinics() {

        List<Clinic> clinics =
                clinicRepository.findByStatus(ClinicStatus.APPROVED);

        return clinics.stream()
                .map(this::mapToClinicProfileResponse)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public ClinicProfileResponse getClinicById(Long clinicId) {

        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Clinic not found."));

        return mapToClinicProfileResponse(clinic);
    }
    
    private ClinicProfileResponse mapToClinicProfileResponse(Clinic clinic) {

        ClinicProfileResponse response = new ClinicProfileResponse();

        response.setClinicId(clinic.getId());
        response.setClinicName(clinic.getClinicName());
        response.setLicenseNumber(clinic.getLicenseNumber());
        response.setStatus(clinic.getStatus());

        User user = clinic.getMyUser();

        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setCity(user.getCity());
        response.setState(user.getState());
        response.setPincode(user.getPincode());
        response.setLatitude(user.getLatitude());
        response.setLongitude(user.getLongitude());

        return response;
    }
}

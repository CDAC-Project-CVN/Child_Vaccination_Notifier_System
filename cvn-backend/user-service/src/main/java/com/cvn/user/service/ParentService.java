package com.cvn.user.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cvn.user.dto.request.UpdateParentRequest;
import com.cvn.user.dto.response.ParentProfileResponse;
import com.cvn.user.entity.Parent;
import com.cvn.user.entity.User;
import com.cvn.user.exception.ResourceNotFoundException;
import com.cvn.user.repository.ParentRepository;
import com.cvn.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ParentService {
	
	private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    
    private Parent getLoggedInParent() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return parentRepository.findByMyUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Parent not found"));
    }
    
    public ParentProfileResponse getParentProfile() {

        Parent parent = getLoggedInParent();

        User user = parent.getMyUser();

        ParentProfileResponse response = new ParentProfileResponse();

        response.setUserId(user.getId());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());

        response.setRole(user.getRole());
        response.setStatus(user.getStatus());

        response.setFirstName(parent.getFirstName());
        response.setLastName(parent.getLastName());

        response.setCity(user.getCity());
        response.setState(user.getState());
        response.setPincode(user.getPincode());

        response.setLatitude(user.getLatitude());
        response.setLongitude(user.getLongitude());

        return response;
    }
    
    public ParentProfileResponse updateParentProfile(
            UpdateParentRequest request) {

        Parent parent = getLoggedInParent();

        User user = parent.getMyUser();

        parent.setFirstName(request.getFirstName());
        parent.setLastName(request.getLastName());

        user.setPhone(request.getPhone());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setPincode(request.getPincode());

        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());

        userRepository.save(user);
        parentRepository.save(parent);

        return getParentProfile();
    }
}

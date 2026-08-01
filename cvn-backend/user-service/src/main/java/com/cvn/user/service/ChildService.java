package com.cvn.user.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cvn.user.dto.request.AddChildRequest;
import com.cvn.user.dto.request.UpdateChildRequest;
import com.cvn.user.dto.response.ChildResponse;
import com.cvn.user.dto.response.MessageResponse;
import com.cvn.user.entity.Child;
import com.cvn.user.entity.Parent;
import com.cvn.user.entity.User;
import com.cvn.user.exception.ResourceNotFoundException;
import com.cvn.user.exception.UnauthorizedException;
import com.cvn.user.repository.ChildRepository;
import com.cvn.user.repository.ParentRepository;
import com.cvn.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChildService {
	
	private final ChildRepository childRepository;
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
                        new ResourceNotFoundException("User not found."));

        return parentRepository.findByMyUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Parent not found."));
    }
    
    private Child getParentChild(Long childId) {
        Parent parent = getLoggedInParent();

        Child child = childRepository.findById(childId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Child not found."));

        if (!child.getMyParent().getId().equals(parent.getId())) {
            throw new UnauthorizedException(
                    "You are not authorized to access this child.");
        }

        return child;
    }
    
    public ChildResponse addChild(AddChildRequest request) {
        Parent parent = getLoggedInParent();

        Child child = new Child();

        child.setFirstName(request.getFirstName());
        child.setLastName(request.getLastName());

        child.setDateOfBirth(request.getDateOfBirth());
        child.setGender(request.getGender());
        child.setBloodGroup(request.getBloodGroup());

        child.setPhotoUrl(request.getPhotoUrl());

        child.setMyParent(parent);

        Child savedChild = childRepository.save(child);

        return modelMapper.map(savedChild, ChildResponse.class);
    }
    
    @Transactional(readOnly = true)
    public List<ChildResponse> getAllChildren() {

        Parent parent = getLoggedInParent();

        List<Child> children = childRepository.findByMyParent(parent);

        return children.stream()
                .map(child -> modelMapper.map(child, ChildResponse.class))
                .toList();
    }
    
    @Transactional(readOnly = true)
    public ChildResponse getChildById(Long childId) {

        Child child = getParentChild(childId);

        return modelMapper.map(child, ChildResponse.class);
    }
    
    @Transactional
    public ChildResponse updateChild(Long childId, UpdateChildRequest request) {
        Child child = getParentChild(childId);

        child.setFirstName(request.getFirstName());
        child.setLastName(request.getLastName());
        child.setDateOfBirth(request.getDateOfBirth());
        child.setGender(request.getGender());
        child.setBloodGroup(request.getBloodGroup());
        child.setPhotoUrl(request.getPhotoUrl());

        Child updatedChild = childRepository.save(child);

        return modelMapper.map(updatedChild, ChildResponse.class);
    }
    
    @Transactional
    public MessageResponse deleteChild(Long childId) {
        Child child = getParentChild(childId);

        childRepository.delete(child);

        return new MessageResponse(
                "Child deleted successfully.");
    }
}

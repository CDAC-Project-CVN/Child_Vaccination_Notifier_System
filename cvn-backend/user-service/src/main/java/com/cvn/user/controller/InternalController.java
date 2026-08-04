package com.cvn.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cvn.user.dto.internal.ChildInfoResponse;
import com.cvn.user.dto.internal.ClinicInfoResponse;
import com.cvn.user.service.ChildService;
import com.cvn.user.service.ClinicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/internal")
@RequiredArgsConstructor
public class InternalController {

    private final ChildService childService;
    private final ClinicService clinicService;

    /*
     * Internal API
     * Get Child Information
     */
    @GetMapping("/children/{childId}")
    public ResponseEntity<ChildInfoResponse> getChildInfo(
            @PathVariable Long childId) {

        return ResponseEntity.ok(
                childService.getChildInfo(childId));
    }

    /*
     * Internal API
     * Get Clinic Information
     */
    @GetMapping("/clinics/{clinicId}")
    public ResponseEntity<ClinicInfoResponse> getClinicInfo(
            @PathVariable Long clinicId) {

        return ResponseEntity.ok(
                clinicService.getClinicInfo(clinicId));
    }

}
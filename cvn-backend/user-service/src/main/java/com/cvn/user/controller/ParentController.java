package com.cvn.user.controller;

import com.cvn.user.dto.request.UpdateParentRequest;
import com.cvn.user.dto.response.ApiResponse;
import com.cvn.user.dto.response.ParentProfileResponse;
import com.cvn.user.service.ParentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {
	private final ParentService parentService;

    /**
     * Get Logged-in Parent Profile
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ParentProfileResponse>> getProfile() {

        ParentProfileResponse response = parentService.getParentProfile();

        return ResponseEntity.ok(
                ApiResponse.<ParentProfileResponse>builder()
                        .success(true)
                        .message("Parent profile fetched successfully.")
                        .data(response)
                        .build()
        );
    }

    /**
     * Update Logged-in Parent Profile
     */
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<ParentProfileResponse>> updateProfile(
            @Valid @RequestBody UpdateParentRequest request) {

        ParentProfileResponse response =
                parentService.updateParentProfile(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<ParentProfileResponse>builder()
                                .success(true)
                                .message("Parent profile updated successfully.")
                                .data(response)
                                .build()
                );
    }
}

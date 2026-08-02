package com.cvn.vaccination.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cvn.vaccination.dto.request.CreateVaccineRequest;
import com.cvn.vaccination.dto.request.UpdateVaccineRequest;
import com.cvn.vaccination.dto.response.ApiResponse;
import com.cvn.vaccination.dto.response.VaccineResponse;
import com.cvn.vaccination.service.VaccineService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vaccines")
@RequiredArgsConstructor
@Validated
public class VaccineController {

    private final VaccineService vaccineService;

    /*
     * Create Vaccine
     */
    @PostMapping
    public ResponseEntity<ApiResponse<VaccineResponse>> createVaccine(
            @Valid @RequestBody CreateVaccineRequest request) {

        VaccineResponse response = vaccineService.createVaccine(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<VaccineResponse>builder()
                        .success(true)
                        .message("Vaccine created successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Get Vaccine By Id
     */
    @GetMapping("/{vaccineId}")
    public ResponseEntity<ApiResponse<VaccineResponse>> getVaccineById(@PathVariable Long vaccineId) {

        VaccineResponse response = vaccineService.getVaccineById(vaccineId);
        return ResponseEntity.ok(
                ApiResponse.<VaccineResponse>builder()
                        .success(true)
                        .message("Vaccine fetched successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Get All Vaccines
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<VaccineResponse>>> getAllVaccines() {

        List<VaccineResponse> response = vaccineService.getAllVaccines();
        return ResponseEntity.ok(
                ApiResponse.<List<VaccineResponse>>builder()
                        .success(true)
                        .message("Vaccines fetched successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Update Vaccine
     */
    @PutMapping("/{vaccineId}")
    public ResponseEntity<ApiResponse<VaccineResponse>> updateVaccine(
            @PathVariable Long vaccineId, 
            @Valid @RequestBody UpdateVaccineRequest request) {

        VaccineResponse response = vaccineService.updateVaccine(vaccineId, request);
        return ResponseEntity.ok(
                ApiResponse.<VaccineResponse>builder()
                        .success(true)
                        .message("Vaccine updated successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Delete Vaccine
     */
    @DeleteMapping("/{vaccineId}")
    public ResponseEntity<ApiResponse<Void>> deleteVaccine(@PathVariable Long vaccineId) {

        vaccineService.deleteVaccine(vaccineId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Vaccine deleted successfully.")
                        .build());
    }
}
package com.cvn.vaccination.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cvn.vaccination.dto.response.ApiResponse;
import com.cvn.vaccination.dto.response.VaccineScheduleResponse;
import com.cvn.vaccination.service.VaccineScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class VaccineScheduleController {

    private final VaccineScheduleService vaccineScheduleService;
    

    /*
     * Generate vaccination schedule for a child
     * (Temporary endpoint for testing)
     */
    
    @PostMapping("/generate/{childId}")
    public ResponseEntity<ApiResponse<Void>> generateSchedules(
            @PathVariable Long childId,
            @RequestParam LocalDate dateOfBirth) {

        vaccineScheduleService.generateSchedulesForChild(childId, dateOfBirth);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Vaccination schedules generated successfully.")
                        .build());
    }
    

    /*
     * Get complete vaccination schedule
     */
    
    @GetMapping("/child/{childId}")
    public ResponseEntity<ApiResponse<List<VaccineScheduleResponse>>> getChildSchedule(
            @PathVariable Long childId) {

        List<VaccineScheduleResponse> response = vaccineScheduleService.getChildSchedule(childId);
        return ResponseEntity.ok(
                ApiResponse.<List<VaccineScheduleResponse>>builder()
                        .success(true)
                        .message("Vaccination schedule fetched successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Get pending vaccinations
     */
    @GetMapping("/child/{childId}/pending")
    public ResponseEntity<ApiResponse<List<VaccineScheduleResponse>>> getPendingSchedules(
            @PathVariable Long childId) {

        List<VaccineScheduleResponse> response =
                vaccineScheduleService.getPendingSchedules(childId);
        return ResponseEntity.ok(
                ApiResponse.<List<VaccineScheduleResponse>>builder()
                        .success(true)
                        .message("Pending vaccinations fetched successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Get completed vaccinations
     */
    @GetMapping("/child/{childId}/completed")
    public ResponseEntity<ApiResponse<List<VaccineScheduleResponse>>> getCompletedSchedules(
            @PathVariable Long childId) {

        List<VaccineScheduleResponse> response =
                vaccineScheduleService.getCompletedSchedules(childId);
        return ResponseEntity.ok(
                ApiResponse.<List<VaccineScheduleResponse>>builder()
                        .success(true)
                        .message("Completed vaccinations fetched successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Get schedule by Id
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<VaccineScheduleResponse>> getScheduleById(
            @PathVariable Long scheduleId) {

        VaccineScheduleResponse response =
                vaccineScheduleService.getScheduleById(scheduleId);
        return ResponseEntity.ok(
                ApiResponse.<VaccineScheduleResponse>builder()
                        .success(true)
                        .message("Vaccination schedule fetched successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Mark vaccination completed
     */
    @PatchMapping("/{scheduleId}/complete")
    public ResponseEntity<ApiResponse<Void>> completeVaccination(
            @PathVariable Long scheduleId) {

        vaccineScheduleService.markScheduleCompleted(scheduleId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Vaccination marked as completed.")
                        .build());
    }
    
}
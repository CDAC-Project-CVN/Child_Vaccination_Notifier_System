package com.cvn.vaccination.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cvn.vaccination.dto.request.BookAppointmentRequest;
import com.cvn.vaccination.dto.response.ApiResponse;
import com.cvn.vaccination.dto.response.AppointmentResponse;
import com.cvn.vaccination.service.AppointmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    /*
     * Book Appointment
     */
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> bookAppointment(
            @Valid @RequestBody BookAppointmentRequest request) {

        AppointmentResponse response = appointmentService.bookAppointment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .message("Appointment booked successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Get Appointment By Id
     */
    @GetMapping("/{appointmentId}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointmentById(
            @PathVariable Long appointmentId) {

        AppointmentResponse response = appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .message("Appointment fetched successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Get All Appointments of Clinic
     */
    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByClinic(
            @PathVariable Long clinicId) {

        List<AppointmentResponse> response = appointmentService.getAppointmentsByClinic(clinicId);
        return ResponseEntity.ok(
                ApiResponse.<List<AppointmentResponse>>builder()
                        .success(true)
                        .message("Clinic appointments fetched successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Get Clinic Appointments By Date
     */
    @GetMapping("/clinic/{clinicId}/date")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByDate(
            @PathVariable Long clinicId, @RequestParam LocalDate date) {

        List<AppointmentResponse> response = appointmentService.getAppointmentsByDate(clinicId, date);
        return ResponseEntity.ok(
                ApiResponse.<List<AppointmentResponse>>builder()
                        .success(true)
                        .message("Appointments fetched successfully.")
                        .data(response)
                        .build());
    }

    /*
     * Cancel Appointment
     */
    @PatchMapping("/{appointmentId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelAppointment(@PathVariable Long appointmentId) {

        appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Appointment cancelled successfully.")
                        .build());
    }

    /*
     * Complete Vaccination
     */
    @PatchMapping("/{appointmentId}/complete")
    public ResponseEntity<ApiResponse<AppointmentResponse>> completeVaccination(
            @PathVariable Long appointmentId, @RequestParam String administeredBy) {

        AppointmentResponse response = appointmentService
        		.completeVaccination(appointmentId, administeredBy);
        return ResponseEntity.ok(
                ApiResponse.<AppointmentResponse>builder()
                        .success(true)
                        .message("Vaccination completed successfully.")
                        .data(response)
                        .build());
    }
    
    
    /*
     * Get Available Slots
     */
    @GetMapping("/available-slots")
    public ResponseEntity<ApiResponse<List<LocalTime>>> getAvailableSlots(
            @RequestParam Long clinicId,
            @RequestParam LocalDate appointmentDate) {

        List<LocalTime> response =
                appointmentService.getAvailableSlots(
                        clinicId,
                        appointmentDate);

        return ResponseEntity.ok(
                ApiResponse.<List<LocalTime>>builder()
                        .success(true)
                        .message("Available slots fetched successfully.")
                        .data(response)
                        .build());
    }
}
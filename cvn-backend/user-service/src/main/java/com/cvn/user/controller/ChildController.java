package com.cvn.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cvn.user.dto.request.AddChildRequest;
import com.cvn.user.dto.request.UpdateChildRequest;
import com.cvn.user.dto.response.ApiResponse;
import com.cvn.user.dto.response.ChildResponse;
import com.cvn.user.dto.response.MessageResponse;
import com.cvn.user.service.ChildService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/children")
@RequiredArgsConstructor
public class ChildController {
	
	private final ChildService childService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<ChildResponse>>> getAllChildren() {

	    List<ChildResponse> children = childService.getAllChildren();

	    return ResponseEntity.ok(
	            ApiResponse.<List<ChildResponse>>builder()
	                    .success(true)
	                    .message("Children fetched successfully.")
	                    .data(children)
	                    .build()
	    );
	}
	
	@GetMapping("/{childId}")
	public ResponseEntity<ApiResponse<ChildResponse>> getChildById(
	        @PathVariable Long childId) {

	    ChildResponse child = childService.getChildById(childId);

	    return ResponseEntity.ok(
	            ApiResponse.<ChildResponse>builder()
	                    .success(true)
	                    .message("Child fetched successfully.")
	                    .data(child)
	                    .build()
	    );
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<ChildResponse>> addChild(
	        @Valid @RequestBody AddChildRequest request) {

	    ChildResponse response = childService.addChild(request);

	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(
	                    ApiResponse.<ChildResponse>builder()
	                            .success(true)
	                            .message("Child added successfully.")
	                            .data(response)
	                            .build()
	            );
	}
	
	@PutMapping("/{childId}")
	public ResponseEntity<ApiResponse<ChildResponse>> updateChild(@PathVariable Long childId,
	        @Valid @RequestBody UpdateChildRequest request) {

	    ChildResponse response =
	            childService.updateChild(childId, request);

	    return ResponseEntity.ok(
	            ApiResponse.<ChildResponse>builder()
	                    .success(true)
	                    .message("Child updated successfully.")
	                    .data(response)
	                    .build()
	    );
	}
	
	@DeleteMapping("/{childId}")
	public ResponseEntity<ApiResponse<MessageResponse>> deleteChild(
	        @PathVariable Long childId) {

	    MessageResponse response =
	            childService.deleteChild(childId);

	    return ResponseEntity.ok(
	            ApiResponse.<MessageResponse>builder()
	                    .success(true)
	                    .message("Child deleted successfully.")
	                    .data(response)
	                    .build()
	    );
	}

}

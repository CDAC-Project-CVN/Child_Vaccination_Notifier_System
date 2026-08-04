package com.cvn.vaccination.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cvn.vaccination.dto.internal.ChildInfoResponse;
import com.cvn.vaccination.dto.internal.ClinicInfoResponse;

@FeignClient(
        name = "user-service",
        url = "${user.service.url}",
        configuration = FeignClientConfig.class
)
public interface UserServiceClient {

    @GetMapping("/api/v1/internal/children/{childId}")
    ChildInfoResponse getChildInfo(@PathVariable Long childId);

    @GetMapping("/api/v1/internal/clinics/{clinicId}")
    ClinicInfoResponse getClinicInfo(@PathVariable Long clinicId);

}
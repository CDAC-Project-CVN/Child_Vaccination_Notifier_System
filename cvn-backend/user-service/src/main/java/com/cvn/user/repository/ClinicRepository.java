package com.cvn.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvn.user.entity.Clinic;
import com.cvn.user.entity.User;
import com.cvn.user.enums.ClinicStatus;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
	List<Clinic> findByStatus(ClinicStatus status);

    boolean existsByLicenseNumber(String licenseNumber);

	Optional<Clinic> findByMyUser(User user);
}

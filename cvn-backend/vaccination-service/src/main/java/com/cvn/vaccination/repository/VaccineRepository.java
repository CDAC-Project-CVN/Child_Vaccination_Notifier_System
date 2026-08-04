package com.cvn.vaccination.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvn.vaccination.entity.Vaccine;

public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
	Optional<Vaccine> findByName(String name);

    boolean existsByName(String name);
}

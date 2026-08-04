package com.cvn.vaccination.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvn.vaccination.entity.VaccineSchedule;
import com.cvn.vaccination.enums.VaccinationStatus;

public interface VaccineScheduleRepository extends JpaRepository<VaccineSchedule, Long> {
	
    List<VaccineSchedule> findByMyChildId(Long childId);

    List<VaccineSchedule> findByStatus(VaccinationStatus status);

    List<VaccineSchedule> findByMyChildIdAndStatus(
            Long childId,
            VaccinationStatus status);

    List<VaccineSchedule> findByMyVaccineId(Long vaccineId);
}

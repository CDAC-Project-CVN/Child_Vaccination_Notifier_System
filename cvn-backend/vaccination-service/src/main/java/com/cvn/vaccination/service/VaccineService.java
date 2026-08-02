package com.cvn.vaccination.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cvn.vaccination.dto.request.CreateVaccineRequest;
import com.cvn.vaccination.dto.request.UpdateVaccineRequest;
import com.cvn.vaccination.dto.response.VaccineResponse;
import com.cvn.vaccination.entity.Vaccine;
import com.cvn.vaccination.exception.ResourceAlreadyExistsException;
import com.cvn.vaccination.exception.ResourceNotFoundException;
import com.cvn.vaccination.repository.VaccineRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final ModelMapper modelMapper;

    /*
     * Create Vaccine
     */

    public VaccineResponse createVaccine(CreateVaccineRequest request) {

        if (vaccineRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException(
                    "Vaccine already exists.");
        }

        Vaccine vaccine = modelMapper.map(request, Vaccine.class);

        vaccine = vaccineRepository.save(vaccine);

        return mapToVaccineResponse(vaccine);
    }

    /*
     * Get Vaccine By Id
     */
    
    private Vaccine getVaccineEntity(Long vaccineId) {

        return vaccineRepository.findById(vaccineId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vaccine not found."));
    }

    @Transactional(readOnly = true)
    public VaccineResponse getVaccineById(Long vaccineId) {

        Vaccine vaccine = getVaccineEntity(vaccineId);

        return mapToVaccineResponse(vaccine);
    }

    @Transactional(readOnly = true)
    public List<VaccineResponse> getAllVaccines() {

        return vaccineRepository.findAll()
                .stream()
                .map(this::mapToVaccineResponse)
                .toList();
    }

    /*
     * Update Vaccine
     */

    public VaccineResponse updateVaccine(
            Long vaccineId,
            UpdateVaccineRequest request) {

        Vaccine vaccine = getVaccineEntity(vaccineId);

        if (!vaccine.getName().equalsIgnoreCase(request.getName())
                && vaccineRepository.existsByName(request.getName())) {

            throw new ResourceAlreadyExistsException(
                    "Vaccine with this name already exists.");
        }

        vaccine.setName(request.getName());
        vaccine.setDescription(request.getDescription());
        vaccine.setDiseasePrevented(request.getDiseasePrevented());
        vaccine.setRequiredAgeDays(request.getRequiredAgeDays());
        vaccine.setNumberOfDoses(request.getNumberOfDoses());
        vaccine.setMandatory(request.getMandatory());

        vaccineRepository.save(vaccine);

        return mapToVaccineResponse(vaccine);
    }

    /*
     * Delete Vaccine
     */

    public void deleteVaccine(Long vaccineId) {

        Vaccine vaccine = getVaccineEntity(vaccineId);

        vaccineRepository.delete(vaccine);
    }

    /*
     * Helper Method
     */

    private VaccineResponse mapToVaccineResponse(Vaccine vaccine) {

        VaccineResponse response =
                modelMapper.map(vaccine, VaccineResponse.class);

        response.setId(vaccine.getId());

        return response;
    }
}
package com.dous.cashload.service;

import com.dous.cashload.service.dto.AtmInformationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing AtmInformation.
 */
public interface AtmInformationService {

    /**
     * Save a atmInformation.
     *
     * @param atmInformationDTO the entity to save
     * @return the persisted entity
     */
    AtmInformationDTO save(AtmInformationDTO atmInformationDTO);

    /**
     * Get all the atmInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AtmInformationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" atmInformation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AtmInformationDTO findOne(Long id);

    /**
     * Delete the "id" atmInformation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

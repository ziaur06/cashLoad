package com.dous.cashload.service;

import com.dous.cashload.service.dto.UserInformationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UserInformation.
 */
public interface UserInformationService {

    /**
     * Save a userInformation.
     *
     * @param userInformationDTO the entity to save
     * @return the persisted entity
     */
    UserInformationDTO save(UserInformationDTO userInformationDTO);

    /**
     * Get all the userInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UserInformationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userInformation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    UserInformationDTO findOne(Long id);

    /**
     * Delete the "id" userInformation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

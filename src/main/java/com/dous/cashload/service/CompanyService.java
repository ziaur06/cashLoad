package com.dous.cashload.service;

import com.dous.cashload.service.dto.CompanyDTO;
import java.util.List;

/**
 * Service Interface for managing Company.
 */
public interface CompanyService {

    /**
     * Save a company.
     *
     * @param companyDTO the entity to save
     * @return the persisted entity
     */
    CompanyDTO save(CompanyDTO companyDTO);

    /**
     * Get all the companies.
     *
     * @return the list of entities
     */
    List<CompanyDTO> findAll();

    /**
     * Get the "id" company.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CompanyDTO findOne(Long id);

    /**
     * Delete the "id" company.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

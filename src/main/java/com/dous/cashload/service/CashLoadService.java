package com.dous.cashload.service;

import com.dous.cashload.service.dto.CashLoadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CashLoad.
 */
public interface CashLoadService {

    /**
     * Save a cashLoad.
     *
     * @param cashLoadDTO the entity to save
     * @return the persisted entity
     */
    CashLoadDTO save(CashLoadDTO cashLoadDTO);

    /**
     * Get all the cashLoads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CashLoadDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cashLoad.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CashLoadDTO findOne(Long id);

    /**
     * Delete the "id" cashLoad.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

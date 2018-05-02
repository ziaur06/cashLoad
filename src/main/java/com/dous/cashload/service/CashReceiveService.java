package com.dous.cashload.service;

import com.dous.cashload.service.dto.CashReceiveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CashReceive.
 */
public interface CashReceiveService {

    /**
     * Save a cashReceive.
     *
     * @param cashReceiveDTO the entity to save
     * @return the persisted entity
     */
    CashReceiveDTO save(CashReceiveDTO cashReceiveDTO);

    /**
     * Get all the cashReceives.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CashReceiveDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cashReceive.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CashReceiveDTO findOne(Long id);

    /**
     * Delete the "id" cashReceive.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

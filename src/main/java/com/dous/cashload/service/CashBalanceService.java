package com.dous.cashload.service;

import com.dous.cashload.service.dto.CashBalanceDTO;
import java.util.List;

/**
 * Service Interface for managing CashBalance.
 */
public interface CashBalanceService {

    /**
     * Save a cashBalance.
     *
     * @param cashBalanceDTO the entity to save
     * @return the persisted entity
     */
    CashBalanceDTO save(CashBalanceDTO cashBalanceDTO);

    /**
     * Get all the cashBalances.
     *
     * @return the list of entities
     */
    List<CashBalanceDTO> findAll();

    /**
     * Get the "id" cashBalance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CashBalanceDTO findOne(Long id);

    /**
     * Delete the "id" cashBalance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

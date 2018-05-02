package com.dous.cashload.service;

import com.dous.cashload.service.dto.CashIssueDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CashIssue.
 */
public interface CashIssueService {

    /**
     * Save a cashIssue.
     *
     * @param cashIssueDTO the entity to save
     * @return the persisted entity
     */
    CashIssueDTO save(CashIssueDTO cashIssueDTO);

    /**
     * Get all the cashIssues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CashIssueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cashIssue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CashIssueDTO findOne(Long id);

    /**
     * Delete the "id" cashIssue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

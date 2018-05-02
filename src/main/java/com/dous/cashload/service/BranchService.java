package com.dous.cashload.service;

import com.dous.cashload.service.dto.BranchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Branch.
 */
public interface BranchService {

    /**
     * Save a branch.
     *
     * @param branchDTO the entity to save
     * @return the persisted entity
     */
    BranchDTO save(BranchDTO branchDTO);

    /**
     * Get all the branches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BranchDTO> findAll(Pageable pageable);

    /**
     * Get the "id" branch.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BranchDTO findOne(Long id);

    /**
     * Delete the "id" branch.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

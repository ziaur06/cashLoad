package com.dous.cashload.service.impl;

import com.dous.cashload.service.CashIssueService;
import com.dous.cashload.domain.CashIssue;
import com.dous.cashload.repository.CashIssueRepository;
import com.dous.cashload.service.dto.CashIssueDTO;
import com.dous.cashload.service.mapper.CashIssueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CashIssue.
 */
@Service
@Transactional
public class CashIssueServiceImpl implements CashIssueService {

    private final Logger log = LoggerFactory.getLogger(CashIssueServiceImpl.class);

    private final CashIssueRepository cashIssueRepository;

    private final CashIssueMapper cashIssueMapper;

    public CashIssueServiceImpl(CashIssueRepository cashIssueRepository, CashIssueMapper cashIssueMapper) {
        this.cashIssueRepository = cashIssueRepository;
        this.cashIssueMapper = cashIssueMapper;
    }

    /**
     * Save a cashIssue.
     *
     * @param cashIssueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CashIssueDTO save(CashIssueDTO cashIssueDTO) {
        log.debug("Request to save CashIssue : {}", cashIssueDTO);
        CashIssue cashIssue = cashIssueMapper.toEntity(cashIssueDTO);
        cashIssue = cashIssueRepository.save(cashIssue);
        return cashIssueMapper.toDto(cashIssue);
    }

    /**
     * Get all the cashIssues.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CashIssueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CashIssues");
        return cashIssueRepository.findAll(pageable)
            .map(cashIssueMapper::toDto);
    }

    /**
     * Get one cashIssue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CashIssueDTO findOne(Long id) {
        log.debug("Request to get CashIssue : {}", id);
        CashIssue cashIssue = cashIssueRepository.findOne(id);
        return cashIssueMapper.toDto(cashIssue);
    }

    /**
     * Delete the cashIssue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CashIssue : {}", id);
        cashIssueRepository.delete(id);
    }
}

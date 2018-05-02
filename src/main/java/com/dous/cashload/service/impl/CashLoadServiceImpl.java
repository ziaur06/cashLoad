package com.dous.cashload.service.impl;

import com.dous.cashload.service.CashLoadService;
import com.dous.cashload.domain.CashLoad;
import com.dous.cashload.repository.CashLoadRepository;
import com.dous.cashload.service.dto.CashLoadDTO;
import com.dous.cashload.service.mapper.CashLoadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CashLoad.
 */
@Service
@Transactional
public class CashLoadServiceImpl implements CashLoadService {

    private final Logger log = LoggerFactory.getLogger(CashLoadServiceImpl.class);

    private final CashLoadRepository cashLoadRepository;

    private final CashLoadMapper cashLoadMapper;

    public CashLoadServiceImpl(CashLoadRepository cashLoadRepository, CashLoadMapper cashLoadMapper) {
        this.cashLoadRepository = cashLoadRepository;
        this.cashLoadMapper = cashLoadMapper;
    }

    /**
     * Save a cashLoad.
     *
     * @param cashLoadDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CashLoadDTO save(CashLoadDTO cashLoadDTO) {
        log.debug("Request to save CashLoad : {}", cashLoadDTO);
        CashLoad cashLoad = cashLoadMapper.toEntity(cashLoadDTO);
        cashLoad = cashLoadRepository.save(cashLoad);
        return cashLoadMapper.toDto(cashLoad);
    }

    /**
     * Get all the cashLoads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CashLoadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CashLoads");
        return cashLoadRepository.findAll(pageable)
            .map(cashLoadMapper::toDto);
    }

    /**
     * Get one cashLoad by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CashLoadDTO findOne(Long id) {
        log.debug("Request to get CashLoad : {}", id);
        CashLoad cashLoad = cashLoadRepository.findOne(id);
        return cashLoadMapper.toDto(cashLoad);
    }

    /**
     * Delete the cashLoad by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CashLoad : {}", id);
        cashLoadRepository.delete(id);
    }
}

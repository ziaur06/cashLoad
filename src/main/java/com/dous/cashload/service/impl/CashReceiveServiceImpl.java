package com.dous.cashload.service.impl;

import com.dous.cashload.service.CashReceiveService;
import com.dous.cashload.domain.CashReceive;
import com.dous.cashload.repository.CashReceiveRepository;
import com.dous.cashload.service.dto.CashReceiveDTO;
import com.dous.cashload.service.mapper.CashReceiveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CashReceive.
 */
@Service
@Transactional
public class CashReceiveServiceImpl implements CashReceiveService {

    private final Logger log = LoggerFactory.getLogger(CashReceiveServiceImpl.class);

    private final CashReceiveRepository cashReceiveRepository;

    private final CashReceiveMapper cashReceiveMapper;

    public CashReceiveServiceImpl(CashReceiveRepository cashReceiveRepository, CashReceiveMapper cashReceiveMapper) {
        this.cashReceiveRepository = cashReceiveRepository;
        this.cashReceiveMapper = cashReceiveMapper;
    }

    /**
     * Save a cashReceive.
     *
     * @param cashReceiveDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CashReceiveDTO save(CashReceiveDTO cashReceiveDTO) {
        log.debug("Request to save CashReceive : {}", cashReceiveDTO);
        CashReceive cashReceive = cashReceiveMapper.toEntity(cashReceiveDTO);
        cashReceive = cashReceiveRepository.save(cashReceive);
        return cashReceiveMapper.toDto(cashReceive);
    }

    /**
     * Get all the cashReceives.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CashReceiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CashReceives");
        return cashReceiveRepository.findAll(pageable)
            .map(cashReceiveMapper::toDto);
    }

    /**
     * Get one cashReceive by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CashReceiveDTO findOne(Long id) {
        log.debug("Request to get CashReceive : {}", id);
        CashReceive cashReceive = cashReceiveRepository.findOne(id);
        return cashReceiveMapper.toDto(cashReceive);
    }

    /**
     * Delete the cashReceive by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CashReceive : {}", id);
        cashReceiveRepository.delete(id);
    }
}

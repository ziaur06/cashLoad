package com.dous.cashload.service.impl;

import com.dous.cashload.service.CashBalanceService;
import com.dous.cashload.domain.CashBalance;
import com.dous.cashload.repository.CashBalanceRepository;
import com.dous.cashload.service.dto.CashBalanceDTO;
import com.dous.cashload.service.mapper.CashBalanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CashBalance.
 */
@Service
@Transactional
public class CashBalanceServiceImpl implements CashBalanceService {

    private final Logger log = LoggerFactory.getLogger(CashBalanceServiceImpl.class);

    private final CashBalanceRepository cashBalanceRepository;

    private final CashBalanceMapper cashBalanceMapper;

    public CashBalanceServiceImpl(CashBalanceRepository cashBalanceRepository, CashBalanceMapper cashBalanceMapper) {
        this.cashBalanceRepository = cashBalanceRepository;
        this.cashBalanceMapper = cashBalanceMapper;
    }

    /**
     * Save a cashBalance.
     *
     * @param cashBalanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CashBalanceDTO save(CashBalanceDTO cashBalanceDTO) {
        log.debug("Request to save CashBalance : {}", cashBalanceDTO);
        CashBalance cashBalance = cashBalanceMapper.toEntity(cashBalanceDTO);
        cashBalance = cashBalanceRepository.save(cashBalance);
        return cashBalanceMapper.toDto(cashBalance);
    }

    /**
     * Get all the cashBalances.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CashBalanceDTO> findAll() {
        log.debug("Request to get all CashBalances");
        return cashBalanceRepository.findAll().stream()
            .map(cashBalanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cashBalance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CashBalanceDTO findOne(Long id) {
        log.debug("Request to get CashBalance : {}", id);
        CashBalance cashBalance = cashBalanceRepository.findOne(id);
        return cashBalanceMapper.toDto(cashBalance);
    }

    /**
     * Delete the cashBalance by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CashBalance : {}", id);
        cashBalanceRepository.delete(id);
    }
}

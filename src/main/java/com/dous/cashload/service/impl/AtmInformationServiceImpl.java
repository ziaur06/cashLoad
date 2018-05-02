package com.dous.cashload.service.impl;

import com.dous.cashload.service.AtmInformationService;
import com.dous.cashload.domain.AtmInformation;
import com.dous.cashload.repository.AtmInformationRepository;
import com.dous.cashload.service.dto.AtmInformationDTO;
import com.dous.cashload.service.mapper.AtmInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AtmInformation.
 */
@Service
@Transactional
public class AtmInformationServiceImpl implements AtmInformationService {

    private final Logger log = LoggerFactory.getLogger(AtmInformationServiceImpl.class);

    private final AtmInformationRepository atmInformationRepository;

    private final AtmInformationMapper atmInformationMapper;

    public AtmInformationServiceImpl(AtmInformationRepository atmInformationRepository, AtmInformationMapper atmInformationMapper) {
        this.atmInformationRepository = atmInformationRepository;
        this.atmInformationMapper = atmInformationMapper;
    }

    /**
     * Save a atmInformation.
     *
     * @param atmInformationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AtmInformationDTO save(AtmInformationDTO atmInformationDTO) {
        log.debug("Request to save AtmInformation : {}", atmInformationDTO);
        AtmInformation atmInformation = atmInformationMapper.toEntity(atmInformationDTO);
        atmInformation = atmInformationRepository.save(atmInformation);
        return atmInformationMapper.toDto(atmInformation);
    }

    /**
     * Get all the atmInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AtmInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AtmInformations");
        return atmInformationRepository.findAll(pageable)
            .map(atmInformationMapper::toDto);
    }

    /**
     * Get one atmInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AtmInformationDTO findOne(Long id) {
        log.debug("Request to get AtmInformation : {}", id);
        AtmInformation atmInformation = atmInformationRepository.findOne(id);
        return atmInformationMapper.toDto(atmInformation);
    }

    /**
     * Delete the atmInformation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AtmInformation : {}", id);
        atmInformationRepository.delete(id);
    }
}

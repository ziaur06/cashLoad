package com.dous.cashload.service.impl;

import com.dous.cashload.service.UserInformationService;
import com.dous.cashload.domain.UserInformation;
import com.dous.cashload.repository.UserInformationRepository;
import com.dous.cashload.service.dto.UserInformationDTO;
import com.dous.cashload.service.mapper.UserInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserInformation.
 */
@Service
@Transactional
public class UserInformationServiceImpl implements UserInformationService {

    private final Logger log = LoggerFactory.getLogger(UserInformationServiceImpl.class);

    private final UserInformationRepository userInformationRepository;

    private final UserInformationMapper userInformationMapper;

    public UserInformationServiceImpl(UserInformationRepository userInformationRepository, UserInformationMapper userInformationMapper) {
        this.userInformationRepository = userInformationRepository;
        this.userInformationMapper = userInformationMapper;
    }

    /**
     * Save a userInformation.
     *
     * @param userInformationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserInformationDTO save(UserInformationDTO userInformationDTO) {
        log.debug("Request to save UserInformation : {}", userInformationDTO);
        UserInformation userInformation = userInformationMapper.toEntity(userInformationDTO);
        userInformation = userInformationRepository.save(userInformation);
        return userInformationMapper.toDto(userInformation);
    }

    /**
     * Get all the userInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserInformations");
        return userInformationRepository.findAll(pageable)
            .map(userInformationMapper::toDto);
    }

    /**
     * Get one userInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserInformationDTO findOne(Long id) {
        log.debug("Request to get UserInformation : {}", id);
        UserInformation userInformation = userInformationRepository.findOne(id);
        return userInformationMapper.toDto(userInformation);
    }

    /**
     * Delete the userInformation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInformation : {}", id);
        userInformationRepository.delete(id);
    }
}

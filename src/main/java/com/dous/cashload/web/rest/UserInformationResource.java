package com.dous.cashload.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dous.cashload.service.UserInformationService;
import com.dous.cashload.web.rest.errors.BadRequestAlertException;
import com.dous.cashload.web.rest.util.HeaderUtil;
import com.dous.cashload.web.rest.util.PaginationUtil;
import com.dous.cashload.service.dto.UserInformationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserInformation.
 */
@RestController
@RequestMapping("/api")
public class UserInformationResource {

    private final Logger log = LoggerFactory.getLogger(UserInformationResource.class);

    private static final String ENTITY_NAME = "userInformation";

    private final UserInformationService userInformationService;

    public UserInformationResource(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;
    }

    /**
     * POST  /user-informations : Create a new userInformation.
     *
     * @param userInformationDTO the userInformationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userInformationDTO, or with status 400 (Bad Request) if the userInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-informations")
    @Timed
    public ResponseEntity<UserInformationDTO> createUserInformation(@RequestBody UserInformationDTO userInformationDTO) throws URISyntaxException {
        log.debug("REST request to save UserInformation : {}", userInformationDTO);
        if (userInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new userInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserInformationDTO result = userInformationService.save(userInformationDTO);
        return ResponseEntity.created(new URI("/api/user-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-informations : Updates an existing userInformation.
     *
     * @param userInformationDTO the userInformationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userInformationDTO,
     * or with status 400 (Bad Request) if the userInformationDTO is not valid,
     * or with status 500 (Internal Server Error) if the userInformationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-informations")
    @Timed
    public ResponseEntity<UserInformationDTO> updateUserInformation(@RequestBody UserInformationDTO userInformationDTO) throws URISyntaxException {
        log.debug("REST request to update UserInformation : {}", userInformationDTO);
        if (userInformationDTO.getId() == null) {
            return createUserInformation(userInformationDTO);
        }
        UserInformationDTO result = userInformationService.save(userInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-informations : get all the userInformations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userInformations in body
     */
    @GetMapping("/user-informations")
    @Timed
    public ResponseEntity<List<UserInformationDTO>> getAllUserInformations(Pageable pageable) {
        log.debug("REST request to get a page of UserInformations");
        Page<UserInformationDTO> page = userInformationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-informations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-informations/:id : get the "id" userInformation.
     *
     * @param id the id of the userInformationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userInformationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-informations/{id}")
    @Timed
    public ResponseEntity<UserInformationDTO> getUserInformation(@PathVariable Long id) {
        log.debug("REST request to get UserInformation : {}", id);
        UserInformationDTO userInformationDTO = userInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userInformationDTO));
    }

    /**
     * DELETE  /user-informations/:id : delete the "id" userInformation.
     *
     * @param id the id of the userInformationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-informations/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserInformation(@PathVariable Long id) {
        log.debug("REST request to delete UserInformation : {}", id);
        userInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

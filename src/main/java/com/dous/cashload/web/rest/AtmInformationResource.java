package com.dous.cashload.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dous.cashload.service.AtmInformationService;
import com.dous.cashload.web.rest.errors.BadRequestAlertException;
import com.dous.cashload.web.rest.util.HeaderUtil;
import com.dous.cashload.web.rest.util.PaginationUtil;
import com.dous.cashload.service.dto.AtmInformationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AtmInformation.
 */
@RestController
@RequestMapping("/api")
public class AtmInformationResource {

    private final Logger log = LoggerFactory.getLogger(AtmInformationResource.class);

    private static final String ENTITY_NAME = "atmInformation";

    private final AtmInformationService atmInformationService;

    public AtmInformationResource(AtmInformationService atmInformationService) {
        this.atmInformationService = atmInformationService;
    }

    /**
     * POST  /atm-informations : Create a new atmInformation.
     *
     * @param atmInformationDTO the atmInformationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new atmInformationDTO, or with status 400 (Bad Request) if the atmInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/atm-informations")
    @Timed
    public ResponseEntity<AtmInformationDTO> createAtmInformation(@Valid @RequestBody AtmInformationDTO atmInformationDTO) throws URISyntaxException {
        log.debug("REST request to save AtmInformation : {}", atmInformationDTO);
        if (atmInformationDTO.getId() != null) {
            throw new BadRequestAlertException("A new atmInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AtmInformationDTO result = atmInformationService.save(atmInformationDTO);
        return ResponseEntity.created(new URI("/api/atm-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /atm-informations : Updates an existing atmInformation.
     *
     * @param atmInformationDTO the atmInformationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated atmInformationDTO,
     * or with status 400 (Bad Request) if the atmInformationDTO is not valid,
     * or with status 500 (Internal Server Error) if the atmInformationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/atm-informations")
    @Timed
    public ResponseEntity<AtmInformationDTO> updateAtmInformation(@Valid @RequestBody AtmInformationDTO atmInformationDTO) throws URISyntaxException {
        log.debug("REST request to update AtmInformation : {}", atmInformationDTO);
        if (atmInformationDTO.getId() == null) {
            return createAtmInformation(atmInformationDTO);
        }
        AtmInformationDTO result = atmInformationService.save(atmInformationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, atmInformationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /atm-informations : get all the atmInformations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of atmInformations in body
     */
    @GetMapping("/atm-informations")
    @Timed
    public ResponseEntity<List<AtmInformationDTO>> getAllAtmInformations(Pageable pageable) {
        log.debug("REST request to get a page of AtmInformations");
        Page<AtmInformationDTO> page = atmInformationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/atm-informations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /atm-informations/:id : get the "id" atmInformation.
     *
     * @param id the id of the atmInformationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the atmInformationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/atm-informations/{id}")
    @Timed
    public ResponseEntity<AtmInformationDTO> getAtmInformation(@PathVariable Long id) {
        log.debug("REST request to get AtmInformation : {}", id);
        AtmInformationDTO atmInformationDTO = atmInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(atmInformationDTO));
    }

    /**
     * DELETE  /atm-informations/:id : delete the "id" atmInformation.
     *
     * @param id the id of the atmInformationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/atm-informations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAtmInformation(@PathVariable Long id) {
        log.debug("REST request to delete AtmInformation : {}", id);
        atmInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

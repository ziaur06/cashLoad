package com.dous.cashload.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dous.cashload.service.CashReceiveService;
import com.dous.cashload.web.rest.errors.BadRequestAlertException;
import com.dous.cashload.web.rest.util.HeaderUtil;
import com.dous.cashload.web.rest.util.PaginationUtil;
import com.dous.cashload.service.dto.CashReceiveDTO;
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
 * REST controller for managing CashReceive.
 */
@RestController
@RequestMapping("/api")
public class CashReceiveResource {

    private final Logger log = LoggerFactory.getLogger(CashReceiveResource.class);

    private static final String ENTITY_NAME = "cashReceive";

    private final CashReceiveService cashReceiveService;

    public CashReceiveResource(CashReceiveService cashReceiveService) {
        this.cashReceiveService = cashReceiveService;
    }

    /**
     * POST  /cash-receives : Create a new cashReceive.
     *
     * @param cashReceiveDTO the cashReceiveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cashReceiveDTO, or with status 400 (Bad Request) if the cashReceive has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cash-receives")
    @Timed
    public ResponseEntity<CashReceiveDTO> createCashReceive(@RequestBody CashReceiveDTO cashReceiveDTO) throws URISyntaxException {
        log.debug("REST request to save CashReceive : {}", cashReceiveDTO);
        if (cashReceiveDTO.getId() != null) {
            throw new BadRequestAlertException("A new cashReceive cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashReceiveDTO result = cashReceiveService.save(cashReceiveDTO);
        return ResponseEntity.created(new URI("/api/cash-receives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cash-receives : Updates an existing cashReceive.
     *
     * @param cashReceiveDTO the cashReceiveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cashReceiveDTO,
     * or with status 400 (Bad Request) if the cashReceiveDTO is not valid,
     * or with status 500 (Internal Server Error) if the cashReceiveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cash-receives")
    @Timed
    public ResponseEntity<CashReceiveDTO> updateCashReceive(@RequestBody CashReceiveDTO cashReceiveDTO) throws URISyntaxException {
        log.debug("REST request to update CashReceive : {}", cashReceiveDTO);
        if (cashReceiveDTO.getId() == null) {
            return createCashReceive(cashReceiveDTO);
        }
        CashReceiveDTO result = cashReceiveService.save(cashReceiveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cashReceiveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cash-receives : get all the cashReceives.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cashReceives in body
     */
    @GetMapping("/cash-receives")
    @Timed
    public ResponseEntity<List<CashReceiveDTO>> getAllCashReceives(Pageable pageable) {
        log.debug("REST request to get a page of CashReceives");
        Page<CashReceiveDTO> page = cashReceiveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cash-receives");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cash-receives/:id : get the "id" cashReceive.
     *
     * @param id the id of the cashReceiveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cashReceiveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cash-receives/{id}")
    @Timed
    public ResponseEntity<CashReceiveDTO> getCashReceive(@PathVariable Long id) {
        log.debug("REST request to get CashReceive : {}", id);
        CashReceiveDTO cashReceiveDTO = cashReceiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cashReceiveDTO));
    }

    /**
     * DELETE  /cash-receives/:id : delete the "id" cashReceive.
     *
     * @param id the id of the cashReceiveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cash-receives/{id}")
    @Timed
    public ResponseEntity<Void> deleteCashReceive(@PathVariable Long id) {
        log.debug("REST request to delete CashReceive : {}", id);
        cashReceiveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

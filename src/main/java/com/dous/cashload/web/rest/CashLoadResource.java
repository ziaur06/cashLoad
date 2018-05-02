package com.dous.cashload.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dous.cashload.service.CashLoadService;
import com.dous.cashload.web.rest.errors.BadRequestAlertException;
import com.dous.cashload.web.rest.util.HeaderUtil;
import com.dous.cashload.web.rest.util.PaginationUtil;
import com.dous.cashload.service.dto.CashLoadDTO;
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
 * REST controller for managing CashLoad.
 */
@RestController
@RequestMapping("/api")
public class CashLoadResource {

    private final Logger log = LoggerFactory.getLogger(CashLoadResource.class);

    private static final String ENTITY_NAME = "cashLoad";

    private final CashLoadService cashLoadService;

    public CashLoadResource(CashLoadService cashLoadService) {
        this.cashLoadService = cashLoadService;
    }

    /**
     * POST  /cash-loads : Create a new cashLoad.
     *
     * @param cashLoadDTO the cashLoadDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cashLoadDTO, or with status 400 (Bad Request) if the cashLoad has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cash-loads")
    @Timed
    public ResponseEntity<CashLoadDTO> createCashLoad(@RequestBody CashLoadDTO cashLoadDTO) throws URISyntaxException {
        log.debug("REST request to save CashLoad : {}", cashLoadDTO);
        if (cashLoadDTO.getId() != null) {
            throw new BadRequestAlertException("A new cashLoad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashLoadDTO result = cashLoadService.save(cashLoadDTO);
        return ResponseEntity.created(new URI("/api/cash-loads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cash-loads : Updates an existing cashLoad.
     *
     * @param cashLoadDTO the cashLoadDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cashLoadDTO,
     * or with status 400 (Bad Request) if the cashLoadDTO is not valid,
     * or with status 500 (Internal Server Error) if the cashLoadDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cash-loads")
    @Timed
    public ResponseEntity<CashLoadDTO> updateCashLoad(@RequestBody CashLoadDTO cashLoadDTO) throws URISyntaxException {
        log.debug("REST request to update CashLoad : {}", cashLoadDTO);
        if (cashLoadDTO.getId() == null) {
            return createCashLoad(cashLoadDTO);
        }
        CashLoadDTO result = cashLoadService.save(cashLoadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cashLoadDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cash-loads : get all the cashLoads.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cashLoads in body
     */
    @GetMapping("/cash-loads")
    @Timed
    public ResponseEntity<List<CashLoadDTO>> getAllCashLoads(Pageable pageable) {
        log.debug("REST request to get a page of CashLoads");
        Page<CashLoadDTO> page = cashLoadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cash-loads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cash-loads/:id : get the "id" cashLoad.
     *
     * @param id the id of the cashLoadDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cashLoadDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cash-loads/{id}")
    @Timed
    public ResponseEntity<CashLoadDTO> getCashLoad(@PathVariable Long id) {
        log.debug("REST request to get CashLoad : {}", id);
        CashLoadDTO cashLoadDTO = cashLoadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cashLoadDTO));
    }

    /**
     * DELETE  /cash-loads/:id : delete the "id" cashLoad.
     *
     * @param id the id of the cashLoadDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cash-loads/{id}")
    @Timed
    public ResponseEntity<Void> deleteCashLoad(@PathVariable Long id) {
        log.debug("REST request to delete CashLoad : {}", id);
        cashLoadService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

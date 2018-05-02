package com.dous.cashload.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dous.cashload.service.CashIssueService;
import com.dous.cashload.web.rest.errors.BadRequestAlertException;
import com.dous.cashload.web.rest.util.HeaderUtil;
import com.dous.cashload.web.rest.util.PaginationUtil;
import com.dous.cashload.service.dto.CashIssueDTO;
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
 * REST controller for managing CashIssue.
 */
@RestController
@RequestMapping("/api")
public class CashIssueResource {

    private final Logger log = LoggerFactory.getLogger(CashIssueResource.class);

    private static final String ENTITY_NAME = "cashIssue";

    private final CashIssueService cashIssueService;

    public CashIssueResource(CashIssueService cashIssueService) {
        this.cashIssueService = cashIssueService;
    }

    /**
     * POST  /cash-issues : Create a new cashIssue.
     *
     * @param cashIssueDTO the cashIssueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cashIssueDTO, or with status 400 (Bad Request) if the cashIssue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cash-issues")
    @Timed
    public ResponseEntity<CashIssueDTO> createCashIssue(@RequestBody CashIssueDTO cashIssueDTO) throws URISyntaxException {
        log.debug("REST request to save CashIssue : {}", cashIssueDTO);
        if (cashIssueDTO.getId() != null) {
            throw new BadRequestAlertException("A new cashIssue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashIssueDTO result = cashIssueService.save(cashIssueDTO);
        return ResponseEntity.created(new URI("/api/cash-issues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cash-issues : Updates an existing cashIssue.
     *
     * @param cashIssueDTO the cashIssueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cashIssueDTO,
     * or with status 400 (Bad Request) if the cashIssueDTO is not valid,
     * or with status 500 (Internal Server Error) if the cashIssueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cash-issues")
    @Timed
    public ResponseEntity<CashIssueDTO> updateCashIssue(@RequestBody CashIssueDTO cashIssueDTO) throws URISyntaxException {
        log.debug("REST request to update CashIssue : {}", cashIssueDTO);
        if (cashIssueDTO.getId() == null) {
            return createCashIssue(cashIssueDTO);
        }
        CashIssueDTO result = cashIssueService.save(cashIssueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cashIssueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cash-issues : get all the cashIssues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cashIssues in body
     */
    @GetMapping("/cash-issues")
    @Timed
    public ResponseEntity<List<CashIssueDTO>> getAllCashIssues(Pageable pageable) {
        log.debug("REST request to get a page of CashIssues");
        Page<CashIssueDTO> page = cashIssueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cash-issues");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cash-issues/:id : get the "id" cashIssue.
     *
     * @param id the id of the cashIssueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cashIssueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cash-issues/{id}")
    @Timed
    public ResponseEntity<CashIssueDTO> getCashIssue(@PathVariable Long id) {
        log.debug("REST request to get CashIssue : {}", id);
        CashIssueDTO cashIssueDTO = cashIssueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cashIssueDTO));
    }

    /**
     * DELETE  /cash-issues/:id : delete the "id" cashIssue.
     *
     * @param id the id of the cashIssueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cash-issues/{id}")
    @Timed
    public ResponseEntity<Void> deleteCashIssue(@PathVariable Long id) {
        log.debug("REST request to delete CashIssue : {}", id);
        cashIssueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

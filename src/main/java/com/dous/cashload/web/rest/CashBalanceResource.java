package com.dous.cashload.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dous.cashload.service.CashBalanceService;
import com.dous.cashload.web.rest.errors.BadRequestAlertException;
import com.dous.cashload.web.rest.util.HeaderUtil;
import com.dous.cashload.service.dto.CashBalanceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CashBalance.
 */
@RestController
@RequestMapping("/api")
public class CashBalanceResource {

    private final Logger log = LoggerFactory.getLogger(CashBalanceResource.class);

    private static final String ENTITY_NAME = "cashBalance";

    private final CashBalanceService cashBalanceService;

    public CashBalanceResource(CashBalanceService cashBalanceService) {
        this.cashBalanceService = cashBalanceService;
    }

    /**
     * POST  /cash-balances : Create a new cashBalance.
     *
     * @param cashBalanceDTO the cashBalanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cashBalanceDTO, or with status 400 (Bad Request) if the cashBalance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cash-balances")
    @Timed
    public ResponseEntity<CashBalanceDTO> createCashBalance(@RequestBody CashBalanceDTO cashBalanceDTO) throws URISyntaxException {
        log.debug("REST request to save CashBalance : {}", cashBalanceDTO);
        if (cashBalanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new cashBalance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CashBalanceDTO result = cashBalanceService.save(cashBalanceDTO);
        return ResponseEntity.created(new URI("/api/cash-balances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cash-balances : Updates an existing cashBalance.
     *
     * @param cashBalanceDTO the cashBalanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cashBalanceDTO,
     * or with status 400 (Bad Request) if the cashBalanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the cashBalanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cash-balances")
    @Timed
    public ResponseEntity<CashBalanceDTO> updateCashBalance(@RequestBody CashBalanceDTO cashBalanceDTO) throws URISyntaxException {
        log.debug("REST request to update CashBalance : {}", cashBalanceDTO);
        if (cashBalanceDTO.getId() == null) {
            return createCashBalance(cashBalanceDTO);
        }
        CashBalanceDTO result = cashBalanceService.save(cashBalanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cashBalanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cash-balances : get all the cashBalances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cashBalances in body
     */
    @GetMapping("/cash-balances")
    @Timed
    public List<CashBalanceDTO> getAllCashBalances() {
        log.debug("REST request to get all CashBalances");
        return cashBalanceService.findAll();
        }

    /**
     * GET  /cash-balances/:id : get the "id" cashBalance.
     *
     * @param id the id of the cashBalanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cashBalanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cash-balances/{id}")
    @Timed
    public ResponseEntity<CashBalanceDTO> getCashBalance(@PathVariable Long id) {
        log.debug("REST request to get CashBalance : {}", id);
        CashBalanceDTO cashBalanceDTO = cashBalanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cashBalanceDTO));
    }

    /**
     * DELETE  /cash-balances/:id : delete the "id" cashBalance.
     *
     * @param id the id of the cashBalanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cash-balances/{id}")
    @Timed
    public ResponseEntity<Void> deleteCashBalance(@PathVariable Long id) {
        log.debug("REST request to delete CashBalance : {}", id);
        cashBalanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

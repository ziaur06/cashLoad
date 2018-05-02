package com.dous.cashload.web.rest;

import com.dous.cashload.CashloadApp;

import com.dous.cashload.domain.CashBalance;
import com.dous.cashload.repository.CashBalanceRepository;
import com.dous.cashload.service.CashBalanceService;
import com.dous.cashload.service.dto.CashBalanceDTO;
import com.dous.cashload.service.mapper.CashBalanceMapper;
import com.dous.cashload.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.dous.cashload.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CashBalanceResource REST controller.
 *
 * @see CashBalanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashloadApp.class)
public class CashBalanceResourceIntTest {

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_N_100 = new BigDecimal(1);
    private static final BigDecimal UPDATED_N_100 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_N_500 = new BigDecimal(1);
    private static final BigDecimal UPDATED_N_500 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_N_1000 = new BigDecimal(1);
    private static final BigDecimal UPDATED_N_1000 = new BigDecimal(2);

    @Autowired
    private CashBalanceRepository cashBalanceRepository;

    @Autowired
    private CashBalanceMapper cashBalanceMapper;

    @Autowired
    private CashBalanceService cashBalanceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCashBalanceMockMvc;

    private CashBalance cashBalance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CashBalanceResource cashBalanceResource = new CashBalanceResource(cashBalanceService);
        this.restCashBalanceMockMvc = MockMvcBuilders.standaloneSetup(cashBalanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CashBalance createEntity(EntityManager em) {
        CashBalance cashBalance = new CashBalance()
            .balance(DEFAULT_BALANCE)
            .n100(DEFAULT_N_100)
            .n500(DEFAULT_N_500)
            .n1000(DEFAULT_N_1000);
        return cashBalance;
    }

    @Before
    public void initTest() {
        cashBalance = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashBalance() throws Exception {
        int databaseSizeBeforeCreate = cashBalanceRepository.findAll().size();

        // Create the CashBalance
        CashBalanceDTO cashBalanceDTO = cashBalanceMapper.toDto(cashBalance);
        restCashBalanceMockMvc.perform(post("/api/cash-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashBalanceDTO)))
            .andExpect(status().isCreated());

        // Validate the CashBalance in the database
        List<CashBalance> cashBalanceList = cashBalanceRepository.findAll();
        assertThat(cashBalanceList).hasSize(databaseSizeBeforeCreate + 1);
        CashBalance testCashBalance = cashBalanceList.get(cashBalanceList.size() - 1);
        assertThat(testCashBalance.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testCashBalance.getn100()).isEqualTo(DEFAULT_N_100);
        assertThat(testCashBalance.getn500()).isEqualTo(DEFAULT_N_500);
        assertThat(testCashBalance.getn1000()).isEqualTo(DEFAULT_N_1000);
    }

    @Test
    @Transactional
    public void createCashBalanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashBalanceRepository.findAll().size();

        // Create the CashBalance with an existing ID
        cashBalance.setId(1L);
        CashBalanceDTO cashBalanceDTO = cashBalanceMapper.toDto(cashBalance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashBalanceMockMvc.perform(post("/api/cash-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashBalanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CashBalance in the database
        List<CashBalance> cashBalanceList = cashBalanceRepository.findAll();
        assertThat(cashBalanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCashBalances() throws Exception {
        // Initialize the database
        cashBalanceRepository.saveAndFlush(cashBalance);

        // Get all the cashBalanceList
        restCashBalanceMockMvc.perform(get("/api/cash-balances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashBalance.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].n100").value(hasItem(DEFAULT_N_100.intValue())))
            .andExpect(jsonPath("$.[*].n500").value(hasItem(DEFAULT_N_500.intValue())))
            .andExpect(jsonPath("$.[*].n1000").value(hasItem(DEFAULT_N_1000.intValue())));
    }

    @Test
    @Transactional
    public void getCashBalance() throws Exception {
        // Initialize the database
        cashBalanceRepository.saveAndFlush(cashBalance);

        // Get the cashBalance
        restCashBalanceMockMvc.perform(get("/api/cash-balances/{id}", cashBalance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cashBalance.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()))
            .andExpect(jsonPath("$.n100").value(DEFAULT_N_100.intValue()))
            .andExpect(jsonPath("$.n500").value(DEFAULT_N_500.intValue()))
            .andExpect(jsonPath("$.n1000").value(DEFAULT_N_1000.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCashBalance() throws Exception {
        // Get the cashBalance
        restCashBalanceMockMvc.perform(get("/api/cash-balances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashBalance() throws Exception {
        // Initialize the database
        cashBalanceRepository.saveAndFlush(cashBalance);
        int databaseSizeBeforeUpdate = cashBalanceRepository.findAll().size();

        // Update the cashBalance
        CashBalance updatedCashBalance = cashBalanceRepository.findOne(cashBalance.getId());
        // Disconnect from session so that the updates on updatedCashBalance are not directly saved in db
        em.detach(updatedCashBalance);
        updatedCashBalance
            .balance(UPDATED_BALANCE)
            .n100(UPDATED_N_100)
            .n500(UPDATED_N_500)
            .n1000(UPDATED_N_1000);
        CashBalanceDTO cashBalanceDTO = cashBalanceMapper.toDto(updatedCashBalance);

        restCashBalanceMockMvc.perform(put("/api/cash-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashBalanceDTO)))
            .andExpect(status().isOk());

        // Validate the CashBalance in the database
        List<CashBalance> cashBalanceList = cashBalanceRepository.findAll();
        assertThat(cashBalanceList).hasSize(databaseSizeBeforeUpdate);
        CashBalance testCashBalance = cashBalanceList.get(cashBalanceList.size() - 1);
        assertThat(testCashBalance.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testCashBalance.getn100()).isEqualTo(UPDATED_N_100);
        assertThat(testCashBalance.getn500()).isEqualTo(UPDATED_N_500);
        assertThat(testCashBalance.getn1000()).isEqualTo(UPDATED_N_1000);
    }

    @Test
    @Transactional
    public void updateNonExistingCashBalance() throws Exception {
        int databaseSizeBeforeUpdate = cashBalanceRepository.findAll().size();

        // Create the CashBalance
        CashBalanceDTO cashBalanceDTO = cashBalanceMapper.toDto(cashBalance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCashBalanceMockMvc.perform(put("/api/cash-balances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashBalanceDTO)))
            .andExpect(status().isCreated());

        // Validate the CashBalance in the database
        List<CashBalance> cashBalanceList = cashBalanceRepository.findAll();
        assertThat(cashBalanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCashBalance() throws Exception {
        // Initialize the database
        cashBalanceRepository.saveAndFlush(cashBalance);
        int databaseSizeBeforeDelete = cashBalanceRepository.findAll().size();

        // Get the cashBalance
        restCashBalanceMockMvc.perform(delete("/api/cash-balances/{id}", cashBalance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CashBalance> cashBalanceList = cashBalanceRepository.findAll();
        assertThat(cashBalanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashBalance.class);
        CashBalance cashBalance1 = new CashBalance();
        cashBalance1.setId(1L);
        CashBalance cashBalance2 = new CashBalance();
        cashBalance2.setId(cashBalance1.getId());
        assertThat(cashBalance1).isEqualTo(cashBalance2);
        cashBalance2.setId(2L);
        assertThat(cashBalance1).isNotEqualTo(cashBalance2);
        cashBalance1.setId(null);
        assertThat(cashBalance1).isNotEqualTo(cashBalance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashBalanceDTO.class);
        CashBalanceDTO cashBalanceDTO1 = new CashBalanceDTO();
        cashBalanceDTO1.setId(1L);
        CashBalanceDTO cashBalanceDTO2 = new CashBalanceDTO();
        assertThat(cashBalanceDTO1).isNotEqualTo(cashBalanceDTO2);
        cashBalanceDTO2.setId(cashBalanceDTO1.getId());
        assertThat(cashBalanceDTO1).isEqualTo(cashBalanceDTO2);
        cashBalanceDTO2.setId(2L);
        assertThat(cashBalanceDTO1).isNotEqualTo(cashBalanceDTO2);
        cashBalanceDTO1.setId(null);
        assertThat(cashBalanceDTO1).isNotEqualTo(cashBalanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cashBalanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cashBalanceMapper.fromId(null)).isNull();
    }
}

package com.dous.cashload.web.rest;

import com.dous.cashload.CashloadApp;

import com.dous.cashload.domain.CashLoad;
import com.dous.cashload.repository.CashLoadRepository;
import com.dous.cashload.service.CashLoadService;
import com.dous.cashload.service.dto.CashLoadDTO;
import com.dous.cashload.service.mapper.CashLoadMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.dous.cashload.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CashLoadResource REST controller.
 *
 * @see CashLoadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashloadApp.class)
public class CashLoadResourceIntTest {

    private static final LocalDate DEFAULT_LOADING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LOADING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_L_100 = new BigDecimal(1);
    private static final BigDecimal UPDATED_L_100 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_L_500 = new BigDecimal(1);
    private static final BigDecimal UPDATED_L_500 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_L_1000 = new BigDecimal(1);
    private static final BigDecimal UPDATED_L_1000 = new BigDecimal(2);

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    @Autowired
    private CashLoadRepository cashLoadRepository;

    @Autowired
    private CashLoadMapper cashLoadMapper;

    @Autowired
    private CashLoadService cashLoadService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCashLoadMockMvc;

    private CashLoad cashLoad;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CashLoadResource cashLoadResource = new CashLoadResource(cashLoadService);
        this.restCashLoadMockMvc = MockMvcBuilders.standaloneSetup(cashLoadResource)
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
    public static CashLoad createEntity(EntityManager em) {
        CashLoad cashLoad = new CashLoad()
            .loadingDate(DEFAULT_LOADING_DATE)
            .amount(DEFAULT_AMOUNT)
            .l100(DEFAULT_L_100)
            .l500(DEFAULT_L_500)
            .l1000(DEFAULT_L_1000)
            .tag(DEFAULT_TAG);
        return cashLoad;
    }

    @Before
    public void initTest() {
        cashLoad = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashLoad() throws Exception {
        int databaseSizeBeforeCreate = cashLoadRepository.findAll().size();

        // Create the CashLoad
        CashLoadDTO cashLoadDTO = cashLoadMapper.toDto(cashLoad);
        restCashLoadMockMvc.perform(post("/api/cash-loads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashLoadDTO)))
            .andExpect(status().isCreated());

        // Validate the CashLoad in the database
        List<CashLoad> cashLoadList = cashLoadRepository.findAll();
        assertThat(cashLoadList).hasSize(databaseSizeBeforeCreate + 1);
        CashLoad testCashLoad = cashLoadList.get(cashLoadList.size() - 1);
        assertThat(testCashLoad.getLoadingDate()).isEqualTo(DEFAULT_LOADING_DATE);
        assertThat(testCashLoad.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCashLoad.getl100()).isEqualTo(DEFAULT_L_100);
        assertThat(testCashLoad.getl500()).isEqualTo(DEFAULT_L_500);
        assertThat(testCashLoad.getl1000()).isEqualTo(DEFAULT_L_1000);
        assertThat(testCashLoad.getTag()).isEqualTo(DEFAULT_TAG);
    }

    @Test
    @Transactional
    public void createCashLoadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashLoadRepository.findAll().size();

        // Create the CashLoad with an existing ID
        cashLoad.setId(1L);
        CashLoadDTO cashLoadDTO = cashLoadMapper.toDto(cashLoad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashLoadMockMvc.perform(post("/api/cash-loads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashLoadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CashLoad in the database
        List<CashLoad> cashLoadList = cashLoadRepository.findAll();
        assertThat(cashLoadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCashLoads() throws Exception {
        // Initialize the database
        cashLoadRepository.saveAndFlush(cashLoad);

        // Get all the cashLoadList
        restCashLoadMockMvc.perform(get("/api/cash-loads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashLoad.getId().intValue())))
            .andExpect(jsonPath("$.[*].loadingDate").value(hasItem(DEFAULT_LOADING_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].l100").value(hasItem(DEFAULT_L_100.intValue())))
            .andExpect(jsonPath("$.[*].l500").value(hasItem(DEFAULT_L_500.intValue())))
            .andExpect(jsonPath("$.[*].l1000").value(hasItem(DEFAULT_L_1000.intValue())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())));
    }

    @Test
    @Transactional
    public void getCashLoad() throws Exception {
        // Initialize the database
        cashLoadRepository.saveAndFlush(cashLoad);

        // Get the cashLoad
        restCashLoadMockMvc.perform(get("/api/cash-loads/{id}", cashLoad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cashLoad.getId().intValue()))
            .andExpect(jsonPath("$.loadingDate").value(DEFAULT_LOADING_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.l100").value(DEFAULT_L_100.intValue()))
            .andExpect(jsonPath("$.l500").value(DEFAULT_L_500.intValue()))
            .andExpect(jsonPath("$.l1000").value(DEFAULT_L_1000.intValue()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCashLoad() throws Exception {
        // Get the cashLoad
        restCashLoadMockMvc.perform(get("/api/cash-loads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashLoad() throws Exception {
        // Initialize the database
        cashLoadRepository.saveAndFlush(cashLoad);
        int databaseSizeBeforeUpdate = cashLoadRepository.findAll().size();

        // Update the cashLoad
        CashLoad updatedCashLoad = cashLoadRepository.findOne(cashLoad.getId());
        // Disconnect from session so that the updates on updatedCashLoad are not directly saved in db
        em.detach(updatedCashLoad);
        updatedCashLoad
            .loadingDate(UPDATED_LOADING_DATE)
            .amount(UPDATED_AMOUNT)
            .l100(UPDATED_L_100)
            .l500(UPDATED_L_500)
            .l1000(UPDATED_L_1000)
            .tag(UPDATED_TAG);
        CashLoadDTO cashLoadDTO = cashLoadMapper.toDto(updatedCashLoad);

        restCashLoadMockMvc.perform(put("/api/cash-loads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashLoadDTO)))
            .andExpect(status().isOk());

        // Validate the CashLoad in the database
        List<CashLoad> cashLoadList = cashLoadRepository.findAll();
        assertThat(cashLoadList).hasSize(databaseSizeBeforeUpdate);
        CashLoad testCashLoad = cashLoadList.get(cashLoadList.size() - 1);
        assertThat(testCashLoad.getLoadingDate()).isEqualTo(UPDATED_LOADING_DATE);
        assertThat(testCashLoad.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCashLoad.getl100()).isEqualTo(UPDATED_L_100);
        assertThat(testCashLoad.getl500()).isEqualTo(UPDATED_L_500);
        assertThat(testCashLoad.getl1000()).isEqualTo(UPDATED_L_1000);
        assertThat(testCashLoad.getTag()).isEqualTo(UPDATED_TAG);
    }

    @Test
    @Transactional
    public void updateNonExistingCashLoad() throws Exception {
        int databaseSizeBeforeUpdate = cashLoadRepository.findAll().size();

        // Create the CashLoad
        CashLoadDTO cashLoadDTO = cashLoadMapper.toDto(cashLoad);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCashLoadMockMvc.perform(put("/api/cash-loads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashLoadDTO)))
            .andExpect(status().isCreated());

        // Validate the CashLoad in the database
        List<CashLoad> cashLoadList = cashLoadRepository.findAll();
        assertThat(cashLoadList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCashLoad() throws Exception {
        // Initialize the database
        cashLoadRepository.saveAndFlush(cashLoad);
        int databaseSizeBeforeDelete = cashLoadRepository.findAll().size();

        // Get the cashLoad
        restCashLoadMockMvc.perform(delete("/api/cash-loads/{id}", cashLoad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CashLoad> cashLoadList = cashLoadRepository.findAll();
        assertThat(cashLoadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashLoad.class);
        CashLoad cashLoad1 = new CashLoad();
        cashLoad1.setId(1L);
        CashLoad cashLoad2 = new CashLoad();
        cashLoad2.setId(cashLoad1.getId());
        assertThat(cashLoad1).isEqualTo(cashLoad2);
        cashLoad2.setId(2L);
        assertThat(cashLoad1).isNotEqualTo(cashLoad2);
        cashLoad1.setId(null);
        assertThat(cashLoad1).isNotEqualTo(cashLoad2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashLoadDTO.class);
        CashLoadDTO cashLoadDTO1 = new CashLoadDTO();
        cashLoadDTO1.setId(1L);
        CashLoadDTO cashLoadDTO2 = new CashLoadDTO();
        assertThat(cashLoadDTO1).isNotEqualTo(cashLoadDTO2);
        cashLoadDTO2.setId(cashLoadDTO1.getId());
        assertThat(cashLoadDTO1).isEqualTo(cashLoadDTO2);
        cashLoadDTO2.setId(2L);
        assertThat(cashLoadDTO1).isNotEqualTo(cashLoadDTO2);
        cashLoadDTO1.setId(null);
        assertThat(cashLoadDTO1).isNotEqualTo(cashLoadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cashLoadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cashLoadMapper.fromId(null)).isNull();
    }
}

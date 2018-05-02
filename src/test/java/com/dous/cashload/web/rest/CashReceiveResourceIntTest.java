package com.dous.cashload.web.rest;

import com.dous.cashload.CashloadApp;

import com.dous.cashload.domain.CashReceive;
import com.dous.cashload.repository.CashReceiveRepository;
import com.dous.cashload.service.CashReceiveService;
import com.dous.cashload.service.dto.CashReceiveDTO;
import com.dous.cashload.service.mapper.CashReceiveMapper;
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
 * Test class for the CashReceiveResource REST controller.
 *
 * @see CashReceiveResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashloadApp.class)
public class CashReceiveResourceIntTest {

    private static final LocalDate DEFAULT_RECEIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_R_100 = new BigDecimal(1);
    private static final BigDecimal UPDATED_R_100 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_R_500 = new BigDecimal(1);
    private static final BigDecimal UPDATED_R_500 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_R_1000 = new BigDecimal(1);
    private static final BigDecimal UPDATED_R_1000 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RECJECTED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_RECJECTED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_F_100 = new BigDecimal(1);
    private static final BigDecimal UPDATED_F_100 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_F_500 = new BigDecimal(1);
    private static final BigDecimal UPDATED_F_500 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_F_1000 = new BigDecimal(1);
    private static final BigDecimal UPDATED_F_1000 = new BigDecimal(2);

    private static final String DEFAULT_TIP = "AAAAAAAAAA";
    private static final String UPDATED_TIP = "BBBBBBBBBB";

    @Autowired
    private CashReceiveRepository cashReceiveRepository;

    @Autowired
    private CashReceiveMapper cashReceiveMapper;

    @Autowired
    private CashReceiveService cashReceiveService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCashReceiveMockMvc;

    private CashReceive cashReceive;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CashReceiveResource cashReceiveResource = new CashReceiveResource(cashReceiveService);
        this.restCashReceiveMockMvc = MockMvcBuilders.standaloneSetup(cashReceiveResource)
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
    public static CashReceive createEntity(EntityManager em) {
        CashReceive cashReceive = new CashReceive()
            .receiveDate(DEFAULT_RECEIVE_DATE)
            .amount(DEFAULT_AMOUNT)
            .userId(DEFAULT_USER_ID)
            .remarks(DEFAULT_REMARKS)
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .tag(DEFAULT_TAG)
            .r100(DEFAULT_R_100)
            .r500(DEFAULT_R_500)
            .r1000(DEFAULT_R_1000)
            .recjectedAmount(DEFAULT_RECJECTED_AMOUNT)
            .f100(DEFAULT_F_100)
            .f500(DEFAULT_F_500)
            .f1000(DEFAULT_F_1000)
            .tip(DEFAULT_TIP);
        return cashReceive;
    }

    @Before
    public void initTest() {
        cashReceive = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashReceive() throws Exception {
        int databaseSizeBeforeCreate = cashReceiveRepository.findAll().size();

        // Create the CashReceive
        CashReceiveDTO cashReceiveDTO = cashReceiveMapper.toDto(cashReceive);
        restCashReceiveMockMvc.perform(post("/api/cash-receives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashReceiveDTO)))
            .andExpect(status().isCreated());

        // Validate the CashReceive in the database
        List<CashReceive> cashReceiveList = cashReceiveRepository.findAll();
        assertThat(cashReceiveList).hasSize(databaseSizeBeforeCreate + 1);
        CashReceive testCashReceive = cashReceiveList.get(cashReceiveList.size() - 1);
        assertThat(testCashReceive.getReceiveDate()).isEqualTo(DEFAULT_RECEIVE_DATE);
        assertThat(testCashReceive.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCashReceive.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCashReceive.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testCashReceive.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testCashReceive.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testCashReceive.getr100()).isEqualTo(DEFAULT_R_100);
        assertThat(testCashReceive.getr500()).isEqualTo(DEFAULT_R_500);
        assertThat(testCashReceive.getr1000()).isEqualTo(DEFAULT_R_1000);
        assertThat(testCashReceive.getRecjectedAmount()).isEqualTo(DEFAULT_RECJECTED_AMOUNT);
        assertThat(testCashReceive.getf100()).isEqualTo(DEFAULT_F_100);
        assertThat(testCashReceive.getf500()).isEqualTo(DEFAULT_F_500);
        assertThat(testCashReceive.getf1000()).isEqualTo(DEFAULT_F_1000);
        assertThat(testCashReceive.getTip()).isEqualTo(DEFAULT_TIP);
    }

    @Test
    @Transactional
    public void createCashReceiveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashReceiveRepository.findAll().size();

        // Create the CashReceive with an existing ID
        cashReceive.setId(1L);
        CashReceiveDTO cashReceiveDTO = cashReceiveMapper.toDto(cashReceive);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashReceiveMockMvc.perform(post("/api/cash-receives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashReceiveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CashReceive in the database
        List<CashReceive> cashReceiveList = cashReceiveRepository.findAll();
        assertThat(cashReceiveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCashReceives() throws Exception {
        // Initialize the database
        cashReceiveRepository.saveAndFlush(cashReceive);

        // Get all the cashReceiveList
        restCashReceiveMockMvc.perform(get("/api/cash-receives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashReceive.getId().intValue())))
            .andExpect(jsonPath("$.[*].receiveDate").value(hasItem(DEFAULT_RECEIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].r100").value(hasItem(DEFAULT_R_100.intValue())))
            .andExpect(jsonPath("$.[*].r500").value(hasItem(DEFAULT_R_500.intValue())))
            .andExpect(jsonPath("$.[*].r1000").value(hasItem(DEFAULT_R_1000.intValue())))
            .andExpect(jsonPath("$.[*].recjectedAmount").value(hasItem(DEFAULT_RECJECTED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].f100").value(hasItem(DEFAULT_F_100.intValue())))
            .andExpect(jsonPath("$.[*].f500").value(hasItem(DEFAULT_F_500.intValue())))
            .andExpect(jsonPath("$.[*].f1000").value(hasItem(DEFAULT_F_1000.intValue())))
            .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP.toString())));
    }

    @Test
    @Transactional
    public void getCashReceive() throws Exception {
        // Initialize the database
        cashReceiveRepository.saveAndFlush(cashReceive);

        // Get the cashReceive
        restCashReceiveMockMvc.perform(get("/api/cash-receives/{id}", cashReceive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cashReceive.getId().intValue()))
            .andExpect(jsonPath("$.receiveDate").value(DEFAULT_RECEIVE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER.toString()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()))
            .andExpect(jsonPath("$.r100").value(DEFAULT_R_100.intValue()))
            .andExpect(jsonPath("$.r500").value(DEFAULT_R_500.intValue()))
            .andExpect(jsonPath("$.r1000").value(DEFAULT_R_1000.intValue()))
            .andExpect(jsonPath("$.recjectedAmount").value(DEFAULT_RECJECTED_AMOUNT.intValue()))
            .andExpect(jsonPath("$.f100").value(DEFAULT_F_100.intValue()))
            .andExpect(jsonPath("$.f500").value(DEFAULT_F_500.intValue()))
            .andExpect(jsonPath("$.f1000").value(DEFAULT_F_1000.intValue()))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCashReceive() throws Exception {
        // Get the cashReceive
        restCashReceiveMockMvc.perform(get("/api/cash-receives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashReceive() throws Exception {
        // Initialize the database
        cashReceiveRepository.saveAndFlush(cashReceive);
        int databaseSizeBeforeUpdate = cashReceiveRepository.findAll().size();

        // Update the cashReceive
        CashReceive updatedCashReceive = cashReceiveRepository.findOne(cashReceive.getId());
        // Disconnect from session so that the updates on updatedCashReceive are not directly saved in db
        em.detach(updatedCashReceive);
        updatedCashReceive
            .receiveDate(UPDATED_RECEIVE_DATE)
            .amount(UPDATED_AMOUNT)
            .userId(UPDATED_USER_ID)
            .remarks(UPDATED_REMARKS)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .tag(UPDATED_TAG)
            .r100(UPDATED_R_100)
            .r500(UPDATED_R_500)
            .r1000(UPDATED_R_1000)
            .recjectedAmount(UPDATED_RECJECTED_AMOUNT)
            .f100(UPDATED_F_100)
            .f500(UPDATED_F_500)
            .f1000(UPDATED_F_1000)
            .tip(UPDATED_TIP);
        CashReceiveDTO cashReceiveDTO = cashReceiveMapper.toDto(updatedCashReceive);

        restCashReceiveMockMvc.perform(put("/api/cash-receives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashReceiveDTO)))
            .andExpect(status().isOk());

        // Validate the CashReceive in the database
        List<CashReceive> cashReceiveList = cashReceiveRepository.findAll();
        assertThat(cashReceiveList).hasSize(databaseSizeBeforeUpdate);
        CashReceive testCashReceive = cashReceiveList.get(cashReceiveList.size() - 1);
        assertThat(testCashReceive.getReceiveDate()).isEqualTo(UPDATED_RECEIVE_DATE);
        assertThat(testCashReceive.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCashReceive.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCashReceive.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testCashReceive.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testCashReceive.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testCashReceive.getr100()).isEqualTo(UPDATED_R_100);
        assertThat(testCashReceive.getr500()).isEqualTo(UPDATED_R_500);
        assertThat(testCashReceive.getr1000()).isEqualTo(UPDATED_R_1000);
        assertThat(testCashReceive.getRecjectedAmount()).isEqualTo(UPDATED_RECJECTED_AMOUNT);
        assertThat(testCashReceive.getf100()).isEqualTo(UPDATED_F_100);
        assertThat(testCashReceive.getf500()).isEqualTo(UPDATED_F_500);
        assertThat(testCashReceive.getf1000()).isEqualTo(UPDATED_F_1000);
        assertThat(testCashReceive.getTip()).isEqualTo(UPDATED_TIP);
    }

    @Test
    @Transactional
    public void updateNonExistingCashReceive() throws Exception {
        int databaseSizeBeforeUpdate = cashReceiveRepository.findAll().size();

        // Create the CashReceive
        CashReceiveDTO cashReceiveDTO = cashReceiveMapper.toDto(cashReceive);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCashReceiveMockMvc.perform(put("/api/cash-receives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashReceiveDTO)))
            .andExpect(status().isCreated());

        // Validate the CashReceive in the database
        List<CashReceive> cashReceiveList = cashReceiveRepository.findAll();
        assertThat(cashReceiveList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCashReceive() throws Exception {
        // Initialize the database
        cashReceiveRepository.saveAndFlush(cashReceive);
        int databaseSizeBeforeDelete = cashReceiveRepository.findAll().size();

        // Get the cashReceive
        restCashReceiveMockMvc.perform(delete("/api/cash-receives/{id}", cashReceive.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CashReceive> cashReceiveList = cashReceiveRepository.findAll();
        assertThat(cashReceiveList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashReceive.class);
        CashReceive cashReceive1 = new CashReceive();
        cashReceive1.setId(1L);
        CashReceive cashReceive2 = new CashReceive();
        cashReceive2.setId(cashReceive1.getId());
        assertThat(cashReceive1).isEqualTo(cashReceive2);
        cashReceive2.setId(2L);
        assertThat(cashReceive1).isNotEqualTo(cashReceive2);
        cashReceive1.setId(null);
        assertThat(cashReceive1).isNotEqualTo(cashReceive2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashReceiveDTO.class);
        CashReceiveDTO cashReceiveDTO1 = new CashReceiveDTO();
        cashReceiveDTO1.setId(1L);
        CashReceiveDTO cashReceiveDTO2 = new CashReceiveDTO();
        assertThat(cashReceiveDTO1).isNotEqualTo(cashReceiveDTO2);
        cashReceiveDTO2.setId(cashReceiveDTO1.getId());
        assertThat(cashReceiveDTO1).isEqualTo(cashReceiveDTO2);
        cashReceiveDTO2.setId(2L);
        assertThat(cashReceiveDTO1).isNotEqualTo(cashReceiveDTO2);
        cashReceiveDTO1.setId(null);
        assertThat(cashReceiveDTO1).isNotEqualTo(cashReceiveDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cashReceiveMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cashReceiveMapper.fromId(null)).isNull();
    }
}

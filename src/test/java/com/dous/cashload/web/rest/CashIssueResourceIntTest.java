package com.dous.cashload.web.rest;

import com.dous.cashload.CashloadApp;

import com.dous.cashload.domain.CashIssue;
import com.dous.cashload.repository.CashIssueRepository;
import com.dous.cashload.service.CashIssueService;
import com.dous.cashload.service.dto.CashIssueDTO;
import com.dous.cashload.service.mapper.CashIssueMapper;
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
 * Test class for the CashIssueResource REST controller.
 *
 * @see CashIssueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashloadApp.class)
public class CashIssueResourceIntTest {

    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_I_100 = new BigDecimal(1);
    private static final BigDecimal UPDATED_I_100 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_I_500 = new BigDecimal(1);
    private static final BigDecimal UPDATED_I_500 = new BigDecimal(2);

    private static final BigDecimal DEFAULT_I_1000 = new BigDecimal(1);
    private static final BigDecimal UPDATED_I_1000 = new BigDecimal(2);

    private static final String DEFAULT_TIP = "AAAAAAAAAA";
    private static final String UPDATED_TIP = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private CashIssueRepository cashIssueRepository;

    @Autowired
    private CashIssueMapper cashIssueMapper;

    @Autowired
    private CashIssueService cashIssueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCashIssueMockMvc;

    private CashIssue cashIssue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CashIssueResource cashIssueResource = new CashIssueResource(cashIssueService);
        this.restCashIssueMockMvc = MockMvcBuilders.standaloneSetup(cashIssueResource)
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
    public static CashIssue createEntity(EntityManager em) {
        CashIssue cashIssue = new CashIssue()
            .issueDate(DEFAULT_ISSUE_DATE)
            .amount(DEFAULT_AMOUNT)
            .i100(DEFAULT_I_100)
            .i500(DEFAULT_I_500)
            .i1000(DEFAULT_I_1000)
            .tip(DEFAULT_TIP)
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .tag(DEFAULT_TAG)
            .userId(DEFAULT_USER_ID)
            .remarks(DEFAULT_REMARKS);
        return cashIssue;
    }

    @Before
    public void initTest() {
        cashIssue = createEntity(em);
    }

    @Test
    @Transactional
    public void createCashIssue() throws Exception {
        int databaseSizeBeforeCreate = cashIssueRepository.findAll().size();

        // Create the CashIssue
        CashIssueDTO cashIssueDTO = cashIssueMapper.toDto(cashIssue);
        restCashIssueMockMvc.perform(post("/api/cash-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashIssueDTO)))
            .andExpect(status().isCreated());

        // Validate the CashIssue in the database
        List<CashIssue> cashIssueList = cashIssueRepository.findAll();
        assertThat(cashIssueList).hasSize(databaseSizeBeforeCreate + 1);
        CashIssue testCashIssue = cashIssueList.get(cashIssueList.size() - 1);
        assertThat(testCashIssue.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testCashIssue.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCashIssue.geti100()).isEqualTo(DEFAULT_I_100);
        assertThat(testCashIssue.geti500()).isEqualTo(DEFAULT_I_500);
        assertThat(testCashIssue.geti1000()).isEqualTo(DEFAULT_I_1000);
        assertThat(testCashIssue.getTip()).isEqualTo(DEFAULT_TIP);
        assertThat(testCashIssue.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testCashIssue.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testCashIssue.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCashIssue.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createCashIssueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cashIssueRepository.findAll().size();

        // Create the CashIssue with an existing ID
        cashIssue.setId(1L);
        CashIssueDTO cashIssueDTO = cashIssueMapper.toDto(cashIssue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCashIssueMockMvc.perform(post("/api/cash-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashIssueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CashIssue in the database
        List<CashIssue> cashIssueList = cashIssueRepository.findAll();
        assertThat(cashIssueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCashIssues() throws Exception {
        // Initialize the database
        cashIssueRepository.saveAndFlush(cashIssue);

        // Get all the cashIssueList
        restCashIssueMockMvc.perform(get("/api/cash-issues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cashIssue.getId().intValue())))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].i100").value(hasItem(DEFAULT_I_100.intValue())))
            .andExpect(jsonPath("$.[*].i500").value(hasItem(DEFAULT_I_500.intValue())))
            .andExpect(jsonPath("$.[*].i1000").value(hasItem(DEFAULT_I_1000.intValue())))
            .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP.toString())))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS.toString())));
    }

    @Test
    @Transactional
    public void getCashIssue() throws Exception {
        // Initialize the database
        cashIssueRepository.saveAndFlush(cashIssue);

        // Get the cashIssue
        restCashIssueMockMvc.perform(get("/api/cash-issues/{id}", cashIssue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cashIssue.getId().intValue()))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.i100").value(DEFAULT_I_100.intValue()))
            .andExpect(jsonPath("$.i500").value(DEFAULT_I_500.intValue()))
            .andExpect(jsonPath("$.i1000").value(DEFAULT_I_1000.intValue()))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP.toString()))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER.toString()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCashIssue() throws Exception {
        // Get the cashIssue
        restCashIssueMockMvc.perform(get("/api/cash-issues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCashIssue() throws Exception {
        // Initialize the database
        cashIssueRepository.saveAndFlush(cashIssue);
        int databaseSizeBeforeUpdate = cashIssueRepository.findAll().size();

        // Update the cashIssue
        CashIssue updatedCashIssue = cashIssueRepository.findOne(cashIssue.getId());
        // Disconnect from session so that the updates on updatedCashIssue are not directly saved in db
        em.detach(updatedCashIssue);
        updatedCashIssue
            .issueDate(UPDATED_ISSUE_DATE)
            .amount(UPDATED_AMOUNT)
            .i100(UPDATED_I_100)
            .i500(UPDATED_I_500)
            .i1000(UPDATED_I_1000)
            .tip(UPDATED_TIP)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .tag(UPDATED_TAG)
            .userId(UPDATED_USER_ID)
            .remarks(UPDATED_REMARKS);
        CashIssueDTO cashIssueDTO = cashIssueMapper.toDto(updatedCashIssue);

        restCashIssueMockMvc.perform(put("/api/cash-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashIssueDTO)))
            .andExpect(status().isOk());

        // Validate the CashIssue in the database
        List<CashIssue> cashIssueList = cashIssueRepository.findAll();
        assertThat(cashIssueList).hasSize(databaseSizeBeforeUpdate);
        CashIssue testCashIssue = cashIssueList.get(cashIssueList.size() - 1);
        assertThat(testCashIssue.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testCashIssue.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCashIssue.geti100()).isEqualTo(UPDATED_I_100);
        assertThat(testCashIssue.geti500()).isEqualTo(UPDATED_I_500);
        assertThat(testCashIssue.geti1000()).isEqualTo(UPDATED_I_1000);
        assertThat(testCashIssue.getTip()).isEqualTo(UPDATED_TIP);
        assertThat(testCashIssue.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testCashIssue.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testCashIssue.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCashIssue.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingCashIssue() throws Exception {
        int databaseSizeBeforeUpdate = cashIssueRepository.findAll().size();

        // Create the CashIssue
        CashIssueDTO cashIssueDTO = cashIssueMapper.toDto(cashIssue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCashIssueMockMvc.perform(put("/api/cash-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cashIssueDTO)))
            .andExpect(status().isCreated());

        // Validate the CashIssue in the database
        List<CashIssue> cashIssueList = cashIssueRepository.findAll();
        assertThat(cashIssueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCashIssue() throws Exception {
        // Initialize the database
        cashIssueRepository.saveAndFlush(cashIssue);
        int databaseSizeBeforeDelete = cashIssueRepository.findAll().size();

        // Get the cashIssue
        restCashIssueMockMvc.perform(delete("/api/cash-issues/{id}", cashIssue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CashIssue> cashIssueList = cashIssueRepository.findAll();
        assertThat(cashIssueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashIssue.class);
        CashIssue cashIssue1 = new CashIssue();
        cashIssue1.setId(1L);
        CashIssue cashIssue2 = new CashIssue();
        cashIssue2.setId(cashIssue1.getId());
        assertThat(cashIssue1).isEqualTo(cashIssue2);
        cashIssue2.setId(2L);
        assertThat(cashIssue1).isNotEqualTo(cashIssue2);
        cashIssue1.setId(null);
        assertThat(cashIssue1).isNotEqualTo(cashIssue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CashIssueDTO.class);
        CashIssueDTO cashIssueDTO1 = new CashIssueDTO();
        cashIssueDTO1.setId(1L);
        CashIssueDTO cashIssueDTO2 = new CashIssueDTO();
        assertThat(cashIssueDTO1).isNotEqualTo(cashIssueDTO2);
        cashIssueDTO2.setId(cashIssueDTO1.getId());
        assertThat(cashIssueDTO1).isEqualTo(cashIssueDTO2);
        cashIssueDTO2.setId(2L);
        assertThat(cashIssueDTO1).isNotEqualTo(cashIssueDTO2);
        cashIssueDTO1.setId(null);
        assertThat(cashIssueDTO1).isNotEqualTo(cashIssueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cashIssueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cashIssueMapper.fromId(null)).isNull();
    }
}

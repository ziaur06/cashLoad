package com.dous.cashload.web.rest;

import com.dous.cashload.CashloadApp;

import com.dous.cashload.domain.AtmInformation;
import com.dous.cashload.repository.AtmInformationRepository;
import com.dous.cashload.service.AtmInformationService;
import com.dous.cashload.service.dto.AtmInformationDTO;
import com.dous.cashload.service.mapper.AtmInformationMapper;
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
import java.util.List;

import static com.dous.cashload.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dous.cashload.domain.enumeration.Status;
/**
 * Test class for the AtmInformationResource REST controller.
 *
 * @see AtmInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashloadApp.class)
public class AtmInformationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private AtmInformationRepository atmInformationRepository;

    @Autowired
    private AtmInformationMapper atmInformationMapper;

    @Autowired
    private AtmInformationService atmInformationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAtmInformationMockMvc;

    private AtmInformation atmInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AtmInformationResource atmInformationResource = new AtmInformationResource(atmInformationService);
        this.restAtmInformationMockMvc = MockMvcBuilders.standaloneSetup(atmInformationResource)
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
    public static AtmInformation createEntity(EntityManager em) {
        AtmInformation atmInformation = new AtmInformation()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .model(DEFAULT_MODEL)
            .status(DEFAULT_STATUS);
        return atmInformation;
    }

    @Before
    public void initTest() {
        atmInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtmInformation() throws Exception {
        int databaseSizeBeforeCreate = atmInformationRepository.findAll().size();

        // Create the AtmInformation
        AtmInformationDTO atmInformationDTO = atmInformationMapper.toDto(atmInformation);
        restAtmInformationMockMvc.perform(post("/api/atm-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atmInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the AtmInformation in the database
        List<AtmInformation> atmInformationList = atmInformationRepository.findAll();
        assertThat(atmInformationList).hasSize(databaseSizeBeforeCreate + 1);
        AtmInformation testAtmInformation = atmInformationList.get(atmInformationList.size() - 1);
        assertThat(testAtmInformation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAtmInformation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAtmInformation.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testAtmInformation.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createAtmInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atmInformationRepository.findAll().size();

        // Create the AtmInformation with an existing ID
        atmInformation.setId(1L);
        AtmInformationDTO atmInformationDTO = atmInformationMapper.toDto(atmInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtmInformationMockMvc.perform(post("/api/atm-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atmInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AtmInformation in the database
        List<AtmInformation> atmInformationList = atmInformationRepository.findAll();
        assertThat(atmInformationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = atmInformationRepository.findAll().size();
        // set the field null
        atmInformation.setName(null);

        // Create the AtmInformation, which fails.
        AtmInformationDTO atmInformationDTO = atmInformationMapper.toDto(atmInformation);

        restAtmInformationMockMvc.perform(post("/api/atm-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atmInformationDTO)))
            .andExpect(status().isBadRequest());

        List<AtmInformation> atmInformationList = atmInformationRepository.findAll();
        assertThat(atmInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = atmInformationRepository.findAll().size();
        // set the field null
        atmInformation.setCode(null);

        // Create the AtmInformation, which fails.
        AtmInformationDTO atmInformationDTO = atmInformationMapper.toDto(atmInformation);

        restAtmInformationMockMvc.perform(post("/api/atm-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atmInformationDTO)))
            .andExpect(status().isBadRequest());

        List<AtmInformation> atmInformationList = atmInformationRepository.findAll();
        assertThat(atmInformationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAtmInformations() throws Exception {
        // Initialize the database
        atmInformationRepository.saveAndFlush(atmInformation);

        // Get all the atmInformationList
        restAtmInformationMockMvc.perform(get("/api/atm-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atmInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getAtmInformation() throws Exception {
        // Initialize the database
        atmInformationRepository.saveAndFlush(atmInformation);

        // Get the atmInformation
        restAtmInformationMockMvc.perform(get("/api/atm-informations/{id}", atmInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(atmInformation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAtmInformation() throws Exception {
        // Get the atmInformation
        restAtmInformationMockMvc.perform(get("/api/atm-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtmInformation() throws Exception {
        // Initialize the database
        atmInformationRepository.saveAndFlush(atmInformation);
        int databaseSizeBeforeUpdate = atmInformationRepository.findAll().size();

        // Update the atmInformation
        AtmInformation updatedAtmInformation = atmInformationRepository.findOne(atmInformation.getId());
        // Disconnect from session so that the updates on updatedAtmInformation are not directly saved in db
        em.detach(updatedAtmInformation);
        updatedAtmInformation
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .model(UPDATED_MODEL)
            .status(UPDATED_STATUS);
        AtmInformationDTO atmInformationDTO = atmInformationMapper.toDto(updatedAtmInformation);

        restAtmInformationMockMvc.perform(put("/api/atm-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atmInformationDTO)))
            .andExpect(status().isOk());

        // Validate the AtmInformation in the database
        List<AtmInformation> atmInformationList = atmInformationRepository.findAll();
        assertThat(atmInformationList).hasSize(databaseSizeBeforeUpdate);
        AtmInformation testAtmInformation = atmInformationList.get(atmInformationList.size() - 1);
        assertThat(testAtmInformation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAtmInformation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAtmInformation.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testAtmInformation.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAtmInformation() throws Exception {
        int databaseSizeBeforeUpdate = atmInformationRepository.findAll().size();

        // Create the AtmInformation
        AtmInformationDTO atmInformationDTO = atmInformationMapper.toDto(atmInformation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAtmInformationMockMvc.perform(put("/api/atm-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atmInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the AtmInformation in the database
        List<AtmInformation> atmInformationList = atmInformationRepository.findAll();
        assertThat(atmInformationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAtmInformation() throws Exception {
        // Initialize the database
        atmInformationRepository.saveAndFlush(atmInformation);
        int databaseSizeBeforeDelete = atmInformationRepository.findAll().size();

        // Get the atmInformation
        restAtmInformationMockMvc.perform(delete("/api/atm-informations/{id}", atmInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AtmInformation> atmInformationList = atmInformationRepository.findAll();
        assertThat(atmInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtmInformation.class);
        AtmInformation atmInformation1 = new AtmInformation();
        atmInformation1.setId(1L);
        AtmInformation atmInformation2 = new AtmInformation();
        atmInformation2.setId(atmInformation1.getId());
        assertThat(atmInformation1).isEqualTo(atmInformation2);
        atmInformation2.setId(2L);
        assertThat(atmInformation1).isNotEqualTo(atmInformation2);
        atmInformation1.setId(null);
        assertThat(atmInformation1).isNotEqualTo(atmInformation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtmInformationDTO.class);
        AtmInformationDTO atmInformationDTO1 = new AtmInformationDTO();
        atmInformationDTO1.setId(1L);
        AtmInformationDTO atmInformationDTO2 = new AtmInformationDTO();
        assertThat(atmInformationDTO1).isNotEqualTo(atmInformationDTO2);
        atmInformationDTO2.setId(atmInformationDTO1.getId());
        assertThat(atmInformationDTO1).isEqualTo(atmInformationDTO2);
        atmInformationDTO2.setId(2L);
        assertThat(atmInformationDTO1).isNotEqualTo(atmInformationDTO2);
        atmInformationDTO1.setId(null);
        assertThat(atmInformationDTO1).isNotEqualTo(atmInformationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(atmInformationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(atmInformationMapper.fromId(null)).isNull();
    }
}

package com.dous.cashload.web.rest;

import com.dous.cashload.CashloadApp;

import com.dous.cashload.domain.UserInformation;
import com.dous.cashload.repository.UserInformationRepository;
import com.dous.cashload.service.UserInformationService;
import com.dous.cashload.service.dto.UserInformationDTO;
import com.dous.cashload.service.mapper.UserInformationMapper;
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
 * Test class for the UserInformationResource REST controller.
 *
 * @see UserInformationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CashloadApp.class)
public class UserInformationResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    @Autowired
    private UserInformationRepository userInformationRepository;

    @Autowired
    private UserInformationMapper userInformationMapper;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserInformationMockMvc;

    private UserInformation userInformation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserInformationResource userInformationResource = new UserInformationResource(userInformationService);
        this.restUserInformationMockMvc = MockMvcBuilders.standaloneSetup(userInformationResource)
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
    public static UserInformation createEntity(EntityManager em) {
        UserInformation userInformation = new UserInformation()
            .userId(DEFAULT_USER_ID)
            .userName(DEFAULT_USER_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .password(DEFAULT_PASSWORD)
            .status(DEFAULT_STATUS);
        return userInformation;
    }

    @Before
    public void initTest() {
        userInformation = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserInformation() throws Exception {
        int databaseSizeBeforeCreate = userInformationRepository.findAll().size();

        // Create the UserInformation
        UserInformationDTO userInformationDTO = userInformationMapper.toDto(userInformation);
        restUserInformationMockMvc.perform(post("/api/user-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the UserInformation in the database
        List<UserInformation> userInformationList = userInformationRepository.findAll();
        assertThat(userInformationList).hasSize(databaseSizeBeforeCreate + 1);
        UserInformation testUserInformation = userInformationList.get(userInformationList.size() - 1);
        assertThat(testUserInformation.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserInformation.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testUserInformation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUserInformation.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testUserInformation.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUserInformation.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createUserInformationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userInformationRepository.findAll().size();

        // Create the UserInformation with an existing ID
        userInformation.setId(1L);
        UserInformationDTO userInformationDTO = userInformationMapper.toDto(userInformation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInformationMockMvc.perform(post("/api/user-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInformationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInformation in the database
        List<UserInformation> userInformationList = userInformationRepository.findAll();
        assertThat(userInformationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserInformations() throws Exception {
        // Initialize the database
        userInformationRepository.saveAndFlush(userInformation);

        // Get all the userInformationList
        restUserInformationMockMvc.perform(get("/api/user-informations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInformation.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getUserInformation() throws Exception {
        // Initialize the database
        userInformationRepository.saveAndFlush(userInformation);

        // Get the userInformation
        restUserInformationMockMvc.perform(get("/api/user-informations/{id}", userInformation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userInformation.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserInformation() throws Exception {
        // Get the userInformation
        restUserInformationMockMvc.perform(get("/api/user-informations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserInformation() throws Exception {
        // Initialize the database
        userInformationRepository.saveAndFlush(userInformation);
        int databaseSizeBeforeUpdate = userInformationRepository.findAll().size();

        // Update the userInformation
        UserInformation updatedUserInformation = userInformationRepository.findOne(userInformation.getId());
        // Disconnect from session so that the updates on updatedUserInformation are not directly saved in db
        em.detach(updatedUserInformation);
        updatedUserInformation
            .userId(UPDATED_USER_ID)
            .userName(UPDATED_USER_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .password(UPDATED_PASSWORD)
            .status(UPDATED_STATUS);
        UserInformationDTO userInformationDTO = userInformationMapper.toDto(updatedUserInformation);

        restUserInformationMockMvc.perform(put("/api/user-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInformationDTO)))
            .andExpect(status().isOk());

        // Validate the UserInformation in the database
        List<UserInformation> userInformationList = userInformationRepository.findAll();
        assertThat(userInformationList).hasSize(databaseSizeBeforeUpdate);
        UserInformation testUserInformation = userInformationList.get(userInformationList.size() - 1);
        assertThat(testUserInformation.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserInformation.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testUserInformation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUserInformation.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testUserInformation.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUserInformation.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingUserInformation() throws Exception {
        int databaseSizeBeforeUpdate = userInformationRepository.findAll().size();

        // Create the UserInformation
        UserInformationDTO userInformationDTO = userInformationMapper.toDto(userInformation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserInformationMockMvc.perform(put("/api/user-informations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInformationDTO)))
            .andExpect(status().isCreated());

        // Validate the UserInformation in the database
        List<UserInformation> userInformationList = userInformationRepository.findAll();
        assertThat(userInformationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserInformation() throws Exception {
        // Initialize the database
        userInformationRepository.saveAndFlush(userInformation);
        int databaseSizeBeforeDelete = userInformationRepository.findAll().size();

        // Get the userInformation
        restUserInformationMockMvc.perform(delete("/api/user-informations/{id}", userInformation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserInformation> userInformationList = userInformationRepository.findAll();
        assertThat(userInformationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInformation.class);
        UserInformation userInformation1 = new UserInformation();
        userInformation1.setId(1L);
        UserInformation userInformation2 = new UserInformation();
        userInformation2.setId(userInformation1.getId());
        assertThat(userInformation1).isEqualTo(userInformation2);
        userInformation2.setId(2L);
        assertThat(userInformation1).isNotEqualTo(userInformation2);
        userInformation1.setId(null);
        assertThat(userInformation1).isNotEqualTo(userInformation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInformationDTO.class);
        UserInformationDTO userInformationDTO1 = new UserInformationDTO();
        userInformationDTO1.setId(1L);
        UserInformationDTO userInformationDTO2 = new UserInformationDTO();
        assertThat(userInformationDTO1).isNotEqualTo(userInformationDTO2);
        userInformationDTO2.setId(userInformationDTO1.getId());
        assertThat(userInformationDTO1).isEqualTo(userInformationDTO2);
        userInformationDTO2.setId(2L);
        assertThat(userInformationDTO1).isNotEqualTo(userInformationDTO2);
        userInformationDTO1.setId(null);
        assertThat(userInformationDTO1).isNotEqualTo(userInformationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userInformationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userInformationMapper.fromId(null)).isNull();
    }
}

package com.example.labxpert.controller;

import com.example.labxpert.dtos.PatientDto;
import com.example.labxpert.model.enums.Sexe;
import com.example.labxpert.model.Patient;
import com.example.labxpert.service.implementation.PatientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = PatientController.class)
@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientServiceImpl patientService;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient patient;
    private PatientDto patientDto;

    @BeforeEach
    public void init()
    {
        patient = new Patient();
        patient.setId(1L);
        patient.setNom("marouane");
        patient.setPrenom("mouslih");
        patient.setTel("0630011246");
        patient.setSexe(Sexe.MALE);
        patient.setAddress("address 1");
        patient.setDateNaissance(LocalDate.of(2001,8,19));
        patient.setVille("casablanca");
        patient.setDeleted(false);
        patient.setAge(21);

        patientDto = new PatientDto();
        patientDto.setId(1L);
        patientDto.setNom("marouane");
        patientDto.setPrenom("mouslih");
        patientDto.setTel("0630011246");
        patientDto.setSexe(Sexe.MALE);
        patientDto.setAddress("address 1");
        patientDto.setDateNaissance(LocalDate.of(2001,8,19));
        patientDto.setVille("casablanca");
        patient.setDeleted(false);
        patientDto.setAge(21);
    }

    @Test
    public void patientController_addPatient_ReturnPatient() throws Exception {
        given(patientService.add(ArgumentMatchers.any(PatientDto.class)))
                .willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/v1/patients/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void patientController_getAllPatient_ReturnMoreThanPatient() throws Exception
    {
        when(patientService.getAll()).thenReturn(Collections.singletonList(patientDto));

        ResultActions response = mockMvc.perform(get("/api/v1/patients")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void patientController_getByIDPatient_ReturnPatient() throws Exception {
        when(patientService.getById(1L)).thenReturn(patientDto);

        ResultActions response = mockMvc.perform(get("/api/v1/patients/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void patientController_deletePatient_ReturnNothing() throws Exception {
        doNothing().when(patientService).delete(1L);

        ResultActions response = mockMvc.perform(delete("/api/v1/patients/1/delete")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void patientController_updatePatient_ReturnPatient() throws Exception {
        when(patientService.update(1L, patientDto)).thenReturn(patientDto);

        ResultActions response = mockMvc.perform(put("/api/v1/patients/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
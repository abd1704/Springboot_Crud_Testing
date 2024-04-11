package com.example.demo.controller;

import com.example.demo.entity.Patient;
import com.example.demo.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @MockBean
    private PatientService patientService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Patient patient;
    private Patient anotherPatient;

    @BeforeEach
    void init() {
        patient = new Patient();
        patient.setName("John");
        patient.setAge(30);
        patient.setAllergies("Pollen");
        patient.setId(1L);

        anotherPatient = new Patient();
        anotherPatient.setName("Alice");
        anotherPatient.setAge(25);
        anotherPatient.setAllergies("Peanuts");
        anotherPatient.setId(2L);
    }

    @Test
    void createNewPatient() throws Exception {
        when(patientService.savePatient(any(Patient.class))).thenReturn(patient);

        this.mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(jsonPath("$.name").value(patient.getName()));
    }

    @Test
    void fetchAllPatients() throws Exception{
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(anotherPatient);

        when(patientService.getAllPatients()).thenReturn(patients);

        this.mockMvc.perform(get("/api/patients"))
                .andExpect(jsonPath("$.size()", is(patients.size())));
    }

    @Test
    void getPatientById() throws Exception{
        when(patientService.getPatientById(anyLong())).thenReturn(Optional.of(patient));

        this.mockMvc.perform(get("/api/patients/{id}", 1L))
                .andExpect(jsonPath("$.name", is(patient.getName())))
                .andExpect(jsonPath("$.age", is(patient.getAge())));
    }

    @Test
    void deletePatient() throws Exception{
        doNothing().when(patientService).deletePatient(anyLong());

        this.mockMvc.perform(delete("/api/patients/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdatePatient() throws Exception{
        when(patientService.updatePatient(anyLong(), any(Patient.class))).thenReturn(patient);
        this.mockMvc.perform(put("/api/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(jsonPath("$.name", is(patient.getName())));
    }
}

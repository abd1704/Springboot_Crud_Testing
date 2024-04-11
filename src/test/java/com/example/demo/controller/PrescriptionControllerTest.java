package com.example.demo.controller;

import com.example.demo.entity.Prescription;
import com.example.demo.service.PrescriptionService;
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
public class PrescriptionControllerTest {

    @MockBean
    private PrescriptionService prescriptionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Prescription prescription;
    private Prescription anotherPrescription;

    @BeforeEach
    void init(){
        prescription = new Prescription();
        prescription.setId(1L);
        prescription.setMedication("Med1");
        prescription.setDosage("Dosage1");

        anotherPrescription = new Prescription();
        anotherPrescription.setId(2L);
        anotherPrescription.setMedication("Med2");
        anotherPrescription.setDosage("Dosage2");
    }

    @Test
    void createNewPrescription() throws Exception {
        when(prescriptionService.savePrescription(any(Prescription.class))).thenReturn(prescription);

        this.mockMvc.perform(post("/api/prescriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prescription)))
                .andExpect(jsonPath("$.medication").value(prescription.getMedication()));
    }

    @Test
    void fetchAllPrescriptions() throws Exception{
        List<Prescription> list = new ArrayList<>();
        list.add(prescription);
        list.add(anotherPrescription);

        when(prescriptionService.getAllPrescriptions()).thenReturn(list);

        this.mockMvc.perform(get("/api/prescriptions"))
                .andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void getPrescriptionById() throws Exception{
        when(prescriptionService.getPrescriptionById(anyLong())).thenReturn(Optional.of(prescription));

        this.mockMvc.perform(get("/api/prescriptions/{id}",1L))
                .andExpect(jsonPath("$.medication",is(prescription.getMedication())))
                .andExpect(jsonPath("$.dosage",is(prescription.getDosage())));
    }

    @Test
    void deletePrescription() throws Exception{
        doNothing().when(prescriptionService).deletePrescription(anyLong());

        this.mockMvc.perform(delete("/api/prescriptions/{id}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdatePrescription() throws Exception{
        when(prescriptionService.updatePrescription(anyLong(), any(Prescription.class))).thenReturn(prescription);

        this.mockMvc.perform(put("/api/prescriptions/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prescription)))
                .andExpect(jsonPath("$.medication", is(prescription.getMedication())));
    }
}

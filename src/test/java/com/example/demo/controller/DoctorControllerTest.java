package com.example.demo.controller;

import com.example.demo.entity.Doctor;
import com.example.demo.service.DoctorService;
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
public class DoctorControllerTest {

    @MockBean
    private DoctorService doctorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Doctor doctor;
    private Doctor doctors;

    @BeforeEach
    void init(){
        doctor = new Doctor();
        doctor.setName("abhishek");
        doctor.setQualification("12th");
        doctor.setId(1L);

        doctors = new Doctor();
        doctors.setName("abhishekk");
        doctors.setQualification("12th");

    }

    @Test
    void createNewDoc() throws Exception {
        when(doctorService.saveDoctor(any(Doctor.class))).thenReturn(doctor);

        this.mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(jsonPath("$.name").value(doctor.getName()));
    }

    @Test
    void fetchAllDoc() throws Exception{

        List<Doctor> list = new ArrayList<>();
        list.add(doctors);
        list.add(doctor);

        when(doctorService.getAllDoctors()).thenReturn(list);

        this.mockMvc.perform(get("/api/doctors"))
                .andExpect(jsonPath("$.size()", is(list.size())));
    }

    @Test
    void getDocById() throws Exception{
        when(doctorService.getDoctorById(anyLong())).thenReturn(Optional.of(doctor));

        this.mockMvc.perform(get("/api/doctors/{id}",1L))
                .andExpect(jsonPath("$.name",is(doctor.getName())))
                .andExpect(jsonPath("$.qualification",is(doctor.getQualification())));
    }

    @Test
    void deleteDoc() throws Exception{
        doNothing().when(doctorService).deleteDoctor(anyLong());

        this.mockMvc.perform(delete("/api/doctors/{id}",1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdateDoc() throws Exception{
        when(doctorService.updateDoctor(anyLong(),any(Doctor.class))).thenReturn(doctor);
        this.mockMvc.perform(put("/api/doctors/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(jsonPath("$.name",is(doctor.getName())));
    }
}

package com.example.demo.service;

import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

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
    void savePatient() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        Patient newPatient = patientService.savePatient(patient);
        assertNotNull(newPatient);
        assertThat(newPatient.getName()).isEqualTo("John");
    }

    @Test
    void getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(anotherPatient);

        when(patientRepository.findAll()).thenReturn(patients);
        List<Patient> fetchedPatients = patientService.getAllPatients();

        assertEquals(2, fetchedPatients.size());
        assertNotNull(fetchedPatients);
    }

    @Test
    void testGetPatientById() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        Optional<Patient> patientOptional = patientService.getPatientById(1L);

        assertTrue(patientOptional.isPresent());

        Patient fetchedPatient = patientOptional.get();

        assertNotNull(fetchedPatient);
        assertThat(fetchedPatient.getId()).isEqualTo(1L);
    }

    @Test
    void updatePatient() {
        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        patient.setName("Updated Name");

        Patient updatedPatient = patientService.updatePatient(1L, patient);

        assertNotNull(updatedPatient);
        assertEquals("Updated Name", updatedPatient.getName());
    }

    @Test
    void deletePatient() {
        patientService.deletePatient(1L);
        verify(patientRepository, times(1)).deleteById(anyLong());
    }
}

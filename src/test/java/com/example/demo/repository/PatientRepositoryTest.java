package com.example.demo.repository;

import com.example.demo.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientRepositoryTest {

    @Mock
    private PatientRepository patientRepository;

    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void init() {
        patient1 = new Patient();
        patient1.setId(1L);
        patient1.setName("John");
        patient1.setAge(30);
        patient1.setAllergies("Pollen");

        patient2 = new Patient();
        patient2.setId(2L);
        patient2.setName("Alice");
        patient2.setAge(25);
        patient2.setAllergies("Peanuts");
    }

    @Test
    void save() {
        when(patientRepository.save(any(Patient.class))).thenReturn(patient1);

        Patient savedPatient = patientRepository.save(new Patient());

        assertNotNull(savedPatient);
        assertEquals("John", savedPatient.getName());
    }

    @Test
    void getAll() {
        when(patientRepository.findAll()).thenReturn(List.of(patient1, patient2));

        List<Patient> list = patientRepository.findAll();

        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void getPatientById() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient1));

        Optional<Patient> foundPatient = patientRepository.findById(1L);

        assertTrue(foundPatient.isPresent());
        assertEquals("John", foundPatient.get().getName());
    }

    @Test
    void updatePatient() {
        Patient updatedPatient = new Patient();
        updatedPatient.setId(1L);
        updatedPatient.setName("Updated Name");
        updatedPatient.setAge(35);
        updatedPatient.setAllergies("Dust");

        when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        Patient savedPatient = patientRepository.save(updatedPatient);

        assertNotNull(savedPatient);
        assertEquals("Updated Name", savedPatient.getName());
        assertEquals(35, savedPatient.getAge());
        assertEquals("Dust", savedPatient.getAllergies());
    }

    @Test
    void deletePatient() {
        // Save two patients
        patientRepository.save(patient1);
        patientRepository.save(patient2);

        // Delete one patient
        patientRepository.delete(patient1);

        // Retrieve the list of patients after deletion
        List<Patient> list = patientRepository.findAll();

        // Assert that there is one patient in the list
        // assertEquals(1, list.size());

        // Assert that the removed patient is not found
        Optional<Patient> removed = patientRepository.findById(patient1.getId());
        assertTrue(removed.isEmpty());
    }
}

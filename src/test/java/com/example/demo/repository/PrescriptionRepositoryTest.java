package com.example.demo.repository;

import com.example.demo.entity.Prescription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrescriptionRepositoryTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    private Prescription prescription;
    private Prescription anotherPrescription;

    @BeforeEach
    void init() {
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
    void savePrescription() {
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(prescription);

        Prescription savedPrescription = prescriptionRepository.save(new Prescription());

        assertNotNull(savedPrescription);
        assertEquals("Med1", savedPrescription.getMedication());
    }

    @Test
    void getAllPrescriptions() {
        when(prescriptionRepository.findAll()).thenReturn(List.of(prescription, anotherPrescription));

        List<Prescription> prescriptions = prescriptionRepository.findAll();

        assertNotNull(prescriptions);
        assertEquals(2, prescriptions.size());
    }

    @Test
    void getPrescriptionById() {
        when(prescriptionRepository.findById(1L)).thenReturn(Optional.of(prescription));

        Optional<Prescription> foundPrescription = prescriptionRepository.findById(1L);

        assertNotNull(foundPrescription);
        assertEquals("Med1", foundPrescription.get().getMedication());
    }

    @Test
    void updatePrescription() {
        Prescription updatedPrescription = new Prescription();
        updatedPrescription.setId(1L);
        updatedPrescription.setMedication("UpdatedMed");
        updatedPrescription.setDosage("UpdatedDosage");

        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(updatedPrescription);

        Prescription savedPrescription = prescriptionRepository.save(updatedPrescription);

        assertNotNull(savedPrescription);
        assertEquals("UpdatedMed", savedPrescription.getMedication());
        assertEquals("UpdatedDosage", savedPrescription.getDosage());
    }

    @Test
    void deletePrescription() {
        prescriptionRepository.deleteById(1L);

        verify(prescriptionRepository, times(1)).deleteById(1L);
    }
}

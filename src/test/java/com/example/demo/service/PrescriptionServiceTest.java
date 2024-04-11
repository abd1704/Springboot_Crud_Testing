package com.example.demo.service;

import com.example.demo.entity.Prescription;
import com.example.demo.repository.PrescriptionRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrescriptionServiceTest {

    @InjectMocks
    private PrescriptionService prescriptionService;

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

        Prescription savedPrescription = prescriptionService.savePrescription(prescription);

        assertNotNull(savedPrescription);
        assertEquals("Med1", savedPrescription.getMedication());
    }

    @Test
    void getAllPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);
        prescriptions.add(anotherPrescription);

        when(prescriptionRepository.findAll()).thenReturn(prescriptions);

        List<Prescription> retrievedPrescriptions = prescriptionService.getAllPrescriptions();

        assertEquals(2, retrievedPrescriptions.size());
    }

    @Test
    void testGetPrescriptionById() {
        when(prescriptionRepository.findById(anyLong())).thenReturn(Optional.of(prescription));

        Optional<Prescription> optionalPrescription = prescriptionService.getPrescriptionById(1L);

        assertThat(optionalPrescription).isPresent();
        assertEquals("Med1", optionalPrescription.get().getMedication());
    }

    @Test
    void updatePrescription() {
        when(prescriptionRepository.findById(anyLong())).thenReturn(Optional.of(prescription));
        when(prescriptionRepository.save(any(Prescription.class))).thenReturn(prescription);

        prescription.setMedication("UpdatedMed");

        Prescription updatedPrescription = prescriptionService.updatePrescription(1L, prescription);

        assertNotNull(updatedPrescription);
        assertEquals("UpdatedMed", updatedPrescription.getMedication());
    }

    @Test
    void deletePrescription() {
        doNothing().when(prescriptionRepository).deleteById(anyLong());

        prescriptionService.deletePrescription(1L);

        verify(prescriptionRepository, times(1)).deleteById(anyLong());
    }
}

package com.example.demo.service;

import com.example.demo.entity.Prescription;
import com.example.demo.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    public Prescription updatePrescription(Long id, Prescription updatedPrescription) {
        Optional<Prescription> optionalPrescription = prescriptionRepository.findById(id);
        if (optionalPrescription.isPresent()) {
            Prescription existingPrescription = optionalPrescription.get();
            existingPrescription.setMedication(updatedPrescription.getMedication());
            existingPrescription.setDosage(updatedPrescription.getDosage());
            return prescriptionRepository.save(existingPrescription);
        } else {
            throw new IllegalArgumentException("Prescription not found with ID: " + id);
        }
    }
}

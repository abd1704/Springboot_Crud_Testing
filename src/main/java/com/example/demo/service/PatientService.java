package com.example.demo.service;

import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
    public Patient updatePatient(Long id, Patient updatedPatient) {
        // Check if the patient exists
        Optional<Patient> optionalPatient = patientRepository.findById(id);
        if (optionalPatient.isPresent()) {
            // Get the existing patient from the optional
            Patient existingPatient = optionalPatient.get();

            // Update the existing patient's fields
            existingPatient.setName(updatedPatient.getName());
            existingPatient.setAge(updatedPatient.getAge());
            existingPatient.setAllergies(updatedPatient.getAllergies());

            // Save and return the updated patient
            return patientRepository.save(existingPatient);
        } else {
            throw new IllegalArgumentException("Patient not found with ID: " + id);
        }
    }
}

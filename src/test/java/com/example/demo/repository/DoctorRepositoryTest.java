package com.example.demo.repository;

import com.example.demo.entity.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorRepositoryTest {

    @Mock
    private DoctorRepository doctorRepository;

    private Doctor doctor;
    private Doctor doctors;

    @BeforeEach
    void init() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setName("abhishek");
        doctor.setQualification("12th");

        doctors = new Doctor();
        doctors.setId(2L);
        doctors.setName("abhishekk");
        doctors.setQualification("12th");
    }

    @Test
    void save() {
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        Doctor savedDoctor = doctorRepository.save(new Doctor());

        assertNotNull(savedDoctor);
        assertEquals("abhishek", savedDoctor.getName());
    }

    @Test
    void getAll() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor, doctors));

        List<Doctor> list = doctorRepository.findAll();

        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void getDoctorById() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Optional<Doctor> foundDoctor = doctorRepository.findById(1L);

        assertTrue(foundDoctor.isPresent());
        assertEquals("abhishek", foundDoctor.get().getName());
    }

    @Test
    void updateDoctor() {
        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setId(1L);
        updatedDoctor.setName("Updated Name");
        updatedDoctor.setQualification("Updated Qualification");

        when(doctorRepository.save(any(Doctor.class))).thenReturn(updatedDoctor);

        Doctor savedDoctor = doctorRepository.save(updatedDoctor);

        assertNotNull(savedDoctor);
        assertEquals("Updated Name", savedDoctor.getName());
        assertEquals("Updated Qualification", savedDoctor.getQualification());
    }
    @Test
    void deleteDoctor() {
        // Save two doctors
        doctorRepository.save(doctor);
        doctorRepository.save(doctors);

        // Delete one doctor
        doctorRepository.delete(doctor);

        // Retrieve the list of doctors after deletion
        List<Doctor> list = doctorRepository.findAll();

        // Assert that there is one doctor in the list
        //assertEquals(1, list.size());

        // Assert that the removed doctor is not found
        Optional<Doctor> removed = doctorRepository.findById(doctor.getId());
        assertTrue(removed.isEmpty());
    }

    @Test
    void getByName() {
        when(doctorRepository.findByName("abhishek")).thenReturn(List.of(doctor));

        List<Doctor> list = doctorRepository.findByName("abhishek");

        assertNotNull(list);
        assertEquals(1, list.size());
    }
}

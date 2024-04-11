package com.example.demo.service;

import com.example.demo.entity.Doctor;
import com.example.demo.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @InjectMocks
    private  DoctorService doctorService;
    @Mock
    private DoctorRepository doctorRepository;

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
    void save(){
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        Doctor newDoctor = doctorService.saveDoctor(doctor);
        assertNotNull(newDoctor);
        assertThat(newDoctor.getName()).isEqualTo("abhishek");
    }


    @Test
    void getDoctors(){
        List<Doctor> list = new ArrayList<>();
        list.add(doctor);
        list.add(doctors);

        when(doctorRepository.findAll()).thenReturn(list);
        List<Doctor> newlist = doctorService.getAllDoctors();

        assertEquals(2,newlist.size());
        assertNotNull(newlist);

    }

    @Test
    void testGetDoctorById() {

        doctor.setId(1L); // Set the ID
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));
        Optional<Doctor> doctorOptional = doctorService.getDoctorById(1L); // Change the return type to Optional

        assertTrue(doctorOptional.isPresent()); // Check if the Optional contains a value

        Doctor existed = doctorOptional.get(); // Extract the Doctor object from the Optional

        assertNotNull(existed);
        assertThat(existed.getId()).isEqualTo(1L);
    }

    @Test
    void updateDoctor(){
        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        doctor.setName("abhi");

        Doctor updatedDoc = doctorService.updateDoctor(1L,doctor);

        assertNotNull(updatedDoc);
        assertEquals("abhi",updatedDoc.getName());
    }


    @Test
    void deleteDoc(){

//        when(doctorRepository.findById(anyLong())).thenReturn(Optional.of(doctor));
//        doNothing().when(doctorRepository).delete(any(Doctor.class));  // as it is returning nothing so do nothing

        doctorService.deleteDoctor(1L);
        verify(doctorRepository, times(1)).deleteById(anyLong()); // Pass the ID directly
    }


}

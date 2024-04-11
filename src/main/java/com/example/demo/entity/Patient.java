package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    private String allergies;

//    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
//    private List<Prescription> prescriptions;

    // Constructors, getters, and setters
    public Patient() {
    }

    public Patient(String name, int age, String allergies) {
        this.name = name;
        this.age = age;
        this.allergies = allergies;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

//    public List<Prescription> getPrescriptions() {
//        return prescriptions;
//    }

//    public void setPrescriptions(List<Prescription> prescriptions) {
//        this.prescriptions = prescriptions;
//    }
}

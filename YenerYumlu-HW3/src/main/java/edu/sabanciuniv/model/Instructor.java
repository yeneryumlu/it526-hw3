package edu.sabanciuniv.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String address;
    private String phoneNumber;
    private double salary;
    private InstructorType type;
    @OneToMany(mappedBy = "instructor")
    private List<Course> courses = new ArrayList<Course>();

    public Instructor() {
    }

    public Instructor(String name, String address, String phoneNumber, double salary, InstructorType type) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", salary=" + salary +
                ", type=" + type +
                '}';
    }
}

package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Schedule {
    @Id
    @GeneratedValue
    private long id;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule", cascade = CascadeType.ALL)
    @ManyToMany
    private List<Employee> employees;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule", cascade = CascadeType.ALL)
    @ManyToMany
    private List<Pet> pets;

    private LocalDate date;

    @ElementCollection(targetClass= EmployeeSkill.class)
    @Enumerated(EnumType.STRING) // Possibly optional (I'm not sure) but defaults to ORDINAL.
    @CollectionTable(name="schedule")
    @Column(name="activities") // Column name in Employee
    private Set<EmployeeSkill> activities;


    public Schedule() {

    }
}


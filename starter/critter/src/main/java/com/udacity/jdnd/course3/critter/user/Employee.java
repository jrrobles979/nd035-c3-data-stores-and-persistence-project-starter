package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Nationalized
    private String name;

    @ElementCollection(targetClass=EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    //@CollectionTable(name="employee")
    //@Column(name="skills")
    private Set<EmployeeSkill> skills = new HashSet<>();

    @ElementCollection(targetClass= DayOfWeek.class)
    @Enumerated(EnumType.STRING)
   // @CollectionTable(name="employee")
   // @Column(name="daysAvailable")
    private Set<DayOfWeek> daysAvailable = new HashSet<>();


    public Employee() {

    }



}

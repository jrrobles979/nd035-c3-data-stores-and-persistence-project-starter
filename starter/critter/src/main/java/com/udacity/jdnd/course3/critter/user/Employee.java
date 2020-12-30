package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private long id;
    @Nationalized
    private String name;

    @ElementCollection(targetClass=EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="employee")
    @Column(name="skills")
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass= DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="employee")
    @Column(name="daysAvailable")
    private Set<DayOfWeek> daysAvailable;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "schedule_id")
    @ManyToMany
    private List<Schedule> schedule;

    public Employee() {

    }
}

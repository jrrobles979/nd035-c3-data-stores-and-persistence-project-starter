package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pet {

    @Id
    @GeneratedValue
    private long id;
    @Enumerated(EnumType.STRING)
    private PetType type;
    @Nationalized
    private String name;
    //private long ownerId;
    @ManyToOne(fetch = FetchType.EAGER  ) //many pets can belong to one owner
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private LocalDate birthDate;
    @Column(length = 500)
    private String notes;

    //@ManyToOne(fetch = FetchType.LAZY) //many plants can belong to one delivery
    //@JoinColumn(name = "schedule_id")
    //@ManyToMany
    //private List<Schedule> schedule;

    public Pet() {

    }


}

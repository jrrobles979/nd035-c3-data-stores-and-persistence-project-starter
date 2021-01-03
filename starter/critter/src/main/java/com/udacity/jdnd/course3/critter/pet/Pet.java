package com.udacity.jdnd.course3.critter.pet;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.udacity.jdnd.course3.critter.user.Customer;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private PetType type;
    @Nationalized
    private String name;

    @ManyToOne(fetch = FetchType.LAZY  ) //many pets can belong to one owner
    @JoinColumn(name = "customer_id")
    @JsonBackReference
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

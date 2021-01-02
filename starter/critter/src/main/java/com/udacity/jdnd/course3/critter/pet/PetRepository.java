package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByCustomerId(long customerId);


}

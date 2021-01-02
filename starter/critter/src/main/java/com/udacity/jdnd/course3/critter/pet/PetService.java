package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetControllerException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;


@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Pet save(Pet pet, long idOwner) throws CustomerNotFoundException, PetControllerException {
        Optional<Customer> optionalCustomer =  customerRepository.findById(idOwner);
        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFoundException("Customer not exist, id:" + idOwner);
        }
        Customer owner = optionalCustomer.get();
        pet.setCustomer(owner);
        Pet petSaved = petRepository.save(pet);
        owner.addPet(petSaved);
        Customer ownerSaved = customerRepository.save(owner);
        return petSaved;
    }


    public Pet findPet(@PathVariable long petId) throws PetNotFoundException {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if(!optionalPet.isPresent()){
           throw new PetNotFoundException("Pet not found, id:" + petId);
        }
        return  optionalPet.get();
    }

    public List<Pet> listAll(){

        return petRepository.findAll();
    }

    public List<Pet> findPetsByOwnerId(long ownerId){

        return petRepository.findByCustomerId(ownerId);
    }





}

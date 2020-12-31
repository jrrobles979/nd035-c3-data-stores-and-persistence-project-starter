package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;


@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;


    public Pet save(Pet pet, long ownerId){
        Optional<Customer> optionalCustomer = customerRepository.findById(ownerId);
        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFoundException("Pet owner not found, id:" + ownerId);
        }
        Customer customer = optionalCustomer.get();
        pet.setCustomer(customer);
        return petRepository.save(pet);
    }

    public Pet findPet(@PathVariable long petId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if(!optionalPet.isPresent()){
           return null;
        }
        return  optionalPet.get();
    }

    public List<Pet> listAll(){
        //List<Pet> listPets = petRepository.findAll();
        //List<PetDTO> petDTOS = listPets.stream().map( pet -> convertPetToDto(pet) ).collect(Collectors.toList());
        //return listPets.stream().map( pet-> convertPetToDto(pet) ).collect(Collectors.toList());
        return petRepository.findAll();
    }

    public List<Pet> findPetsByOwnerId(long ownerId){
        //List<Pet> listPets = petRepository.findByCustomerId(ownerId);
        //return listPets.stream().map( pet-> convertPetToDto(pet) ).collect(Collectors.toList());
        return petRepository.findByCustomerId(ownerId);
    }





}

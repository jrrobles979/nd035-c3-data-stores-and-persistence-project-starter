package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.exception.PetControllerException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet petToSave = convertDtoToPet(petDTO);
        Pet petSaved = petService.save(petToSave, petDTO.getOwnerId());
        if ( petSaved == null )
            throw new PetControllerException("There was a problem saving the pet");
        return convertPetToDto(petSaved);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findPet(petId);
        if(pet == null)
            throw new PetNotFoundException("Pet not found;  id:" + petId);
        return convertPetToDto(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.listAll().stream().map( pet-> convertPetToDto(pet) ).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
       return petService.findPetsByOwnerId(ownerId).stream().map( pet-> convertPetToDto(pet) ).collect(Collectors.toList());
    }

    public PetDTO convertPetToDto(Pet pet){
        PetDTO dto = new PetDTO();
        BeanUtils.copyProperties(pet, dto);
        dto.setOwnerId(pet.getCustomer().getId());
        return dto;
    }

    public Pet convertDtoToPet(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

}

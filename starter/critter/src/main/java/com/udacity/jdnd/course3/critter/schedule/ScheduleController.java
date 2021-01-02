package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.exception.ScheduleControllerException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        List<Pet> pets = petRepository.findAllById(scheduleDTO.getPetIds());
        List<Employee> employees = employeeRepository.findAllById(scheduleDTO.getEmployeeIds());
        Schedule scheduleToSave = convertDTOToSchedule(scheduleDTO);
        scheduleToSave.setEmployees(employees);
        scheduleToSave.setPets(pets);
        Schedule saved = scheduleService.save(scheduleToSave);
        if(saved==null){
            throw new ScheduleControllerException("There was an error saving the new schedule");
        }
        return convertScheduleToDTO( saved );
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.listAll().stream().map( schedule -> convertScheduleToDTO(schedule) ).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        //throw new UnsupportedOperationException();
        return scheduleService.getScheduleForPet(petId).stream().map(schedule -> convertScheduleToDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        //throw new UnsupportedOperationException();
        return scheduleService.getScheduleForEmployee(employeeId).stream().map(schedule -> convertScheduleToDTO(schedule)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        //throw new UnsupportedOperationException();
        return scheduleService.getScheduleForCostumer(customerId).stream().map(schedule -> convertScheduleToDTO(schedule)).collect(Collectors.toList());
    }


    public ScheduleDTO convertScheduleToDTO(Schedule schedule) {
        ScheduleDTO dto = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, dto);
        dto.setEmployeeIds( schedule.getEmployees().stream().map(employee -> employee.getId()).collect(Collectors.toList()) );
        dto.setPetIds(  schedule.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList()) );
        return dto;
    }

    public Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }



}

package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.exception.ScheduleControllerException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule save(Schedule schedule){
      return scheduleRepository.save(schedule);
    }

    public List<Schedule> listAll(){
       // List<Schedule> listSchedule = scheduleRepository.findAll();
       // return listSchedule.stream().map( schedule -> convertScheduleToDTO(schedule) ).collect(Collectors.toList());
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId){
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if(!optionalPet.isPresent()){
            throw new PetNotFoundException("Pet not found, id:" + petId);
        }
        return scheduleRepository.getAllByPetsContains(optionalPet.get());
    }

    public List<Schedule> getScheduleForEmployee(long employeeId){
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(!optionalEmployee.isPresent()){
            throw new EmployeeNotFoundException("Employee not found, id:" + employeeId);
        }
        return scheduleRepository.getAllByEmployeesContains(optionalEmployee.get());
    }

    public List<Schedule> getScheduleForCostumer(long customerId){
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFoundException("Customer not found, id:" + customerId);
        }


        return scheduleRepository.getAllByPetsIn( optionalCustomer.get().getPets() );
    }



}

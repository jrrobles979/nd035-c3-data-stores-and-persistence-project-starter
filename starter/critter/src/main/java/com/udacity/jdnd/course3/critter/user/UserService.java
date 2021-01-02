package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    @Transactional
    public Customer saveCustomer(Customer customer, List<Long> petIds) throws PetNotFoundException {
       List<Pet> pets =  petIds.stream().map( petId ->  {
           Optional<Pet> optionalPet = petRepository.findById(petId);
           if (!optionalPet.isPresent()){
               throw new PetNotFoundException("Pet not found, not added to new customer. id:" + petId);
           }
           return optionalPet.get();
       } ).collect(Collectors.toList());

       customer.getPets().addAll(pets);
       Customer customerSaved = customerRepository.save(customer);
       pets.forEach( pet ->  {
           pet.setCustomer(customerSaved);
           petRepository.save(pet);
       } );
        return customerSaved;
    }

    public Customer findCustomerById(long customerId) throws CustomerNotFoundException{
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFoundException("Customer not found, id:" + customerId);
        }
        return optionalCustomer.get();
    }

    public List<Customer> listAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer findByPetId(long petId) throws PetNotFoundException {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if(!optionalPet.isPresent()){
            throw new PetNotFoundException("Pet not found. id:" + petId);
        }
        return optionalPet.get().getCustomer();
    }


    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }


    public Employee findEmployeeById(long employeeId) throws EmployeeNotFoundException{
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(!optionalEmployee.isPresent()){
            throw new EmployeeNotFoundException("Employee not found, id:" + employeeId);
        }
        return optionalEmployee.get();
    }

    public List<Employee> listAllEmployees(){
        return employeeRepository.findAll();
    }


    public List<Employee> getByDaysAvailablesAndSkillSet(EmployeeRequestDTO employeeRequestDTO){
        List<Employee> employeesAvailables =
                employeeRepository.getAllByDaysAvailableContains( employeeRequestDTO.getDate().getDayOfWeek() );
        List<Employee> employeesSkilledAndAvailable = employeesAvailables.stream().filter(employee -> employee.getSkills().containsAll(employeeRequestDTO.getSkills())).collect(Collectors.toList());
        return employeesSkilledAndAvailable;

    }


}

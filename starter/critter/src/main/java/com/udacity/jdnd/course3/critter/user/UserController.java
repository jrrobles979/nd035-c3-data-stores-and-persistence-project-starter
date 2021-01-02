package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.UserControllerException;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.schedule.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //@Autowired
    //private PetRepository petRepository;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    //@Autowired
    //private EmployeeRepository employeeRepository;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customerToSave = convertDTOToCustomer(customerDTO);
        if(customerDTO.getPetIds() == null){
            customerDTO.setPetIds(new ArrayList<>());
        }

        return convertCustomerToDto( userService.saveCustomer(customerToSave, customerDTO.getPetIds()) );
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> listCustomer = userService.listAllCustomers();
        return listCustomer.stream().map( customer -> convertCustomerToDto (customer) ).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.findPet(petId);
        if(pet.getCustomer() == null)
            throw new CustomerNotFoundException("Pet doesn't have an owner");
        return  convertCustomerToDto( pet.getCustomer() );
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {

        Employee employee = convertDTOToEmployee(employeeDTO);
        Employee employeeSaved = userService.saveEmployee(employee);
        if (employeeSaved != null) {
            return convertEmployeeToDto(employeeSaved);
        }
        throw new UserControllerException("Employee not saved");

    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

        return convertEmployeeToDto(userService.findEmployeeById(employeeId));

    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {

       Employee employee = userService.findEmployeeById(employeeId);
       employee.setDaysAvailable( daysAvailable );
       Employee employeeSaved = userService.saveEmployee(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {

       return userService.getByDaysAvailablesAndSkillSet(employeeRequestDTO).stream()
               .map( employee -> convertEmployeeToDto(employee) ).collect(Collectors.toList());


        //throw new UnsupportedOperationException();
    }


    public CustomerDTO convertCustomerToDto(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        BeanUtils.copyProperties(customer, dto);
        if(customer.getPets() != null) {
            List<Long> petIds = customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
            dto.setPetIds(petIds);
        }
        return dto;
    }

    public Customer convertDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }


    public EmployeeDTO convertEmployeeToDto(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(employee, dto);
        return dto;
    }

    public Employee convertDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }


}

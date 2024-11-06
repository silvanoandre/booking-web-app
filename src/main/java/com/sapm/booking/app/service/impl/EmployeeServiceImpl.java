package com.sapm.booking.app.service.impl;

import com.sapm.booking.app.model.Employee;
import com.sapm.booking.app.model.Person;
import com.sapm.booking.app.model.Product;
import com.sapm.booking.app.repositories.EmployeeRepository;
import com.sapm.booking.app.repositories.PersonRepository;
import com.sapm.booking.app.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PersonRepository personRepository;


    @Override
    public Employee save(Employee employee) {
        // Ensure the associated Person is created before the Employee
        Person person = employee.getPerson();
        if (person != null && person.getId() == null) {
            person = personRepository.save(person);
            employee.setPerson(person);
        }

        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    @Override
    public boolean existById(Long id) {
        return employeeRepository.existsById(id);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }


    @Override
    public Page<Employee> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee update(Employee employee) {

        Employee existingEmployee = employeeRepository.findById(employee.getId()).orElseThrow();

        // Update fields
        existingEmployee.getPerson().setName(employee.getPerson().getName()); // Example for updating nested Person
        existingEmployee.getPerson().setLastName(employee.getPerson().getLastName());
        existingEmployee.getPerson().setMiddleName(employee.getPerson().getMiddleName());
        existingEmployee.getPerson().setDocumentType(employee.getPerson().getDocumentType());
        existingEmployee.getPerson().setDocumentNumber(employee.getPerson().getDocumentNumber());
        existingEmployee.getPerson().setState(employee.getPerson().getState());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setAccess(employee.getAccess());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public void deleteById(Long id) {

        Employee currentEmployee = this.getEmployeeById(id);
        this.delete(currentEmployee);

    }


    @Override
    public Page<Employee> getAllEmployeesByAccess(String access, Pageable pageable) {
        return employeeRepository.findByAccess(access, pageable);
    }
}

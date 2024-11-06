package com.sapm.booking.app.service;

import com.sapm.booking.app.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmployeeService {


    Employee save(Employee employee);

    Employee getEmployeeById(Long id);

    boolean existById(Long id);

    Page<Employee> getAll(Pageable pageable);

    Employee update(Employee employee);

    void delete(Employee employee);

    void deleteById(Long employee);

    Page<Employee> getAllEmployeesByAccess(String access, Pageable pageable);
}
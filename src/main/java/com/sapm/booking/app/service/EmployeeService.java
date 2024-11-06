package com.sapm.booking.app.service;

import com.sapm.booking.app.model.Employee;
import com.sapm.booking.app.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface EmployeeService {


    Employee save(Employee employee);

    Employee getEmployeeById(Long id);

    boolean existById(Long id);

    Optional<Employee> findById(Long id);

    Page<Employee> getAll(Pageable pageable);

    Employee update(Employee employee);

    void delete(Employee employee);

    void deleteById(Long employee);

    Page<Employee> getAllEmployeesByAccess(String access, Pageable pageable);
}

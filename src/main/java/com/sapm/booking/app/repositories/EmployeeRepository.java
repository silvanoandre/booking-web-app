package com.sapm.booking.app.repositories;

import com.sapm.booking.app.model.Employee;
import com.sapm.booking.app.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Method to find employees by the state of the associated Person
    Page<Employee> findByAccess(String state, Pageable pageable);
}


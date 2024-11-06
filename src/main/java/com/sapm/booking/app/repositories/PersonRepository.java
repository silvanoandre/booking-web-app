package com.sapm.booking.app.repositories;

import com.sapm.booking.app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    // Custom query methods can be added here
}


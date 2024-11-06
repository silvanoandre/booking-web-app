package com.sapm.booking.app.repositories;

import com.sapm.booking.app.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Custom query methods can be added here
}


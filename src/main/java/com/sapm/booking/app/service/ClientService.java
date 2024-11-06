package com.sapm.booking.app.service;

import com.sapm.booking.app.model.Client;
import com.sapm.booking.app.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClientService {


    Client save(Client client);

    Optional<Client> findById(Long id);

    Page<Client> getAll(Pageable pageable);

    Client update(Client client);

    void delete(Client client);

    void deleteById(Long client);
}

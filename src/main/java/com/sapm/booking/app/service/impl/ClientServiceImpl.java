package com.sapm.booking.app.service.impl;

import com.sapm.booking.app.model.Client;
import com.sapm.booking.app.repositories.ClientRepository;
import com.sapm.booking.app.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;


    @Override
    public Client save(Client client) {
        return null;
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Page<Client> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Client update(Client client) {
        return null;
    }

    @Override
    public void delete(Client client) {

    }

    @Override
    public void deleteById(Long client) {

    }
}

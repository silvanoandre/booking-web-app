package com.sapm.booking.app.service;


import com.sapm.booking.app.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoomService {


    Room save(Room account);

    Optional<Room> findById(Long id);

    Page<Room> getAll(Pageable pageable);

    Room update(Room account);

    void delete(Room account);

    void deleteById(Long account);

    Page<Room> getAllRoomsByStatus(String status, Pageable pageable);
}

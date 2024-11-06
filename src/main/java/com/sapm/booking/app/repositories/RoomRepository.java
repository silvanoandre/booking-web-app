package com.sapm.booking.app.repositories;

import com.sapm.booking.app.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Custom query methods or additional operations can be defined here if needed
    Page<Room> findByStatus(String status, Pageable pageable);

}


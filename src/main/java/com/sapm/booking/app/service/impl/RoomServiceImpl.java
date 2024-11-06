package com.sapm.booking.app.service.impl;

import com.sapm.booking.app.model.Room;
import com.sapm.booking.app.repositories.RoomRepository;
import com.sapm.booking.app.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public Room save(Room Room) {
        return roomRepository.save(Room);
    }

    @Override
    public Optional<Room> findById(Long id) {

        return roomRepository.findById(id);
    }

    public Page<Room> getAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public Room update(Room updatedRoom) {
        // Retrieve the existing Room entity by its ID
        Room existingRoom = roomRepository.findById(updatedRoom.getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Update only specific properties
        existingRoom.setDescription(updatedRoom.getDescription());
        existingRoom.setCharacteristics(updatedRoom.getCharacteristics());
        existingRoom.setDailyPrice(updatedRoom.getDailyPrice());
        existingRoom.setRoomType(updatedRoom.getRoomType());
        existingRoom.setStatus(updatedRoom.getStatus());

        // Save the updated entity
        return roomRepository.save(existingRoom);
    }

    @Override
    public void delete(Room Room) {
        roomRepository.delete(Room);
    }

    @Override
    public void deleteById(Long RoomId) {
        Optional<Room> Room = roomRepository.findById(RoomId);

        roomRepository.findById(RoomId).ifPresent(this::delete);
        // Raise an exception in the future

    }

    @Override
    public Page<Room> getAllRoomsByStatus(String status, Pageable pageable) {
        return roomRepository.findByStatus(status, pageable);
    }
}

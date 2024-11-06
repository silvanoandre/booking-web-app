package com.sapm.booking.app.controllers;

import com.sapm.booking.app.model.Room;
import com.sapm.booking.app.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class MvcRoomController {

    private final RoomService roomService;


    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/add")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "add-room";
    }


    @PostMapping("/add")
    public String addRoom(@ModelAttribute("room") Room room) {
        roomService.save(room);
        return "redirect:/rooms";
    }

    @GetMapping("/edit/{id}")
    public String showEditRoomForm(@PathVariable("id") Long id, Model model) {
        Optional<Room> room = roomService.findById(id);
        if (room.isPresent()) {
            model.addAttribute("room", room.get());
            return "edit-room";
        } else {
            return "redirect:/rooms";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateRoom(@PathVariable("id") Long id, @ModelAttribute("room") @Valid Room room, BindingResult result) {
        // Check for null or empty dailyPrice before saving
        if (Objects.isNull(room.getDailyPrice())) {
            result.rejectValue("dailyPrice", "error.room", "Daily price must not be empty");
            return "edit-room";
        }

        room.setId(id);
        roomService.update(room);
        return "redirect:/rooms";
    }


    @GetMapping
    public String getAllRooms(@PageableDefault(size = 8) Pageable pageable,
                              @RequestParam(name = "status", required = false) String status,
                              Model model,
                              HttpServletRequest request) {
        Page<Room> page;
        if (status != null && !status.isEmpty()) {
            page = roomService.getAllRoomsByStatus(status, pageable);
        } else {
            page = roomService.getAll(pageable);
        }
        model.addAttribute("page", page);
        model.addAttribute("status", status); // Add status to model
        model.addAttribute("requestURI", request.getRequestURI());  // Pasar la URI al modelo
        return "rooms";
    }

    @PostMapping("/delete/{id}")
    public String deleteRoom(@PathVariable("id") Long id) {

        // Retrieve the existing Room entity by its ID
        Room existingRoom = roomService.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        roomService.delete(existingRoom);
        return "redirect:/rooms";
    }


}


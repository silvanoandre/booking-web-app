package com.sapm.booking.app.controllers;


import com.sapm.booking.app.model.Employee;
import com.sapm.booking.app.model.Product;
import com.sapm.booking.app.model.Room;
import com.sapm.booking.app.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class MvcEmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute @Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-employee";
        }
        try {
            employeeService.save(employee);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Error: there is a problem with the data entered");
            return "add-employee";
        }
        return "redirect:/employees";
    }


    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable("id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        if (Objects.isNull(employee)) {
            return "redirect:/employees";

        } else {
            model.addAttribute("employee", employee);
            return "edit-employee";

        }
    }

    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable("id") Long id, @ModelAttribute("employee") @Valid Employee employee, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employee", employee);
            return "edit-employee"; // Ensures that errors are displayed back in the form
        }

        if (employeeService.existById(id)) {
            employee.setId(id);
            employeeService.update(employee); // Assume this method handles the save
            return "redirect:/employees";

        }

        result.rejectValue("id", "error.employee", "Employee not found");
        return "edit-employee";

    }


    @GetMapping
    public String getAllEmployees(@PageableDefault(size = 8) Pageable pageable,
                              @RequestParam(name = "access", required = false) String access,
                              Model model, HttpServletRequest request) {
        Page<Employee> page;
        if (access != null && !access.isEmpty()) {
            page = employeeService.getAllEmployeesByAccess(access, pageable);
        } else {
            page = employeeService.getAll(pageable);
        }
        model.addAttribute("page", page);
        model.addAttribute("access", access); // Add status to model
        model.addAttribute("requestURI", request.getRequestURI());  // Pasar la URI al modelo
        return "employees";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {

        // Retrieve the existing Room entity by its ID
        Employee existingEmployee = employeeService.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeService.delete(existingEmployee);

        return "redirect:/employees";

    }

}

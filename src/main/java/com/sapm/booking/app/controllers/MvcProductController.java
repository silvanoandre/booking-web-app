package com.sapm.booking.app.controllers;

import com.sapm.booking.app.model.Product;
import com.sapm.booking.app.model.Room;
import com.sapm.booking.app.service.ProductService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class MvcProductController {

    private final ProductService productService;


    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("room", new Room());
        return "add-product";
    }


    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "edit-product";
        } else {
            return "redirect:/products";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") @Valid Product product, BindingResult result) {
        // Check for null or empty dailyPrice before saving
        if (Objects.isNull(product.getPrice())) {
            result.rejectValue("Price", "error.room", "Price must not be empty");
            return "edit-product";
        }

        product.setId(id);
        productService.update(product);
        return "redirect:/products";
    }




    @GetMapping
    public String getAllProducts(@PageableDefault(size = 1000) Pageable pageable,
                                 @RequestParam(name = "status", required = false) String status,
                                 Model model,
                                 HttpServletRequest request) {
        Page<Product> page;

        page = productService.getAll(pageable);

        // Agregar la URI de la solicitud al modelo
        model.addAttribute("page", page);
        model.addAttribute("requestURI", request.getRequestURI());  // Pasar la URI al modelo
        return "products";
    }


    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/products";
        }

        try {
            productService.uploadProductsFromFile(file);
            redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Failed to upload file: " + e.getMessage());
        }

        return "redirect:/products";
    }


}


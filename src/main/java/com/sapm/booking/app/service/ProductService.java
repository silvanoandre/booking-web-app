package com.sapm.booking.app.service;

import com.sapm.booking.app.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {


    Product save(Product account);

    void uploadProductsFromFile(MultipartFile file) throws IOException;


    Optional<Product> findById(Long id);

    public boolean existById(Long id);

    Page<Product> getAll(Pageable pageable);

    Product update(Product account);

    void delete(Product account);

    void deleteById(Long account);
}

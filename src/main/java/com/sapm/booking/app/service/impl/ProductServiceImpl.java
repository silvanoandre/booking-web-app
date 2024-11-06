package com.sapm.booking.app.service.impl;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.sapm.booking.app.model.Product;
import com.sapm.booking.app.model.dto.ProductInput;
import com.sapm.booking.app.repositories.ProductRepository;
import com.sapm.booking.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void uploadProductsFromFile(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            // Usar Poiji para convertir el archivo Excel en una lista de ProductInput
            List<ProductInput> products = Poiji.fromExcel(inputStream, PoijiExcelType.XLSX, ProductInput.class);

            // Crear un executor de hilos virtuales
            ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

            try {
                // Procesar cada producto en un hilo virtual
                List<? extends Future<?>> futures = products.stream()
                        .map(productInput -> executorService.submit(() -> {
                            Product product = Product.builder()
                                    .name(productInput.getName())
                                    .description(productInput.getDescription())
                                    .unitOfMeasurement(productInput.getUnitOfMeasurement())
                                    .price(productInput.getPrice())
                                    .build();
                            this.save(product);
                        }))
                        .toList();

                // Esperar a que todos los hilos terminen su ejecución
                for (Future<?> future : futures) {
                    future.get();
                }
            } catch (Exception e) {
                throw new RuntimeException("Error processing products", e);
            } finally {
                executorService.shutdown();
            }
        }
    }

    @Override
    public Optional<Product> findById(Long id) {

        return productRepository.findById(id);
    }

    @Override
    public boolean existById(Long id) {

        return productRepository.existsById(id);
    }

    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            this.delete(product.get());
        }

    }
}

package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean exists(String productId) {
        return productRepository.existsById(productId);
    }

    public boolean updateStock(String productId, int quantity) {
        return productRepository.findById(productId).map(product -> {
            product.setUnit(product.getUnit() + quantity);
            productRepository.save(product);
            return true;
        }).orElse(false);
    }

    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }
}

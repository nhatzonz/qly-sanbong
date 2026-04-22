package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean exists(String productId) {
        return productRepository.existsById(productId);
    }

    @Transactional
    public boolean updateStock(String productId, int quantity) {
        return productRepository.findById(productId).map(product -> {
            int truoc = product.getUnit();
            product.setUnit(truoc + quantity);
            productRepository.save(product);
            log.info("[CAP NHAT TON KHO] San pham: {}, Truoc: {}, Them: {}, Sau: {}", productId, truoc, quantity, product.getUnit());
            return true;
        }).orElse(false);
    }

    public Product addNewProduct(Product product) {
        Product saved = productRepository.save(product);
        log.info("[TAO SAN PHAM] Ma: {}, Ten: {}, Gia: {}, So luong: {}",
                saved.getProductId(), saved.getProductName(), saved.getPrice(), saved.getUnit());
        return saved;
    }

    public List<Product> getAll() {
        List<Product> list = productRepository.findAll();
        log.info("[LAY SAN PHAM] Tra ve {} san pham", list.size());
        return list;
    }
}

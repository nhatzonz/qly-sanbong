package com.example.productservice.controller;

import com.example.productservice.entity.Product;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{productId}/exists")
    public ResponseEntity<Boolean> exists(@PathVariable String productId) {
        return ResponseEntity.ok(productService.exists(productId));
    }

    @PutMapping("/{productId}/stock")
    public ResponseEntity<Boolean> updateStock(@PathVariable String productId,
                                               @RequestParam int quantity) {
        return ResponseEntity.ok(productService.updateStock(productId, quantity));
    }

    @PostMapping
    public ResponseEntity<Product> addNewProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addNewProduct(product));
    }
}

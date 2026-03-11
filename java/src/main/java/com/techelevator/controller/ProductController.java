package com.techelevator.controller;

import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Product;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ProductController {

    private ProductDao productDao;
    private UserDao userDao;

    public ProductController (ProductDao productDao, UserDao userDao) {
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productDao.createProduct(product);
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable int id) {
        return productDao.getProductById(id);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @GetMapping("/products?name=trace")
    public Product getProductByName(@RequestParam String name) {
        return productDao.getProductByName(name);
    }

    @GetMapping("/products?categoryId={id}")
    public List<Product> getProductsByCategoryId(@RequestParam int categoryId) {
        return productDao.getProductsByCategory(categoryId);
    }

    
}

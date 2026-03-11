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
@RequestMapping("/products")
public class ProductController {

    private final ProductDao productDao;
    private final UserDao userDao;

    public ProductController(ProductDao productDao, UserDao userDao) {
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productDao.createProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable int id) {
        return productDao.getProductById(id);
    }

    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) String category,
                                     @RequestParam(required = false) String vendor) {
        if (name != null && !name.isBlank()) {
            return productDao.getProductsByName(name);
        }
        if (category != null) {
            return productDao.getProductsByCategory(category);
        }

        if (vendor != null) {
            return productDao.getProductsByVendor(vendor);
        }
        return productDao.getAllProducts();
    }
}

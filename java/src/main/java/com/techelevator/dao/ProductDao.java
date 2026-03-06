package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {

    Product createProduct(Product product);

    Product getProductById(int id);

    List<Product> getAllProducts();

    Product getProductByName(String name);

    List<Product> getProductsByCategory(int categoryId);

    List<Product> getProductsByVendor(int vendorId);

    Product updateProduct(Product product);

    int deleteProductById(int productId);
}

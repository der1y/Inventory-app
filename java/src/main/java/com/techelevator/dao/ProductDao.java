package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao {

    Product createProduct(Product product);

    Product getProductById(int id);

    List<Product> getAllProducts();

    List<Product> getProducts(String category, String vendor);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByCategory(String categoryName);

    List<Product> getProductsByVendor(String vendorName);

    Product updateProduct(Product product);

    int deleteProductById(int productId);
}

package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        String upcToSave = normalizeUpc(product.getUpc());
        String insertProductSQL = "INSERT INTO products (upc, name, category_id, default_bottle_ml, is_active) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING product_id";

        try {
            Integer newProductId = jdbcTemplate.queryForObject(insertProductSQL, Integer.class,
                    upcToSave,
                    product.getName(),
                    product.getCategoryId(),
                    product.getDefault_bottle_ml(),
                    product.isIs_active());
            product.setId(newProductId);
            product.setUpc(upcToSave);
            return product;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation while creating product", e);
        }
    }

    @Override
    public Product getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, productId);

        if (sqlRowSet.next()) {
            return mapRowToProduct(sqlRowSet);
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product getProductByName(String name) {
        return null;
    }

    @Override
    public Product getProductByCategory(int categoryId) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public int deleteProductById(int productId) {
        return 0;
    }

    private String normalizeUpc(String upc) {
        if (upc == null) {
            return null;
        }
        String normalized = upc.trim();
        if (normalized.isBlank()) {
            return null;
        }
        if (!normalized.matches("\\d{12,14}")) {
            throw new IllegalArgumentException("UPC must be 12 to 14 digits");
        }
        return normalized;
    }

    private Product mapRowToProduct(SqlRowSet set) {
        Product tempProduct = new Product();

        tempProduct.setName(set.getString("name"));
        tempProduct.setCategoryId(set.getInt("category_id"));
        tempProduct.setDefault_bottle_ml(set.getInt("default_bottle_ml"));
        tempProduct.setIs_active(set.getBoolean("is_active"));
        tempProduct.setId(set.getInt("product_id"));
        tempProduct.setUpc(set.getString("upc"));

        return tempProduct;
    }
}

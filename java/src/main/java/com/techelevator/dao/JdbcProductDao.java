package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    private final String ERROR_MESSAGE = "Unable to connect to server or database";

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
            throw new DaoException(ERROR_MESSAGE, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation while creating product", e);
        }
    }

    @Override
    public Product getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try {
            SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, productId);

            if (sqlRowSet.next()) {
                return mapRowToProduct(sqlRowSet);
            }
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                products.add(mapRowToProduct(results));
            }

            return products;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public Product getProductByName(String name) {
        String sql = "SELECT * FROM products WHERE name = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);

            if (results.next()) {
                return mapRowToProduct(results);
            }
            return null;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> productsByCategory = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE category_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, categoryId);

            while (results.next()) {
                productsByCategory.add(mapRowToProduct(results));
            }
            return productsByCategory;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<Product> getProductsByVendor(int vendorId) {
        List<Product> productsByVendor = new ArrayList<>();

        String sql = "SELECT * FROM vendor_product vp " +
                "JOIN products p ON p.product_id = vp.product_id " +
                "WHERE vendor_id = ? " +
                "ORDER BY name;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, vendorId);

            while (results.next()) {
                productsByVendor.add(mapRowToProduct(results));
            }

            return productsByVendor;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public Product updateProduct(Product product) {
        Product updatedProduct;

        String updateProductSQL = "UPDATE products " +
                "SET upc = ?, name = ?, category_id = ?, default_bottle_ml = ?, is_active = ? " +
                "WHERE product_id = ?";

        try {
            int updatedRows = jdbcTemplate.update(updateProductSQL, product.getUpc(), product.getName(),
                    product.getCategoryId(), product.getDefault_bottle_ml(), product.isIs_active());
            if (updatedRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }

            updatedProduct = getProductById(product.getId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(ERROR_MESSAGE, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation while updating product", e);
        }
        return updatedProduct;
    }

    @Override
    public int deleteProductById(int productId) {
        String deleteProduct = "DELETE FROM products WHERE product_id = ?";
        int deletedRows;
        try {
            deletedRows = jdbcTemplate.update(deleteProduct, productId);
            if (deletedRows == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(ERROR_MESSAGE, e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation while deleting product");
        }
        return deletedRows;
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

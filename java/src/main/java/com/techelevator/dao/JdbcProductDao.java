package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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
                    product.getDefaultBottleMl(),
                    product.isActive());
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
    public List<Product> getProducts(String name, String category, String vendor) {
        List<Product> matchingProducts = new ArrayList<>();
        String trimmedName = name == null ? null : name.trim();
        boolean hasName = trimmedName != null && !trimmedName.isBlank();
        boolean hasCategory = category != null && !category.isBlank();
        boolean hasVendor = vendor != null && !vendor.isBlank();

        StringBuilder sql = new StringBuilder("SELECT DISTINCT p.* FROM products p");
        List<Object> params = new ArrayList<>();
        List<String> filters = new ArrayList<>();

        if (hasCategory) {
            sql.append(" JOIN categories c ON p.category_id = c.category_id");
            filters.add("LOWER(c.name) = LOWER(?)");
            params.add(category);
        }

        if (hasVendor) {
            sql.append(" JOIN vendor_product vp ON vp.product_id = p.product_id");
            sql.append(" JOIN vendors v ON v.vendor_id = vp.vendor_id");
            filters.add("LOWER(v.name) = LOWER(?)");
            params.add(vendor);
        }

        if (hasName) {
            filters.add("p.name ILIKE ?");
            params.add("%" + trimmedName + "%");
        }

        if (!filters.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", filters));
        }

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql.toString(), params.toArray());

            while (results.next()) {
                matchingProducts.add(mapRowToProduct(results));
            }
            return matchingProducts;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> matchingProducts = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name ILIKE ?";
        String searchString = "%" + name.trim() + "%";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, searchString);

            while (results.next()) {
                matchingProducts.add(mapRowToProduct(results));
            }
            return matchingProducts;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        List<Product> productsByCategory = new ArrayList<>();

        String sql = "SELECT p.* FROM products p " +
                "JOIN categories c ON p.category_id = c.category_id " +
                "WHERE LOWER(c.name) = LOWER(?)";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, categoryName);

            while (results.next()) {
                productsByCategory.add(mapRowToProduct(results));
            }
            return productsByCategory;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException(ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<Product> getProductsByVendor(String vendorName) {
        List<Product> productsByVendor = new ArrayList<>();

        String sql = "SELECT p.* FROM products p " +
                "JOIN vendor_product vp ON vp.product_id = p.product_id " +
                "JOIN vendors v ON v.vendor_id = vp.vendor_id " +
                "WHERE LOWER(v.name) = LOWER(?)";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, vendorName);

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
                    product.getCategoryId(), product.getDefaultBottleMl(), product.isActive());
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
        tempProduct.setDefaultBottleMl(set.getInt("default_bottle_ml"));
        tempProduct.setActive(set.getBoolean("is_active"));
        tempProduct.setId(set.getInt("product_id"));
        tempProduct.setUpc(set.getString("upc"));

        return tempProduct;
    }
}

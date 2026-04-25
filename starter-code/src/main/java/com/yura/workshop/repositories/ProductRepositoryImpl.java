package com.yura.workshop.repositories;

import com.yura.workshop.db.DatabaseConnection;
import com.yura.workshop.models.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of ProductRepository.
 *
 * Rules:
 *  - Use PreparedStatement for ALL queries (no string concatenation).
 *  - Wrap Create, Update, and Delete in explicit transactions (setAutoCommit(false)).
 *  - Call connection.rollback() in the catch block before re-throwing.
 *
 * First branch (feature/create-read):  implement save, findAll, findById.
 * Second branch (feature/update-delete): implement update, delete.
 */
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public Product save(Product product) {
        // TODO: Validate product.getPrice() > 0 and product.getStock() >= 0
        //       (throw IllegalArgumentException if invalid).
        // TODO: Open connection, setAutoCommit(false).
        // TODO: prepareStatement("INSERT INTO products ...", Statement.RETURN_GENERATED_KEYS).
        // TODO: Execute, get generated key, set on product.
        // TODO: commit() on success, rollback() + throw on failure.
        throw new UnsupportedOperationException("save() not implemented yet");
    }

    @Override
    public List<Product> findAll() {
        // TODO: prepareStatement("SELECT id, name, description, price, stock, created_at FROM products ORDER BY id").
        // TODO: Execute query, iterate ResultSet, map each row to a Product.
        // TODO: Return the list (empty list if no rows).
        throw new UnsupportedOperationException("findAll() not implemented yet");
    }

    @Override
    public Product findById(int id) {
        // TODO: prepareStatement("SELECT ... FROM products WHERE id = ?").
        // TODO: Return the mapped Product, or null if the ResultSet is empty.
        throw new UnsupportedOperationException("findById() not implemented yet");
    }

    @Override
    public Product update(Product product) {
        // TODO: setAutoCommit(false).
        // TODO: prepareStatement("UPDATE products SET name=?, description=?, price=?, stock=? WHERE id=?").
        // TODO: commit() / rollback(). Return the updated product.
        throw new UnsupportedOperationException("update() not implemented yet");
    }

    @Override
    public void delete(int id) {
        // TODO: setAutoCommit(false).
        // TODO: prepareStatement("DELETE FROM products WHERE id=?").
        // TODO: commit() / rollback().
        throw new UnsupportedOperationException("delete() not implemented yet");
    }
}

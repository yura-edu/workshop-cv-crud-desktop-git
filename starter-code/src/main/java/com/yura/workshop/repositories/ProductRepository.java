package com.yura.workshop.repositories;

import com.yura.workshop.models.Product;
import java.util.List;

public interface ProductRepository {

    /** Inserts a new product and returns it with the generated id. */
    Product save(Product product);

    /** Returns all products ordered by id. */
    List<Product> findAll();

    /** Returns the product with the given id, or null if not found. */
    Product findById(int id);

    /** Updates name, description, price and stock for the product. Returns the updated product. */
    Product update(Product product);

    /** Deletes the product with the given id. */
    void delete(int id);
}

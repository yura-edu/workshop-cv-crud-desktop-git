package com.yura.workshop.repositories;

import com.yura.workshop.models.Product;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for ProductRepositoryImpl.
 *
 * Requirements:
 *  - PostgreSQL must be running (docker compose up -d).
 *  - Set DB_URL, DB_USER, DB_PASSWORD env vars if needed (defaults work with compose.yml).
 *  - Tests run in order so later tests can rely on data inserted by earlier ones.
 *
 * Instructions:
 *  Remove each @Disabled annotation as you implement the corresponding method,
 *  then fill in the test body following the suggestions in the comments.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryTest {

    private static ProductRepository repo;
    private static int savedId;

    @BeforeAll
    static void setUp() {
        repo = new ProductRepositoryImpl();
    }

    // ── save ─────────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @Disabled("Remove @Disabled after implementing save()")
    void save_shouldPersistProductAndReturnGeneratedId() {
        // TODO: Create a Product with name, description, price=10.00, stock=5
        // TODO: Call repo.save(product)
        // TODO: Assert saved.getId() > 0
        // TODO: Assert saved.getName() equals the name you set
        // TODO: Store savedId = saved.getId() for use in later tests
        fail("Not implemented");
    }

    @Test
    @Order(2)
    @Disabled("Remove @Disabled after implementing save()")
    void save_shouldThrowForNonPositivePrice() {
        Product p = new Product("Bad", "desc", new BigDecimal("-1.00"), 5);
        // TODO: assertThrows(IllegalArgumentException.class, () -> repo.save(p));
        fail("Not implemented");
    }

    @Test
    @Order(3)
    @Disabled("Remove @Disabled after implementing save()")
    void save_shouldThrowForNegativeStock() {
        Product p = new Product("Bad", "desc", new BigDecimal("10.00"), -1);
        // TODO: assertThrows(IllegalArgumentException.class, () -> repo.save(p));
        fail("Not implemented");
    }

    // ── findAll ──────────────────────────────────────────────────────────────

    @Test
    @Order(4)
    @Disabled("Remove @Disabled after implementing findAll()")
    void findAll_shouldReturnNonEmptyList() {
        // TODO: List<Product> products = repo.findAll();
        // TODO: assertFalse(products.isEmpty());
        fail("Not implemented");
    }

    // ── findById ─────────────────────────────────────────────────────────────

    @Test
    @Order(5)
    @Disabled("Remove @Disabled after implementing findById()")
    void findById_shouldReturnProductWhenExists() {
        // TODO: Product found = repo.findById(savedId);
        // TODO: assertNotNull(found);
        // TODO: assertEquals(savedId, found.getId());
        fail("Not implemented");
    }

    @Test
    @Order(6)
    @Disabled("Remove @Disabled after implementing findById()")
    void findById_shouldReturnNullWhenNotFound() {
        // TODO: Product result = repo.findById(999_999);
        // TODO: assertNull(result);
        fail("Not implemented");
    }

    // ── update ───────────────────────────────────────────────────────────────

    @Test
    @Order(7)
    @Disabled("Remove @Disabled after implementing update()")
    void update_shouldModifyExistingProduct() {
        // TODO: Product p = repo.findById(savedId);
        // TODO: p.setPrice(new BigDecimal("99.99"));
        // TODO: p.setStock(100);
        // TODO: Product updated = repo.update(p);
        // TODO: assertEquals(new BigDecimal("99.99"), updated.getPrice());
        // TODO: assertEquals(100, updated.getStock());
        fail("Not implemented");
    }

    // ── delete ───────────────────────────────────────────────────────────────

    @Test
    @Order(8)
    @Disabled("Remove @Disabled after implementing delete()")
    void delete_shouldRemoveProduct() {
        // TODO: repo.delete(savedId);
        // TODO: assertNull(repo.findById(savedId));
        fail("Not implemented");
    }
}

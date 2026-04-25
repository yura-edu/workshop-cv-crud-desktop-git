package com.yura.workshop.ui;

import com.yura.workshop.models.Product;
import com.yura.workshop.repositories.ProductRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Main application window. DO NOT MODIFY.
 *
 * Displays a JTable with all products and delegates all data operations
 * to the ProductRepository interface.
 */
public class ProductFrame extends JFrame {

    private final ProductRepository repository;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private static final String[] COLUMNS =
            {"ID", "Nombre", "Descripción", "Precio", "Stock", "Creado"};

    public ProductFrame(ProductRepository repository) {
        this.repository = repository;
        this.tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(tableModel);
        initUI();
        loadProducts();
    }

    private void initUI() {
        setTitle("Gestión de Productos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JButton btnAdd     = new JButton("Agregar");
        JButton btnEdit    = new JButton("Editar");
        JButton btnDelete  = new JButton("Eliminar");
        JButton btnRefresh = new JButton("Actualizar");

        buttons.add(btnAdd);
        buttons.add(btnEdit);
        buttons.add(btnDelete);
        buttons.add(btnRefresh);
        add(buttons, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> showAddDialog());
        btnEdit.addActionListener(e -> showEditDialog());
        btnDelete.addActionListener(e -> deleteSelected());
        btnRefresh.addActionListener(e -> loadProducts());
    }

    private void loadProducts() {
        tableModel.setRowCount(0);
        try {
            List<Product> products = repository.findAll();
            for (Product p : products) {
                tableModel.addRow(new Object[]{
                        p.getId(), p.getName(), p.getDescription(),
                        p.getPrice(), p.getStock(), p.getCreatedAt()
                });
            }
        } catch (Exception ex) {
            showError("Error al cargar productos", ex);
        }
    }

    private void showAddDialog() {
        JTextField nameField  = new JTextField(20);
        JTextField descField  = new JTextField(20);
        JTextField priceField = new JTextField(10);
        JTextField stockField = new JTextField(10);

        JPanel panel = buildFormPanel(nameField, descField, priceField, stockField);
        int result = JOptionPane.showConfirmDialog(this, panel, "Nuevo Producto",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Product p = new Product(
                        nameField.getText().trim(),
                        descField.getText().trim(),
                        new BigDecimal(priceField.getText().trim()),
                        Integer.parseInt(stockField.getText().trim())
                );
                repository.save(p);
                loadProducts();
            } catch (Exception ex) {
                showError("Error al agregar producto", ex);
            }
        }
    }

    private void showEditDialog() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para editar.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        try {
            Product existing = repository.findById(id);
            if (existing == null) { loadProducts(); return; }

            JTextField nameField  = new JTextField(existing.getName(), 20);
            JTextField descField  = new JTextField(existing.getDescription(), 20);
            JTextField priceField = new JTextField(existing.getPrice().toString(), 10);
            JTextField stockField = new JTextField(String.valueOf(existing.getStock()), 10);

            JPanel panel = buildFormPanel(nameField, descField, priceField, stockField);
            int result = JOptionPane.showConfirmDialog(this, panel, "Editar Producto #" + id,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                existing.setName(nameField.getText().trim());
                existing.setDescription(descField.getText().trim());
                existing.setPrice(new BigDecimal(priceField.getText().trim()));
                existing.setStock(Integer.parseInt(stockField.getText().trim()));
                repository.update(existing);
                loadProducts();
            }
        } catch (Exception ex) {
            showError("Error al editar producto", ex);
        }
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Confirmas eliminar el producto #" + id + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                repository.delete(id);
                loadProducts();
            } catch (Exception ex) {
                showError("Error al eliminar producto", ex);
            }
        }
    }

    private JPanel buildFormPanel(JTextField name, JTextField desc,
                                  JTextField price, JTextField stock) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.add(new JLabel("Nombre:"));      panel.add(name);
        panel.add(new JLabel("Descripción:")); panel.add(desc);
        panel.add(new JLabel("Precio:"));      panel.add(price);
        panel.add(new JLabel("Stock:"));       panel.add(stock);
        return panel;
    }

    private void showError(String message, Exception ex) {
        JOptionPane.showMessageDialog(this,
                message + ":\n" + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}

package com.yura.workshop;

import com.yura.workshop.repositories.ProductRepository;
import com.yura.workshop.repositories.ProductRepositoryImpl;
import com.yura.workshop.ui.ProductFrame;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductRepository repository = new ProductRepositoryImpl();
            new ProductFrame(repository).setVisible(true);
        });
    }
}

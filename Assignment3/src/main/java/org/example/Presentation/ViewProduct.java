package org.example.Presentation;

import org.example.BusinessLogic.ProductBLL;
import org.example.Model.Product;
import org.example.Start.Reflection;

import javax.swing.*;
import java.awt.*;

/**
 * Clasa ViewProduct implementeaza interfata grafica pentru gestionarea produselor.
 * Aceasta clasa include butoane pentru vizualizarea sub forma de tabel a produselor, inserarea, actualizarea si stergerea produselor.
 */
public class ViewProduct {
    private JFrame productFrame;
    private JButton buttonInsertProduct, buttonUpdateProduct, buttonDeleteProduct, buttonViewProducts;
    private JTable table;
    /**
     * Constructorul fara parametri ai clasei ViewProduct.
     * Aici se initializeaza fereastra prin adaugarea componentelor necesare.
     */
    public ViewProduct() {
        productFrame = new JFrame();
        productFrame.setTitle("PRODUCTS");
        productFrame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 12));
        productFrame.setBounds(100, 100, 500, 450);
        productFrame.setBackground(Color.LIGHT_GRAY);
        productFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productFrame.getContentPane().setLayout(null);
        productFrame.setResizable(false);

        JLabel labelOperations = new JLabel("Product operations:");
        labelOperations.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        labelOperations.setBounds(180, 10, 215, 20);
        labelOperations.setForeground(Color.DARK_GRAY);
        productFrame.getContentPane().add(labelOperations);

        JLabel label = new JLabel("The products: ");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        label.setBounds(200, 150, 215, 20);
        label.setForeground(Color.DARK_GRAY);
        productFrame.getContentPane().add(label);

        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        panel.setBounds(20, 200, 450, 160);
        productFrame.getContentPane().add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 450, 160);
        panel.add(scrollPane);

        buttonViewProducts = new JButton("View products");
        buttonViewProducts.addActionListener(e -> {
            ProductBLL productBLL = new ProductBLL();
            table = Reflection.retrivePropertiesAndValuesForList(productBLL.findAllProducts());
            scrollPane.setViewportView(table);
        });
        buttonViewProducts.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        buttonViewProducts.setBounds(30, 50, 200, 30);
        buttonViewProducts.setBackground(Color.lightGray);
        productFrame.getContentPane().add(buttonViewProducts);

        buttonInsertProduct = new JButton("Insert product");
        buttonInsertProduct.addActionListener(e -> {
            UpdateInsertProductDialog dialog = new UpdateInsertProductDialog(new ViewProduct(), null, 1);
            dialog.setVisible(true);
        });
        buttonInsertProduct.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        buttonInsertProduct.setBounds(260, 50, 200, 30);
        buttonInsertProduct.setBackground(Color.lightGray);
        productFrame.getContentPane().add(buttonInsertProduct);

        buttonUpdateProduct = new JButton("Update product");
        buttonUpdateProduct.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(productFrame, "You must select a product", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int id = (int) table.getValueAt(row, ProductTableModel.ID_COL);
                ProductBLL productBLL = new ProductBLL();
                Product tempProduct = productBLL.findProductById(id);
                UpdateInsertProductDialog dialog = new UpdateInsertProductDialog(new ViewProduct(), tempProduct, 0);
                dialog.setVisible(true);
            } catch (Exception ignored){
            }
        });
        buttonUpdateProduct.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        buttonUpdateProduct.setBounds(30, 90, 200, 30);
        buttonUpdateProduct.setBackground(Color.lightGray);
        productFrame.getContentPane().add(buttonUpdateProduct);

        buttonDeleteProduct = new JButton("Delete product");
        buttonDeleteProduct.addActionListener(e -> {
            int id;
            try {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(productFrame, "You must select a product", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                id = (int) table.getValueAt(row, ProductTableModel.ID_COL);
                ProductBLL productBLL = new ProductBLL();
                if (productBLL.deleteProduct(id) == 1) {
                    table = Reflection.retrivePropertiesAndValuesForList(productBLL.findAllProducts());
                    scrollPane.setViewportView(table);
                    JOptionPane.showMessageDialog(productFrame, "Product deleted", "Product updated", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(productFrame, "This product was ordered, cannot delete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ignored) {
            }
        });
        buttonDeleteProduct.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        buttonDeleteProduct.setBounds(260, 90, 200, 30);
        buttonDeleteProduct.setBackground(Color.lightGray);
        productFrame.getContentPane().add(buttonDeleteProduct);

    }
    /**
     * Metoda returneaza fereastra JFrame din interfata pentru categoria produselor.
     * @return un obiect de tipul JFrame
     */
    public JFrame getProductFrame() {
        return this.productFrame;
    }
}

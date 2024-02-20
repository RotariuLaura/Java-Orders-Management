package org.example.Presentation;

import org.example.BusinessLogic.ProductBLL;
import org.example.Model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Clasa UpdateInsertProductDialo implementeaza o fereastra pentru inserarea sau actualizarea datelor pentru produse.
 * Aceasta clasa extinde clasa JDialog.
 */
public class UpdateInsertProductDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField nameTextField;
    private JTextField priceTextField;
    private JTextField stockTextField;
    private ViewProduct viewProduct;
    /**
     * Constructorul clasei UpdateInsertProductDialog.
     * @param viewProduct_ un obiect de tipul clasei ViewProduct, fereastra cu operatii pe produse
     * @param product un obiect de tipul clasei Product care va fi inserat/actualizat
     * @param insert o variabila de tip intreg care indica daca se va face inserarea sau actualizarea unui produs
     */
    public UpdateInsertProductDialog(ViewProduct viewProduct_, Product product, int insert) {
        this.viewProduct = viewProduct_;
        if (insert == 1) {
            setTitle("Insert Product");
        } else {
            setTitle("Update Product");
        }
        setBounds(100, 100, 347, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNume = new JLabel("Name=");
        lblNume.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblNume.setBounds(38, 62, 66, 28);
        contentPanel.add(lblNume);

        JLabel lblAddress = new JLabel("Price=");
        lblAddress.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblAddress.setBounds(38, 101, 66, 28);
        contentPanel.add(lblAddress);

        JLabel lblEmail = new JLabel("Stock=");
        lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblEmail.setBounds(38, 140, 66, 28);
        contentPanel.add(lblEmail);

        nameTextField = new JTextField();
        nameTextField.setBounds(115, 62, 200, 28);
        contentPanel.add(nameTextField);
        nameTextField.setColumns(10);

        priceTextField = new JTextField();
        priceTextField.setBounds(115, 101, 200, 28);
        contentPanel.add(priceTextField);
        priceTextField.setColumns(10);

        stockTextField = new JTextField();
        stockTextField.setBounds(115, 140, 200, 28);
        contentPanel.add(stockTextField);
        stockTextField.setColumns(10);

        if (insert == 0) {
            nameTextField.setText(product.getName());
            priceTextField.setText(String.valueOf(product.getPrice()));
            stockTextField.setText(String.valueOf(product.getStock()));
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton okButton = new JButton("Save");
            okButton.addActionListener(arg0 -> {
                try {
                    if (insert == 0) {
                        String name = nameTextField.getText();
                        double price = Double.parseDouble(priceTextField.getText());
                        int stock = Integer.parseInt(stockTextField.getText());
                        Product newProduct = new Product(name, price, stock);
                        int i = product.getId();
                        ProductBLL productBLL = new ProductBLL();
                        if (productBLL.updateProduct(newProduct, i) == 1) {
                            setVisible(false);
                            dispose();
                            JOptionPane.showMessageDialog(viewProduct.getProductFrame(), "Product updated", "Product updated", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(viewProduct.getProductFrame(), "Error updating product", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        String nume = nameTextField.getText();
                        double price = Double.parseDouble(priceTextField.getText());
                        int stock = Integer.parseInt(stockTextField.getText());
                        Product newProduct = new Product(nume, price, stock);
                        ProductBLL productBLL = new ProductBLL();
                        if (productBLL.insertProduct(newProduct) != -1) {
                            setVisible(false);
                            dispose();
                            JOptionPane.showMessageDialog(viewProduct.getProductFrame(), "Product added", "Product updated", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(viewProduct.getProductFrame(), "Error adding product", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex1){
                    JOptionPane.showMessageDialog(viewProduct.getProductFrame(), "Introduce the data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            okButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            okButton.setBackground(Color.lightGray);
            okButton.setActionCommand("OK");
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);
        }
        {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> {
                setVisible(false);
                dispose();
            });
            cancelButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            cancelButton.setBackground(Color.lightGray);
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }
    }
}
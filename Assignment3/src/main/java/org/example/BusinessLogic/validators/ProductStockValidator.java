package org.example.BusinessLogic.validators;

import org.example.Model.Product;
import org.example.Presentation.ViewProduct;

import javax.swing.*;

/**
 * Validator pentru stocul unui produs.
 * Implementeaza interfata definita Validator.
 */
public class ProductStockValidator implements Validator <Product> {
    /**
     * Metoda valideaza stocul unui produs. Daca e mai mic decat 1, se afiseaza un mesaj si se arunca o exceptie.
     * @param product este produsul de validat
     */
    public void validate(Product product) {
        if(product.getStock() < 1){
            JOptionPane.showMessageDialog(new ViewProduct().getProductFrame(), "Stock cannot be less than 1.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Stock cannot be less than 1.");
        }
    }
}

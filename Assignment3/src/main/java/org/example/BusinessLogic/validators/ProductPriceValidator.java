package org.example.BusinessLogic.validators;

import org.example.Model.Product;
import org.example.Presentation.ViewProduct;

import javax.swing.*;

/**
 * Validator pentru pretul unui produs.
 * Implementeaza interfata definita Validator.
 */
public class ProductPriceValidator implements Validator<Product> {
    /**
     * Metoda valideaza pretul unui produs. Daca e mai mic decat 1, se afiseaza un mesaj si se arunca o exceptie.
     * @param product este produsul de validat
     */
    public void validate(Product product) {
        if(product.getPrice() < 1){
            JOptionPane.showMessageDialog(new ViewProduct().getProductFrame(), "Price cannot be less than 1.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Price cannot be less than 1.");
        }
    }
}

package org.example.BusinessLogic.validators;

import org.example.Model.Order;
import org.example.Presentation.ViewOrder;

import javax.swing.*;

/**
 * Validator pentru validarea cantitatii unei comenzi.
 * Implementeaza interfata definita Validator.
 */
public class OrderQuantityValidator implements Validator<Order> {
    /**
     * Metoda valideaza cantitatea unei comenzi. Daca este mai mica decat 1, se afiseaza un mesaj si se arunca o exceptie.
     * @param order este comanda de tip Order de validat
     */
    public void validate(Order order) {
        if (order.getQuantity() < 1) {
            JOptionPane.showMessageDialog(new ViewOrder().getOrderFrame(), "Quantity cannot be less than 1.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Quantity cannot be less than 1.");
        }
    }
}

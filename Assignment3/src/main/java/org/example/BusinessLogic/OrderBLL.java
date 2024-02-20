package org.example.BusinessLogic;

import org.example.BusinessLogic.validators.OrderQuantityValidator;
import org.example.BusinessLogic.validators.Validator;
import org.example.DataAccess.OrderDAO;
import org.example.Model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Clasa OrderBLL contine logica pentru obiectele de tip Order.
 * Aceasta clasa utilizeaza obiectele de tip Validator pentru a valida informatiile introduse de utilizator
 * inainte de a fi inserate sau actualizate in baza de date.
 */
public class OrderBLL {
    /** Lista de validatori pentru clasa Order. */
    private final List<Validator<Order>> validators;
    /** Constructorul clasei OrderBLL initializeaza o lista de validatori pentru obiectele de tip Order. */
    public OrderBLL() {
        validators = new ArrayList<>();
        validators.add(new OrderQuantityValidator());
    }
    /**
     * Metoda insereaza o noua comanda in baza de date dupa ce valideaza informatiile folosind metoda din clasa OrderDAO.
     * @param order este comanda de inserat
     * @return id-ul obiectului Order inserat in baza de date
     */
    public int insertOrder(Order order) {
        for (Validator<Order> v : validators) {
            v.validate(order);
        }
        return OrderDAO.insert(order);
    }
    /**
     * Metoda cauta toate comenzile de tipul Order din baza de date folosind metoda din clasa OrderDAO.
     * @return o lista cu obiectele de tip Order gasite
     */
    public ArrayList<Object> findAllOrders() {
        return OrderDAO.findAll();
    }
}

package org.example.BusinessLogic;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.example.BusinessLogic.validators.ProductPriceValidator;
import org.example.BusinessLogic.validators.ProductStockValidator;
import org.example.BusinessLogic.validators.Validator;
import org.example.DataAccess.ProductDAO;
import org.example.Model.Product;

/**
 * Clasa ProductBLL contine logica pentru obiectele de tip Product.
 * Aceasta clasa utilizeaza obiectele de tip Validator pentru a valida informatiile introduse de utilizator
 * inainte de a fi inserate sau actualizate in baza de date.
 */
public class ProductBLL {
    /** Lista de validatori pentru clasa Product. */
    private final List<Validator<Product>> validators;
    /** Constructorul clasei ProductBLL initializeaza o lista de validatori pentru obiectele de tip Product. */
    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new ProductPriceValidator());
        validators.add(new ProductStockValidator());
    }
    /**
     * Metoda cauta un obiect Product pe baza id-ului in baza de date folosind metoda din clasa ProductDAO.
     * @param id este id-ul produsului de cautat
     * @return product este produsul cu id-ul specificat
     * @throws NoSuchElementException daca nu este gasit produsul
     */
    public Product findProductById(int id) {
        Product product = ProductDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return product;
    }
    /**
     * Metoda insereaza un nou produs in baza de date dupa ce valideaza informatiile folosind metoda din clasa ProductDAO.
     * @param product este produsul de inserat
     * @return id-ul obiectului Product inserat in baza de date
     */
    public int insertProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        return ProductDAO.insert(product);
    }
    /**
     * Metoda cauta toate produsele de tip Product din baza de date folosind metoda din clasa ProductDAO.
     * @return o lista cu obiectele de tip Product gasite
     */
    public ArrayList<Object> findAllProducts() {
        return ProductDAO.findAll();
    }
    /**
     * Metoda sterge un produs din baza de date pe baza id-ului folosind metoda din clasa ProductDAO.
     * @param id este id-ul produsului de sters
     * @return un flag care specifica daca s-a sters sau nu produsul
     */
    public int deleteProduct(int id) {
        return ProductDAO.deleteProduct(id);
    }
    /**
     * Metoda actualizeaza un produs in baza de date cu informatiile furnizate ca parametri dupa validare folosind metoda din clasa ProductDAO.
     * @param product este produsul cu noile date
     * @param id este id-ul produsului de actualizat in baza de date
     * @return un flag care specifica daca s-a actualizat produsul sau nu
     */
    public int updateProduct(Product product, int id) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        return ProductDAO.updateProduct(product, id);
    }
}

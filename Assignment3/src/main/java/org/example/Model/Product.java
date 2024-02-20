package org.example.Model;

/**
 *  Clasa Product reprezinta modelul pentru un produs.
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    /**
     * Un constructor al clasei Product.
     * @param id id-ul produsului
     * @param name numele produsului
     * @param price pretul produsului
     * @param stock stocul produsului
     */
    public Product(int id, String name, double price, int stock) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    /**
     * Un constructor al clasei Product.
     * @param name numele produsului
     * @param price pretul produsului
     * @param stock stocul produsului
     */
    public Product(String name, double price, int stock) {
        super();
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Getter pentru id-ul produsului.
     * @return id-ul produsului
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pentru id-ul produsului
     * @param id id-ul de setat
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Getter pentru numele produsului.
     * @return numele produsului
     */
    public String getName() {
        return name;
    }
    /**
     * Getter pentru pretul produsului.
     * @return pretul produsului
     */
    public double getPrice() {
        return price;
    }
    /**
     * Getter pentru stocul produsului.
     * @return stocul produsului
     */
    public int getStock() {
        return stock;
    }
    /**
     * Suprascrierea metodei toString a clasei Object.
     * @return reprezentarea unui produs sub forma de String
     */
    @Override
    public String toString() {
        return id + ") Product " + name;
    }
}


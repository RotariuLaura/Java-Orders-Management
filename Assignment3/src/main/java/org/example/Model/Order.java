package org.example.Model;

public class Order {
    /**
     *  Clasa Order reprezinta modelul pentru o comanda.
     */
    private int id;
    private int idClient;
    private int idProduct;
    private int quantity;
    /**
     * Un constructor al clasei Order.
     * @param id id-ul comenzii
     * @param idClient id-ul clientului care face comanda
     * @param idProduct id-ul produsului comandat
     * @param quantity cantitatea de produs comandata
     */
    public Order(int id, int idClient, int idProduct, int quantity) {
        super();
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }
    /**
     * Un constructor al clasei Order.
     * @param idClient id-ul clientului care face comanda
     * @param idProduct id-ul produsului comandat
     * @param quantity cantitatea de produs comandata
     */
    public Order(int idClient, int idProduct, int quantity) {
        super();
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    /**
     * Getter pentru id-ul comenzii.
     * @return id-ul comenzii
     */
    public int getId() {
        return id;
    }

    /**
     * Setter pentru id-ul comenzii
     * @param id id-ul de setat
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Getter pentru id-ul clientului.
     * @return id-ul clientului
     */
    public int getIdClient() {
        return idClient;
    }
    /**
     * Getter pentru id-ul produsului.
     * @return id-ul produsului
     */
    public int getIdProduct() {
        return idProduct;
    }
    /**
     * Getter pentru cantitatea de produs.
     * @return cantitatea de produs
     */
    public int getQuantity() {
        return quantity;
    }
}

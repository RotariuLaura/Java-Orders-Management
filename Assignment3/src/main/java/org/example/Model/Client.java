package org.example.Model;

public class Client {
    /**
     * Clasa Client reprezinta modelul pentru un client.
     */
    private int id;
    private String name;
    private String address;
    private String email;

    /**
     * Un constructor al clasei Client.
     * @param id id-ul clientul
     * @param name numele clientului
     * @param address adresa clientului
     * @param email adresa de email a clientul
     */
    public Client(int id, String name, String address, String email) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    /**
     * Un constructor al clasei Client.
     * @param name numele clientului
     * @param address adresa clientului
     * @param email adresa de email a clientului
     */
    public Client(String name, String address, String email) {
        super();
        this.name = name;
        this.address = address;
        this.email = email;
    }

    /**
     * Getter pentru id-ul clientului.
     * @return id-ul clientului
     */
    public int getId() {
        return id;
    }

    /** Setter pentru id-ul clientului.
     * @param id este id-ul de setat
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Getter pentru numele clientului.
     * @return numele clientului
     */
    public String getName() {
        return name;
    }
    /**
     * Getter pentru adresa clientului.
     * @return adresa clientului
     */
    public String getAddress() {
        return address;
    }
    /**
     * Getter pentru adresa de email clientului.
     * @return adresa de email a clientului
     */
    public String getEmail() {
        return email;
    }

    /**
     * Suprascrierea metodei toString a clasei Object.
     * @return reprezentarea unui client sub forma de String
     */
    @Override
    public String toString() {
        return  id + ") Client " + name;
    }

}

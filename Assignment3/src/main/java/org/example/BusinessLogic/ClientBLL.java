package org.example.BusinessLogic;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.example.BusinessLogic.validators.EmailValidator;
import org.example.BusinessLogic.validators.Validator;
import org.example.DataAccess.ClientDAO;
import org.example.Model.Client;

/**
 * Clasa ClientBLL contine logica pentru obiectele de tip Client.
 * Aceasta clasa utilizeaza obiectele de tip Validator pentru a valida informatiile introduse de utilizator
 * inainte de a fi inserate sau actualizate in baza de date.
 */
public class ClientBLL {
    /** Lista de validatori pentru clasa Client. */
    private final List<Validator<Client>> validators;
    /** Constructorul clasei ClientBLL initializeaza o lista de validatori pentru obiectele de tip Client. */
    public ClientBLL() {
        validators = new ArrayList<>();
        validators.add(new EmailValidator());
    }

    /**
     * Metoda cauta un obiect Client pe baza id-ului in baza de date folosind metoda din clasa ClientDAO.
     * @param id este id-ul clientului de cautat
     * @return client este clientul cu id-ul specificat
     * @throws NoSuchElementException daca nu este gasit clientul
     */
    public Client findClientById(int id) {
        Client client = ClientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return client;
    }

    /**
     * Metoda insereaza un nou client in baza de date dupa ce valideaza informatiile folosind metoda din clasa ClientDAO.
     * @param client este clientul de inserat
     * @return id-ul obiectului Client inserat in baza de date
     */
    public int insertClient(Client client) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        return ClientDAO.insert(client);
    }

    /**
     * Metoda cauta toti clientii de tip Client din baza de date folosind metoda din clasa ClientDAO.
     * @return o lista cu obiectele de tip Client gasite
     */
    public ArrayList<Object> findAllClients() {
        return ClientDAO.findAll();
    }

    /**
     * Metoda sterge un client din baza de date pe baza id-ului.
     * @param id este id-ul clientului de sters
     * @return un flag care specifica daca s-a sters sau nu clientul
     */
    public int deleteClient(int id) {
        return ClientDAO.deleteClient(id);
    }

    /**
     * Metoda actualizeaza un client in baza de date cu informatiile furnizate ca parametri dupa validare.
     * @param client este clientul cu noile date
     * @param id este id-ul clientului de actualizat in baza de date
     * @return un flag care specifica daca s-a actualizat clientul sau nu
     */
    public int updateClient(Client client, int id) {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        return ClientDAO.updateClient(client, id);
    }
}

package org.example.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.Connection.ConnectionFactory;
import org.example.Model.Client;

/**
 * Clasa ClientDAO este folosita pentru accesarea si manipularea datelor din tabelul "client" din baza de date utilizata.
 */
public class ClientDAO {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO client (name,address,email)"
            + " VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM client where id = ?";
    private final static String findAllStatementString = "SELECT * FROM client";
    private final static String deleteStatementString = "DELETE FROM client WHERE id = ?";
    private final static String updateStatementString = "UPDATE client SET name=?, address=?, email=? WHERE id=?";

    /**
     * Metoda cauta clientul cu id-ul specificat in baza de date.
     * @param clientId este id-ul clientului de cautat
     * @return clientul gasit sau null daca nu exista in baza de date
     */
    public static Client findById(int clientId) {
        Client toReturn = null;
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, clientId);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            String address = rs.getString("address");
            String email = rs.getString("email");
            toReturn = new Client(clientId, name, address, email);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Metoda cauta toti clientii din baza de date.
     * @return o lista cu toti clientii gasiti in baza de date
     */
    public static ArrayList<Object> findAll(){
        ArrayList <Object> clients = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findAllStatement = null;
        ResultSet rs = null;
        try{
            findAllStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = findAllStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String email = rs.getString("email");
                clients.add(new Client(id, name, address, email));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"ClientDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findAllStatement);
            ConnectionFactory.close(dbConnection);
        }
        return clients;
    }

    /**
     * Metoda insereaza un nou client in baza de date.
     * @param client este clientul ce va fi inserat
     * @return id-ul clientului inserat
     */

    public static int insert(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getAddress());
            insertStatement.setString(3, client.getEmail());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Metoda sterge un client din baza de date pe baza id-ului specificat.
     * @param clientId este id-ul clientului de sters
     * @return un flag care specifica daca clientul a fost sters cu succes sau nu
     */
    public static int deleteClient(int clientId){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        int deleted = 0;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, clientId);
            int rowsAffected = deleteStatement.executeUpdate();
            if(rowsAffected > 0){
                deleted = 1;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:deleteClient " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deleted;
    }

    /**
     * Metoda actualizeaza informatiile pentru un client existent din baza de date.
     * @param client contine noile informatiile pentru client
     * @param id este id-ul clientului de modificat
     * @return un flag care specifica daca clientul a fost modificat cu succes sau nu
     */
    public static int updateClient(Client client, int id) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        int updated = 0;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, client.getName());
            updateStatement.setString(2, client.getAddress());
            updateStatement.setString(3, client.getEmail());
            updateStatement.setInt(4, id);
            updateStatement.executeUpdate();
            int rowsAffected = updateStatement.executeUpdate();
            if(rowsAffected > 0) {
                updated = 1;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return updated;
    }
}

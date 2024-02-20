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
import org.example.Model.Order;

/**
 * Clasa OrderDAO este folosita pentru accesarea si manipularea datelor din tabelul "orders" din baza de date utilizata.
 */
public class OrderDAO {
    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO orders (idClient,idProduct,quantity)"
            + " VALUES (?,?,?)";
    private final static String findAllStatementString = "SELECT * FROM orders";

    /**
     * Metoda cauta toate comenzile de tipul Order din baza de date.
     * @return o lista cu obiectele gasite
     */
    public static ArrayList<Object> findAll(){
        ArrayList <Object> orders = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findAllStatement = null;
        ResultSet rs = null;
        try{
            findAllStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = findAllStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int idClient = rs.getInt("idClient");
                int idProduct = rs.getInt("idProduct");
                int quantity = rs.getInt("quantity");
                orders.add(new Order(id, idClient, idProduct, quantity));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"OrderDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findAllStatement);
            ConnectionFactory.close(dbConnection);
        }
        return orders;
    }

    /**
     * Metoda insereaza o noua comanda in baza de date.
     * @param order este comanda de tipul Order de inserat
     * @return id-ul comenzii adaugate in baza de date
     */
    public static int insert(Order order) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, String.valueOf(order.getIdClient()));
            insertStatement.setString(2, String.valueOf(order.getIdProduct()));
            insertStatement.setString(3, String.valueOf(order.getQuantity()));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }
}

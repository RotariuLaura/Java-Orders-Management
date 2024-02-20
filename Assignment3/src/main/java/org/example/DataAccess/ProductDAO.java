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
import org.example.Model.Product;

/**
 * Clasa ProductDAO este folosita pentru accesarea si manipularea datelor din tabelul "product" din baza de date utilizata.
 */
public class ProductDAO {
    protected static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO product (name,price,stock)"
            + " VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM product where id = ?";
    private final static String findAllStatementString = "SELECT * FROM product";
    private final static String deleteStatementString = "DELETE FROM product WHERE id = ?";
    private final static String updateStatementString = "UPDATE product SET name=?, price=?, stock=? WHERE id=?";

    /**
     * Metoda cauta un produs in baza de date pe baza id-ului specificat.
     * @param productId este id-ul produsul de cautat
     * @return produsul de tip Product gasit sau null daca nu a fost gasit
     */
    public static Product findById(int productId) {
        Product toReturn = null;
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, productId);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            int price = rs.getInt("price");
            int stock = rs.getInt("stock");
            toReturn = new Product(productId, name, price, stock);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ProductDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Metoda cauta toate produsele din baza de date.
     * @return o lista cu toate produsele gasite
     */
    public static ArrayList<Object> findAll(){
        ArrayList <Object> products = new ArrayList<>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findAllStatement = null;
        ResultSet rs = null;
        try{
            findAllStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = findAllStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getInt("price");
                int stock = rs.getInt("stock");
                products.add(new Product(id, name, price, stock));
            }
        } catch (SQLException e){
            LOGGER.log(Level.WARNING,"ProductDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findAllStatement);
            ConnectionFactory.close(dbConnection);
        }
        return products;
    }

    /**
     * Metoda insereaza un nou produs in baza de date
     * @param product este produsul de inserat
     * @return id-ul produsul inserat
     */
    public static int insert(Product product) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, product.getName());
            insertStatement.setString(2, String.valueOf(product.getPrice()));
            insertStatement.setString(3, String.valueOf(product.getStock()));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Metoda sterge un produs din baza de date pe baza id-ului specificat.
     * @param productId este id-ul produsului de sters
     * @return un flag care specifica daca produsul a fost sters cu succes sau nu
     */
    public static int deleteProduct(int productId){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        int deleted = 0;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, productId);
            int rowsAffected = deleteStatement.executeUpdate();
            if(rowsAffected > 0){
                deleted = 1;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:deleteProduct " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deleted;
    }

    /**
     * Metoda actualizeaza informatiile unui produs existent pe baza informatiile specificate.
     * @param product este produsul cu noile informatii
     * @param id este id-ul produsului de modificat
     * @return un flag care specifica daca produsul a fost actualizat cu succes sau nu
     */
    public static int updateProduct(Product product, int id) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        int updated = 0;
        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, product.getName());
            updateStatement.setString(2, String.valueOf(product.getPrice()));
            updateStatement.setString(3, String.valueOf(product.getStock()));
            updateStatement.setInt(4, id);
            updateStatement.executeUpdate();
            int rowsAffected = updateStatement.executeUpdate();
            if(rowsAffected > 0) {
                updated = 1;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return updated;
    }
}

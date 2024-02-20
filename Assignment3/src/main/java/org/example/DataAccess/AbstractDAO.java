package org.example.DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.example.Connection.ConnectionFactory;

/**
 * Clasa AbstractDAO - pentru accesarea si manipularea datelor din tabelele din baza de date utilizata.
 */

public class AbstractDAO <T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class <T> type;
    public AbstractDAO(){
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * pentru compunerea query-ului de cautare dupa id
     * @return string
     */
    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + "id" + " =?");
        return sb.toString();
    }
    /**
     * pentru compunerea query-ului de cautare
     * @return string
     */
    private String createSelectAllQuery(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }
    /**
     * pentru compunerea query-ului de inserare
     * @return string
     */
    private String createInsertQuery(T object){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");
        int i = 0;
        for(Field field : type.getDeclaredFields()){
            String fieldName = field.getName();
            if(i > 0){
                sb.append(", ");
            }
            sb.append(fieldName);
            i++;
        }
        sb.append(") VALUES (");
        for(i = 0; i < type.getDeclaredFields().length; i++){
            if(i > 0){
                sb.append(", ");
            }
            sb.append("?");
            i++;
        }
        sb.append(")");
        return sb.toString();
    }
    /**
     * pentru compunerea query-ului de actualizare
     * @return string
     */
    private String createUpdateQuery(T object){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        int i = 0;
        for(Field field : type.getDeclaredFields()){
            String fieldName = field.getName();
            if(fieldName.equals("id")){
                continue;
            }
            if(i > 0){
                sb.append(", ");
            }
            sb.append(fieldName);
            sb.append("=?");
            i++;
        }
        sb.append(" WHERE id=?");
        return sb.toString();
    }
    /**
     * pentru compunerea query-ului de stergere
     * @return string
     */
    private String createDeleteQuery(){
        return "DELETE FROM " +
                type.getSimpleName() +
                " WHERE id=?";
    }

    /**
     * metoda pentru cautare obiect
     * @param id este id-ul obiectului de cautat
     * @return obiectul
     */
    public Object findById(int id) {
        Connection dbConnection = null;
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        String query = createSelectQuery();
        try {
            dbConnection = ConnectionFactory.getConnection();
            findStatement = dbConnection.prepareStatement(query);
            findStatement.setLong(1, id);
            rs = findStatement.executeQuery();
            return createObjects(rs).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return null;
    }
    /**
     * metoda pentru cautare obiecte
     * @return lista de obiecte
     */
    public ArrayList<T> findAll() {
        Connection dbConnection = null;
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        String query = createSelectAllQuery();
        try {
            dbConnection = ConnectionFactory.getConnection();
            findStatement = dbConnection.prepareStatement(query);
            rs = findStatement.executeQuery();
            return createObjects(rs);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return null;
    }
    /**
     * metoda pentru creare obiecte
     * @param resultSet
     * @return lista obiecte
     */
    private ArrayList<T> createObjects(ResultSet resultSet) {
        ArrayList<T> list = new ArrayList<>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (Constructor constructor : ctors) {
            ctor = constructor;
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IntrospectionException | SecurityException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * metoda pentru inserare obiect
     * @param t obiectul de inserat
     * @return id-ul obiectului inserat
     */
    public int insert(T t) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        String query = createInsertQuery(t);
        int insertedId = -1, i = 1;
        try {
            insertStatement = dbConnection.prepareStatement(query);
            for(Field field : t.getClass().getDeclaredFields()){
                field.setAccessible(true);
                Object value = field.get(t);
                insertStatement.setObject(i, value);
                i++;
            }
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }
    /**
     * metoda pentru stergere obiect
     * @param id este id-ul obiectului de sters
     * @return flag
     */
    public int delete(int id){
        String query = createDeleteQuery();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        int deleted = 0;
        try {
            deleteStatement = dbConnection.prepareStatement(query);
            deleteStatement.setInt(1, id);
            int rowsAffected = deleteStatement.executeUpdate();
            if(rowsAffected > 0){
                deleted = 1;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deleted;
    }
    /**
     * metoda pentru actualizare obiect
     * @param t obiectul cu noile detalii
     * @param id este id-ul obiectului de actualizat
     * @return obiectul
     */
    public int update(T t, int id){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        String query = createUpdateQuery(t);
        int updated = 0, i = 1;
        try {
            updateStatement = dbConnection.prepareStatement(query);
            for(Field field : t.getClass().getDeclaredFields()){
                field.setAccessible(true);
                String fieldName = field.getName();
                if(fieldName.equals("id")){
                    continue;
                }
                Object value = field.get(t);
                updateStatement.setObject(i++, value);
            }
            updateStatement.setInt(i, id);
            int rowsAffected = updateStatement.executeUpdate();
            if(rowsAffected > 0) {
                updated = 1;
            }
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
        return updated;
    }
}

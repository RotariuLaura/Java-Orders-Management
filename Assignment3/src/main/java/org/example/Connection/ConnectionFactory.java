package org.example.Connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa ConnectionFactory este pentru a crea conexiunile la baza de date si inchiderea corecta a acestora.
 * Clasa are metode pentru a crea si inchide conexiunile la baza de date, instructiuni si seturi de rezultate.
 */
public class ConnectionFactory {
    /**
     * Obiectul LOGGER este folosit pentru a transmite avertismente.
     */
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    /**
     *  Constanta DRIVER contine numele complet al clasei driverului JDBC.
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /**
     * Constanta DBURl contine URL-ul bazei de date la care se va conecta.
     */
    private static final String DBURL = "jdbc:mysql://127.0.0.1:3306/schooldb";
    /**
     *  Constanta USER contine numele de utlizator pentru conectarea la baza de date.
     */
    private static final String USER = "root";
    /**
     *  Constanta PASS contine parola pentru conectarea la baza de date.
     */
    private static final String PASS = "12345678";
    /**
     * Constanta singleInstance este o instanta a clasei ConnectionFactory.
     */
    private static ConnectionFactory singleInstance = new ConnectionFactory();
    /**
     * Constructorul privat al clasei initializeaza driverul JDBC.
     */
    private ConnectionFactory(){
        try{
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     *  Metoda creeaza o conexiune la baza de date.
     * @return un obiect de tipul Connection care reprezinta conexiunea realizata la baza de date
     */
    private Connection createConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error-connecting to the database.");
            e.printStackTrace();
        }
        return connection;
    }

    /** Metoda returneaza conexiunea la baza de date prin instanta clasei ConnectionFactory.
     *
     * @return conexiunea de tip Connection la baza de date
     */
    public static Connection getConnection(){
        return singleInstance.createConnection();
    }

    /** Metoda inchide conexiunea la baza de date.
     *
     * @param connection este instanta clasei Connection de inchis
     */
    public static void close(Connection connection){
        if(connection != null){
            try{
                connection.close();
            } catch (SQLException e){
                LOGGER.log(Level.WARNING, "Error-closing the connection.");
            }
        }
    }

    /**
     *  Metoda inchide statement-ul.
     * @param statement este obiectul Statement de inchis
     */
    public static void close(Statement statement){
        if(statement != null){
            try{
                statement.close();
            } catch (SQLException e){
                LOGGER.log(Level.WARNING, "Error-closing the statement");
            }
        }
    }

    /**
     *  Metoda inchide setul de rezultate.
     * @param resultSet este obiectul ResultSet de inchis
     */
    public static void close(ResultSet resultSet){
        if(resultSet != null){
            try{
                resultSet.close();
            } catch (SQLException e){
                LOGGER.log(Level.WARNING, "Error-closing the resultSet.");
            }
        }

    }
}

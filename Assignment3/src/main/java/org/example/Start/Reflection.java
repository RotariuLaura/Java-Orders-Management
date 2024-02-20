package org.example.Start;

import org.example.Model.Client;
import org.example.Model.Order;
import org.example.Model.Product;
import org.example.Presentation.ClientTableModel;
import org.example.Presentation.OrderTableModel;
import org.example.Presentation.ProductTableModel;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Clasa Reflection este utilizata pentru a genera un tabel pornind de la o lista de obiecte folosind tehnice de reflectie, generand header-ul tabelului cu proprietatile
 * si apoi populandu-l cu valorile elementelor din lista. Clasa contine doua metode care ajuta la crearea tabelului.
 */
public class Reflection {
    private static String [] columnNames;
    private static Object [][] rowData;

    /**
     * Metoda primeste o lista de obiecte si returneaza un JTable in functie de tipul clasei careia apartin obiectele primite in lista data ca parametru.
     * Metoda este folosita in metoda retrivePropertiesAndValuesForList,
     *  @param objects lista de obiecte ce vor aparea in tabel
     * @return un tabel JTabel
     */
    public static JTable createTable(ArrayList <Object> objects){
        JTable table = new JTable(rowData, columnNames);
        if(objects.get(0).getClass().equals(Client.class)) {
            ClientTableModel model = new ClientTableModel(columnNames, rowData);
            table.setModel(model);
        }
        else if(objects.get(0).getClass().equals(Product.class)){
            ProductTableModel model = new ProductTableModel(columnNames, rowData);
            table.setModel(model);
        }
        else if(objects.get(0).getClass().equals(Order.class)){
            OrderTableModel model = new OrderTableModel(columnNames, rowData);
            table.setModel(model);
        }
        return table;
    }

    /**
     * Metoda creeaza un tabel folosind metoda createTable si completeaza coloanele si randurile acestuia cu proprietatile si valorile obiectelor date in lista, folosind reflectia.
     * @param objects lista de obiecte ce va fi afisata in tabel
     * @return tabelul JTable creat cu datele specificate
     */
    public static JTable retrivePropertiesAndValuesForList(ArrayList<Object> objects){
        ArrayList <String> fields = new ArrayList<>();
        for (Field field : objects.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                fields.add(field.getName());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        int n = fields.size();
        columnNames = new String[n];
        for(int i = 0; i < n; i++){
            columnNames[i] = fields.get(i);
        }
        rowData = new Object[objects.size()][n];
        for(int i = 0; i < objects.size(); i++){
            Object object = objects.get(i);
            for(int j = 0; j < n; j++){
                try {
                    Field field = object.getClass().getDeclaredField(fields.get(j));
                    field.setAccessible(true);
                    Object value = field.get(object);
                    rowData[i][j] = value;
                } catch (NoSuchFieldException | IllegalAccessException ex){
                    ex.printStackTrace();
                }
            }
        }
        return Reflection.createTable(objects);
    }
}


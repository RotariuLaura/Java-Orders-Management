package org.example.Presentation;

import javax.swing.table.AbstractTableModel;

/**
 * Clasa ClientTableModel defineste un model de tabel pentru a afisa o lista de clienti in interfata grafica.
 * Clasa mosteneste clasa AbstractTableModel care furnizeaza o implementare de baza pentru tabele.
 */
public class ClientTableModel extends AbstractTableModel {
    public static final int ID_COL = 0;
    private static final int NAME_COL = 1;
    private static final int ADDRESS_COL = 2;
    private static final int EMAIL_COL = 3;
    private String[] columnNames;
    private Object[][] rowData;

    /**
     * Constructorul clasei ClientTableModel.
     * @param columnNames contine numele coloanelor tabelului
     * @param rowData contine datele pentru fiecare rand al tabelului
     */

    public ClientTableModel(String[] columnNames, Object[][] rowData) {
        this.columnNames = columnNames;
        this.rowData = rowData;
    }

    /**
     * Metoda returneaza numarul de randuri din tabel, lungimea array-ului rowData.
     * @return numarul de randuri din tabel
     */
    @Override
    public int getRowCount() {
        return rowData.length;
    }

    /**
     * Metoda returneaza numarul de coloane din tabel, care este egal cu lungimea array-ului columnNames.
     * @return numarul de coloane din tabel
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Metoda returneaza numele coloanei specificate.
     * @param col este coloana interogata
     * @return numele coloanei
     */

    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Metoda returneaza valoarea de la pozitia specificata din tabel.
     * @param rowIndex este randul interogat
     * @param columnIndex este coloana interogata
     * @return valoarea de la pozitia specificata prin parametri dati
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case ID_COL -> rowData[rowIndex][0];
            case NAME_COL -> rowData[rowIndex][1];
            case ADDRESS_COL -> rowData[rowIndex][2];
            case EMAIL_COL -> rowData[rowIndex][3];
            default -> rowData[rowIndex];
        };
    }
}
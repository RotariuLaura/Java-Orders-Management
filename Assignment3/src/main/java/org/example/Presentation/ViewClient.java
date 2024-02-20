package org.example.Presentation;

import org.example.BusinessLogic.ClientBLL;
import org.example.Model.Client;
import org.example.Start.Reflection;

import javax.swing.*;
import java.awt.*;

/**
 * Clasa ViewClient implementeaza interfata grafica pentru gestionarea clientilor.
 * Aceasta clasa include butoane pentru vizualizarea sub forma de tabel a clientilor, inserarea, actualizarea si stergerea clientilor.
 */
public class ViewClient {
    private JFrame clientFrame;
    private JButton buttonInsertClient, buttonUpdateClient, buttonDeleteClients, buttonViewClients;
    public JTable table;
    public JScrollPane scrollPane;
    private ClientBLL clientBLL;

    /**
     * Constructorul fara parametri ai clasei ViewClient.
     * Aici se initializeaza fereastra prin adaugarea componentelor necesare.
     */
    public ViewClient() {
        clientFrame = new JFrame();
        clientFrame.setTitle("CLIENTS");
        clientFrame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 12));
        clientFrame.setBounds(100, 100, 500, 450);
        clientFrame.setBackground(Color.LIGHT_GRAY);
        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientFrame.getContentPane().setLayout(null);
        clientFrame.setResizable(false);

        JLabel labelOperations = new JLabel("Client operations:");
        labelOperations.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        labelOperations.setBounds(180, 10, 215, 20);
        labelOperations.setForeground(Color.DARK_GRAY);
        clientFrame.getContentPane().add(labelOperations);

        JLabel label = new JLabel("The clients: ");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        label.setBounds(200, 150, 215, 20);
        label.setForeground(Color.DARK_GRAY);
        clientFrame.getContentPane().add(label);

        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        panel.setBounds(20, 200, 450, 160);
        clientFrame.getContentPane().add(panel);
        panel.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 450, 160);
        panel.add(scrollPane);


        buttonViewClients = new JButton("View clients");
        buttonViewClients.addActionListener(e -> {
            clientBLL = new ClientBLL();
            table = Reflection.retrivePropertiesAndValuesForList(clientBLL.findAllClients());
            scrollPane.setViewportView(table);
        });
        buttonViewClients.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        buttonViewClients.setBounds(30, 50, 200, 30);
        buttonViewClients.setBackground(Color.lightGray);
        clientFrame.getContentPane().add(buttonViewClients);

        buttonInsertClient = new JButton("Insert client");
        buttonInsertClient.addActionListener(e -> {
            UpdateInsertClientDialog dialog = new UpdateInsertClientDialog(new ViewClient(), null, 1);
            dialog.setVisible(true);
        });
        buttonInsertClient.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        buttonInsertClient.setBounds(260, 50, 200, 30);
        buttonInsertClient.setBackground(Color.lightGray);
        clientFrame.getContentPane().add(buttonInsertClient);

        buttonUpdateClient = new JButton("Update client");
        buttonUpdateClient.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(clientFrame, "You must select a client", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int id = (int) table.getValueAt(row, ClientTableModel.ID_COL);
                clientBLL = new ClientBLL();
                Client tempClient = clientBLL.findClientById(id);
                UpdateInsertClientDialog dialog = new UpdateInsertClientDialog(new ViewClient(), tempClient, 0);
                dialog.setVisible(true);
            } catch (Exception ignored){
            }
        });
        buttonUpdateClient.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        buttonUpdateClient.setBounds(30, 90, 200, 30);
        buttonUpdateClient.setBackground(Color.lightGray);
        clientFrame.getContentPane().add(buttonUpdateClient);

        buttonDeleteClients = new JButton("Delete client");
        buttonDeleteClients.addActionListener(e -> {
            int id;
            try {
                int row = table.getSelectedRow();
                if (row < 0) {
                    JOptionPane.showMessageDialog(clientFrame, "You must select a client", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                id = (int) table.getValueAt(row, ClientTableModel.ID_COL);
                clientBLL = new ClientBLL();
                if (clientBLL.deleteClient(id) == 1) {
                    table = Reflection.retrivePropertiesAndValuesForList(clientBLL.findAllClients());
                    scrollPane.setViewportView(table);
                    JOptionPane.showMessageDialog(clientFrame, "Client deleted", "Client updated", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(clientFrame, "This client made an order, cannot delete", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ignored) {
            }
        });
        buttonDeleteClients.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        buttonDeleteClients.setBounds(260, 90, 200, 30);
        buttonDeleteClients.setBackground(Color.lightGray);
        clientFrame.getContentPane().add(buttonDeleteClients);

    }
    /**
     * Metoda returneaza fereastra JFrame din interfata pentru categoria clientilor.
     * @return un obiect de tipul JFrame
     */
    public JFrame getClientFrame() {
        return this.clientFrame;
    }
}

package org.example.Presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Clasa View reprezinta interfata garfica pentru o aplicatie de gestionare a comenzilor.
 * Aceasta clasa are o fereastra principala cu butoane pentru accesarea diferitelor categorii (clienti, produse si comenzi).
 *
 */
public class View {
    private JFrame startFrame;
    private final JLabel titleLabel;
    private final JLabel messageLabel;
    private final JButton buttonClients, buttonProducts, buttonOrders;

    /**
     * Constructorul fara parametri ai clasei View.
     * Aici se creeaza fereastra principala a aplicatiei prin adaugarea componentelor necesare.
     */
    public View() {
        startFrame = new JFrame();
        startFrame.setTitle("ORDERS MANAGEMENT");
        startFrame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 12));
        startFrame.setBounds(100, 100, 350, 320);
        startFrame.setBackground(Color.LIGHT_GRAY);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.getContentPane().setLayout(null);

        titleLabel = new JLabel("ORDERS MANAGEMENT");
        titleLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        titleLabel.setBounds(85, 11, 215, 20);
        titleLabel.setForeground(Color.DARK_GRAY);
        startFrame.getContentPane().add(titleLabel);

        messageLabel = new JLabel("Click on a button to see a category!");
        messageLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        messageLabel.setBounds(60, 40, 250, 20);
        messageLabel.setForeground(Color.DARK_GRAY);
        startFrame.getContentPane().add(messageLabel);

        buttonClients = new JButton("CLIENTS");
        buttonClients.addActionListener(e -> {
            ViewClient viewClient = new ViewClient();
            viewClient.getClientFrame().setVisible(true);
        });
        buttonClients.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        buttonClients.setBounds(60, 72, 215, 50);
        buttonClients.setBackground(Color.lightGray);
        startFrame.getContentPane().add(buttonClients);

        buttonProducts = new JButton("PRODUCTS");
        buttonProducts.addActionListener(e -> {
            ViewProduct viewProduct = new ViewProduct();
            viewProduct.getProductFrame().setVisible(true);
        });
        buttonProducts.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        buttonProducts.setBounds(60, 140, 215, 50);
        buttonProducts.setBackground(Color.lightGray);
        startFrame.getContentPane().add(buttonProducts);

        buttonOrders = new JButton("ORDERS");
        buttonOrders.addActionListener(e -> {
            ViewOrder viewOrder = new ViewOrder();
            viewOrder.getOrderFrame().setVisible(true);
        });
        buttonOrders.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        buttonOrders.setBounds(60, 208, 215, 50);
        buttonOrders.setBackground(Color.lightGray);
        startFrame.getContentPane().add(buttonOrders);
    }

    /**
     * Metoda returneaza obiectul de tipul JFrame care reprezinta fereastra principala a aplicatiei.
     * @return fereastra principala care este un obiect de tipul JFrame
     */
    public JFrame getStartFrame() {
        return startFrame;
    }
}


package org.example.Presentation;

import org.example.BusinessLogic.OrderBLL;
import org.example.BusinessLogic.ProductBLL;
import org.example.DataAccess.ClientDAO;
import org.example.DataAccess.ProductDAO;
import org.example.Model.Client;
import org.example.Model.Order;
import org.example.Model.Product;
import org.example.Start.Reflection;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Clasa ViewOrder implementeaza interfata grafica pentru gestionarea comenzilor.
 * Aceasta clasa include butoane pentru vizualizarea sub forma de tabel a comenzilor si adaugarea comenzilor.
 */
public class ViewOrder {
    private JFrame orderFrame;
    private JButton buttonCreateOrder, buttonViewOrders;
    private JLabel label, labelOperations;
    private JTable table;
    public JComboBox<Object> comboBox1;
    public JComboBox comboBox2;
    private JTextField textQuantity;
    /**
     * Constructorul fara parametri ai clasei ViewOrder.
     * Aici se initializeaza fereastra prin adaugarea componentelor necesare.
     */
    public ViewOrder() {
        orderFrame = new JFrame();
        orderFrame.setTitle("ORDERS");
        orderFrame.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 12));
        orderFrame.setBounds(100, 100, 500, 500);
        orderFrame.setBackground(Color.LIGHT_GRAY);
        orderFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        orderFrame.getContentPane().setLayout(null);
        orderFrame.setResizable(false);

        labelOperations = new JLabel("View and create product orders:");
        labelOperations.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        labelOperations.setBounds(140, 10, 300, 20);
        labelOperations.setForeground(Color.DARK_GRAY);
        orderFrame.getContentPane().add(labelOperations);

        label = new JLabel("The orders: ");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        label.setBounds(200, 220, 215, 20);
        label.setForeground(Color.DARK_GRAY);
        orderFrame.getContentPane().add(label);

        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        panel.setBounds(20, 260, 450, 160);
        orderFrame.getContentPane().add(panel);
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 450, 160);
        panel.add(scrollPane);

        JLabel lblClient = new JLabel("Client=");
        lblClient.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblClient.setBounds(38, 100, 66, 28);
        orderFrame.getContentPane().add(lblClient);

        JLabel lblProduct = new JLabel("Product=");
        lblProduct.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblProduct.setBounds(38, 140, 66, 28);
        orderFrame.getContentPane().add(lblProduct);

        JLabel lblQuantity = new JLabel("Quantity=");
        lblQuantity.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblQuantity.setBounds(38, 180, 66, 28);
        orderFrame.getContentPane().add(lblQuantity);

        comboBox1 = new JComboBox<>();
        comboBox1.setModel(new DefaultComboBoxModel<>(ClientDAO.findAll().toArray(new Object[0])));
        comboBox1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        comboBox1.setBounds(115, 100, 200, 28);
        comboBox1.setBackground(Color.WHITE);
        orderFrame.getContentPane().add(comboBox1);

        comboBox2 = new JComboBox();
        comboBox2.setModel(new DefaultComboBoxModel(ProductDAO.findAll().toArray(new Object[0])));
        comboBox2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        comboBox2.setBounds(115, 140, 200, 28);
        comboBox2.setBackground(Color.WHITE);
        orderFrame.add(comboBox2);

        textQuantity = new JTextField();
        textQuantity.setBounds(115, 180, 200, 28);
        orderFrame.add(textQuantity);
        textQuantity.setColumns(10);

        buttonViewOrders = new JButton("View orders");
        buttonViewOrders.addActionListener(e -> {
            OrderBLL orderBLL = new OrderBLL();
            table = Reflection.retrivePropertiesAndValuesForList(orderBLL.findAllOrders());
            scrollPane.setViewportView(table);
        });
        buttonViewOrders.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        buttonViewOrders.setBounds(30, 50, 200, 30);
        buttonViewOrders.setBackground(Color.lightGray);
        orderFrame.getContentPane().add(buttonViewOrders);

        buttonCreateOrder = new JButton("Create order");
        buttonCreateOrder.addActionListener(e -> {
            try {
                int clientId = ((Client) Objects.requireNonNull(comboBox1.getSelectedItem())).getId();
                int productId = ((Product) Objects.requireNonNull(comboBox2.getSelectedItem())).getId();
                int quantity = Integer.parseInt(textQuantity.getText());
                ProductBLL productBLL = new ProductBLL();
                Product product = productBLL.findProductById(productId);
                int stock = product.getStock();
                if (quantity > stock) {
                    JOptionPane.showMessageDialog(orderFrame, "Under-stock!", "Under-stock!", JOptionPane.ERROR_MESSAGE);
                } else {
                    Order newOrder = new Order(clientId, productId, quantity);
                    OrderBLL orderBLL = new OrderBLL();
                    if (orderBLL.insertOrder(newOrder) != -1) {
                        table = Reflection.retrivePropertiesAndValuesForList(orderBLL.findAllOrders());
                        scrollPane.setViewportView(table);
                        JOptionPane.showMessageDialog(orderFrame, "Order created", "Order created", JOptionPane.INFORMATION_MESSAGE);
                        Product newProduct = new Product(product.getName(), product.getPrice(), stock - quantity);
                        productBLL.updateProduct(newProduct, productId);
                    } else {
                        JOptionPane.showMessageDialog(orderFrame, "Error creating order", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception exception){
                JOptionPane.showMessageDialog(orderFrame, "Introduce the data for the order", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonCreateOrder.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        buttonCreateOrder.setBounds(260, 50, 200, 30);
        buttonCreateOrder.setBackground(Color.lightGray);
        orderFrame.getContentPane().add(buttonCreateOrder);
    }

    /**
     * Metoda returneaza fereastra JFrame din interfata pentru categoria comenzilor.
     * @return un obiect de tipul JFrame
     */
    public JFrame getOrderFrame() {
        return this.orderFrame;
    }
}

package org.example.Presentation;

import org.example.BusinessLogic.ClientBLL;
import org.example.Model.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Clasa UpdateInsertClientDialog implementeaza o fereastra pentru inserarea sau actualizarea datelor pentru clienti.
 * Aceasta clasa extinde clasa JDialog.
 */
public class UpdateInsertClientDialog extends JDialog {
    private final JPanel contentPanel = new JPanel();
    private JTextField nameTextField;
    private JTextField addressTextField;
    private JTextField emailTextField;
    private ViewClient viewClient;

    /**
     * Constructorul clasei UpdateInsertClientDialog.
     * @param viewClient_ un obiect de tipul clasei ViewClient, fereastra cu operatii pe clienti
     * @param client un obiect de tipul clasei Client care va fi inserat/actualizat
     * @param insert o variabila de tip intreg care indica daca se va face inserarea sau actualizarea unui client
     */
    public UpdateInsertClientDialog(ViewClient viewClient_, Client client, int insert) {
        this.viewClient = viewClient_;
        if (insert == 1) {
            setTitle("Insert Client");
        } else {
            setTitle("Update Client");
        }
        setBounds(100, 100, 347, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JLabel lblNume = new JLabel("Name=");
        lblNume.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblNume.setBounds(38, 62, 66, 28);
        contentPanel.add(lblNume);

        JLabel lblAddress = new JLabel("Address=");
        lblAddress.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblAddress.setBounds(38, 101, 66, 28);
        contentPanel.add(lblAddress);

        JLabel lblEmail = new JLabel("Email=");
        lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblEmail.setBounds(38, 140, 66, 28);
        contentPanel.add(lblEmail);

        nameTextField = new JTextField();
        nameTextField.setBounds(115, 62, 200, 28);
        contentPanel.add(nameTextField);
        nameTextField.setColumns(10);

        addressTextField = new JTextField();
        addressTextField.setBounds(115, 101, 200, 28);
        contentPanel.add(addressTextField);
        addressTextField.setColumns(10);

        emailTextField = new JTextField();
        emailTextField.setBounds(115, 140, 200, 28);
        contentPanel.add(emailTextField);
        emailTextField.setColumns(10);

        if (insert == 0) {
            nameTextField.setText(client.getName());
            addressTextField.setText(client.getAddress());
            emailTextField.setText(client.getEmail());
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        {
            JButton okButton = new JButton("Save");
            okButton.addActionListener(arg0 -> {
                try {
                    if (insert == 0) {
                        String nume = nameTextField.getText();
                        String address = addressTextField.getText();
                        String email = emailTextField.getText();
                        Client newClient = new Client(nume, address, email);
                        ClientBLL clientBLL = new ClientBLL();
                        int i = client.getId();
                        if (clientBLL.updateClient(newClient, i) == 1) {
                            setVisible(false);
                            dispose();
                            JOptionPane.showMessageDialog(viewClient.getClientFrame(), "Client updated", "Client updated", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(viewClient.getClientFrame(), "Error updating client", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        String nume = nameTextField.getText();
                        String address = addressTextField.getText();
                        String email = emailTextField.getText();
                        Client newClient = new Client(nume, address, email);
                        ClientBLL clientBLL = new ClientBLL();
                        if (clientBLL.insertClient(newClient) != -1) {
                            setVisible(false);
                            dispose();
                            JOptionPane.showMessageDialog(viewClient.getClientFrame(), "Client added", "Client updated", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(viewClient.getClientFrame(), "Error adding client", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex1){
                    JOptionPane.showMessageDialog(viewClient.getClientFrame(), "Introduce the data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            okButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            okButton.setBackground(Color.lightGray);
            okButton.setActionCommand("OK");
            buttonPane.add(okButton);
            getRootPane().setDefaultButton(okButton);
        }
        {
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> {
                setVisible(false);
                dispose();
            });
            cancelButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            cancelButton.setBackground(Color.lightGray);
            cancelButton.setActionCommand("Cancel");
            buttonPane.add(cancelButton);
        }
    }
}
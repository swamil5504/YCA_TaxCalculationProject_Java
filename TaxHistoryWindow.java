package com.gvp.orm.YCA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class TaxHistoryWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private DefaultTableModel model;
    private JTable table;
    private List<TaxRecord> taxRecords;

    public TaxHistoryWindow(List<TaxRecord> taxRecords) {
        this.taxRecords = taxRecords;
        setTitle("Tax History");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Search Panel at Top
        JPanel searchPanel = new JPanel();
        JLabel idLabel = new JLabel("Enter ID:");
        JTextField idField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        JButton resetButton = new JButton("Reset");

        searchPanel.add(idLabel);
        searchPanel.add(idField);
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);
        add(searchPanel, BorderLayout.NORTH);

        // Table Setup
        String[] columnNames = {"ID", "Year", "Tax Amount", "Status"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // **IMPORTANT CHANGE:**
        // Do NOT populate table initially (leave empty)
        //populateTable(taxRecords); --> COMMENTED OUT

        // Search button action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = idField.getText().trim();
                if (!idText.isEmpty()) {
                    try {
                        int searchId = Integer.parseInt(idText);
                        List<TaxRecord> filtered = taxRecords.stream()
                                .filter(record -> record.getid() == searchId)
                                .collect(Collectors.toList());
                        if (filtered.isEmpty()) {
                            JOptionPane.showMessageDialog(TaxHistoryWindow.this, 
                                "No records found for ID: " + searchId, 
                                "No Record", JOptionPane.INFORMATION_MESSAGE);
                        }
                        populateTable(filtered);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(TaxHistoryWindow.this, 
                            "Please enter a valid numeric ID!", 
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(TaxHistoryWindow.this, 
                        "ID field cannot be empty!", 
                        "Empty Input", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Reset button action
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idField.setText("");
                clearTable(); // Clear table when reset
            }
        });
    }

    // Populate table only with filtered records
    private void populateTable(List<TaxRecord> records) {
        model.setRowCount(0); // Clear existing rows
        for (TaxRecord record : records) {
            Object[] rowData = {
                    record.getid(),
                    record.getYear(),
                    record.getTaxAmount(),
                    record.getStatus()
            };
            model.addRow(rowData);
        }
    }

    // Method to clear table completely
    private void clearTable() {
        model.setRowCount(0);
    }
}

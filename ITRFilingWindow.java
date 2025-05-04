package com.gvp.orm.YCA;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ITRFilingWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField panField;
    private JTextField aadhaarField;
    private JTextField addressField;
    private JButton submitButton;

    private String name;
    private double basicSalary, bonuses, deductions, taxableIncome, taxAmount;

    public ITRFilingWindow(String name, double basicSalary, double bonuses, double deductions, double taxableIncome, double taxAmount) {
        this.name = name;
        this.basicSalary = basicSalary;
        this.bonuses = bonuses;
        this.deductions = deductions;
        this.taxableIncome = taxableIncome;
        this.taxAmount = taxAmount;

        setupUI();
        setupActions();
    }

    private void setupUI() {
        setTitle("ITR Filing Form");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("PAN Number:"));
        panField = new JTextField();
        panel.add(panField);

        panel.add(new JLabel("Aadhaar Number:"));
        aadhaarField = new JTextField();
        panel.add(aadhaarField);

        panel.add(new JLabel("Residential Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        submitButton = new JButton("Submit & Generate ITR");
        panel.add(new JLabel());
        panel.add(submitButton);

        add(panel);
    }

    private void setupActions() {
        submitButton.addActionListener(e -> {
            if (panField.getText().trim().isEmpty() || aadhaarField.getText().trim().isEmpty() || addressField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all details.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                generateITRDocument();
            }
        });
    }

    private void generateITRDocument() {
        try {
            // Save directly inside com/gvp/orm/YCA folder
            String folderPath = "C:\\Users\\swami\\eclipse-workspace\\YCA\\src\\main\\java\\com\\gvp\\orm\\YCA";
            String filename = folderPath + name.replaceAll("\\s+", "_") + "_ITR_" + System.currentTimeMillis() + ".txt";

            FileWriter writer = new FileWriter(filename);
            writer.write("--- Income Tax Return Document ---\n\n");
            writer.write("Name: " + name + "\n");
            writer.write("PAN Number: " + panField.getText().trim() + "\n");
            writer.write("Aadhaar Number: " + aadhaarField.getText().trim() + "\n");
            writer.write("Address: " + addressField.getText().trim() + "\n\n");
            writer.write("Basic Salary: " + basicSalary + "\n");
            writer.write("Bonuses: " + bonuses + "\n");
            writer.write("Deductions: " + deductions + "\n");
            writer.write("Taxable Income: " + taxableIncome + "\n");
            writer.write("Tax Amount: " + taxAmount + "\n\n");
            writer.write("Filed On: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.close();

            JOptionPane.showMessageDialog(this, "ITR Filed and Document Generated Successfully!\nFile: " + filename);
            dispose();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error generating ITR document: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

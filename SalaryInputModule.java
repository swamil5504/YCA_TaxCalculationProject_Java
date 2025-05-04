package com.gvp.orm.YCA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaryInputModule extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField employeeNameField;
    private JTextField basicSalaryField;
    private JTextField bonusesField;
    private JTextField deductionsField;
    private JButton calculateButton;
    private JButton saveButton;
    private JButton clearButton;
    private JButton viewHistoryButton;
    private JButton fileITRButton;
    private JButton exportToCSVButton; 
    private JLabel taxableIncomeLabel;
    private JLabel taxAmountLabel;

    private boolean isCalculated = false;

    public SalaryInputModule() {
        setTitle("Salary Input Module");
        setSize(600, 700); // Increased height to adjust ITR button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(230, 240, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Employee Salary Input", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 70, 129));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        panel.add(new JLabel("Employee Name:"), gbc);
        employeeNameField = new JTextField();
        gbc.gridx = 1;
        panel.add(employeeNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Basic Salary:"), gbc);
        basicSalaryField = new JTextField();
        gbc.gridx = 1;
        panel.add(basicSalaryField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Bonuses:"), gbc);
        bonusesField = new JTextField();
        gbc.gridx = 1;
        panel.add(bonusesField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Deductions:"), gbc);
        deductionsField = new JTextField();
        gbc.gridx = 1;
        panel.add(deductionsField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Taxable Income:"), gbc);
        taxableIncomeLabel = new JLabel("---");
        gbc.gridx = 1;
        panel.add(taxableIncomeLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Tax Amount:"), gbc);
        taxAmountLabel = new JLabel("---");
        gbc.gridx = 1;
        panel.add(taxAmountLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        calculateButton = new JButton("Calculate");
        panel.add(calculateButton, gbc);

        gbc.gridx = 1;
        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        panel.add(saveButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        clearButton = new JButton("Clear");
        gbc.gridwidth = 2;
        panel.add(clearButton, gbc);

        gbc.gridy++;
        viewHistoryButton = new JButton("View Tax History");
        panel.add(viewHistoryButton, gbc);

        gbc.gridy++;
        fileITRButton = new JButton("File ITR");
        panel.add(fileITRButton, gbc);

        gbc.gridy++;
        exportToCSVButton = new JButton("Export to CSV"); // Initialize export button
        panel.add(exportToCSVButton, gbc);

        add(panel);

        // Action Listeners
        calculateButton.addActionListener(e -> calculateTax());
        saveButton.addActionListener(e -> saveSalaryDetails());
        clearButton.addActionListener(e -> clearFields());
        viewHistoryButton.addActionListener(e -> viewTaxHistory());
        fileITRButton.addActionListener(e -> fileITR());
        exportToCSVButton.addActionListener(e -> exportToCSV()); // Add action listener for export
    }

    private void calculateTax() {
        try {
            if (employeeNameField.getText().trim().isEmpty() ||
                    basicSalaryField.getText().trim().isEmpty() ||
                    bonusesField.getText().trim().isEmpty() ||
                    deductionsField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields before calculating.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double basicSalary = Double.parseDouble(basicSalaryField.getText().trim());
            double bonuses = Double.parseDouble(bonusesField.getText().trim());
            double deductions = Double.parseDouble(deductionsField.getText().trim());

            if (basicSalary < 0 || bonuses < 0 || deductions < 0) {
                JOptionPane.showMessageDialog(this, "Please enter positive numbers for Salary, Bonuses, and Deductions.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double taxableIncome = basicSalary + bonuses - deductions;
            double taxAmount = TaxCalculationModule.calculateTax(taxableIncome);

            taxableIncomeLabel.setText(String.format("%.2f", taxableIncome));
            taxAmountLabel.setText(String.format("%.2f", taxAmount));

            isCalculated = true;
            saveButton.setEnabled(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveSalaryDetails() {
        if (!isCalculated) {
            JOptionPane.showMessageDialog(this, "Please calculate tax before saving.", "Action Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = employeeNameField.getText().trim();
        double basicSalary = Double.parseDouble(basicSalaryField.getText().trim());
        double bonuses = Double.parseDouble(bonusesField.getText().trim());
        double deductions = Double.parseDouble(deductionsField.getText().trim());
        double taxableIncome = Double.parseDouble(taxableIncomeLabel.getText());
        double taxAmount = Double.parseDouble(taxAmountLabel.getText());

        int confirm = JOptionPane.showConfirmDialog(this, "Do you want to save the details?", "Confirm Save", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO salary_details (name, basic_salary, bonuses, deductions, taxable_income, tax_amount) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2, basicSalary);
            pstmt.setDouble(3, bonuses);
            pstmt.setDouble(4, deductions);
            pstmt.setDouble(5, taxableIncome);
            pstmt.setDouble(6, taxAmount);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Salary details saved successfully!");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving salary details: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        employeeNameField.setText("");
        basicSalaryField.setText("");
        bonusesField.setText("");
        deductionsField.setText("");
        taxableIncomeLabel.setText("---");
        taxAmountLabel.setText("---");
        isCalculated = false;
        saveButton.setEnabled(false);
    }

    private List<TaxRecord> retrieveTaxHistory() {
        List<TaxRecord> records = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, year, tax_amount, status FROM tax_history";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                int year = rs.getInt("year");
                double taxPaid = rs.getDouble("tax_amount");
                String status = rs.getString("status");

                TaxRecord record = new TaxRecord(id, year, taxPaid, status);
                records.add(record);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving tax history: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return records;
    }

    private void viewTaxHistory() {
        List<TaxRecord> records = retrieveTaxHistory();
        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tax history found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            TaxHistoryWindow historyWindow = new TaxHistoryWindow(records);
            historyWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            historyWindow.setVisible(true);
        }
    }

    private void fileITR() {
        if (employeeNameField.getText().trim().isEmpty() ||
                basicSalaryField.getText().trim().isEmpty() ||
                bonusesField.getText().trim().isEmpty() ||
                deductionsField.getText().trim().isEmpty() ||
                taxableIncomeLabel.getText().equals("---") ||
                taxAmountLabel.getText().equals("---")) {
            JOptionPane.showMessageDialog(this, "Please calculate tax and fill all details before filing ITR.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to proceed to ITR Filing?", "File ITR", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            String name = employeeNameField.getText().trim();
            double basicSalary = Double.parseDouble(basicSalaryField.getText().trim());
            double bonuses = Double.parseDouble(bonusesField.getText().trim());
            double deductions = Double.parseDouble(deductionsField.getText().trim());
            double taxableIncome = Double.parseDouble(taxableIncomeLabel.getText());
            double taxAmount = Double.parseDouble(taxAmountLabel.getText());

            // Pass data to ITR Filing Window
            ITRFilingWindow itrWindow = new ITRFilingWindow(name, basicSalary, bonuses, deductions, taxableIncome, taxAmount);
            itrWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            itrWindow.setVisible(true);
        }
    }

    private void exportToCSV() {
        if (!isCalculated) {
            JOptionPane.showMessageDialog(this, "Please calculate tax before exporting.", "Action Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String name = employeeNameField.getText().trim();
        double basicSalary = Double.parseDouble(basicSalaryField.getText().trim());
        double bonuses = Double.parseDouble(bonusesField.getText().trim());
        double deductions = Double.parseDouble(deductionsField.getText().trim());
        double taxableIncome = Double.parseDouble(taxableIncomeLabel.getText());
        double taxAmount = Double.parseDouble(taxAmountLabel.getText());

        ExportSalary.exportToCSV(name, basicSalary, bonuses, deductions, taxableIncome, taxAmount);
        JOptionPane.showMessageDialog(this, "Data exported to SalaryDetails.csv successfully!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SalaryInputModule().setVisible(true));
    }
}
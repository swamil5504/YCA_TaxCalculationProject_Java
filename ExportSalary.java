package com.gvp.orm.YCA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExportSalary {
    public static void exportToCSV(String name, double basicSalary, double bonuses, double deductions, double taxableIncome, double taxAmount) {
        // Specify the folder path where you want to save the CSV file
        String folderPath = "C:\\Users\\swami\\eclipse-workspace\\YCA\\src\\main\\java\\com\\gvp\\orm\\YCA";
        String csvFile = folderPath + "\\SalaryDetails.csv"; // Full path to the CSV file

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, true))) {
            // Check if the file is empty to write the header only once
            if (new java.io.File(csvFile).length() == 0) {
                writer.write("Employee Name,Basic Salary,Bonuses,Deductions,Taxable Income,Tax Amount");
                writer.newLine();
            }
            writer.write(String.format("%s,%.2f,%.2f,%.2f,%.2f,%.2f", name, basicSalary, bonuses, deductions, taxableIncome, taxAmount));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package com.gvp.orm.YCA;

public class TaxRecord {
    private int id;
    private int year;
    private double taxAmount;
    private String status;


    // ADD THIS NEW CONSTRUCTOR:
    public TaxRecord(int id,int year, double taxAmount, String status) {
    	this.id = id;
    	this.year = year;
        this.taxAmount = taxAmount;
        this.status = status;
    }

    // Getters and setters (if not already present)
    public int getid() {
        return id;
    }
    
    public int getYear() {
        return year;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public String getStatus() {
        return status;
    }
}

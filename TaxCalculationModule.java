package com.gvp.orm.YCA;

public class TaxCalculationModule {

    public static double calculateTax(double salary) {
        double tax = 0.0;

        if (salary <= 300000) {
            tax = 0;
        } else if (salary <= 500000) {
            tax = (salary - 300000) * 0.05;
        } else if (salary <= 1000000) {
            tax = 10000 + (salary - 500000) * 0.20;
        } else {
            tax = 110000 + (salary - 1000000) * 0.30;
        }

        return tax;
    }
}

package com.gvp.orm.YCA;

import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SalaryInputModule().setVisible(true);
        });
    }
}

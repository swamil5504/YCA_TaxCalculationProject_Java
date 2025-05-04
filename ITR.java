package com.gvp.orm.YCA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ITR extends JFrame {
    private static final long serialVersionUID = 1L;

    private JLabel titleLabel;
    private JButton fileITRButton;
    private JButton backButton;

    public ITR() {
        setTitle("Income Tax Return Filing");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 250, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        titleLabel = new JLabel("Income Tax Return (ITR) Filing", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 70, 129));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        fileITRButton = new JButton("File ITR");
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(fileITRButton, gbc);

        gbc.gridy++;
        backButton = new JButton("Back");
        panel.add(backButton, gbc);

        add(panel);

        fileITRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ITR.this, "Your ITR has been filed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ITR().setVisible(true);
            }
        });
    }
}

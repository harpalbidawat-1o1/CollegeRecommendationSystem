
package com.college.gui;

import javax.swing.*;
import java.awt.*;

public class SearchFrame extends JFrame {

    public SearchFrame() {
        setTitle("Search Colleges");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel heading = new JLabel("Search Engineering Colleges");
        heading.setFont(new Font("Arial", Font.BOLD, 20));

        JTextField percentileField = new JTextField();

        String[] categories = {"OPEN", "OBC", "SC", "ST", "EWS", "VJ", "DT", "NT"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);

        JTextField cityField = new JTextField();

        JButton searchButton = new JButton("Search");
        JButton backButton = new JButton("Back to Home");

        panel.add(heading);
        panel.add(new JLabel(""));

        panel.add(new JLabel("JEE  Percentile:"));
        panel.add(percentileField);

        panel.add(new JLabel("Category:"));
        panel.add(categoryBox);

        panel.add(new JLabel("Preferred City:"));
        panel.add(cityField);

        panel.add(searchButton);
        panel.add(backButton);

        add(panel);

        searchButton.addActionListener(e -> {
            String percentile = percentileField.getText().trim();
            String category = (String) categoryBox.getSelectedItem();
            String city = cityField.getText().trim();

            if (percentile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your percentile.");
                return;
            }

            try {
                double value = Double.parseDouble(percentile);

                if (value < 0 || value > 100) {
                    JOptionPane.showMessageDialog(this,
                        "Percentile must be between 0 and 100.");
                    return;
                }

                JOptionPane.showMessageDialog(this,
                    "Search details received!\nPercentile: " + value
                    + "\nCategory: " + category
                    + "\nCity: " + (city.isEmpty() ? "Any city" : city)
                    + "\n\nCollege results will be connected next.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a valid numeric percentile.");
            }
        });

        backButton.addActionListener(e -> dispose());
    }
}


package com.college;
import com.college.gui.SearchFrame;
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("College Recommendation System");

            frame.setSize(700, 450);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(6, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));

            JLabel title = new JLabel(
                "COLLEGE RECOMMENDATION SYSTEM",
                SwingConstants.CENTER
            );
            title.setFont(new Font("Arial", Font.BOLD, 22));

            JButton findButton = new JButton("Find Colleges");
            JButton searchButton = new JButton("Search Colleges");
            JButton recommendationButton = new JButton("My Recommendations");
            JButton adminButton = new JButton("Admin");
            JButton exitButton = new JButton("Exit");

            panel.add(title);
            panel.add(findButton);
            panel.add(searchButton);
            panel.add(recommendationButton);
            panel.add(adminButton);
            panel.add(exitButton);

            findButton.addActionListener(e -> new SearchFrame().setVisible(true));

searchButton.addActionListener(e -> new SearchFrame().setVisible(true));

            recommendationButton.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Recommendations will be added next.")
            );

            adminButton.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Admin features will be added next.")
            );

            exitButton.addActionListener(e -> frame.dispose());

            frame.add(panel);
            frame.setVisible(true);
        });
    }
}

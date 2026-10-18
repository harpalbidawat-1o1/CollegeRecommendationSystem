
package com.college.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import com.college.model.College;
import com.college.model.Course;
import com.college.model.Cutoff;
import com.college.service.RecommendationService;

public class SearchFrame extends JFrame {

    private final RecommendationService recommendationService;
    private final List<College> colleges;
    private final List<Course> courses;

    public SearchFrame() {
        recommendationService = new RecommendationService();
        colleges = recommendationService.getColleges();
        courses = recommendationService.getCourses();

        setTitle("Search Colleges");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        JLabel heading = new JLabel("Search Engineering Colleges");
        heading.setFont(new Font("Arial", Font.BOLD, 20));

        JTextField percentileField = new JTextField();

        String[] categories = {
            "OPEN", "OBC", "SC", "ST", "EWS", "VJ", "DT", "NT"
        };
        JComboBox<String> categoryBox = new JComboBox<>(categories);

        JTextField cityField = new JTextField();

        JButton searchButton = new JButton("Search");
        JButton backButton = new JButton("Back to Home");

        panel.add(heading);
        panel.add(new JLabel(""));

        panel.add(new JLabel("JEE Main Percentile:"));
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
                JOptionPane.showMessageDialog(
                    this, "Please enter your percentile."
                );
                return;
            }

            try {
                double value = Double.parseDouble(percentile);

                if (value < 0 || value > 100) {
                    JOptionPane.showMessageDialog(
                        this, "Percentile must be between 0 and 100."
                    );
                    return;
                }

                List<String> recommendations =
                    recommendationService.recommendColleges(value, category);

                DefaultListModel<String> listModel = new DefaultListModel<>();

                for (String result : recommendations) {
                    if (city.isEmpty()
                            || result.toLowerCase().contains(city.toLowerCase())) {
                        listModel.addElement(result);
                    }
                }

                if (listModel.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this, "No matching colleges found."
                    );
                    return;
                }

                JList<String> resultList = new JList<>(listModel);
                resultList.setSelectionMode(
                    ListSelectionModel.SINGLE_SELECTION
                );

                JButton detailsButton = new JButton("View College Details");

                JPanel resultPanel = new JPanel(new BorderLayout(10, 10));
                resultPanel.add(new JScrollPane(resultList), BorderLayout.CENTER);
                resultPanel.add(detailsButton, BorderLayout.SOUTH);

                detailsButton.addActionListener(event -> {
                    int selectedIndex = resultList.getSelectedIndex();

                    if (selectedIndex == -1) {
                        JOptionPane.showMessageDialog(
                            this, "Please select a result first."
                        );
                        return;
                    }

                    String selectedResult = resultList.getSelectedValue();

                    for (Course course : courses) {
                        for (College college : colleges) {
                            String prefix = college.getCollegeName()
                                + " | " + college.getCity()
                                + " | " + course.getCourseName() + " |";

                            if (selectedResult.startsWith(prefix)
                                    && course.getCollegeId() == college.getCollegeId()) {
                                new CollegeDetailsFrame(college, courses)
                                    .setVisible(true);
                                return;
                            }
                        }
                    }
                });

                JOptionPane.showMessageDialog(
                    this,
                    resultPanel,
                    "College Recommendations",
                    JOptionPane.PLAIN_MESSAGE
                );

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    this, "Please enter a valid numeric percentile."
                );
            }
        });

        backButton.addActionListener(e -> dispose());
    }
}

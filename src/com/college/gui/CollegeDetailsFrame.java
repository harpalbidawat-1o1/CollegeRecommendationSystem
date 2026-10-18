
package com.college.gui;

import javax.swing.*;
import java.awt.*;
import com.college.model.College;
import com.college.model.Course;
import java.util.List;

public class CollegeDetailsFrame extends JFrame {

    public CollegeDetailsFrame(College college, List<Course> courses) {
        setTitle("College Details");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("College Information"));

        detailsPanel.add(new JLabel("College Name: " + college.getCollegeName()));
        detailsPanel.add(new JLabel("City: " + college.getCity()));
        detailsPanel.add(new JLabel("University: " + college.getUniversity()));
        detailsPanel.add(new JLabel("Address: " + college.getAddress()));

        JPanel coursePanel = new JPanel(new BorderLayout(5, 5));
        coursePanel.setBorder(BorderFactory.createTitledBorder("Available Courses"));

        DefaultListModel<String> courseListModel = new DefaultListModel<>();

        for (Course course : courses) {
            if (course.getCollegeId() == college.getCollegeId()) {
                courseListModel.addElement(
                    course.getCourseName() + " - Seats: " + course.getTotalSeats()
                );
            }
        }

        JList<String> courseList = new JList<>(courseListModel);
        coursePanel.add(new JScrollPane(courseList), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());

        mainPanel.add(detailsPanel, BorderLayout.NORTH);
        mainPanel.add(coursePanel, BorderLayout.CENTER);
        mainPanel.add(closeButton, BorderLayout.SOUTH);

        add(mainPanel);
    }
}

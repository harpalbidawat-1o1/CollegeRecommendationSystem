
package com.college.gui;

import com.college.model.College;
import com.college.model.Course;
import com.college.model.Cutoff;
import com.college.service.CollegeDataService;
import com.college.service.CourseDataService;
import com.college.service.CutoffDataService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CourseManagementFrame extends JFrame {

    private JComboBox<College> collegeBox;
    private JTextField courseNameField;
    private JTextField seatsField;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public CourseManagementFrame() {
        setTitle("Admin - Manage Courses");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        collegeBox = new JComboBox<>();
        for (College college : CollegeDataService.getColleges()) {
            collegeBox.addItem(college);
        }

        collegeBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                if (value instanceof College) {
                    setText(((College) value).getCollegeName());
                }

                return this;
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select College:"));
        topPanel.add(collegeBox);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Course Name", "Total Seats"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        courseTable = new JTable(tableModel);
        add(new JScrollPane(courseTable), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        courseNameField = new JTextField();
        seatsField = new JTextField();

        formPanel.add(new JLabel("Course Name:"));
        formPanel.add(new JLabel("Total Seats:"));
        formPanel.add(courseNameField);
        formPanel.add(seatsField);

        JButton addButton = new JButton("Add Course");
        JButton updateButton = new JButton("Update Selected");
        JButton deleteButton = new JButton("Delete Selected");
        JButton clearButton = new JButton("Clear Fields");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        collegeBox.addActionListener(e -> refreshTable());

        courseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFields();
            }
        });

        addButton.addActionListener(e -> addCourse());
        updateButton.addActionListener(e -> updateCourse());
        deleteButton.addActionListener(e -> deleteCourse());
        clearButton.addActionListener(e -> clearFields());

        refreshTable();
    }

    private College getSelectedCollege() {
        return (College) collegeBox.getSelectedItem();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        College college = getSelectedCollege();

        if (college == null) {
            return;
        }

        List<Course> courses =
                CourseDataService.getCoursesByCollege(college.getCollegeId());

        for (Course course : courses) {
            tableModel.addRow(new Object[]{
                    course.getCourseId(),
                    course.getCourseName(),
                    course.getTotalSeats()
            });
        }
    }

    private void addCourse() {
        College college = getSelectedCollege();

        if (college == null) {
            JOptionPane.showMessageDialog(this, "Add a college first.");
            return;
        }

        String name = courseNameField.getText().trim();
        String seatsText = seatsField.getText().trim();

        if (name.isEmpty() || seatsText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please fill in all fields.");
            return;
        }

        try {
            int seats = Integer.parseInt(seatsText);

            if (seats <= 0) {
                JOptionPane.showMessageDialog(
                        this, "Seats must be greater than zero.");
                return;
            }

            Course course = new Course(
                    CourseDataService.getNextId(),
                    college.getCollegeId(),
                    name,
                    seats
            );

            CourseDataService.addCourse(course);
            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(
                    this, "Course added successfully.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Enter a valid number of seats.");
        }
    }

    private void updateCourse() {
        int row = courseTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this, "Select a course to update.");
            return;
        }

        String name = courseNameField.getText().trim();
        String seatsText = seatsField.getText().trim();

        if (name.isEmpty() || seatsText.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please fill in all fields.");
            return;
        }

        try {
            int seats = Integer.parseInt(seatsText);

            if (seats <= 0) {
                JOptionPane.showMessageDialog(
                        this, "Seats must be greater than zero.");
                return;
            }

            int courseId = (int) tableModel.getValueAt(row, 0);
            College college = getSelectedCollege();

            if (college == null) {
                return;
            }

            Course updatedCourse = new Course(
                    courseId,
                    college.getCollegeId(),
                    name,
                    seats
            );

            CourseDataService.updateCourse(updatedCourse);
            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(
                    this, "Course updated successfully.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Enter a valid number of seats.");
        }
    }

    private void deleteCourse() {
        int row = courseTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this, "Select a course to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this course and its cutoff records?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int courseId = (int) tableModel.getValueAt(row, 0);

        for (Cutoff cutoff : CutoffDataService.getCutoffsByCourse(courseId)) {
    CutoffDataService.deleteCutoff(cutoff.getCutoffId());
}

CourseDataService.deleteCourse(courseId);

        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(
                this,
                "Course deleted. Any existing cutoff records must also be removed."
        );
    }

    private void fillFields() {
        int row = courseTable.getSelectedRow();

        if (row == -1) {
            return;
        }

        courseNameField.setText(
                tableModel.getValueAt(row, 1).toString());
        seatsField.setText(
                tableModel.getValueAt(row, 2).toString());
    }

    private void clearFields() {
        courseNameField.setText("");
        seatsField.setText("");
        courseTable.clearSelection();
    }
}

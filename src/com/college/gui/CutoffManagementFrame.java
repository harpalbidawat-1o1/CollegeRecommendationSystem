
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

public class CutoffManagementFrame extends JFrame {

    private JComboBox<College> collegeBox;
    private JComboBox<Course> courseBox;
    private JComboBox<String> categoryBox;
    private JComboBox<String> examBox;
    private JTextField percentileField;
    private JTable cutoffTable;
    private DefaultTableModel tableModel;

    public CutoffManagementFrame() {
        setTitle("Admin - Manage Cutoffs");
        setSize(850, 500);
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

        courseBox = new JComboBox<>();
        categoryBox = new JComboBox<>(
                new String[]{"OPEN", "OBC", "SC", "ST", "EWS", "VJ", "DT", "NT"}
        );
        examBox = new JComboBox<>(
        new String[]{"JEE Main"}
);
        percentileField = new JTextField();

        JPanel selectionPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        selectionPanel.add(new JLabel("College:"));
        selectionPanel.add(new JLabel("Course:"));
        selectionPanel.add(new JLabel("Category:"));
        selectionPanel.add(new JLabel("Exam:"));
        selectionPanel.add(collegeBox);
        selectionPanel.add(courseBox);
        selectionPanel.add(categoryBox);
        selectionPanel.add(examBox);

        add(selectionPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Course", "Category", "Cutoff", "Exam"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        cutoffTable = new JTable(tableModel);
        add(new JScrollPane(cutoffTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));

        JPanel formPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        formPanel.add(new JLabel("Cutoff Percentile (0-100):"));
        formPanel.add(percentileField);

        JButton addButton = new JButton("Add Cutoff");
        JButton updateButton = new JButton("Update Selected");
        JButton deleteButton = new JButton("Delete Selected");
        JButton clearButton = new JButton("Clear Fields");

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        collegeBox.addActionListener(e -> refreshCourses());
        courseBox.addActionListener(e -> refreshTable());

        cutoffTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFields();
            }
        });

        addButton.addActionListener(e -> addCutoff());
        updateButton.addActionListener(e -> updateCutoff());
        deleteButton.addActionListener(e -> deleteCutoff());
        clearButton.addActionListener(e -> clearFields());

        refreshCourses();
    }

    private College getSelectedCollege() {
        return (College) collegeBox.getSelectedItem();
    }

    private Course getSelectedCourse() {
        return (Course) courseBox.getSelectedItem();
    }

    private void refreshCourses() {
        courseBox.removeAllItems();

        College college = getSelectedCollege();

        if (college != null) {
            for (Course course : CourseDataService.getCoursesByCollege(
                    college.getCollegeId())) {
                courseBox.addItem(course);
            }
        }

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        Course selectedCourse = getSelectedCourse();

        if (selectedCourse == null) {
            return;
        }

        for (Cutoff cutoff : CutoffDataService.getCutoffsByCourse(
                selectedCourse.getCourseId())) {

            tableModel.addRow(new Object[]{
                    cutoff.getCutoffId(),
                    selectedCourse.getCourseName(),
                    cutoff.getCategory(),
                    cutoff.getCutoffPercentile(),
                    cutoff.getExamName()
            });
        }
    }

    private void addCutoff() {
        Course course = getSelectedCourse();

        if (course == null) {
            JOptionPane.showMessageDialog(
                    this, "Select a course first.");
            return;
        }

        try {
            double percentile = Double.parseDouble(
                    percentileField.getText().trim());

            if (percentile < 0 || percentile > 100) {
                JOptionPane.showMessageDialog(
                        this, "Cutoff must be between 0 and 100.");
                return;
            }

            String category = (String) categoryBox.getSelectedItem();
            String exam = (String) examBox.getSelectedItem();

            for (Cutoff existing : CutoffDataService.getCutoffsByCourse(
                    course.getCourseId())) {

                if (existing.getCategory().equals(category)
                        && existing.getExamName().equals(exam)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "A cutoff already exists for this course, category, and exam. Update it instead."
                    );
                    return;
                }
            }

            Cutoff cutoff = new Cutoff(
                    CutoffDataService.getNextId(),
                    course.getCourseId(),
                    category,
                    percentile,
                    exam
            );

            CutoffDataService.addCutoff(cutoff);
            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(
                    this, "Cutoff added successfully.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Enter a valid numeric cutoff.");
        }
    }

    private void updateCutoff() {
        int row = cutoffTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this, "Select a cutoff to update.");
            return;
        }

        try {
            double percentile = Double.parseDouble(
                    percentileField.getText().trim());

            if (percentile < 0 || percentile > 100) {
                JOptionPane.showMessageDialog(
                        this, "Cutoff must be between 0 and 100.");
                return;
            }

            int cutoffId = (int) tableModel.getValueAt(row, 0);
            Course course = getSelectedCourse();

            if (course == null) {
                return;
            }

            Cutoff updated = new Cutoff(
                    cutoffId,
                    course.getCourseId(),
                    (String) categoryBox.getSelectedItem(),
                    percentile,
                    (String) examBox.getSelectedItem()
            );

            CutoffDataService.updateCutoff(updated);
            refreshTable();
            clearFields();

            JOptionPane.showMessageDialog(
                    this, "Cutoff updated successfully.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this, "Enter a valid numeric cutoff.");
        }
    }

    private void deleteCutoff() {
        int row = cutoffTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this, "Select a cutoff to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete this cutoff?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int cutoffId = (int) tableModel.getValueAt(row, 0);
        CutoffDataService.deleteCutoff(cutoffId);

        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(
                this, "Cutoff deleted successfully.");
    }

    private void fillFields() {
        int row = cutoffTable.getSelectedRow();

        if (row == -1) {
            return;
        }

        String category = tableModel.getValueAt(row, 2).toString();
        String exam = tableModel.getValueAt(row, 4).toString();
        String percentile = tableModel.getValueAt(row, 3).toString();

        categoryBox.setSelectedItem(category);
        examBox.setSelectedItem(exam);
        percentileField.setText(percentile);
    }

    private void clearFields() {
        percentileField.setText("");
        cutoffTable.clearSelection();
    }
}

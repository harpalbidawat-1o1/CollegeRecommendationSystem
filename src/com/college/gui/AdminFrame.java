
package com.college.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.college.model.College;
import com.college.model.Course;
import com.college.model.Cutoff;
import com.college.service.BookmarkService;
import com.college.service.CollegeDataService;
import com.college.service.CourseDataService;
import com.college.service.CutoffDataService;

public class AdminFrame extends JFrame {

    private List<College> colleges;
    private DefaultTableModel tableModel;
    private JTable collegeTable;

    private JTextField nameField;
    private JTextField cityField;
    private JTextField universityField;
    private JTextField addressField;

    public AdminFrame() {
        colleges = CollegeDataService.getColleges();

        setTitle("Admin - Manage Colleges");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel(
                "ADMIN - MANAGE COLLEGES",
                JLabel.CENTER
        );
        add(heading, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{
                    "ID", "College Name", "City", "University", "Address"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        collegeTable = new JTable(tableModel);
        add(new JScrollPane(collegeTable), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(2, 4, 5, 5));

        nameField = new JTextField();
        cityField = new JTextField();
        universityField = new JTextField();
        addressField = new JTextField();

        formPanel.add(new JLabel("College Name"));
        formPanel.add(new JLabel("City"));
        formPanel.add(new JLabel("University"));
        formPanel.add(new JLabel("Address"));

        formPanel.add(nameField);
        formPanel.add(cityField);
        formPanel.add(universityField);
        formPanel.add(addressField);

        JButton addButton = new JButton("Add College");
        JButton updateButton = new JButton("Update Selected");
        JButton deleteButton = new JButton("Delete Selected");
        JButton clearButton = new JButton("Clear Fields");

        JPanel collegeButtonPanel =
                new JPanel(new GridLayout(1, 4, 5, 5));

        collegeButtonPanel.add(addButton);
        collegeButtonPanel.add(updateButton);
        collegeButtonPanel.add(deleteButton);
        collegeButtonPanel.add(clearButton);

        JButton coursesButton = new JButton("Manage Courses");
        JButton cutoffsButton = new JButton("Manage Cutoffs");

        JPanel managementPanel =
                new JPanel(new GridLayout(1, 2, 5, 5));

        managementPanel.add(coursesButton);
        managementPanel.add(cutoffsButton);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        bottomPanel.add(formPanel);
        bottomPanel.add(collegeButtonPanel);
        bottomPanel.add(managementPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addCollege());
        updateButton.addActionListener(e -> updateCollege());
        deleteButton.addActionListener(e -> deleteCollege());
        clearButton.addActionListener(e -> clearFields());

        coursesButton.addActionListener(e ->
                new CourseManagementFrame().setVisible(true)
        );

        cutoffsButton.addActionListener(e ->
                new CutoffManagementFrame().setVisible(true)
        );

        collegeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFields();
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        colleges = CollegeDataService.getColleges();
        tableModel.setRowCount(0);

        for (College college : colleges) {
            tableModel.addRow(new Object[]{
                    college.getCollegeId(),
                    college.getCollegeName(),
                    college.getCity(),
                    college.getUniversity(),
                    college.getAddress()
            });
        }
    }

    private void addCollege() {
        if (!validateFields()) {
            return;
        }

        int nextId = CollegeDataService.getNextId();

        College college = new College(
                nextId,
                nameField.getText().trim(),
                cityField.getText().trim(),
                universityField.getText().trim(),
                addressField.getText().trim()
        );

        CollegeDataService.addCollege(college);

        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(
                this, "College added successfully."
        );
    }

    private void updateCollege() {
        int row = collegeTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this, "Select a college to update."
            );
            return;
        }

        if (!validateFields()) {
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        College updatedCollege = new College(
                id,
                nameField.getText().trim(),
                cityField.getText().trim(),
                universityField.getText().trim(),
                addressField.getText().trim()
        );

        CollegeDataService.updateCollege(updatedCollege);

        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(
                this, "College updated successfully."
        );
    }

    private void deleteCollege() {
        int row = collegeTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(
                    this, "Select a college to delete."
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this college and its related records?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        List<Course> courses =
                CourseDataService.getCoursesByCollege(id);

        for (Course course : courses) {
            List<Cutoff> cutoffs =
                    CutoffDataService.getCutoffsByCourse(
                            course.getCourseId()
                    );

            for (Cutoff cutoff : cutoffs) {
                CutoffDataService.deleteCutoff(cutoff.getCutoffId());
            }

            CourseDataService.deleteCourse(course.getCourseId());
        }

        CollegeDataService.deleteCollege(id);
        BookmarkService.removeBookmark(id);

        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(
                this,
                "College and its related records deleted successfully."
        );
    }

    private void fillFields() {
        int row = collegeTable.getSelectedRow();

        if (row == -1) {
            return;
        }

        nameField.setText(tableModel.getValueAt(row, 1).toString());
        cityField.setText(tableModel.getValueAt(row, 2).toString());
        universityField.setText(tableModel.getValueAt(row, 3).toString());
        addressField.setText(tableModel.getValueAt(row, 4).toString());
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty()
                || cityField.getText().trim().isEmpty()
                || universityField.getText().trim().isEmpty()
                || addressField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this, "Please fill in all four fields."
            );

            return false;
        }

        return true;
    }

    private void clearFields() {
        nameField.setText("");
        cityField.setText("");
        universityField.setText("");
        addressField.setText("");
        collegeTable.clearSelection();
    }
}

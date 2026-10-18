
package com.college.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
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

public class AdminFrame extends JFrame {

    private List<College> colleges;
    private DefaultTableModel tableModel;
    private JTable collegeTable;

    private JTextField nameField;
    private JTextField cityField;
    private JTextField universityField;
    private JTextField addressField;

    public AdminFrame() {
        colleges = new ArrayList<>();

        colleges.add(new College(
                1, "Example Engineering College",
                "Pune", "Example University", "Pune, Maharashtra"
        ));

        colleges.add(new College(
                2, "Sample Institute of Technology",
                "Mumbai", "Sample University", "Mumbai, Maharashtra"
        ));

        setTitle("Admin - Manage Colleges");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel(
                "ADMIN - MANAGE COLLEGES",
                JLabel.CENTER
        );
        add(heading, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "College Name", "City", "University", "Address"}, 0
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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addCollege());
        updateButton.addActionListener(e -> updateCollege());
        deleteButton.addActionListener(e -> deleteCollege());
        clearButton.addActionListener(e -> clearFields());

        collegeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFields();
            }
        });

        refreshTable();
    }

    private void refreshTable() {
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

        int nextId = 1;

        for (College college : colleges) {
            if (college.getCollegeId() >= nextId) {
                nextId = college.getCollegeId() + 1;
            }
        }

        College college = new College(
                nextId,
                nameField.getText().trim(),
                cityField.getText().trim(),
                universityField.getText().trim(),
                addressField.getText().trim()
        );

        colleges.add(college);
        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(this, "College added successfully.");
    }

    private void updateCollege() {
        int row = collegeTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a college to update.");
            return;
        }

        if (!validateFields()) {
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        for (College college : colleges) {
            if (college.getCollegeId() == id) {
                college.setCollegeName(nameField.getText().trim());
                college.setCity(cityField.getText().trim());
                college.setUniversity(universityField.getText().trim());
                college.setAddress(addressField.getText().trim());
                break;
            }
        }

        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(this, "College updated successfully.");
    }

    private void deleteCollege() {
        int row = collegeTable.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a college to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this college?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);

        colleges.removeIf(college -> college.getCollegeId() == id);

        refreshTable();
        clearFields();

        JOptionPane.showMessageDialog(this, "College deleted successfully.");
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
                    this,
                    "Please fill in all four fields."
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

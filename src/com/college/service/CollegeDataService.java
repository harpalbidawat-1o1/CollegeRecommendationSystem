
package com.college.service;

import com.college.dao.CollegeDAO;
import com.college.model.College;
import java.util.List;

public class CollegeDataService {

    private static final CollegeDAO collegeDAO = new CollegeDAO();

    public static List<College> getColleges() {
        return collegeDAO.getAllColleges();
    }

    public static void addCollege(College college) {
        if (!collegeDAO.addCollege(college)) {
            System.out.println("Failed to add college.");
        }
    }

    public static void updateCollege(College updatedCollege) {
        if (!collegeDAO.updateCollege(updatedCollege)) {
            System.out.println("Failed to update college.");
        }
    }

    public static void deleteCollege(int collegeId) {
        if (!collegeDAO.deleteCollege(collegeId)) {
            System.out.println("Failed to delete college.");
        }
    }

    public static int getNextId() {
        int maxId = 0;

        for (College college : getColleges()) {
            if (college.getCollegeId() > maxId) {
                maxId = college.getCollegeId();
            }
        }

        return maxId + 1;
    }
}


package com.college.service;

import com.college.model.College;
import java.util.ArrayList;
import java.util.List;

public class CollegeDataService {

    private static final List<College> colleges = new ArrayList<>();

    static {
        colleges.add(new College(
                1, "Example Engineering College",
                "Pune", "Example University", "Pune, Maharashtra"
        ));

        colleges.add(new College(
                2, "Sample Institute of Technology",
                "Mumbai", "Sample University", "Mumbai, Maharashtra"
        ));
    }

    public static List<College> getColleges() {
        return new ArrayList<>(colleges);
    }

    public static void addCollege(College college) {
        colleges.add(college);
    }

    public static void updateCollege(College updatedCollege) {
        for (int i = 0; i < colleges.size(); i++) {
            if (colleges.get(i).getCollegeId()
                    == updatedCollege.getCollegeId()) {
                colleges.set(i, updatedCollege);
                return;
            }
        }
    }

    public static void deleteCollege(int collegeId) {
        colleges.removeIf(
                college -> college.getCollegeId() == collegeId
        );
    }

    public static int getNextId() {
        int maxId = 0;

        for (College college : colleges) {
            if (college.getCollegeId() > maxId) {
                maxId = college.getCollegeId();
            }
        }

        return maxId + 1;
    }
}

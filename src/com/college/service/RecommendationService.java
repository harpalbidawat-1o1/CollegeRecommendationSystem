
package com.college.service;

import com.college.model.College;
import com.college.model.Course;
import com.college.model.Cutoff;
import java.util.ArrayList;
import java.util.List;

public class RecommendationService {

    private List<College> colleges;
    private List<Course> courses;
    private List<Cutoff> cutoffs;

    public RecommendationService() {
        colleges = new ArrayList<>();
        courses = new ArrayList<>();
        cutoffs = new ArrayList<>();

        loadSampleData();
    }

    private void loadSampleData() {
        colleges.add(new College(
            1, "Example Engineering College",
            "Pune", "Example University", "Pune, Maharashtra"
        ));

        colleges.add(new College(
            2, "Sample Institute of Technology",
            "Mumbai", "Sample University", "Mumbai, Maharashtra"
        ));

        courses.add(new Course(1, 1, "Computer Engineering", 120));
        courses.add(new Course(2, 1, "Mechanical Engineering", 60));
        courses.add(new Course(3, 2, "Information Technology", 90));

        cutoffs.add(new Cutoff(1, 1, "OPEN", 95.0, "JEE Main"));
        cutoffs.add(new Cutoff(2, 2, "OPEN", 80.0, "JEE Main"));
        cutoffs.add(new Cutoff(3, 3, "OPEN", 90.0, "JEE Main"));
    }

    public List<String> recommendColleges(double percentile, String category) {
        List<String> recommendations = new ArrayList<>();

        for (Cutoff cutoff : cutoffs) {
            if (cutoff.getExamName().equalsIgnoreCase("JEE Main")
                    && cutoff.getCategory().equalsIgnoreCase(category)
                    && percentile >= cutoff.getCutoffPercentile()) {

                for (Course course : courses) {
                    if (course.getCourseId() == cutoff.getCourseId()) {
                        for (College college : colleges) {
                            if (college.getCollegeId() == course.getCollegeId()) {
                                String result = college.getCollegeName()
                                    + " | " + college.getCity()
                                    + " | " + course.getCourseName()
                                    + " | Cutoff: " + cutoff.getCutoffPercentile();

                                recommendations.add(result);
                            }
                        }
                    }
                }
            }
        }

        return recommendations;
    }
        public List<College> getColleges() {
        return new ArrayList<>(colleges);
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }
}

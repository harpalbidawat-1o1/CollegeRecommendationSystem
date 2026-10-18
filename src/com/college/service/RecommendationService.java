
package com.college.service;

import com.college.model.College;
import com.college.model.Course;
import com.college.model.Cutoff;

import java.util.ArrayList;
import java.util.List;

public class RecommendationService {

    public RecommendationService() {
    }

    public List<String> recommendColleges(
            double percentile, String category) {

        List<String> recommendations = new ArrayList<>();

        List<College> colleges = CollegeDataService.getColleges();
        List<Course> courses = CourseDataService.getCourses();
        List<Cutoff> cutoffs = CutoffDataService.getCutoffs();

        for (Cutoff cutoff : cutoffs) {

            if (cutoff.getExamName().equalsIgnoreCase("JEE Main")
                    && cutoff.getCategory().equalsIgnoreCase(category)
                    && percentile >= cutoff.getCutoffPercentile()) {

                for (Course course : courses) {

                    if (course.getCourseId() == cutoff.getCourseId()) {

                        for (College college : colleges) {

                            if (college.getCollegeId()
                                    == course.getCollegeId()) {

                                String result =
                                        college.getCollegeName()
                                        + " | " + college.getCity()
                                        + " | " + course.getCourseName()
                                        + " | Cutoff: "
                                        + cutoff.getCutoffPercentile();

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
        return CollegeDataService.getColleges();
    }

    public List<Course> getCourses() {
        return CourseDataService.getCourses();
    }
}

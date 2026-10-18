
package com.college.service;

import com.college.model.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseDataService {

    private static final List<Course> courses = new ArrayList<>();

    static {
        courses.add(new Course(
                1, 1, "Computer Engineering", 120
        ));

        courses.add(new Course(
                2, 1, "Mechanical Engineering", 60
        ));

        courses.add(new Course(
                3, 2, "Information Technology", 90
        ));
    }

    public static List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public static List<Course> getCoursesByCollege(int collegeId) {
        List<Course> result = new ArrayList<>();

        for (Course course : courses) {
            if (course.getCollegeId() == collegeId) {
                result.add(course);
            }
        }

        return result;
    }

    public static void addCourse(Course course) {
        courses.add(course);
    }

    public static void updateCourse(Course updatedCourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId()
                    == updatedCourse.getCourseId()) {
                courses.set(i, updatedCourse);
                return;
            }
        }
    }

    public static void deleteCourse(int courseId) {
        courses.removeIf(
                course -> course.getCourseId() == courseId
        );
    }

    public static int getNextId() {
        int maxId = 0;

        for (Course course : courses) {
            if (course.getCourseId() > maxId) {
                maxId = course.getCourseId();
            }
        }

        return maxId + 1;
    }
}

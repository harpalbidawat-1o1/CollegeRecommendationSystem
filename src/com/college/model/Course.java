
package com.college.model;

public class Course {
    private int courseId;
    private int collegeId;
    private String courseName;
    private int totalSeats;

    public Course(int courseId, int collegeId, String courseName, int totalSeats) {
        this.courseId = courseId;
        this.collegeId = collegeId;
        this.courseName = courseName;
        this.totalSeats = totalSeats;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return courseName;
    }
}

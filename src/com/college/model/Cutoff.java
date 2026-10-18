
package com.college.model;

public class Cutoff {
    private int cutoffId;
    private int courseId;
    private String category;
    private double cutoffPercentile;
    private String examName;

    public Cutoff(int cutoffId, int courseId, String category,
                  double cutoffPercentile, String examName) {
        this.cutoffId = cutoffId;
        this.courseId = courseId;
        this.category = category;
        this.cutoffPercentile = cutoffPercentile;
        this.examName = examName;
    }

    public int getCutoffId() {
        return cutoffId;
    }

    public void setCutoffId(int cutoffId) {
        this.cutoffId = cutoffId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCutoffPercentile() {
        return cutoffPercentile;
    }

    public void setCutoffPercentile(double cutoffPercentile) {
        this.cutoffPercentile = cutoffPercentile;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }
}

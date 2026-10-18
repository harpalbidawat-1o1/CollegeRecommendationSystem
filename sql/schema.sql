
CREATE DATABASE IF NOT EXISTS college_recommendation;

USE college_recommendation;

CREATE TABLE IF NOT EXISTS colleges (
    college_id INT PRIMARY KEY AUTO_INCREMENT,
    college_name VARCHAR(200) NOT NULL,
    city VARCHAR(100) NOT NULL,
    university VARCHAR(200) NOT NULL,
    address VARCHAR(300) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    college_id INT NOT NULL,
    course_name VARCHAR(200) NOT NULL,
    total_seats INT NOT NULL,
    CONSTRAINT fk_courses_college
        FOREIGN KEY (college_id)
        REFERENCES colleges(college_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cutoffs (
    cutoff_id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    category VARCHAR(20) NOT NULL,
    cutoff_percentile DOUBLE NOT NULL,
    exam_name VARCHAR(50) NOT NULL,
    CONSTRAINT fk_cutoffs_course
        FOREIGN KEY (course_id)
        REFERENCES courses(course_id)
        ON DELETE CASCADE
);

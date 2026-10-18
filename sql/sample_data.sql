
USE college_recommendation;

INSERT INTO colleges (college_id, college_name, city, university, address)
VALUES
(1, 'Example Engineering College', 'Pune', 'Example University', 'Pune, Maharashtra'),
(2, 'Sample Institute of Technology', 'Mumbai', 'Sample University', 'Mumbai, Maharashtra');

INSERT INTO courses (course_id, college_id, course_name, total_seats)
VALUES
(1, 1, 'Computer Engineering', 120),
(2, 1, 'Mechanical Engineering', 60),
(3, 2, 'Information Technology', 90);

INSERT INTO cutoffs (cutoff_id, course_id, category, cutoff_percentile, exam_name)
VALUES
(1, 1, 'OPEN', 95.0, 'JEE Main'),
(2, 2, 'OPEN', 80.0, 'JEE Main'),
(3, 3, 'OPEN', 90.0, 'JEE Main');

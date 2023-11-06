CREATE TABLE StudyGroups (
                             group_id SERIAL PRIMARY KEY,
                             group_name INT UNIQUE
);

CREATE TABLE Students (
                          student_id SERIAL PRIMARY KEY,
                          name VARCHAR(255),
                          family_name VARCHAR(255),
                          age INT,
                          group_id INT REFERENCES StudyGroups(group_id)
);

CREATE TABLE Curricula (
                           curricula_id SERIAL PRIMARY KEY,
                           group_id INT REFERENCES StudyGroups(group_id),
                           subject_name VARCHAR(255)
);

CREATE TABLE Grades (
                        grade_id SERIAL PRIMARY KEY,
                        student_id INT REFERENCES Students(student_id),
                        curricula_id INT REFERENCES Curricula(curricula_id),
                        grade INT
);
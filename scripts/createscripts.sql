CREATE TABLE placements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    organisation_id BIGINT NOT NULL,
    profile VARCHAR(255),
    description TEXT,
    intake INT,
    min_grade DOUBLE,
    CONSTRAINT fk_organisation FOREIGN KEY (organisation_id) REFERENCES organisation(id) ON DELETE CASCADE
);
CREATE TABLE student_filter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    specialisation_id BIGINT NOT NULL,
    placement_id BIGINT NOT NULL,
    domain_id BIGINT NOT NULL,
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_specialisation FOREIGN KEY (specialisation_id) REFERENCES specialisation(id) ON DELETE CASCADE,
    CONSTRAINT fk_placement FOREIGN KEY (placement_id) REFERENCES placements(id) ON DELETE CASCADE,
    CONSTRAINT fk_domain FOREIGN KEY (domain_id) REFERENCES domain(id) ON DELETE CASCADE
);
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255) UNIQUE NOT NULL,
    photo_path VARCHAR(255),
    cgpa DOUBLE,
    graduation_year INT,
);
CREATE TABLE placement_filter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    placement_id BIGINT NOT NULL,
    specialisation_id BIGINT NOT NULL,
    domain_id BIGINT NOT NULL,
    CONSTRAINT fk_placement FOREIGN KEY (placement_id) REFERENCES placements(id) ON DELETE CASCADE,
    CONSTRAINT fk_specialisation FOREIGN KEY (specialisation_id) REFERENCES specialisation(id) ON DELETE CASCADE,
    CONSTRAINT fk_domain FOREIGN KEY (domain_id) REFERENCES domain(id) ON DELETE CASCADE
);
CREATE TABLE student_courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    grade_id BIGINT NOT NULL,
    comments TEXT,
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    CONSTRAINT fk_grade FOREIGN KEY (grade_id) REFERENCES grades(id) ON DELETE SET NULL
);
CREATE TABLE placement_student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    placement_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    cv_application MEDIUMBLOB,
    about TEXT,
    acceptance BOOLEAN,
    comments TEXT,
    date DATE,
    CONSTRAINT fk_placement FOREIGN KEY (placement_id) REFERENCES placements(id) ON DELETE CASCADE,
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);
CREATE TABLE specialisation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255),
    description TEXT,
    year INT,
    credits_required INT
);
CREATE TABLE grades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    grade_letter VARCHAR(10),
    grade_point DOUBLE,
    comments TEXT
);
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255),
    description TEXT,
    year INT,
    term VARCHAR(255),
    faculty VARCHAR(255),
    credits INT,
    capacity INT
);
CREATE TABLE organisation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    address TEXT
);
CREATE TABLE domain (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    program VARCHAR(255),
    batch VARCHAR(255),
    capacity INT,
    qualification VARCHAR(255)
);


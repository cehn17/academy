-- =================================================================================
-- 1. USUARIOS (USERS)
-- Contraseña para todos: "clave123"
-- =================================================================================

-- ID 1: ADMIN
INSERT INTO "users" (id, username, password, email, role)
VALUES (1, 'admin', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 'admin@academy.com', 'ADMINISTRATOR');

-- ID 2: PROFESOR (Walter White)
INSERT INTO "users" (id, username, password, email, role)
VALUES (2, 'walter', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 'walter@academy.com', 'TEACHER');

-- ID 3: PROFESOR (Saul Goodman)
INSERT INTO "users" (id, username, password, email, role)
VALUES (3, 'saul', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 'saul@academy.com', 'TEACHER');

-- ID 4: ALUMNO (Jesse Pinkman)
INSERT INTO "users" (id, username, password, email, role)
VALUES (4, 'jesse', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 'jesse@academy.com', 'STUDENT');

-- ID 5: ALUMNO (Kim Wexler)
INSERT INTO "users" (id, username, password, email, role)
VALUES (5, 'kim', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 'kim@academy.com', 'STUDENT');

ALTER TABLE "users" ALTER COLUMN id RESTART WITH 6;


-- =================================================================================
-- 2. PERFILES (TEACHERS & STUDENTS)
-- =================================================================================

-- Profesores
INSERT INTO teachers (id, name, last_name, specialization, user_id)
VALUES (1, 'Walter', 'White', 'Química y Java Avanzado', 2);

INSERT INTO teachers (id, name, last_name, specialization, user_id)
VALUES (2, 'Saul', 'Goodman', 'Leyes y Python Legal', 3);

-- Alumnos
INSERT INTO students (id, name, last_name, dni, address, user_id)
VALUES (1, 'Jesse', 'Pinkman', '11223344', 'Calle Falsa 123', 4);

INSERT INTO students (id, name, last_name, dni, address, user_id)
VALUES (2, 'Kim', 'Wexler', '55667788', 'Albuquerque Centro', 5);

ALTER TABLE students ALTER COLUMN id RESTART WITH 3;

-- =================================================================================
-- 3. CURSOS (COURSES)
-- =================================================================================

INSERT INTO courses (id, name, description, active)
VALUES (1, 'Introducción a Spring Boot', 'Curso intensivo de backend con Java', true);

INSERT INTO courses (id, name, description, active)
VALUES (2, 'Python para Data Science', 'Análisis de datos e IA', true);

INSERT INTO courses (id, name, description, active)
VALUES (3, 'Seguridad Informática', 'Hacking ético y redes', false); -- Curso inactivo de ejemplo

ALTER TABLE courses ALTER COLUMN id RESTART WITH 4;

-- =================================================================================
-- 4. RELACIÓN CURSO-PROFESOR (COURSE_TEACHERS - ManyToMany)
-- =================================================================================

-- Walter da Spring Boot (Curso 1)
INSERT INTO course_teachers (course_id, teacher_id) VALUES (1, 1);

-- Saul da Python (Curso 2)
INSERT INTO course_teachers (course_id, teacher_id) VALUES (2, 2);

-- Ambos dan Seguridad (Curso 3 - Ejemplo de curso compartido)
INSERT INTO course_teachers (course_id, teacher_id) VALUES (3, 1);
INSERT INTO course_teachers (course_id, teacher_id) VALUES (3, 2);


-- =================================================================================
-- 5. HORARIOS DE CURSADA (COURSE_SCHEDULES)
-- =================================================================================

-- Horario 1: Spring Boot - Turno Noche
INSERT INTO course_schedules (id, course_id, start_time, end_time, room)
VALUES (1, 1, '18:00:00', '22:00:00', 'Aula Virtual 1');

-- Horario 2: Python - Turno Mañana
INSERT INTO course_schedules (id, course_id, start_time, end_time, room)
VALUES (2, 2, '09:00:00', '13:00:00', 'Laboratorio B');

ALTER TABLE course_schedules ALTER COLUMN id RESTART WITH 3;


-- =================================================================================
-- 6. DÍAS DE CURSADA (SCHEDULE_DAYS - ElementCollection)
-- =================================================================================

-- El Horario 1 (Spring) es Lunes y Miércoles
INSERT INTO schedule_days (schedule_id, assigned_day) VALUES (1, 'MONDAY');
INSERT INTO schedule_days (schedule_id, assigned_day) VALUES (1, 'WEDNESDAY');

-- El Horario 2 (Python) es Martes y Jueves
INSERT INTO schedule_days (schedule_id, assigned_day) VALUES (2, 'TUESDAY');
INSERT INTO schedule_days (schedule_id, assigned_day) VALUES (2, 'THURSDAY');


-- =================================================================================
-- 7. PROFESORES ASIGNADOS AL HORARIO (COURSE_SCHEDULE_TEACHERS)
-- A veces el titular del curso no es quien da este horario específico
-- =================================================================================

INSERT INTO course_schedule_teachers (course_schedule_id, teacher_id) VALUES (1, 1); -- Walter da la noche
INSERT INTO course_schedule_teachers (course_schedule_id, teacher_id) VALUES (2, 2); -- Saul da la mañana


-- =================================================================================
-- 8. INSCRIPCIONES (ENROLLMENTS)
-- =================================================================================

-- Jesse se inscribe a Spring Boot (Horario 1)
INSERT INTO enrollments (id, student_id, course_schedule_id, enrollment_date, status)
VALUES (1, 1, 1, CURRENT_TIMESTAMP, 'ACTIVE');

-- Kim se inscribe a Python (Horario 2)
INSERT INTO enrollments (id, student_id, course_schedule_id, enrollment_date, status)
VALUES (2, 2, 2, CURRENT_TIMESTAMP, 'ACTIVE');
package com.cehn17.academy.user.util;

public enum RolePermission {

    // Permisos de Cursos y Horarios
    READ_ALL_COURSES,
    READ_ONE_COURSE,
    CREATE_COURSE,
    UPDATE_COURSE,
    DELETE_COURSE,

    // Permisos de Usuarios y Perfiles
    READ_ALL_STUDENTS,
    READ_ONE_STUDENT,
    DELETE_STUDENT,
    UPDATE_ANY_STUDENT,

    READ_ALL_TEACHERS,
    READ_ONE_TEACHER,
    DELETE_TEACHER,
    UPDATE_ANY_TEACHER,

    // Permisos de Inscripción y Académicos
    ENROLL_STUDENT,
    UPDATE_GRADES,
    READ_MY_ENROLLMENTS,

    // Permiso Administrativo General
    ADMIN_PANEL,

    READ_MY_PROFILE,
    UPDATE_MY_PROFILE;
}

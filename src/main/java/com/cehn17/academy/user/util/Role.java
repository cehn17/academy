package com.cehn17.academy.user.util;

import java.util.Arrays;
import java.util.List;

public enum Role {

    ADMINISTRATOR(Arrays.asList(
            RolePermission.READ_ALL_COURSES,
            RolePermission.READ_ONE_COURSE,
            RolePermission.CREATE_COURSE,
            RolePermission.UPDATE_COURSE,
            RolePermission.DELETE_COURSE,

            RolePermission.READ_ALL_STUDENTS,
            RolePermission.READ_ONE_STUDENT,
            RolePermission.DELETE_STUDENT,
            RolePermission.UPDATE_ANY_STUDENT,

            RolePermission.READ_ALL_TEACHERS,
            RolePermission.READ_ONE_TEACHER,
            RolePermission.DELETE_TEACHER,
            RolePermission.UPDATE_ANY_TEACHER,

            RolePermission.ADMIN_PANEL,
            RolePermission.READ_MY_PROFILE
    )),

    TEACHER(Arrays.asList(
            RolePermission.READ_ALL_COURSES,
            RolePermission.READ_ONE_COURSE,
            RolePermission.READ_ALL_STUDENTS,
            RolePermission.UPDATE_GRADES,
            RolePermission.READ_MY_PROFILE,
            RolePermission.UPDATE_MY_PROFILE
    )),

    STUDENT(Arrays.asList(
            RolePermission.READ_ALL_COURSES,
            RolePermission.READ_ONE_COURSE,
            RolePermission.READ_MY_ENROLLMENTS,
            RolePermission.ENROLL_STUDENT,
            RolePermission.READ_MY_PROFILE,
            RolePermission.UPDATE_MY_PROFILE
    ));

    private List<RolePermission> permissions;

    Role(List<RolePermission> permissions) {
        this.permissions = permissions;
    }

    public List<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<RolePermission> permissions) {
        this.permissions = permissions;
    }
}

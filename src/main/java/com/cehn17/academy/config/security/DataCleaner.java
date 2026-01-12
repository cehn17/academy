package com.cehn17.academy.config.security;

import com.cehn17.academy.teacherreview.repository.TeacherReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataCleaner implements CommandLineRunner {

    @Autowired
    private TeacherReviewRepository teacherReviewRepository;

    @Override
    public void run(String... args) throws Exception {
        teacherReviewRepository.deleteAll();
        System.out.println("--- Colección de Mongo limpiada ---");
    }
}

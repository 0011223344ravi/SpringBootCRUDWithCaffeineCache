package com.example.springcaffeinecache.service;


import com.example.springcaffeinecache.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(Long id);
    Student save(Student student);
    void deleteById(Long id);
}


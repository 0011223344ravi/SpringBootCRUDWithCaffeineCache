package com.example.springcaffeinecache.service;


import com.example.springcaffeinecache.entity.Student;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@Component
public interface StudentService {
    List<Student> findAll();
    CompletableFuture<Student> findById(Long id);
    CompletableFuture<Student> save(Student student);
   Student deleteById(Long id);
}


package com.example.springcaffeinecache.controller;


import com.example.springcaffeinecache.entity.Student;
import com.example.springcaffeinecache.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Slf4j
@CacheConfig(cacheNames ={"studentscache"})
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id")
    public Student findById(@PathVariable Long id) {
        log.info("Employee data fetched from database:: "+id);
        return studentService.findById(id);
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.save(student);
    }

    @PutMapping("/{id}")
    @CachePut(key = "#id")
    public Student update(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        return studentService.save(student);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id")
    public void delete(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}


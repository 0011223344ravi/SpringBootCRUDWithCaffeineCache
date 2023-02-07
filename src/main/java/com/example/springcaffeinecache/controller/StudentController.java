package com.example.springcaffeinecache.controller;


import com.example.springcaffeinecache.entity.Student;
import com.example.springcaffeinecache.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/students")
@Slf4j
//@CacheConfig(cacheNames ={"studentscache"})
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
   // @Cacheable(key = "#id")
    public CompletableFuture<Student> findById(@PathVariable Long id) {

        return studentService.findById(id);
    }

    @PostMapping
    public CompletableFuture<Student> create(@RequestBody Student student) {
        return studentService.save(student);
    }

    @PutMapping("/{id}")
   // @CachePut(key = "#id")
    public CompletableFuture<Student> update(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        return studentService.save(student);
    }

    @DeleteMapping("/{id}")
   // @CacheEvict(key = "#id")
    public Student delete(@PathVariable Long id) {
        return studentService.deleteById(id);
    }
}


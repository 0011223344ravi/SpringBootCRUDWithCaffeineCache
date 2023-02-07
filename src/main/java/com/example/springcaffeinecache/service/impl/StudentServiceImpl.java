package com.example.springcaffeinecache.service.impl;


import com.example.springcaffeinecache.entity.Student;
import com.example.springcaffeinecache.repo.StudentRepository;
import com.example.springcaffeinecache.service.StudentService;
import com.github.benmanes.caffeine.cache.*;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

   /* AsyncLoadingCache<Long, Student> cache11 = (AsyncLoadingCache<Long, Student>) Caffeine.newBuilder()
            .expireAfter(new Expiry<Long, Student>() {
                @Override
                public long expireAfterCreate(Long aLong, Student student, long l) {
                    return 0;
                }

                @Override
                public long expireAfterUpdate(Long aLong, Student student, long l, @NonNegative long l1) {
                    return 0;
                }

                @Override
                public long expireAfterRead(Long aLong, Student student, long l, @NonNegative long l1) {
                    return 0;
                }


            }).build(id->studentRepository.findById(id).orElse(null));
*/
    @Override
    public List<Student> findAll() {

        return studentRepository.findAll();
    }

    @Override
    public CompletableFuture<Student> findById(Long id) {
        AsyncLoadingCache< Long, Student > cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.MINUTES)
                .buildAsync(k -> studentRepository.findById(k).orElse(null));
        return cache.get(id);


    }

    @Override
    public CompletableFuture<Student> save(Student student) {

        AsyncLoadingCache< Student, Student > cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.NANOSECONDS)
                .buildAsync(s -> studentRepository.save(s));
        return cache.get(student);

      //  return studentRepository.save(student);
    }

    @Override
    public Student deleteById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
         studentRepository.deleteById(id);
         return student;
    }
}


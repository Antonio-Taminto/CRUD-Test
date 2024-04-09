package com.CRUDTest.controllers;

import com.CRUDTest.entites.StudentEntity;
import com.CRUDTest.servicies.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService service;
    @PostMapping("/poststudent")
    public ResponseEntity<StudentEntity> postStudent(@RequestBody StudentEntity student){
        return ResponseEntity.ok().body(service.createStudent(student));
    }
    @GetMapping("/getstudents")
    public ResponseEntity<List<StudentEntity>> getStudentList(){
        return ResponseEntity.ok().body(service.getAllStudents());
    }
    @GetMapping("/getstudent/{id}")
    public ResponseEntity<StudentEntity> getStudentFromId(@PathVariable Long id){
        Optional<StudentEntity> studentOptional = service.getStudentFromId(id);
        if (studentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(studentOptional.get());
    }
    @PutMapping("/updatestudent/{id}")
    public ResponseEntity<StudentEntity> updateStudentNameAndSurname(
            @PathVariable Long id,@RequestBody StudentEntity student){
        Optional<StudentEntity> studentOptional = service.updateStudenteNameSurname(id,student);
        if(studentOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(studentOptional.get());
    }
    @PutMapping("/updatestudentworkingstatus/{id}")
    public ResponseEntity<StudentEntity> updateStudentWorkingStatus(
            @PathVariable Long id,@RequestParam Boolean working){
        Optional<StudentEntity> studentOptional = service.updateStudenteWorkingStatus(id,working);
        if(studentOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(studentOptional.get());
    }
    @DeleteMapping("/deletestudent/{id}")
    public ResponseEntity<StudentEntity> deleteStudentFromId(@PathVariable Long id){
        Optional<StudentEntity>studentOptional = service.deleteStudenteFromId(id);
        if(studentOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(studentOptional.get());
    }
}

package com.CRUDTest.servicies;

import com.CRUDTest.entites.StudentEntity;
import com.CRUDTest.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;
    public StudentEntity createStudent(StudentEntity studentEntity){
        return repository.save(studentEntity);
    }
    public List<StudentEntity> getAllStudents(){
        return repository.findAll();
    }
    public Optional<StudentEntity> getStudentFromId(Long id){
        return repository.findById(id);
    }
    public Optional<StudentEntity> updateStudenteNameSurname(Long id,StudentEntity studentEntity){
        Optional<StudentEntity> studentEntityOptional = repository.findById(id);
        if(studentEntityOptional.isPresent()){
            studentEntityOptional.get().setName(studentEntity.getName());
            studentEntityOptional.get().setSurname(studentEntity.getSurname());
            StudentEntity studentUpdated = repository.save(studentEntityOptional.get());
            return Optional.of(studentUpdated);
        }else {
            return Optional.empty();
        }
    }
    public Optional<StudentEntity> updateStudenteWorkingStatus(Long id,Boolean status){
        Optional<StudentEntity> studentEntityOptional = repository.findById(id);
        if(studentEntityOptional.isPresent()){
            studentEntityOptional.get().setWorking(status);
            StudentEntity studentUpdated = repository.save(studentEntityOptional.get());
            return Optional.of(studentUpdated);
        }else
            return Optional.empty();
    }
    public Optional<StudentEntity> deleteStudenteFromId(Long id){
        Optional<StudentEntity> studentEntityOptional = repository.findById(id);
        if(studentEntityOptional.isPresent()){
            repository.delete(studentEntityOptional.get());
            return studentEntityOptional;
        }else {
            return Optional.empty();
        }
    }
}

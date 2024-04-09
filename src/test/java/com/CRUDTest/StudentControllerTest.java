package com.CRUDTest;

import com.CRUDTest.entites.StudentEntity;
import com.CRUDTest.controllers.StudentController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentControllerTest {
    @Autowired
    private StudentController controller;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void studentControllerLoads(){
        assertThat(controller).isNotNull();
    }
    @Test
    @Order(1)
    void createStudentTest() throws Exception {
        StudentEntity student = new StudentEntity();

        student.setName("Antonio");
        student.setSurname("Taminto");
        student.setWorking(false);
        //dato uno studente ritorna il Json del medesimo
        String studentJson = objectMapper.writeValueAsString(student);
        //utlizzando il Json utilizziamo mockMvc per fare una chiamata post
        MvcResult result =  this.mockMvc.perform(post("/student/poststudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        StudentEntity studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(),StudentEntity.class);

        assertThat(studentFromResponse).isNotNull();
    }

    @Test
    @Order(2)
    void readAllStudentTest() throws Exception {
        MvcResult result =  this.mockMvc.perform(get("/student/getstudents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        List studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(),List.class);

        assertThat(studentFromResponse.size()).isNotZero();
    }
    @Test
    @Order(3)
    void readStudentTest() throws Exception{
        Long id = 1L;
        MvcResult result = this.mockMvc.perform(get("/student/getstudent/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        StudentEntity studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(),StudentEntity.class);
        assertThat(studentFromResponse.getId().equals(id));
    }
    @Test
    @Order(4)
    void updateStudentTest() throws Exception{
        Long id = 1L;
        StudentEntity studentForUpdate = new  StudentEntity();
        studentForUpdate.setName("Giacomo");
        studentForUpdate.setSurname("Poretti");
        studentForUpdate.setWorking(true);
        String studentJsonForUpdate = objectMapper.writeValueAsString(studentForUpdate);

        MvcResult result = this.mockMvc.perform(put("/student/updatestudent/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJsonForUpdate))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        StudentEntity studentFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(),StudentEntity.class);
        assertThat(studentFromResponse.getId().equals(id)
                && studentFromResponse.getName().equals(studentForUpdate.getName())
                && studentFromResponse.getSurname().equals(studentForUpdate.getSurname())
                && studentFromResponse.isWorking());

    }
    @Test
    @Order(5)
    void deleteStudentTest() throws Exception{
        Long id = 1L;
        this.mockMvc.perform(delete("/student/deletestudent/"+id))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/student/getstudent/"+id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}

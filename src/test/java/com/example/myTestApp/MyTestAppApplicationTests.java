package com.example.myTestApp;


import com.example.myTestApp.model.Expression;
import com.example.myTestApp.repository.ExpressionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class MyTestAppApplicationTests {
    @Autowired
    private ExpressionRepository repository;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        assertThat(repository).isNotNull();
        assertThat(mockMvc).isNotNull();
        assertThat(objectMapper).isNotNull();
    }

    @BeforeEach
    public void addData() {
        var firstExpression = new Expression(null, "2+2*2", 6.0, 3);
        var secondExpression = new Expression(null, "2(2+2)/8", 1.0, 4);
        repository.saveAll(List.of(firstExpression, secondExpression));
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void getResult() throws Exception {
        var expression = new Expression(null, "2+2*2", null, null);
        this.mockMvc.perform(post("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expression)))
                .andExpect(jsonPath("result", equalTo(6.0)))
                .andExpect(jsonPath("numOfDoubles", equalTo(3)));
    }

    @Test
    public void updateExpression() throws Exception {
        var expression = new LinkedList<>(repository.findAll()).getLast();
        expression.setArithmeticExpression("5+5*5");
        this.mockMvc.perform(patch("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expression)))
                .andExpect(jsonPath("result", equalTo(30.0)))
                .andExpect(jsonPath("numOfDoubles", equalTo(3)));
    }

    @Test
    public void getExpressions() throws Exception {
        this.mockMvc.perform(get("/task/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getExpressionsByResult() throws Exception {
        this.mockMvc.perform(get("/task/all/6.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getExpressionsWithLowerResults() throws Exception {
        this.mockMvc.perform(get("/task/all/lower/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getExpressionsWithHigherResults() throws Exception {
        this.mockMvc.perform(get("/task/all/higher/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void updateExpression_shouldThrowExpressionNotFoundException() throws Exception{
        var expression = new LinkedList<>(repository.findAll()).getLast();
        expression.setId(expression.getId() +1);
        this.mockMvc.perform(patch("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expression)))
                .andExpect(jsonPath("message", equalTo("entity not found exception")))
                .andExpect(jsonPath("debugMessage", equalTo("expression not found! id = " + expression.getId())));
    }

    @Test
    public void updateExpression_shouldThrowHandleHttpMessageNotReadable() throws Exception{
        this.mockMvc.perform(patch("/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("abracadabra")))
                .andExpect(jsonPath("message", equalTo("Malformed JSON Request")));
    }
    @Test
    public void getExpressionsWithLowerResults_shouldThrowHandleMethodArgumentTypeMismatchException() throws Exception {
        this.mockMvc.perform(get("/task/all/lower/d")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("message", equalTo("bad argument type")));
    }

    @Test
    public void handleNoHandlerFoundException() throws Exception {
        this.mockMvc.perform(get("/task/snr"))
                .andExpect(status().isNotFound());
    }

}

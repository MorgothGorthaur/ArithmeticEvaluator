package com.example.myTestApp;

import com.example.myTestApp.model.Expression;
import com.example.myTestApp.repository.ExpressionRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class MyTestAppApplicationTests {
    @Autowired
    private ExpressionRepository repository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertThat(repository).isNotNull();
        assertThat(mockMvc).isNotNull();
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
    public void getResult() {
    }

}

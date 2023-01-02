package com.example.myTestApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expressions")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Expression {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "arithmetic_expression")
    private String arithmeticExpression;
    private Double result;
    @Column(name = "num_of_doubles")
    private Integer numOfDoubles;
}

package com.example.demo.sec02.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    
    @Id
    private Integer id;

    @Column(value = "name")
    private String name;
    private String email;


}

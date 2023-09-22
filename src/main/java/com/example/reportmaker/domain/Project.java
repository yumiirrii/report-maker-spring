package com.example.reportmaker.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Project {
    private Integer id;
    private String name;
}

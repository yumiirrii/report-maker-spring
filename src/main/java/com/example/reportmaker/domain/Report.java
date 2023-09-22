package com.example.reportmaker.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
public class Report {
    private Integer id;
    @NotBlank(message = "this field is mandatory")
    private Date week;
    private String summary;
}

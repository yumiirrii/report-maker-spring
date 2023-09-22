package com.example.reportmaker.domain;

import lombok.Data;

@Data
public class Task {
    private Integer id;
    private Integer reportId;
    private Integer projectId;
    private String task;
    private boolean isDone;
}

package com.example.reportmaker.dto;

import com.example.reportmaker.domain.Project;
import com.example.reportmaker.domain.Task;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class ReportViewDto {
    private Integer id;
    private Date week;
    private String summary;
    private List<Project> projects;
    private List<Task> tasks;
}

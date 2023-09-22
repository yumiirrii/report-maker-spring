package com.example.reportmaker.form;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReportForm {
    private Integer id;
    private String week;
    private String summary;
    private List<String> projectList;
    private List<Map<String, List<String>>> doneTaskMapList;
    private List<Map<String, List<String>>> planningTaskMapList;
}

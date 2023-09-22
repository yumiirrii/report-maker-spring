package com.example.reportmaker.mapper;

import com.example.reportmaker.domain.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper {

    void insertTask(Task task);

    void deleteTaskByReportId(Integer reportId);
}

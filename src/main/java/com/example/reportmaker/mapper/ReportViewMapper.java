package com.example.reportmaker.mapper;

import com.example.reportmaker.domain.Report;
import com.example.reportmaker.dto.ReportViewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReportViewMapper {
    List<ReportViewDto> selectAllReportView();

    ReportViewDto selectReportViewDetail(Integer reportId);

    List<ReportViewDto> selectSameProjectTask(Integer reportId, Integer projectId);

    List<ReportViewDto> selectPlanningTaskByProjectName(String projectName);
}

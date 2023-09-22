package com.example.reportmaker.mapper;

import com.example.reportmaker.domain.Report;
import com.example.reportmaker.form.ReportForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReportMapper {

    void insertReport(Report report);

    Report selectReportByWeek(Date week);

    void deleteReportById(Integer id);

}

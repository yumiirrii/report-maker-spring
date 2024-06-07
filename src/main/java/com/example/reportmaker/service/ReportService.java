package com.example.reportmaker.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reportmaker.domain.Project;
import com.example.reportmaker.domain.Report;
import com.example.reportmaker.domain.Task;
import com.example.reportmaker.dto.ReportViewDto;
import com.example.reportmaker.form.ReportForm;
import com.example.reportmaker.mapper.ProjectMapper;
import com.example.reportmaker.mapper.ReportMapper;
import com.example.reportmaker.mapper.ReportViewMapper;
import com.example.reportmaker.mapper.TaskMapper;

@Service
public class ReportService {

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    ReportViewMapper reportViewMapper;

    public static String convertWeekToWeekStartDate(String weekInput) {
        LocalDate weekStartDate = LocalDate.parse(weekInput + "-1", DateTimeFormatter.ISO_WEEK_DATE);
        String formattedDate = weekStartDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return formattedDate;
    }

    @Transactional
    public boolean addNewReport(String weekStartDate, String summary) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(weekStartDate);
            Report report = new Report();
            report.setWeek(date);
            report.setSummary(summary);
            reportMapper.insertReport(report);
        } catch (ParseException e) {
            System.out.println("parse exception");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    public Report getReportByWeek(String week) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Report report = new Report();
        try {
            report = reportMapper.selectReportByWeek(format.parse(week));
        } catch (ParseException e) {
            System.out.println("parse exception");
        }
        return report;
    }

    @Transactional
    public void addNewProject(String projectName) {
        Project project = new Project();
        project.setName(projectName);
        projectMapper.insertProject(project);
    }

    @Transactional
    public Project getProjectByName(String projectName) {
        Project project = projectMapper.selectProjectByName(projectName);
        return project;
    }

    @Transactional
    public void addNewTask(Integer reportId, Integer projectId, String taskName, boolean isDone) {
        Task task = new Task();
        task.setReportId(reportId);
        task.setProjectId(projectId);
        task.setTask(taskName);
        task.setDone(isDone);
        taskMapper.insertTask(task);
    }

    @Transactional
    public boolean registerWeeklyReport(ReportForm reportForm)  {
        List<Project> projectList = new ArrayList<>();
        for(String projectName : reportForm.getProjectList()) {
            Project project = getProjectByName(projectName);
            if (project == null) {
                addNewProject(projectName);
                project = getProjectByName(projectName);
            }
            projectList.add(project);
        }
        boolean isReportAdded = addNewReport(reportForm.getWeek(), reportForm.getSummary());
        if(!isReportAdded) {
            return false;
        }
        Report report = getReportByWeek(reportForm.getWeek());
        System.out.println("list:" + reportForm.getDoneTaskMapList());
        for(Map<String, List<String>> map : reportForm.getDoneTaskMapList()) {
            for(Project project : projectList) {
                if(map.containsKey(project.getName())) {
                    for(String taskName : map.get(project.getName())) {
                        this.addNewTask(report.getId(), project.getId(), taskName, true);
                    }
                }
            }
        }

        for(Map<String, List<String>> map : reportForm.getPlanningTaskMapList()) {
            for(Project project : projectList) {
                if(map.containsKey(project.getName())) {
                    for(String taskName : map.get(project.getName())) {
                        this.addNewTask(report.getId(), project.getId(), taskName, false);
                    }
                }
            }
        }
        return true;
    }

    @Transactional
    public List<ReportForm> getAllReportView() {
        List<ReportViewDto> reportViewList = reportViewMapper.selectAllReportView();
        List<ReportForm> reportFormList = new ArrayList<>();
        for (ReportViewDto reportView : reportViewList) {
            ReportForm form = new ReportForm();
            form.setId(reportView.getId());
            form.setWeek(new SimpleDateFormat("yyyy-MM-dd").format(reportView.getWeek()));
            reportFormList.add(form);
        }
        return reportFormList;
    }

    @Transactional
    public ReportForm getReportViewDetail(Integer reportId) {
        ReportViewDto reportViewDto = reportViewMapper.selectReportViewDetail(reportId);
        ReportForm form = new ReportForm();
        form.setWeek(new SimpleDateFormat("yyyy-MM-dd").format(reportViewDto.getWeek()));
        form.setSummary(reportViewDto.getSummary());
        List<String> projectNameList = new ArrayList<>();
        List<Map<String, List<String>>> doneTaskMapList = new ArrayList<>();
        List<Map<String, List<String>>> planningTaskMapList = new ArrayList<>();
        for (Project project : reportViewDto.getProjects()) {
            projectNameList.add(project.getName());
            List<String> doneTaskList = new ArrayList<>();
            List<String> planningTaskList = new ArrayList<>();
            Map<String, List<String>> doneTaskMap = new HashMap<>();
            Map<String, List<String>> planningTaskMap = new HashMap<>();
            for (Task task : reportViewDto.getTasks()) {
                if (project.getId() == task.getProjectId()) {
                    if (task.isDone()) {
                        doneTaskList.add(task.getTask());
                    } else {
                        planningTaskList.add(task.getTask());
                    }
                }
            }
            doneTaskMap.put(project.getName(), doneTaskList);
            doneTaskMapList.add(doneTaskMap);
            planningTaskMap.put(project.getName(), planningTaskList);
            planningTaskMapList.add(planningTaskMap);
        }
        form.setProjectList(projectNameList);
        form.setDoneTaskMapList(doneTaskMapList);
        form.setPlanningTaskMapList(planningTaskMapList);
        return form;
    }

    @Transactional
    public void deleteReport(Integer reportId) {
        ReportViewDto reportViewDto = reportViewMapper.selectReportViewDetail(reportId);
        for (Project project : reportViewDto.getProjects()) {
            List<ReportViewDto> reportViewDtoList = reportViewMapper.selectSameProjectTask(reportId, project.getId());
            if (reportViewDtoList.isEmpty() || reportViewDtoList == null) {
                projectMapper.deleteProjectByProjectId(project.getId());
            }
        }
        taskMapper.deleteTaskByReportId(reportId);
        reportMapper.deleteReportById(reportId);
    }

    @Transactional
    public List<Task> getPlanningTaskByProjectName(String projectName) {
        List<ReportViewDto> reportViewDtoList = reportViewMapper.selectPlanningTaskByProjectName(projectName);
        List<Task> planningTaskList = new ArrayList<>();
        for (ReportViewDto dto : reportViewDtoList) {
            for (Task planningTask : dto.getTasks()) {
                planningTaskList.add(planningTask);
            }
        }
        return planningTaskList;
    }

    public Map<String, List<ReportForm>> getAllReportViewMonthly(List<ReportForm> reportFormList) {
        Map<String, List<ReportForm>> reportFormMonthlyMap = new TreeMap<>(Comparator.reverseOrder());
        for (ReportForm form : reportFormList) {
            String yearMonth = form.getWeek().substring(0,7);
            reportFormMonthlyMap.computeIfAbsent(yearMonth, (key) -> new ArrayList<>()).add(form);
        }
        return reportFormMonthlyMap;
    }
}

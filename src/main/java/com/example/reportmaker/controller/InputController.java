package com.example.reportmaker.controller;

import com.example.reportmaker.domain.Task;
import com.example.reportmaker.dto.ReportViewDto;
import com.example.reportmaker.form.ReportForm;
import com.example.reportmaker.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class InputController {

    @Autowired
    ReportService reportService;

    ReportForm reportForm;
    String inputProject;
    List<String> inputProjectList;
    List<String> lastPlanningTaskList;
    List<String> inputDoneTaskList;
    List<String> inputPlanningTaskList;
    List<Map<String, List<String>>> inputDoneTaskMapList;
    List<Map<String, List<String>>> inputPlanningTaskMapList;

    @GetMapping("/")
    public String top() {
        return "top";
    }

    @PostMapping(value="/input")
    public String init(@RequestParam("menu") String menu, Model model) {
        if(menu.equals("INPUT")) {
            reportForm = new ReportForm();
            inputProject = null;
            inputProjectList = new ArrayList<>();
            inputDoneTaskList = new ArrayList<>();
            inputPlanningTaskList = new ArrayList<>();
            inputDoneTaskMapList = new ArrayList<>();
            inputPlanningTaskMapList = new ArrayList<>();
        }
        if(menu.equals("SEARCH")) {
            List<ReportForm> reportFormList = reportService.getAllReportView();
            Map<String, List<ReportForm>> reportFormMonthlyMap = reportService.getAllReportViewMonthly(reportFormList);
            model.addAttribute("reportFormMonthlyMap", reportFormMonthlyMap);
            return "search";
        }
        return "input";
    }

    @PostMapping(value="/input/addWeek")
    public String test2(@RequestParam("inputWeek") String inputWeek, Model model) {
        if(inputWeek == null || inputWeek.isEmpty()) {
            model.addAttribute("weekError", true);
        } else {
            String weekStartDate = ReportService.convertWeekToWeekStartDate(inputWeek);
            reportForm.setWeek(weekStartDate);
            model.addAttribute("week", reportForm.getWeek());
        }
        return "input";
    }

    @PostMapping(value="/input/addProject")
    public String test3(@RequestParam("inputProject") String inputProject, Model model) {
        model.addAttribute("week", reportForm.getWeek());
        if (inputProject == null || inputProject.isEmpty()) {
            model.addAttribute("projectBlank", true);
        } else if (inputProjectList.contains(inputProject)){
            model.addAttribute("projectDupe", true);
        } else {
            this.inputProject = inputProject;
            inputProjectList.add(this.inputProject);
            lastPlanningTaskList = new ArrayList<>();
            List<Task> planningTaskDtoList = reportService.getPlanningTaskByProjectName(this.inputProject);
            for (Task planningTaskDto : planningTaskDtoList) {
                if (!(planningTaskDto.getTask().equals("none"))) {
                    lastPlanningTaskList.add(planningTaskDto.getTask());
                }
            }
            model.addAttribute("project", this.inputProject);
            model.addAttribute("lastPlanningTaskList", lastPlanningTaskList);
            model.addAttribute("isDoneTaskExist", true);
        }
        return "input";
    }

    @PostMapping(value="/input/addLastPlanningTask")
    public String test34(@RequestParam(name="selectedLastPlanningTasks") String selectedLastPlanningTasks, Model model) {
        String[] selectedLastPlanningTasksArray = selectedLastPlanningTasks.split(",");
        for (String selectedLastPlanningTask : selectedLastPlanningTasksArray) {
            lastPlanningTaskList.remove(selectedLastPlanningTask);
            inputDoneTaskList.add(selectedLastPlanningTask);
        }
        model.addAttribute("week", reportForm.getWeek());
        model.addAttribute("project", inputProject);
        model.addAttribute("doneTaskList", inputDoneTaskList);
        model.addAttribute("planningTaskList", inputPlanningTaskList);
        model.addAttribute("lastPlanningTaskList", lastPlanningTaskList);
        model.addAttribute("isDoneTaskExist", true);
        model.addAttribute("isPlanningTaskExist", true);
        return "input";
    }

    @PostMapping(value="/input/addDoneTask")
    public String test4(@RequestParam("inputDoneTask") String inputDoneTask, @RequestParam(name="isDoneTaskNoneChecked", defaultValue = "false") boolean isDoneTaskNoneChecked, Model model) {
        if (isDoneTaskNoneChecked) {
            inputDoneTaskList.clear();
            inputDoneTaskList.add("none");
            model.addAttribute("isDoneTaskExist", false);
        } else {
            model.addAttribute("isDoneTaskExist", true);
            model.addAttribute("lastPlanningTaskList", lastPlanningTaskList);
            if (inputDoneTask == null || inputDoneTask.isEmpty()) {
                model.addAttribute("doneTaskBlank", true);
            } else if (inputDoneTaskList.contains(inputDoneTask)) {
                model.addAttribute("doneTaskDupe", true);
            } else {
                inputDoneTaskList.add(inputDoneTask);
            }
        }
        if (inputPlanningTaskList.contains("none")) {
            model.addAttribute("isPlanningTaskExist", false);
        } else {
            model.addAttribute("isPlanningTaskExist", true);
        }
        model.addAttribute("week", reportForm.getWeek());
        model.addAttribute("project", inputProject);
        model.addAttribute("doneTaskList", inputDoneTaskList);
        model.addAttribute("planningTaskList", inputPlanningTaskList);
        return "input";
    }

    @PostMapping(value="/input/addPlanningTask")
    public String test5(@RequestParam("inputPlanningTask") String inputPlanningTask, @RequestParam(name="isPlanningTaskNoneChecked", defaultValue = "false") boolean isPlanningTaskNoneChecked, Model model) {
        if (isPlanningTaskNoneChecked) {
            inputPlanningTaskList.clear();
            inputPlanningTaskList.add("none");
            model.addAttribute("isPlanningTaskExist", false);
        } else {
            model.addAttribute("isPlanningTaskExist", true);
            if (inputPlanningTask == null || inputPlanningTask.isEmpty()) {
                model.addAttribute("planningTaskBlank", true);
            } else if (inputPlanningTaskList.contains(inputPlanningTask)) {
                model.addAttribute("planningTaskDupe", true);
            } else {
                inputPlanningTaskList.add(inputPlanningTask);
            }
        }
        if (inputDoneTaskList.contains("none")) {
            model.addAttribute("isDoneTaskExist", false);
        } else {
            model.addAttribute("lastPlanningTaskList", lastPlanningTaskList);
            model.addAttribute("isDoneTaskExist", true);
        }
        model.addAttribute("week", reportForm.getWeek());
        model.addAttribute("project", inputProject);
        model.addAttribute("doneTaskList", inputDoneTaskList);
        model.addAttribute("planningTaskList", inputPlanningTaskList);
        return "input";
    }

    @PostMapping(value="/input/registerProjectAndTask")
    public String test6(@RequestParam("method") String method, Model model) {
        Map<String, List<String>> doneTaskMap = new LinkedHashMap<>();
        doneTaskMap.put(inputProject, inputDoneTaskList);
        inputDoneTaskMapList.add(doneTaskMap);
        Map<String, List<String>> planningTaskMap = new LinkedHashMap<>();
        planningTaskMap.put(inputProject, inputPlanningTaskList);
        inputPlanningTaskMapList.add(planningTaskMap);
        if(method.equals("YES")) {
            inputProject = null;
            inputDoneTaskList = new ArrayList<>();
            inputPlanningTaskList = new ArrayList<>();
            model.addAttribute("week", reportForm.getWeek());
        }
        if(method.equals("NO")) {
            reportForm.setProjectList(inputProjectList);
            reportForm.setDoneTaskMapList(inputDoneTaskMapList);
            reportForm.setPlanningTaskMapList(inputPlanningTaskMapList);
            if (reportForm.getSummary() != null) {
                model.addAttribute("isSucceeded", false);
                model.addAttribute("reportForm", reportForm);
                return "confirm";
            }
            return "input-summary";
        }
        return "input";
    }

    @PostMapping(value="/input/confirm")
    public String test7(@RequestParam("input-summary") String inputSummary, Model model) {
        reportForm.setSummary(inputSummary);
        model.addAttribute("isSucceeded", false);
        model.addAttribute("reportForm", reportForm);
        return "confirm";
    }

    @PostMapping(value="/confirm/submit")
    public String test8(Model model) {
        if (reportForm.getProjectList() == null || reportForm.getProjectList().isEmpty()) {
            model.addAttribute("projectBlank", true);
        } else {
            boolean result = reportService.registerWeeklyReport(reportForm);
            if (!result) {
                model.addAttribute("weekDuplicateError", true);
                model.addAttribute("isSucceeded", false);
            } else {
                model.addAttribute("isSucceeded", true);
            }
        }
        model.addAttribute("reportForm", reportForm);
        return "confirm";
    }

    @PostMapping(value="/confirm/changeweek")
    public String test9(@RequestParam("inputWeek") String inputWeek, Model model) {
        String weekStartDate = ReportService.convertWeekToWeekStartDate(inputWeek);
        reportForm.setWeek(weekStartDate);
        model.addAttribute("isSucceeded", false);
        model.addAttribute("reportForm", reportForm);
        return "confirm";
    }

    @PostMapping(value="/confirm/deleteProjectAndTasks")
    public String test10(@RequestParam("deleteProjectAndTasks") String deleteProjects, Model model) {
        String[] deleteProjectsArray = deleteProjects.split(",");
        for (String project : deleteProjectsArray) {
            reportForm.getProjectList().remove(project);
            for (Map<String, List<String>> doneTaskMap : reportForm.getDoneTaskMapList()) {
                doneTaskMap.remove(project);
            }
            for (Map<String, List<String>> planningTaskMap : reportForm.getPlanningTaskMapList()) {
                planningTaskMap.remove(project);
            }
        }
        model.addAttribute("isSucceeded", false);
        model.addAttribute("reportForm", reportForm);
        return "confirm";
    }

    @PostMapping(value="/confirm/addProject")
    public String test11(Model model) {
        inputProject = null;
        inputDoneTaskList = new ArrayList<>();
        inputPlanningTaskList = new ArrayList<>();
        model.addAttribute("week", reportForm.getWeek());
        return "input";
    }

    @PostMapping(value="/confirm/editSummary")
    public String test12(@RequestParam("input-summary") String editedSummary, Model model) {
        reportForm.setSummary(editedSummary);
        model.addAttribute("isSucceeded", false);
        model.addAttribute("reportForm", reportForm);
        return "confirm";
    }
}

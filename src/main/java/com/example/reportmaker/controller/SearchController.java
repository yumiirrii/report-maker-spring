package com.example.reportmaker.controller;

import com.example.reportmaker.form.ReportForm;
import com.example.reportmaker.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Controller
public class SearchController {

    @Autowired
    ReportService reportService;

    @PostMapping(value="/delete")
    public String deleteReport(@RequestParam(name="deleteReportIds") String deleteReportIds, Model model) {
        String[] deleteReportIdsArray = deleteReportIds.split(",");
        for (String deleteReportId : deleteReportIdsArray) {
            reportService.deleteReport(Integer.parseInt(deleteReportId));
        }
        List<ReportForm> reportFormList = reportService.getAllReportView();
        Map<String, List<ReportForm>> reportFormMonthlyMap = reportService.getAllReportViewMonthly(reportFormList);
        model.addAttribute("reportFormMonthlyMap", reportFormMonthlyMap);
        return "search";
    }

    @GetMapping("/detail/{reportId}")
    public String showReportDetail(@PathVariable Integer reportId, Model model) {
        ReportForm reportForm = reportService.getReportViewDetail(reportId);
        model.addAttribute("reportForm", reportForm);
        return "detail";
    }
//    @PostMapping(value="/delete/{reportId}")
//    public String deleteReport(@PathVariable Integer reportId, Model model) {
//        reportService.deleteReport(reportId);
//        List<ReportForm> reportFormList = reportService.getAllReportView();
//        model.addAttribute("reportList", reportFormList);
//        return "report-search";
//    }
}

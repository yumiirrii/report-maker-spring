package com.example.reportmaker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.reportmaker.form.ReportForm;
import com.example.reportmaker.service.ReportService;

//@Controller
@RestController
public class SearchController {

    @Autowired
    ReportService reportService;

//    @PostMapping(value="/delete")
//    public String deleteReport(@RequestParam(name="deleteReportIds") String deleteReportIds, Model model) {
//        String[] deleteReportIdsArray = deleteReportIds.split(",");
//        for (String deleteReportId : deleteReportIdsArray) {
//            reportService.deleteReport(Integer.parseInt(deleteReportId));
//        }
//        List<ReportForm> reportFormList = reportService.getAllReportView();
//        Map<String, List<ReportForm>> reportFormMonthlyMap = reportService.getAllReportViewMonthly(reportFormList);
//        model.addAttribute("reportFormMonthlyMap", reportFormMonthlyMap);
//        return "search";
//    }
    
    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping(value="/delete")
    public List<ReportForm> deleteReport(@RequestBody List<Integer> deleteReportIds) {
        for (Integer deleteReportId : deleteReportIds) {
            reportService.deleteReport(deleteReportId);
        }
        List<ReportForm> reportFormList = reportService.getAllReportView();
        return reportFormList;
    }
    
//    @GetMapping("/detail/{reportId}")
//    public String showReportDetail(@PathVariable Integer reportId, Model model) {
//        ReportForm reportForm = reportService.getReportViewDetail(reportId);
//        model.addAttribute("reportForm", reportForm);
//        return "detail";
//    }
    
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/detail/{reportId}")
    public ReportForm showReportDetail(@PathVariable Integer reportId) {
        ReportForm reportForm = reportService.getReportViewDetail(reportId);
        return reportForm;
    }
    
//    @PostMapping(value="/delete/{reportId}")
//    public String deleteReport(@PathVariable Integer reportId, Model model) {
//        reportService.deleteReport(reportId);
//        List<ReportForm> reportFormList = reportService.getAllReportView();
//        model.addAttribute("reportList", reportFormList);
//        return "report-search";
//    }
}

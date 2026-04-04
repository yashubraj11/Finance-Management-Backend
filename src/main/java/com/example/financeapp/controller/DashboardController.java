package com.example.financeapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.financeapp.model.FinancialRecord;
import com.example.financeapp.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/summary")
    public Map<String, Object> summary() {
        return service.summary();
    }
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/category-summary")
    public Map<String, Double> categorySummary() {
        return service.categorySummary();
    }
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/recent")
    public List<FinancialRecord> recent(@RequestParam(defaultValue = "5") int limit) {
        return service.recent(limit);
    }
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    @GetMapping("/monthly-trend")
    public Map<String, Map<String, Double>> monthlyTrend() {
        return service.monthlyTrend();
    }
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    @GetMapping("/full")
    public Map<String, Object> fullDashboard() {
        return service.fullDashboard();
    }
}
package com.example.financeapp.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.financeapp.model.FinancialRecord;
import com.example.financeapp.repository.RecordRepository;

@Service
public class DashboardService {

    @Autowired
    private RecordRepository repo;

    
    public Map<String, Object> summary() {

        List<FinancialRecord> list = repo.findAll();

        double income = list.stream()
                .filter(r -> r.getType().name().equals("INCOME"))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double expense = list.stream()
                .filter(r -> r.getType().name().equals("EXPENSE"))
                .mapToDouble(FinancialRecord::getAmount)
                .sum();

        double balance = income - expense;

        String status;
        String color;
        String message;

        if (income > expense) {
            status = "PROFIT";
            color = "green";
            message = "You are saving money";
        } else if (income < expense) {
            status = "LOSS";
            color = "red";
            message = "You are overspending";
        } else {
            status = "NEUTRAL";
            color = "gray";
            message = "Balanced spending";
        }

        double savingsRate = income == 0 ? 0 : (balance / income) * 100;
        double expenseRatio = income == 0 ? 0 : (expense / income) * 100;

        return Map.of(
                "summary", Map.of(
                        "totalIncome", income,
                        "totalExpense", expense,
                        "netBalance", balance
                ),
                "insights", Map.of(
                        "status", status,
                        "message", message,
                        "savingsRate", savingsRate,
                        "expenseRatio", expenseRatio
                ),
                "ui", Map.of("color", color),
                "stats", Map.of("totalTransactions", list.size())
        );
    }

    
    public Map<String, Double> categorySummary() {
        return repo.findAll().stream()
                .filter(r -> r.getType().name().equals("EXPENSE"))
                .collect(Collectors.groupingBy(
                        FinancialRecord::getCategory,
                        Collectors.summingDouble(FinancialRecord::getAmount)
                ));
    }

    
    public List<FinancialRecord> recent(int limit) {
        return repo.findAll().stream()
                .sorted((a, b) -> b.getDate().compareTo(a.getDate()))
                .limit(limit)
                .toList();
    }

   
    public Map<String, Map<String, Double>> monthlyTrend() {
        return repo.findAll().stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDate().getMonth().toString(),
                        Collectors.groupingBy(
                                r -> r.getType().name(),
                                Collectors.summingDouble(FinancialRecord::getAmount)
                        )
                ));
    }

    
    public Map<String, Object> fullDashboard() {
        return Map.of(
                "summary", summary(),
                "categorySummary", categorySummary(),
                "recent", recent(5),
                "monthlyTrend", monthlyTrend()
        );
    }
}
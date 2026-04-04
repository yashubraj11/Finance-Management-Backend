package com.example.financeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.example.financeapp.model.FinancialRecord;
import com.example.financeapp.service.RecordService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService service;

    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public FinancialRecord create(@Valid @RequestBody FinancialRecord record) {
        return service.create(record);
    }

    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public List<FinancialRecord> getAll() {
        return service.getAll();
    }

    
    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public Page<FinancialRecord> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return service.getAll(page, size);
    }

    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted Successfully";
    }

    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FinancialRecord update(@PathVariable Long id,
                                 @RequestBody FinancialRecord record) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();

        return service.update(id, record, role);
    }

    
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public List<FinancialRecord> filter(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        return service.filter(type, category, startDate, endDate);
    }
}
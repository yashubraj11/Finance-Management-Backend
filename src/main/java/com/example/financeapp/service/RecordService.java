package com.example.financeapp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.financeapp.model.FinancialRecord;
import com.example.financeapp.repository.RecordRepository;

@Service
public class RecordService {

    @Autowired
    private RecordRepository repo;

    public FinancialRecord create(FinancialRecord record) {
        if (record.getDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Date cannot be in future");
        }
        return repo.save(record);
    }

    public List<FinancialRecord> getAll() {
        return repo.findAll();
    }

    public Page<FinancialRecord> getAll(int page, int size) {
        return repo.findAll(PageRequest.of(page, size));
    }

    public void delete(Long id) {
        FinancialRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        repo.delete(record);
    }

    public FinancialRecord update(Long id, FinancialRecord updatedRecord, String role) {

        if (!role.equals("ROLE_ADMIN")) {
            throw new RuntimeException("Only ADMIN can update");
        }

        FinancialRecord existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        existing.setAmount(updatedRecord.getAmount());
        existing.setType(updatedRecord.getType());
        existing.setCategory(updatedRecord.getCategory());
        existing.setDate(updatedRecord.getDate());
        existing.setDescription(updatedRecord.getDescription());
        existing.setUserId(updatedRecord.getUserId());

        return repo.save(existing);
    }

    public List<FinancialRecord> filter(String type,
                                        String category,
                                        String startDate,
                                        String endDate) {

        final LocalDate start = startDate != null ? LocalDate.parse(startDate) : null;
        final LocalDate end = endDate != null ? LocalDate.parse(endDate) : null;

        return repo.findAll().stream()
                .filter(r -> type == null || r.getType().name().equalsIgnoreCase(type))
                .filter(r -> category == null || r.getCategory().equalsIgnoreCase(category))
                .filter(r -> start == null || !r.getDate().isBefore(start))
                .filter(r -> end == null || !r.getDate().isAfter(end))
                .toList();
    }
}
package com.example.financeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.financeapp.model.FinancialRecord;

public interface RecordRepository extends JpaRepository<FinancialRecord, Long> {
}

package com.example.batch;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ExcelDataWriter implements ItemWriter<ExcelData> {

    private final ExcelDataRepository repository;

    public ExcelDataWriter(ExcelDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void write(List<? extends ExcelData> items) {
        repository.saveAll(items);
    }
}

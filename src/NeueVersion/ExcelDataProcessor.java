package com.example.batch;

import org.springframework.batch.item.ItemProcessor;

public class ExcelDataProcessor implements ItemProcessor<ExcelData, ExcelData> {
    @Override
    public ExcelData process(ExcelData item) {
        // Transformation (z. B. Trimmen oder Validierung)
        item.setColumn1(item.getColumn1().trim());
        item.setColumn2(item.getColumn2().trim());
        return item;
    }
}

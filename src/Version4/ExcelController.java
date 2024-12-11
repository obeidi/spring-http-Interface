package com.example.exceltransformer.controller;

import com.example.exceltransformer.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/process-excel-files")
    public String processExcelFiles() {
        try {
            excelService.processExcelFiles();
            return "Excel-Dateien erfolgreich verarbeitet!";
        } catch (Exception e) {
            return "Fehler beim Verarbeiten der Excel-Dateien: " + e.getMessage();
        }
    }
}

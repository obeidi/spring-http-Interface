package com.example.exceltransformer.service;

import com.example.exceltransformer.model.VerdichtungVP;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class ExcelService {

    @Value("${excel.files.path}")
    private String excelFilesPath;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void processExcelFiles() throws IOException, InvalidFormatException {
        File dir = new File(excelFilesPath);
        File[] files = dir.listFiles((dir1, name) -> name.contains("rph") && name.endsWith(".xlsx"));

        if (files != null) {
            for (File file : files) {
                processExcelFile(file);
            }
        }
    }

    private void processExcelFile(File file) throws IOException, InvalidFormatException {
        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);  // Wir gehen davon aus, dass die Daten im ersten Sheet sind
            Iterator<Row> rowIterator = sheet.iterator();

            // Spring Data JPA verwendet, um Daten zu speichern
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) {
                    continue; // Überspringe die Header-Zeile
                }

                VerdichtungVP verdichtungVP = new VerdichtungVP();
                verdichtungVP.setColumn1(row.getCell(0).getStringCellValue());
                verdichtungVP.setColumn2(row.getCell(1).getStringCellValue());
                verdichtungVP.setColumn3(row.getCell(2).getStringCellValue());

                entityManager.persist(verdichtungVP);
            }
        }
    }
}

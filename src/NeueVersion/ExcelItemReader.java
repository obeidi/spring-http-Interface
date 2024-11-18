package com.example.batch;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.batch.item.ItemReader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class ExcelItemReader implements ItemReader<ExcelData> {

    private final Iterator<Row> rowIterator;

    public ExcelItemReader(String filePath) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0); // Erste Arbeitsmappe
        this.rowIterator = sheet.iterator();
        // Überspringe Header
        if (rowIterator.hasNext()) rowIterator.next();
    }

    @Override
    public ExcelData read() throws Exception {
        if (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            ExcelData data = new ExcelData();
            data.setColumn1(row.getCell(0).getStringCellValue());
            data.setColumn2(row.getCell(1).getStringCellValue());
            return data;
        }
        return null;
    }
}

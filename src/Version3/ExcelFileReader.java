package com.example.batch.reader;

import com.example.batch.model.RPHVerdichtungVP;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

@Component
public class ExcelFileReader implements ItemReader<RPHVerdichtungVP> {

    @Value("${batch.excel.file-path}")
    private String filePath;

    private Iterator<Row> rowIterator;

    @Override
    public RPHVerdichtungVP read() throws Exception {
        if (rowIterator == null) {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            rowIterator = sheet.iterator();
            rowIterator.next(); // Skip Header Row
        }

        if (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            RPHVerdichtungVP entity = new RPHVerdichtungVP();
            entity.setDate(row.getCell(0).getDateCellValue());
            entity.setIntegerValue((int) row.getCell(1).getNumericCellValue());
            entity.setDoubleValue(row.getCell(2).getNumericCellValue());
            return entity;
        }
        return null;
    }
}

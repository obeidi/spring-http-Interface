import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelReader implements ItemReader<Vertriebspartner> {

    private List<Vertriebspartner> vertriebspartnerList = new ArrayList<>();
    private Iterator<Vertriebspartner> iterator;

    @Value("${excel.directory.path}")
    private String directoryPath;

    public ExcelReader() {
        loadVertriebspartnerFromExcel();
    }

    private void loadVertriebspartnerFromExcel() {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".xlsx"));

        if (files == null || files.length == 0) {
            return;
        }

        for (File file : files) {
            try (FileInputStream excelFile = new FileInputStream(file);
                 Workbook workbook = new XSSFWorkbook(excelFile)) {

                Sheet sheet = workbook.getSheetAt(0);
                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    Vertriebspartner vertriebspartner = new Vertriebspartner();
                    vertriebspartner.setId((int) row.getCell(0).getNumericCellValue());
                    vertriebspartner.setName(row.getCell(1).getStringCellValue());
                    vertriebspartner.setAge((int) row.getCell(2).getNumericCellValue());
                    vertriebspartner.setDepartment(row.getCell(3).getStringCellValue());
                    vertriebspartnerList.add(vertriebspartner);
                }

                // Datei nach Verarbeitung verschieben oder löschen
                file.renameTo(new File(directoryPath + "/processed/" + file.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        iterator = vertriebspartnerList.iterator();
    }

    @Override
    public Vertriebspartner read() {
        if (iterator != null && iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}

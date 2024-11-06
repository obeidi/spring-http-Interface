import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ExcelReader implements ItemReader<Employee> {
    private List<Employee> employees = new ArrayList<>();
    private Iterator<Employee> iterator;

    public ExcelReader() {
        // Starte die Überwachung des Verzeichnisses
        loadEmployeesFromExcel();
    }

    private void loadEmployeesFromExcel() {
        File dir = new File("C:/path/to/your/excel/files");
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
                    Employee employee = new Employee();
                    employee.setId((int) row.getCell(0).getNumericCellValue());
                    employee.setName(row.getCell(1).getStringCellValue());
                    employee.setAge((int) row.getCell(2).getNumericCellValue());
                    employee.setDepartment(row.getCell(3).getStringCellValue());
                    employees.add(employee);
                }

                // Datei nach Verarbeitung verschieben oder löschen
                file.renameTo(new File("C:/path/to/your/excel/files/processed/" + file.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        iterator = employees.iterator();
    }

    @Override
    public Employee read() {
        if (iterator != null && iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}

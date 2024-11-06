import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(Employee employee) {
        // Hier können Daten validiert oder transformiert werden
        return employee;
    }
}

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeWriter implements ItemWriter<Employee> {

    private static final String INSERT_QUERY = "INSERT INTO EMPLOYEE_DATA (ID, NAME, AGE, DEPARTMENT) VALUES (?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends Employee> employees) throws Exception {
        for (Employee employee : employees) {
            jdbcTemplate.update(INSERT_QUERY,
                    employee.getId(),
                    employee.getName(),
                    employee.getAge(),
                    employee.getDepartment());
        }
    }
}

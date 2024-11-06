import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VertriebspartnerWriter implements ItemWriter<Vertriebspartner> {

    private static final String INSERT_QUERY = "INSERT INTO VERTRIEBSPARTNER (ID, NAME, AGE, DEPARTMENT) VALUES (?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends Vertriebspartner> vertriebspartnerList) throws Exception {
        for (Vertriebspartner vertriebspartner : vertriebspartnerList) {
            jdbcTemplate.update(INSERT_QUERY,
                    vertriebspartner.getId(),
                    vertriebspartner.getName(),
                    vertriebspartner.getAge(),
                    vertriebspartner.getDepartment());
        }
    }
}

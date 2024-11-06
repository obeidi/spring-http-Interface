import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class VertriebspartnerProcessor implements ItemProcessor<Vertriebspartner, Vertriebspartner> {
    @Override
    public Vertriebspartner process(Vertriebspartner vertriebspartner) {
        // Hier können Daten validiert oder transformiert werden
        return vertriebspartner;
    }
}

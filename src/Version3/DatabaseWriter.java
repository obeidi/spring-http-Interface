package com.example.batch.writer;

import com.example.batch.model.RPHVerdichtungVP;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class DatabaseWriter implements ItemWriter<RPHVerdichtungVP> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void write(List<? extends RPHVerdichtungVP> items) {
        for (RPHVerdichtungVP item : items) {
            entityManager.persist(item);
        }
    }
}

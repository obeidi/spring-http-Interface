package com.example.batch.processor;

import com.example.batch.model.RPHVerdichtungVP;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ExcelRowProcessor implements ItemProcessor<RPHVerdichtungVP, RPHVerdichtungVP> {

    @Override
    public RPHVerdichtungVP process(RPHVerdichtungVP item) {
        // Transformation falls notwendig
        return item;
    }
}

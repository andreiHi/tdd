package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class LaurelWorldProducer implements WorldProducer{

    @Override
    public String getWorld() {
        return "Laurel";
    }
}

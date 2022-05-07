package org.springframework.samples.petclinic.sfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("externalized")
@Primary
public class PropertiesWorldProducerImpl implements WorldProducer {
    @Value("${say.word}")
    private String word;
    @Override
    public String getWorld() {
        return word;
    }
}

package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Primary
@Profile("yanny")
public class YannyWorldProducer implements WorldProducer {

    @Override
    public String getWorld() {
        return "Yanny";
    }
}

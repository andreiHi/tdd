package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Component;

@Component
public class YannyWorldProducer implements WorldProducer {

    @Override
    public String getWorld() {
        return "Yanny";
    }
}

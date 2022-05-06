package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Service;

@Service
public class HearingInterpret {

    private final WorldProducer worldProducer;

    public HearingInterpret(WorldProducer worldProducer) {
        this.worldProducer = worldProducer;
    }

    public String whatIHeard() {
        String world = worldProducer.getWorld();
        System.out.println(world);
        return world;
    }
}

package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.BaseConfig;
import org.springframework.samples.petclinic.sfg.HearingInterpret;
import org.springframework.samples.petclinic.sfg.YannyConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(("base-test"))
@SpringJUnitConfig(classes = {BaseConfig.class, YannyConfig.class})
class YannyWorldProducerTest {

    @Autowired
    HearingInterpret interpret;
    @Test
    void name() {
        String heard = interpret.whatIHeard();
        assertEquals("Yanny", heard);
    }
}
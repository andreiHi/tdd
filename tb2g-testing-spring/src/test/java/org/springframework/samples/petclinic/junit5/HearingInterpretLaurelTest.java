package org.springframework.samples.petclinic.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.sfg.BaseConfig;
import org.springframework.samples.petclinic.sfg.HearingInterpret;
import org.springframework.samples.petclinic.sfg.LaurelConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(classes = {BaseConfig.class, LaurelConfig.class})
class HearingInterpretLaurelTest {

    @Autowired
    HearingInterpret hearingInterpret;


    @Test
    void whatIHeard() {
        String heard = hearingInterpret.whatIHeard();
        assertEquals("Laurel", heard);
    }
}
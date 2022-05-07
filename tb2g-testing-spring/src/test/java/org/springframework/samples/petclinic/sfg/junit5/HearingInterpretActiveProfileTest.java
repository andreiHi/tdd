package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.HearingInterpret;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("yanny")
@SpringJUnitConfig(classes = {HearingInterpretActiveProfileTest.TestConfig.class})
class HearingInterpretActiveProfileTest {
    @Configuration
    @ComponentScan("org.springframework.samples.petclinic.sfg")
    static class TestConfig {

    }
    @Autowired
    HearingInterpret hearingInterpret;

    @Test
    void whatIHeard() {
        String heard = hearingInterpret.whatIHeard();
        assertEquals("Yanny", heard);
    }
}

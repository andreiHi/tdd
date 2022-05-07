package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterpret;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("component-scan")
@SpringJUnitConfig(classes = HearingInterpretComponentScanTest.TestConfig.class)
class HearingInterpretComponentScanTest {
    @Profile("component-scan")
    @Configuration
    @ComponentScan("org.springframework.samples.petclinic.sfg")
    static class TestConfig {

    }
    @Autowired
    HearingInterpret hearingInterpret;

    @Test
    void whatIHeard() {
        String heard = hearingInterpret.whatIHeard();
        assertEquals("Laurel", heard);
    }
}
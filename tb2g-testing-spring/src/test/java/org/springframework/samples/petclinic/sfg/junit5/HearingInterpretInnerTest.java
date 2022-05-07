package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterpret;
import org.springframework.samples.petclinic.sfg.LaurelWorldProducer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("inner-class")
@SpringJUnitConfig(classes = {HearingInterpretInnerTest.TestConfig.class})
class HearingInterpretInnerTest {

    @Profile("inner-class")
    @Configuration
    static class TestConfig {

        @Bean
        HearingInterpret hearingInterpret(LaurelWorldProducer laurelWorldProducer) {
            return new HearingInterpret(laurelWorldProducer);
        }
        @Bean
         LaurelWorldProducer laurelWorldProducer() {
            return new LaurelWorldProducer();
        }
    }
    @Autowired
    HearingInterpret interpret;

    @Test
    void whatIHeard() {
        String heard = interpret.whatIHeard();
        assertEquals("Laurel", heard);
    }
}
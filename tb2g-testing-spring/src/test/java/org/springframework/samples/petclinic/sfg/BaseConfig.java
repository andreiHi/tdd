package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseConfig {
    @Bean
    HearingInterpret hearingInterpret(WorldProducer worldProducer) {
        return new HearingInterpret(worldProducer);
    }
}

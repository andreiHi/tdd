package org.springframework.samples.petclinic.sfg;

import org.junit.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YannyConfig {

    @Bean
    public YannyWorldProducer yannyWorldProducer() {
        return new YannyWorldProducer();
    }
}

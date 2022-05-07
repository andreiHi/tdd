package org.springframework.samples.petclinic.sfg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(("base-test"))
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, LaurelConfig.class})
public class HearingInterpretTest {

    @Autowired
    HearingInterpret hearingInterpret;

    @Before
    public void setUp() {
        hearingInterpret = new HearingInterpret(new LaurelWorldProducer());
    }

    @Test
    public void whatIHeard() {
        String world = hearingInterpret.whatIHeard();
        assertEquals("Laurel", world);
    }
}
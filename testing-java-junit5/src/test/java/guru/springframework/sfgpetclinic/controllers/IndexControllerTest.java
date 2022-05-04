package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.ControllersTest;
import guru.springframework.sfgpetclinic.exceptions.ValueNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class IndexControllerTest implements ControllersTest {

    IndexController controller;

    @BeforeEach
    void setUp() {
        controller = new IndexController();
    }

    @DisplayName("Test Proper View name is returned for index page")
    @Test
    void index() {
        assertEquals("index", controller.index());
        assertEquals("index", controller.index(), "Wrong View Returned");
        assertEquals("index", controller.index(), () -> "For expensive case: Wrong View Returned");

        // AssertJ
        assertThat(controller.index()).isEqualTo("index");
    }

    @Test
    @DisplayName("Test oupsHandler method")
    void oupsHandler() {
        assertTrue("notimplemented".equals(controller.oupsHandler()), () -> "This is some expensive Message to build for my test");
    }

    @Test
    @DisplayName("Test oopsHandler method")
    void testThrow() {
        assertThrows(ValueNotFoundException.class, () -> controller.oopsHandler());
    }

    @Test
    @DisplayName("In the another thread")
    void testTimeOutPreempt() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(50);
            System.out.println("I got here ...");
        });
    }

    @Test
    @DisplayName("In the same thread")
    void testTimeOut() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(50);
            System.out.println("I got here ...");
        });
    }

    @Test
    void testAssumptionTrue() {
        String oo = "oo";
        assumeTrue("Foo".equals("F"  + oo));
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void testMeOnLinux() {
        System.out.println("This is Linux");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testMeOnWindows() {
        System.out.println("This is WINDOWS");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void testMeOnJava8() {
        System.out.println("This is Java 8");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_11)
    void testMeOnJava11() {
        System.out.println("This is Java 11");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USER", matches = "user")
    void testIfUserIsUser() {
        System.out.println("I am user");
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "USER", matches = "fred")
    void testIfUserIsFred() {
        System.out.println("I am fred");
    }
}
package org.ttd.junit5;

import org.junit.jupiter.api.*;

class GreetingTest {

    private Greeting greeting;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before - I am calling Once!!!");
    }

    @BeforeEach
    void setUp() {
        System.out.println("In BeforeEach ...");
        greeting = new Greeting();
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each ....");
    }

    @Test
    void helloWorld() {
        System.out.println(greeting.helloWorld());
    }

    @Test
    void testHelloWorld() {
        System.out.println(greeting.helloWorld("MIke"));
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After - I am only calling Once!!!");
    }
}
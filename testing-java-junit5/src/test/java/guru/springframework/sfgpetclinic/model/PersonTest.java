package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.CustomArgsProvider;
import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest implements ModelTests {
    @Test
    void groupedAssertions() {
        //given
        Person person = new Person(1L, "Joe", "Buck");
        //then
        assertAll("Test Property Set",
                () -> assertEquals("Joe", person.getFirstName()),
                () -> assertEquals("Buck", person.getLastName())
        );
    }

    @Test
    void groupedAssertionsMsg() {
        //given
        Person person = new Person(1L, "Joe", "Buck");
        //then
        assertAll("Test Property Set",
                () -> assertEquals("Joe", person.getFirstName(), "First Name Failed"),
                () -> assertEquals("Buck", person.getLastName(), "Last Name Failed")
        );
    }


    @DisplayName("Value Source Test ")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ValueSource(strings = {"Spring", "Framework", "Guru"})
    void testValueSource(String val) {
        System.out.println(val);
    }

    @DisplayName("Enum Source Test ")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @EnumSource(OwnerType.class)
    void enumTest(OwnerType val) {
        System.out.println(val);
    }

    @DisplayName("CSV Input Source Test ")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @CsvSource({
            "FL, 1, 1",
            "OH, 2, 2",
            "MI, 3, 3",
    })
    void csvInputTest(String stateName, int val1, int val2) {
        System.out.println(stateName + " = " + val1 + " : " + val2);
    }

    @DisplayName("CSV From File Source Test ")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @CsvFileSource(resources = "/input.csv", numLinesToSkip = 1)
    void csvFromFileTest(String stateName, int val1, int val2) {
        System.out.println(stateName + " = " + val1 + " : " + val2);
    }

    @DisplayName("Method Provider Test ")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @MethodSource("getArgs")
    void fromMethodTest(String stateName, int val1, int val2) {
        System.out.println(stateName + " = " + val1 + " : " + val2);
    }

    static Stream<Arguments> getArgs() {
        return Stream.of(Arguments.of("FL", 1, 1),
                Arguments.of("OH", 2, 5),
                Arguments.of( "MI", 3, 8));
    }


    @DisplayName("Custom Provider Test ")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ArgumentsSource(CustomArgsProvider.class)
    void fromCustomProviderTest(String stateName, int val1, int val2) {
        System.out.println(stateName + " = " + val1 + " : " + val2);
    }
}